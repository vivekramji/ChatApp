package server;

import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable{
	
	private List<ServerClient> clientsList = new ArrayList<ServerClient>();
	private int port;
	private DatagramSocket socket;
	private Thread run, manage, receive, send;
	private boolean running = false;
	
	Server(int port)
	{
		this.port = port;
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server");
		run.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		System.out.println("Server started on port " + port);
		manageClients();
		receive();
	}
	private void receive() {
		// TODO Auto-generated method stub
		receive = new Thread("Receive") {
			public void run(){
				while(running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String string = new String(packet.getData());
					process(packet);
				}
			}
		};
	   receive.start();	
	}
	
	private void process(DatagramPacket packet)
	{
		String text = new String(packet.getData());
		if(text.startsWith("/c/"))
		{
			clientsList.add(new ServerClient(text.substring(3, text.length()), packet.getAddress(), packet.getPort(), 50));
		}
		else if(text.startsWith("/m/"))
		{
			broadcastToAll(text);
		} else {
			System.out.println(text);
		}
	}
	
	private void broadcastToAll(String message)
	{
		for(ServerClient servC : clientsList)
		{
			broadcastToOne(message.getBytes(), servC.address, servC.port);
		}
	}
	
	private void broadcastToOne(final byte[] data, final InetAddress address, final int port)
	{
		send = new Thread("Send") {
		public void run() {
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  };
	  send.start();
	}
	private void manageClients() {
		// TODO Auto-generated method stub
		manage = new Thread("manage"){
		public void run(){
			while(running){
				
			}
		}
	};
	manage.start();
   }
}
