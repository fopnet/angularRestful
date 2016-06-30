package ngdemo.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by eii5 on 21/09/2015.
 */
//@Configuration
//@ComponentScan({"ngdemo"})
//@ImportResource("/WEB-INF/spring/appServlet/spring-context.xml")
public class SpringConfig {

    @Bean
    public MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        return new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter();
    }

}
