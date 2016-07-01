package ngdemo.infrastructure.json;

import com.google.gson.GsonBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by Felipe on 01/07/2016.
 */
public class CustomGsonHttpMessageConverter extends GsonHttpMessageConverter {

    private static GsonExclusionStrategy annotationSkipFieldStratety = new GsonExclusionStrategy(HibernateProxy.class);

    public CustomGsonHttpMessageConverter() {
        super();
        super.setGson(new GsonBuilder()
                .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .setExclusionStrategies(annotationSkipFieldStratety)
                .create());

    }
}
