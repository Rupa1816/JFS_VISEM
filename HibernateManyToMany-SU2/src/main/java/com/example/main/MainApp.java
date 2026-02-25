package com.example.main;

import org.hibernate.Session;
import com.example.entity.*;
import com.example.util.HibernateUtil;

import java.util.*;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getFactory().openSession();

        // -------- Create Courses First --------
        System.out.print("Enter number of courses: ");
        int courseCount = sc.nextInt();
        sc.nextLine();  // consume newline

        List<Course> courseList = new ArrayList<>();

        for (int i = 1; i <= courseCount; i++) {
            System.out.print("Enter title of Course " + i + ": ");
            String title = sc.nextLine();

            Course course = new Course();
            course.setTitle(title);

            courseList.add(course);
        }

        // -------- Create Students --------
        System.out.print("Enter number of students: ");
        int studentCount = sc.nextInt();
        sc.nextLine();

        List<Student> studentList = new ArrayList<>();

        for (int i = 1; i <= studentCount; i++) {

            System.out.print("Enter name of Student " + i + ": ");
            String name = sc.nextLine();

            Student student = new Student();
            student.setName(name);

            System.out.println("Available Courses:");
            for (int j = 0; j < courseList.size(); j++) {
                System.out.println((j + 1) + ". " + courseList.get(j).getTitle());
            }

            System.out.print("Enter course numbers separated by comma (e.g., 1,2): ");
            String input = sc.nextLine();

            String[] choices = input.split(",");
            List<Course> selectedCourses = new ArrayList<>();

            for (String ch : choices) {
                int index = Integer.parseInt(ch.trim()) - 1;
                selectedCourses.add(courseList.get(index));
            }

            student.setCourses(selectedCourses);
            studentList.add(student);
        }

        // -------- Save to Database --------
        session.beginTransaction();

        for (Course c : courseList) {
            session.persist(c);
        }

        for (Student s : studentList) {
            session.persist(s);
        }

        session.getTransaction().commit();
        session.close();
        sc.close();

        System.out.println("Data Saved Successfully!");
    }
}