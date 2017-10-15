package es.uned.lsi.eped.DataStructures;

public class List<T> extends Collection<T> implements ListIF<T> {

	/*constructor por defecto: crea una lista vacía*/
	public List(){
		super();
	}
	//constructor por copia
	public List(ListIF<T> L){
		super();
		IteratorIF<T> iterator = L.iterator();
		while (iterator.hasNext()){
			T e = iterator.getNext();
			this.insert(e, size+1);
		}
	}
	//número de elementos de la lista
	public int size() {
		return super.size();
	}
    //decide si la lista es vacía
	public boolean isEmpty() {
		return super.isEmpty();
	}
    //decide si la lista contiene el elemento dado por parámetro
	public boolean contains(T e) {
		return super.contains(e);
	}
   //borra todos los elementos de la lista
	public void clear() {
		super.clear();
	}
  //devuelve un iterador para la lista llamante
	public IteratorIF<T> iterator() {
		return super.iterator();
	}
	/* Devuelve el elemento de la lista que ocupa la posición   *
	 * indicada por el parámetro.								*	
	 * @param pos la posición comenzando en 1.					*	
	 * @Pre: 1 <= pos <= size().								*
	 * @return el elemento en la posición pos.					*/
	public T get(int pos) {
		if(1<=pos && pos<=size()){
			Node<T> node = firstNode.getNode(pos);
			return node.getValue();
		}
		return null;
	}
	/* Modifica la posición dada por el parámetro pos para que  *
	 * contenga el valor dado por el parámetro e.				*
	 * @param pos la posición cuyo valor se debe modificar,		*
	 *  comenzando en 1.                                        *
	 * @param e el valor que debe adoptar la posición pos.		*
	 * @Pre: 1 <= pos <= size().								*/
	public void set(int pos, T e) {
		if(1<=pos && pos<=size()){
			Node<T> node = firstNode.getNode(pos);
			node.setValue(e);
		}
	}
	/* Inserta un elemento en la Lista.  			            *
   	 * @param elem El elemento que hay que añadir.				*				
   	 * @param pos  La posición en la que se debe añadir elem,   *
   	 *  comenzando en 1.      	                                *
 	 * @Pre: 1 <= pos <= size()+1									*/
	public void insert(T elem, int pos) {
		if(1<=pos && pos<=this.size()+1){
				if(pos==1){
					Node<T> newNode = new Node<T>(elem);
					newNode.setNext(firstNode);
					firstNode = newNode;
				}
				else{
					Node<T> newNode = new Node<T>(elem);
					Node<T> previousNode = firstNode.getNode(pos-1);
					Node<T> nextNode = firstNode.getNode(pos);
					previousNode.setNext(newNode);
					if(pos<size()+1){
						newNode.setNext(nextNode);
					}
				}
				size++;
			}
	}

	 /* Elimina el elemento que ocupa la posición del parámetro 	*	 
     * @param pos la posición que ocupa el elemento a eliminar,	*
     *  comenzando en 1                                         *
	 * @Pre: 1 <= pos <= size()									*/
	public void remove(int pos) {
		if(1<=pos && pos<=size){
			if(pos==1){
				firstNode = firstNode.getNext();
			}
			else{
				Node<T> previousNode = firstNode.getNode(pos-1);
				Node<T> nextNode = firstNode.getNode(pos+1);
				previousNode.setNext(nextNode);
			}
			size--;
		}
	}

	public Node<T> returnFirstNode(){
		return this.firstNode;
	}
}