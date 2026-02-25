package com.example.main;

import org.hibernate.Session;
import com.example.entity.*;
import com.example.util.HibernateUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Open Hibernate Session
        Session session = HibernateUtil.getFactory().openSession();

        // Create Department
        Department dept = new Department();

        System.out.print("Enter Department Name: ");
        String deptName = sc.nextLine();
        dept.setName(deptName);

        // Number of employees
        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();
        sc.nextLine();  // consume leftover newline

        List<Employee> emplist = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter name of Employee " + i + ": ");
            String empName = sc.nextLine();

            Employee emp = new Employee();
            emp.setName(empName);

            // Set department for employee
            emp.setDepartment(dept);

            emplist.add(emp);
        }

        // Set employees to department
        dept.setEmployees(emplist);

        // Save to DB
        session.beginTransaction();
        session.persist(dept);   // Cascade must be enabled
        session.getTransaction().commit();

        session.close();
        sc.close();

        System.out.println("Data Saved Successfully!");
    }
}