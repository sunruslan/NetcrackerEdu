package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {
    private double[] vector;
    /**
     * Задает все элементы вектора (определяет длину вектора).
     * Передаваемый массив не клонируется.
     *
     * @param elements Не равен null
     */
    @Override
    public void set(double[] elements) {
        vector = elements;
    }

    /**
     * Возвращает все элементы вектора. Массив не клонируется.
     */
    @Override
    public double[] get() {
        return vector;
    }

    /**
     * Возвращает копию вектора (такую, изменение элементов
     * в которой не приводит к изменению элементов данного вектора).<br/>
     * Рекомендуется вызвать метод clone() у самого массива или использовать
     * {@link System#arraycopy(Object, int, Object, int, int)}.
     */
    @Override
    public ArrayVector clone() {
        ArrayVector copy = new ArrayVectorImpl();
        ((ArrayVectorImpl) copy).vector = new double[vector.length];
        System.arraycopy(vector, 0, ((ArrayVectorImpl) copy).vector, 0, vector.length);
        return copy;
    }

    /**
     * Возвращает число элементов вектора.
     */
    @Override
    public int getSize() {
        return vector.length;
    }

    /**
     * Изменяет элемент по индексу.
     *
     * @param index В случае выхода индекса за пределы массива:<br/>
     *              а) если index<0, ничего не происходит;<br/>
     *              б) если index>=0, размер массива увеличивается так, чтобы index стал последним.
     * @param value
     */
    @Override
    public void set(int index, double value) {
        if (index >= 0) {
            if (index < vector.length) {
                vector[index] = value;
            } else {
                double[] newVector = new double[index + 1];
                System.arraycopy(vector, 0, newVector, 0, vector.length);
                newVector[index] = value;
                this.set(newVector);
            }
        }
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index В случае выхода индекса за пределы массива
     *              должно генерироваться ArrayIndexOutOfBoundsException
     */
    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= 0 && index < vector.length) {
            return vector[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Возвращает максимальный элемент вектора.
     */
    @Override
    public double getMax() {
        double max = Double.MIN_VALUE;
        for (double e: vector) {
            if (e > max) {
                max = e;
            }
        }
        return max;
    }

    /**
     * Возвращает минимальный элемент вектора.
     */
    @Override
    public double getMin() {
        double min = Double.MAX_VALUE;
        for (double e: vector) {
            if (e < min) {
                min = e;
            }
        }
        return min;
    }

    /**
     * Сортирует элементы вектора в порядке возрастания.
     */
    @Override
    public void sortAscending() {
        Arrays.sort(vector);
    }

    /**
     * Умножает вектор на число.<br/>
     * Замечание: не пытайтесь использовать безиндексный цикл foreach:
     * для изменения элемента массива нужно знать его индекс.
     *
     * @param factor
     */
    @Override
    public void mult(double factor) {
        for (int i = 0; i < vector.length; ++i) {
            vector[i] *= factor;
        }
    }

    /**
     * Складывает вектор с другим вектором, результат запоминает в элементах данного вектора.<br/>
     * Если векторы имеют разный размер, последние элементы большего вектора не учитываются<br/>
     * (если данный вектор - больший, его размер менять не надо, просто не меняйте последние элементы).
     *
     * @param anotherVector Не равен null
     * @return Ссылка на себя (результат сложения)
     */
    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        for (int i = 0; i < Math.min(vector.length, anotherVector.getSize()); ++i) {
            vector[i] += anotherVector.get(i);
        }
        return this;
    }

    /**
     * Возвращает скалярное произведение двух векторов.<br/>
     * Если векторы имеют разный размер, последние элементы большего вектора не учитываются.
     *
     * @param anotherVector Не равен null
     */
    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double scalar = 0;
        for (int i = 0; i < Math.min(anotherVector.getSize(), vector.length); ++i) {
            scalar += (vector[i] * anotherVector.get(i));
        }
        return scalar;
    }

    /**
     * Возвращает евклидову норму вектора (длину вектора
     * в n-мерном евклидовом пространстве, n={@link #getSize()}).
     * Это можно подсчитать как корень квадратный от скалярного произведения вектора на себя.
     */
    @Override
    public double getNorm() {
        return Math.pow(this.scalarMult(this), 0.5);
    }
}
