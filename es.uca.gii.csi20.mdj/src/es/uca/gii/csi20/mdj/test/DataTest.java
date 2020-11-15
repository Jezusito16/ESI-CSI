package es.uca.gii.csi20.mdj.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi20.mdj.data.Data;

class DataTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Disabled
	@Test
	void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ResultSet count = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT id, Titulo, Descripcion, Importancia FROM caso;");
			count = con.createStatement().executeQuery("SELECT COUNT(id) FROM caso;");
			
			count.next();
			int numreg = count.getInt("COUNT(id)");
			
			int i = 0;
			while(rs.next()) {
				System.out.println(rs.getString("id")+" "+rs.getString("Titulo")+" "+rs.getString("Descripcion")
				+" "+rs.getInt("Importancia"));
				i++;
				assertEquals(4,rs.getMetaData().getColumnCount());
			}
			assertEquals(numreg,i);
		}
		catch (SQLException ee) {throw ee;}
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testString2Sql() {
		assertEquals("hola",Data.String2Sql("hola", false, false));
		assertEquals("'hola'",Data.String2Sql("hola", true, false));
		assertEquals("%hola%",Data.String2Sql("hola", false, true));
		assertEquals("'%hola%'",Data.String2Sql("hola", true, true));
		assertEquals("O''Connel",Data.String2Sql("O'Connel", false, false));
		assertEquals("'O''Connel'",Data.String2Sql("O'Connel", true, false));
		assertEquals("%''Smith ''%",Data.String2Sql("'Smith '", false, true));
		assertEquals("'''Smith '''",Data.String2Sql("'Smith '", true, false));
		assertEquals("'%''Smith ''%'",Data.String2Sql("'Smith '", true, true));
	}
	
	@Test
	void testBoolean2Sql() {
		assertEquals(1,Data.Boolean2Sql(true));
		assertEquals(0,Data.Boolean2Sql(false));
	}

}
