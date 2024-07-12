package com.brian.quizservice.service;

import com.brian.quizservice.feign.QuizInterface;
import com.brian.quizservice.model.Question;
import com.brian.quizservice.model.QuestionWrapper;
import com.brian.quizservice.model.Quiz;
import com.brian.quizservice.model.Response;
import com.brian.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizInterface quizInterface;
    @Autowired
    private  Quiz quiz;


    public ResponseEntity<String> createQuiz( String category,  int numQuestions,  String title) {
        //the trick here is that quiz service will have to interact with the questionservice not question dao
        //List<Integer> questionId = //call  generate url -RestTemplate http://localhost:8080/question/generate
        //in production u don't hard code your ports so u will use a feign client, service discovery
        //as u see the quiz service is trying to search for the question service
        //every microservice which wants  to search the other will register to a eureka server, and from there
       // any of them will connect to the eureka server
        //every service will have a name such that u dont depend on the port and ip number
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQuestions).getBody();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return  new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Quiz quiz1= quizRepository.findById(id).orElseThrow();
        List<Integer> questionIds = quiz1.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionForUser =quizInterface.getQuestionsFromId(questionIds);
        return  questionForUser;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;

    }
}
