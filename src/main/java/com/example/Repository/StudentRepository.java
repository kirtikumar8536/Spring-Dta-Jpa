package com.example.Repository;

import com.example.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //custom query
    //Hibernate: select s1_0.id,s1_0.age,s1_0.email,s1_0.first_name,s1_0.last_name from student s1_0 where s1_0.email=?
   // @Query(value="SELECT * FROM student WHERE email =?1 ",nativeQuery = true) l-25
    @Query("SELECT s FROM Students s WHERE s.email=?1")//Students-comes from Entity name
    Optional<Student> findStudentByEmail(String Email);

    //select s1_0.id,s1_0.age,s1_0.email,s1_0.first_name,s1_0.last_name from student s1_0 where s1_0.first_name=? and s1_0.age>?
    //jpql- here i use ?
    @Query("SELECT s FROM Students s WHERE s.firstName= ?1 AND s.age>=?2")
    List<Student> selectStudWherefNameAndAgeGreaterOrEqual(String firstname, Integer age);
    // List<Student> findStudentwaByFirstNameEqualsAndAgeIsGreaterThan(String firstname, Integer age);

    //using native query student-name attribute of the table ,first_name- name attribute of column in firstName

    @Query(value = "SELECT * FROM student WHERE first_name = :firstname AND age >= :age", nativeQuery = true)
    List<Student> selectStudWherefNameAndAgeGreaterOrEqualNative(
          @Param("firstname") String firstname,
           @Param("age") Integer age);
    @Transactional
    @Modifying
    @Query("DELETE FROM Students u WHERE u.id=?1")
    int deleteStudentById(Long id);



}
