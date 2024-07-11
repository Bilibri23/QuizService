package com.brian.quizservice.service;

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


    public ResponseEntity<String> createQuiz( String category,  int numQuestions,  String title) {
        //the trick here is that quiz service will have to interact with the questionservice not question dao
        //List<Integer> questionId = //call  generate url -RestTemplate http://localhost:8080/question/generate
        //in production u don't hard code your ports so u will use a feign client, service discovery
        //as u see the quiz service is trying to search for the question service
        //every microservice which wants  to search the other will register to a eureka server, and from there
       // any of them will connect to the eureka server
        //every service will have a name such that u dont depend on the port and ip number
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Quiz quiz1= quizRepository.findById(id).orElseThrow();
        List<Question> questionsFromDB = quiz1.getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for(Question question: questionsFromDB){
            QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getQuestionTitle());
            questionForUser.add(questionWrapper);
        }
        return  new ResponseEntity<>(questionForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz  = quizRepository.findById(id).orElseThrow();
        List<Question> questions = quiz.getQuestions();
        int correct = 0;
        int i = 0;
        for(Response response1: responses){
            if(response1.getResponse().equals(questions.get(i).getRightAnswer())){
                correct++;
            }
            i++;

        }
        return  new ResponseEntity<>(correct, HttpStatus.OK);

    }
}
