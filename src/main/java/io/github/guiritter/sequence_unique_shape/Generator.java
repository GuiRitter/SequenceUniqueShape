package io.github.guiritter.sequence_unique_shape;

import io.github.guiritter.tally_counter.TallyCounter;
import static io.github.guiritter.tally_counter.TallyCounter.Type.UNIQUE_NUMBERS;
import java.io.IOException;
import java.util.LinkedList;

/**
 * ignoring all treatments for now (null or empty parameters, etc)
 */
public final class Generator {

    private TallyCounter counter;

    private final LinkedList<Sequence> list = new LinkedList<>();

    private LinkedList<Sequence> returnList;

    public LinkedList<Sequence> generate(int size) {
        counter = new TallyCounter(size, UNIQUE_NUMBERS, size - 1);
        list.clear();
        int index = 0;
        while (!counter.overflowFlag) {
            list.add(new Sequence(/*index++, */counter.getArray()));
//            System.out.println(list.getLast());
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
        Generator generator = new Generator();
        int size = 5;
        boolean graphFlag = false;
        long timeA;
        long timeB;
        timeA = System.nanoTime();
        LinkedList<Sequence> sequenceList = generator.generate(size);
//        for (Sequence sequence : sequenceList) {
//            System.out.println(sequence);
//        }
//        System.out.println();
        LinkedList<Sequence> sequenceListUnrepeated = generator.removeDuplicate(sequenceList);
        timeB = System.nanoTime();
        System.out.println(sequenceListUnrepeated.size());
        System.out.println(timeB - timeA);
        /*
        Grapher grapher = null;
        int array[] = null;
        int i;
        Distance distance = new Distance();
        array = new int[size];
        if (graphFlag) {
            grapher = new Grapher(512, size, new File("C:\\Users\\GuiR\\Downloads\\test"));
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
        /**/
    }
}
