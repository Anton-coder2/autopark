package MainPackage.threads.configurators;

import MainPackage.configurators.ProxyConfigurator;
import MainPackage.core.Context;
import MainPackage.threads.anntotations.Schedule;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {

    @Override
    public <T> T makeProxy(T obj, Class<T> implementation, Context context) throws Exception {

            for (Method method : obj.getClass().getMethods()) {
                //method.setAccessible(true);
                if (method.isAnnotationPresent(Schedule.class)) {
                    if (method.getModifiers() != Modifier.PUBLIC) {
                        throw new Exception("method " + method + " hasn't only public method");
                    }
                    /*if (!method.getReturnType().equals(void.class)) {
                        throw new Exception("method " + method + " doesn't return 'void'");
                    }*/

                        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
                        service.scheduleAtFixedRate(() -> {
                            //System.out.println("Hello");
                            try {
                                //Method m = implementation.getMethod("collect");
                                method.invoke(obj);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }, 0, method.getAnnotation(Schedule.class).delta()/1000, TimeUnit.SECONDS);

                    }
                }
            return obj;
            }


    }


