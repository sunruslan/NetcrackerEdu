package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements2Impl implements ControlFlowStatements2 {
    @Override
    public int getFunctionValue(int x) {
        int f;
        if (x < -2 || x > 2) {
            f = 2 * x;
        } else {
            f = -3 * x;
        }
        return f;
    }

    @Override
    public String decodeMark(int mark) {
        String res = "";
        switch (mark) {
            case 1:
                res = "Fail";
                break;
            case 2:
                res = "Poor";
                break;
            case 3:
                res = "Satisfactory";
                break;
            case 4:
                res = "Good";
                break;
            case 5:
                res = "Excellent";
                break;
            default:
                res = "Error";
        }
        return res;
    }

    @Override
    public double[][] initArray() {
        double[][] arr = new double[5][8];
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 8; ++j) {
                arr[i][j] = Math.pow(i, 4) - Math.sqrt(j);
            }
        }
        return arr;
    }

    @Override
    public double getMaxValue(double[][] array) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
                if (max < array[i][j]) {
                    max = array[i][j];
                }
            }
        }
        return max;
    }

    @Override
    public Sportsman calculateSportsman(float P) {
        Sportsman sportsman = new Sportsman();
        float dist = 10;
        while (sportsman.getTotalDistance() <= 200) {
            sportsman.addDay(dist);
            dist *= 1 + P/100;
        }
        return sportsman;
    }
}

