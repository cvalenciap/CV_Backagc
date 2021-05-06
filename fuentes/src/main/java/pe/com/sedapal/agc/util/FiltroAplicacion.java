package pe.com.sedapal.agc.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FiltroAplicacion implements Filter {

    @Value("${url.validate.seguridad}")
    private String pathValidate;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (!httpServletRequest.getMethod().equals("OPTIONS")){
			if (!validatePath(httpServletRequest.getRequestURI())) {
				String token = httpServletRequest.getHeader("Authorization");
				if (token == null) {
					httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
				}
			}
		}
		if (httpServletResponse.getStatus() != HttpStatus.UNAUTHORIZED.value()) {

			chain.doFilter(request, response);
		}
	}

	private boolean validatePath(String url) {
		boolean resultado = true;
		String baseUrl = pathValidate;
		if (!baseUrl.isEmpty()) {
			if (baseUrl.length() <= url.length()) {
				for (int indice = 0; indice < baseUrl.length(); indice++){
					if (!baseUrl.substring(indice, indice + 1).equals(url.substring(indice, indice + 1))) {
						resultado = false;
					}
				}
			} else return false;

        }
		return resultado;
	}
	
	@Override
	public void init(FilterConfig arg0) {
        // TODO Auto-generated method stub
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
