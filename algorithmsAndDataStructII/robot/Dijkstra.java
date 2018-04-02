import java.util.Arrays;
import java.util.List;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Dijkstra {
	static boolean 	withTrace 		= false,
					withOutputFile 	= false;

	static String 	pathToInput, 
					pathToOutput;

	static String	traza;
	
	public static void main(String[] args) {
		setArguments(args);
		Reader.getMatrix(pathToInput);

		traza 				= (withTrace) ? Reader.imprimirMatriz(Reader.matrix) + "Vamos de " + Reader.robot + " a " + Reader.exit + "\n\n" : "";
		
		List<String> path 	= getGrafo(Reader.matrix, Reader.nLineas, Reader.nColumnas).caminoMasCorto(Reader.robot, Reader.exit, withTrace);
		
		String results 		= traza + preparePath(Reader.robot, path) + "\nEl coste del recorrido ha sido: " + getCost(path, Reader.matrix);
		
		if(withOutputFile) {	
			writeToFile(pathToOutput, results);
		} else {
			System.out.println(results);
		}
	}

	private static void writeToFile(String pathToOutput, String results) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(pathToOutput));
			out.write(results);
			out.close();
		} catch (IOException e) {
			System.out.println("Exception ");
		}
	}

	private static void setArguments(String[] args) {
		if(args.length < 2) {							//si tenemos un argumento
			if (args[0].equals("-h")) {					//o es la ayuda
				System.out.println("ayuda"); return;
			} else pathToInput 		= args[0];			//o es solo el archivo de entrda
		} else {										//si tenemos dos o mas
			if(args.length < 3) {						//si tenemos dos: traza e input
				if(args[0].equals("-t")){
					withTrace 		= true;

					pathToInput 	= args[1];
				} else {								//si tenemos dos: input y output
					withOutputFile 	= true;

					pathToInput 	= args[0];
					pathToOutput 	= args[1];
				}
			} else {									//si son 3 argumentos (o mas...) : traza, input y output
				withTrace 			= true;
				withOutputFile 		= true;

				pathToInput 		= args[1];
				pathToOutput	 	= args[2];
			}
		}
	}

	private static Grafo getGrafo(MatrixNode[][] matrix, int nlines, int ncolumns) {
		Grafo graph = new Grafo();		

		class Mover {
			public Vertice toN(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line-1) + "," + String.valueOf(column) + "]", matrix[line-1][column].getCost());
			}
			public Vertice toS(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line+1) + "," + String.valueOf(column) + "]", matrix[line+1][column].getCost());
			}
			public Vertice toE(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line) + "," + String.valueOf(column+1) + "]", matrix[line][column+1].getCost());
			}
			public Vertice toW(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line) + "," + String.valueOf(column-1) + "]", matrix[line][column-1].getCost());
			}
			public Vertice toNE(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line-1) + "," + String.valueOf(column+1) + "]", matrix[line][column+1].getCost());
			}
			public Vertice toNW(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line-1) + "," + String.valueOf(column-1) + "]", matrix[line-1][column-1].getCost());
			}
			public Vertice toSE(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line+1) + "," + String.valueOf(column+1) + "]", matrix[line+1][column+1].getCost());
			}
			public Vertice toSW(MatrixNode[][] matrix, int line, int column) {
				return new Vertice("[" + String.valueOf(line+1) + "," + String.valueOf(column-1) + "]", matrix[line+1][column-1].getCost());
			}
		}
		
		Mover move = new Mover();
		
		for (int line = 0; line < nlines; line++) {
            for (int column = 0; column < ncolumns; column++) {
				String nombre = "[" + String.valueOf(line) + "," + String.valueOf(column) + "]";

                if (line == 0) { //*** si es la primera line ****
                    if (column == 0) { //si es la primera column 
						graph.anadirVertice(nombre, Arrays.asList(move.toE(matrix, line, column), move.toSE(matrix, line, column), move.toS(matrix, line, column)));
                    } else if (column == ncolumns - 1) { //si es la ultima column
						graph.anadirVertice(nombre, Arrays.asList(move.toW(matrix, line, column), move.toSW(matrix, line, column), move.toS(matrix, line, column)));
					} else { //si es una casilla de en medio
						graph.anadirVertice(nombre, Arrays.asList(move.toS(matrix, line, column), move.toSE(matrix, line, column), move.toSW(matrix, line, column), move.toW(matrix, line, column), move.toE(matrix, line, column)));
					}
				} else {
                    if (line == nlines - 1) { // *** si es la ultima line ***
						if (column == 0) { //si es la primera column
							graph.anadirVertice(nombre, Arrays.asList(move.toN(matrix, line, column), move.toE(matrix, line, column), move.toNE(matrix, line, column)));
						} else if (column == ncolumns - 1) { //si es la ultima column
							graph.anadirVertice(nombre, Arrays.asList(move.toN(matrix, line, column), move.toNW(matrix, line, column), move.toW(matrix, line, column)));
						} else { //si es una casilla de en medio
							graph.anadirVertice(nombre, Arrays.asList(move.toE(matrix, line, column), move.toN(matrix, line, column), move.toNE(matrix, line, column), move.toNW(matrix, line, column), move.toNW(matrix, line, column)));
						}
					} else { // *** si es una fila de en medio ***
						if (column == 0) { //si es la primera column 
							graph.anadirVertice(nombre, Arrays.asList(move.toE(matrix, line, column), move.toN(matrix, line, column), move.toNE(matrix, line, column), move.toSE(matrix, line, column), move.toS(matrix, line, column)));
						} else if (column == ncolumns - 1) { //si es la ultima column
							graph.anadirVertice(nombre, Arrays.asList(move.toNW(matrix, line, column), move.toN(matrix, line, column), move.toS(matrix, line, column), move.toSW(matrix, line, column), move.toW(matrix, line, column)));
						} else { //si es una casilla de en medio
							graph.anadirVertice(nombre, Arrays.asList(move.toE(matrix, line, column), move.toN(matrix, line, column), move.toNE(matrix, line, column), move.toNW(matrix, line, column), move.toS(matrix, line, column), move.toSE(matrix, line, column), move.toSW(matrix, line, column), move.toW(matrix, line, column)));
						}
					}
				}
			}
		} return graph;
	}

	private static int getCost(List<String> path, MatrixNode[][] matrix ) {
		int cost = 0;

		for(String node : path) {
			int i = Integer.parseInt(Character.toString((node.charAt(1))));
			int j = Integer.parseInt(Character.toString((node.charAt(3))));			
			
			cost = cost + matrix[i][j].getCost();
		} return cost;
	}

	private static String preparePath(String robot, List<String> path) {
		String preparedPath = robot;

		for(int i = path.size() - 1; i >= 0; i--) {
			preparedPath = preparedPath + "," + path.get(i);
		} return preparedPath = "\n" + preparedPath + "\n";
	}
}