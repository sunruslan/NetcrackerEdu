package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements1Impl implements ControlFlowStatements1 {
    @Override
    public float getFunctionValue(float x) {
        float f;
        if (x > 0) {
            f = 2 * (float) Math.sin(x);
        } else {
            f = 6 - x;
        }
        return f;
    }

    @Override
    public String decodeWeekday(int weekday) {
        String ans = "";
        switch (weekday) {
            case 1:
                ans = "Monday";
                break;
            case 2:
                ans = "Tuesday";
                break;
            case 3:
                ans = "Wednesday";
                break;
            case 4:
                ans = "Thursday";
                break;
            case 5:
                ans = "Friday";
                break;
            case 6:
                ans = "Saturday";
                break;
            case 7:
                ans = "Sunday";
                break;
        }
        return ans;
    }

    @Override
    public int[][] initArray() {
        int[][] arr = new int[8][5];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 5; ++j) {
                arr[i][j] = i * j;
            }
        }
        return arr;
    }

    @Override
    public int getMinValue(int[][] array) {
        int min = array[0][0];
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j){
                if (min > array[i][j]) {
                    min = array[i][j];
                }
            }
        }
        return min;
    }

    @Override
    public BankDeposit calculateBankDeposit(double P) {
        BankDeposit bankDeposit = new BankDeposit();
        bankDeposit.amount = 1000;
        bankDeposit.years = 0;
        while (bankDeposit.amount < 5000) {
            bankDeposit.amount *= 1 + P / 100;
            bankDeposit.years += 1;
        }
        return bankDeposit;
    }
}
