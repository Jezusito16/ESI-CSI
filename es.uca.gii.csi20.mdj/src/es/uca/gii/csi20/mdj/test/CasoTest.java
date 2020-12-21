package es.uca.gii.csi20.mdj.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import es.uca.gii.csi20.mdj.data.Caso;
import es.uca.gii.csi20.mdj.data.Data;
import es.uca.gii.csi20.mdj.data.Estado;

public class CasoTest {
	
	@BeforeAll
	public void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	@Test
	public void testConstructor() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		Caso cCaso = new Caso(1);
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT caso.id, caso.Titulo, caso.Descripcion, caso.Importancia FROM caso;");
			rs.next();			
			
			assertEquals(rs.getInt("Id"), cCaso.getId());
			assertEquals(rs.getString("Titulo"), cCaso.getTitulo());
			assertEquals(rs.getString("Descripcion"), cCaso.getDescripcion());
			assertEquals(rs.getInt("Importancia"), cCaso.getImportancia());	
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	@Test
	public void testCreate() throws Exception{		
		Connection con = null;
		ResultSet rs = null;
		Estado eEstado = Estado.Create("Estado");
		Caso cCaso  = Caso.Create("Titulo del caso", "Descipcion del caso", 2, eEstado);
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT caso.id, caso.Titulo, caso.Descripcion,"
					+ " caso.Importancia FROM caso WHERE id = " + cCaso.getId() + ";");
			rs.next();
			
			assertEquals(rs.getInt("Id"), cCaso.getId());
			assertEquals(rs.getString("Titulo"), cCaso.getTitulo());
			assertEquals(rs.getString("Descripcion"), cCaso.getDescripcion());
			assertEquals(rs.getInt("Importancia"), cCaso.getImportancia());			
			
			cCaso.Delete(); eEstado.Delete();
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
	
	@Test
	public void testSelect() throws Exception{
		Estado eEstado = Estado.Create("Estado");
		Caso.Create("PruebaTitulo", "PruebaDescripcion", 3, eEstado);
		
		ArrayList<Caso> alCasoList = Caso.Select("Prueba", null, null, null);
		Caso cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		alCasoList = Caso.Select("rue", null, null, null);
		cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		alCasoList = Caso.Select(null, "bades", null, null);
		cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		alCasoList = Caso.Select(null, null, 3, null);
		cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		alCasoList = Caso.Select(null, null, null, "sta");
		cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		alCasoList = Caso.Select("rue", "bades", 3, "sta");
		cCaso = alCasoList.get(0);
		assertEquals("PruebaTitulo", cCaso.getTitulo());
		assertEquals("PruebaDescripcion", cCaso.getDescripcion());
		assertEquals(3, cCaso.getImportancia());
		
		cCaso.Delete(); eEstado.Delete();
	}
	
	@Test
	public void testUpdate() throws Exception{
		Estado eEstado = Estado.Create("Estado");
		Caso cCaso = Caso.Create("PruebaTitulo", "PruebaDescripcion", 0, eEstado);
		
		cCaso.setTitulo("UpdateTitulo");
		cCaso.setDescripcion("UpdateDescripcion");
		cCaso.setImportancia(2);
		cCaso.Update();
		
		Caso cCasoUpdated = new Caso(cCaso.getId());
		
		assertEquals("UpdateTitulo", cCasoUpdated.getTitulo());
		assertEquals("UpdateDescripcion", cCasoUpdated.getDescripcion());
		assertEquals(2, cCasoUpdated.getImportancia());
		
		cCasoUpdated.Delete();	eEstado.Delete();
	}
	
	@Test
	public void testDelete() throws Exception{
		Connection con = null;
		ResultSet rs = null;
		Estado eEstado = Estado.Create("Estado");
		try {
			Caso cCaso = Caso.Create("TituloDelete", "DescripcionDelete", 0, eEstado);
			con = Data.Connection();
			cCaso.Delete();
			eEstado.Delete();
			rs = con.createStatement().executeQuery("SELECT id FROM caso WHERE id = "+cCaso.getId());
			assertEquals(false, rs.next());
			assertEquals(true, cCaso.getIsDeleted());	
		} catch (SQLException e) { throw e; }
		finally {
			if (con != null) con.close();
			if (rs != null) rs.close();
		}
	}
}
