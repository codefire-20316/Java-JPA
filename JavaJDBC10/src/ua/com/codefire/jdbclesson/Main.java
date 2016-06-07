/*
 * CodeFireUA public license.
 */
package ua.com.codefire.jdbclesson;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName(com.mysql.jdbc.Driver.class.getName());

        List<String> tableList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "student", "12345")) {

            ResultSet rsTables = conn.createStatement().executeQuery("SHOW TABLES");

            System.out.println("Available tables:");
            int i = 1;
            while (rsTables.next()) {
                String tableName = rsTables.getString(1);
                tableList.add(tableName);

                System.out.printf(" %2d. %s\n", i++, tableName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        int tableNumber;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose table:");

            try {
                tableNumber = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Wrong input.");
            }
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "student", "12345")) {
            String selectedTableName = tableList.get(tableNumber - 1);
            String selectQuery = String.format("SELECT * FROM `%s`", selectedTableName);

            ResultSet rs = conn.createStatement().executeQuery(selectQuery);
            ResultSetMetaData meta = rs.getMetaData();

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rsPK = metaData.getPrimaryKeys(null, "test", selectedTableName);
            ResultSetMetaData rsPKMeta = rs.getMetaData();

            while (rsPK.next()) {
                for (int i = 1; i <= rsPKMeta.getColumnCount(); i++) {
                    System.out.print(rsPK.getString(2) + " ");
                }
            }
            System.out.println("");

            System.out.print("|");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String columnName = meta.getColumnName(i);
                String columnType = meta.getColumnTypeName(i);
                int columnSize = meta.getColumnDisplaySize(i);
                System.out.printf(" %-" + columnName.length() + "s [%s(%d)] |", columnName, columnType, columnSize);
            }
            System.out.println();

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
