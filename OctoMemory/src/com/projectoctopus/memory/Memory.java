package com.projectoctopus.memory;

import com.projectoctopus.memory.exception.MemoryException;

public class Memory {

	public static final int	MIN_SIZE	= 16;
	public static final int	MAX_SIZE	= 64 * 1000000;

	public byte[]		data;
	public boolean[]	alloc_f;

	public Memory(int size) {
		if (MIN_SIZE > size || size > MAX_SIZE) throw new IllegalArgumentException("Memory size must be between : " + MIN_SIZE + " bytes and " + MAX_SIZE + " bytes.");
		data = new byte[size];
		alloc_f = new boolean[size];
	}

	public static void m_print(Memory memory, int lsize) {
		for (int i = 1; i < size_(memory) + 1; i++)
			System.out.printf("0x%x " + ((i % lsize == 0) ? "\n" : ""), memory.data[i - 1]);
	}

	public static int m_swap(Memory memory, int ptr1, int ptr2) {
		byte tmp = memory.data[ptr2];
		boolean tmp_f = memory.alloc_f[ptr2];
		memory.data[ptr2] = memory.data[ptr1];
		memory.data[ptr1] = tmp;
		memory.alloc_f[ptr2] = memory.alloc_f[ptr1];
		memory.alloc_f[ptr1] = tmp_f;
		return 0;
	}

	public static int m_push(Memory memory, int ptr) {
		for (int i = ptr; i < size_(memory) - 1; i++) {
			if (!memory.alloc_f[i] && memory.alloc_f[i + 1]) return m_swap(memory, i, i + 1);
		}
		return -1;
	}

	public static void m_arrange(Memory memory, int ptr) {
		while (m_push(memory, ptr) != -1)
			continue;
	}

	public static int m_alloc(Memory memory, int size) throws MemoryException {
		if (size > free_(memory)) throw new MemoryException("Not enough memory available to store " + size + " new bytes");

		return -1;
	}

	public static void m_free(Memory memory, int ptr) {
		if (ptr > size_(memory)) throw new IndexOutOfBoundsException();
		memory.data[ptr] = 0x0;
		memory.alloc_f[ptr] = false;
	}

	public static void m_free(Memory memory, int start, int end) {
		if (start > end) throw new IllegalArgumentException("Start index must be lower than end index");
		for (int ptr = start; ptr < end; ptr++)
			m_free(memory, ptr);
	}

	public static int next_free(Memory memory) {
		for (int ptr = 0; ptr < size_(memory); ptr++)
			if (!memory.alloc_f[ptr]) return ptr;
		return -1;
	}

	public static int size_(Memory memory) {
		return memory.data.length;
	}

	public static int free_(Memory memory) {
		int idx = 0;
		for (boolean flag : memory.alloc_f)
			if (!flag) idx++;
		return idx;
	}

	public static int sizeof_(byte b) {
		return Byte.BYTES;
	}

	public static int sizeof_(short s) {
		return Short.BYTES;
	}

	public static int sizeof_(char c) {
		return Character.BYTES;
	}

	public static int sizeof_(int i) {
		return Integer.BYTES;
	}

	public static int sizeof_(long l) {
		return Long.BYTES;
	}

	public static int sizeof_(float f) {
		return Float.BYTES;
	}

	public static int sizeof_(double d) {
		return Double.BYTES;
	}

	public static int sizeof_(boolean b) {
		return Short.BYTES;
	}

}