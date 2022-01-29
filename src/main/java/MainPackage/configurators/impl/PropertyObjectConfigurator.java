package MainPackage.configurators.impl;

import MainPackage.configurators.ObjectConfigurator;
import MainPackage.core.Context;
import MainPackage.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if(path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        }
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object t, Context context) {
        for(Field field : t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                String value;
                if(property.value().equals("")) {
                    value = properties.get(field.getName());
                } else {
                    value = properties.get(property.value());
                }
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
