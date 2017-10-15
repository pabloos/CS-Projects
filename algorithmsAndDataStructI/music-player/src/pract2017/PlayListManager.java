package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class PlayListManager implements PlayListManagerIF{

    private List<String> nPlayLists;        //lista de identificadores
    private List<PlayList> cPlayLists;      //lista de playLists
    private int cont;

    public PlayListManager(){
        this.nPlayLists = new List<String>();
        this.cPlayLists = new List<PlayList>();
        this.cont = 0;
    }
    
    public boolean contains(String playListID){
        return nPlayLists.contains(playListID);
    }

    public PlayListIF getPlayList(String playListID){
        int contador = 1;
        Node<String> n = nPlayLists.returnFirstNode();
        Node<PlayList> p = cPlayLists.returnFirstNode();

        while(n!=null){
            if (n.getValue().equals(playListID)) {
                return p.getNode(contador).getValue();
            }
            n = n.getNext();  
            contador++;
        }
        return null;
    }

    public ListIF<String> getIDs(){
        return this.nPlayLists;
    }

    public void createPlayList(String playListID){
        cont++;        
        this.cPlayLists.insert(new PlayList(playListID), cont);
        this.nPlayLists.insert(playListID, cont);
    }

    public void removePlayList(String playListID){
        int contador = 1;
        Node<String> n = nPlayLists.returnFirstNode();

		while(n!=null){
            if (n.getValue().equals(playListID)) {
                cPlayLists.remove(contador);
                nPlayLists.remove(contador);
                cont--;
                contador--;
            }
			n = n.getNext();  
            contador++;
        }
    }
}