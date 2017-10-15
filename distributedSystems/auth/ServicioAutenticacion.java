/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import io.RMIInputStream;
import io.RMIInputStreamImpl;
import io.RMIOutputStream;
import io.RMIOutputStreamImpl;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServicioAutenticacion extends UnicastRemoteObject implements Servidor {
		private static Map<String, String> user_pass;
		private static Map<String, String> user_ip;

		public ServicioAutenticacion() throws RemoteException {}

		public OutputStream getOutputStream(File f) throws IOException {
				System.out.println("Upload file: " + f.getName());
		    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
		}

		public InputStream getInputStream(File f) throws IOException {
				System.out.println("Download file: " + f.getName());
		    return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
		}

		public Map devolverUsersIP() throws RemoteException {
        return user_ip;
    };

    public void registrarUsuario(String user, String pass, String ip){
				cargarUsuarios();

				user_pass.put(user, pass);
				user_ip.put(user, ip);

        guardarUsuarios();
    }

	public static void cargarUsuarios(){                                        //cargar usuarios
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

        } catch(IOException i) {
           i.printStackTrace();
           return;
        } catch(ClassNotFoundException c) {
           System.out.println("Maps objects not found");
           c.printStackTrace();
           return;
        }
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

        } catch(IOException i) {
           i.printStackTrace();
        }
    }

		public String loginUsuario(String usuario, String password) throws RemoteException {
				String ip = "";

				try {
						FileInputStream fileIn1 = new FileInputStream("user_pass.ser");
						ObjectInputStream in1 = new ObjectInputStream(fileIn1);

						user_pass = (HashMap) in1.readObject();

						in1.close();
						fileIn1.close();

						FileInputStream fileIn2 = new FileInputStream("user_ip.ser");
						ObjectInputStream in2 = new ObjectInputStream(fileIn2);

						user_ip = (HashMap) in2.readObject();

						in2.close();
						fileIn2.close();

						if(password.equals(user_pass.get(usuario))){
								ip = user_ip.get(usuario);
						}

				} catch(IOException i) {
					 i.printStackTrace();
				} catch(ClassNotFoundException c) {
					 c.printStackTrace();
				}

				return ip;
		}

		public void crearCarpeta(String usuario) throws IOException{
				Process p;

				try {
						p = Runtime.getRuntime().exec("mkdir " + " clientes" + "/" + usuario);

						p.waitFor();
						p.destroy();
				} catch (Exception e) {}
		}
}
