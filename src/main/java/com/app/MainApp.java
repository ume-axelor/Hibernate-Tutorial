// MainApp.java
package com.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create
        createStudent(sessionFactory);

        // Read
        readStudents(sessionFactory);

        // Update
        updateStudent(sessionFactory);

        // Read after update
        readStudents(sessionFactory);

        // Delete
        deleteStudent(sessionFactory);

        // Read after delete
        readStudents(sessionFactory);
        
    }

    private static void createStudent(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Inserting a new student
            Student student = new Student();
            student.setFirst_name("John");
            student.setLast_name("Doe");
            student.setDate_of_birth(java.sql.Date.valueOf("1990-05-15"));
            session.persist(student);

            transaction.commit();
            System.out.println("Created Student: " + student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readStudents(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Student> students = session.createQuery("FROM Student", Student.class).list();
            System.out.println("List of Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Updating an existing student
            Student studentToUpdate = session.get(Student.class, 4L);
            if (studentToUpdate != null) {
                studentToUpdate.setLast_name("UpdatedLastName");
                session.update(studentToUpdate);
                transaction.commit();
                System.out.println("Updated Student: " + studentToUpdate);
            } else {
                System.out.println("Student not found for update.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Deleting a student
            Student studentToDelete = session.get(Student.class, 5L);
            if (studentToDelete != null) {
                session.delete(studentToDelete);
                transaction.commit();
                System.out.println("Deleted Student: " + studentToDelete);
            } else {
                System.out.println("Student not found for deletion.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}