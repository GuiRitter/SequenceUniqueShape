package io.github.guiritter.sequence_unique_shape;

public final class Util {

	/**
	 * Adapted from Copilot.
	 * @param src
	 * @return
	 */
	public static final int[] arrayLongToInt(long src[]) {
		int[] dst = new int[src.length];

		int length = src.length;

		for (int i = 0; i < length; i++) {
			dst[i] = (int) src[i];
		}

		return dst;
	}
}
