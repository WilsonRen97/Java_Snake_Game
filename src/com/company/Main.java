package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel implements KeyListener {

    public static final int cellSize = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = width / cellSize;
    public static int column = height / cellSize;
    private Snake snake;
    private Fruit fruit;
    private static String direction;
    private boolean allowKeyPress;
    private int speed = 100;
    private Timer t;
    private int score;
    private int highest_score;

    public Main() {
        read_highest_score();
        reset();
        addKeyListener(this);
    }

    public void setTimer() {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0,speed);
    }

    public void reset() {
        if (snake != null) {
            snake.snakeBody.clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        score = 0;
        setTimer();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        // check if the snake bites itself
        for (int i = 1; i < snake.snakeBody.size(); i++) {
            if (snake.snakeBody.get(i).x == snake.snakeBody.get(0).x && snake.snakeBody.get(i).y == snake.snakeBody.get(0).y) {
                allowKeyPress = false;
                t.cancel();
                t.purge();
                int response = JOptionPane.showOptionDialog(this, "Game over! Your score is " + score + ". The highest score was " + highest_score + ". Would you like to start over?", "Game Over", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
                write_a_file(score);

                switch (response) {
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        // get a black background
        g.fillRect(0, 0, width, height);
        // draw the fruit
        fruit.drawFruit(g);
        snake.drawSnake(g);

        // remove snake tail and put it in head
        int snakeX = snake.snakeBody.get(0).x;
        int snakeY = snake.snakeBody.get(0).y;
        if (direction.equals("Left")) {
            snakeX -= cellSize;
        } else if (direction.equals("Up")) {
            snakeY -= cellSize;
        } else if (direction.equals("Right")) {
            snakeX += cellSize;
        } else if (direction.equals("Down")) {
            snakeY += cellSize;
        }
        Node newHead = new Node(snakeX, snakeY);

        //if the snake eats the fruit
        if (snake.snakeBody.get(0).x == fruit.getX() && snake.snakeBody.get(0).y == fruit.getY()) {
            // first we need to draw another fruit
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++;
        } else {
            snake.snakeBody.remove(snake.snakeBody.size() - 1);
        }

        snake.snakeBody.add(0, newHead);

        allowKeyPress = true;
        requestFocusInWindow();
    }



    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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

    public void read_highest_score() {
        try {
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);
            highest_score = myReader.nextInt();
            myReader.close();
        } catch (FileNotFoundException e) {
            highest_score = 0;
            // create new file
            try {
                File myObj = new File("filename.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                }
                FileWriter myWriter = new FileWriter("filename.txt");
                myWriter.write(0);
            } catch (IOException err) {
                System.out.println("An error occurred.");
                err.printStackTrace();
            }
        }
    }


    public void write_a_file(int score) {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            if (score > highest_score) {
                myWriter.write("" + score);
                highest_score = score;
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
