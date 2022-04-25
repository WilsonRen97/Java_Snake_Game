package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Fruit {
    private int x;
    private int y;
    private ArrayList<Integer> snake_x;
    private ArrayList<Integer> snake_y;
    private ImageIcon img;

    public Fruit() {
        img = new ImageIcon("fruit.png");
        this.x = (int) (Math.floor(Math.random() * Main.column) * Main.cellSize);
        this.y = (int) (Math.floor(Math.random() * Main.row) * Main.cellSize);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void drawFruit(Graphics g) {
        img.paintIcon(null, g, this.x, this.y);
    }

    public void setNewLocation(Snake s) {
        // first, get 2 arrays that stores the x and y position of snakes
        snake_x = new ArrayList<>();
        snake_y = new ArrayList<>();
        for (Node n : s.getSnakeBody()) {
            snake_x.add(n.x);
        }
        for (Node n : s.getSnakeBody()) {
            snake_y.add(n.y);
        }
        int new_x;
        int new_y;
        boolean overlapping;
        do {
            new_x = (int) Math.floor(Math.random() * Main.column) * Main.cellSize;
            new_y = (int) Math.floor(Math.random() * Main.row) * Main.cellSize;
            overlapping = check_overlap(new_x, new_y);
        } while (overlapping);

        // after picking a good location, we can change x, y coordinate
        this.x = new_x;
        this.y = new_y;
    }

    // a helper function that checks if snake and fruit are overlapping
    private boolean check_overlap(int new_x, int new_y) {
        for (var j = 0; j < snake_y.size(); j++) {
            if (new_x == snake_x.get(j) && new_y == snake_y.get(j)) {
                return true;
            }
        }
        return false;
    }
}
