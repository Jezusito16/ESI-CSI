package es.uca.gii.csi20.mdj.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
	UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gesti�n de Archivos Policiales de Silent Hill");
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);
		
		JMenuItem mitNewCaso = new JMenuItem("Caso");
		mitNewCaso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrCaso ifrCaso;
				try {
					ifrCaso = new IfrCaso(null);
					ifrCaso.setBounds(10, 27, 300, 192);
					frame.getContentPane().add(ifrCaso);
					ifrCaso.setVisible(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al introducir los datos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mitNew.add(mitNewCaso);
		
		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);
		
		JMenuItem mitSearchCaso = new JMenuItem("Caso");
		mitSearchCaso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrCasos ifrCasos;
				try {
					ifrCasos = new IfrCasos(frame);
					ifrCasos.setBounds(12, 28, 500, 350);
					frame.getContentPane().add(ifrCasos, 0);
					ifrCasos.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al introducir los datos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mitSearch.add(mitSearchCaso);
		frame.getContentPane().setLayout(null);
	}
}
