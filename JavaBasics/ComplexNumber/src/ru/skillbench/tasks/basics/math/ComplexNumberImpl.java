package ru.skillbench.tasks.basics.math;

import java.util.Arrays;


public class ComplexNumberImpl implements ComplexNumber {
    private double re = 0;
    private double im = 0;
    /**
     * @return real part of this complex number
     */
    @Override
    public double getRe() {
        return re;
    }

    /**
     * @return imaginary part of this complex number
     */
    @Override
    public double getIm() {
        return im;
    }

    /**
     * @return true if this complex number has real part only (otherwise false)
     */
    @Override
    public boolean isReal() {
        return im == 0;
    }

    /**
     * Sets both real and imaginary part of this number.
     *
     * @param re
     * @param im
     */
    @Override
    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Parses the given string value and sets the real and imaginary parts of this number accordingly.<br/>
     * The string format is "re+imi", where re and im are numbers (floating point or integer) and 'i' is a special symbol
     * denoting imaginary part (if present, it's always the last character in the string).<br/>
     * Both '+' and '-' symbols can be the first characters of re and im; but '*' symbol is NOT allowed.<br/>
     * Correct examples: "-5+2i", "1+i", "+4-i", "i", "-3i", "3". Incorrect examples: "1+2*i", "2+2", "j".<br/>
     * Note: explicit exception generation is an OPTIONAL requirement for this task,
     * but NumberFormatException can be thrown by {@link Double#parseDouble(String)}).<br/>
     * Note: it is not reasonable to use regex while implementing this method: the parsing logic is too complicated.
     *
     * @param value
     * @throws NumberFormatException if the given string value is incorrect
     */
    @Override
    public void set(String value) throws NumberFormatException {
        if (value.indexOf('i') >= 0) {
            re = 0;
            if (value.indexOf('i') == 0) {
                im = 1;
            } else {
                int reEnd = Math.max(value.lastIndexOf('+'), value.lastIndexOf('-'));
                int reStart = Character.isDigit(value.charAt(0)) ? 0 : 1;
                if (reEnd - reStart > 0) {
                    re = Double.parseDouble(value.substring(reStart, reEnd));
                    int reSign = value.charAt(0) == '-' ? -1 : 1;
                    re *= reSign;
                }
                int imStart = reEnd + 1;
                int imEnd = value.indexOf('i');
                int imSign = value.charAt(reEnd) == '-' ? -1 : 1;
                im = Double.parseDouble(value.substring(imStart, imEnd));
                im *= imSign;
            }
        } else {
            int reStart = Character.isDigit(value.charAt(0)) ? 0 : 1;
            int reEnd = value.length();
            int reSign = value.charAt(0) == '-' ? -1 : 1;
            re = Double.parseDouble(value.substring(reStart, reEnd));
            re *= reSign;
            im = 0;
        }
    }

    /**
     * Creates and returns a copy of this object: <code>x.copy().equals(x)</code> but <code>x.copy()!=x</code>.
     *
     * @return the copy of this number
     * @see #clone()
     */
    @Override
    public ComplexNumber copy() {
        ComplexNumber copy = new ComplexNumberImpl();
        copy.set(re, im);
        return copy;
    }

    /**
     * Creates and returns a copy of this object: the same as {@link #copy()}.<br/>
     * Note: when implemented in your class, this method overrides the {@link Object#clone()} method but changes
     * the visibility and the return type of the Object's method: the visibility modifier is changed
     * from protected to public, and the return type is narrowed from Object to ComplexNumber (see also
     * <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#d5e11368">covariant types example from JLS</a>).
     *
     * @return the copy of this number
     * @see Object#clone()
     */
    @Override
    public ComplexNumber clone() throws CloneNotSupportedException {
        ComplexNumber copy = new ComplexNumberImpl();
        copy.set(re, im);
        return copy;
    }

    /**
     * Returns a string representation of this number, which must be compatible with {@link #set(String)}:
     * for any ComplexNumber x, the code <code>x.set(x.toString());</code> must not change x.<br/>
     * For example: 12.5-1.0i or 0.0 or 0.3333333333333333i<br/>
     * If the imaginary part of the number is 0, only "re" must be returned (where re is the real part).<br/>
     * If the real part of the number is 0 and the imaginary part is not 0,
     *  "imi" must be returned (where im is the imaginary part).<br/>
     * Both re and im must be converted to string "as is" - without truncation of last digits,
     * i.e. the number of characters in their string representation is not limited
     *   (it is determined by {@link Double#toString(double)}).
     * @see Object#toString()
     */
    public String toString() {
        if (im == 0) {
            return String.valueOf(re);
        } else {
            if (re == 0) {
                return String.valueOf(im) + "i";
            } else {
                if (im > 0) {
                    return String.valueOf(re)+"+"+String.valueOf(im)+"i";
                } else {
                    return String.valueOf(re)+String.valueOf(im)+"i";
                }
            }
        }
    }
    /**
     * Checks whether some other object is "equal to" this number.
     * @param other Any implementation of {@link ComplexNumber} interface (may not )
     * @see Object#equals(Object)
     */
    public boolean equals(Object other) {
        return (this == other);
    }

    /**
     * Compares this number with the other number by the absolute values of the numbers:
     * x < y if and only if |x| < |y| where |x| denotes absolute value (modulus) of x.<br/>
     * Can also compare the square of the absolute value which is defined as the sum
     * of the real part squared and the imaginary part squared: |re+imi|^2 = re^2 + im^2.
     *
     * @param other the object to be compared with this object.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the given object.
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(ComplexNumber other) {
        double diff = Math.sqrt(re*re + im*im) - Math.sqrt(other.getRe()*other.getRe() + other.getIm()*other.getIm());
        if (diff < 0) {
            return -1;
        } else if (diff == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * Sorts the given array in ascending order according to the comparison rule defined in
     * {@link #compareTo(ComplexNumber)}.<br/>
     * It's strongly recommended to use {@link Arrays} utility class here
     * (and do not transform the given array to a double[] array).<br/>
     * Note: this method could be static: it does not use this instance of the ComplexNumber.
     * Nevertheless, it is not static because static methods can't be overridden.
     *
     * @param array an array to sort
     */
    @Override
    public void sort(ComplexNumber[] array) {
        Arrays.sort(array);
    }

    /**
     * Changes the sign of this number. Both real and imaginary parts change their sign here.
     *
     * @return this number (the result of negation)
     */
    @Override
    public ComplexNumber negate() {
        re *= -1;
        im *= -1;
        return this;
    }

    /**
     * Adds the given complex number arg2 to this number. Both real and imaginary parts are added.
     *
     * @param arg2 the second operand of the operation
     * @return this number (the sum)
     */
    @Override
    public ComplexNumber add(ComplexNumber arg2) {
        re += arg2.getRe();
        im += arg2.getIm();
        return this;
    }

    /**
     * Multiplies this number by the given complex number arg2. If this number is a+bi and arg2 is c+di then
     * the result of their multiplication is (a*c-b*d)+(b*c+a*d)i<br/>
     * The method should work correctly even if arg2==this.
     *
     * @param arg2 the second operand of the operation
     * @return this number (the result of multiplication)
     */
    @Override
    public ComplexNumber multiply(ComplexNumber arg2) {
        double newRe = re*arg2.getRe() - im*arg2.getIm();
        double newIm = im*arg2.getRe() + re * arg2.getIm();
        this.set(newRe, newIm);
        return this;
    }
}
