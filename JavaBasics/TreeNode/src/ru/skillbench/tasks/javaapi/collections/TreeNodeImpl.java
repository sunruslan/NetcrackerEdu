package ru.skillbench.tasks.javaapi.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeNodeImpl implements TreeNode {
    TreeNode parent;
    Set<TreeNode> children;
    boolean expanded = false;
    Object data;
    /**
     * Возвращает родительский объект <code>TreeNode</code>.
     */
    @Override
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Задает родительский объект <code>TreeNode</code>.<br/>
     * Обычно вызывается в методах {@link #addChild(TreeNode)} и {@link #removeChild(TreeNode)}
     * родительского объекта <code>TreeNode</code>.
     *
     * @param parent
     */
    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * Возвращает корень дерева, содержащего данный объект <code>TreeNode</code>.
     *
     * @return корневой узел. Или <code>null</code>, если у данного узла нет родителя.
     */
    @Override
    public TreeNode getRoot() {
        if (parent == null) {
            return null;
        }
        TreeNode temp = parent;
        while (temp.getParent() != null) {
            temp = temp.getParent();
        }
        return temp;
    }

    /**
     * Возвращает <code>false</code>, если <code>TreeNode</code> имеет ненулевое число дочерних узлов.
     *
     * @return <code>true</code>, если данный узел является листовым (т.е. не имеет дочерних узлов)
     */
    @Override
    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    /**
     * Возвращает число дочерних узлов данного <code>TreeNode</code>.
     */
    @Override
    public int getChildCount() {
        return children == null ? 0 : children.size();
    }

    /**
     * Возвращает дочерние узлы данного <code>TreeNode</code> в виде {@link Iterator}.
     */
    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return children.iterator();
    }

    /**
     * Добавляет указанный в аргументе <code>TreeNode</code> в качестве дочернего узла
     * и задает ему в качестве родителя <code>this</code>.
     *
     * @param child
     */
    @Override
    public void addChild(TreeNode child) {
        child.setParent(this);
        if (children == null) {
            children = new HashSet<>();
        }
        children.add(child);
    }

    /**
     * Удаляет указанный в аргументе <code>TreeNode</code> из коллекции дочерних узлов и (если успешно)
     * задает ему в качестве родителя <code>null</code> (чтобы обеспечить согласованное состояние дерева).<br/>
     * Узел ищется в коллекции и удаляется из нее по правилам любой коллекции: <br/>
     * а) поиск/удаление успешно, если в ней нашелся объект, равный указанному в смысле метода {@link Object#equals(Object)};<br/>
     * б) если в коллекции несколько таких объектов (если это не {@link Set}), то удаляется только один.
     *
     * @param child
     * @return <code>true</code> - если удаление успешно, <code>false</code> - если такой дочерний узел не найден.
     */
    @Override
    public boolean removeChild(TreeNode child) {
        child.setParent(null);
        return children.remove(child);
    }

    /**
     * Возвращает признак "развернутости / свернутости" данного <code>TreeNode</code>
     * (в UI-компонентах типа "дерево" от этого зависит иконка и показ дочерних узлов).<br/>
     * Узел "свернут" по умолчанию - то есть, если {@link #setExpanded(boolean)} не вызывался.
     *
     * @return <code>true</code> - если узел развернут, <code>false</code></code></code> - если узел "свернут" (collapsed)
     */
    @Override
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Задает признак "развернутости" (expanded) данному <code>TreeNode</code> и рекурсивно всем его дочерним узлам
     *
     * @param expanded <code>true</code> - разворачивает эту ветвь дерева, <code>false</code> - сворачивает ее.
     */
    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        for (TreeNode c: children) {
            c.setExpanded(expanded);
        }
    }

    /**
     * @return "пользовательский" (заданный извне) объект данных, хранящийся в этом <code>TreeNode</code>.
     * Или <code>null</code>, если {@link #setData(Object)} не вызывался.
     */
    @Override
    public Object getData() {
        return data;
    }

    /**
     * Задает "пользовательский" объект данных для хранения в этом <code>TreeNode</code>.
     *
     * @param data
     */
    @Override
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Возвращает строковое представление пути от корня дерева до данного <code>TreeNode</code>.<br/>
     * Элементы пути разделяются символами "->".<br/>
     * Каждый элемент пути - это либо getData().toString(), либо строка "empty", если getData()==null.<br/>
     * Например: "rootNode0->node1->node13->empty" ("rootNode0" - это в данном примере результат вызова метода
     * getRoot().getData().toString() ).
     */
    @Override
    public String getTreePath() {
        String str = data == null ? "empty" : data.toString();
        if (parent == null) {
            return str;
        } else {
            return parent.getTreePath() + "->" + str;
        }
    }

    /**
     * Среди цепочки родительских узлов данного <code>TreeNode</code> метод находит (первый) узел с заданным объектом <code>data</code>.<br/>
     * По соглашению, "цепочка родительских узлов" содержит сам данный узел (то есть, возможно следующее: obj.findParent(*) == obj).<br/>
     * Объекты <code>data</code> должны сравниваться с помощью {@link Object#equals(Object)}, а если <code>data == null</code>,
     * тогда должен возвращаться родительский узел, у которого <code>getData() == null</code>).
     *
     * @param data Объект поиска; может быть равен <code>null</code>
     * @return Найденный узел. Или <code>null</code> если не было найдено узла, содержащего такой <code>data</code>.
     */
    @Override
    public TreeNode findParent(Object data) {
        if (this.data.equals(data)) {
            return this;
        }
        if (parent == null) {
            return null;
        }
        return parent.findParent(data);
    }

    /**
     * Среди дочерних узлов данного <code>TreeNode</code> метод находит (первый) узел с заданным объектом <code>data</code>.<br/>
     * Причем ищет такой узел рекурсивно: если некоторый дочерний узел не имеет заданного объекта <code>data</code>,
     * он ищется среди детей этого дочернего узла, и так далее.<br/>
     * Объекты <code>data</code> должны сравниваться с помощью {@link Object#equals(Object)}, а если <code>data == null</code>,
     * тогда должен возвращаться дочерний узел, у которого <code>getData() == null</code>).
     *
     * @param data Объект поиска; может быть равен <code>null</code>
     * @return Найденный узел. Или <code>null</code> если не было найдено узла, содержащего такой <code>data</code>.
     */
    @Override
    public TreeNode findChild(Object data) {
        TreeNode res = null;
        if (children != null && children.size() > 0) {
            for (TreeNode c: children) {
                if (c != null) {
                    if (c.getData() != null && c.getData().equals(data) || c.getData() == null && data == null) {
                        res = c;
                        break;
                    } else {
                        res = c.findChild(data);
                    }
                }
            }
        }
        return res;
    }
}
