package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;

public abstract class Entity {
	protected int _iId;
	protected boolean _bIsDeleted = false;
	protected String _sTabla;
	
	public int getId() { return _iId; }
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	/**
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted)
				throw new Exception("El Caso ya ha sido eliminado");
			
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		} catch(Exception e) {
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void Delete() throws Exception {
		Connection con = null;
		
		try {
			if(_bIsDeleted)
				throw new Exception("El estado ya ha sido eliminado");
			
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM " + _sTabla + " WHERE id = " 
			+ _iId + ";");
			_bIsDeleted = true;
		} catch(Exception e){
			throw e;
		}
		finally {
			if(con != null) con.close();
		}
	}
}
