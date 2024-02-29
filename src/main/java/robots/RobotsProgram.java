package robots;

import robots.gui.MainApplicationFrame;

import javax.swing.SwingUtilities;

public class RobotsProgram
{
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
        frame.setVisualMainFrame();
        frame.setVisible(true);
      });
    }
}
