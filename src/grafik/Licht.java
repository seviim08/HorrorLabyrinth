package grafik;

import static org.lwjgl.opengl.GL11.*;

/**
 * Spotlicht auf Basis einer OpenGL-Lichtquelle (GL_LIGHT0 bis GL_LIGHT7).
 */
public class Licht {

	private final int nummer;

	public Licht(int nummer) {
		this.nummer = nummer;
	}

	/**
	 * Setzt alle Werte des Spots. Position und Richtung werden mit der aktuellen
	 * Modelview-Matrix umgerechnet, deshalb nach dem Setzen der Kamera aufrufen.
	 */
	public void spot(float[] position, float[] richtung, float[] farbe, float winkel, float exponent) {
		glLightfv(nummer, GL_POSITION, position);
		glLightfv(nummer, GL_SPOT_DIRECTION, richtung);
		glLightfv(nummer, GL_DIFFUSE, farbe);
		glLightfv(nummer, GL_SPECULAR, farbe);
		glLightfv(nummer, GL_AMBIENT, new float[] { 0, 0, 0, 1 });
		glLightf(nummer, GL_SPOT_CUTOFF, winkel);
		glLightf(nummer, GL_SPOT_EXPONENT, exponent);
	}

	public void setAn(boolean an) {
		if (an) {
			glEnable(nummer);
		} else {
			glDisable(nummer);
		}
	}
}
