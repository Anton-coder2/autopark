package MainPackage.config.impl;

import MainPackage.config.Config;
import MainPackage.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> subTypes = scanner.getSubTypesOf(target);
        if(subTypes.size() == 1) {
            return subTypes.stream().findFirst().get();
        } else {
            Class<?> value = interfaceToImplementation.get(target);
            if(value != null) {
                return (Class<? extends T>) value;
            } else {
                throw new RuntimeException("target interface has 0 or more than one impl");
            }
        }
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
