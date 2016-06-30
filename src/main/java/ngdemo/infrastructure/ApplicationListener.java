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
public class ApplicationListener implements ServletContextListener {
    private final static Logger LOG = Logger.getLogger(ApplicationListener.class);

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
        // On Application Shutdown, please…
        /*DataSource dataSource = null;
        try {
            // 1. Go fetch that DataSource
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("jdbc/database");

            // 2. Deregister Driver
            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:firebirdsql:localhost:3050");
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ex) {
            LOG.info("Could not deregister driver:".concat(ex.getMessage()));
        } catch (NamingException ex) {
            LOG.info("Could not deregister driver:".concat(ex.getMessage()));
        } finally {
            // 3. For added safety, remove the reference to dataSource for GC to enjoy.
            dataSource = null;
        }*/

    }
}
