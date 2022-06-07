package etf.openpgp.af18273dij18203d.front;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import etf.openpgp.af18273dij18203d.back.ManageKeysController;

public class ManageKeysWindow extends JFrame {
	private static WelcomeWindow welcomeWindow;
	
	public ManageKeysWindow() {
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
		setTitle("Manage keys");
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        dispose();
		    }
		});
	}
	
	public void setComponents() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
		JButton generateKeysButton = new JButton("Generate keys");
		generateKeysButton.addActionListener((ev) -> {
			new GenerateKeysWindow();
		});
		JButton backButton = new JButton("Back");
		backButton.addActionListener((ev) -> {
	        dispose();
		});
		topPanel.add(generateKeysButton);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(backButton);
		
		JPanel keyRingsPanel = new JPanel(new GridLayout(2, 1));
		//public keys
		JPanel publicKeysPanel =  new JPanel(new BorderLayout());
		publicKeysPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
		JTextArea publicKeysText = new JTextArea();
		for (String publicKeyRing : ManageKeysController.loadPublicKeyRingCollection()) {
			publicKeysText.append(publicKeyRing + "\n");
		}
		publicKeysText.setEditable(false);
		JPanel publicKeyButtonsPanel = new JPanel();
		JButton importPublicKeyButton = new JButton("Import");
		JButton exportPublicKeyButton = new JButton("Export");
		JButton deletePublicKeyButton = new JButton("Delete");
		publicKeyButtonsPanel.add(importPublicKeyButton);
		publicKeyButtonsPanel.add(exportPublicKeyButton);
		publicKeyButtonsPanel.add(deletePublicKeyButton);
		
		publicKeysPanel.add(new JLabel("Public key ring"), BorderLayout.NORTH);
		publicKeysPanel.add(publicKeysText, BorderLayout.CENTER);
		publicKeysPanel.add(publicKeyButtonsPanel, BorderLayout.SOUTH);
		
		keyRingsPanel.add(publicKeysPanel);
		
		//private keys
		JPanel secretKeysPanel =  new JPanel(new BorderLayout());
		secretKeysPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
		JTextArea secretKeysText = new JTextArea();
		for (String secretKeyRing : ManageKeysController.loadSecretKeyRingCollection()) {
			secretKeysText.append(secretKeyRing + "\n");
		}
		secretKeysText.setEditable(false);
		JPanel secretKeyButtonsPanel = new JPanel();
		JButton importSecretKeyButton = new JButton("Import");
		JButton exportSecretKeyButton = new JButton("Export");
		JButton deleteSecretKeyButton = new JButton("Delete");
		secretKeyButtonsPanel.add(importSecretKeyButton);
		secretKeyButtonsPanel.add(exportSecretKeyButton);
		secretKeyButtonsPanel.add(deleteSecretKeyButton);
		
		secretKeysPanel.add(new JLabel("Private key ring"), BorderLayout.NORTH);
		secretKeysPanel.add(secretKeysText, BorderLayout.CENTER);
		secretKeysPanel.add(secretKeyButtonsPanel, BorderLayout.SOUTH);
		
		keyRingsPanel.add(secretKeysPanel);
		
		panel.add(keyRingsPanel, BorderLayout.CENTER);
		panel.add(topPanel, BorderLayout.NORTH);
		
		add(panel);
	}
}
