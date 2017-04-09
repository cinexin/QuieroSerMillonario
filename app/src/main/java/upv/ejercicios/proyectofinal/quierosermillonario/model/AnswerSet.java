package upv.ejercicios.proyectofinal.quierosermillonario.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by migui on 0009.
 */

public class AnswerSet {

    private List<String> possibleAnswers = new ArrayList<>();
    private int answerWhenAudienceJoker;
    private int answer1ToDiscardWhenFiftyPercentJoker;
    private int answer2ToDiscardWhenFiftyPercentJoker;
    private int answerWhenPhoneCallJoker;
    private int rightAnswer;

    public AnswerSet(QuestionItem questionItem) {
        this.possibleAnswers.add(0, questionItem.getAnswer1());
        this.possibleAnswers.add(1,questionItem.getAnswer2());
        this.possibleAnswers.add(2, questionItem.getAnswer3());
        this.possibleAnswers.add(3, questionItem.getAnswer4());

        this.answerWhenAudienceJoker = Integer.valueOf(questionItem.getAudience());
        this.answer1ToDiscardWhenFiftyPercentJoker = Integer.valueOf(questionItem.getFifty1());
        this.answer2ToDiscardWhenFiftyPercentJoker = Integer.valueOf(questionItem.getFifty2());
        this.answerWhenPhoneCallJoker = Integer.valueOf(questionItem.getPhone());
        this.rightAnswer = Integer.valueOf(questionItem.getRight());
    }



    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getAnswerWhenAudienceJoker() {
        return answerWhenAudienceJoker;
    }

    public void setAnswerWhenAudienceJoker(int answerWhenAudienceJoker) {
        this.answerWhenAudienceJoker = answerWhenAudienceJoker;
    }

    public int getAnswer1ToDiscardWhenFiftyPercentJoker() {
        return answer1ToDiscardWhenFiftyPercentJoker;
    }

    public void setAnswer1ToDiscardWhenFiftyPercentJoker(int answer1ToDiscardWhenFiftyPercentJoker) {
        this.answer1ToDiscardWhenFiftyPercentJoker = answer1ToDiscardWhenFiftyPercentJoker;
    }

    public int getAnswer2ToDiscardWhenFiftyPercentJoker() {
        return answer2ToDiscardWhenFiftyPercentJoker;
    }

    public void setAnswer2ToDiscardWhenFiftyPercentJoker(int answer2ToDiscardWhenFiftyPercentJoker) {
        this.answer2ToDiscardWhenFiftyPercentJoker = answer2ToDiscardWhenFiftyPercentJoker;
    }

    public int getAnswerWhenPhoneCallJoker() {
        return answerWhenPhoneCallJoker;
    }

    public void setAnswerWhenPhoneCallJoker(int answerWhenPhoneCallJoker) {
        this.answerWhenPhoneCallJoker = answerWhenPhoneCallJoker;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "AnswerSet{" +
                "possibleAnswers=" + possibleAnswers +
                ", answerWhenAudienceJoker=" + answerWhenAudienceJoker +
                ", answer1ToDiscardWhenFiftyPercentJoker=" + answer1ToDiscardWhenFiftyPercentJoker +
                ", answer2ToDiscardWhenFiftyPercentJoker=" + answer2ToDiscardWhenFiftyPercentJoker +
                ", answerWhenPhoneCallJoker=" + answerWhenPhoneCallJoker +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
