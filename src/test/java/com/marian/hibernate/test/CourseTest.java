package com.marian.hibernate.test;

import com.marian.hibernate.model.Course;
import com.marian.hibernate.model.Teacher;
import com.marian.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CourseTest {

    @Test
    public void createCourse() {
        try (SessionFactory factory = HibernateUtil.getSessionFactory();
             Session session = factory.openSession()) {

            Course course = new Course();
            course.setCode("Java123");
            course.setDescription("Basic Java course");
            // 1. Create new teacher
            // 2. Load teacher from db
            Teacher teacher1 = new Teacher();
            teacher1.setFirstName("Gigel");
            teacher1.setLastName("Bratescu");
            teacher1.setSalary(1500.45);

            Transaction tx = session.beginTransaction();
            Long teacherId = (Long) session.save(teacher1);
            course.setTeacher(teacher1);

            session.save(course);
            List<Course> courses = new ArrayList<>();
            courses.add(course);
            teacher1.setCourses(courses);

            session.saveOrUpdate(teacher1);

            tx.commit();
        }
    }
}