package com.cappuccino.struct;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class SortedList<E> extends AbstractList<E> {

	private ArrayList<E> internalList = new ArrayList<E>();
	private Comparator<? super E> comparator;

	public SortedList(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean add(E e) {
		boolean ret = internalList.add(e);
		Collections.sort(internalList, comparator);
		return ret;
	}

	@Override
	public E get(int index) {
		return internalList.get(index);
	}

	@Override
	public boolean remove(Object object) {
		return internalList.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return internalList.removeAll(collection);
	}

	@Override
	public int size() {
		return internalList.size();
	}

}
