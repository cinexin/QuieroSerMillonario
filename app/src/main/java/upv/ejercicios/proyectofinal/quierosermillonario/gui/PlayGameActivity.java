package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ModalMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ToastMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.model.AnswerSet;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.model.JokerStatus;
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
    private AnswerItemAdapter answerItemAdapter;


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

        JokerStatus jokerStatus = gameScore.getJokerStatus();
        if (jokerStatus != null) {

            if (jokerStatus.isAudienceJokerUsed())
                menu.removeItem(R.id.btn_audience_joker);
            if (jokerStatus.isFiftyPercentJokerUsed())
                menu.removeItem(R.id.btn_fifty_percent_joker);
            if (jokerStatus.isPhoneCallJokerUsed())
                menu.removeItem(R.id.btn_call_joker);
        }
    }

    // DONE: displayCurrentQuestion() implementation
    private void displayCurrentQuestion() {

        Logging logging = new Logging();
        logging.debug("displayCurrentQuestion() method called");
        logging.debug("GAME SCORE" + gameScoresService.getGameScore().toString());



        final QuestionItem questionItem;
        if (currentQuestion > 0)
            questionItem  =  questionItems.get(currentQuestion - 1);
        else
            questionItem =  questionItems.get(currentQuestion);

        // sample question
        TextView txtQuestion = (TextView) findViewById(R.id.txt_question);
        txtQuestion.setText(questionItem.getText());

        TextView txtQuestionNumber = (TextView) findViewById(R.id.txt_question_number) ;
        txtQuestionNumber.setText(String.valueOf(currentQuestion));


        AnswerSet answerSet = new AnswerSet(questionItem);


        this.answersGridView = (GridView) findViewById(R.id.possible_answers_grid_view);
        answerItemAdapter =  new AnswerItemAdapter(this, answerSet);
        answerItemAdapter.setAudienceJokerRequested(false);

        /* PoC: changing a random button (answer button) */
        this.answersGridView.setAdapter(answerItemAdapter);

        TextView txtPlayingFor = (TextView) findViewById(R.id.txt_playing_for);
        txtPlayingFor.setText(String.valueOf(gameScoresService.getGameScore().getPlayingFor()) );

        answersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logging logging = new Logging();
                if (view.isEnabled() == false)
                    return;
                // check if it's the correct answer
                int rightAnswer = Integer.valueOf(questionItem.getRight());
                if ((position + 1) == rightAnswer ) { // right answer :)
                    logging.debug("RIGHT ANSWER :-)");
                    view.setBackgroundColor(getResources().getColor(R.color.rightAnswer));
                    ToastMessage.rightAnswerMessage(getApplicationContext());
                    if (currentQuestion == AppConstants.NUMBER_OF_QUESTIONS) {
                        /*
                            user won the game!
                         */
                        gameScoresService.nextQuestion(); // actually user's just answered the last question!
                        finishGame(AppConstants.CONTESTANT_WON_GAME);

                    } else {
                        /*
                            update scores and let's go to next question...
                         */
                        gameScoresService.nextQuestion();
                        currentQuestion++;
                        displayCurrentQuestion();
                    }

                } else { // wrong answer :(
                    logging.debug("WRONG ANSWER :-(");
                    view.setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
                    finishGame(AppConstants.CONTESTANT_FAILED_QUESTION);
                }
            }
        });

        /*
            Special message when we're on last answer...
            ;-)
        */
        if (currentQuestion == AppConstants.NUMBER_OF_QUESTIONS) {
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            dialog.dismiss();
                            break;
                        default:
                            break;
                    }
                }
            };
            ModalMessage.ModalInfoMessage(PlayGameActivity.this, getResources().getString(R.string.msg_are_you_ready_for_your_last_answer),onClickListener);
        }
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
        Log.d("[DEBUG]" , "Game Settings -> onCreate() PlayGameActivity: " + gameSettings.toString());

        currentQuestion = gameSettings.getCurrentQuestion();

        gameScore = new GameScore();
        gameScore.setUserName(gameSettings.getUserName());
        gameScore.setLongitude(gameSettings.getLongitude());
        gameScore.setLatitude(gameSettings.getLatitude());
        JokerStatus jokerStatus = new JokerStatus();
        jokerStatus.setAudienceJokerUsed(gameSettings.isAudienceJokerUsed());
        jokerStatus.setFiftyPercentJokerUsed(gameSettings.isFiftyPercentJokerUsed());
        jokerStatus.setPhoneCallJokerUsed(gameSettings.isPhoneCallJokerUsed());
        gameScore.setJokerStatus(jokerStatus);

        if (currentQuestion > 0)
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
        Logging log = new Logging();

        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        gameSettingsService.resetGameState(); // we restart the game position to the initial state

        try {
            gameScoresService.saveScore(); // register local score
            // DONE: Perform remote high score registration on a separate thread....
            new SaveRemoteHighScoresTask().execute(gameScoresService);
        } catch (PersistenceException persistEx) {
            persistEx.printStackTrace();
            log.error("ERROR: Couldn't store score on local database!!");
        }

        gameFinished = true;
        finish();
    }

    private void finishGame(int cause) {


        if (cause == AppConstants.CONTESTANT_FAILED_QUESTION) {
            if (gameScore != null) {
                StringBuffer confirmationMsg = new StringBuffer(getResources().getString(R.string.msg_you_lose_the_game));
                confirmationMsg.append(" " + getResources().getString(R.string.lbl_score_achieved));
                confirmationMsg.append(" " + String.valueOf(gameScore.getMoneyEnsured()));
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                gameScore.setMoneyAchieved(gameScore.getMoneyEnsured());
                                gameScoresService.setGameScore(gameScore);
                                updateScoresAndQuitGame();
                            default:
                                break;
                        }
                    }
                };
                ModalMessage.ModalInfoMessage(PlayGameActivity.this, confirmationMsg.toString(), onClickListener);

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

            // DONE: Change the call to adapt to refactored class
            ModalMessage.ModalYesNoMessage(PlayGameActivity.this, R.string.abandon_game_confirmation, yesNoDialogClickListener);

        } else {
            /*
                Contestant won the game!!
            */
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            updateScoresAndQuitGame();
                            break;
                        default:
                            break;
                    }
                }
            };
            ModalMessage.ModalInfoMessage(PlayGameActivity.this, R.string.msg_you_won_the_game, onClickListener);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_quit_game:
                finishGame(AppConstants.CONTESTANT_ABANDONED_GAME);
                return true;
            case R.id.btn_call_joker:
                answerItemAdapter.setPhoneCallJokerRequested(true);
                gameScore.getJokerStatus().setPhoneCallJokerUsed(true);
                gameScore.getJokerStatus().increaseNumOfJokersUsed();
                item.setEnabled(false);
                item.setVisible(false);
                answerItemAdapter.notifyDataSetChanged();
                return true;
            case R.id.btn_audience_joker:
                answerItemAdapter.setAudienceJokerRequested(true);
                gameScore.getJokerStatus().setAudienceJokerUsed(true);
                gameScore.getJokerStatus().increaseNumOfJokersUsed();
                item.setEnabled(false);
                item.setVisible(false);
                answerItemAdapter.notifyDataSetChanged();
                return true;

            case R.id.btn_fifty_percent_joker:
                answerItemAdapter.setFiftyPercentJokerRequested(true);
                gameScore.getJokerStatus().setFiftyPercentJokerUsed(true);
                gameScore.getJokerStatus().increaseNumOfJokersUsed();
                item.setEnabled(false);
                item.setVisible(false);
                answerItemAdapter.notifyDataSetChanged();
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
        if (!gameFinished) {
            Log.d("[DEBUG]", "On Pause() -> gameScore: " + gameScore);
            JokerStatus jokerStatus = gameScore.getJokerStatus();
            if (jokerStatus != null) {
                gameSettingsService.saveGameState(currentQuestion, jokerStatus.isAudienceJokerUsed(),
                        jokerStatus.isFiftyPercentJokerUsed(), jokerStatus.isPhoneCallJokerUsed());
            } else {
                gameSettingsService.saveGameState(currentQuestion);
            }
        }
        logger.debug("GAME SETTINGS: " + gameSettingsService.getSettings().toString());
        super.onPause();
    }

    private class SaveRemoteHighScoresTask extends AsyncTask<GameScoresService, Void, Boolean> {

        @Override
        protected Boolean doInBackground(GameScoresService... params) {
            Boolean ok ;
            Logging logging = new Logging();
            GameScoresService scoresService = params[0];
            try {
                logging.debug("Calling save remote High Score remotely...");
                scoresService.saveHighScore(); // register remote high score
            } catch (IOException ioEx) {
                logging.error("There was an error while trying to register high score remotely!");
                ioEx.printStackTrace();
                ok = Boolean.FALSE;
                return ok;
            }
            ok = Boolean.TRUE;
            return ok;
        }

        @Override
        protected void onPostExecute(Boolean savedSuccessfully) {
            super.onPostExecute(savedSuccessfully);
            if (!savedSuccessfully) {
                ToastMessage.infoMessage(getApplicationContext(), getResources().getString(R.string.msg_high_scores_remote_record_error));
            } else {
                ToastMessage.infoMessage(getApplicationContext(), getResources().getString(R.string.msg_high_scores_remote_record));
            }
        }
    }
}
