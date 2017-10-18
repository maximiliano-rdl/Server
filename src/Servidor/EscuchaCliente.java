package Servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import Herramientas.IMensajes;

public class EscuchaCliente extends Thread {

	private final Socket socket;
	private final BufferedReader entrada;
	private final PrintWriter salida;
	private Integer numeroUser;
	private Usuario user;

	public EscuchaCliente(Integer numeroUser, Socket socket, BufferedReader d, DataOutputStream salida)
			throws IOException {
		this.socket = socket;
		this.entrada = d;
		this.salida = new PrintWriter(salida, true);
		this.numeroUser = numeroUser;
		this.user = Server.getInfoDeClientes().get(numeroUser);
	}

	public void run() {
		try {

			String cadenaLeida = entrada.readLine();
			String[] partesCadena = cadenaLeida.split("\\s+");

			while (partesCadena[0] != IMensajes.FINALIZAR) /* no DESCONECTAR */
			{
				
				if(cadenaLeida.charAt(0) == IMensajes.DESTINO_PUNTUAL.charAt(0)) {
					for (EscuchaCliente clienteAjeno : Server.getClientesConectados().values()) {
						String receptor = partesCadena[0].substring(1);
						if(clienteAjeno.user.getNick().equals(receptor) && !(receptor.equals(user.getNick())));
						{
							clienteAjeno.salida.println("(whisp)"+user.getNick() + ": " + cadenaLeida.substring(partesCadena[0].length()).trim());
						}
					}
				}else {
				
					for (EscuchaCliente clienteAjeno : Server.getClientesConectados().values()) {
						clienteAjeno.salida.println(user.getNick() + ": " + cadenaLeida);
					}
				}
				cadenaLeida = entrada.readLine();
				partesCadena = cadenaLeida.split("\\s+");
			}
			salida.println(IMensajes.FIN_CONEXION);

			entrada.close();
			salida.close();
			socket.close();

			Server.getClientesConectados().remove(numeroUser);
			Server.getInfoDeClientes().remove(numeroUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}