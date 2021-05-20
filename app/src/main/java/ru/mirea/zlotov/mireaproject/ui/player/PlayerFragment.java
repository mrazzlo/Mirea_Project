package ru.mirea.zlotov.mireaproject.ui.player;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.mirea.zlotov.mireaproject.R;


public class PlayerFragment extends Fragment {

    Button button_start;
    Button button_stop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        button_start = root.findViewById(R.id.button_start);
        button_stop = root.findViewById(R.id.button_stop);

        View.OnClickListener startClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getContext(), AudioPlayerService.class));
            }
        };

        button_start.setOnClickListener(startClickListener);

        View.OnClickListener stopClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getContext(), AudioPlayerService.class));
            }
        };

        button_stop.setOnClickListener(stopClickListener);

        return  root;
    }
}