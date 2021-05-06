package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.IContratistaDAO;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.impl.DocumentoServicioImpl;
import pe.com.sedapal.agc.util.DbConstants;

@Service
public class ContratistaDAOImpl implements IContratistaDAO {

	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentoServicioImpl.class);
	
	private Error error;
	
	@Override
	public Map<String, Object> listaContratista(Empresa empresa) {

		Map<String, Object> out = null;
		try {
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PACKAGE_ENVIO)
					.withProcedureName(DbConstants.PRC_LIS_CONTRATISTAS)
					.declareParameters(
							new SqlParameter("V_N_ID_EMPR", OracleTypes.NUMBER),
							new SqlParameter("V_V_TIPO_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_V_RUC_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_V_NUME_CONT", OracleTypes.VARCHAR),
							new SqlParameter("V_V_NOMBR_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_V_ESTA_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_V_DIRE_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_V_COME_EMPR", OracleTypes.VARCHAR),
							new SqlParameter("V_D_FECH_INI_VIGE", OracleTypes.DATE),
							new SqlParameter("V_D_FECH_FIN_VIGE", OracleTypes.DATE),
							new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
							);
			
			MapSqlParameterSource paraMap = new MapSqlParameterSource().addValue("V_N_ID_EMPR", empresa.getCodigo())
																		.addValue("V_V_TIPO_EMPR", empresa.getTipoEmpresa())
																		.addValue("V_V_RUC_EMPR", empresa.getNroRUC())
																		.addValue("V_V_NUME_CONT", empresa.getNumeroContrato())
																		.addValue("V_V_NOMBR_EMPR", empresa.getDescripcion())
																		.addValue("V_V_ESTA_EMPR", empresa.getEstado())
																		.addValue("V_V_DIRE_EMPR", empresa.getDireccion())
																		.addValue("V_V_COME_EMPR", empresa.getComentario())
																		.addValue("V_D_FECH_INI_VIGE", empresa.getFechaInicioVigencia())
																		.addValue("V_D_FECH_FIN_VIGE", empresa.getFechaFinVigencia())
																		;
			out = this.jdbcCall.execute(paraMap);
		}
		catch (Exception e){
			logger.error("[AGC: ContratistaDAOImpl - listaContratista()] - "+e.getMessage());
		}
		
		return out;
		
	}

	@Override
	public List<Empresa> listaContratistaPersonal(Long idPers) throws Exception {
		Map<String, Object> out = null;
		List<Empresa> lista = new ArrayList<>();
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(DbConstants.DBSCHEMA)
				.withCatalogName(DbConstants.PACKAGE_ENVIO)
				.withProcedureName(DbConstants.PRC_LIS_EMPR_PERS)
				.declareParameters(
						new SqlParameter("V_N_IDPERS", OracleTypes.VARCHAR),
						new SqlOutParameter("C_OUT", OracleTypes.CURSOR),
						new SqlOutParameter("N_RESP", OracleTypes.NUMBER),
						new SqlOutParameter("N_EJEC", OracleTypes.NUMBER),
						new SqlOutParameter("V_EJEC", OracleTypes.VARCHAR)
						);	
		
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("V_N_IDPERS", idPers);
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = ((BigDecimal)out.get("N_RESP")).intValue();
			
			if(resultado == 1) {
				lista = mapeaEmpresa(out);							
			} else {
				String mensaje = (String)out.get("V_EJEC");
				String mensajeInterno = (String)out.get("N_EJEC");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("N_RESP");
			String mensaje = (String)out.get("V_EJEC");
			String mensajeInterno = (String)out.get("N_EJEC");
			this.error = new Error(resultado,mensaje,mensajeInterno);
			logger.error("[AGC: ContratistaDAOImpl - listaContratistaPersonal()] - "+e.getMessage());
		}
		
		return lista;
	}
	
	private List<Empresa> mapeaEmpresa(Map<String, Object> resultados){
		Empresa item;
		List<Empresa> listaEmpresa = new ArrayList<>();
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("C_OUT");
		
		for(Map<String, Object> map : lista) {
			item = new Empresa();
			item.setCodigo(((BigDecimal)map.get("n_idempr")).intValue());
			item.setTipoEmpresa((String)map.get("v_tipoempr"));
			item.setNroRUC((String)map.get("v_rucempr"));
			item.setNumeroContrato((String)map.get("v_numecont"));
			item.setDescripcion((String)map.get("v_nombrempr"));
			item.setEstado((String)map.get("v_estaempr"));
			
			Timestamp fechaFinTimer = (Timestamp) map.get("d_fechfinvige");
			item.setFechaFinVigencia(fechaFinTimer.getTime());

			Timestamp fechaIniTimer = (Timestamp) map.get("d_fechinivige");
			item.setFechaInicioVigencia(fechaIniTimer.getTime());
			listaEmpresa.add(item);		
		}
		return listaEmpresa;
	}

	@Override
	public Error getError() {
		return this.error;
	}

}
