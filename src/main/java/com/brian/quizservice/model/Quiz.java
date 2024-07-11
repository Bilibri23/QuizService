package com.brian.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Entity
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToMany  // a third table which contains the quiz id and the question id to show their relationship, remember that the many part shows there is a foreign key there
    private List<Question> questions; //as a quiz contains many questions, and the same question can be found in multiple instances of the quizzes

}
