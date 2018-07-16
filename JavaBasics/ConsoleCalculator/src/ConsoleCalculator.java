import java.util.Scanner;
import java.util.Stack;

public class ConsoleCalculator {
    private static Scanner scanner;
    private static Double result = new Double(0);
    private static Stack<String> operations = new Stack<>();
    private static Stack<Double> nums = new Stack<>();
    private static boolean start () {
        System.out.println("Instruction:\n");
        String cmd = scanner.next();
        while (!cmd.equals("quit") && !cmd.equals("start")) {
            System.out.println("Enter \"start\" or \"quit\"");
            cmd = scanner.next();
        }
        if (cmd.equals("quit")) {
            return false;
        }
        return true;
    }
    private static boolean numHolder (String cmd) {
        if (cmd.equals("quit")) {
            return false;
        }
        try {
            nums.push(Double.parseDouble(cmd));
        } catch (NumberFormatException e){
            System.out.println(e.toString());
        }
        return true;
    }
    private static boolean operationHolder (String cmd) {
        if (cmd.equals("quit")) {
            return false;
        }
        return true;
    }
    public static void main (String[] args) {
        scanner = new Scanner(System.in);
        if (!start()) {
            return;
        }
        String cmd;
        int num = 0;
        System.out.println("Enter a number! To exit enter \"quit\".");
        while (!(cmd = scanner.nextLine()).equals("quit")) {
            if (num % 2 == 0) {
                if (!numHolder(cmd)) {
                    break;
                }
                System.out.println("Enter a operation! To exit enter \"quit\".\nCurrent result: " + result);
            } else {
                if (!operationHolder(cmd)) {
                    break;
                }
                System.out.println("Enter a number! To exit enter \"quit\".");
            }
            ++num;
        }
        System.out.println("Result: " + result);
    }
}
