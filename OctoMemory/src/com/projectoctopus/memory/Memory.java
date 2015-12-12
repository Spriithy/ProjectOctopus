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

import com.projectoctopus.memory.exception.MemoryException;

/**
 * Represents a dynamic memory that can be read from or written in
 * @see MemoryReader
 * @see MemoryWriter
 * @author Théophile Dano, Spriithy 2015
 * @since 0.1
 */
public class Memory {

	/**
	 * The minimum memory size value
	 */
	public static final int MIN_SIZE = 16;

	/**
	 * The maximum memory size value
	 */
	public static final int MAX_SIZE = 64 * 1000000;

	/**
	 * The memory's data (as a byte array)<br>
	 * <b>Accessing a value :</b><br>
	 * 
	 * <pre>
	 * memory.data[ptr]
	 * </pre>
	 */
	public byte[] data;

	/**
	 * The memory's data allocation flags (as a bool array)<br>
	 * <b>Accessing a value :</b><br>
	 * 
	 * <pre>
	 * memory.alloc_f[ptr]
	 * </pre>
	 * 
	 * <b>Values : </b><br>
	 * - true if <code>data[ptr]</code> is allocated<br>
	 * - false otherwise
	 */
	public boolean[] alloc_f;

	/**
	 * Initializes a new size-byte Memory
	 * @param size The size of the new Memory in bytes
	 * @throws IllegalArgumentException
	 */
	public Memory(int size) {
		/* Is the size in bounds of MIN_SIZE, MAX_SIZE */
		if (MIN_SIZE > size || size > MAX_SIZE) throw new IllegalArgumentException("Memory size must be between : " + MIN_SIZE + " bytes and " + MAX_SIZE + " bytes.");
		data = new byte[size]; /* Initializes the memory data array */
		alloc_f = new boolean[size]; /* Initializes the memory's data allocation flags (false default) */
	}

	/**
	 * Prints the content of the memory as a block formatted with lsize-long lines
	 * @param memory The memory to print the content of
	 * @param lsize The lines length (in bytes per line)
	 */
	public static void m_print(Memory memory, int lsize) {
		for (int ptr = 1; ptr < size_(memory) + 1; ptr++) /* loops through every bytes stored in memory */
			System.out.printf("0x%x " + ((ptr % lsize == 0) ? "\n" : ""), memory.data[ptr - 1]); /* prints the given byte at index ptr in data[] */
	}

	/**
	 * Swaps to given bytes in the memory
	 * @param memory The memory to operate with
	 * @param ptr1 A pointer to the first byte to swap
	 * @param ptr2 A pointer to the second byte to swap
	 * @return 0
	 */
	public static int m_swap(Memory memory, int ptr1, int ptr2) {
		/* temp var */
		byte tmp = memory.data[ptr2];
		boolean tmp_f = memory.alloc_f[ptr2];

		/* puts the first values into the second ones */
		memory.data[ptr2] = memory.data[ptr1];
		memory.alloc_f[ptr2] = memory.alloc_f[ptr1];

		/* puts the temp values (second) into the first one */
		memory.data[ptr1] = tmp;
		memory.alloc_f[ptr1] = tmp_f;
		return 0;
	}

	/**
	 * Pushes the memory addresses 1 byte forward
	 * @param memory The memory to operate with
	 * @param ptr The pointer at which to start pushing
	 * @return <b>0</b> if pushed successfully<br>
	 *         <b>-1</b> otherwise
	 */
	public static int m_push(Memory memory, int ptr) {
		for (int i = ptr; i < size_(memory) - 1; i++) /* loops through memory addresses after ptr */
			if (!memory.alloc_f[i] && memory.alloc_f[i + 1]) return m_swap(memory, i, i + 1); /* swaps the first empty index with the last non-empty one */
		return -1; /* Got no indexes to swap (memory already pushed or full) */
	}

	/**
	 * Pushes the memory until its fully pushed / arranged
	 * @param memory The memory to arrange
	 * @param ptr The pointer to which to start
	 */
	public static void m_arrange(Memory memory, int ptr) {
		while (m_push(memory, ptr) != -1) /* keep pushing till no more pointers to push */
			continue; /* go ahead ? */
	}

	/**
	 * Allocates size bytes in the memory (sets size bytes to allocated and 0x00)
	 * @param memory The memory to allocate into
	 * @param size The size (in bytes) to allocate
	 * @return -1
	 * @throws MemoryException if size to allocate is greater than the memory's capacity
	 */
	public static int m_alloc(Memory memory, int size) throws MemoryException {
		if (size > free_(memory)) throw new MemoryException("Not enough memory available to store " + size + " new bytes");
		// TODO implementation
		return -1;
	}

	/**
	 * Frees a pointer in the memory
	 * @param memory The memory to operate with
	 * @param ptr The pointer to free
	 * @throws IndexOutOfBoundsException
	 */
	public static void m_free(Memory memory, int ptr) {
		if (ptr > size_(memory)) throw new IndexOutOfBoundsException();
		memory.data[ptr] = 0x0; /* resets the ptr's byte */
		memory.alloc_f[ptr] = false; /* sets the ptr's allocation flag to false (free) */
	}

	/**
	 * Frees a range of pointers in the memory
	 * @param memory The memory to operate with
	 * @param start The starting pointer (inclusive)
	 * @param size The number of bytes to free
	 */
	public static void m_free(Memory memory, int start, int size) {
		if (start + size > size_(memory)) throw new IllegalArgumentException("Start index + size must be less than the memory's size");
		for (int ptr = start; ptr < start + size; ptr++) /* loops through all bytes to free */
			m_free(memory, ptr); /* frees the current byte */
	}

	/**
	 * Gives a pointer to the first free byte in the given memory
	 * @param memory The memory to scan
	 * @return A pointer to the first free pointer<br>
	 *         <b>-1</b> if couldn't find any
	 */
	public static int first_free(Memory memory) {
		for (int ptr = 0; ptr < size_(memory); ptr++) /* loops through all bytes in memory */
			if (!memory.alloc_f[ptr]) return ptr; /* if free -> return current pointer */
		return -1; /* if no free pointer found */
	}

	/**
	 * Gives the size of the given memory in bytes
	 * @param memory The memory to get the size of
	 * @return The size of the memory
	 */
	public static int size_(Memory memory) {
		return memory.data.length;
	}

	/**
	 * Gives the free space in the given memory
	 * @param memory The memory to get the free space of
	 * @return The count of free pointers
	 */
	public static int free_(Memory memory) {
		int ptr = 0;
		for (boolean flag : memory.alloc_f) /* loops through all the allocation flags */
			if (!flag) ptr++; /* if free */
		return ptr;
	}

	/**
	 * Gives the size of a byte in memory
	 * @param b A byte
	 * @return The size of a byte
	 */
	public static int sizeof_(byte b) {
		return Byte.BYTES;
	}

	/**
	 * Gives the size of a given byte array in memory
	 * @param bytes A byte array
	 * @return The size of the array
	 */
	public static int sizeof_(byte[] bytes) {
		return sizeof_((byte) 1) * bytes.length;
	}

	/**
	 * Gives the size of a short in memory
	 * @param s A short
	 * @return The size of a short
	 */
	public static int sizeof_(short s) {
		return Short.BYTES;
	}

	/**
	 * Gives the size of a given short array in memory
	 * @param shorts A short array
	 * @return The size of the array
	 */
	public static int sizeof_(short[] shorts) {
		return sizeof_((short) 1) * shorts.length;
	}

	/**
	 * Gives the size of a char in memory
	 * @param c A char
	 * @return The size of a char
	 */
	public static int sizeof_(char c) {
		return Character.BYTES;
	}

	/**
	 * Gives the size of a given char array in memory
	 * @param chars A char array
	 * @return The size of the array
	 */
	public static int sizeof_(char[] chars) {
		return sizeof_((char) 1) * chars.length;
	}

	/**
	 * Gives the size of an integer in memory
	 * @param i An integer
	 * @return The size of an integer
	 */
	public static int sizeof_(int i) {
		return Integer.BYTES;
	}

	/**
	 * Gives the size of a given integer array in memory
	 * @param ints An integer array
	 * @return The size of the array
	 */
	public static int sizeof_(int[] ints) {
		return sizeof_((int) 1) * ints.length;
	}

	/**
	 * Gives the size of a long in memory
	 * @param l A long
	 * @return The size of a long
	 */
	public static int sizeof_(long l) {
		return Long.BYTES;
	}

	/**
	 * Gives the size of a given long array in memory
	 * @param longs A long array
	 * @return The size of the array
	 */
	public static int sizeof_(long[] longs) {
		return sizeof_(1L) * longs.length;
	}

	/**
	 * Gives the size of a float in memory
	 * @param f A float
	 * @return The size of a float
	 */
	public static int sizeof_(float f) {
		return Float.BYTES;
	}

	/**
	 * Gives the size of a given float array in memory
	 * @param floats A float array
	 * @return The size of the array
	 */
	public static int sizeof_(float[] floats) {
		return sizeof_(1.0f) * floats.length;
	}

	/**
	 * Gives the size of a double in memory
	 * @param d A double
	 * @return The size of a double
	 */
	public static int sizeof_(double d) {
		return Double.BYTES;
	}

	/**
	 * Gives the size of a given double array in memory
	 * @param doubles A double array
	 * @return The size of the array
	 */
	public static int sizeof_(double[] doubles) {
		return sizeof_(1.0d) * doubles.length;
	}

	/**
	 * Gives the size of a boolean in memory
	 * @param b A boolean
	 * @return The size of a boolean
	 */
	public static int sizeof_(boolean b) {
		return 1;
	}

	/**
	 * Gives the size of a given boolean array in memory
	 * @param bools A boolean array
	 * @return The size of the array
	 */
	public static int sizeof_(boolean[] bools) {
		return sizeof_(true) * bools.length;
	}

	/**
	 * Gives the size of a given string in memory
	 * @param str A String
	 * @return The size of a string
	 */
	public static int sizeof_(String str) {
		return sizeof_(str.toCharArray()); /* the actual size of the byte array */
	}

}
