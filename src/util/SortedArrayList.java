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
	
	//Revisado, cambio for break por un while bien hecho, en teoria funciona bien,
	// si size == 0 lo mete en hueco 0, si son iguales lo mete por orden de llegada gracias al bucle
	public boolean add(E e) {
		int index = 0;

		while(index < this.size() && (cmp.compare(e, this.get(index)) >= 0)){
			++index;
		}
		
		super.add(index, e);
		//ordena por time, en casos de time coincidentes se ordena por orden de entrada por ficheros
		//en teoria en el cmp esta como ordenar? se vera en un futuro, en caso positivo deberia funcionar
		// programar la insercion ordenada
		return true;
		
	 }
	 @Override
	 public boolean addAll(Collection<? extends E> c) {
		// for(int i = 0; i < c.size(); i++)
		 for(E aux : c)
			 this.add(aux);
		
		return true;
			 
	 // programar inserci�n ordenada (invocando a add)
		 
	 }
	 public void add(int index,E element) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	 public boolean addAll(int index, Collection<? extends E> c) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	 public E set(int index, E element) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	 // sobreescribir los m�todos que realizan operaciones de
	 // inserci�n basados en un �ndice para que lancen excepcion.
	 // Ten en cuenta que esta operaci�n romper�a la ordenaci�n.
	 // estos m�todos son add(int index, E element),
	 // addAll(int index, Colection<? Extends E>) y E set(int index, E element).
	}