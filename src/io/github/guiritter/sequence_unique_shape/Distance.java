package io.github.guiritter.sequence_unique_shape;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.Math.floorMod;
import java.util.Arrays;

/**
 * Defines a distance metric for numbers in a circular sequence. The numbers
 * are unique in the sequence and range from {@code 0} to {@code N - 1}
 * for a length {@code N} sequence. This distance is an array. First,
 * it picks a number in the sequence. It looks for the neighbors to this number
 * in Euclidean space. That is, for {@code 0} it's {@code 1} and {@code N - 1}.
 * Then, it looks for the difference in index between the number
 * and its neighbors. The smallest such difference is stored in the first index
 * in the array. Then, the process is repeated for further neighbors. That is,
 * the next step for {@code 0} is {@code 2} and {@code N - 2}, which is stored
 * in the second index in the array, then the next step is {@code 3}
 * and {@code N - 3}, which is stored in the third index in the array,
 * and so on.
 *
 * The most desirable is a distance where each index is greater than or equal
 * to the next one. That is, the distance between closest neighbors is maximum,
 * and the distance between furthest neighbors can be minimum.
 * @author Guilherme Alan Ritter
 */
public final class Distance {

    private int distance[];

    private int distanceIndex;

    private int distanceMinimum;

    private int n;

    private int neighborI;

    private int neighborIndex0;

    private int neighborIndex1;

    private int neighborValue0;

    private int neighborValue1;

    private int sequenceIndex;

    public final int[] op(int sequence[]) {
        n = sequence.length;
        neighborIndex0 = -1;
        neighborIndex1 = -1;
        distance = new int[n / 2];
        for (distanceIndex = 1; distanceIndex <= distance.length; distanceIndex++) {
            distanceMinimum = MAX_VALUE;
            for (sequenceIndex = 0; sequenceIndex < n; sequenceIndex++) {
                neighborValue0 = (sequence[sequenceIndex] + distanceIndex) % n;
                neighborValue1 = floorMod(sequence[sequenceIndex] - distanceIndex, n);
                for (neighborI = 0; neighborI < n; neighborI++) {
                    if (sequence[neighborI] == neighborValue0) {
                        neighborIndex0 = neighborI;
                    } else if (sequence[neighborI] == neighborValue1) {
                        neighborIndex1 = neighborI;
                    }
                }
                distanceMinimum = min(distanceMinimum, abs(sequenceIndex - neighborIndex0) % n);
                distanceMinimum = min(distanceMinimum, abs(sequenceIndex - neighborIndex1) % n);
            }
            distance[distanceIndex - 1] = distanceMinimum;
        }
        return distance;
    }

    public static void main(String args[]) {
//        int sequence[] = {0, 1, 2, 3, 4};
//        int sequence[] = {0, 2, 4, 1, 3};
//        int sequence[] = {0, 3, 6, 2, 5, 1, 4};
//        int sequence[] = {0, 2, 4, 6, 1, 3, 5};
//        int sequence[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; // step 0 // [1, 2, 3, 4, 5, 6, 7, 0]
//        int sequence[] = {0, 3, 6, 9, 12, 15, 2, 5, 8, 11, 14, 1, 4, 7, 10, 13}; // step 2 // [5, 6, 1, 4, 7, 2, 3, 0]
//        int sequence[] = {0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12, 1, 6, 11}; // step 4 // [3, 6, 7, 4, 1, 2, 5, 0]
        int sequence[] = {0, 7, 14, 5, 12, 3, 10, 1, 8, 15, 6, 13, 4, 11, 2, 9}; // step 6 // [7, 2, 5, 4, 3, 6, 1, 0]
        int distanceIndex;
        int n = sequence.length;
        int distance[] = new int[n / 2];
        int sequenceIndex;
        int neighborValue0;
        int neighborValue1;
        int neighborI;
        int neighborIndex0 = -1;
        int neighborIndex1 = -1;
        int distanceMinimum;
        for (distanceIndex = 1; distanceIndex <= distance.length; distanceIndex++) {
            distanceMinimum = MAX_VALUE;
            for (sequenceIndex = 0; sequenceIndex < n; sequenceIndex++) {
                neighborValue0 = (sequence[sequenceIndex] + distanceIndex) % n;
                neighborValue1 = floorMod(sequence[sequenceIndex] - distanceIndex, n);
                for (neighborI = 0; neighborI < n; neighborI++) {
                    if (sequence[neighborI] == neighborValue0) {
                        neighborIndex0 = neighborI;
                    } else if (sequence[neighborI] == neighborValue1) {
                        neighborIndex1 = neighborI;
                    }
                }
                distanceMinimum = min(distanceMinimum, abs(sequenceIndex - neighborIndex0) % n);
                distanceMinimum = min(distanceMinimum, abs(sequenceIndex - neighborIndex1) % n);
            }
            distance[distanceIndex - 1] = distanceMinimum;
        }
        System.out.println(Arrays.toString(distance));
    }
}

/*

  1
4   3
 2 0

0 1 2 3 4

max 2

5 / 2 = 2

0 1 2 3 4 5

max 3

6 / 2 = 3

check neighbors up to N / 2

*/