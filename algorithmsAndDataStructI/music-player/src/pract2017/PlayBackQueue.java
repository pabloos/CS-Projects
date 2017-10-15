package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class PlayBackQueue {
    private Queue<Integer> playBackQueue;

    public PlayBackQueue(){
        this.playBackQueue = new Queue<Integer>();
    }

    public ListIF<Integer> getContent(){
        List<Integer> list = new List<Integer>();
        Node<Integer> n = this.playBackQueue.firstNode;
        int contador = 1;

		while(n!=null){
            list.insert(n.getValue(), contador);
		    n = n.getNext();
            contador++;
		}

        return list;
    }

    public boolean isEmpty(){
        return this.playBackQueue.isEmpty();
    }

    public int getFirstTune(){
        return this.playBackQueue.getFirst();
    }
    
    public void extractFirstTune(){
        this.playBackQueue.dequeue();
    }

    public void addTunes(List<Integer> lT){
        Node<Integer> n = lT.firstNode;
        
		while(n!=null){
            this.playBackQueue.enqueue(n.getValue());
		    n = n.getNext();
		}
    }

    public void clear(){
        this.playBackQueue.clear();
    }
}