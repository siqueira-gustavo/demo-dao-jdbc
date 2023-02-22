package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
  private static Connection conn = null;

  public static Connection getConnection() {
    if (conn == null) {
      try {
        Properties props = loadProperties();
        String url = props.getProperty("dburl");
        conn = DriverManager.getConnection(url, props);
        System.out.println("Connected to '" + conn.getMetaData() + "'");
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
    return conn;
  }

  public static void closeConnection() {
    if (conn != null) {
      try {
        System.out.println("Connection closed");
        conn.close();
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }

  private static Properties loadProperties() {
    try (FileInputStream fs = new FileInputStream("db.properties")) {
      Properties props = new Properties();
      props.load(fs);
      System.out.println("Properties loaded");
      return props;
    } catch (IOException e) {
      throw new DbException(e.getMessage());
    }
  }

  public static void closeStatement(Statement st) {
    if (st != null) {
      try {
        st.close();
        System.out.println("Statement closed");
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }

  public static void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
        System.out.println("ResultSet closed");
      } catch (SQLException e) {
        throw new DbException(e.getMessage());
      }
    }
  }
}
