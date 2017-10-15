package es.uned.lsi.eped.pract2016_2017;
import es.uned.lsi.eped.DataStructures.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String [] args) throws IOException{
		/* 
		  leer argumentos: 
		  	numero máximo de canciones recientemente escuchadas (arg[0]),
		  	fichero del repositorio de canciones (arg[1]),
		  	fichero de operaciones (arg[2]).
		 */
		
		String pathTunes = args[0];
		String pathOperations = args[1];
		int maxRecentlyPlayed = Integer.valueOf(args[2]);
		
		//Creación de TunesCollection
		TuneCollection tCollection = new TuneCollection(pathTunes);
		
		//Creación de Player
		Player player = new Player(tCollection,maxRecentlyPlayed);
		//Número de canciones del repositorio
		System.out.println(tCollection.size());
		//Lectura del fichero de operaciones
	    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(pathOperations), "UTF-8"));
		//Ejecución de las operaciones en el orden recibido
	    String operation;
	    while ((operation = in.readLine())!=null) {
	    	Operation op = new Operation(operation);
	    	op.execute(player);
	    } 
	    in.close();	    
	}
}