package pe.com.sedapal.agc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.agc.dao.ITipoCargoDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.mapper.TipoCargoMapper;
import pe.com.sedapal.agc.model.TipoCargo;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.util.CastUtil;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.DbConstants;

@Repository
public class TipoCargoDAOImpl implements ITipoCargoDAO{

	private static final Logger logger = LoggerFactory.getLogger(TipoCargoDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	private SimpleJdbcCall jdbcCall;

	@Override
	public List<TipoCargo> listarTiposCargo() {
		List<TipoCargo> listaTipoCargo = new ArrayList<>();		
		try {
			Map<String, Object> respuestaConsulta = null;
			Integer resultadoEjecucion = 0;
			this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(DbConstants.DBSCHEMA)
					.withCatalogName(DbConstants.PCK_AGC_PERSONA_CONTRATISTA)
					.withProcedureName(DbConstants.PRC_LISTAR_TIPOS_CARGO)
					.withoutProcedureColumnMetaDataAccess()
					.declareParameters(
							new SqlOutParameter("O_C_LISTA_TIPO_CARGO", OracleTypes.CURSOR),
							new SqlOutParameter("O_N_RESP", OracleTypes.NUMBER),
							new SqlOutParameter("O_N_EJEC", OracleTypes.NUMBER),
							new SqlOutParameter("O_V_EJEC", OracleTypes.VARCHAR));
			
			respuestaConsulta = this.jdbcCall.execute();
			resultadoEjecucion = CastUtil.leerValorMapInteger(respuestaConsulta, "O_N_RESP");
			if (resultadoEjecucion == 1) {
				listaTipoCargo = TipoCargoMapper.mapRows(respuestaConsulta);
			} else {
				Integer codigoErrorBD = ((BigDecimal) respuestaConsulta.get("O_N_EJEC")).intValue();
				String mensajeErrorBD = (String) respuestaConsulta.get("O_V_EJEC");
				logger.error("Error al obtener Tipos de Cargo.", mensajeErrorBD);
				throw new AgcException(Constantes.Mensajes.ERROR_BD, new Error(codigoErrorBD, mensajeErrorBD));				
			}			
		} catch(Exception e) {
			logger.error("Error al obtener Tipos de Cargo.", e.getMessage(), e);
			throw new AgcException(Constantes.Mensajes.ERROR_BD,
					new Error(Constantes.CodigoErrores.ERROR_BD, e.getMessage()));
		}
		return listaTipoCargo;
	}

}
