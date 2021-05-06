package pe.com.sedapal.agc.servicio.impl;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.agc.dao.IActividadDAO;
import pe.com.sedapal.agc.dao.IEmpresaDAO;
import pe.com.sedapal.agc.exceptions.AgcException;
import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.request.EmpresaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import pe.com.sedapal.agc.servicio.IEmpresaServicio;
import pe.com.sedapal.agc.util.Constantes;
import pe.com.sedapal.agc.util.Constantes.MantenimientoEmpresas;

@Service
public class EmpresaServicioImpl implements IEmpresaServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpresaServicioImpl.class);

	private Error error;

	@Autowired
	IActividadDAO dao;

	@Autowired
	private IEmpresaDAO daoCompany;

	@Override
	public Error getError() {
		if(this.error == null) {
			return daoCompany.getError();
		} else {
			return this.error;
		}
	}

	@Override
	public List<Empresa> getCompanies(EmpresaRequest empresaRequest, PageRequest pageRequest) {
		this.error = null;
		return daoCompany.getCompaniesFromRepository(empresaRequest, pageRequest);
	}

	@Override
	public String addCompany(EmpresaRequest empresaRequest) {
		Empresa company = parseFromEmpresaResponse(empresaRequest, false);
		boolean validationAdd = daoCompany.addCompanyToRepository(company, empresaRequest.getUsuario());
		if (validationAdd) {
			return Constantes.MESSAGE_ERROR.get(1000);
		} else {
			this.error = Constantes.obtenerError(9999, null);
			return null;
		}
	}

	@Override
	public Empresa updateCompany(EmpresaRequest empresaRequest) {
		Empresa company = parseFromEmpresaResponse(empresaRequest, true);
		boolean validationUpdate = daoCompany.updateCompanyFromRepository(company, empresaRequest.getUsuario());
		if (validationUpdate) { return company; } else { return null; }
	}

	@Override
	public String deleteCompany(Integer companyCode, String user) {
		this.error = null;
		String message = Constantes.MESSAGE_ERROR.get(1000);
		boolean validationDelete = daoCompany.deleteCompanyFromRepository(companyCode, user);
		if (!validationDelete) { message = Constantes.MESSAGE_ERROR.get(9999); }
		return message;
	}

	@Override
	public Empresa getCompanyByCode(Integer companyCode) {
		Empresa company = daoCompany.getCompanyByIdFromRepository(companyCode);
		return company;
	}

	private Empresa parseFromEmpresaResponse(EmpresaRequest empresaRequest, boolean switches) {
		Empresa company = new Empresa();
		company.setTipoEmpresa(empresaRequest.getTipoEmpresa());
		company.setNroRUC(empresaRequest.getRuc());
		company.setNumeroContrato(empresaRequest.getNumeroContrato());
		company.setDescripcion(empresaRequest.getRazonSocial());
		company.setTelefono(empresaRequest.getTelefono());
		company.setDireccion(empresaRequest.getDireccion());
		company.setComentario(empresaRequest.getComentario());
		company.setFechaInicioVigencia(empresaRequest.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		company.setFechaFinVigencia(empresaRequest.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		if (switches) {
			company.setCodigo(empresaRequest.getCodigo());
			company.setEstado(empresaRequest.getEstadoEmpresa());
		}
		return company;
	}

	@Override
	public Map<String, Object> listarEmpresaItem(Integer idEmpresa) {
		return this.daoCompany.listarEmpresaItem(idEmpresa);
	}

	@Override
	public void registrarEmpresaItem(List<Item> listaItem, Integer idEmpresa) {
		try {
			StringBuilder builder = new StringBuilder();
			for (Item item : listaItem) {
				builder.append(item.getId()).append(",").append(item.getEstado()).append(",").append(item.getUsuarioCreacion()).append("@");
			}
			builder.deleteCharAt(builder.length() - 1);
			this.daoCompany.registrarEmpresaItem(builder.toString(), idEmpresa);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AgcException(e.getMessage(),
					new Error(Constantes.CodigoErrores.ERROR_SERVICIO, MantenimientoEmpresas.MSE001));
		}
			
	}
}
