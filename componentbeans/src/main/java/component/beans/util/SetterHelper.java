package component.beans.util;

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
}
