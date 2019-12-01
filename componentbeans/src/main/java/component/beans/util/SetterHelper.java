package component.beans.util;



import nu.pattern.OpenCV;

import java.util.function.Consumer;

public final class SetterHelper {

    private SetterHelper() {
    }

    private static boolean isInitialized = false;

    public static void initOpenCV() {
        if (!isInitialized) {
            OpenCV.loadLocally();
            isInitialized = true;
        }
    }

    public static void between(int actual, int lower, int upper, Runnable runIfTrue) {
        if (actual <= upper && actual >= lower) {
            runIfTrue.run();
        }
    }

    public static void notNeg(int actual, Runnable runIfTrue) {
        if (actual >= 0) {
            runIfTrue.run();
        }
    }

    public static void between(double actual, double lower, double upper, Runnable runIfTrue) {
        if (actual <= upper && actual >= lower) {
            runIfTrue.run();
        }
    }

    public static void notNeg(double actual, Runnable runIfTrue) {
        if (actual >= 0) {
            runIfTrue.run();
        }
    }

    public static void notNull(Object o, Runnable runIfTrue) {
        if (o != null) {
            runIfTrue.run();
        }
    }

    @SafeVarargs
    public static void ifNullableClass(Object newValue, Class<?> clazz, Runnable onNull, Runnable runIfTrue, Consumer<Class<?>>... onClassCast) {
        if (newValue == null) {
            onNull.run();
            return;
        }
        if (newValue.getClass().equals(clazz)) {
            runIfTrue.run();
        } else {
            for (Consumer<Class<?>> consumer : onClassCast) {
                consumer.accept(newValue.getClass());
            }
        }
    }
}
