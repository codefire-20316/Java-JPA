/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author human
 */
public class DAO implements ListSelectionListener {

    private String hostname;
    private String username;
    private String password;

    public DAO(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:mysql://%s", hostname), username, password);
    }

    /**
     * Getting database list.
     *
     * @return database list.
     */
    public List<String> getDatabases() {
        List<String> databaseList = new ArrayList<>();

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");

            while (rs.next()) {
                String databaseName = rs.getString(1);
                databaseList.add(databaseName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return databaseList;
    }

    /**
     * Getting tables list from passed database.
     *
     * @param database database for selection.
     * @return tables list.
     */
    public List<String> getTables(String database) {
        List<String> databaseList = new ArrayList<>();

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SHOW TABLES FROM `%s`", database));

            while (rs.next()) {
                String tableName = rs.getString(1);
                databaseList.add(tableName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return databaseList;
    }

    public void updateTable(String databaseName, String tableName, String columnName, String value, int id) {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();

            String updateQuery = String.format("UPDATE `%s`.`%s`  SET `%s` = '%s' where `id` ='%s' ",
                    databaseName, tableName, columnName, value, id);
            
            System.out.println(updateQuery);
            stmt.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DefaultTableModel getTableDataModel(String databaseName, String tableName) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM `%s`.`%s`", databaseName, tableName));
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                columnNames.add(columnName);
            }

            while (rs.next()) {
                Vector<Object> row = new Vector<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }

                data.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        // TODO: Create table model by database data from resultset.

        return dtm;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
