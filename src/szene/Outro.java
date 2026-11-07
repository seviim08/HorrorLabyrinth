package szene;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Abschlussfenster, das nach dem Aufschließen der Tür erscheint.
 */
public final class Outro {

	private Outro() {
	}

	public static void zeigen() {
		SwingUtilities.invokeLater(() -> {
			JFrame fenster = new JFrame("Boo!!!");
			fenster.setSize(600, 600);
			fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenster.setLocationRelativeTo(null);
			fenster.setResizable(false);
			fenster.setLayout(null);

			JLabel text = new JLabel("Herzlichen Glückwunsch!", SwingConstants.CENTER);
			text.setFont(text.getFont().deriveFont(Font.BOLD | Font.ITALIC, 40f));
			text.setBounds(0, 0, 600, 400);
			fenster.add(text);

			JLabel bild = new JLabel(new ImageIcon(Outro.class.getResource("bild.png")));
			bild.setBounds(0, 0, 1000, 600);
			fenster.add(bild);

			fenster.setVisible(true);
		});
	}
}
