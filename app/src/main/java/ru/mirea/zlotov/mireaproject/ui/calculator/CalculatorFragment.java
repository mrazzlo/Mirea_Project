package ru.mirea.zlotov.mireaproject.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.zlotov.mireaproject.R;

public class CalculatorFragment extends Fragment {

    private CalculatorModel calculator;

    private TextView text;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        int[] numberIds = new int[] {
                R.id.zero,
                R.id.one,
                R.id.two,
                R.id.three,
                R.id.four,
                R.id.five,
                R.id.six,
                R.id.seven,
                R.id.eight,
                R.id.nine
        };

        int[] actionsIds = new int[] {
                R.id.plus,
                R.id.minus,
                R.id.multiply,
                R.id.division,
                R.id.equals
        };

        text = root.findViewById(R.id.text);

        calculator = new CalculatorModel();

        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                text.setText(calculator.getText());
            }
        };

        View.OnClickListener actionButtonOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
                text.setText(calculator.getText());
            }
        };

        for (int i = 0; i < numberIds.length; i++) {
            root.findViewById(numberIds[i]).setOnClickListener(numberButtonClickListener);
        }

        for (int i = 0; i < actionsIds.length; i++) {
            root.findViewById(actionsIds[i]).setOnClickListener(actionButtonOnclickListener);
        }

        root.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.reset();
                text.setText(calculator.getText());
            }
        });
        return root;
    }
}