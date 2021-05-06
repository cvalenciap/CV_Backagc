package pe.com.sedapal.agc.dao;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.request.EmpresaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;

public interface IEmpresaDAO {

	List<Empresa> getCompaniesFromRepository(EmpresaRequest empresaRequest, PageRequest pageRequest);

	boolean deleteCompanyFromRepository(Integer code, String user);

	Empresa getCompanyByIdFromRepository(Integer code);

	boolean addCompanyToRepository(Empresa company, String user);

	boolean updateCompanyFromRepository(Empresa company, String user);

	Error getError();
	
	Map<String,Object> listarEmpresaItem(Integer idEmpresa);
	void registrarEmpresaItem(String tramaItem, Integer idEmpresa);
}
