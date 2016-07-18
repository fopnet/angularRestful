package ngdemo.web;

//import com.google.inject.Singleton;

import ngdemo.infrastructure.security.AuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Allow CORS requests.
 */
//@Singleton
public class ResponseCorsFilter implements Filter , AuthenticationToken{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse alteredResponse = ((HttpServletResponse) servletResponse);
            addHeadersFor200Response(alteredResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getMethod() != "OPTIONS") {
            filterChain.doFilter(request, servletResponse);
        } else {
        }
    }

    private void addHeadersFor200Response(HttpServletResponse response) {
       response.addHeader("Access-Control-Allow-Origin", "*");
       response.addHeader("Access-Control-Allow-Methods", "*, Cache-Control, Pragma, Origin, Authorization, X-Requested-With, POST, GET, PUT, OPTIONS, DELETE");
       response.addHeader("Access-Control-Allow-Headers", "*, Content-Type, GET, OPTIONS, " + TOKEN_HEADER_NAME);
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Max-Age", "3600");

    }
}
