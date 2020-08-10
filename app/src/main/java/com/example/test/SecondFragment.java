package com.example.test;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SecondFragment extends Fragment {
    private ArrayList<Integer> buttonList = new ArrayList<>();
    private ArrayList<Button> onList = new ArrayList<>();
    private long timeLeft = 30000;
    private CountDownTimer timer;
    private View gameScreen;
    private Drawable buttonBackground;
    private Random rand = new Random();
    private double angelButtonProbability = 0.01;
    private double clickButtonProbability = 0.05;
    private double bombButtonProbability = 0.10;
    private double decreaseTimeButtonProbability = 0.40;
    private double timeButtonProbability = 0.50;
    private double decreaseScoreButtonProbability = 0.90;
    private double replacementButtonProbability = 0.25;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startGame(view);
    }

    private void startGame(View view) {
        gameScreen = view;
        TextView scoreText = (TextView) gameScreen.findViewById(R.id.scoreAchievedText);
        scoreText.setText("0");
        Button standardButton = (Button) gameScreen.findViewById(R.id.button00);
        buttonBackground = standardButton.getBackground();
        startTimer(timeLeft);

        buttonList.add(R.id.button00);
        buttonList.add(R.id.button01);
        buttonList.add(R.id.button02);
        buttonList.add(R.id.button10);
        buttonList.add(R.id.button11);
        buttonList.add(R.id.button12);
        buttonList.add(R.id.button20);
        buttonList.add(R.id.button21);
        buttonList.add(R.id.button22);
        buttonList.add(R.id.button30);
        buttonList.add(R.id.button31);
        buttonList.add(R.id.button32);

        makeGrid();
        makeClickButton(getRandomButton());
    }

    private void stopGame() {
        int finalScore = getScore();

        SecondFragmentDirections.ActionSecondFragmentToScoreScreen action = SecondFragmentDirections
                .actionSecondFragmentToScoreScreen(finalScore);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(action);
    }

    private void startTimer(long time) {
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                stopGame();
            }
        }.start();
    }

    private void stopTime() {
        timer.cancel();
    }

    private void updateTimer() {
        int minute = (int) timeLeft / 60000;
        int second = (int) timeLeft % 60000 / 1000;

        String timeLeft;

        timeLeft = "" + minute + ":";
        if (second < 10) timeLeft += "0";
        timeLeft += second;

        TextView timeLeftText = (TextView) gameScreen.findViewById(R.id.timeLeftText);
        timeLeftText.setText(timeLeft);
    }

    private Button getRandomButton() {
        return gameScreen.findViewById(buttonList.get(rand.nextInt(buttonList.size())));
    }

    private void makeGrid() {
        for (int i = 0; i < 3; i++) {
            addButton(getRandomButton());
        }
    }

    private void addButton(Button button) {
        if (!onList.contains(button)) {
            if (rand.nextDouble() < angelButtonProbability) {
                makeAngelButton(button);
            }
            else if (rand.nextDouble() < clickButtonProbability) {
                makeClickButton(button);
            }
            else if (rand.nextDouble() < bombButtonProbability) {
                makeBombButton(button);
            }
            else if (rand.nextDouble() < decreaseTimeButtonProbability) {
                makeDecreaseTimeButton(button);
            }
            else if (rand.nextDouble() < timeButtonProbability) {
                makeTimeButton(button);
            }
            else if (rand.nextDouble() < decreaseScoreButtonProbability) {
                makeDecreaseButton(button);
            }
        }
        else if (rand.nextDouble() < replacementButtonProbability) {
            addButton(getRandomButton());
        }
    }

    private void removeButton(Button button) {
        onList.remove(button);
        button.setBackground(buttonBackground);
        button.setOnClickListener(null);
    }

    private void makeClickButton(final Button clickButton) {
        onList.add(clickButton);
        clickButton.setBackgroundColor(Color.GREEN);
        clickButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                removeButton(clickButton);
                changeScore(1);
                makeClickButton(getRandomButton());
                if (rand.nextDouble() < replacementButtonProbability) {
                    makeGrid();
                }
            }
        });
    }

    private void makeTimeButton(final Button timeButton) {
        onList.add(timeButton);
        timeButton.setBackgroundColor(Color.BLUE);
        timeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                removeButton(timeButton);
                changeTime(10000);
                makeGrid();
            }
        });
    }

    private void makeDecreaseButton(final Button decreaseScoreButton) {
        onList.add(decreaseScoreButton);
        decreaseScoreButton.setBackgroundColor(Color.RED);
        decreaseScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButton(decreaseScoreButton);
                changeScore(-2);
                makeGrid();
            }
        });
    }

    private void makeDecreaseTimeButton(final Button decreaseTimeButton) {
        onList.add(decreaseTimeButton);
        decreaseTimeButton.setBackgroundColor(Color.YELLOW);
        decreaseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButton(decreaseTimeButton);
                changeTime(-10000);
                makeGrid();
            }
        });
    }

    private void makeBombButton(final Button bombButton) {
        onList.add(bombButton);
        bombButton.setBackgroundColor(Color.BLACK);
        bombButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButton(bombButton);
                changeTime(-30000);
                changeScore(-getScore());
                makeGrid();
            }
        });
    }

    private void makeAngelButton(final Button angelButton) {
        onList.add(angelButton);
        angelButton.setBackgroundColor(Color.WHITE);
        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButton(angelButton);
                changeTime(60000);
                changeScore(getScore()*2);
                makeGrid();
            }
        });
    }

    private int getScore() {
        TextView scoreText = (TextView) gameScreen.findViewById(R.id.scoreAchievedText);
        int score = Integer.parseInt((String) scoreText.getText());
        return score;
    }

    private int setScore(int score) {
        TextView scoreText = (TextView) gameScreen.findViewById(R.id.scoreAchievedText);
        scoreText.setText(Integer.toString(score));
        return score;
    }

    private void changeScore(int points) {
        int score = getScore();
        score += points;
        setScore(score);
    }

    private void changeTime(long milliseconds) {
        stopTime();
        startTimer(timeLeft + milliseconds);
    }
}