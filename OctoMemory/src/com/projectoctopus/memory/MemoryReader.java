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
public class MemoryReader {

	private MemoryReader() {}

	public static byte read_byte(Memory memory, int ptr) {
		return memory.data[ptr];
	}

	public static byte[] read_bytes(Memory memory, int ptr, int size) {
		byte[] bytes = new byte[size];
		for (int i = ptr; i < size + ptr; i++)
			bytes[i - ptr] = read_byte(memory, i);
		return bytes;
	}

	public static short read_short(Memory memory, int ptr) {
		return (short) ((memory.data[ptr] << 8) | memory.data[ptr + 1]);
	}

	public static char read_char(Memory memory, int ptr) {
		return (char) ((memory.data[ptr] << 8) | memory.data[ptr + 1]);
	}

	public static int read_int(Memory memory, int ptr) {
		byte[] bytes = read_bytes(memory, ptr, 4);
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result <<= 8;
			result |= (bytes[i] & 0xff);
		}
		return result;
	}

	public static long read_long(Memory memory, int ptr) {
		byte[] bytes = read_bytes(memory, ptr, 8);
		long result = 0;
		for (int i = 0; i < 8; i++) {
			result <<= 8;
			result |= (bytes[i] & 0xff);
		}
		return result;
	}

	public static float read_float(Memory memory, int ptr) {
		return Float.intBitsToFloat(read_int(memory, ptr));
	}

	public static double read_double(Memory memory, int ptr) {
		return Double.longBitsToDouble(read_long(memory, ptr));
	}

	public static boolean read_bool(Memory memory, int ptr) {
		assert(memory.data[ptr] == 0 || memory.data[ptr] == 1);
		return read_byte(memory, ptr) != 0;
	}

}
