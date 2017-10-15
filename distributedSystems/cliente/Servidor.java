/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

public interface Servidor extends java.rmi.Remote {
		String loginUsuario(String usuario, String password) throws RemoteException;

		Map devolverUsersIP() throws RemoteException;

		void crearCarpeta(String usuario) throws IOException;

		void registrarUsuario(String user, String pass, String ip) throws RemoteException;

		String listarClientes() throws RemoteException;

		String listarFicheros(String usuario) throws RemoteException;

		void borrar(String file, String usuario) throws IOException;

		void mover(String archivo, String usuario) throws IOException;

		OutputStream getOutputStream(File f) throws IOException;

		InputStream getInputStream(File f) throws IOException;
}
