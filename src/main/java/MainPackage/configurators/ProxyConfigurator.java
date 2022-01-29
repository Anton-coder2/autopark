package MainPackage.configurators;

import MainPackage.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T obj, Class<T> implementation, Context context) throws Exception;
}
