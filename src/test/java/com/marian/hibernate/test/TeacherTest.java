package com.marian.hibernate.test;

import com.marian.hibernate.model.Teacher;
import com.marian.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class TeacherTest {

    @Test
    @Ignore
    public void testCreateTeacher() {
        // Obtin un SessionFactory
        // din care sa obtin un Session
        Transaction tx = null;
        try(SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Teacher teacher = new Teacher();
            teacher.setFirstName("Ionel");
            teacher.setLastName("Georgescu");
            teacher.setHireDate(new Date());
            teacher.setSalary(new Double(1200.50));

            session.save(teacher);
            tx.commit();

        } catch (Exception ex) {
            if(tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Test
    public void testFindAll() {
        try(SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession()) {

            // HQL - Hibernate Querry Language - entities **
            TypedQuery<Teacher> teacherQuerry = session.createQuery("from Teacher", Teacher.class);

            List<Teacher> result = teacherQuerry.getResultList();
            result.forEach(teacher -> {
                System.out.println("firstname == " + teacher.getFirstName());
            });
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    @Test
    public void testFindByFirstName() {
        try(SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession()) {

            // HQL - Hibernate Querry Language - entities **
//            TypedQuery<Teacher> teacherQuerry = session.createQuery("select t from Teacher t where t.firstName = ?1",
//                                                                    Teacher.class);
            TypedQuery<Teacher> teacherQuerry = session.createQuery("select t from Teacher t where t.firstName = :prenume",
                    Teacher.class);

            teacherQuerry.setParameter("prenume", "Ionel");
            List<Teacher> result = teacherQuerry.getResultList();
            result.forEach(teacher -> {
                System.out.println("firstname == " + teacher.getFirstName());
            });
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    @Test
    public void testNamedQuerry() {
        try(SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession()) {

            // HQL - Hibernate Querry Language - entities **

            // When I don't know the returned type of query:
//            Query<Teacher> teacherQuerry1 = session.createNamedQuery("Teacher.findByFirstName");
            // When I know the returned type of querry
            TypedQuery<Teacher> teacherQuerry = session.createNamedQuery("Teacher.findByFirstName", Teacher.class);

            // prenume2 == numele parametrului din NamedQuery declarata la nivel de entitate (Teacher)
            teacherQuerry.setParameter("prenume2", "Ionel");
            List<Teacher> result = teacherQuerry.getResultList();
            result.forEach(teacher -> {
                System.out.println("firstname == " + teacher.getFirstName());
            });
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    @Test
    public void testGetSingleResult() {
        try(SessionFactory sf = HibernateUtil.getSessionFactory();
            Session session = sf.openSession()) {

//            Teacher teacher = session.get(Teacher.class, 100L);
            TypedQuery<Teacher> query = session.createQuery("select t from Teacher t where t.firstName = :prenume3",
                                                            Teacher.class);
            query.setParameter("prenume3", "Ionel");

            Teacher teacher = query.getSingleResult();
            System.out.println("teacher name = " + teacher.getFirstName());

        } catch (NoResultException | NonUniqueResultException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
