package ngdemo.infrastructure.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This entry point is called once the request missing the authentication but if
 * the request dosn't have the cookie then we send the unauthorized response.
 *
 * Created by Felipe on 11/07/2016.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static Logger logger = Logger.getLogger(RestAuthenticationEntryPoint.class);

    public void commence( HttpServletRequest request, HttpServletResponse response,
                          AuthenticationException authException ) throws IOException {
        logger.debug("<--- Inside authentication entry point --->");
        // this is very important for a REST based API login.
        // WWW-Authenticate header should be set as FormBased , else browser will show login dialog with realm
        response.setHeader("WWW-Authenticate", "FormBased");
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

        if (SecurityContextHolder.getContext() != null)
            logger.info( SecurityContextHolder.getContext().getAuthentication() );


    }


}