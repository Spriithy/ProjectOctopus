/**
 * The MIT License (MIT)
 * Copyright (c) 2015 | Théophile Dano, Spriithy
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.projectoctopus.memory;

/**
 * @author Théophile Dano, Spriithy 2015
 */
public class MemoryWriter {

	private MemoryWriter() {}

	public static int write_(Memory memory, int ptr, byte value) {
		memory.alloc_f[ptr] = true;
		memory.data[ptr++] = value;
		return ptr;
	}

	public static int write_(Memory memory, int ptr, byte[] bytes) {
		for (byte value : bytes)
			ptr = write_(memory, ptr, value);
		return ptr;
	}

	public static int write_(Memory memory, int ptr, short value) {
		ptr = write_(memory, ptr, (byte) ((value >> 8) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 0) & 0xff));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, short[] shorts) {
		for (short value : shorts)
			ptr = write_(memory, ptr, value);
		return ptr;
	}
	
	public static int write_(Memory memory, int ptr, char value) {
		ptr = write_(memory, ptr, (byte) ((value >> 8) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 0) & 0xff));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, char[] chars) {
		for (char value : chars)
			ptr = write_(memory, ptr, value);
		return ptr;
	}

	public static int write_(Memory memory, int ptr, int value) {
		ptr = write_(memory, ptr, (byte) ((value >> 24) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 16) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 8) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 0) & 0xff));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, int[] ints) {
		for (int value : ints)
			ptr = write_(memory, ptr, value);
		return ptr;
	}

	public static int write_(Memory memory, int ptr, long value) {
		ptr = write_(memory, ptr, (byte) ((value >> 56) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 48) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 40) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 32) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 24) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 16) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 8) & 0xff));
		ptr = write_(memory, ptr, (byte) ((value >> 0) & 0xff));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, long[] longs) {
		for (long value : longs)
			ptr = write_(memory, ptr, value);
		return ptr;
	}

	public static int write_(Memory memory, int ptr, float value) {
		ptr = write_(memory, ptr, Float.floatToIntBits(value));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, float[] floats) {
		for (float value : floats)
			ptr = write_(memory, ptr, Float.floatToIntBits(value));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, double value) {
		ptr = write_(memory, ptr, Double.doubleToLongBits(value));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, double[] doubles) {
		for (double value : doubles)
			ptr = write_(memory, ptr, Double.doubleToLongBits(value));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, boolean value) {
		ptr = write_(memory, ptr, (byte) ((value) ? 0x01 : 0x00));
		return ptr;
	}

	public static int write_(Memory memory, int ptr, boolean[] booleans) {
		for (boolean value : booleans)
			ptr = write_(memory, ptr, value);
		return ptr;
	}

}
