/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/

import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Auth{
    private static Map<String, String> user_pass;
    private static Map<String, String> user_ip;

    public static String registrarUsuario(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduzca su usuario: ");
        String user = scan.nextLine();
        System.out.println("Introduzca su contrasena: ");
        String pass = scan.nextLine();
        System.out.println("Introduzca su servidor: ");
        String ip = scan.nextLine();

        user_pass.put(user, pass);
        user_ip.put(user, ip);

        guardarUsuarios();

        return user;
    }

    public static void borrarUsuario(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce el usuario a eliminar: ");
        String user = scan.nextLine();

        user_pass.remove(user);
        user_ip.remove(user);

        guardarUsuarios();
    }

    public static void consultarUsuario(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce el usuario a consultar: ");
        String user = scan.nextLine();

        System.out.println("Datos del usuario: " + user);
        System.out.println("Contraseña: " + user_pass.get(user));
        System.out.println("Repositorio: " + user_ip.get(user));
    }

    public static void guardarUsuarios(){
        try {
          FileOutputStream fileUsers = new FileOutputStream("user_pass.ser");
          ObjectOutputStream out1 = new ObjectOutputStream(fileUsers);

          out1.writeObject(user_pass);
          out1.close();
          fileUsers.close();

          FileOutputStream fileServers = new FileOutputStream("user_ip.ser");
          ObjectOutputStream out2 = new ObjectOutputStream(fileServers);

          out2.writeObject(user_ip);
          out2.close();
          fileServers.close();

        }catch(IOException i) {
           i.printStackTrace();
        }
    }

    public static void cargarUsuarios(){
        try {
           FileInputStream fileIn1 = new FileInputStream("user_pass.ser");
           ObjectInputStream in1 = new ObjectInputStream(fileIn1);

           user_pass = (HashMap)in1.readObject();

           in1.close();
           fileIn1.close();

           FileInputStream fileIn2 = new FileInputStream("user_ip.ser");
           ObjectInputStream in2 = new ObjectInputStream(fileIn2);

           user_ip = (HashMap)in2.readObject();

           in2.close();
           fileIn2.close();

        }catch(IOException i) {
           i.printStackTrace();
           return;
        }catch(ClassNotFoundException c) {
           System.out.println("Maps objects not found");
           c.printStackTrace();
           return;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("En primer lugar, introduzca la dirección IP de esta máquina: ");
        String host = scan.nextLine();

	      System.setProperty("java.rmi.server.hostname", host);

        boolean seguir = true;
        boolean block = false;

        while(seguir){
            try{
            		cargarUsuarios();
            } catch (Exception e) {
            		System.out.println("LOS MAPEOS NO SE ESTAN CARGANDO");
            		e.printStackTrace();
            }

            System.out.println("---------------------------------------");
						System.out.println(">>>>>>>>>>>>BIENVENIDO A MYCLOUD SERVER");
						System.out.println("---------------------------------------");
            System.out.println("Elige una de las siguientes opciones: ");
            System.out.println("1) listar usuarios");
            System.out.println("2) listar repositorios");
            System.out.println("3) listar usuarios y su repositorio");
            System.out.println("4) registrar usuario");
            System.out.println("5) borrar usuario");
      		  System.out.println("6) consultar usuario");
      		  System.out.println("7) iniciar servidor");
            System.out.println("8) salir");
      	    System.out.println("Introduzca su opción: ");

            int opcion = Integer.parseInt(scan.nextLine());

            switch(opcion){
              	case 1:
                    System.out.println(user_pass.keySet());
                    break;

              	case 2:
                    System.out.println(user_ip.values());
                    break;

              	case 3:
                    System.out.println("----------------------------");
                    System.out.println("\nUsuario\t\tRepositorio");
                    System.out.println("----------------------------");

                    for(Map.Entry<String, String> entry : user_ip.entrySet()){
                        System.out.println(entry.getKey() + "\t\t" + entry.getValue());
                    }
                    System.out.println("");

                    break;

              	case 4:
                    cargarUsuarios();

                    String user = registrarUsuario();
                    Servidor server;

                    try{
                        boolean useSecurityManager = false;
                        server = (Servidor) Naming.lookup( "//" + user_ip.get(user) + ":" + 3099 + "/Server");
                        server.crearCarpeta(user);
                    } catch(Exception e) {
                        e.printStackTrace();
                        System.out.println("ERROR al cargar el repositorio");
                    }

                    break;

              	case 5:
                    borrarUsuario();
                    break;

              	case 6:
                    consultarUsuario();
                    break;

              	case 7:
                    if (!block) {
                        try {
    			                  cargarUsuarios();
                            boolean useSecurityManager = false;

                            if (useSecurityManager && System.getSecurityManager() == null) {
                                System.setSecurityManager(new SecurityManager());
                            }

                            Registry registry = LocateRegistry.createRegistry(2099);

                            ServicioAutenticacion obj = new ServicioAutenticacion();
                            registry.rebind("ServerAuth", obj);

                            System.out.println("Servidor arrancando");

                            block = true;
                        } catch (Exception e){
                            System.out.println("Error en servidor de autenticación: " + e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("--------------------------------------------------------");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("El servidor ya está iniciado");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("--------------------------------------------------------\n\n");
                    }

                    continue;

              	case 8:
              		  seguir = false;
                    System.exit(0);

                default:
              	    System.out.println("Seleccione una opción válida");

                    return;
          	}
        }
    }
}
