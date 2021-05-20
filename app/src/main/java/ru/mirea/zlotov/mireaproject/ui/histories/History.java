package ru.mirea.zlotov.mireaproject.ui.histories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.zlotov.mireaproject.R;


public class History extends Fragment {
    private RecyclerView histories;
    private ru.mirea.zlotov.mireaproject.ui.histories.AdapterCell adapterCell;
    List<ru.mirea.zlotov.mireaproject.ui.histories.Cell> listhistories;

    public History() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ru.mirea.zlotov.mireaproject.ui.histories.AppDatabase db = App.getInstance().getDatabase();
        ru.mirea.zlotov.mireaproject.ui.hihistories.HistoryDao historyDao = db.storyDao();
        histories = view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        histories.setLayoutManager(layoutManager);
        histories.setHasFixedSize(true);
        listhistories = historyDao.getAll();
        adapterCell = new AdapterCell(listhistories);
        histories.setAdapter(adapterCell);
        return view;
    }
}