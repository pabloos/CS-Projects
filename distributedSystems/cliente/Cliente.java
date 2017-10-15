/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.registry.Registry;

public class Cliente{
    final public static int BUF_SIZE = 1024 * 64;

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[BUF_SIZE];
        int len;

        while ((len = in.read(b)) >= 0) {
            out.write(b, 0, len);
        }
        out.close();
    }

    public static void upload(Servidor server, File src, File dest) throws IOException {
        copy(new FileInputStream(src), server.getOutputStream(dest));
    }

    public static void download(Servidor server, File src, File dest) throws IOException {
        copy(server.getInputStream(src), new FileOutputStream(dest));
    }

    public static void eliminar(Servidor server, String archivo, String usuario) throws IOException {
        try{
            server.borrar(archivo, usuario);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String arg[]) {
        boolean useSecurityManager = false;

        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce la dirección del servidor de autenticación: ");
        String serverIP = scan.nextLine();
		    System.out.println("Iniciando conexion con el servidor: " + serverIP);

	      try{
		        Servidor auth = (Servidor) Naming.lookup( "//" + serverIP + ":" + 2099 + "/ServerAuth");

	          boolean seguir = true,
                    seguir2 = true;

            int opcion, opcion2;

	          while(seguir){
                System.out.println("---------------------------------------");
                System.out.println(">>>>>>>>>>>>>>>>>>>BIENVENIDO A MYCLOUD");
                System.out.println("---------------------------------------");
                System.out.println("Elige una de las siguientes opciones: ");
                System.out.println("1) Log in\n2) Registrar usuario\n3) Salir");
                System.out.println("---------------------------------------\n");
                System.out.println("Introduzca su opción: ");

	        	    opcion = Integer.parseInt(scan.nextLine());

			          Servidor server;

        	      switch (opcion) {
                    case 1:
                        try{
                            System.out.println("Introduzca su usuario: ");
                            String user = scan.nextLine();

                            System.out.println("Introduzca su contrasena: ");
                            String pass = scan.nextLine();

                            server = (Servidor) Naming.lookup( "//" + auth.loginUsuario(user,pass) + ":" + 3099 + "/Server");

                            while(seguir2){
                                System.out.println("--------------------------------------");
                                System.out.println("Elige una de las siguientes opciones: ");
                                System.out.println("1) subir archivo\n2) descargar archivo\n3) borrar archivo\n4) listar ficheros\n5) listar clientes\n6) salir");
                                System.out.println("--------------------------------------\n");
                                System.out.println("Introduzca su opción: ");

            	                  opcion2 = Integer.parseInt(scan.nextLine());

                                switch(opcion2){
                                    case 1:
                                        System.out.println("Introduce el nombre del archivo a subir: ");
                                        String srcFilename = scan.nextLine();

                                        if (srcFilename != null && srcFilename.length() > 0) {
                                            String destFilename = srcFilename;

                                            upload(server, new File(srcFilename), new File(destFilename));
                                            server.mover(srcFilename, user);
                                        }

                                        break;

                                    case 2:
                                        System.out.println("Introduce el nombre del archivo a descargar: ");
                                        String srcFilename1 = scan.nextLine();

                                        if (srcFilename1 != null && srcFilename1.length() > 0) {
                                            String destFilename = srcFilename1;

                                            download(server, new File(srcFilename1), new File(destFilename));
                                        }

                                        break;

                                    case 3:
                                        System.out.println("Introduce el nombre del archivo a eliminar: ");
                                        String archivo = scan.nextLine();

                                        eliminar(server, archivo, user);

                                        break;

                                    case 4:
                                        System.out.println("Lista de ficheros: \n--------------------");
                                        System.out.println(server.listarFicheros(user));

                                        break;

                                    case 5:
                                        System.out.println("Lista de clientes: \n--------------------");
                                        System.out.println(server.listarClientes());

                                        break;

                              	    case 6:
                              		      seguir2 = false;
                                        System.exit(0);
                                        
                              		      break;

                                    default:
                                        System.out.println("ERROR: Introduzca una opción válida");
                                        break;
                                }
                            }

                        } catch (Exception e){
                            System.out.println("Excepcion en el cliente: " + e.getMessage());
                            e.printStackTrace();
                        }

                        break;

                    case 2:
                        System.out.println("Introduzca su usuario: ");
                        String user1 = scan.nextLine();
                        System.out.println("Introduzca su contrasena: ");
                        String pass1 = scan.nextLine();
                        System.out.println("Introduzca su servidor: ");
                        String ip1 = scan.nextLine();

                  			try{
                  				  auth.registrarUsuario(user1, pass1, ip1);
                  			} catch (Exception e){
                  					e.printStackTrace();
                        		System.out.println("Error conectando con el servidor al registrar el usuario");
                        }

                        try{
                            server = (Servidor) Naming.lookup( "//" + ip1 + ":" + 3099 + "/Server");
                            server.crearCarpeta(user1);
                        } catch(Exception e) {}

                        break;

      	            case 3:
                  		  seguir = false;
                  		  break;

                    default:
                        System.out.println("ERROR: Ejecute de nuevo el programa e introduzca una opción válida");
                        break;
                  }
  	          }

    	  } catch (Exception e){
            System.out.println("Exception en el cliente: " + e.getMessage());
                e.printStackTrace();
        }
    }
}
