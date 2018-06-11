package com.cappuccino.struct;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class UniqueQueue<T> extends LinkedList<T> {
	private static final long serialVersionUID = -5579690682134119058L;

	private final Set<T> set = new HashSet<T>();

	@Override
	public boolean add(T e) {
		// Only add element to queue if the set does not contain the specified
		// element.
		if (set.add(e)) {
			super.add(e);
		}
		return true; // Must always return true as per API def.
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T e : c) {
			if (set.add(e)) {
				super.add(e);
			}
		}
		return true;
	}

	@Override
	public T remove() {
		T ret = super.remove();
		set.remove(ret);
		return ret;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		set.removeAll(c);
		return super.removeAll(c);
	}

	@Override
	public boolean remove(Object o) {
		set.remove(o);
		return super.remove(o);
	}

	@Override
	public void clear() {
		super.clear();
		set.clear();
	}

}
