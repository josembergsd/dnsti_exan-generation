package br.gov.pi.appfapepi.v1.filter;
/**
 * Monitora toas as url's
 */

import br.gov.pi.appfapepi.v1.util.TokenUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@WebFilter(urlPatterns = {"/*"}, description = "Session checker filter")
public class LoginFilter implements Filter, Serializable {

    @Inject
    private TokenUtil tokenUtil;
    //Se as url's naõ vierem da página de login retorna para a mesma
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(!req.getRequestURI().endsWith("login.xhtml") && !isTokenValid(req)){
            resp.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }
        chain.doFilter(req, resp);
    }
    //Verifica se o expirationtime é válido e o token não é vazio
    private boolean isTokenValid(HttpServletRequest request){
        return tokenUtil.isExpirationTimeFromCookieValid(request) && !tokenUtil.getTokenFromCookies(request).isEmpty();
    }
}
