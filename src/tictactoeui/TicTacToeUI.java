/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeui;

/**
 *
 * @author Do Dat
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeUI {
    private JFrame frame;
    private JButton[][] buttons;
    private TicTacToeGame game;
    private boolean againstAI;
    private char player1Symbol;
    private char player2Symbol;

    public TicTacToeUI() {
        showStartMenu();
    }

    private void showStartMenu() {
        JFrame startFrame = new JFrame("Tic-Tac-Toe - Start Menu");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(2, 1));

        JButton vsPlayerButton = new JButton("Play with another Player");
        JButton vsAIButton = new JButton("Play with AI");

        vsPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                againstAI = false;
                showSymbolSelectionMenu(startFrame);
            }
        });

        vsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                againstAI = true;
                startFrame.dispose();
                initializeGame();
            }
        });

        startFrame.add(vsPlayerButton);
        startFrame.add(vsAIButton);

        startFrame.setSize(300, 200);
        startFrame.setVisible(true);
    }

    private void showSymbolSelectionMenu(JFrame startFrame) {
        JFrame symbolFrame = new JFrame("Choose Symbols");
        symbolFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        symbolFrame.setLayout(new GridLayout(3, 2));

        JLabel player1Label = new JLabel("Player 1 Symbol:");
        JLabel player2Label = new JLabel("Player 2 Symbol:");
        String[] symbols = {"X", "O"};
        JComboBox<String> player1ComboBox = new JComboBox<>(symbols);
        JComboBox<String> player2ComboBox = new JComboBox<>(symbols);
        player2ComboBox.setSelectedIndex(1); // Default player 2 to "O"
        JButton startButton = new JButton("Start Game");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Symbol = player1ComboBox.getSelectedItem().toString().charAt(0);
                player2Symbol = player2ComboBox.getSelectedItem().toString().charAt(0);
                if (player1Symbol != player2Symbol) {
                    symbolFrame.dispose();
                    startFrame.dispose();
                    initializeGame();
                } else {
                    JOptionPane.showMessageDialog(symbolFrame, "Symbols must be different!");
                }
            }
        });

        symbolFrame.add(player1Label);
        symbolFrame.add(player1ComboBox);
        symbolFrame.add(player2Label);
        symbolFrame.add(player2ComboBox);
        symbolFrame.add(new JLabel()); // Empty cell for layout
        symbolFrame.add(startButton);

        symbolFrame.setSize(300, 150);
        symbolFrame.setVisible(true);
    }

    private void initializeGame() {
        game = new TicTacToeGame(againstAI);
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (buttons[row][col].getText().equals("-")) {
                            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer()));
                            game.placeMark(row, col);
                            if (game.checkForWin()) {
                                JOptionPane.showMessageDialog(frame, "Player " + game.getCurrentPlayer() + " wins!");
                                resetBoard();
                            } else if (game.isBoardFull()) {
                                JOptionPane.showMessageDialog(frame, "The game is a tie!");
                                resetBoard();
                            } else {
                                game.changePlayer();
                                if (againstAI && game.getCurrentPlayer() == 'O') {
                                    game.aiMove();
                                    updateBoard();
                                    if (game.checkForWin()) {
                                        JOptionPane.showMessageDialog(frame, "Player " + game.getCurrentPlayer() + " wins!");
                                        resetBoard();
                                    } else if (game.isBoardFull()) {
                                        JOptionPane.showMessageDialog(frame, "The game is a tie!");
                                        resetBoard();
                                    } else {
                                        game.changePlayer();
                                    }
                                }
                            }
                        }
                    }
                });
                frame.add(buttons[i][j]);
            }
        }

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void updateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(String.valueOf(game.getBoardValue(i, j)));
            }
        }
    }

    public void resetBoard() {
        game.initializeBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToeUI();
    }
}



