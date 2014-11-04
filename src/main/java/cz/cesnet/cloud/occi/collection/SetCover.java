package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.type.Identifiable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class SetCover<E extends Identifiable> {

    private final Set<E> set = new HashSet<>();

    public boolean contains(E element) {
        return set.contains(element);
    }

    public boolean contains(String elementIdentifier) {
        for (E element : set) {
            if (element.getIdentifier().equals(elementIdentifier)) {
                return true;
            }
        }

        return false;
    }

    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null " + getElementClassName() + " .");
        }

        return set.add(element);
    }

    public boolean addAll(Collection<E> elements) {
        if (elements.contains(null)) {
            throw new NullPointerException("Cannot add null " + getElementClassName() + " .");
        }

        return set.addAll(elements);
    }

    public E get(String elementIdentifier) {
        if (!contains(elementIdentifier)) {
            return null;
        }

        return find(elementIdentifier);
    }

    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot remove null " + getElementClassName() + " .");
        }

        return set.remove(element);
    }

    private E find(String elementIdentifier) {
        for (E element : set) {
            if (element.getIdentifier().equals(elementIdentifier)) {
                return element;
            }
        }

        return null;
    }

    public void clear() {
        set.clear();
    }

    public Set<E> getSet() {
        return getSet(false);
    }

    public Set<E> getSet(boolean modifiable) {
        if (modifiable) {
            return set;
        } else {
            return Collections.unmodifiableSet(set);
        }
    }

    private String getElementClassName() {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
    }

    @Override
    public String toString() {
        return "SetCover{" + set + '}';
    }
}
