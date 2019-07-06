package com.marian.hibernate.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries(
        @NamedQuery(name = "Teacher.findByFirstName", query = "select t from Teacher t where t.firstName = :prenume2")
)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_seq_gen")
    @SequenceGenerator(name = "teacher_seq_gen", sequenceName = "teacher_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "hire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hireDate;

    @Column(name = "salary")
    private Double salary;

    @OneToMany(mappedBy = "teacher")
    List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
