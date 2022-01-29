package MainPackage.configurators.impl;

import MainPackage.configurators.ObjectConfigurator;
import MainPackage.core.Context;
import MainPackage.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, Context context) {
        for(Field field : t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Autowired.class)) {
                Object obj = context.getObject(field.getType());
                field.setAccessible(true);
                field.set(t, obj);
            }
        }
    }
}
