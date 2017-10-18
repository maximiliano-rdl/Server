package Servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

	private static Map<Integer, EscuchaCliente> clientesConectados = new HashMap<Integer, EscuchaCliente>();
	private static Map<Integer, Usuario> infoDeClientes = new HashMap<Integer, Usuario>();

	private static Thread server;
	private final int PUERTO = 1234;
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		server = new Thread(new Server());
		server.start();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(PUERTO);
			String ipRemota;
			System.out.println("Iniciando server");
			Integer numeroUser = 0;
			
			while (true) {
				String nick = "";
				System.out.println("Esperando conexiones...");
				Socket cliente = serverSocket.accept();
				ipRemota = cliente.getInetAddress().getHostAddress();
				BufferedReader d = new BufferedReader(
						new InputStreamReader(cliente.getInputStream(), StandardCharsets.UTF_8));
				DataOutputStream e = new DataOutputStream(cliente.getOutputStream());

				System.out.println(ipRemota + " se ha conectado.");

				System.out.println("Esperado nick.");
				nick = d.readLine();

				Usuario user = new Usuario(nick, Integer.getInteger(ipRemota), numeroUser);
				getInfoDeClientes().put(numeroUser, user);

				System.out.println("Su nick es: " + nick);

				EscuchaCliente atencion = new EscuchaCliente(numeroUser, cliente, d, e);
				atencion.start();
				clientesConectados.put(numeroUser, atencion);
				
				numeroUser++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<Integer, EscuchaCliente> getClientesConectados() {
		return clientesConectados;
	}

	public static void setClientesConectados(Map<Integer, EscuchaCliente> clientesConectados) {
		Server.clientesConectados = clientesConectados;
	}

	public static void addCliente(Integer ip, EscuchaCliente cliente) {
		Server.clientesConectados.put(ip, cliente);
	}

	public static void removeCliente(Integer ip) {
		Server.clientesConectados.remove(ip);
	}

	public static Map<Integer, Usuario> getInfoDeClientes() {
		return infoDeClientes;
	}

	public static void setInfoDeClientes(Map<Integer, Usuario> infoDeClientes) {
		Server.infoDeClientes = infoDeClientes;
	}

}
