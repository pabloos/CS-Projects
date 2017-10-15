package es.uned.lsi.eped.DataStructures;

public class Queue<T> extends Collection<T> implements QueueIF<T>  {

	private Node<T> lastNode;
	
	//constructor por defecto: crea una cola vacía
	public Queue(){
		super();
		lastNode = null;
	}
	
	//constructor por copia
	public Queue(Queue<T> Q){
		super();
		IteratorIF<T> iterator = Q.iterator();
		while (iterator.hasNext()){
			T e = iterator.getNext();
			enqueue(e);
		}
	}
	//devuelve el número de elementos de la cola
	public int size() {
		return super.size();
	}
    //decide si la cola llamante está vacía
	public boolean isEmpty() {
		return super.isEmpty();
	}
    //decide si la cola llamante contiene el elemento dado por parámetro
	public boolean contains(T e) {
		return super.contains(e);
	}
  //borra todos los elementos de la cola
	public void clear() {
		super.clear();
	}
   //devuelve un iterador para la cola llamante
	public IteratorIF<T> iterator() {
		return super.iterator();
	}
	/* @Pre !isEmpty()											*
	 * @return la cabeza de la cola (su primer elemento).		*/
	public T getFirst() {
		if(!isEmpty()){
			return firstNode.getValue();
		}
		return null;
	}

	/* Incluye un elemento al final de la cola. Modifica el 	*
     * tamaño de la misma.										*
     * @param elem el elemento que debe encolar (añadir).		*/
	public void enqueue(T elem) {
		if(isEmpty()){
			Node<T> newNode = new Node<T>(elem);
			firstNode = newNode;
			lastNode = newNode;
		}
		else{
			Node<T> newNode = new Node<T>(elem);
			lastNode.setNext(newNode);
			lastNode = newNode;
		}
		size++;
	}
	 /* Elimina el primer elemento de la cola. Modifica la 		*
     * tamaño de la misma.										*
     * @Pre !isEmpty();											*/
	public void dequeue() {
		firstNode = firstNode.getNext();
		size--;
		if(size==0){
			lastNode = null;
		}
	}
}