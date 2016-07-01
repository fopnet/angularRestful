package ngdemo.repositories.impl.hibernate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

/**
 * Created by Felipe on 01/07/2016.
 */
public class UserTypeAdapter extends TypeAdapter<HibernateProxy> {
    @Override
    public void write(JsonWriter jsonWriter, HibernateProxy hibernateProxy) throws IOException {
        Hibernate.initialize(hibernateProxy);
    }

    @Override
    public HibernateProxy read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
