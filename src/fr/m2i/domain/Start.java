package fr.m2i.domain;

import fr.m2i.database.Database;
import fr.m2i.models.Student;

public class Start {

    public static void main(String[] args) {
        try (Database db = new Database()) {
            Student student = new Student("Dark", "Vador", 40);
            student = db.addStudent(student);
            db.getStudents();
            System.out.printf("Added student at id %d\n", student.getId());
            db.deleteStudent(student);
            db.getStudents();
            db.updateStudent(new Student(1, "Uzumaki", "Naruto", 15));
        }
    }

}
