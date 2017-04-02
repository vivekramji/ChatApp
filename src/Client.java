import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends JFrame {

	private JPanel contentPane;
    String name, address;
    int port;
    private JTextField txtInputmessage;
    private JTextArea txtHistory;
    
    private DatagramSocket socket;
    private InetAddress ip;
    
    private Thread send;
    
	public Client(String name, String address, int port) {
		this.name = name;
		this.address= address;
		this.port = port;
		boolean connect = openConnection(address);
		if(!connect)
		{
			System.err.println("Connection failed");
			console("Connection failed");
			return;
		}
		createWindow();
		console("Attemting a connection to " + address + " port: " + port + " user: " + name);
		String connection = "/c/"+name;
		send(connection.getBytes());
	}
	
	private boolean openConnection(String address)
	{
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String receive()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}
	
	private void send(final byte[] data)
	{
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
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
  
	public void createWindow()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{28, 815, 30, 7};
		gbl_contentPane.rowHeights = new int[]{35, 475, 40};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtHistory = new JTextArea();
		txtHistory.setEditable(false);
		GridBagConstraints gbc_txtHistory = new GridBagConstraints();
		gbc_txtHistory.insets = new Insets(0, 0, 5, 5);
		gbc_txtHistory.fill = GridBagConstraints.BOTH;
		gbc_txtHistory.gridx = 1;
		gbc_txtHistory.gridy = 1;
		gbc_txtHistory.gridwidth = 2;
		gbc_txtHistory.insets = new Insets(0, 5, 0, 0);
		contentPane.add(txtHistory, gbc_txtHistory);
		
		txtInputmessage = new JTextField();
		txtInputmessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					appendToHistory(txtInputmessage.getText());
				}
			}
		});
		GridBagConstraints gbc_txtInputmessage = new GridBagConstraints();
		gbc_txtInputmessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtInputmessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtInputmessage.gridx = 1;
		gbc_txtInputmessage.gridy = 2;
		contentPane.add(txtInputmessage, gbc_txtInputmessage);
		txtInputmessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToHistory(txtInputmessage.getText());
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		setVisible(true);
		txtInputmessage.requestFocusInWindow();
	}
	
	public void appendToHistory(String message)
	{
		if(message.equals("")) return; //TODO append date and time
		message = name +": "+message;
		console(message);
		message = "/m/"+message;
		send(message.getBytes());
		txtInputmessage.setText("");
	}
	
	
	public void console(String message)
	{
		txtHistory.append(message + "\n\r");
	}
}
