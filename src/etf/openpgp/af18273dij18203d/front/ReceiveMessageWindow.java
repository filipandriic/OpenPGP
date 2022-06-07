package etf.openpgp.af18273dij18203d.front;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;

public class ReceiveMessageWindow extends JFrame {
	private static WelcomeWindow welcomeWindow;
	private File file;
    private File outputDirectory;

    private final JFileChooser fileChooser = new JFileChooser();

    
	public ReceiveMessageWindow() {
		welcomeWindow = WelcomeWindow.getInstance();
		init();
		setComponents();
	}
	
	@Override
	public void dispose() {
		super.dispose();
        welcomeWindow.setVisible(true);
	}
	
	public void init() {
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Receive/Verify files");
		setLayout(new GridLayout(3, 1));
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        dispose();
		    }
		});
	}
	
	public void setComponents() {
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(Box.createHorizontalGlue());
		JButton backButton = new JButton("Back");
		backButton.addActionListener((ev) -> {
	        dispose();
		});
		topPanel.add(backButton);
		
		JPanel filePanel = new JPanel();
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.PAGE_AXIS));
		
		panel.add(topPanel);
		add(panel);
	}
}
