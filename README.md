# Sequence Unique Shape

Consider a cyclic ordered sequence of `n` numbers ranging from `0` to `n - 1`. Consider the concept of neighborhood: a number is a neighbor with the number that comes before and after it in the sequence. Consider that the sequence can be transformed in ways that does not change the neighborhood setup. Therefore, applying a combination of transformations to a sequence can result in several different forms that all represent the same neighborhood setup. Consider that one wants to know every sequence of `n` numbers that represent a distinct neighborhood setup.

I've written a program that does that.

Though the sequence exists only in one dimension, it's easier to understand these concepts by visualizing them. The sequence can be mapped a polygon, with vertices coinciding with those of a convex regular n-gon. Then, every vertex is a number and every line segment is a neighborhood between two numbers.

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/sequence.svg" height="256" alt="sequence example in an hexagon (012453)"/>

This way, it's easy to visually see what kinds of transformations do not alter the "shape" of the polygon. The first such transformation is the mirroring:

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/mirror.svg" height="256" alt="012453 mirrored"/>

It must be done about an axis, which can either go through numbers or the space between them, but in practice only any one is necessary. About the number zero, it's computed by `(size - numberValue) % size`, where `size` is the amount of numbers. The second transformation is the rotation:

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/rotation.svg" height="256" alt="012453 rotated"/>

It can be done in either direction, but a rotation by 1 in a direction is the same as a rotation by `size - 1` in the other direction. Also, a combination of mirrorings and rotations result in other combinations of mirrorings and rotations, and that's why only one mirroring is necessary. It can be computed by adding 1 to the value of each number, using the modulo to loop back to zero. The third transformation is the reversion:

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/reverse.svg" height="256" alt="012453 reversed"/>

It's about forming the neighborhoods in the reverse direction. Every combination of mirroring and rotation needs also to be reversed for the equality test. It can be computed by reversing the ordering of the numbers. It looks like it's the same as mirroring by the number(s) in the middle, but remember that mirroring work with the number's value and reversion works with the number's position, and that makes all the diference. The last transformation is the offset:

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/offset.svg" height="256" alt="012453 offsetted"/>

It's about changing the ordering of the numbers in the sequence (while maintaining the neighborhoods). It can be computed by removing numbers from the first position and adding to the last, or vice-versa. Since it doesn't matter in which number the sequence starts, it's convenient to always start it at the same number, such as zero, so forms can be tested for equality by testing each number in order.

I managed to find all such sequences for `n` up to 8. For 9, it threw `java.lang.OutOfMemoryError: Java heap space`, even with `-Xmx3G`, after 24 minutes and 37 seconds. I used online curve fitting software and estimated that there should have about 894 unique sequences of 9 numbers, so, yeah, I think it will take some improvements to manage that. Here's all the sequences for 4 numbers (2 sequences):

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/4.svg" height="128" alt="all sequences with four numbers"/>

5 numbers (4 sequences):

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/5.svg" height="256" alt="all sequences with five numbers"/>

6 numbers (12 sequences):

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/6.svg" height="384" alt="all sequences with six numbers"/>

7 numbers (39 sequences):

<img src="https://cdn.rawgit.com/GuiRitter/SequenceUniqueShape/master/src/io/github/guiritter/sequence_unique_shape/image/7.svg" height="768" alt="all sequences with seven numbers"/>

8 numbers (202 sequences):

![all sequences with eight numbers](src/io/github/guiritter/sequence_unique_shape/image/8.png)

Although my program is capable of finding these sequences for `n` up to infinity (I suppose), clearly it can't do that in a practical amount of time. That's because it generates every single possible combination of numbers and then tests all of them for equality to remove the duplicate sequences. Ideally, it should generate the unique sequences from scratch, which should increase it's performance by a lot, but I still haven't managed to find out how to do that. I would be happy if someone managed to do it and made it available to all as I'm doing.

And the reason why I went through all this trouble was to solve another problem: make a sequence of comparable items where, the more similar two items are, the further away they are in the sequence. The mapping from this problem to this program is that the items are numbers and they are similar to each other by their value (and, since the sequence is cyclic, the last value is most similar to the one before it and zero). This program generates all the sequences that are inputs to this problem, so the items/numbers can be tested for likeliness or "distance".

I found a few interesting things while tackling these problems. If you look at all the sequences above, you'll see not only a pentagram, but also a septagram and an octagram. These sequences result in the highest "distance" between the closest neighbors (for sequences of 5 numbers: 1-2, 2-3, 3-4, 4-5, 5-0), but the lowest "distance" between the second-closest neighbors (0-2, 1-3, 2-4, 3-0, 4-1). I wanted the "distance" to be inversely proportional to how close together each item/number is, and these sequences fail that hard. But these n-grams, that are stellations of the n-gons, made me believe the answer may be at [stellation](https://en.wikipedia.org/wiki/Stellation). For example, one of the stellations of the 16-gon results in 4 squares, and that gives a pretty good "sequence" (4 sequences, actually; minor hacking is required), if I remember it right.

This program is not user friendly, because it's meant only to test these theories. So, you'll need to look at the source code and edit it to your needs.

Depends on my [TallyCounter](https://github.com/GuiRitter/TallyCounter).

## Version 1

I actually managed to improve how duplicates are detected and discarded. I don't know how I didn't think of this before. Since a sequence can be transformed into many equivalent forms, it's much more efficient to define a method to elect one of these forms as the canonical one. Since all sequences that are equivalent generate the same set of equivalent forms, they're also bound to generate the same canonical form. So, there's no need to store the many forms, just the canonical one.

For sequences of 8 numbers, sequence generation went from 8.1 seconds to 1.2 seconds. It managed to find sequences of 9 numbers. I predicted 894 sequences and found 1 219 after 12 seconds. Funny thing is, I managed to break the 9 numbers barrier only to hit another barrier at 10 numbers... First, I got a `java.lang.OutOfMemoryError: GC overhead limit exceeded` because I'm generating way too much `LinkedList`s out of fear of leaking references. I optimized my code to remove as much object allocation as I could, then it was back to `java.lang.OutOfMemoryError: Java heap space`. It stopped after 9 minutes and 44 seconds after generating 2 751 573 sequences (before removing duplicates).

### Update

I added a method that generates the sequences by only adding to the list sequences that have not been generated before. So, it tests for duplicates after generating every sequence candidate. I managed to find 9 468 unique sequences of 10 numbers after 10 minutes and 38 seconds. Let's see how far it will go this time...

Theoretically, it's not the right way, but it might allow my program to go a bit further. Even then, it can be seen that it will not be able to go to much larger numbers without the unique sequences being generated directly, instead of generating all possibilities and then removing the duplicates.

Also, I didn't call this a new version because I'm keeping both sequence generation methods.
