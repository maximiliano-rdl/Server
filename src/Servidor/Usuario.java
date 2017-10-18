package Servidor;


public class Usuario {
	private String nick;
	private Integer ip;
	private Integer idUsuario;
	
	public Usuario(String nick, Integer ip, Integer idUsuario) {
		super();
		this.nick = nick;
		this.ip = ip;
		this.setIdUsuario(idUsuario);
	}
	
	public Usuario(Integer ip)
	{
		this.ip = ip;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getIp() {
		return ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
