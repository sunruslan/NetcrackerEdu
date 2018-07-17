package ru.skillbench.tasks.javaapi.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StringFilterImpl implements StringFilter {
    interface Filter {
        boolean check(String str);
    }
    private static Set<String> bag = new HashSet<>();
    /**
     * Добавляет строку s в набор, приводя ее к нижнему регистру.
     * Если строка s уже есть в наборе, ничего не делает.
     *
     * @param s может быть null
     */
    @Override
    public void add(String s) {
        bag.add(s == null ? s : s.toLowerCase());
    }

    /**
     * Удаляет строку s из набора (предварительно приведя ее к нижнему регистру).
     *
     * @param s может быть null
     * @return true если строка была удалена, false если строка отсутствовала в наборе.
     */
    @Override
    public boolean remove(String s) {
        return bag.remove(s == null ? s : s.toLowerCase());
    }

    /**
     * Очищает набор - удаляет из него все строки
     */
    @Override
    public void removeAll() {
        bag.clear();
    }

    /**
     * Возвращает набор (коллекцию), в котором хранятся строки.
     * В наборе не может быть двух одинаковых строк, однако может быть null.
     */
    @Override
    public Collection<String> getCollection() {
        return bag;
    }

    private Iterator<String> getString(Filter filter) {
        Set<String> res = new HashSet<>();
        for (Iterator<String> it = bag.iterator(); it.hasNext();) {
            String str = it.next();
            if (filter.check(str)) {
                res.add(str);
            }
        }
        return res.iterator();
    }

    /**
     * Ищет и возвращает все строки, содержащие указанную последовательность символов.<br/>
     * Если <code>chars</code> - пустая строка или <code>null</code>,
     * то результат содержит все строки данного набора.<br/>
     *
     * @param chars символы, входящие в искомые строки
     *              (все символы, являющиеся буквами, - в нижнем регистре)
     * @return строки, содержащие указанную последовательность символов
     */
    @Override
    public Iterator<String> getStringsContaining(String chars) {
        if (chars == null || chars.equals("")) {
            return bag.iterator();
        }
        final String contains = chars.toLowerCase();
        Filter filter = new Filter() {
            @Override
            public boolean check(String str) {
                return str != null && str.contains(contains);
            }
        };
        return getString(filter);
    }

    /**
     * Ищет и возвращает строки, начинающиеся с указанной последовательности символов,
     * (без учета регистра). <br/>
     * Если <code>begin</code> - пустая строка или <code>null</code>,
     * то результат содержит все строки данного набора.<br/>
     *
     * @param begin первые символы искомых строк
     *              (для сравнения со строками набора символы нужно привести к нижнему регистру)
     * @return строки, начинающиеся с указанной последовательности символов
     */
    @Override
    public Iterator<String> getStringsStartingWith(String begin) {
        if (begin == null || begin.equals("")) {
            return bag.iterator();
        }
        final String prefix = begin.toLowerCase();
        Filter filter = new Filter() {
            @Override
            public boolean check(String str) {
                return str != null && str.startsWith(prefix);
            }
        };
        return getString(filter);
    }

    /**
     * Ищет и возвращает все строки, представляющие собой число в заданном формате.<br/>
     * Формат может содержать символ # (место для одной цифры от 0 до 9) и любые символы.
     * Примеры форматов: "(###)###-####" (телефон), "# ###" (целое число от 1000 до 9999),
     * "-#.##" (отрицательное число, большее -10, с ровно двумя знаками после десятичной точки).<br/>
     * Упрощающее ограничение: в строке, удовлетворяющей формату, должно быть ровно столько символов,
     * сколько в формате (в отличие от стандартного понимания числового формата,
     * где некоторые цифры на месте # не являются обязательными).<br/>
     * Примечание: в данной постановке задачи НЕ предполагается использование регулярных выражений
     * или какого-либо высокоуровневого API (эти темы изучаются позже).<br/>
     * Если <code>format</code> - пустая строка или <code>null</code>,
     * то результат содержит все строки данного набора.<br/>
     *
     * @param format формат числа
     * @return строки, удовлетворяющие заданному числовому формату
     */
    @Override
    public Iterator<String> getStringsByNumberFormat(String format) {
        if (format == null || format.equals("")) {
            return bag.iterator();
        }
        final String numFormat = format.toLowerCase();
        Filter filter = new Filter() {
            @Override
            public boolean check(String str) {
                if (str != null) {
                    if (str.length() != numFormat.length()) {
                        return false;
                    }
                    for (int i = 0; i < numFormat.length(); ++i) {
                        if (numFormat.charAt(i) == '#' && Character.isDigit(str.charAt(i))
                                || numFormat.charAt(i) == str.charAt(i)) {
                            continue;
                        }
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }
        };
        return getString(filter);
    }

    /**
     * Ищет и возвращает строки, удовлетворяющие заданному шаблону поиска, содержащему символы *
     * в качестве wildcards (на месте * в строке может быть ноль или больше любых символов).<br/>
     * <a href="http://en.wikipedia.org/wiki/Wildcard_character#File_and_directory_patterns">Про * wildcard</a>.<br/>
     * Примеры шаблонов, которым удовлетворяет строка "distribute": "distr*", "*str*", "di*bute*".<br/>
     * Упрощение: достаточно поддерживать всего два символа * в шаблоне (их может быть и меньше двух).<br/>
     * Примечание: в данной постановке задачи НЕ предполагается использование регулярных выражений
     * и какого-либо высокоуровневого API (эти темы изучаются позже), цель - применить методы String.<br/>
     * Если <code>pattern</code> - пустая строка или <code>null</code>,
     * то результат содержит все строки данного набора.<br/>
     *
     * @param pattern шаблон поиска (все буквы в нем - в нижнем регистре)
     * @return строки, удовлетворяющие заданному шаблону поиска
     */
    @Override
    public Iterator<String> getStringsByPattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            return bag.iterator();
        }
        final String temp  = pattern.toLowerCase();
        Filter filter = new Filter() {
            @Override
            public boolean check(String str) {
                if (str != null) {
                    int first = temp.indexOf('*');
                    int last = temp.lastIndexOf('*');
                    if (first == -1) {
                        return str.equals(temp);
                    } else if (first == last) {
                        return str.startsWith(temp.substring(0, first))
                                && str.endsWith(temp.substring(first+1, temp.length()));
                    } else {
                        String part1 = temp.substring(0, first);
                        String part2 = temp.substring(first+1, last);
                        String part3 = temp.substring(last+1, temp.length());
                        if (last-1 == first) {
                            return str.startsWith(part1)
                                    && str.endsWith(part3);
                        }
                        if (part1.equals("") && part3.equals("")) {
                            return str.contains(part2);
                        }
                        if (part1.equals("") && !part3.equals("")) {
                            return str.endsWith(part3)
                                    && str.substring(0, str.length()- part1.length()).contains(part2);
                        }
                        if (!part1.equals("") && part3.equals("")) {
                            return str.startsWith(part1)
                                    && str.substring(part1.length(), str.length()).contains(part2);
                        }
                        return str.startsWith(part1) && str.endsWith(part3)
                                && str.substring(part1.length(), str.length()-part3.length()).contains(part2);
                    }
                } else {
                    return false;
                }
            }
        };
        return getString(filter);
    }
}
