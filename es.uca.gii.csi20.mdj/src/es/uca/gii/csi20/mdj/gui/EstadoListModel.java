package es.uca.gii.csi20.mdj.gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi20.mdj.data.Estado;

public class EstadoListModel extends AbstractListModel<Estado> implements ComboBoxModel<Estado>{
	
	private static final long serialVersionUID = 1L;
	private List<Estado> _aData;
	private Object _oSelectedItem = null;
	
	public Object getSelectedItem() { return _oSelectedItem; }
	
	public void setSelectedItem(Object oSelectedItem) { _oSelectedItem = oSelectedItem; }
	
	public EstadoListModel(List<Estado> aData) { _aData = aData; }
	
	public Estado getElementAt(int iIndex) { return _aData.get(iIndex); }
	
	public int getSize() { return _aData.size(); } 

}
