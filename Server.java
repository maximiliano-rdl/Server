package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

	private static ArrayList<EscuchaCliente> clientesConectados = new ArrayList<>();
	private static Map<Integer, Usuario> nicksDeClientes = new HashMap();

	private static Thread server;
	private final int PUERTO = 1234;
	private static ServerSocket serverSocket;

	public void main(String[] args) {
		server = new Thread(new Server());
		server.start();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(PUERTO);
			String ipRemota;
			String nombre,
			mensajeA,
			nick,
			mensajeATodos;
			
			while (true) {
				Socket cliente = serverSocket.accept();
				ipRemota = cliente.getInetAddress().getHostAddress();
				
				DataInputStream d = new DataInputStream(cliente.getInputStream());
				DataOutputStream e = new DataOutputStream(cliente.getOutputStream());
				
				String mensaje = d.readUTF();
				
				String[] m = mensaje.split(" ");
				
				if(m[0]=="Conexion")
				{
					nick = mensaje.substring(9);
					
				}else
				{
					if(mensaje.charAt(0)=='@')
					{
						nombre =  m[0];
						mensajeA = mensaje.substring(nombre.length()+1);
					}else
						{
							mensajeATodos = mensaje;
						}
	
				EscuchaCliente atencion = new EscuchaCliente(ipRemota, cliente, d, e);
				atencion.start();
				clientesConectados.add(atencion);
			}
		}
	}
}
