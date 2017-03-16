package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0012.
 */

public class QuestionItem extends Object {
    String number = null; // question number
    String text = null; // question text
    String answer1 = null; // answer number 1
    String answer2 = null; // answer number 2
    String answer3 = null; // answer number 3
    String answer4 = null; // answer number 4
    String right = null; // right answer number (1,2,3 or 4)
    String audience = null; // answer number audience thinks thats right
    String phone = null; // answer number provided by phone joker
    String fifty1 = null; // 1st answer number to remove by 50% joker
    String fifty2= null; // 2nd answer number to remove by 50% joker

    public QuestionItem() {
    }

    public QuestionItem(String number, String text, String answer1, String answer2, String answer3, String answer4, String right, String audience, String phone, String fifty1, String fifty2) {
        this.number = number;
        this.text = text;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.right = right;
        this.audience = audience;
        this.phone = phone;
        this.fifty1 = fifty1;
        this.fifty2= fifty2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFifty1() {
        return fifty1;
    }

    public void setFifty1(String fifty1) {
        this.fifty1 = fifty1;
    }

    public String getFifty2() {
        return fifty2;
    }

    public void setFifty2(String fifty2) {
        this.fifty2 = fifty2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



}
