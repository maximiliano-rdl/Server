package server;

public class Usuario {
	private String nick;
	private Integer ip;
	
	public Usuario(String nick, Integer ip) {
		super();
		this.nick = nick;
		this.ip = ip;
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
	
}
