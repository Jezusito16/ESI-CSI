package es.uca.gii.csi20.mdj.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class Caso extends Entity{
	
	private String _sTitulo;
	private String _sDescripcion;
	private int _iImportancia;
	private Estado _eEstado;
	
	public String getTitulo() { return _sTitulo; }
	public void setTitulo(String sTitulo) { _sTitulo = sTitulo; }
	public String getDescripcion() { return _sDescripcion; }
	public void setDescripcion(String sDescripcion) { _sDescripcion = sDescripcion; }
	public int getImportancia() { return _iImportancia; }
	public void setImportancia(int iImportancia) { _iImportancia = iImportancia; }
	public Estado getEstado() { return _eEstado; }
	public void setEstado(Estado eEstado) { _eEstado = eEstado; }
	
	/**
	 * @param iId
	 * @throws Exception
	 */
	public Caso(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT caso.Id_Estado, caso.Titulo, "
					+ "caso.Descripcion, caso.Importancia "
					+ "FROM caso WHERE id = " + iId);
			rs.next();
						
			_sTitulo = rs.getString("Titulo");
			_sDescripcion = rs.getString("Descripcion");
			_iImportancia = rs.getInt("Importancia");
			__bIsDeleted = false;
			_eEstado = new Estado(rs.getInt("Id_Estado"));
			__sTabla = "caso";
			__iId = iId;
			
		} catch ( SQLException e ) { throw e; } 
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	private Caso(int iId, String sTitulo, String sDescripcion, int iImportancia, Estado eEstado) {
		_sTitulo = sTitulo;
		_sDescripcion = sDescripcion;
		_iImportancia = iImportancia;
		__bIsDeleted = false;
		_eEstado = eEstado;
		__sTabla = "caso";
		__iId = iId;
	}

	public String toString() {
		return "Caso" + "@" + __iId + ":" + _sTitulo + ":" + _sDescripcion 
				+ ":" + _iImportancia + ":" + _eEstado;
	}
	
	public void Update() throws Exception {
		super.Update("UPDATE caso SET"
				+ " Titulo = " + Data.String2Sql(_sTitulo, true, false)
				+ ", Descripcion = " + Data.String2Sql(_sDescripcion, true, false)
				+ ", Importancia = " + _iImportancia
				+ ", Id_Estado = " + _eEstado.getId()
				+ " WHERE id = " + __iId);
	}
	
	/**
	 * @param sTitulo
	 * @param sDescripcion
	 * @param iImportancia
	 * @param sEstado
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Caso> Select(String sTitulo, String sDescripcion,
			Integer iImportancia, String sEstado) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Caso> acCaso = new ArrayList<Caso>();
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("Select caso.Id, caso.Id_Estado, caso.Titulo,"
					+ " caso.Descripcion, caso.Importancia"
					+ " FROM caso INNER JOIN estado ON caso.Id_Estado = estado.Id "
					+ Where(
							new String[] { "caso.Titulo", "caso.Descripcion", "caso.Importancia", "estado.Nombre" },
							new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR }, 
							new Object[] { sTitulo, sDescripcion,  iImportancia, sEstado }));
			while(rs.next())
				acCaso.add(new Caso(rs.getInt("Id"), rs.getString("Titulo"), rs.getString("Descripcion"),
						rs.getInt("Importancia"), new Estado(rs.getInt("Id_Estado"), con)));
			
			return acCaso;
		} catch (Exception e) { throw e; }
		finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
	}
	
	/**
	 * @param sTitulo
	 * @param sDescripcion
	 * @param iImportancia
	 * @param eEstado
	 * @return
	 * @throws Exception
	 */
	public static Caso Create(String sTitulo, String sDescripcion, int iImportancia,
			Estado eEstado) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO caso (Id_Estado, Titulo, Descripcion, Importancia)"
					+ "VALUES (" + eEstado.getId() + "," + Data.String2Sql(sTitulo, true, false) + "," 
					+ Data.String2Sql(sDescripcion, true, false) + "," + iImportancia + ")");
			return new Caso(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally { if(con != null) con.close(); }
	}
}
