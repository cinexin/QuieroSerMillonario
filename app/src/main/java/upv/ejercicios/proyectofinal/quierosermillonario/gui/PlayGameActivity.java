package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ModalYesNoMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ToastMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.model.QuestionItem;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameScoresService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.XMLParsingService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;

/**
 * Created by migui on 0012.
 */

public class PlayGameActivity extends AppCompatActivity {
    private GridView answersGridView ;
    private int currentQuestion;
    private GameSettings gameSettings;
    private GameScore gameScore;
    private GameScoresService gameScoresService;
    private List<QuestionItem> questionItems;
    private boolean gameFinished = false;


    private void manageAvailableJokers(int numberOfJokers, Menu menu) {
        switch (numberOfJokers) {
            case 2:
                // 2 jokers = we remove audience joker
                menu.removeItem(R.id.btn_audience_joker);
                break;

            case 1:
                // 1 joker = we remove audience and 50% jokers
                menu.removeItem(R.id.btn_audience_joker);
                menu.removeItem(R.id.btn_fifty_percent_joker);
                break;

            case 0:
                // 0 jokers = we remove all jokers >:)
                menu.removeItem(R.id.btn_audience_joker);
                menu.removeItem(R.id.btn_fifty_percent_joker);
                menu.removeItem(R.id.btn_call_joker);
                break;

            default:
                // all jokers are available by default
                break;
        }
    }

    // DONE: displayCurrentQuestion() implementation
    private void displayCurrentQuestion() {

        Logging logging = new Logging();
        logging.debug("displayCurrentQuestion() method called");
        logging.debug("GAME SCORE" + gameScoresService.getGameScore().toString());



        final QuestionItem questionItem = questionItems.get(currentQuestion - 1);

        // sample question
        TextView txtQuestion = (TextView) findViewById(R.id.txt_question);
        txtQuestion.setText(questionItem.getText());

        TextView txtQuestionNumber = (TextView) findViewById(R.id.txt_question_number) ;
        txtQuestionNumber.setText(String.valueOf(currentQuestion));

        // sample - 1st question 4 possible answers

        List<String> possibleAnswers =  new ArrayList<>();
        possibleAnswers.add(0, questionItem.getAnswer1());
        possibleAnswers.add(1, questionItem.getAnswer2());
        possibleAnswers.add(2, questionItem.getAnswer3());
        possibleAnswers.add(3, questionItem.getAnswer4());

        this.answersGridView = (GridView) findViewById(R.id.possible_answers_grid_view);
        final AnswerItemAdapter answerItemAdapter =  new AnswerItemAdapter(this, possibleAnswers);
        this.answersGridView.setAdapter(answerItemAdapter);


        TextView txtPlayingFor = (TextView) findViewById(R.id.txt_playing_for);
        txtPlayingFor.setText(String.valueOf(gameScoresService.getGameScore().getPlayingFor()) );

        answersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logging logging = new Logging();

                // check if it's the correct answer
                int rightAnswer = Integer.valueOf(questionItem.getRight());
                if ((position + 1) == rightAnswer ) { // right answer :)
                    logging.debug("RIGHT ANSWER :-)");
                    view.setBackgroundColor(getResources().getColor(R.color.rightAnswer));
                    ToastMessage.rightAnswerMessage(getApplicationContext());
                    gameScoresService.nextQuestion();
                    currentQuestion++;

                    displayCurrentQuestion();

                } else { // wrong answer :(
                    logging.debug("WRONG ANSWER :-(");
                    view.setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
                    finishGame(AppConstants.CONTESTANT_FAILED_QUESTION);
                }
            }
        });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        // Old hardcoded way of questions population....
        // questionItems = this.generateQuestionItemList();
        // Replaced by new population via XML files...
        Logging log = new Logging();
        String sysLanguage = Locale.getDefault().getLanguage();
        log.debug("SYSTEM LANGUAGE: " + sysLanguage);
        XMLParsingService xmlParsingService = new XMLParsingService(getApplicationContext());
        try {
            questionItems = xmlParsingService.parseQuestionsFile(sysLanguage);
        } catch (IOException | XmlPullParserException xmlParsingEx) {
            xmlParsingEx.printStackTrace();
            log.error("Error retrieving / parsing XML Question file.");
            // TODO: find a smarter way to handle this critical exception
            return;
        }
        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        gameSettings = gameSettingsService.getSettings();

        currentQuestion = gameSettings.getCurrentQuestion();

        gameScore = new GameScore();
        gameScore.setUserName(gameSettings.getUserName());
        gameScore.setLastQuestionAnswered(currentQuestion - 1);
        gameScoresService = new GameScoresService(gameScore, this.getApplicationContext());
        gameScoresService.refreshGameScore();


        displayCurrentQuestion();

        log.debug(gameScoresService.getGameScore().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play_game_screen, menu);
        if (this.gameSettings == null) {
            GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
            gameSettings = gameSettingsService.getSettings();
        }

        manageAvailableJokers(gameSettings.getNumberOfJokers(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateScoresAndQuitGame() {


        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        gameSettingsService.saveGamePosition(1);

        try {
            gameScoresService.saveScore();
        } catch (PersistenceException persistEx) {
            persistEx.printStackTrace();
            Logging log = new Logging();
            log.error("ERROR: Couldn't store score on database!!");
        }

        gameFinished = true;
        finish();
    }

    private void finishGame(int cause) {


        if (cause == AppConstants.CONTESTANT_FAILED_QUESTION) {
            if (gameScore != null) {
                gameScore.setMoneyAchieved(gameScore.getMoneyEnsured());
                gameScoresService.setGameScore(gameScore);
                updateScoresAndQuitGame();
            }
        }
        else if (cause == AppConstants.CONTESTANT_ABANDONED_GAME) {
            DialogInterface.OnClickListener yesNoDialogClickListener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            updateScoresAndQuitGame();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;

                        default:
                            break;
                    }
                }
            };

            ModalYesNoMessage modalMsg = new ModalYesNoMessage(PlayGameActivity.this, R.string.abandon_game_confirmation, yesNoDialogClickListener);

            modalMsg.show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_quit_game:
                finishGame(AppConstants.CONTESTANT_ABANDONED_GAME);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPause() {
        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        Logging logger = new Logging();
        logger.debug("On Pause()....");
        logger.debug("Saving game position...");
        if (!gameFinished)
            gameSettingsService.saveGamePosition(currentQuestion);
        logger.debug("GAME SETTINGS: " + gameSettingsService.getSettings().toString());
        super.onPause();
    }

}
