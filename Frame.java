import javax.swing.JFrame;

public class Frame extends JFrame {
    public Frame() {
        this.setTitle("Game of Life");
        this.add(new Game());
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
