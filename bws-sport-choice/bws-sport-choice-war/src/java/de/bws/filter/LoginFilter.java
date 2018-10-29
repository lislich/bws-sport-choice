/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.filter;

import de.bws.namedBeans.LoginNB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lisa
 */
public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginNB login = null;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if(session != null){
            login = (LoginNB)session.getAttribute("loginNB");
        }
        
        if(login != null){
            if(!login.isAngemeldet()){
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/nosecure/login.xhtml");
            }
        }
        
        chain.doFilter(httpRequest, httpResponse);
        
    }

    @Override
    public void destroy() {
        //TODO
    }
    
}
