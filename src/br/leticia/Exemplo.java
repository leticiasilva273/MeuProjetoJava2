package br.leticia;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
public class Exemplo extends WeilGraficos {
    private final Random random = new Random();
    private final int quantidade = 11;
    private final float[] posicao = new float[quantidade];
    private final float[] altura = new float[quantidade];
    private final float[] tamanho = new float[quantidade];
    private final float[] fase = new float[quantidade];
    private final float[] velocidadeVento = new float[quantidade];
    private final float[] inclinacaoAtual = new float[quantidade];
    private final int[] tipo = new int[quantidade];
    private final int horario = random.nextInt(3);
    private final int quantidadeNuvens = 6;
    private final float[] nuvemX = new float[quantidadeNuvens];
    private final float[] nuvemY = new float[quantidadeNuvens];
    private final float[] nuvemTamanho = new float[quantidadeNuvens];
    private final float[] nuvemVelocidade = new float[quantidadeNuvens];
    private final int quantidadeEstrelas = 90;
    private final float[] estrelaX = new float[quantidadeEstrelas];
    private final float[] estrelaY = new float[quantidadeEstrelas];
    private final float[] estrelaBrilho = new float[quantidadeEstrelas];
    private final int quantidadeGrama = 100;
    private final int[] gramaX = new int[quantidadeGrama];
    private final int[] gramaY = new int[quantidadeGrama];
    private final int[] gramaAltura = new int[quantidadeGrama];
    private float deslocamentoChuva = 0;
    private final int quantidadeGotas = 650;
    private final float[] gotasX = new float[quantidadeGotas];
    private final float[] gotasY = new float[quantidadeGotas];
    private final float[] gotasVelocidadeX = new float[quantidadeGotas];
    private final float[] gotasVelocidadeY = new float[quantidadeGotas];
    private final boolean[] gotasDesviadas = new boolean[quantidadeGotas];
    private final float[] gotasLado = new float[quantidadeGotas];
    private float tempoAnimacao = 0;
    public Exemplo() {
        super("Jardim Animado", 1000, 700);
        quadrosPorSegundo = 24;
        for (int i = 0; i < quantidadeGotas; i++) {
            gotasX[i] = random.nextFloat() * 1000;
            gotasY[i] = random.nextFloat() * 700;
            gotasVelocidadeX[i] = (random.nextFloat() - 0.5f) * 0.6f;
            gotasVelocidadeY[i] = 2.5f + random.nextFloat() * 2.0f;
            gotasDesviadas[i] = false;
            gotasLado[i] = 0;
        }
        for (int i = 0; i < quantidade; i++) {
            posicao[i] = 0.05f + random.nextFloat() * 0.90f;
            altura[i] = 0.35f + random.nextFloat() * 0.35f;
            tamanho[i] = 0.8f + random.nextFloat() * 0.7f;
            fase[i] = random.nextFloat() * 6.28f;
            velocidadeVento[i] = 0.7f + random.nextFloat() * 0.8f;
            tipo[i] = random.nextInt(7);
        }
        for (int i = 0; i < quantidadeNuvens; i++) {
            nuvemX[i] = random.nextFloat() * 1100;
            nuvemY[i] = 40 + random.nextFloat() * 180;
            nuvemTamanho[i] = 0.7f + random.nextFloat() * 0.9f;
            nuvemVelocidade[i] = 0.2f + random.nextFloat() * 0.5f;
        }
        for (int i = 0; i < quantidadeEstrelas; i++) {
            estrelaX[i] = random.nextFloat();
            estrelaY[i] = random.nextFloat() * 0.65f;
            estrelaBrilho[i] = 0.4f + random.nextFloat() * 0.6f;
        }
        for (int i = 0; i < quantidadeGrama; i++) {
            gramaX[i] = random.nextInt(1000);
            gramaY[i] = random.nextInt(50);
            gramaAltura[i] = 4 + random.nextInt(10);
        }
    }
    @Override
    public void onDraw(Graphics2D g, double tempo) {
        float w = getWidth();
        float h = getHeight();
        tempoAnimacao += 1.0f / 24.0f;
        desenharCeu(g, w, h);
        desenharSolOuLua(g, w, h);
        if (horario == 2) {
            desenharEstrelas(g, w, h);
        }
        desenharNuvens(g, w, tempoAnimacao);
        desenharMontanhas(g, w, h);
        desenharChao(g, w, h);
        for (int i = 0; i < quantidade; i++) {
            desenharFlor(g, i, w, h, tempoAnimacao);
        }
        desenharChuva(g, w, h);
    }
    private void desenharCeu(Graphics2D g, float w, float h) {
        Color cima;
        Color baixo;
        if (horario == 0) {
            cima = new Color(100, 185, 245);
            baixo = new Color(220, 240, 255);
        } else if (horario == 1) {
            cima = new Color(245, 140, 105);
            baixo = new Color(255, 215, 150);
        } else {
            cima = new Color(15, 25, 65);
            baixo = new Color(65, 75, 125);
        }
        GradientPaint ceu = new GradientPaint(
            0, 0, cima,
            0, h * 0.85f, baixo
        );
        g.setPaint(ceu);
        g.fillRect(0, 0, (int) w, (int) h);
    }
    private void desenharSolOuLua(Graphics2D g, float w, float h) {
        float x = w * 0.82f;
        float y = h * 0.16f;
        float raio = 42;
        if (horario == 2) {
            g.setColor(new Color(255, 245, 190));
            g.fillOval((int) (x - raio), (int) (y - raio),
                       (int) (raio * 2), (int) (raio * 2));
            g.setColor(new Color(35, 45, 85));
            g.fillOval((int) (x - raio * 0.45f),
                       (int) (y - raio * 1.15f),
                       (int) (raio * 1.7f),
                       (int) (raio * 1.7f));
        } else {
            g.setColor(new Color(255, 220, 90));
            g.fillOval((int) (x - raio), (int) (y - raio),
                       (int) (raio * 2), (int) (raio * 2));
            g.setColor(new Color(255, 240, 160, 100));
            g.fillOval((int) (x - raio - 12), (int) (y - raio - 12),
                       (int) (raio * 2 + 24), (int) (raio * 2 + 24));
        }
    }
    private void desenharEstrelas(Graphics2D g, float w, float h) {
        for (int i = 0; i < quantidadeEstrelas; i++) {
            float brilho = estrelaBrilho[i]
                * (0.65f + 0.35f * (float) Math.sin(tempoAnimacao * 2 + i));
            int alpha = Math.max(50, Math.min(255, (int) (brilho * 255)));
            g.setColor(new Color(255, 255, 220, alpha));
            int x = (int) (estrelaX[i] * w);
            int y = (int) (estrelaY[i] * h);
            g.fillOval(x, y, 3, 3);
        }
    }
    private void desenharNuvens(Graphics2D g, float w, float tempo) {
        for (int i = 0; i < quantidadeNuvens; i++) {
            nuvemX[i] += nuvemVelocidade[i] * 0.5f;
            if (nuvemX[i] > w + 150) {
                nuvemX[i] = -150;
            }
            float x = nuvemX[i];
            float y = nuvemY[i];
            float s = nuvemTamanho[i];
            Color cor;
            if (horario == 2) {
                cor = new Color(100, 110, 145, 190);
            } else {
                cor = new Color(255, 255, 255, 220);
            }
            g.setColor(cor);
            g.fillOval((int) x, (int) y, (int) (75 * s), (int) (35 * s));
            g.fillOval((int) (x + 20 * s), (int) (y - 18 * s),
                       (int) (45 * s), (int) (45 * s));
            g.fillOval((int) (x + 45 * s), (int) (y - 10 * s),
                       (int) (45 * s), (int) (40 * s));
        }
    }
    private void desenharMontanhas(Graphics2D g, float w, float h) {
        int base = (int) (h * 0.78f);
        g.setColor(new Color(95, 145, 115));
        Polygon montanha1 = new Polygon();
        montanha1.addPoint(0, base);
        montanha1.addPoint((int) (w * 0.25f), (int) (h * 0.40f));
        montanha1.addPoint((int) (w * 0.52f), base);
        g.fillPolygon(montanha1);
        g.setColor(new Color(75, 125, 100));
        Polygon montanha2 = new Polygon();
        montanha2.addPoint((int) (w * 0.35f), base);
        montanha2.addPoint((int) (w * 0.68f), (int) (h * 0.43f));
        montanha2.addPoint((int) w, base);
        g.fillPolygon(montanha2);
    }
    private void desenharChao(Graphics2D g, float w, float h) {
        int y = (int) (h * 0.78f);
        GradientPaint grama = new GradientPaint(
            0, y, new Color(75, 155, 70),
            0, h, new Color(30, 100, 45)
        );
        g.setPaint(grama);
        g.fillRect(0, y, (int) w, (int) h - y);
        g.setColor(new Color(130, 190, 90));
        for (int i = 0; i < quantidadeGrama; i++) {
            int x = gramaX[i] % Math.max(1, (int) w);
            int yGrama = y + gramaY[i];
            g.drawLine(x, yGrama,
                       x - 2, yGrama - gramaAltura[i]);
        }
    }
    private void desenharFlor(Graphics2D g, int i, float w, float h, double tempo) {
        float x = posicao[i] * w;
        float base = h * 0.88f;
        float alturaFlor = altura[i] * h * 0.48f;
        float s = tamanho[i] * 42;
        float guardaX = getMouseX();
        float baseGuardaY = getMouseY() - 35.0f;
        float raioCobertura = 65.0f;
        float distanciaX = x - guardaX;
        float topoY = base - alturaFlor;
        float superficieGuarda = superficieGuardaChuva(
            guardaX, baseGuardaY, x, raioCobertura
        );
        boolean protegida = Math.abs(distanciaX) <= raioCobertura
                         && topoY >= superficieGuarda;
        float balanco = (float) Math.sin(tempo * 2.5f * velocidadeVento[i] + fase[i]) * 42;
        float rajada = (float) Math.sin(tempo * 4.0f + fase[i]) * 8;
        float inclinacaoAlvo = protegida ? 0.0f : balanco + rajada;
        inclinacaoAtual[i] += (inclinacaoAlvo - inclinacaoAtual[i]) * 0.08f;
        float inclinacao = inclinacaoAtual[i];
        float topoX = x + inclinacao;
        Graphics2D caule = (Graphics2D) g.create();
        caule.setColor(new Color(35, 100, 45));
        caule.setStroke(new BasicStroke(
            4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND
        ));
        caule.draw(new QuadCurve2D.Float(
            x, base,
            x + inclinacao * 0.45f, base - alturaFlor * 0.55f,
            topoX, topoY
        ));
        caule.dispose();
        desenharFolha(
            g, x + inclinacao * 0.20f,
            base - alturaFlor * 0.32f,
            -1, alturaFlor * 0.20f, tempo, inclinacao
        );
        desenharFolha(
            g, x + inclinacao * 0.55f,
            base - alturaFlor * 0.65f,
            1, alturaFlor * 0.18f, tempo, inclinacao
        );
        Graphics2D flor = (Graphics2D) g.create();
        flor.translate(topoX, topoY);
        flor.rotate(inclinacao * 0.012);
        switch (tipo[i]) {
            case 0:
                desenharMargarida(flor, s);
                break;
            case 1:
                desenharGirassol(flor, s);
                break;
            case 2:
                desenharRosa(flor, s);
                break;
            case 3:
                desenharTulipa(flor, s);
                break;
            case 4:
                desenharCrisantemo(flor, s);
                break;
            case 5:
                desenharJasmim(flor, s);
                break;
            case 6:
                desenharLavanda(flor, s);
                break;
            default:
                desenharMargarida(flor, s);
                break;
        }
        flor.dispose();
    }
    private void desenharFolha(Graphics2D g, float x, float y, int lado,
                               float tamanho, double tempo, float inclinacao) {
        Graphics2D folha = (Graphics2D) g.create();
        folha.translate(x, y);
        folha.rotate(lado * 0.45 + inclinacao * 0.008
                     + Math.sin(tempo * 2 + x) * 0.08);
        folha.setColor(new Color(45, 145, 60));
        Polygon forma = new Polygon();
        forma.addPoint(0, 0);
        forma.addPoint((int) (tamanho * lado * 0.45f), (int) (-tamanho * 0.45f));
        forma.addPoint((int) (tamanho * lado), 0);
        forma.addPoint((int) (tamanho * lado * 0.45f), (int) (tamanho * 0.45f));
        folha.fillPolygon(forma);
        folha.setColor(new Color(105, 185, 85));
        folha.setStroke(new BasicStroke(1.5f));
        folha.drawLine(0, 0, (int) (tamanho * lado), 0);
        folha.dispose();
    }
    private void desenharMargarida(Graphics2D g, float s) {
        petalas(g, 12, s * 0.38f, Color.WHITE);
        centro(g, s * 0.20f, new Color(255, 205, 35));
    }
    private void desenharGirassol(Graphics2D g, float s) {
        petalas(g, 16, s * 0.43f, new Color(255, 185, 20));
        petalas(g, 12, s * 0.30f, new Color(255, 220, 45));
        centro(g, s * 0.27f, new Color(105, 65, 30));
    }
    private void desenharRosa(Graphics2D g, float s) {
        petalas(g, 10, s * 0.38f, new Color(190, 35, 75));
        petalas(g, 8, s * 0.25f, new Color(235, 65, 105));
        centro(g, s * 0.13f, new Color(155, 25, 65));
    }
    private void desenharTulipa(Graphics2D g, float s) {
        g.setColor(new Color(220, 45, 85));
        Polygon esquerda = new Polygon();
        esquerda.addPoint(0, 0);
        esquerda.addPoint((int) (-s * 0.45f), (int) (-s * 0.65f));
        esquerda.addPoint((int) (-s * 0.38f), (int) (-s * 0.05f));
        esquerda.addPoint((int) (-s * 0.18f), (int) (s * 0.25f));
        g.fillPolygon(esquerda);
        Polygon direita = new Polygon();
        direita.addPoint(0, 0);
        direita.addPoint((int) (s * 0.45f), (int) (-s * 0.65f));
        direita.addPoint((int) (s * 0.38f), (int) (-s * 0.05f));
        direita.addPoint((int) (s * 0.18f), (int) (s * 0.25f));
        g.fillPolygon(direita);
        Polygon meio = new Polygon();
        meio.addPoint(0, (int) (s * 0.25f));
        meio.addPoint((int) (-s * 0.25f), (int) (-s * 0.35f));
        meio.addPoint(0, (int) (-s * 0.75f));
        meio.addPoint((int) (s * 0.25f), (int) (-s * 0.35f));
        g.setColor(new Color(245, 85, 115));
        g.fillPolygon(meio);
        g.setColor(new Color(255, 175, 175));
        g.drawLine(0, (int) (-s * 0.55f), 0, (int) (s * 0.10f));
    }
    private void desenharCrisantemo(Graphics2D g, float s) {
        petalas(g, 20, s * 0.42f, new Color(220, 100, 180));
        petalas(g, 16, s * 0.30f, new Color(245, 145, 205));
        centro(g, s * 0.16f, new Color(255, 210, 60));
    }
    private void desenharJasmim(Graphics2D g, float s) {
        petalas(g, 6, s * 0.42f, new Color(255, 255, 240));
        centro(g, s * 0.12f, new Color(255, 220, 90));
    }
    private void desenharLavanda(Graphics2D g, float s) {
        g.setColor(new Color(100, 65, 165));
        for (int i = 0; i < 7; i++) {
            float y = -s * 0.50f + i * s * 0.12f;
            Polygon petala = new Polygon();
            petala.addPoint((int) (-s * 0.05f), (int) y);
            petala.addPoint((int) (-s * 0.25f), (int) (y - s * 0.10f));
            petala.addPoint((int) (-s * 0.18f), (int) (y + s * 0.08f));
            petala.addPoint((int) (-s * 0.02f), (int) (y + s * 0.12f));
            g.fillPolygon(petala);
            Polygon petala2 = new Polygon();
            petala2.addPoint((int) (s * 0.05f), (int) (y - s * 0.02f));
            petala2.addPoint((int) (s * 0.25f), (int) (y - s * 0.12f));
            petala2.addPoint((int) (s * 0.20f), (int) (y + s * 0.08f));
            petala2.addPoint((int) (s * 0.02f), (int) (y + s * 0.12f));
            g.fillPolygon(petala2);
        }
    }
    private void petalas(Graphics2D g, int quantidadePetalas,
                         float raio, Color cor) {
        g.setColor(cor);
        for (int i = 0; i < quantidadePetalas; i++) {
            double angulo = Math.PI * 2 * i / quantidadePetalas;
            Graphics2D petala = (Graphics2D) g.create();
            petala.rotate(angulo);
            Polygon forma = new Polygon();
            forma.addPoint(0, (int) (-raio * 1.35f));
            forma.addPoint((int) (raio * 0.30f), (int) (-raio * 0.55f));
            forma.addPoint(0, (int) (-raio * 0.15f));
            forma.addPoint((int) (-raio * 0.30f), (int) (-raio * 0.55f));
            petala.fillPolygon(forma);
            petala.dispose();
        }
    }
    private void centro(Graphics2D g, float raio, Color cor) {
        g.setColor(cor);
        Polygon centro = new Polygon();
        for (int i = 0; i < 12; i++) {
            double angulo = Math.PI * 2 * i / 12;
            float tamanhoCentro = (i % 2 == 0) ? raio : raio * 0.72f;
            int x = (int) (Math.cos(angulo) * tamanhoCentro);
            int y = (int) (Math.sin(angulo) * tamanhoCentro);
            centro.addPoint(x, y);
        }
        g.fillPolygon(centro);
    }
    private void desenharChuva(Graphics2D g, float w, float h) {
        float mouseX = getMouseX();
        float mouseY = getMouseY();
        float raioGuardaChuva = 73.0f;
        float baseY = mouseY - 35.0f;
        g.setColor(new Color(190, 220, 255, 190));
        g.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < quantidadeGotas; i++) {
            float yAnterior = gotasY[i];
            if (gotasDesviadas[i]) {
                gotasX[i] += gotasLado[i] * 1.65f;
                if (Math.abs(gotasX[i] - mouseX) < raioGuardaChuva) {
                    gotasY[i] = superficieGuardaChuva(mouseX, baseY, gotasX[i], raioGuardaChuva);
                } else {
                    gotasX[i] = mouseX + gotasLado[i] * raioGuardaChuva;
                    gotasY[i] = baseY;
                    gotasDesviadas[i] = false;
                    gotasVelocidadeX[i] = gotasLado[i] * 1.0f;
                    gotasVelocidadeY[i] = 1.8f;
                }
            } else {
                gotasVelocidadeY[i] += 0.045f;
                if (gotasVelocidadeY[i] > 9.0f) {
                    gotasVelocidadeY[i] = 9.0f;
                }
                gotasX[i] += gotasVelocidadeX[i];
                gotasY[i] += gotasVelocidadeY[i];
                float dx = gotasX[i] - mouseX;
                if (Math.abs(dx) <= raioGuardaChuva) {
                    float superficie = superficieGuardaChuva(mouseX, baseY, gotasX[i], raioGuardaChuva);
                    if (gotasVelocidadeY[i] > 0 && yAnterior <= superficie && gotasY[i] >= superficie) {
                        gotasY[i] = superficie;
                        gotasDesviadas[i] = true;
                        gotasLado[i] = dx < 0 ? -1.0f : 1.0f;
                        if (Math.abs(dx) < 1.5f) {
                            gotasLado[i] = random.nextBoolean() ? -1.0f : 1.0f;
                        }
                    }
                }
            }
            if (gotasDesviadas[i]) {
                g.drawLine((int) gotasX[i], (int) gotasY[i],
                           (int) (gotasX[i] + gotasLado[i]), (int) (gotasY[i] + 2));
            } else {
                g.drawLine((int) gotasX[i], (int) gotasY[i],
                           (int) (gotasX[i] - 1 + gotasVelocidadeX[i]),
                           (int) (gotasY[i] + 10));
            }
            if (gotasY[i] > h + 25 || gotasX[i] < -25 || gotasX[i] > w + 25) {
                gotasX[i] = random.nextFloat() * w;
                gotasY[i] = -random.nextFloat() * (h + 100);
                gotasVelocidadeX[i] = (random.nextFloat() - 0.5f) * 0.7f;
                gotasVelocidadeY[i] = 2.0f + random.nextFloat() * 3.0f;
                gotasDesviadas[i] = false;
                gotasLado[i] = 0;
            }
        }
        desenharGuardaChuva(g, mouseX, mouseY);
    }
    private float superficieGuardaChuva(float centroX, float baseY, float gotaX, float raio) {
        float dx = gotaX - centroX;
        float dentroDaRaiz = Math.max(0.0f, raio * raio - dx * dx);
        return baseY - (float) Math.sqrt(dentroDaRaiz);
    }
    private void desenharGuardaChuva(Graphics2D g, float x, float y) {
        Graphics2D guarda = (Graphics2D) g.create();
        float raio = 65.0f;
        float baseY = y - 35.0f;
        guarda.setColor(new Color(90, 55, 35));
        guarda.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        guarda.drawLine((int) x, (int) baseY, (int) x, (int) (y - 12));
        guarda.draw(new QuadCurve2D.Float(x, y - 12, x, y, x + 12, y));
        Arc2D.Float arco = new Arc2D.Float(
            x - raio, baseY - raio, raio * 2, raio * 2,
            0, 180, Arc2D.OPEN
        );
        Path2D.Float cobertura = new Path2D.Float();
        cobertura.append(arco, false);
        cobertura.lineTo(x - raio, baseY);
        cobertura.closePath();
        guarda.setColor(new Color(220, 65, 95));
        guarda.fill(cobertura);
        guarda.setColor(new Color(145, 35, 65));
        guarda.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        guarda.draw(arco);
        guarda.drawLine((int) (x - raio), (int) baseY, (int) (x + raio), (int) baseY);
        guarda.setColor(new Color(255, 190, 200));
        guarda.setStroke(new BasicStroke(1.6f));
        guarda.drawLine((int) x, (int) (baseY - raio), (int) x, (int) baseY);
        guarda.drawLine((int) x, (int) (baseY - raio), (int) (x - raio * 0.72f), (int) (baseY - raio * 0.69f));
        guarda.drawLine((int) x, (int) (baseY - raio), (int) (x + raio * 0.72f), (int) (baseY - raio * 0.69f));
        guarda.dispose();
    }
    public static void main(String[] args) {
        Exemplo jardim = new Exemplo();
        jardim.iniciarGraficos();
    }
}