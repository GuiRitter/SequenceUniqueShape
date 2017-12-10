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
public final class Sequence1 {

    public final LinkedList<Integer> canonical = new LinkedList<>();

    public final int size;

    /**
     * Returns a new sequence where the first segment starts at zero.
     * Explores the fact that the sequence is cyclic. Thus,
     * if there are <code>n</code> numbers, there are <code>n</code> segments
     * going from number <code>n</code> to <code>n + 1</code>.
     * With this operation, every segment is replaced by the following one
     * until the first one starts at zero.
     * @param list
     */
    public static void getOffsettedToZero(LinkedList<Integer> list) {
        if (!list.contains(0)) {
            return;
        }
        while (list.getFirst() != 0) {
            list.addLast(list.removeFirst());
        }
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
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.canonical);
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
    public Sequence1(long array[]) {
        LinkedList<Integer> original = new LinkedList<>();
        for (long l : array) {
            original.add((int) l);
        }
        if (!original.contains(0)) {
            throw new RuntimeException("Sequence must contain a zero.");
        }
        int i;
        int rotationI;
        size = original.size();
        LinkedList<Integer> mirror = new LinkedList<>(original);
        boolean reverseOption[] = new boolean[]{false, true};
        for (i = 0; i < size; i++) {
            mirror.set(i, (size - mirror.get(i)) % size);
            canonical.add(size - 1);
        }
        LinkedList<Integer> temporary = new LinkedList<>();
        LinkedList<LinkedList<Integer>> originalOrMirrorList = new LinkedList<>();
        originalOrMirrorList.add(original);
        originalOrMirrorList.add(mirror);
        for (LinkedList<Integer> originalOrMirror : originalOrMirrorList) {
            for (rotationI = 0; rotationI < size; rotationI++) {
                temporary.clear();
                temporary.addAll(originalOrMirror);
                for (i = 0; i < size; i++) {
                    temporary.set(i, (temporary.get(i) + rotationI) % size);
                }
                for (boolean isReversed : reverseOption) {
                    if (isReversed) {
                        Collections.reverse(temporary);
                    }
                    getOffsettedToZero(temporary);
                    if (isFirstSmallerThanSecond(temporary, canonical)) {
                        canonical.clear();
                        canonical.addAll(temporary);
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
//        new Sequence(new long[]{0, 1, 2, 3, 4/*, 5, 6*/});
        Sequence1 sequence = new Sequence1(new long[]{4, 2, 3, 1, 0});
        System.out.println("sequence 3");
        System.out.println(sequence.canonical);
    }
}
