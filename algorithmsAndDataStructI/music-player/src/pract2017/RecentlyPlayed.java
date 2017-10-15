package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class RecentlyPlayed implements RecentlyPlayedIF {

    private List<Integer> lista;
    private int numMax;
    private int numCanciones;

    public RecentlyPlayed(int numMax){
       this.lista = new List<Integer>();
       this.numMax = numMax;
       this.numCanciones = 0;
    }

    public ListIF<Integer> getContent(){
        List<Integer> lista2 = new List<Integer>();
        for(int i = 1; i <= this.numCanciones; i++){
            lista2.insert(this.lista.get(this.numCanciones - i + 1), i);
        }
        return lista2;
    }

    public void addTune(int tuneID){
        if(this.numCanciones == this.numMax){
            this.lista.remove(1);
            this.numCanciones--;
        }
        this.lista.insert(tuneID, ++numCanciones);
    }
}