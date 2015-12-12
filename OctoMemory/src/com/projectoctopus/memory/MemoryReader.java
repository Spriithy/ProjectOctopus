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
 * A toolkit for reading a source's data
 * @author Theophile Dano, Spriithy 2015
 * @since 0.1
 */
public class MemoryReader {

	private Memory	memory;
	private boolean	linked	= false;

	public void link(Memory memory) {
		assert(!linked);
		linked = true;
		this.memory = memory;
	}

	public byte read_byte(int ptr) {
		return read_byte(memory.data, ptr);
	}

	public short read_short(int ptr) {
		return read_short(memory.data, ptr);
	}

	public char read_char(int ptr) {
		return read_char(memory.data, ptr);
	}

	public int read_int(int ptr) {
		return read_int(memory.data, ptr);
	}

	public long read_long(int ptr) {
		return read_long(memory.data, ptr);
	}

	public float read_float(int ptr) {
		return read_float(memory.data, ptr);
	}

	public double read_double(int ptr) {
		return read_double(memory.data, ptr);
	}

	public boolean read_bool(int ptr) {
		return read_bool(memory.data, ptr);
	}

	public static byte read_byte(byte[] source, int ptr) {
		return (byte) (0xff & source[ptr]);
	}

	public static short read_short(byte[] source, int ptr) {
		return (short) ((0xff & source[ptr++]) << 8 | (0xff & source[ptr]));
	}

	public static char read_char(byte[] source, int ptr) {
		return (char) ((0xff & source[ptr++]) << 8 | (0xff & source[ptr]));
	}

	public static int read_int(byte[] source, int ptr) {
		return (int) ((int) (0xff & source[ptr++]) << 24 |
				(int) (0xff & source[ptr++]) << 16 |
				(int) (0xff & source[ptr++]) << 8 |
				(int) (0xff & source[ptr++]) << 0);
	}

	public static long read_long(byte[] source, int ptr) {
		return (long) ((long) (0xff & source[ptr++]) << 56 | (long) (0xff & source[ptr++]) << 48 |
				(long) (0xff & source[ptr++]) << 40 | (long) (0xff & source[ptr++]) << 32 |
				(long) (0xff & source[ptr++]) << 24 | (long) (0xff & source[ptr++]) << 16 |
				(long) (0xff & source[ptr++]) << 8 | (long) (0xff & source[ptr++]) << 0);
	}

	public static float read_float(byte[] source, int ptr) {
		return Float.intBitsToFloat(read_int(source, ptr));
	}

	public static double read_double(byte[] source, int ptr) {
		return Double.longBitsToDouble(read_long(source, ptr));
	}

	public static boolean read_bool(byte[] source, int ptr) {
		assert(source[ptr] == 0 || source[ptr] == 1);
		return source[ptr] != 0;
	}
}
