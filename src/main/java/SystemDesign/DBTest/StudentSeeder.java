package SystemDesign.DBTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentSeeder {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "naruto";

        String insertSQL = "INSERT INTO student(name) VALUES (?)";

        double startTime = System.nanoTime();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false); // batch inserts in a transaction

            int records = 1000_000_000;

            for (int i = 1; i <= records; i++) {
                String name = "Student_" + i;
                pstmt.setString(1, name);
                pstmt.addBatch();

                if (i % 1000 == 0) {  // execute every 1000 for performance
                    pstmt.executeBatch();
                    conn.commit();
                }
            }

            // final flush
            pstmt.executeBatch();
            conn.commit();

            System.out.println("✅ Inserted " + records + " students successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        double endTime = System.nanoTime();

        System.out.println("Operation Completed in: " + (endTime - startTime));
    }
}
