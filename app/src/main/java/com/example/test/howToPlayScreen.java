package com.example.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class howToPlayScreen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_how_to_play_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHowToPara(view);
    }

    public void setHowToPara(View view) {
        TextView howToParaView = (TextView) view.findViewById(R.id.howToPara);

        String howToPara = "The game contains a grid of 4x3 (rows x columns) and a count down timer set" +
                " at 30s. The boxes in the grid will change colour and clicking each coloured boxes" +
                " affects the game differently. The Green Box will reward the player with a point." +
                " The Red Box will reduce two points from the player's score. The Blue Box will increase" +
                " the count down timer by 10s whereas the Yellow Box will reduce it by 10s. The Black Box" +
                " will reduce the time by 30s and erase all points scored by the user. The White Box" +
                " will increase the time by 1 min and double the points scored by the user.";

        howToParaView.setText(howToPara);
    }
}