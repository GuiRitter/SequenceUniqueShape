package io.github.guiritter.sequence_unique_shape;

import io.github.guiritter.tally_counter.TallyCounter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Value {

    private final long valueDigitArray[];

    private final int size;

    public final long of(List<Integer> sequence) {
        /* more correct but less performance
        int sequenceInt[] = new int[size];
        for (int i = 0; i < size; i++) {
            sequenceInt[i] = sequence.get(i);
        }
        return of(sequenceInt);
        /**/
        long value = 0;
        for (int i = 0; i < size; i++) {
            value += valueDigitArray[i] * sequence.get(i);
        }
        return value;
    }

    public final long of(int sequence[]) {
        long value = 0;
        for (int i = 0; i < size; i++) {
            value += valueDigitArray[i] * sequence[i];
        }
        return value;
    }

    public Value(int size) {
        this.size = size;
        valueDigitArray = new long[size];
        for (int i = 0; i < size; i++) {
            valueDigitArray[i] = (new BigInteger(size + "")).pow(size - i - 1).longValue();
        }
    }

    public static void main(String args[]) {
        TallyCounter counter = new TallyCounter(3, TallyCounter.Type.UNIQUE_NUMBERS, 2);
        Value value = new Value(3);
        int array[] = new int[3];
        while (!counter.overflowFlag) {
            for (int i = 0; i < 3; i++) {
                array[i] = (int) counter.getArray()[i];
            }
            System.out.println(Arrays.toString(counter.getArray()) + "\t" + value.of(array));
            counter.increment();
        }
    }
}
