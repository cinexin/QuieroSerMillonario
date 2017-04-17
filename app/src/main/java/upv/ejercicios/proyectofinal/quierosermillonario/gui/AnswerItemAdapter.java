package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.model.AnswerSet;

/**
 * Created by migui on 0013.
 */

public class AnswerItemAdapter extends BaseAdapter {

    private Context context;
    AnswerSet answerSet;
    private boolean audienceJokerRequested ;
    private boolean fiftyPercentJokerRequested;
    private boolean phoneCallJokerRequested;

    public AnswerItemAdapter(Context context, AnswerSet answerSet) {
        this.context = context;
        this.answerSet = answerSet;
        this.audienceJokerRequested = false;
        this.fiftyPercentJokerRequested = false;
        this.phoneCallJokerRequested = false;
    }

    public boolean isAudienceJokerRequested() {
        return audienceJokerRequested;
    }

    public void setAudienceJokerRequested(boolean audienceJokerRequested) {
        this.audienceJokerRequested = audienceJokerRequested;
    }

    public boolean isFiftyPercentJokerRequested() {
        return fiftyPercentJokerRequested;
    }

    public void setFiftyPercentJokerRequested(boolean fiftyPercentJokerRequested) {
        this.fiftyPercentJokerRequested = fiftyPercentJokerRequested;
    }

    public boolean isPhoneCallJokerRequested() {
        return phoneCallJokerRequested;
    }

    public void setPhoneCallJokerRequested(boolean phoneCallJokerRequested) {
        this.phoneCallJokerRequested = phoneCallJokerRequested;
    }

    @Override
    public int getCount() {
        return answerSet.getPossibleAnswers().size();
    }

    @Override
    public Object getItem(int position) {
        return answerSet.getPossibleAnswers().get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView ;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.answer_item,parent, false);

        } else {
            rowView = convertView;
        }

        // Set data into the possible answers view....
        Button answerButton = (Button) rowView.findViewById(R.id.btn_answer_item);
        String answerItem = this.answerSet.getPossibleAnswers().get(position);

        answerButton.setText(answerItem);

        /*
            DONE: Complete Jokers management....
          */

        // audience Joker...
        if (this.audienceJokerRequested && position == (answerSet.getAnswerWhenAudienceJoker() - 1)) {
            answerButton.setBackgroundColor(rowView.getResources().getColor(R.color.suggestedAnswer));
        }

        // phone call Joker....
        if (this.phoneCallJokerRequested && position == (answerSet.getAnswerWhenPhoneCallJoker() -1)) {
            answerButton.setBackgroundColor(rowView.getResources().getColor(R.color.suggestedAnswer));
        }

        // 50% joker...
        if (this.fiftyPercentJokerRequested &&
                ( position == (answerSet.getAnswer1ToDiscardWhenFiftyPercentJoker() -1) ||
                        position == (answerSet.getAnswer2ToDiscardWhenFiftyPercentJoker() -1)
                )
            ) {
            answerButton.setAlpha((float) 0.5);
            answerButton.setFocusableInTouchMode(false);
            answerButton.setEnabled(false);
            ViewGroup layout = (ViewGroup) answerButton.getParent();
            if (layout != null)
                layout.setEnabled(false);

        }


        return rowView;
    }



}
