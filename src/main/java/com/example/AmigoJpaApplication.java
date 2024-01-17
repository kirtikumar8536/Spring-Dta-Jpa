package com.example;

import com.example.Entity.Student;
import com.example.Repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AmigoJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigoJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student("Maria",
                    "Jones",
                    "maria@gmail.com",
                    32);
            Student maya = new Student("maya",
                    "Rob",
                    "maya.Rob@gmail.com",
                    22);
            Student martha = new Student("martha",
                    "Jones",
                    "martha@gmail.com",
                    18);
            Student Brandon = new Student("Brandon",
                    "mecullum",
                    "mecullum.Brandon@gmail.com",
                    26);
            Student maria2 = new Student("Maria",
                    "mecullum",
                    "mecullum.maria@gmail.com",
                    26);
            //studentRepository.save(maria);
            System.out.println("================Adding maria and maya===========================");
            studentRepository.saveAll(List.of(maria, maya,martha,Brandon,maria2));
		/*	System.out.println("=======================No of Students==========================");
			System.out.println(studentRepository.count());//counting total tuples/people

			studentRepository.findById(2L).ifPresentOrElse(System.out::println,
					()->{
						System.out.println("student with id 2 not found");
					});
			studentRepository.findById(3L).ifPresentOrElse(System.out::println,
					()->{
						System.out.println("student with id 3 not found");
					});
			System.out.println("=======================Select All Students:==========================");
			List<Student>students=studentRepository.findAll();
			students.forEach(System.out::println);
			System.out.println("=======================delete id 2: ==========================");
			studentRepository.deleteById(2L);
			System.out.println("=======================No of Students:==========================");
			System.out.println(studentRepository.count());*/
            studentRepository
                    .findStudentByEmail("maria@gmail.com").
                    ifPresentOrElse(System.out::println,
                            () -> System.out.println("Student with email maria@gmail.com is not found"));
            System.out.println("======================================");
            studentRepository.
                    selectStudWherefNameAndAgeGreaterOrEqual
                            ("Maria",18).forEach(System.out::println);
            System.out.println("==============using native=true========================");
            studentRepository.
                    selectStudWherefNameAndAgeGreaterOrEqualNative
                            ("Maria",18).forEach(System.out::println);

            System.out.println("============deleting maria with id=5===========");
            System.out.println(studentRepository.deleteStudentById(5L));
        };
    }
}


