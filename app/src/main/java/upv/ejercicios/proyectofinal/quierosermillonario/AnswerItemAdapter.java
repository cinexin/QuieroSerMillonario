package upv.ejercicios.proyectofinal.quierosermillonario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by migui on 0013.
 */

public class AnswerItemAdapter extends BaseAdapter {

    private Context context;
    private List<String> answersList;

    public AnswerItemAdapter(Context context, List<String> answersList) {
        this.context = context;
        this.answersList = answersList;
    }

    @Override
    public int getCount() {
        return answersList.size();
    }

    @Override
    public Object getItem(int position) {
        return answersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.answer_item,parent, false);

        }

        // Set data into the possible answers view....
        Button answerButton = (Button) rowView.findViewById(R.id.btn_answer_item);
        String answerItem = this.answersList.get(position);

        answerButton.setText(answerItem);

        return rowView;
    }
}
