package MainPackage.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation) throws Exception;
}
