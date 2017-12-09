package io.github.guiritter.sequence_unique_shape;

import static java.lang.Long.MAX_VALUE;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents an ordered cyclic sequence of <code>n</code> numbers,
 * ranging from <code>0</code> to <code>n - 1</code>.
 * This sequence can be represented in several different forms.
 * Two sequences are considered equal if any of one of the sequence's forms
 * is equal number by number to any of the other sequence's forms.
 * All of a sequence's forms are combinations of these transformations:
 * <ul><li>Mirroring: replace the value each number
 * by <code>(size - value) % size</code>.
 * <li>Rotation: replace the value of each number by the next value.
 * <li>Reversal: replace the position of the first number
 * by the ultimate position, the second by the penultimate one, etc.
 * <li>Offset: replace the position of each number by the next position.</ul>
 * @author GuiR
 */
public final class Sequence1 {

    public final LinkedList<Integer> canonical = new LinkedList<>();

    public final int size;

    public final long value;

    /**
     * Returns a new sequence where the first segment starts at zero.
     * Explores the fact that the sequence is cyclic. Thus,
     * if there are <code>n</code> numbers, there are <code>n</code> segments
     * going from number <code>n</code> to <code>n + 1</code>.
     * With this operation, every segment is replaced by the following one
     * until the first one starts at zero.
     * @param list
     * @return
     */
    public static LinkedList<Integer> getOffsettedToZero(LinkedList<Integer> list) {
        LinkedList<Integer> returnList = new LinkedList<>(list);
        if (!returnList.contains(0)) {
            return null;
        }
        while (returnList.getFirst() != 0) {
            returnList.addLast(returnList.removeFirst());
        }
        return returnList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Sequence1)) {
            return false;
        }
        if (size != ((Sequence1) obj).size) {
            return false;
        }
        for (int i = 1; i < size; i++) {
            if (!canonical.get(i).equals(((Sequence1) obj).canonical.get(i))) {
                return false;
            }
        }
        return true;
//        return value == ((Sequence1) obj).value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.canonical);
        hash = 31 * hash + (int) (this.value ^ (this.value >>> 32));
        return hash;
    }

    private boolean isFirstSmallerThanSecond(LinkedList<Integer> first, LinkedList<Integer> second) {
        for (int i = 0; i < size; i++) {
            if (first.get(i) < second.get(i)) {
                return true;
            } else if (first.get(i) > second.get(i)) {
                return false;
            }
        }
        return false;
    }

    private static int[] longArrayToIntArray(long array[]) {
        int intArray[] = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = (int) array[i];
        }
        return intArray;
    }

    @Override
    public String toString() {
        return canonical.toString();
    }

    /**
     * Constructs this sequence with all of it's forms.
     * May generate duplicate forms, but it doesn't make a difference
     * for the sake of comparison.
     * @param array
     */
    public Sequence1(int array[]) {
        LinkedList<Integer> original = new LinkedList<>();
        for (int l : array) {
            original.add(l);
        }
        if (!original.contains(0)) {
            throw new RuntimeException("Sequence must contain a zero.");
        }
        int i;
        int rotationI;
        size = original.size();
        long valueForm;
        long valueMinimum = MAX_VALUE;
        Value sequenceValue = new Value(size);
        LinkedList<Integer> mirror = new LinkedList<>(original);
        boolean reverseOption[] = new boolean[]{false, true};
        for (i = 0; i < size; i++) {
            mirror.set(i, (size - mirror.get(i)) % size);
            canonical.add(size - 1);
        }
        LinkedList<Integer> temporary;
        LinkedList<LinkedList<Integer>> originalOrMirrorList = new LinkedList<>();
        originalOrMirrorList.add(original);
        originalOrMirrorList.add(mirror);
        for (LinkedList<Integer> originalOrMirror : originalOrMirrorList) {
            for (rotationI = 0; rotationI < size; rotationI++) {
                temporary = new LinkedList<>(originalOrMirror);
                for (i = 0; i < size; i++) {
                    temporary.set(i, (temporary.get(i) + rotationI) % size);
                }
                for (boolean isReversed : reverseOption) {
                    if (isReversed) {
                        Collections.reverse(temporary);
                    }
                    temporary = getOffsettedToZero(temporary);
                    /*
                    // removed because, due to using long, maximum array size is 15
                    // more than that results in overflow
                    // could use BigInteger, but at this point it's simply better to compare index by index
                    valueForm = sequenceValue.of(temporary);
                    if (valueMinimum > valueForm) {
                        valueMinimum = valueForm;
                        canonical.clear();
                        canonical.addAll(temporary);
                    }
                    /**/
                    if (isFirstSmallerThanSecond(temporary, canonical)) {
                        canonical.clear();
                        canonical.addAll(temporary);
                    }
                }
            }
        }
        value = valueMinimum;
//        System.out.println(value + "\t" + canonical);
    }

    /**
     * Constructs this sequence with all of it's forms.
     * May generate duplicate forms, but it doesn't make a difference
     * for the sake of comparison.
     * @param array
     */
    public Sequence1(long array[]) {
        this(longArrayToIntArray(array));
    }

    public static void main(String args[]) {
//        new Sequence(new long[]{0, 1, 2, 3, 4/*, 5, 6*/});
        Sequence1 sequence = new Sequence1(new int[]{4, 2, 3, 1, 0});
        System.out.println("sequence 3");
        System.out.println(sequence.canonical);
    }
}
