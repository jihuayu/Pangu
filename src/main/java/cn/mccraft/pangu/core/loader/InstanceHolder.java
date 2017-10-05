package cn.mccraft.pangu.core.loader;

import cn.mccraft.pangu.core.util.ReflectUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * instance holder to storage some class's instance
 */
public interface InstanceHolder {
    /**
     * The class to instance
     */
    Map<Class<?>, Object> loaderInstanceMap = new HashMap<>();

    /**
     * Getting the instance of class.
     * Firstly, it will find in {@link InstanceHolder##loaderInstanceMap}.
     * Secondly, it will find in class's fields named "instance", "INSTANCE".
     * or method like "getInstance", "instance".
     * At last storage to {@link InstanceHolder##loaderInstanceMap} if found instance or return null
     *
     * @param object class or object your want to get or storage
     * @return null if no saved instance
     */
    static Object getInstance(@Nonnull Object object) {
        // check if object is a class to invoke static load
        boolean isStatic = object instanceof Class;

        // get the class with diff status
        Class<?> loaderClass = isStatic ? (Class<?>) object : object.getClass();

        Object cachedInstance = loaderInstanceMap.get(loaderClass);

        // try to find instance
        if (cachedInstance == null) {
            if (!isStatic) cachedInstance = object;
            if (cachedInstance == null) cachedInstance = ReflectUtils.getField(loaderClass, "instance");
            if (cachedInstance == null) cachedInstance = ReflectUtils.getField(loaderClass, "INSTANCE");
            if (cachedInstance == null) cachedInstance = ReflectUtils.invokeMethod(loaderClass, "getInstance");
            if (cachedInstance == null) cachedInstance = ReflectUtils.invokeMethod(loaderClass, "instance");
            if (cachedInstance != null) loaderInstanceMap.put(loaderClass, cachedInstance);
        }

        return cachedInstance;
    }

    /**
     * Putting the instance to {@link InstanceHolder##loaderInstanceMap} and return it
     * @param object the instance your gotta storage
     * @return the instance you given
     */
    static Object putInstance(@Nonnull Object object){
        loaderInstanceMap.put(object.getClass(), object);
        return object;
    }

    /**
     * Try to find instance for class
     *
     * @return true while found, false while couldn't
     */
    static boolean searchInstance(Class<?> clazz) {
        return getInstance(clazz) != null;
    }
}
