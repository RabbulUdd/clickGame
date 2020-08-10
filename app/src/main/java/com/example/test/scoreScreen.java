package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class scoreScreen extends Fragment {

    private SecondFragment gameScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showScore(view);
    }

    private void showScore(View view) {
        TextView scoreText = (TextView) view.findViewById(R.id.scoreText);

        int score = scoreScreenArgs.fromBundle(getArguments()).getScore();
        String scoreMessage;

        scoreMessage = "You scored: " + score;

        scoreText.setText(scoreMessage);
    }
}