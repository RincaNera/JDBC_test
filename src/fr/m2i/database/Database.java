package fr.m2i.database;

import fr.m2i.models.Student;

import java.sql.*;

public class Database implements AutoCloseable {
    private static final String HOST = "jdbc:mysql://localhost:3306/m2i";
    private static final String USER = "root";
    private static final String PASSWORD = "toto1234";

    private final Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getStudents() {
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM student")
        ) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.printf("%d %s %s %s\n",
                        result.getInt("idStudent"),
                        result.getString("nom"),
                        result.getString("prenom"),
                        result.getInt("age")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Student addStudent(Student student) {
        try (
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO student (nom, prenom, age) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, student.getNom());
            statement.setString(2, student.getPrenom());
            statement.setInt(3, student.getAge());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                student.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public void deleteStudent(Student student) {
        try (
                PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE idStudent = ?")
        ) {
            statement.setInt(1, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudentByID(int id) {
        try (
                PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE idStudent = ?")
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(Student student) {
        try (
                PreparedStatement statement = connection.prepareStatement("UPDATE student SET nom = ?, prenom = ?, age = ? WHERE idStudent = ?")
        ) {
            statement.setString(1, student.getNom());
            statement.setString(2, student.getPrenom());
            statement.setInt(3, student.getAge());
            statement.setInt(4, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        System.out.println("Closing...");
    }
}
