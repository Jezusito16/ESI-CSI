package es.uca.gii.csi20.mdj.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi20.mdj.data.Caso;
import es.uca.gii.csi20.mdj.data.Estado;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrCasos extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTitulo;
	private JTextField txtDescripcion;
	private JTextField txtImportancia;
	private JTable tabResult;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public IfrCasos(Container pnlParent) throws Exception {
		setResizable(true);
		setClosable(true);
		setEnabled(false);
		setTitle("Casos");
		setBounds(100, 100, 800, 500);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		panel.add(lblTitulo);
		
		txtTitulo = new JTextField();
		panel.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descrici\u00F3n");
		panel.add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		panel.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JLabel lblImportancia = new JLabel("Importancia");
		panel.add(lblImportancia);
		
		txtImportancia = new JTextField();
		panel.add(txtImportancia);
		txtImportancia.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado");
		panel.add(lblEstado);
		
		JComboBox<Estado> cmbEstado = new JComboBox<Estado>();
		cmbEstado.setModel(
				new EstadoListModel(Estado.Select(null)));
		cmbEstado.setEditable(true);
		panel.add(cmbEstado);
		
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
						tabResult.setModel(
								new CasosTableModel(
										Caso.Select(
												txtTitulo.getText(), 
												txtDescripcion.getText(), 
												(txtImportancia.getText().equals("")?null:
													Integer.parseInt(txtImportancia.getText())),
												cmbEstado.getSelectedItem() == null?null
														:cmbEstado.getSelectedItem().toString())));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al introducir los datos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		panel.add(btnBuscar);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int iRow = ((JTable)e.getSource()).getSelectedRow();
					
					Caso cCaso = ((CasosTableModel)tabResult.getModel()).getData(iRow);
					
					if (cCaso != null) {			
						int iIfrCaso = 0;
						
						while(iIfrCaso < FrmMain.lOpenCasos.size() && 
								FrmMain.lOpenCasos.get(iIfrCaso).getCaso().getId() != cCaso.getId())
							++iIfrCaso;
						
						if(iIfrCaso < FrmMain.lOpenCasos.size()) 
							FrmMain.lOpenCasos.get(iIfrCaso).setVisible(true);
						else 
							try {
								IfrCaso ifrCaso = new IfrCaso(cCaso);
								FrmMain.lOpenCasos.add(ifrCaso);
								ifrCaso.setBounds(10, 27, 300, 192);
								pnlParent.add(ifrCaso, 0);
								ifrCaso.setVisible(true);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Error al introducir los datos",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);

	}

}
