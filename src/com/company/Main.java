package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private String direction = "Right";
    private boolean allowKeyPress = true;
    private int speed = 100;

    public Main() {
        snake = new Snake();
        fruit = new Fruit();
        addKeyListener(this);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0,speed);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        // check if the snake bites itself

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
}
