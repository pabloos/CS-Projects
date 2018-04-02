import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

abstract class Mochila {
    static boolean 	withTrace           = false,         //parametros
                    withpathToOutput 	= false;

    static String 	pathToInput, pathToOutput, traza = "", resultado = "";
    static int      nObjetos, valores[], pesos[], capacidad = 0;
   
    public static String mochila(int[] pesos, int[] valores, int capacidad, int numeroDeObjetos, boolean withTrace) {
        final int menos_infinito  = Integer.MIN_VALUE;

        int[][] m           = new int[numeroDeObjetos + 1][capacidad + 1];
        int[][] solucion    = new int[numeroDeObjetos + 1][capacidad + 1];

        String result = "";

        for (int i = 1; i <= numeroDeObjetos; i++) {
            for (int j = 0; j <= capacidad; j++) {
                int m1 = m[i - 1][j];
                int m2 = menos_infinito;
                
                traza += withTrace ? "Analizando nodo: " + Integer.toString(i) + "\nMirando si el nodo: " + Integer.toString(i) + " es menor de peso que: " + Integer.toString(m1) + "\n\n" : "";

                if (j >= pesos[i]) {
                    m2 = m[i - 1][j - pesos[i]] + valores[i];

                    traza += withTrace ? "Cierto\n" : "";
                }

                m[i][j] = Math.max(m1, m2);
                solucion[i][j] = m2 > m1 ? 1 : 0;
            }
        }        

        //SACAMOS EL VALOR MAXIMO DE LA MOCHILA
        int tabla[][] = new int[numeroDeObjetos+1][capacidad+1];
        
        for (int i = 0; i <= numeroDeObjetos; i++) {
            for (int w = 0; w <= capacidad; w++) {
                if      (i==0 || w==0) tabla[i][w] = 0;
                else if (pesos[i-1] <= w) tabla[i][w] = Math.max(valores[i-1] + tabla[i-1][w-pesos[i-1]], tabla[i-1][w]);
                else    tabla[i][w] = tabla[i-1][w];
            }
        }

        int[] selecciondo = new int[numeroDeObjetos + 1];  //hacemos un array de todos los objetos seleccionados
        
        for (int n = numeroDeObjetos, w = capacidad; n > 0; n--) {
            if (solucion[n][w] != 0) {
                selecciondo[n] = 1;
                w = w - pesos[n];
            } else {
                selecciondo[n] = 0;
            }
        }

        for (int i = 1; i < numeroDeObjetos + 1; i++) {
            if(selecciondo[i] == 1) {
                result += (selecciondo[i] == 1) ? Integer.toString(pesos[i]) + " " : "";
            }
        } return "\nObjetos de volumen: " + result + "\nBeneficio: " + tabla[numeroDeObjetos][capacidad];
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
					withpathToOutput = true;
					pathToInput     = args[0];
					pathToOutput    = args[1];
				}
			} else {									//si son 3 argumentos (o mas...) : traza, input y output
				withTrace 			= true;
				withpathToOutput 	= true;
				pathToInput 		= args[1];
				pathToOutput	 	= args[2];
			}
		}
    }
    
    private static void getParams() {
        try {
            BufferedReader buffer   = new BufferedReader(new FileReader(pathToInput));

            nObjetos    = Integer.parseInt(buffer.readLine());                      //extraemos en primer lugar el numero de objetos
            valores     = new int[nObjetos];
            pesos       = new int[nObjetos];            

            for(int i = 0; i < nObjetos; i++) {
                String[] firstline = buffer.readLine().trim().split("\\s+");        //leemos una linea
                
                pesos[i]    = Integer.parseInt(firstline[0]);                       //sacamos el primer numero
                valores[i]  = Integer.parseInt(firstline[1]);                       //sacamos el segundo numero
            }   capacidad   = Integer.parseInt(buffer.readLine());

            buffer.close();
        } catch (IOException error) {
            System.out.println("Error al leer el archivo");
		}   
    }

    private static void printToFile(String pathToOutput, String result){
        try {
            FileWriter writer = new FileWriter(new File(pathToOutput));
            writer.write(result);
            writer.flush(); writer.close();
        } catch(IOException error) {
            System.out.println("Error al abrir el archivo para escritura");
        }
    }

    public static void main(String args[]) {
        setArguments(args);
        getParams();

        int[] wt    = new int[valores.length+1];
        int[] val   = new int[valores.length+1];

        for (int i = 1; i <= valores.length; i++) wt[i]  = pesos[i-1];
        for (int i = 1; i <= valores.length; i++) val[i] = valores[i-1];

        resultado = mochila(wt, val, capacidad, val.length-1, withTrace);
        
        if  (withpathToOutput)  printToFile(pathToOutput, traza + resultado);
        else                    System.out.println(traza + resultado);            
    }
}