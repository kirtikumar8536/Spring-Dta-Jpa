package com.example;

import com.example.Entity.Book;
import com.example.Entity.Course;
import com.example.Entity.Student;
import com.example.Entity.StudentIdCard;
import com.example.Repository.StudentIdCardRepository;
import com.example.Repository.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class AmigoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigoJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                               StudentIdCardRepository studentIdCardRepository) {
        return args -> {

            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@helper.in", firstName, lastName);
            Student student = new Student(firstName, lastName, email,
                    faker.number().numberBetween(18, 50));
            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            student.addBook(
                    new Book("clean code ", LocalDateTime.now().minusDays(4)));
            student.addBook(
                    new Book("Think grow ", LocalDateTime.now()));
            student.addBook(
                    new Book("spring code ", LocalDateTime.now().minusYears(1)));

            //Here im not using BookRepository still im able to save the book data due to Cascade
            //Here im not using student repo to save Student property data
            //studentIdCardRepository.save(studentIdCard);
            student.setStudentIdCard(studentIdCard);
            student.enrolToCourse(new Course("computer science","IT"));
            student.enrolToCourse(new Course("Amigos code spring data jpa","IT"));

            studentRepository.save(student);
            studentRepository.findById(1L).
                    ifPresent(System.out::println);
            studentRepository.findById(1L).
                    ifPresent(s -> {
                        System.out.println("fetch Book Lazy.....");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(s.getFirstName() + " -borrowed- " + book.getBookName());
                        });
                    });


        };
    }



    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 25; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@helper.in", firstName, lastName);
            Student student = new Student(firstName, lastName, email,
                    faker.number().numberBetween(18, 50));
            studentRepository.save(student);
        }
    }

}


