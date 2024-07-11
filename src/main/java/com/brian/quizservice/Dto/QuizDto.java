package com.brian.quizservice.Dto;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private  int numQuestions;
    private  String title;
}
