package com.example;

import com.example.Entity.Student;
import com.example.Entity.StudentIdCard;
import com.example.Repository.StudentIdCardRepository;
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
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                               StudentIdCardRepository studentIdCardRepository) {
        return args -> {
            /*generateRandomStudents(studentRepository);
            // Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
            Page<Student> page = studentRepository.findAll(pageRequest);
            System.out.println(page);*/
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@helper.in", firstName, lastName);
            Student student = new Student(firstName, lastName, email,
                    faker.number().numberBetween(18, 50));
            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            //Here im not using student repo to save Student property data
            studentIdCardRepository.save(studentIdCard);
            // by default OneToOne mapping uses FetchType.EAGER so that here student object will be load here
            studentIdCardRepository.findById(1L).
                    ifPresent(System.out::println);
            // by default OneToOne mapping uses FetchType.EAGER so that here studentIdCard object will be load here
            studentRepository.findById(1L)
                    .ifPresent(System.out::println);
/*
            Hibernate: select s1_0.id,s1_0.age,s1_0.email,s1_0.first_name,s1_0.last_name,sic1_0.id,sic1_0.card_number from student s1_0 left join student_id_card sic1_0 on s1_0.id=sic1_0.student_id where s1_0.id=?
            Student{id=1, firstName='Alan', lastName='Quigley', email='Alan.Quigley@helper.in', age=29}
            Hibernate: select sic1_0.id,sic1_0.card_number,s1_0.id,s1_0.age,s1_0.email,s1_0.first_name,s1_0.last_name from student_id_card sic1_0 left join student s1_0 on s1_0.id=sic1_0.student_id where sic1_0.id=?
            StudentIdCard{id=1, cardNumber='123456789', student=Student{id=1, firstName='Alan', lastName='Quigley', email='Alan.Quigley@helper.in', age=29}}
*/
            System.out.println("============");
           /* select s1_0.id,s1_0.age,s1_0.email,s1_0.first_name,s1_0.last_name,sic1_0.id,sic1_0.card_number from student s1_0
            left join student_id_card sic1_0 on s1_0.id=sic1_0.student_id where s1_0.id=?*/
//            Hibernate: delete from student_id_card where id=?
//            Hibernate: delete from student where id=?
            studentRepository.deleteById(1L);

        };
    }

  /*  private  void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("age").ascending());
        studentRepository.
                findAll(sort).forEach(student -> System.out.println(student.getFirstName()+" "+student.getAge()));
    }*/

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


