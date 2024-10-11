package com.example.piggame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int player1Score = 0;
    private int player2Score = 0;
    private int currentTurnScore = 0;
    private boolean isPlayer1Turn = true;
    private final int WINNING_SCORE = 100;

    private TextView txtScorePlayer1, txtScorePlayer2, txtTurnScore, txtGameMessage;
    private Button btnRoll, btnHold, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtScorePlayer1 = findViewById(R.id.txtScorePlayer1);
        txtScorePlayer2 = findViewById(R.id.txtScorePlayer2);
        txtTurnScore = findViewById(R.id.txtTurnScore);
        txtGameMessage = findViewById(R.id.txtGameMessage);
        btnRoll = findViewById(R.id.btnRoll);
        btnHold = findViewById(R.id.btnHold);
        btnReset = findViewById(R.id.btnReset);

        btnRoll.setOnClickListener(v -> rollDice());
        btnHold.setOnClickListener(v -> holdTurn());
        btnReset.setOnClickListener(v -> resetGame());
    }

    private void rollDice() {
        Random random = new Random();
        int diceValue = random.nextInt(6) + 1;

        Toast.makeText(this, "Dado: " + diceValue, Toast.LENGTH_SHORT).show();

        if (diceValue == 1) {
            currentTurnScore = 0;
            switchTurn();
            return;
        }

        currentTurnScore += diceValue;
        updateUI();
    }

    private void holdTurn() {
        if (isPlayer1Turn) {
            player1Score += currentTurnScore;
        } else {
            player2Score += currentTurnScore;
        }

        if (player1Score >= WINNING_SCORE) {
            showWinner(1);
        } else if (player2Score >= WINNING_SCORE) {
            showWinner(2);
        } else {
            currentTurnScore = 0;
            switchTurn();
        }
    }

    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        Toast.makeText(this, isPlayer1Turn ? "Turno de Jugador 1" : "Turno de Jugador 2", Toast.LENGTH_SHORT).show();
        updateUI();
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        currentTurnScore = 0;
        isPlayer1Turn = true;
        txtGameMessage.setVisibility(View.INVISIBLE);
        updateUI();
    }

    private void showWinner(int player) {
        txtGameMessage.setText("¡Jugador " + player + " ha ganado!");
        txtGameMessage.setVisibility(View.VISIBLE);
    }

    private void updateUI() {
        txtScorePlayer1.setText("Puntuación Jugador 1: " + player1Score);
        txtScorePlayer2.setText("Puntuación Jugador 2: " + player2Score);
        txtTurnScore.setText("Puntos del Turno: " + currentTurnScore);
    }
}
