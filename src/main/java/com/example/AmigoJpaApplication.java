package com.example;

import com.example.Entity.Student;
import com.example.Repository.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class AmigoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigoJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            generateRandomStudents(studentRepository);
            // Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
            Page<Student> page = studentRepository.findAll(pageRequest);
            System.out.println(page);
        };
    }

    private  void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").ascending());
        studentRepository.
                findAll(sort).forEach(student -> System.out.println(student.getFirstName()+" "+student.getAge()));
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


