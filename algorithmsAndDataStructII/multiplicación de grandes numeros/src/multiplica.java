import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class multiplica {
    static boolean traza         = false,
                   archivo       = false;
    static int     iteracion     = 0;
    static String  mensajesTraza = "";

    private static String anadirCeros(String numero, int n) {
        for(int i = 1; i <= n; i++) {
            numero = "0" + numero;
        }
        return numero;
    }

    private static String removerCeros(String numero) {
        while(numero.startsWith("0")) {
            numero = numero.substring(1);
        }
        return numero;
    }

    private static String potencia(String numero, int n) {
        for(int i = 1; i <= n; i++) {
            numero += "0";
        }
        return numero;
    }

    private static String resta(String numero1, String numero2) {
        String resultado = new String("");
        char acarreo = '0';

        char[] numero1Array = new String(numero1).toCharArray(),  //convertimos ambas cadenas a arrays
               numero2Array = new String(numero2).toCharArray();

        if ( !(numero1Array.length == numero2Array.length)) {   //anadimos ceros al menor de los numeros
            if (numero1Array.length - numero2Array.length < 0) {
                numero1Array = anadirCeros(numero1,-numero1Array.length-numero2Array.length).toCharArray();
            } else {
                numero2Array = anadirCeros(numero2, numero1Array.length-numero2Array.length).toCharArray();
            }
        }

        for(int i=numero1Array.length-1; i>=0; i=i-1) {  //RESTA ITERATIVA
            int bx = (int)numero1Array[i] - (int)acarreo,
                by = (int)numero2Array[i] - (int)'0',
                tv = bx - by;

            if (tv<0) {
                acarreo = '1'; tv += 10;
            } else {
                acarreo = '0';
            }

            resultado = (char) ( tv+(int)'0')+resultado;
        }
        return resultado;
    }

    private static String suma(String numero1, String numero2) {
        char[] numero1Array = new String(numero1).toCharArray(),
               numero2Array = new String(numero2).toCharArray();

        String resultado    = new String("");
        char   acarreo      = '0';

        if (!(numero1Array.length == numero2Array.length)) {
            int diferencia = numero1Array.length - numero2Array.length;

            if (diferencia<0) {
                numero1Array = anadirCeros(numero1,-diferencia).toCharArray();
            } else {
                numero2Array = anadirCeros(numero2,diferencia).toCharArray();
            }
        }

        for(int i=numero1Array.length-1; i>=0; i=i-1) {
            int tv = (int)numero1Array[i] - (int)'0' + (int)numero2Array[i]-(int)'0' + (int)acarreo - (int)'0';

            if (tv>9) {acarreo = '1'; tv -= 10;}
            else acarreo = '0';

            resultado = (char) (tv + (int)'0') + resultado;
        }

        if (acarreo == '1') resultado = "1" + resultado;

        return resultado;
    }

    private static String multiply(String numero1, String numero2) {          //funcion con el algoritmo de Karatsuba
        char[] numero1Array = new String(numero1).toCharArray(),     //convertimos los numeros en arrays de caracteres
               numero2Array = new String(numero2).toCharArray();

        int diferencia = numero1Array.length - numero2Array.length,
            longitud;

        String resultado = new String();

        if ( !(numero1Array.length == numero2Array.length) ) {    //igualamos longitudes antes de calcular
            if (diferencia < 0) {
                numero1Array = anadirCeros(numero1, diferencia).toCharArray();
            } else {
                numero2Array = anadirCeros(numero2, diferencia).toCharArray();
            }
        }

        if (numero1Array.length <= 4) {  //si el numero tiene menos de 4 digitos...
            resultado = String.valueOf(Integer.parseInt(String.valueOf(numero1Array)) * Integer.parseInt(String.valueOf(numero2Array)) );
            return resultado;

        } else {     //si tiene mas...
            if (numero1Array.length%2==1) {
                numero1Array = anadirCeros(String.valueOf(numero1Array),1).toCharArray();  //añadimos ceros
                numero2Array = anadirCeros(String.valueOf(numero2Array),1).toCharArray();
            }

            longitud = numero1Array.length;

            //////////////////////////////////////////////////////////////////
            //IMPREMENTACIÓN DEL ALGORITMO DE LA MULTIPLICACION DE KARATSUBA//
            //////////////////////////////////////////////////////////////////
            String mostSigDigX   = new String((String.valueOf(numero1Array)).substring(0, longitud/2)),  //FASE DE DESCOMPOSICION
                   leastSigDigX  = new String((String.valueOf(numero1Array)).substring(longitud/2, longitud)),
                   mostSigDigY   = new String((String.valueOf(numero2Array)).substring(0, longitud/2)),
                   leastSigDigY  = new String((String.valueOf(numero2Array)).substring(longitud/2, longitud)),

                   a_mas_b       = new String(suma(mostSigDigX, leastSigDigX)),      //sumas
                   c_mas_d       = new String(suma(mostSigDigY, leastSigDigY)),

                   primerTermino = new String(multiply(mostSigDigX, mostSigDigY)),    //FASE DE RESOLUCIÓN RECURSIVA
                   segundoTermino= new String(multiply(leastSigDigX, leastSigDigY)),
                   tercerTermino = new String(multiply(a_mas_b, c_mas_d)),

                   u_mas_v       = new String(suma(primerTermino, segundoTermino)),   //FASE DE COMBINACION:
                   w_menos_uv    = new String(resta(tercerTermino, u_mas_v)),         //primerTermino*10^n + (tercerTermino-(primerTermino+segundoTermino))*10^(n/2) + segundoTermino
                   resultado1    = new String(potencia(primerTermino, longitud)),     //(a*10^(n/2)+b)*(c*10^(n/2)+d) + x*y
                   resultado2    = new String(suma(resultado1, potencia(w_menos_uv, longitud/2))),
                   resultado3    = new String(suma(resultado2, segundoTermino));

            if (traza) {
                String formula = primerTermino + " * 10^n (" + tercerTermino + " - (" + primerTermino + " + " + segundoTermino + ")) * 10^(n/2) + " + segundoTermino,
                       lineas = "";

                iteracion++;

                for (int i = 0; i < formula.length(); i++) {
                    lineas += "-";
                }

                mensajesTraza += "\n--------------------\nIteración numero: " + iteracion + "\n" + lineas + "\n" + formula + "\n" + lineas;
            }

        return removerCeros(resultado3);
        }
    }

    public static void main(String[] args){ //método principal
        String  num1 = "", num2 = "", lineas = "",
                resultado, line1, line2,
                entrada = null, salida = null;

        boolean help = false;

        if(args[0].equals("-h")){ //tag de ayuda
            help = true;

            System.out.println("------------------------------------------------------");
            System.out.println("SINTAXIS:");
            System.out.println("multiplica [-t][-h] [fichero_entrada] [fichero_salida]");
            System.out.println("-t Traza");
            System.out.println("-h Muestra esta ayuda");
            System.out.println("fichero_salida Nombre del fichero de salida");
            System.out.println("------------------------------------------------------");

        } else if (args[0].equals("-t")){ //tag de traza
            traza   = true;
            entrada = args[1];

            if (args.length > 2) salida = args[2]; archivo = true;

        } else if (!(args[0].equals("-t")) && args.length < 2) {
            traza   = false;
            entrada = args[0];

        } else if (!(args[0].equals("-t")) && (args.length == 2)) {
            traza   = false;
            archivo = true;

            entrada = args[0];
            salida  = args[1];
        }

        if(help != true){  //Preparamos la lectura del archivo de entrada
            try {
          		  FileReader     fileReader      = new FileReader(new File(entrada));
          		  BufferedReader bufferedReader  = new BufferedReader(fileReader);

                while((line1 = bufferedReader.readLine()) != null) {
                    num1 = line1.replaceAll("\\s+","");
                    break;
                }

                while((line2 = bufferedReader.readLine()) != null){
                    if(line2.replaceAll("\\s+","").equals("")){
                        continue;
                    }

                    if(!(line2.equals(line1))) {
                        num2 = line2.replaceAll("\\s+","");
                        break;
                    }
                }

                fileReader.close();

                resultado = multiply(num1, num2);       //resultado final

                if(args.length == 3 && traza){          //salida via archivo de texto
                    PrintWriter writer = new PrintWriter(salida, "UTF-8");
                    writer.println("Resultado: " + resultado + "\n\nTraza de la aplicación. Ecuaciones iterativas: \n" + mensajesTraza);
                    writer.close();

                } else if (args.length == 2 && !traza){
                    PrintWriter writer = new PrintWriter(salida, "UTF-8");
                    writer.println("----------------------------------------------------------------------------\nPrimer término: " + num1 + "\n" + "Segundo térmido: " + num2 + "\n----------\n" + "Resultado: " + resultado + "\n----------");
                    writer.close();

                } else if (args.length == 1 && !traza){ // multiplica entrada
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Primer termino: "   + num1);
                    System.out.println("Segundo termino: "  + num2);
                    System.out.println("----------"         + lineas);
                    System.out.println("Resultado: "        + resultado);
                    System.out.println("----------"         + lineas);

                } else if (args.length == 2 && traza){  //multiplica -t entrada
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println("Primer termino: "   + num1);
                    System.out.println("Segundo termino: "  + num2);
                    System.out.println("----------"         + lineas);
                    System.out.println("Resultado: "        + resultado);
                    System.out.println("----------"         + lineas);
                    System.out.println(mensajesTraza);

                } else {
                    System.out.println("ERROR DE SINTAXIS. Consulte el uso de argumentos con el comando multiplica -h");
                }

          	} catch (IOException e) {
            		e.printStackTrace();
          	}
        }
    }
}
