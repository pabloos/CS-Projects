package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class PlayList extends List implements PlayListIF{
    private List<Integer> playList;
    public String nombreLista;

    public PlayList(String nombreLista){
        this.nombreLista = nombreLista;
        this.playList = new List<Integer>();
    }

    public ListIF<Integer> getPlayList(){
        return this.playList;
    }

    public void addListOfTunes(ListIF<Integer> lT){
        int contador = 1;

        for(int i = this.playList.size() + 1; contador <= lT.size(); i++){
            this.playList.insert(lT.get(contador), i);
            contador++;
        }
    }

    public void removeTune(int tuneID){
        int cont = 1;
        if (this.playList != null) {
            Node<Integer> n = playList.returnFirstNode();
		    while(n!=null){
                if (n.getValue()==tuneID)  {
                    playList.remove(cont);
                    cont--;
                }
                cont++;
			    n = n.getNext();  
		    }
        }
    }
}