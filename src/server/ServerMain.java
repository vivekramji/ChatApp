package server;

public class ServerMain {
	int port;
	Server server;
	ServerMain(int port)
	{
		this.port = port;
		server = new Server(port);
	}

	public static void main(String[] args){
		if(args.length != 1)
		{
			return;
		}
		int port = Integer.parseInt(args[0]);
	    new ServerMain(port);
	}
}
