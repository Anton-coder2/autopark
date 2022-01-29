package MainPackage.core;

import MainPackage.config.Config;

public interface Context {
    <T> T getObject(Class<T> type) throws Exception;
    Config getConfig();
}
