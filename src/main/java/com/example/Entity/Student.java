package com.example.Entity;

import jakarta.persistence.*;

@Entity(name = "Students")  //default name will be class name// this name will used in jpql
@Table(name = "student",//
       uniqueConstraints = {
        @UniqueConstraint(name="student_email_unique",columnNames = "email")
})
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence",
                        sequenceName = "student_sequence",
                         allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_sequence")
    @Column(name="id",updatable = false)
    private Long id;
    @Column(name="first_name",nullable = false,columnDefinition = "TEXT")
    private String firstName;
    @Column(name="last_name",nullable = false,columnDefinition = "TEXT")
    private String lastName;
    @Column(name="email",nullable = false,columnDefinition = "TEXT")
    private String email;
    @Column(name="age",nullable = false)
    private Integer age;
    @OneToOne(mappedBy = "student") //this student comes from StudentIdCard property
    private StudentIdCard studentIdCard;

    public Student() {
    }

    public Student( String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
