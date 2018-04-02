import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class Reader {
    static int nLineas, nColumnas;
    static String robot, exit;
    static MatrixNode[][] matrix;

    public static void getMatrix(String pathToInput) {
        try { 
            BufferedReader buffer = new BufferedReader(new FileReader(pathToInput));

            nLineas     = Integer.parseInt(buffer.readLine());
            nColumnas   = Integer.parseInt(buffer.readLine());

            matrix      = new MatrixNode[nLineas][nColumnas];            

            for (int i = 0; i < nLineas; i++) {
                for (int j = 0; j < nColumnas; j++) {
                    String readed = buffer.readLine();
                    
                    if (readed.matches("S")) {
                        exit = "[" + i + "," + j + "]";                        
                        matrix[i][j] = new MatrixNode(i, j, 0);
                    } else if (readed.matches("R")) {    
                        robot = "[" + i + "," + j + "]";                        
                        matrix[i][j] = new MatrixNode(i, j, Integer.MAX_VALUE-10);
                    } else if (readed.matches("O")){
                        matrix[i][j] = new MatrixNode(i, j, Integer.MAX_VALUE-5);
                    } else {
                        matrix[i][j] = new MatrixNode(i, j, Integer.parseInt(readed));
                    }
                }
            } buffer.close();            
        } catch (IOException error) {
            System.out.println("Error al procesar el archivo y construir la matriz");
		}
	}
	
	public static String imprimirMatriz(MatrixNode[][] matrix) {
        String traza = "\n==================\nMatriz desglosada:\n==================\n\n";

		for (int i = 0; i < nLineas; i++) {
			for (int j = 0; j < nColumnas; j++) {
                String cost;
                int realCost = matrix[i][j].getCost();

                cost = (realCost < Integer.MAX_VALUE - 10) ? Integer.toString(realCost) : "inf";
                
				traza += cost + "\t";
			} traza += "\n";	
		} return traza + "\n";
    }
}