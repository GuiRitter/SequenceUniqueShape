package io.github.guiritter.sequence_unique_shape;

import io.github.guiritter.tallycounter.TallyCounter;
import static io.github.guiritter.tallycounter.TallyCounter.Type.UNIQUE_NUMBERS;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * ignoring all treatments for now (null or empty parameters, etc)
 */
public final class SequenceGenerator {

    private TallyCounter counter;

    private final LinkedList<Sequence> list = new LinkedList<>();

    private LinkedList<Sequence> returnList;

    public LinkedList<Sequence> generate(int size) {
        counter = new TallyCounter(size, UNIQUE_NUMBERS, size - 1);
        list.clear();
        int index = 0;
        while (!counter.overflowFlag) {
            list.add(new Sequence(/*index++, */counter.getArray()));
            counter.increment();
        }
        return list;
    }

    public LinkedList<Sequence> removeDuplicate(LinkedList<Sequence> sequenceList) {
        returnList = new LinkedList<>();
        boolean contains;
        for (Sequence sequenceArgument : sequenceList) {
            contains = false;
            for (Sequence sequenceReturn : returnList) {
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
        SequenceGenerator generator = new SequenceGenerator();
        int size = 8;
        LinkedList<Sequence> sequenceList = generator.generate(size);
        boolean graphFlag = false;
//        for (Sequence sequence : sequenceList) {
//            System.out.println(sequence);
//        }
//        System.out.println();
        LinkedList<Sequence> sequenceListUnrepeated = generator.removeDuplicate(sequenceList);
        SequenceGrapher grapher = null;
        int array[] = null;
        int i;
        Distance distance = new Distance();
        array = new int[size];
        if (graphFlag) {
            grapher = new SequenceGrapher(512, size, new File("C:\\Users\\GuiR\\Downloads\\test"));
        }
        for (Sequence sequence : sequenceListUnrepeated) {
            for (i = 0; i < sequence.original.size(); i++) {
                array[i] = sequence.original.get(i);
            }
            if (graphFlag) {
                grapher.graph(array);
            }
            System.out.println(Arrays.toString(array) + "\t" + Arrays.toString(distance.op(array)));
//            System.out.println(Arrays.toString(array));
//            System.out.println(sequence);
        }
    }
}
