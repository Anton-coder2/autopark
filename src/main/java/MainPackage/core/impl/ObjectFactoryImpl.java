package MainPackage.core.impl;

import MainPackage.configurators.ObjectConfigurator;
import MainPackage.configurators.ProxyConfigurator;
import MainPackage.core.Context;
import MainPackage.core.ObjectFactory;
import MainPackage.core.Scanner;
import MainPackage.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        var subTypesOfObjectConfigurator = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (var type : subTypesOfObjectConfigurator) {
            objectConfigurators.add(type.getDeclaredConstructor().newInstance());
        }
        var subTypesOfProxyConfigurator = scanner.getSubTypesOf(ProxyConfigurator.class);
        for (var type : subTypesOfProxyConfigurator) {
            proxyConfigurators.add(create(type));
        }
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator conf : objectConfigurators) {
            conf.configure(object, context);
        }
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for(Method method : implementation.getMethods()) {
            if(method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    @Override
    public <T> T createObject(Class<T> implementation) throws Exception {
        T obj = null;
        try {
            obj = create(implementation);
            configure(obj);
            initialize(implementation, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        obj = makeProxy(implementation, obj);
        return obj;
    }

    private <T> T makeProxy(Class<T> implClass, T obj) throws Exception {
        for(ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            obj = (T) proxyConfigurator.makeProxy(obj, implClass, context);
        }
        return obj;
    }
}
