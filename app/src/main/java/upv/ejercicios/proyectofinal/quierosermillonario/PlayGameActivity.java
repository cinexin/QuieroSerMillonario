package upv.ejercicios.proyectofinal.quierosermillonario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.model.QuestionItem;

/**
 * Created by migui on 0012.
 */

public class PlayGameActivity extends ActionBarActivity {
    private GridView answersGridView ;

    public List<QuestionItem> generateQuestionItemList() {
        List<QuestionItem> list = new ArrayList<QuestionItem>();
        QuestionItem q = null;

        q = new QuestionItem(
                "1",
                "Which is the Sunshine State of the US?",
                "North Carolina",
                "Florida",
                "Texas",
                "Arizona",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new QuestionItem(
                "2",
                "Which of these is not a U.S. state?",
                "New Hampshire",
                "Washington",
                "Wyoming",
                "Manitoba",
                "4",
                "4",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "3",
                "What is Book 3 in the Pokemon book series?",
                "Charizard",
                "Island of the Giant Pokemon",
                "Attack of the Prehistoric Pokemon",
                "I Choose You!",
                "3",
                "2",
                "3",
                "1",
                "4"
        );
        list.add(q);

        q = new QuestionItem(
                "4",
                "Who was forced to sign the Magna Carta?",
                "King John",
                "King Henry VIII",
                "King Richard the Lion-Hearted",
                "King George III",
                "1",
                "3",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "5",
                "Which ship was sunk in 1912 on its first voyage, although people said it would never sink?",
                "Monitor",
                "Royal Caribean",
                "Queen Elizabeth",
                "Titanic",
                "4",
                "4",
                "4",
                "1",
                "2"
        );
        list.add(q);

        q = new QuestionItem(
                "6",
                "Who was the third James Bond actor in the MGM films? (Do not include &apos;Casino Royale&apos;.)",
                "Roger Moore",
                "Pierce Brosnan",
                "Timothy Dalton",
                "Sean Connery",
                "1",
                "3",
                "3",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "7",
                "Which is the largest toothed whale?",
                "Humpback Whale",
                "Blue Whale",
                "Killer Whale",
                "Sperm Whale",
                "4",
                "2",
                "2",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "8",
                "In what year was George Washington born?",
                "1728",
                "1732",
                "1713",
                "1776",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new QuestionItem(
                "9",
                "Which of these rooms is in the second floor of the White House?",
                "Red Room",
                "China Room",
                "State Dining Room",
                "East Room",
                "2",
                "2",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new QuestionItem(
                "10",
                "Which Pope began his reign in 963?",
                "Innocent III",
                "Leo VIII",
                "Gregory VII",
                "Gregory I",
                "2",
                "1",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new QuestionItem(
                "11",
                "What is the second longest river in South America?",
                "Parana River",
                "Xingu River",
                "Amazon River",
                "Rio Orinoco",
                "1",
                "1",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "12",
                "What Ford replaced the Model T?",
                "Model U",
                "Model A",
                "Edsel",
                "Mustang",
                "2",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "13",
                "When was the first picture taken?",
                "1860",
                "1793",
                "1912",
                "1826",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "14",
                "Where were the first Winter Olympics held?",
                "St. Moritz, Switzerland",
                "Stockholm, Sweden",
                "Oslo, Norway",
                "Chamonix, France",
                "4",
                "1",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new QuestionItem(
                "15",
                "Which of these is not the name of a New York tunnel?",
                "Brooklyn-Battery",
                "Lincoln",
                "Queens Midtown",
                "Manhattan",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        return list;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);




        List<QuestionItem> questionItems = this.generateQuestionItemList();
        QuestionItem questionItem = questionItems.get(4);

        // sample - 1st question
        TextView txtQuestion = (TextView) findViewById(R.id.txt_question);
        txtQuestion.setText(questionItem.getText());

        TextView txtQuestionNumber = (TextView) findViewById(R.id.txt_question_number) ;
        txtQuestionNumber.setText(questionItem.getNumber());

        // sample - 1st question 4 possible answers

        List<String> possibleAnswers =  new ArrayList<>();
        possibleAnswers.add(0, questionItem.getAnswer1());
        possibleAnswers.add(1, questionItem.getAnswer2());
        possibleAnswers.add(2, questionItem.getAnswer3());
        possibleAnswers.add(3, questionItem.getAnswer4());

        this.answersGridView = (GridView) findViewById(R.id.possible_answers_grid_view);
        this.answersGridView.setAdapter(new AnswerItemAdapter(this, possibleAnswers));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play_game_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
