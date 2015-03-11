package cz.cesnet.cloud.occi.collection;

import cz.cesnet.cloud.occi.type.Identifiable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * Collection class serving as a set for all classes identifiable.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 * @param <E>
 */
public class SetCover<E extends Identifiable> {

    private final Set<E> set = new HashSet<>();

    /**
     * Checks whether set contains the element.
     *
     * @param element looked up in the set
     * @return true if set contains the element, false otherwise
     */
    public boolean contains(E element) {
        return set.contains(element);
    }

    /**
     * Checks whether set contains an element specified by the identifier.
     *
     * @param elementIdentifier identifier of the element looked up in the set
     * @return true if set contains the element, false otherwise
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
     * Adds element to the set.
     *
     * @param element element to be added. Cannot be null.
     * @return true if the addition was successful, false otherwise
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }

        return set.add(element);
    }

    /**
     * Adds all elements from the collection to the set.
     *
     * @param elements collection of elements to be added. Cannot contain any
     * null elements.
     * @return true if the addition was successful, false otherwise
     */
    public boolean addAll(Collection<E> elements) {
        if (elements.contains(null)) {
            throw new NullPointerException("Cannot add null element.");
        }

        return set.addAll(elements);
    }

    /**
     * Retrieves element rom the set.
     *
     * @param elementIdentifier identifier of the element to be retrieved
     * @return element specified by its identifier if in set, null otherwise
     */
    public E get(String elementIdentifier) {
        if (!contains(elementIdentifier)) {
            return null;
        }

        return find(elementIdentifier);
    }

    /**
     * Removes element from the set
     *
     * @param element element to be remoed from the set. Cannot be null.
     * @return true if the deletion was successful, false otherwise
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
     * Removes all the elements from the set.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns unmodifiable set of elements.
     *
     * @return unmodifiable set of elements
     */
    public Set<E> getSet() {
        return getSet(false);
    }

    /**
     * Returns set of elements.
     *
     * @param modifiable specifies if the set will be modifiable or not
     * @return set of elements
     */
    public Set<E> getSet(boolean modifiable) {
        if (modifiable) {
            return set;
        } else {
            return Collections.unmodifiableSet(set);
        }
    }

    /**
     * Returns number of elements in the set.
     *
     * @return number of elements in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * @see Object#hashCode()
     * @return set's hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.set);
        return hash;
    }

    /**
     * @see Object#equals(java.lang.Object)
     * @param obj object to compare set with
     * @return true if two sets are equal, false otherwise
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
     * Resturns string representation of the set
     *
     * @see Object#toString()
     * @return string representation of the set
     */
    @Override
    public String toString() {
        return "SetCover{" + set + '}';
    }
}
