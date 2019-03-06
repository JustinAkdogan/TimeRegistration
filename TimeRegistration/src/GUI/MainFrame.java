package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import Database.Database;

public class MainFrame extends JFrame {
	JButton closeBtn, minimizeBtn, post, lastDay, nextDay, timeRegistrationTable;
	JTextField project, start, end, pause, description;
	JLabel title,border,project_title, start_title, 
	end_title,postedtimes_title, 
	pause_title, description_title;
	JPanel jp = new JPanel();
	int width = 600;
	int height = 800;
	JScrollPane scrollPane = new JScrollPane();
	private java.awt.Point initialClick;
	
	
	String userid = System.getProperty("user.name");
	
    Database database = new Database();
    
    String[][] data = database.fetchAllTimeRegistrationsFromToday();
    
    String memberName = database.fetchForAndLastname();
    

    
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public MainFrame() {
					
		int chartPanelWidth = 490;
		
		database.fetchAllTimeRegistrationsFromTheWeek();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		float [] weekHours = database.fetchAllTimeRegistrationsFromTheWeek();
		dataset.setValue(weekHours[0], "", "Montag");
		dataset.setValue(weekHours[1], "", "Dienstag");
		dataset.setValue(weekHours[2], "", "Mittwoch");
		dataset.setValue(weekHours[3], "", "Donnerstag");
		dataset.setValue(weekHours[4], "", "Freitag");
		
		
		JFreeChart chart = ChartFactory.createBarChart3D("Offene Arbeitszeiten","","", dataset, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot catplot = chart.getCategoryPlot();
		BarRenderer barRenderer = (BarRenderer) catplot.getRenderer();
		barRenderer.setBaseCreateEntities(true);
		barRenderer.setSeriesPaint(0, Color.RED);
		barRenderer.setBaseFillPaint(Color.YELLOW);
		barRenderer.setAutoPopulateSeriesFillPaint(true);
		barRenderer.setBasePaint(Color.YELLOW);
		barRenderer.setSeriesFillPaint(0, Color.YELLOW);
		barRenderer.setBaseOutlinePaint(Color.YELLOW);
		barRenderer.setItemMargin(0.1);
		barRenderer.setBase(8);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		
		//chartPanel.setForeground(Color.RED);
		chartPanel.setBounds((width-chartPanelWidth)/2, 600, chartPanelWidth, 200);
		
		
		//Layout
		setSize(width, height);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		project = new JTextField();
		project.setBounds(55, 100, 80, 32);
		project.setBorder(null);
		
		project_title = new JLabel("Projekt Nr.");
		project_title.setBounds(55,60,126,32);
		
		start = new JTextField();
		start.setBounds(155, 100, 40, 32);
		start.setBorder(null);
		
		start_title = new JLabel("Startzeit");
		start_title.setBounds(155,60,126,32);
		
		end = new JTextField();
		end.setBounds(215, 100, 40, 32);
		end.setBorder(null);
		
		end_title = new JLabel("Endzeit");
		end_title.setBounds(215,60,126,32);
		
		pause = new JTextField();
		pause.setBounds(275, 100, 40, 32);
		pause.setBorder(null);
		
		pause_title = new JLabel("Pause");
		pause_title.setBounds(275,60,126,32);
		
		description = new JTextField();
		description.setBounds(335, 100, 210, 32);
		description.setBorder(null);
		
		description_title = new JLabel("Beschreibung");
		description_title.setBounds(335,60,126,32);
		
		post = new JButton();
		post.setBounds((width-100)/2, 150, 100, 32);
		post.setText("Buchen");
		//post.setBorderPainted(false);
		//post.setBorder(null);
		//post.setContentAreaFilled(false);
		//post.setIcon(new ImageIcon("res/close_operation.png"));
		Date date = new Date();
		postedtimes_title = new JLabel("Gebuchte Zeiten: " + sdf.format(date)); //#TODO
		postedtimes_title.setBounds((width-200)/2,200,200,32);
		
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
		
	
		title = new JLabel("Zeiterfassung: " + memberName);
		title.setBounds((width-350)/2,0,350,32);
		title.setFont(new Font("Calibri",Font.PLAIN,22));
		title.setForeground(Color.WHITE);
		
		border = new JLabel("_____________________________________________________________________________________________________________________________");
		border.setBounds(0,12,width+100,32);
		border.setHorizontalAlignment(JLabel.CENTER);
		border.setFont(new Font("Calibri",Font.PLAIN,22));
		border.setForeground(Color.WHITE);
		
		lastDay = new JButton();
		lastDay.setBounds(0, 280, 32, 32);
		lastDay.setBackground(new java.awt.Color(224, 74, 74,0));
		lastDay.setBorderPainted(false);
		lastDay.setBorder(null);
		lastDay.setContentAreaFilled(false);
		lastDay.setIcon(new ImageIcon("res/back.png"));
		
		nextDay = new JButton();
		nextDay.setBounds(568, 280, 32, 32);
		nextDay.setBackground(new java.awt.Color(224, 74, 74,0));
		nextDay.setBorderPainted(false);
		nextDay.setBorder(null);
		nextDay.setContentAreaFilled(false);
		nextDay.setIcon(new ImageIcon("res/next.png"));
		
		timeRegistrationTable = new JButton();
		timeRegistrationTable.setBounds(5, 0, 32, 32);
		timeRegistrationTable.setBackground(new java.awt.Color(224, 74, 74,0));
		timeRegistrationTable.setBorderPainted(false);
		timeRegistrationTable.setBorder(null);
		timeRegistrationTable.setContentAreaFilled(false);
		timeRegistrationTable.setIcon(new ImageIcon("res/list.png"));
		
		String[] table_title = {
				"A", "B", "C", "D","E"
		};
			
		JTable table = new JTable(data, table_title);
		table.setBounds((width-490)/2,250,490,320);
		table.disable();
		
		//JavaFX
		//Adding Elements
		add(closeBtn);
		add(minimizeBtn);
		add(title);
		add(border);
		add(project);
		add(project_title);
		add(start);
		add(start_title);
		add(end);
		add(end_title);
		add(pause);
		add(pause_title);
		add(description);
		add(description_title);
		add(post);
		add(postedtimes_title);
		add(timeRegistrationTable);
		add(table);
		add(chartPanel);
		add(lastDay);
		add(nextDay);
		add(jp);
		validate();
		
		changeSetupToAuthorization();
		
		//getContentPane().setBackground(new java.awt.Color(128, 230, 242));	
		jp.setBackground(new java.awt.Color(224, 74, 74));	
		

				
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
		
		post.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				database.insertTimeRegistration(getInput());
				restartGUI();
			}
		});
		
		timeRegistrationTable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TimeRegistrationsFrame timeRegistrationFrame = new TimeRegistrationsFrame();
			}
		});
		
		
		project.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				//char input = e.getKeyChar();
				//if((input < '0' || input > '9' && input != '\b')) {
					//e.consume();
					//changeSettings.changeSettings(delete_size_range.getText(),(byte) 8);
				//}
				//if (start.getText() == "") {
					start.setText(database.fetchLastEndTimeFromToday());
				//}
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
	
	public String [] getInput() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String [] values = new String [7];
		values[0] = userid;
		values[1] = project.getText();
		values[2] = start.getText();
		values[3] = end.getText();
		values[4] = pause.getText().replace(",", ".");
		values[5] = description.getText();
		values[6] = sdf.format(System.currentTimeMillis());
		return values;	
	}
	
	public void restartGUI() {
		MainFrame main = new MainFrame(); //#TODO Tabelle statt GUI aktualisieren
		main.setVisible(true);
		dispose();
	}
	
	public void changeSetupToAuthorization() {
		if (database.getAuthorizationLevel() < 1) {
			timeRegistrationTable.setEnabled(false);
			timeRegistrationTable.setVisible(false);
		}
	}
	
}

