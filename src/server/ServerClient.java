package server;

import java.net.InetAddress;

public class ServerClient {
	String name;
	InetAddress address;
	int port;
	final int ID;
	int attempt = 0;
	
	ServerClient(String name, InetAddress address, int port, int id)
	{
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = id;
	}
	
	public int getId()
	{
		return ID;
	}
}
