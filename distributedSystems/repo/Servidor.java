/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import java.io.*;
import java.rmi.RemoteException;

public interface Servidor extends java.rmi.Remote {
		String listarClientes() throws RemoteException;

		String listarFicheros(String usuario) throws RemoteException;

		void borrar(String file, String usuario) throws IOException;

		void mover(String archivo, String usuario) throws IOException;

		void crearCarpeta(String usuario) throws IOException;

		OutputStream getOutputStream(File f) throws IOException;

		InputStream getInputStream(File f) throws IOException;
}
