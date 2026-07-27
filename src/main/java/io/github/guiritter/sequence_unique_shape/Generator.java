package io.github.guiritter.sequence_unique_shape;

import static io.github.guiritter.tally_counter.TallyCounter.Type.UNIQUE_NUMBERS;
import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.script.ScriptException;

import io.github.guiritter.tally_counter.TallyCounter;
import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 * ignoring all treatments for now (null or empty parameters, etc)
 */
public final class Generator implements Runnable {

	@Option(names = { "-graph" }, description = "render graphs of sequences") 
	boolean isGraph;

	// @Option(names = { "-skip-bad-distance" }, description = "skips sequences whose distance is 1 for the closest neighbors") 
	// boolean skipBadDistance;

	private TallyCounter counter;

	private final LinkedList<Sequence> list = new LinkedList<>();

	private LinkedList<Sequence> returnList;

	public LinkedList<Sequence> generate(int size) {
		counter = new TallyCounter(size, UNIQUE_NUMBERS, size - 1);
		list.clear();
		// int index = 0;
		while (!counter.overflowFlag) {
			list.add(new Sequence(/*index++, */counter.getArray()));
			// System.out.println(list.getLast());
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

	@Override
	public void run() {
		out.println("Sequence Unique Shape · Generator · run; graph: " + isGraph);
		// out.println("Sequence Unique Shape · Generator · run; graph: " + isGraph + "; skipBadDistance: " + skipBadDistance);

		Generator generator = new Generator();
		int size = 7;
		long timeA;
		long timeB;
		timeA = System.nanoTime();
		LinkedList<Sequence> sequenceList = generator.generate(size);
		// for (Sequence sequence : sequenceList) {
		// 	System.out.println(sequence);
		// }
		// System.out.println();
		LinkedList<Sequence> sequenceListUnrepeated = generator.removeDuplicate(sequenceList);
		timeB = System.nanoTime();
		System.out.println(sequenceListUnrepeated.size());
		System.out.println(timeB - timeA);
		// render graph

		Grapher grapher = null;
		int array[] = null;
		int i;
		Distance distance = new Distance();
		array = new int[size];
		if (isGraph) {
			grapher = new Grapher(512, size, new File("C:\\ciência\\matemática\\combinatória\\sequence unique shape\\graph\\7"));
		}
		for (Sequence sequence : sequenceListUnrepeated) {
			for (i = 0; i < sequence.original.size(); i++) {
				array[i] = sequence.original.get(i);
			}
			if (isGraph) {
				grapher.graph(array);
			}
			System.out.println(Arrays.toString(array) + "\t" + Arrays.toString(distance.apply(array)));
			// System.out.println(Arrays.toString(array));
			// System.out.println(sequence);
		}
	}

	public static void main(String args[]) throws IOException, ScriptException {
		out.println("Sequence Unique Shape · Generator · main");
		int exitCode = new CommandLine(new Generator()).execute(args); 
		System.exit(exitCode); 
	}
}
