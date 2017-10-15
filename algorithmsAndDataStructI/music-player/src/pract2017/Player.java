package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

public class Player implements PlayerIF {

    private TuneCollection collection;
    private PlayListManager playListManager;

    private PlayBackQueue playBackQueue;
    private RecentlyPlayed recentlyPlayed;

    public Player(TuneCollection collection, int maxRecentlyPlayer){
        this.collection = collection;

        this.playListManager = new PlayListManager();
        this.playBackQueue = new PlayBackQueue();
        this.recentlyPlayed = new RecentlyPlayed(maxRecentlyPlayer);
    }

    public ListIF<String> getPlayListIDs() {
        return this.playListManager.getIDs();
    }

    public ListIF<Integer> getPlayListContent(String playListID) {      //estamos haciendo un casting algo feote
        if(this.playListManager.contains(playListID)){
            return (ListIF<Integer>) this.playListManager.getPlayList(playListID).getPlayList();
        } else {
            return new List<Integer>();
        }
    }

    public ListIF<Integer> getPlayBackQueue() {
        return this.playBackQueue.getContent();
    }

    public ListIF<Integer> getRecentlyPlayed() {
        return this.recentlyPlayed.getContent();
    }

    public void createPlayList(String playListID) {
        if(!(this.playListManager.contains(playListID))){
            this.playListManager.createPlayList(playListID);
        }
    }

    public void removePlayList(String playListID) {
        if(this.playListManager.contains(playListID)){
            this.playListManager.removePlayList(playListID);
        }
    }

    public void addListOfTunesToPlayList(String playListID, ListIF<Integer> lT) {
        if(this.playListManager.contains(playListID)){
            this.playListManager.getPlayList(playListID).addListOfTunes(lT);
        } 
    }

    public void addSearchToPlayList(String playListID, String t, String a, String g, String al, int min_y, int max_y,
            int min_d, int max_d) {
        if(this.playListManager.contains(playListID)){
            Query query = new Query(t,a,g,al,min_y,max_y, min_d, max_d);
            List<Integer> lista = new List<Integer>();
            int contador = 0;

            for(int i = 0; i < collection.size()-1; i++){
                if(collection.getTune(i).match(query)){
                    lista.insert(i, ++contador);
                }
            }

            this.playListManager.getPlayList(playListID).addListOfTunes(lista);
        }
    }

    public void removeTuneFromPlayList(String playListID, int tuneID) {
        if(this.playListManager.contains(playListID)){
            this.playListManager.getPlayList(playListID).removeTune(tuneID);
        }
    }

    public void addListOfTunesToPlayBackQueue(ListIF<Integer> lT) {        
        this.playBackQueue.addTunes((List<Integer>) lT);
    }

    public void addSearchToPlayBackQueue(String t, String a, String g, String al, int min_y, int max_y, int min_d,
            int max_d) {
        Query query = new Query(t,a,g,al,min_y,max_y, min_d, max_d);
        List<Integer> lista = new List<Integer>();
        int contador = 0;

        for(int i = 0; i < collection.size()-1; i++){
            if(collection.getTune(i).match(query)){
                lista.insert(i, ++contador);
            }
        }

        this.playBackQueue.addTunes(lista);
    }

    public void addPlayListToPlayBackQueue(String playListID) {
		PlayListIF playList = this.playListManager.getPlayList(playListID);
		List<Integer> list = (List<Integer>) playList.getPlayList();

		List<Integer> list2 = new List<Integer>();

		int contador = 1;

		Node<Integer> n = list.returnFirstNode(); 

		while(n!=null){
			list2.insert(n.getValue(), contador);
			contador++;
			n = n.getNext();
		}

        this.playBackQueue.addTunes(list2);
    }

    public void clearPlayBackQueue() {
        this.playBackQueue.clear();
    }

    public void play() {
        if(!this.playBackQueue.isEmpty()){
            this.recentlyPlayed.addTune(this.playBackQueue.getFirstTune());
            this.playBackQueue.extractFirstTune();
        } else {
            return;
        }
    }
}