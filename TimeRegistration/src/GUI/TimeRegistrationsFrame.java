package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Element;

import Database.Database;

public class TimeRegistrationsFrame extends JFrame{
	
	final short width = 600;
	final short height = 800;
	JButton closeBtn, minimizeBtn;
	JLabel title,border,programmedBy,iconsFrom;
	JTextField membername;
	JPanel jp = new JPanel(null);
	Database database = new Database();
	private java.awt.Point initialClick;
	
	public TimeRegistrationsFrame() {
		
		//Layout
		setSize(width, height);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		
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
		
	
		title = new JLabel("Alle Zeiterfassungen");
		title.setBounds((width-350)/2,0,350,32);
		title.setFont(new Font("Calibri",Font.PLAIN,22));
		title.setForeground(Color.WHITE);
		
		border = new JLabel("_____________________________________________________________________________________________________________________________");
		border.setBounds(0,12,width+100,32);
		border.setHorizontalAlignment(JLabel.CENTER);
		border.setFont(new Font("Calibri",Font.PLAIN,22));
		border.setForeground(Color.WHITE);
		
		membername = new JTextField("");
		membername.setBounds(200,1000,100,32);
					  
		String [] columnNames = {"Datum", "Mitarbeiter", "Projekt", "Start", "Ende", "Pause", "Beschreibung" };
		
		Object[][] data = database.fetchAllTimeRegistrationEntries();
		
		JTable table = new JTable(data,columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.disable();
		scrollPane.setBounds(centerObject(500), 100, 500, 500);
	
		
		//Adding Elements
		jp.add(closeBtn);
		jp.add(minimizeBtn);
		jp.add(title);
		jp.add(border);
		jp.add(scrollPane);
		jp.add(membername);
		add(jp);
		setPreferredSize(new Dimension(600,800));
		pack();
		setVisible(true);
	
		jp.setBackground(new java.awt.Color(224, 74, 74));	
		
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
	   
	   private int centerObject(int objectWidth) {
		   int position = (width - objectWidth) / 2;
		   return position;
	   }
}
