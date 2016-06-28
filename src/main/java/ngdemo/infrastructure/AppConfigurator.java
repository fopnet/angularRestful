package ngdemo.infrastructure;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by eii5 on 06/11/2015.
 */
public class AppConfigurator implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(AppConfigurator.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("log4j.properties");
        if (stream != null) {
            try {
                Properties properties = new Properties();
                properties.load(stream);
                PropertyConfigurator.configure(properties);

                LOG.info("configurando log4j para ngdemo...");

            } catch (Exception e) {
                LOG.error("---------------------------------------------");
                LOG.error("[ERROR] Erro ao configurar log4j - ngdemo.");
                LOG.error(e);
                LOG.error("---------------------------------------------");
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
