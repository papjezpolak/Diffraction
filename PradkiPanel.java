package wykres;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class PradkiPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> prazki;

    public PradkiPanel() {
        prazki = new ArrayList<>();
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE); // Ustawienie tła ekranu na białe
    }

    public void setPrazki(ArrayList<Integer> prazki) {
        this.prazki = prazki;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int prazek : prazki) {
            g.fillRect(200, prazek - 5, 5, 10); // Rysowanie prążków jako prostokątów na ekranie
        }
    }
}
