package ru.skillbench.tasks.basics.entity;

public class LocationImpl implements Location {
    private String name;
    private Type type;
    private Location parent;
    /**
     * @return Название места
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name Название места
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Тип места
     * @see Type
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * @param type Тип места
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @param parent "Родительское" место - то, чьей частью является данное место.
     *               Например, если данное место - это улица, то родительским местом может быть район или город.
     */
    @Override
    public void setParent(Location parent) {
        this.parent = parent;
    }

    /**
     * @return Название "родительского" места, частью которого является данное место.
     * Если родительское место не задано (равно null), метод возвращает строку "--".
     */
    @Override
    public String getParentName() {
        if (parent == null) {
            return "--";
        } else {
            return parent.getName();
        }
    }

    /**
     * Возвращает место верхнего уровня, т.е. вершину иерархии, в которую входит данное место.<br/>
     *
     * @return Место, отличное от <code>null</code>. Если выше в иерархии больше нет мест, возвращает данное место.
     */
    @Override
    public Location getTopLocation() {
        if (parent == null) {
            return this;
        } else {
            return parent.getTopLocation();
        }
    }

    /**
     * Проверяет иерархию родительских мест на соответствие их типов: например, город не должен быть частью дома или города.<br/>
     * Пропуски в иерархии допустимы: например, улица может находиться в городе (не в районе), а город - в стране (не в области).
     *
     * @return true если каждое следующее (более высокое) место в иерархии имеет {@link #getType()} меньше, чем у предыдущего места.<br/>
     * Обратите внимание: {@link Type}, как любой enum, реализует интерфейс {@link Comparable},
     * и порядок определяется порядком записи значений в коде enum.
     */
    @Override
    public boolean isCorrect() {
        return parent == null || type.compareTo(parent.getType()) > 0 && parent.isCorrect();
    }

    /**
     * Адрес - это список мест, начинающийся с данного места и включающий все родительские места.
     * Элементы списка отделяются друг от друга запятой и пробелом (", ").<br/>
     * Каждый элемент списка - это:<ol>
     * <li>просто название места, если уже содержит префикс или суффикс типа, - то есть,
     * если оно заканчиваются на точку ('.') или содержит точку среди символов от начала названия до первого пробела (' ');</li>
     * <li>в противном случае - дефолтный префикс типа ({@link Type#getNameForAddress()}) и название места.</li>
     * </ol>
     * Пример названий с префиксом/суффиксом, удовлетворяющих условию пункта 1: "Московская обл.", "туп. Гранитный", "оф. 321".<br/>
     * Пример названий без префикса/суффикса, НЕ удовлетворяющих этому условию: "Москва", "25 к. 2"<br/>
     *
     * @return адрес, составленный из имен (и типов) всех мест, начиная с данного места до {@link #getTopLocation()}
     */
    @Override
    public String getAddress() {
        int index = name.indexOf('.');
        String cur;
        if (index >= 0 && (index == name.length() - 1) || index + 1 == name.indexOf(' ')) {
            cur = name;
        } else {
            cur = type.getNameForAddress() + name;
        }
        if (parent == null) {
            return cur;
        } else {
            return cur + ", " + parent.getAddress();
        }
    }

    /**
     * Примечание: существует одноименный метод в классе {@link Object}, поэтому, в отличие от остальных методов {@link Location},
     *  IDE автоматически НЕ создаст шаблон реализации этого метода в классе LocationImpl - следует создать его вручную.
     * @return Строковое представление места в формате:<br/>
     *  name (type)<br/>
     *  где name - это название места, а type - это его тип: см. {@link Type#toString()}. Например:<br/>
     *  34/5 (Building)
     */
    @Override
    public String toString(){
        return name + " (" + type.toString() + ")";
    }
}
