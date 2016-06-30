package ngdemo.infrastructure;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Created by eii5 on 21/09/2015.
 */
public class GipWebInitializer /*implements WebApplicationInitializer*/ {

   /* @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext dispatcherContext =
//                new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(SpringConfig.class);

        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("WEB-INF/spring/appServlet/spring-context.xml");

        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

 */

/*  @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
//        servletContext.addFilter("springSecurityFilterChain", new org.springframework.web.filter.DelegatingFilterProxy());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*//*");

    }*/

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("br.com.petrobras.ep.gip.configuration");
        return context;
    }
}