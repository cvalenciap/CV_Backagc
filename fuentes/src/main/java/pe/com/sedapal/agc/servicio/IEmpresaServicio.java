package pe.com.sedapal.agc.servicio;

import pe.com.sedapal.agc.model.Empresa;
import pe.com.sedapal.agc.model.Item;
import pe.com.sedapal.agc.model.request.EmpresaRequest;
import pe.com.sedapal.agc.model.request.PageRequest;
import pe.com.sedapal.agc.model.response.Error;
import java.util.List;
import java.util.Map;

public interface IEmpresaServicio {

	List<Empresa> getCompanies(EmpresaRequest empresaRequest, PageRequest pageRequest);

	String addCompany(EmpresaRequest empresaRequest);

	String deleteCompany(Integer companyCode, String user);

	Empresa updateCompany(EmpresaRequest empresaRequest);

	Empresa getCompanyByCode(Integer companyCode);

	Error getError();
	
	Map<String,Object> listarEmpresaItem(Integer idEmpresa);
	
	void registrarEmpresaItem(List<Item> listaItem, Integer idEmpresa);

}
