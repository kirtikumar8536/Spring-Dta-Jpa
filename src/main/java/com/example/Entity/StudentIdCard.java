package com.example.Entity;

import jakarta.persistence.*;

@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        @UniqueConstraint(name = "student_id_card_number_unique", columnNames = "card_number")
})
public class StudentIdCard {
    @Id
    @SequenceGenerator(name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_card_id_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;
    //--don't use Lazy here if u are not using  @OneToOne in Student.java
    @OneToOne(cascade = CascadeType.ALL,//36,37
            fetch = FetchType.EAGER,
            orphanRemoval = false)//default fetch type is eager//39.default value of orphanRemoval is false
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id"//comes from student class id
            ,foreignKey = @ForeignKey(name="student_Id_Card_Student_Id_FK")
    )
    private Student student;

    public StudentIdCard(Long id, String cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }

    public StudentIdCard() {
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student +
                '}';
    }
}
/*
By default, @ManyToMany and @OneToMany associations use the FetchType. LAZY strategy,
while the @ManyToOne and @OneToOne associations use the FetchType. EAGER strategy.*/

//in both side we have to put OneToOne maping to use the bi-directional feature