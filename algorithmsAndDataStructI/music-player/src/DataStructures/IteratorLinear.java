package es.uned.lsi.eped.DataStructures;

//representa un iterador de una estructura lineal: colecciones, conjuntos
//listas, pilas, colas, ...
public class IteratorLinear<T> extends Iterator<T> {

	IteratorLinear(){
		super();
	}
	IteratorLinear(Node<T> node){
		super(node);
	}
}