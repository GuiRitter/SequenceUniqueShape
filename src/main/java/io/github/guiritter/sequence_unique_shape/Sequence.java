package io.github.guiritter.sequence_unique_shape;

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
public final class Sequence {

    /**
     * This isn't even my final form!
     */
    public final LinkedList<LinkedList<Integer>> formList = new LinkedList<>();

    public final LinkedList<Integer> original = new LinkedList<>();

    /**
     * Returns a new sequence where the first segment starts at zero.
     * Explores the fact that the sequence is cyclic. Thus,
     * if there are <code>n</code> numbers, there are <code>n</code> segments
     * going from number <code>n</code> to <code>n + 1</code>.
     * With this operation, every segment is replaced by the following one
     * until the first one starts at zero.
     * @param list a sequence
     * @return the sequence cycled such that the first element is zero
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
        if (!(obj instanceof Sequence)) {
            return false;
        }
        if (((Sequence) obj).original.size() != original.size()) {
            return false;
        }
        boolean equal;
        int i;
        int formI;
        int size = original.size();
        int formAmount = formList.size();
        for (formI = 0; formI < formAmount; formI++) {
            equal = true;
            for (i = 1; i < size; i++) {
                if (!formList.getFirst().get(i).equals(((Sequence) obj).formList.get(formI).get(i))) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return true;
            }
        }
        return false;
    }

    /**
     * Almost mandatory override.
     * @return unique code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.formList);
        hash = 29 * hash + Objects.hashCode(this.original);
        return hash;
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
        return original + ", m: " + formList;
    }

    /**
     * Constructs this sequence with all of it's forms.
     * May generate duplicate forms, but it doesn't make a difference
     * for the sake of comparison.
     * @param array numbers
     */
    public Sequence(int array[]) {
        for (int l : array) {
            original.add(l);
        }
        if (!original.contains(0)) {
            throw new RuntimeException("Sequence must contain a zero.");
        }
        formList.add(getOffsettedToZero(original));
        LinkedList<Integer> temporary = new LinkedList<>(original);
        Collections.reverse(temporary);
        formList.add(getOffsettedToZero(temporary));
        int i;
        int rotationI;
        int size = original.size();
        LinkedList<Integer> mirror = new LinkedList<>(original);
        for (i = 0; i < size; i++) {
            mirror.set(i, (size - mirror.get(i)) % size);
        }
        formList.add(getOffsettedToZero(mirror));
        temporary = new LinkedList<>(mirror);
        Collections.reverse(temporary);
        formList.add(getOffsettedToZero(temporary));
        LinkedList<LinkedList<Integer>> originalOrMirrorList = new LinkedList<>();
        originalOrMirrorList.add(original);
        originalOrMirrorList.add(mirror);
        for (LinkedList<Integer> originalOrMirror : originalOrMirrorList) {
            for (rotationI = 1; rotationI < size; rotationI++) {
                temporary = new LinkedList<>(originalOrMirror);
                for (i = 0; i < size; i++) {
                    temporary.set(i, (temporary.get(i) + rotationI) % size);
                }
                formList.add(getOffsettedToZero(temporary));
                temporary = new LinkedList<>(temporary);
                Collections.reverse(temporary);
                formList.add(getOffsettedToZero(temporary));
            }
        }
    }

    /**
     * Constructs this sequence with all of it's forms.
     * May generate duplicate forms, but it doesn't make a difference
     * for the sake of comparison.
     * @param array numbers
     */
    public Sequence(long array[]) {
        this(longArrayToIntArray(array));
    }

    public static void main(String args[]) {
//        new Sequence(new long[]{0, 1, 2, 3, 4/*, 5, 6*/});
        Sequence sequence = new Sequence(new int[]{4, 2, 3, 1, 0});
        System.out.println("sequence 3");
        sequence.formList.forEach((intList) -> {
            System.out.println(intList);
        });
    }
}
