package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {
	private Comparator<E> cmp;
	public SortedArrayList(Comparator<E> cmp) {
		super();
		this.cmp = cmp;
	}

	public boolean add(E e) {
		int index = 0;

		while(index < this.size() && (cmp.compare(e, this.get(index)) >= 0)){
			++index;
		}
		
		super.add(index, e);

		return true;
		
	 }
	 @Override
	 public boolean addAll(Collection<? extends E> c) {
		 for(E aux : c)
			 this.add(aux);
		
		return true;
	}

	//las siguientes operaciones romperian la ordenacion
	//all this operations would break the sort
	public void add(int index,E element) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends E> c) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public E set(int index, E element) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}