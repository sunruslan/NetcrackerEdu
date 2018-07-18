import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.Stack;

public class ConsoleCalculator {
    private static Method[] methods = Operation.class.getMethods();
    private static int bracket = 0;
    private static Scanner scanner;
    private static Stack<String> operations = new Stack<>();
    private static Stack<Double> nums = new Stack<>();
    private static boolean hasop = false;
    private static boolean needNum = false, needBracket = true, needOp = true;
    private static boolean start () {
        System.out.println("Instruction:\nExample(inline mode): start ( 2 + ( ( 3 - 4 ) * 9 ) ) quit\nEnter \"start\" or \"quit\":");
        String cmd = scanner.next();
        while (!cmd.equals("quit") && !cmd.equals("start")) {
            System.out.println("Enter \"start\" or \"quit\":");
            cmd = scanner.next();
        }
        return !cmd.equals("quit");
    }
    private static void numHolder (String cmd){
        try {
            nums.push(Double.parseDouble(cmd));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        needNum = false;
        needOp = needBracket = true;
    }
    private static void operationHolder (String cmd){
        switch(cmd) {
            case "(":
                ++bracket;
                needOp = false;
                needBracket = needNum = true;
                break;
            case ")":
                --bracket;
                if (bracket < 0){
                    System.err.println("Incorrect expression");
                    System.exit(1);
                }
                needNum = false;
                needBracket = needOp = true;
                if (bracket == 0) {
                    needOp = needBracket = false;
                }
                holder();
                break;
            default:
                operations.push(cmd);
                needOp = false;
                needBracket = needNum = true;
        }
    }
    private static void holder () {
        String op = operations.pop();
        for (Method m: methods) {
            if (m.getName().equals(op)) {
                int valence = m.getParameterCount();
                Double[] params = new Double[valence];
                for (int i = 0 ; i < valence; ++i) {
                    params[valence-i-1] = nums.pop();
                }
                try {
                    try {
                        nums.push((Double) m.invoke(null, params));
                    } catch (ArithmeticException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    System.err.println("Invoke error");
                    return;
                }
                return;
            }
        }
    }
    private static String operation(String name){
        for (Method m: methods) {
            Annotation annotaion = m.getAnnotation(MyAnnotation.class);
            if (annotaion instanceof MyAnnotation && ((MyAnnotation) annotaion).shortName().equals(name)){
                return m.getName();
            } else if (name.equals(")") || name.equals("(")) {
                return name;
            }
        }
        return null;
    }
    private static void quit () {
        if (nums.size() > 1 || !operations.isEmpty() || bracket != 0){
            System.err.println("Incorrect expression");
            System.exit(0);
        }
        System.out.println("Result: " + (nums.isEmpty() ? 0 : nums.pop()));
        System.exit(0);
    }
    private static void showInstruction () {
        if (!needOp && !needNum && needBracket)
            System.out.println("Enter a bracket (to exit enter \"quit\"):");
        if (!needOp && needNum && !needBracket)
            System.out.println("Enter a number (to exit enter \"quit\"):");
        if (!needOp && needNum && needBracket)
            System.out.println("Enter a number/bracket (to exit enter \"quit\"):");
        if (needOp && !needNum && !needBracket)
            System.out.println("Enter a operation (to exit enter \"quit\"):");
        if (needOp && !needNum && needBracket)
            System.out.println("Enter a operation/bracket (to exit enter \"quit\"):");
        if (needOp && needNum && !needBracket)
            System.out.println("Enter a operation/number (to exit enter \"quit\"):");
        if (needOp && needNum && needBracket)
            System.out.println("Enter a operation/number/bracket (to exit enter \"quit\"):");
        if (!needOp && !needNum && !needBracket)
            System.out.println("To exit enter \"quit\":");
    }
    private static void check (String cmd) {
        for (Method m: methods) {
            Annotation annotaion = m.getAnnotation(MyAnnotation.class);
            if (annotaion instanceof MyAnnotation && ((MyAnnotation) annotaion).isFunction()
                    && m.getName().equals(cmd)) {
                return;
            }
        }
        if (cmd.equals("quit") || operation(cmd) != null && needOp  ||
                (cmd.equals(")") || cmd.equals("(")) && needBracket) {
        } else if (needNum) {
            try {
                Double.parseDouble(cmd);
            } catch (NumberFormatException e) {
                System.err.println("Incorrect input");
                System.exit(1);
            }
        } else {
            System.err.println("Incorrect input");
            System.exit(1);
        }
    }
    public static void main (String[] args) {
        boolean inline = args.length > 0 && args[0].equals("inline");
        scanner = new Scanner(System.in);
        if (!start()) {
            return;
        }
        if (!inline) {
            showInstruction();
        }
        String cmd;
        String op;
        while (scanner.hasNext()) {
            cmd = scanner.next();
            if (cmd.equals("quit")) {
                quit();
            }
            check(cmd);
            if ((op = operation(cmd)) != null) {
                operationHolder(op);
            } else {
                numHolder(cmd);
            }
            if (!inline) {
                showInstruction();
            }
        }
    }
}
