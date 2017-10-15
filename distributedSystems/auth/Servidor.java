/*
Autor: Pablo Cumpian Diaz
correo: pcumpian1@gmail.com
*/


import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

public interface Servidor extends java.rmi.Remote {
		void registrarUsuario(String user, String pass, String ip) throws RemoteException;

		void crearCarpeta(String usuario) throws IOException;

		Map devolverUsersIP() throws RemoteException;

		String loginUsuario(String usuario, String password) throws RemoteException;

		OutputStream getOutputStream(File f) throws IOException;

		InputStream getInputStream(File f) throws IOException;
}
