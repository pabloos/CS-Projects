/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.io.*;

public class Repositorio{
		private static String listarFicheros(String cliente) {
				String s;
				String result = "";
				Process p;

				try {
						p = Runtime.getRuntime().exec("ls -la" + " clientes/" + cliente);
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

						while ((s = br.readLine()) != null){
								result += s + "\n";
						}

						p.waitFor();
						p.destroy();
				} catch (Exception e) {}

				return result;
		}

		private static String listarClientes() {
				String s;
				String result = "";
				Process p;

				try {
						p = Runtime.getRuntime().exec("ls ./clientes");
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

						while ((s = br.readLine()) != null){
								result += s + "\n";
						}

						p.waitFor();
						p.destroy();
				} catch (Exception e) {}

				return result;
		}

		public static void main(String args[]){
				Scanner scan = new Scanner(System.in);

				System.out.println("En primer lugar, introduce la dirección IP de esta máquina: ");
				String host = scan.nextLine();
				System.setProperty("java.rmi.server.hostname", host);

				int port = 3099;

				boolean seguir = true;
				boolean block = false;

				int opcion;

				while (seguir) {
						try {
								port = 3099;
						} catch (Exception e) {}

						System.out.println("---------------------------------------");
						System.out.println(">>>>>>>>>>>>BIENVENIDO A MYCLOUD SERVER");
						System.out.println("---------------------------------------");
						System.out.println("Elige una de las siguientes opciones: ");
						System.out.println("1) listar ficheros\n2) listar clientes\n3) iniciar repositorio\n4) salir\n");
						System.out.println("Introduzca su opción: ");

						opcion = Integer.parseInt(scan.nextLine());

						switch (opcion) {
								case 1:
										System.out.println("Introduzca el usuario para el que se consultan sus archivos: ");

										String usuario = scan.nextLine();

										System.out.println("Lista de ficheros: \n--------------------");
										System.out.println(listarFicheros(usuario));

										break;

								case 2:
										System.out.println("Lista de clientes: \n--------------------");
										System.out.println(listarClientes());

										break;

								case 3:
										if(!block){
												try {
														System.out.println("Iniciando respositorio...");
														boolean useSecurityManager = false;

														Registry registry = LocateRegistry.createRegistry(port);

														ServicioDisco obj = new ServicioDisco();

														registry.rebind("Server", obj);

														System.out.println("Repositorio activo en el puerto: " + port);

														block = true;

												} catch (Exception e){
														System.out.println("Error de repositorio err: " + e.getMessage());
														e.printStackTrace();
												}
										} else {
												System.out.println("--------------------------------------------------------");
												System.out.println("--------------------------------------------------------");
												System.out.println("El servidor ya está iniciado");
												System.out.println("--------------------------------------------------------");
												System.out.println("--------------------------------------------------------\n\n");
										}

										break;

								case 4:
										seguir = false;
										System.exit(0);

								default:
										System.out.println("ERROR: Ejecute de nuevo el Repositorio y escoja una opcion vallida");
										break;
						}
				}
    }
}
