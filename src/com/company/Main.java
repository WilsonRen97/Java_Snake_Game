package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel implements KeyListener {

    public static final int cellSize = 20;
    public static final int row = 700 / cellSize;
    public static final int column = 700 / cellSize;
    private int score = 0;
    private Snake snake;
    private Fruit fruit;
    private String direction = "Right";
    private boolean allowKeyPress = true;
    int k = 0;


    public Main() {
        // create a new snake
        snake = new Snake();
        fruit = new Fruit();
        addKeyListener(this);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0,100);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("Calling paintcomponent..." + k);
        k++;
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game");
        window.setSize(700,700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (allowKeyPress) {
            if (e.getKeyCode() == 37 && !direction.equals("Right")) {
                direction= "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
