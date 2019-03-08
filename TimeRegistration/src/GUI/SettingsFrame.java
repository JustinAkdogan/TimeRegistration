package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Functions.General;
import Functions.Settings;

public class SettingsFrame extends JFrame {
	
	General general = new General();
	Settings settings = new Settings();
	
	final short width = 600;
	final short height = 400;
	private java.awt.Point initialClick;
	
	JButton closeBtn, minimizeBtn, submitSettings;
	JLabel title,border, connection_type_title,server_title, database_title, username_title, password_title;
	JTextField connection_type,server,database,username,password;
	JPanel jp = new JPanel();
	
	public SettingsFrame() {
		//Layout
		setSize(width, height);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		jp.setBackground(new java.awt.Color(224, 74, 74));	
		
		closeBtn = new JButton();
		closeBtn.setBounds(568, 0, 32, 32);
		closeBtn.setBorderPainted(false);
		closeBtn.setBorder(null);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setIcon(new ImageIcon("res/close_operation.png"));
		
		minimizeBtn = new JButton();
		minimizeBtn.setBounds(532, 0, 32, 32);
		minimizeBtn.setBorderPainted(false);
		minimizeBtn.setBorder(null);
		minimizeBtn.setContentAreaFilled(false);
		minimizeBtn.setIcon(new ImageIcon("res/minimize_operation.png"));
		
		title = new JLabel("SQL Configuration");
		title.setBounds(general.centerObject(190),0,190,32);
		title.setFont(new Font("Calibri",Font.PLAIN,22));
		title.setForeground(Color.WHITE);
		
		border = new JLabel("_____________________________________________________________________________________________________________________________");
		border.setBounds(0,12,width+100,32);
		border.setHorizontalAlignment(JLabel.CENTER);
		border.setFont(new Font("Calibri",Font.PLAIN,22));
		border.setForeground(Color.WHITE);
		
		connection_type_title = new JLabel("Connection Type");
		connection_type_title.setBounds(50,50,120,32);
		connection_type_title.setFont(new Font("Calibri",Font.PLAIN,14));
		connection_type_title.setForeground(Color.WHITE);
		
		database_title = new JLabel("Database");
		database_title.setBounds(170,50,120,32);
		database_title.setFont(new Font("Calibri",Font.PLAIN,14));
		database_title.setForeground(Color.WHITE);
		
		username_title = new JLabel("Username");
		username_title.setBounds(290,50,120,32);
		username_title.setFont(new Font("Calibri",Font.PLAIN,14));
		username_title.setForeground(Color.WHITE);
		
		password_title = new JLabel("Password");
		password_title.setBounds(410,50,120,32);
		password_title.setFont(new Font("Calibri",Font.PLAIN,14));
		password_title.setForeground(Color.WHITE);
		
		connection_type = new JTextField("");
		connection_type.setBounds(50,80,100,32);
		
		server = new JTextField("");
		server.setBounds(170,80,100,32);
		
		database = new JTextField("");
		database.setBounds(170,80,100,32);
		
		username = new JTextField("");
		username.setBounds(290,80,100,32);
		
		password = new JTextField("");
		password.setBounds(410,80,100,32);
		
		submitSettings = new JButton();
		submitSettings.setBounds(general.centerObject(32), 150, 32, 32);
		submitSettings.setBorderPainted(false);
		submitSettings.setBorder(null);
		submitSettings.setContentAreaFilled(false);
		submitSettings.setIcon(new ImageIcon("res/search.png"));
		
		add(minimizeBtn);
		add(closeBtn);
		add(title);
		add(border);
		add(connection_type_title);
		add(database_title);
		add(username_title);
		add(password_title);
		add(connection_type);
		add(server);
		add(database);
		add(username);
		add(password);
		add(submitSettings);
		add(jp);
		
		fillTextFields();
		
		//Trigger
		closeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		minimizeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setState(Frame.ICONIFIED);
			}
		});
		
		/* Moving the GUI */
		  addMouseListener(new MouseAdapter() {
		        public void mousePressed(MouseEvent e) {
		            initialClick = e.getPoint();
		            getComponentAt(initialClick);
		        }
		    });
		
		   addMouseMotionListener(new MouseMotionAdapter() {
		        @Override
		        public void mouseDragged(MouseEvent e) {

		            // get location of Window
		            int thisX = getLocation().x;
		            int thisY = getLocation().y;

		            // Determine how much the mouse moved since the initial click
		            int xMoved = e.getX() - initialClick.x;
		            int yMoved = e.getY() - initialClick.y;

		            // Move window to this position
		            int X = thisX + xMoved;
		            int Y = thisY + yMoved;
		            setLocation(X, Y);
		        }
		    });
	}
	
	private void fillTextFields() {
		String [] settingLines = settings.readAndGetSettings();
		connection_type.setText(settingLines[1]);
		database.setText(settingLines[2]);
		username.setText(settingLines[3]);
		password.setText(settingLines[4]);
	}
}
