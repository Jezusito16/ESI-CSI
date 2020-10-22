package es.uca.gii.csi20.mdj.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi20.mdj.data.Data;

class DataTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Test
	void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT * FROM caso;");
			
			int i = 0;
			while(rs.next()) {
				System.out.println(rs.getString("id")+" "+rs.getString("Titulo")+" "+rs.getString("Descripcion")+" "+rs.getString("Fecha Creacion"));
				i++;
				assertEquals(rs.getMetaData().getColumnCount(),4);
			}
			assertEquals(2,i);
		}
		catch (SQLException ee) {throw ee;}
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testString2Sql() {
		assertEquals(Data.String2Sql("hola", false, false),"hola");
		assertEquals(Data.String2Sql("hola", true, false),"'hola'");
		assertEquals(Data.String2Sql("hola", false, true),"%hola%");
		assertEquals(Data.String2Sql("hola", true, true),"'%hola%'");
		assertEquals(Data.String2Sql("O'Connel", false, false),"O''Connel");
		assertEquals(Data.String2Sql("O'Connel", true, false),"'O''Connel'");
		assertEquals(Data.String2Sql("'Smith '", false, true),"%''Smith ''%");
		assertEquals(Data.String2Sql("'Smith '", true, false),"'''Smith '''");
		assertEquals(Data.String2Sql("'Smith '", true, true),"'%''Smith ''%'");
	}
	
	@Test
	void testBoolean2Sql() {
		assertEquals(Data.Boolean2Sql(true),1);
		assertEquals(Data.Boolean2Sql(false),0);
	}

}
