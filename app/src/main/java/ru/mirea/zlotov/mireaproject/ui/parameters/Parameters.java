package ru.mirea.zlotov.mireaproject.ui.parameters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.mirea.zlotov.mireaproject.R;

import static android.content.Context.MODE_PRIVATE;

public class Parameters extends Fragment {
    private TextView textStory;
    private EditText editNewGreeting;
    private Button buttonChange;
    private String note;
    private SharedPreferences preferences;
    private final String SAVED_NOTE = "saved_тщеу";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parameters, container, false);

        textStory = root.findViewById(R.id.text_story);
        editNewGreeting = root.findViewById(R.id.edit_new_greeting);
        buttonChange = root.findViewById(R.id.button_change);
        preferences = getActivity().getPreferences(MODE_PRIVATE);

        buttonChange.setOnClickListener(changeClickListener);

        if (!preferences.getString(SAVED_NOTE, "Empty").equals("Empty"))
            textStory.setText(preferences.getString(SAVED_NOTE, "Empty"));

        return root;
    }

    View.OnClickListener changeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!editNewGreeting.getText().toString().equals("")) {
                note = editNewGreeting.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAVED_NOTE, note);
                editor.apply();
                textStory.setText(note);
            }
        }
    };
}