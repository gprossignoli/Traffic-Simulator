package model;

public interface Observador<T> {
	 void addObservador(T o);
	 void removeObservador(T o);
}
