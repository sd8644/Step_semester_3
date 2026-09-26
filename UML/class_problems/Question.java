import java.util.*;

abstract class Question {
    protected String id, text;
    public Question(String id, String text) { this.id = id; this.text = text; }
    public String getId() { return id; }
    public abstract boolean evaluate(String response);
}

class MultipleChoiceQuestion extends Question {
    private String correctAnswer;
    public MultipleChoiceQuestion(String id, String text, String correctAnswer) {
        super(id, text);
        this.correctAnswer = correctAnswer;
    }
    public boolean evaluate(String response) {
        return correctAnswer.equalsIgnoreCase(response != null ? response.trim() : "");
    }
}

class Student {
    private String id, name;
    public Student(String id, String name) { this.id = id; this.name = name; }
    public String getName() { return name; }
}

class Examination {
    private String id, title;
    private List questions = new ArrayList<>();
    public Examination(String id, String title) { this.id = id; this.title = title; }
    public void addQuestion(Question q) { questions.add(q); }
    public String getTitle() { return title; }
    public List getQuestions() { return questions; }
}

class ExamAttempt {
    private Student student;
    private Examination examination;
    private Map answers = new HashMap<>();
    private boolean submitted = false;
    private int score = 0;

    public ExamAttempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answerQuestion(String qId, String answer) {
        if (submitted) throw new IllegalStateException("Attempt already submitted.");
        answers.put(qId, answer);
    }

    public void submit() {
        if (submitted) throw new IllegalStateException("Attempt already submitted.");
        submitted = true;
        score = 0;
        for (Question q : examination.getQuestions()) {
            if (q.evaluate(answers.get(q.getId()))) score++;
        }
    }

    public int getScore() { return score; }
    public Student getStudent() { return student; }
    public Examination getExamination() { return examination; }
}

public class OnlineExamSystem {
    public static void main(String[] args) {
        Examination mathQuiz = new Examination("EX1", "Math Quiz");
        mathQuiz.addQuestion(new MultipleChoiceQuestion("Q1", "2+2?", "A"));
        mathQuiz.addQuestion(new MultipleChoiceQuestion("Q2", "5x3?", "B"));

        Student student = new Student("S1", "Student");

        ExamAttempt attempt = new ExamAttempt(student, mathQuiz);
        System.out.println("Examination '" + mathQuiz.getTitle() + "' started by " + student.getName() + ".");

        attempt.answerQuestion("Q1", "A");
        System.out.println("Question 1 answered with 'A'.");

        attempt.answerQuestion("Q2", "C");
        System.out.println("Question 2 answered with 'C'.");

        attempt.submit();
        System.out.println("Examination '" + mathQuiz.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + mathQuiz.getTitle() + "' attempt: " 
                + attempt.getScore() + "/" + mathQuiz.getQuestions().size() + " correct.");
    }
}