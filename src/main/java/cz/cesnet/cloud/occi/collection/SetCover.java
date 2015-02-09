package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Michal Kimle <kimle.michal@gmail.com>
 * @param <E>
 */
public class SetCover<E extends Identifiable> {

    private final Set<E> set = new HashSet<>();

    /**
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return set.contains(element);
    }

    /**
     *
     * @param elementIdentifier
     * @return
     */
    public boolean contains(String elementIdentifier) {
        for (E element : set) {
            if (element.getIdentifier().equals(elementIdentifier)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param element
     * @return
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }

        return set.add(element);
    }

    /**
     *
     * @param elements
     * @return
     */
    public boolean addAll(Collection<E> elements) {
        if (elements.contains(null)) {
            throw new NullPointerException("Cannot add null element.");
        }

        return set.addAll(elements);
    }

    /**
     *
     * @param elementIdentifier
     * @return
     */
    public E get(String elementIdentifier) {
        if (!contains(elementIdentifier)) {
            return null;
        }

        return find(elementIdentifier);
    }

    /**
     *
     * @param element
     * @return
     */
    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot remove null element.");
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

    /**
     *
     */
    public void clear() {
        set.clear();
    }

    /**
     *
     * @return
     */
    public Set<E> getSet() {
        return getSet(false);
    }

    /**
     *
     * @param modifiable
     * @return
     */
    public Set<E> getSet(boolean modifiable) {
        if (modifiable) {
            return set;
        } else {
            return Collections.unmodifiableSet(set);
        }
    }

    /**
     *
     * @return
     */
    public int size() {
        return set.size();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.set);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SetCover<?> other = (SetCover<?>) obj;
        if (!Objects.equals(this.set, other.set)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "SetCover{" + set + '}';
    }
}
