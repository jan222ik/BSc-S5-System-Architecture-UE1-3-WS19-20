package component.beans.util;

import java.util.function.Consumer;

public final class SetterHelper {

    private SetterHelper() {
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
    public static void ifClass(Object newValue, Class<?> clazz, Runnable runIfTrue, Consumer<Class<?>>... onClassCast) {
        if (newValue.getClass().equals(clazz)) {
            runIfTrue.run();
        } else {
            for (Consumer<Class<?>> consumer : onClassCast) {
                consumer.accept(newValue.getClass());
            }
        }
    }
}
