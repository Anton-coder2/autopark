package MainPackage.core.impl;

import MainPackage.config.Config;
import MainPackage.config.impl.JavaConfig;
import MainPackage.core.Cache;
import MainPackage.core.Context;
import MainPackage.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>,Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) throws Exception {
        T value = cache.get(type);
        if (value == null) {
            if (type.isInterface()) {
                Class<? extends T> impl = config.getImplementation(type);
                value = factory.createObject(impl);
            } else {
                value = factory.createObject(type);
            }
            cache.put(type, value);
        }
        return value;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
