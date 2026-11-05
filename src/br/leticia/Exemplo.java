package br.leticia;

import java.awt.Color;
import java.awt.Graphics2D;

public class Exemplo extends WeilGraficos {

    public static void main(String[] args) {
        new Exemplo().iniciarGraficos();
    }

    public Exemplo() {
        super("Exemplo", 800, 600);
    }

    int n = 0;

    @Override
    public void onDraw(Graphics2D g, double deltaTime) {
        g.setColor(Color.RED);
        g.drawOval(0, 0, n, n);
        n++;
    }
}
