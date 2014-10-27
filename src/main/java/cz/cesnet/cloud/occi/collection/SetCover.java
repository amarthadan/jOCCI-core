package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.type.Identifiable;
import cz.cesnet.cloud.occi.exception.NonexistingElementException;
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
        return set.add(element);
    }

    public boolean addAll(Collection<E> elements) {
        return set.addAll(elements);
    }

    public E get(String elementIdentifier) throws NonexistingElementException {
        if (!contains(elementIdentifier)) {
            Class elementClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            throw new NonexistingElementException(elementClass.getName() + " with identifier " + elementIdentifier + " was not found.");
        }

        return find(elementIdentifier);
    }

    public boolean remove(E element) {
        return set.remove(element);
    }

    private E find(String elementIdentifier) throws NonexistingElementException {
        for (E element : set) {
            if (element.getIdentifier().equals(elementIdentifier)) {
                return element;
            }
        }

        Class elementClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        throw new NonexistingElementException(elementClass.getName() + " with identifier " + elementIdentifier + " was not found.");
    }

    public void clear() {
        set.clear();
    }

    public Set<E> getSet() {
        return Collections.unmodifiableSet(set);
    }
}
