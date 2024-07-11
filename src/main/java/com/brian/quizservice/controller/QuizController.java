package com.brian.quizservice.controller;

import com.brian.quizservice.Dto.QuizDto;
import com.brian.quizservice.model.QuestionWrapper;
import com.brian.quizservice.model.Response;
import com.brian.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;


    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){

        return  quizService.createQuiz(quizDto.getCategory(),quizDto.getNumQuestions(),quizDto.getTitle());
    }
    @GetMapping("get/{id}")
    public  ResponseEntity<List<QuestionWrapper>> GetQuizQuestion(@PathVariable Integer id){
        return quizService.getQuiz(id); // when u get the quiz from the database u want to exclude the right answer using a wrapper
    }

   @PostMapping("submit/{id}")
    public  ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses)
   {
      return  quizService.calculateResult(id, responses);
   }
}
