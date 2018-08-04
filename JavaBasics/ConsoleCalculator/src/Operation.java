public final class Operation {
    @MyAnnotation(shortName="+", isFunction = false)
    public static double plus (double a, double b) {
        return a + b;
    }
    @MyAnnotation(shortName="-", isFunction = false)
    public static double minus (double a, double b) {
        return a - b;
    }
    @MyAnnotation(shortName="/", isFunction = false)
    public static double divide (double a, double b)  throws ArithmeticException{
        if (b == 0) {
            throw new ArithmeticException("divide by zero");
        }
        return a / b;
    }
    @MyAnnotation(shortName="*", isFunction = false)
    public static double multiply (double a, double b) {
        return a * b;
    }
    @MyAnnotation(shortName="sqrt", isFunction = true)
    public static double sqrt (double a) {
        if (a < 0) {
            throw new ArithmeticException("argument less than zero");
        }
        return Math.sqrt(a);
    }
    @MyAnnotation(shortName="sqr", isFunction = true)
    public static double sqr (double a) {
        return a*a;
    }
    @MyAnnotation(shortName="abs", isFunction = true)
    public static double abs (double a) {
        return Math.abs(a);
    }
}