package com.example.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "Students")  //default name will be class name// this name will used in jpql
@Table(name = "student",//
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        })
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(name = "age", nullable = false)
    private Integer age;
    @OneToOne(mappedBy = "student", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) //this student comes from StudentIdCard property.
    private StudentIdCard studentIdCard;

    @OneToMany(mappedBy = "student",//comes from Book field(student)
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)//default is Lazy
    private List<Book> books = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(name = "enrolment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
            )
    )
    private List<Course> courses = new ArrayList<>();


    public Student() {
    }

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void enrolToCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);

    }
    public void unEnrolToCourse(Course course){
        courses.remove(course);
        course.getStudents().remove(this);
    }

    public List<Book> getBooks() {
        return books;
    }


    public void setBooks(List<Book> books) {
        this.books = books;
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

    public Integer getAge() {
        return age;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public void addBook(Book book) {
        if (!this.books.contains(book)) {
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
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
