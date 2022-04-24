package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    public ArrayList<Node> snakeBody;

    public Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80, 0));
        snakeBody.add(new Node(60, 0));
        snakeBody.add(new Node(40, 0));
        snakeBody.add(new Node(20, 0));
    }

    public void drawSnake(Graphics g) {
        g.setColor(Color.RED);
        for (Node n : snakeBody) {
            if (n.x >= Main.width) {
                n.x = 0;
            }
            if (n.x < 0) {
                n.x = Main.width - Main.cellSize;
            }
            if (n.y >= Main.height) {
                n.y = 0;
            }
            if (n.y < 0) {
                n.y = Main.height - Main.cellSize;
            }
            g.drawRect(n.x, n.y, Main.cellSize, Main.cellSize);
        }
    }

    public ArrayList<Node> getSnakeBody() {
        return this.snakeBody;
    }
}
