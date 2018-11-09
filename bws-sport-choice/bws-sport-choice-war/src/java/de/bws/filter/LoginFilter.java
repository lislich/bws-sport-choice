/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.filter;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lisa
 */
@WebFilter("/secured/*")
public class LoginFilter implements Filter{

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String loginURL = httpRequest.getContextPath() + "/nosecure/login.xhtml";
        
        // Prüft ob eine Session existiert und ein Benutzer angemeldet ist
        boolean loggedIn = (session != null) && (session.getAttribute("benutzer") != null);
        // Prüft ob der Request auf die Login-Seite geht
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURL);
        
        boolean resourceRequest = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(httpRequest.getHeader("Faces-Request"));
        
        if(loggedIn || loginRequest || resourceRequest){
            if (!resourceRequest) { // Prevent browser from caching restricted resources. See also https://stackoverflow.com/q/4194207/157882
                httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                httpResponse.setDateHeader("Expires", 0); // Proxies.
            }
            chain.doFilter(httpRequest, httpResponse);
        } else if(ajaxRequest) {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().printf(AJAX_REDIRECT_XML, loginURL);
        }else {
            httpResponse.sendRedirect(loginURL);
        }
        
    }

    @Override
    public void destroy() {
        //TODO
    }
    
}
