package com.marian.students;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class TeacherJdbcTest {

    @Test
    public void testFindAll() throws SQLException {
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");

        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", props);
            st = conn.createStatement();
            String sqlQuerry = "select * from teacher";
            rs = st.executeQuery(sqlQuerry);

            while(rs.next()) {
                System.out.println("id == " + rs.getInt(1) + "\n" +
                                   "firstname == " + rs.getString("first_name") + "\n" +
                                   "lastname == " + rs.getString("last_name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) {
                rs.close();
            }

            if(null != st) {
                st.close();
            }

            if(null != conn) {
                conn.close();
            }
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");

        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", props);
            st = conn.createStatement();
            String sqlQuerry = "update teacher set first_name = 'Alexandru' where id = 1";
            int nrRowsUpdated = st.executeUpdate(sqlQuerry);

            assertEquals(1, nrRowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) {
                rs.close();
            }

            if(null != st) {
                st.close();
            }

            if(null != conn) {
                conn.close();
            }
        }
    }

    @Test
    public void testPrepairStatement() throws SQLException {
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");

        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", props);
            String sqlQuerry = "SELECT * FROM teacher WHERE first_name = ? ";
            st = conn.prepareStatement(sqlQuerry);
            st.setString(1, "Alexandru");
            rs = st.executeQuery();
            while(rs.next()) {
                System.out.println("id == " + rs.getInt(1) + "\n" +
                        "firstname == " + rs.getString("first_name") + "\n" +
                        "lastname == " + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != rs) {
                rs.close();
            }

            if(null != st) {
                st.close();
            }

            if(null != conn) {
                conn.close();
            }
        }
    }

    @Test
    public void testCommit() throws SQLException {
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");

        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", props);
            conn.setAutoCommit(false);
            st = conn.createStatement();
            String sqlQuery = "update teacher set first_name = 'Alexandru' where id = 1";
            int noRowsUpdated = st.executeUpdate(sqlQuery);

            conn.commit();
            assertEquals(1, noRowsUpdated);

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            if(null != rs) {
                rs.close();
            }

            if(null != st) {
                st.close();
            }

            if(null != conn) {
                conn.close();
            }
        }
    }
}
