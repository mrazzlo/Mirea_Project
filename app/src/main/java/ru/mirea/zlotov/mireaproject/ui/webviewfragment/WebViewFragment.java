package ru.mirea.zlotov.mireaproject.ui.webviewfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.zlotov.mireaproject.R;

public class WebViewFragment extends Fragment {
    WebView webView;
    EditText editText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_webviewfragment, container, false);
        webView = root.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.mirea.ru/");
        editText = root.findViewById(R.id.edit_link);

        View.OnClickListener homePageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://www.mirea.ru/");
            }
        };
        root.findViewById(R.id.home_b).setOnClickListener(homePageClickListener);

        View.OnClickListener searchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(editText.getText().toString());
            }
        };
        root.findViewById(R.id.search_b).setOnClickListener(searchClickListener);
        return root;
    }
}