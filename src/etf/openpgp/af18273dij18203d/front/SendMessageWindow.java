package etf.openpgp.af18273dij18203d.front;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class SendMessageWindow extends JFrame {
	private static WelcomeWindow welcomeWindow;
	
	public SendMessageWindow() {
		welcomeWindow = WelcomeWindow.getInstance();
		init();
		setComponents();
	}
	
	public void init() {
		setVisible(true);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Send a message");
		setLayout(new GridLayout(3, 1));
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        welcomeWindow.setVisible(true);
		        dispose();
		    }
		});
	}
	
	public void setComponents() {
		
	}
}
