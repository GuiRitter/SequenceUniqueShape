package io.github.guiritter.sequence_unique_shape;

import io.github.guiritter.tallycounter.TallyCounter;
import static io.github.guiritter.tallycounter.TallyCounter.Type.UNIQUE_NUMBERS;
import java.io.IOException;
import java.util.LinkedList;

/**
 * ignoring all treatments for now (null or empty parameters, etc)
 */
public final class Generator1 {

    private TallyCounter counter;

    private final LinkedList<Sequence1> list = new LinkedList<>();

    private LinkedList<Sequence1> returnList;

    public LinkedList<Sequence1> generate(int size) {
        counter = new TallyCounter(size, UNIQUE_NUMBERS, size - 1);
        list.clear();
        while (!counter.overflowFlag) {
            list.add(new Sequence1(counter.getArray()));
            counter.increment();
        }
        return list;
    }

    public LinkedList<Sequence1> removeDuplicate(LinkedList<Sequence1> sequenceList) {
        returnList = new LinkedList<>();
        boolean contains;
        for (Sequence1 sequenceArgument : sequenceList) {
            contains = false;
            for (Sequence1 sequenceReturn : returnList) {
                if (sequenceArgument.equals(sequenceReturn)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                returnList.add(sequenceArgument);
            }
        }
        return returnList;
    }

    public static void main(String args[]) throws IOException {
        Generator1 generator = new Generator1();
        int size = 10;
        long timeA;
        long timeB;
        timeA = System.nanoTime();
        LinkedList<Sequence1> sequenceList = generator.generate(size);
        LinkedList<Sequence1> sequenceListUnrepeated = generator.removeDuplicate(sequenceList);
        timeB = System.nanoTime();
        System.out.println(sequenceListUnrepeated.size() + "\t\t" + (timeB - timeA));
    }
}
