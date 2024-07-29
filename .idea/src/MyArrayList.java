import jdk.internal.util.ArraysSupport;

import java.util.*;

public class MyArrayList<E> extends AbstractList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private E[] elements;
    private E[] emptyArray = (E[]) new Object[0];

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public MyArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elements = (E[]) new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elements = emptyArray;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public MyArrayList(Collection<? extends E> collection) {
        E[] array = (E[]) collection.toArray();
        if ((size = array.length) != 0) {
            if (collection.getClass() == ArrayList.class) {
                elements = array;
            } else {
                elements = (E[]) Arrays.copyOf(array, size, Object[].class);
            }
        } else {
            elements = emptyArray;
        }
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return elements[index];
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E remove(int index) {
        Objects.checkIndex(index, size);
        final E[] es = elements;

        E oldValue = es[index];
        fastRemove(es, index);

        return oldValue;
    }

    private void fastRemove(E[] es, int index) {
        final int newSize;
        if ((newSize = size - 1) > index) {
            System.arraycopy(es, index + 1, es, index, newSize - index);
        }

        es[size = newSize] = null;
    }

    public boolean remove(Object o) {
        final E[] es = elements;
        final int size = this.size;
        int i = 0;
        found:
        {
            if (o == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            return false;
        }

        fastRemove(es, i);
        return true;
    }

    public void clear() {
        final Object[] es = elements;
        for (int to = size, i = size = 0; i < to; i++) {
            es[i] = null;
        }
    }

    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        final int s;
        Object[] elements;
        if ((s = size) == (elements = this.elements).length)
            elements = grow();
        System.arraycopy(elements, index,
            elements, index + 1,
            s - index);
        elements[index] = element;
        size = s + 1;
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private E[] grow(int minCapacity) {
        int oldCapacity = elements.length;
        if (oldCapacity > 0 || elements != emptyArray) {
            int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity,
                oldCapacity >> 1);
            return elements = Arrays.copyOf(elements, newCapacity);
        } else {
            return elements = (E[]) new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    public boolean addAll(Collection<? extends E> collection) {
        Object[] inputArray = collection.toArray();
        int numNew = inputArray.length;
        if (numNew == 0) {
            return false;
        }

        E[] elements;
        final int s;
        if (numNew > (elements = this.elements).length - (s = size)) {
            elements = grow(s + numNew);
        }

        System.arraycopy(inputArray, 0, elements, s, numNew);
        size = s + numNew;

        return true;
    }

    public void quickSort(int left, int right, Comparator<? super E> comparator) {
        if (left >= right) return;

        E pivot = elements[right];
        int partitionIndex = partition(left, right, pivot, comparator);

        if (left < partitionIndex - 1) {
            quickSort(left, partitionIndex - 1, comparator);
        }

        if (partitionIndex < right) {
            quickSort(partitionIndex, right, comparator);
        }
    }

    private int partition(int left, int right, E pivot, Comparator<? super E> comparator) {
        while (left <= right) {
            while (comparator.compare(elements[left], pivot) < 0) {
                left++;
            }
            while (comparator.compare(elements[right], pivot) > 0) {
                right--;
            }
            if (left <= right) {
                swap(elements, left, right);
                left++;
                right--;
            }
        }

        return left;
    }

    private void swap(E[] elements, int left, int right) {
        E temp = elements[left];
        elements[left] = elements[right];
        elements[right] = temp;
    }
}