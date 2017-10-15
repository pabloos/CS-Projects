/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import io.RMIInputStream;
import io.RMIInputStreamImpl;
import io.RMIOutputStream;
import io.RMIOutputStreamImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServicioDisco extends UnicastRemoteObject implements Servidor {
		public ServicioDisco() throws RemoteException {}

		public OutputStream getOutputStream(File f) throws IOException {
				System.out.println("Subido el archivo: " + f.getName());
		    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
		}

		public InputStream getInputStream(File f) throws IOException {
				System.out.println("Descargado el archivo: " + f.getName());
		    return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
		}

		public void borrar(String archivo, String usuario){
				try{
						Path path = Paths.get("clientes/" + usuario + "/" + archivo);
						Files.delete(path);
				} catch (IOException e) {
						System.err.println(e);
				}

				System.out.println("Eliminado el archivo: " + archivo);
		}

		public void mover(String archivo, String usuario) {
				Process p;

				try {
						p = Runtime.getRuntime().exec("cp " + archivo + " clientes" + "/" + usuario + "/");

						p.waitFor();
						p.destroy();
				} catch (Exception e) {}
		}

		public void crearCarpeta(String usuario){
				Process p;

				try {
						p = Runtime.getRuntime().exec("mkdir " + " clientes" + "/" + usuario);

						p.waitFor();
						p.destroy();
				} catch (Exception e) {}

				System.out.println("Creado el directorio del usuario: " + usuario);
		}

		public String listarFicheros(String usuario) {
				String s;
				String result = "";
				Process p;

				try {
						p = Runtime.getRuntime().exec("ls -la" + " clientes" + "/" + usuario);
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while ((s = br.readLine()) != null){
								result += s + "\n";
						}
						p.waitFor();
						p.destroy();
				} catch (Exception e) {}

				return result;
		}

		public String listarClientes() {
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
}
