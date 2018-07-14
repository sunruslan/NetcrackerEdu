package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements3Impl implements ControlFlowStatements3 {
    @Override
    public double getFunctionValue(double x) {
        double f;
        if (x <= 0) {
            f = -x;
        } else if (x > 0 && x < 2) {
            f = x * x;
        } else {
            f = 4;
        }
        return f;
    }

    @Override
    public String decodeSeason(int monthNumber) {
        String season;
        switch (monthNumber) {
            case 1:
                season = "Winter";
                break;
            case 2:
                season = "Winter";
                break;
            case 3:
                season = "Spring";
                break;
            case 4:
                season = "Spring";
                break;
            case 5:
                season = "Spring";
                break;
            case 6:
                season = "Summer";
                break;
            case 7:
                season = "Summer";
                break;
            case 8:
                season = "Summer";
                break;
            case 9:
                season = "Autumn";
                break;
            case 10:
                season = "Autumn";
                break;
            case 11:
                season = "Autumn";
                break;
            case 12:
                season = "Winter";
                break;
            default:
                season = "Error";
        }
        return season;
    }

    @Override
    public long[][] initArray() {
        long[][] arr = new long[8][5];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 5; ++j) {
                arr[i][j] = (long)Math.pow(Math.abs(i - j), 5);
            }
        }
        return arr;
    }

    @Override
    public int getMaxProductIndex(long[][] array) {
        int index = 0;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < array.length; ++i) {
            long mul = array[i][0];
            for (int j = 1; j < array[i].length; ++j) {
                mul *= array[i][j];
            }
            if (mul > max) {
                max = mul;
                index = i;
            }
        }
        return index;
    }

    @Override
    public float calculateLineSegment(float A, float B) {
        while (A >= 0) {
            A -= B;
        }
        return A+B;
    }
}
