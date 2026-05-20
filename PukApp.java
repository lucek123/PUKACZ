import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PukApp extends JFrame {

    private JLabel label;
    private int clickCount = 0;

    public PukApp() {
        setTitle("Puk App");
        setSize(800, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Puk");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(150, 120, 200, 40);

        add(label);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                animateAndRespawn();
            }
        });

        setVisible(true);
    }

    private void animateAndRespawn() {
        int startX = label.getX();
        int startY = label.getY();

        Timer moveTimer = new Timer(8, null);
        Timer fadeTimer = new Timer(40, null);

        final int[] dy = {0};
        final float[] alpha = {1.0f};

        moveTimer.addActionListener(ev -> {
            dy[0] -= 6;  // mocniejszy odskok w górę

            // ograniczenie, żeby nie wyszło poza okno
            int newY = Math.max(startY + dy[0], 10);

            label.setLocation(startX, newY);

            if (dy[0] < -70) {  // krótszy, dynamiczny odskok
                moveTimer.stop();
                fadeTimer.start();
            }
        });

        fadeTimer.addActionListener(ev -> {
            alpha[0] -= 0.12f;
            label.setForeground(new Color(0, 0, 0, Math.max(alpha[0], 0)));

            if (alpha[0] <= 0) {
                fadeTimer.stop();
                respawn(startX, startY);
            }
        });

        moveTimer.start();
    }

    private void respawn(int x, int y) {
        clickCount++;

        if (clickCount >= 4) {
            label.setText("czy ktos mnie slyszy?");
        } else {
            label.setText("Puk");
        }

        label.setLocation(x, y);
        label.setForeground(new Color(0, 0, 0, 255));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PukApp::new);
    }
}
