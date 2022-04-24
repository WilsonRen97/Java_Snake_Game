package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Node> snakeBody;

    public Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(0, 80));
        snakeBody.add(new Node(0, 60));
        snakeBody.add(new Node(0, 20));
        snakeBody.add(new Node(0, 0));
    }

    public void drawSnake(Graphics g) {
        for (Node n : snakeBody) {
            g.drawRect(n.x, n.y, Main.cellSize, Main.cellSize);
        }
    }

    public ArrayList<Node> getSnakeBody() {
        return this.snakeBody;
    }
}
