package labyrinth;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import grafik.Formen;
import grafik.Material;
import objekte.Objekt;

/**
 * Boden und Mauern des Labyrinths. Damit nicht immer das ganze Labyrinth
 * gezeichnet wird, gibt es pro Gang einen eigenen Abschnitt, der nur die von
 * dort sichtbaren Mauern enthält.
 * <p>
 * Mauern und Boden werden aus 0,5 breiten Kacheln zusammengesetzt, die nur
 * einmal als Display-Liste angelegt und dann wiederverwendet werden.
 */
public class Labyrinth extends Objekt {

	private static final float KACHEL = 0.5f;
	private static final float HOEHE = 3;
	private static final float FEIN = 100;

	private static final Material BODEN = new Material(
			new float[] { 0.19f, 0.07f, 0.02f, 1 },
			new float[] { 0.70f, 0.27f, 0.08f, 1 },
			new float[] { 0.26f, 0.14f, 0.09f, 1 });

	private static final Material MAUER = new Material(
			new float[] { 0.25f, 0.21f, 0.21f, 1 },
			new float[] { 0.99f, 0.83f, 0.83f, 1 },
			new float[] { 0.30f, 0.30f, 0.30f, 1 });

	private static int kachelBoden = -1;
	private static int kachelWandX = -1;
	private static int kachelWandZ = -1;

	private final List<float[]> boeden = new ArrayList<>();
	private final List<float[]> waendeX = new ArrayList<>();
	private final List<float[]> waendeZ = new ArrayList<>();

	public static Labyrinth komplett() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 14.5f)
				.boden(0, 2, -2, 0)
				.wandX(0, 2, -2, 1)
				.wandZ(-2, 0, 2, -1)
				.wandZ(-2, 0, 0, 1)
				.wandX(2, 6.5f, 0, 1)
				.wandX(-7.5f, 0, 0, 1)
				.wandZ(0, 14.5f, -7.5f, 1)
				.wandX(-7.5f, 6.5f, 14.5f, -1)
				.wandZ(0, 12.5f, 6.5f, -1)
				.wandZ(12.5f, 14.5f, -5, 1)
				.wandZ(12.5f, 14.5f, -5.5f, -1)
				.wandX(-5.5f, -5, 12.5f, -1)
				.wandX(-0.5f, 4.5f, 2, -1)
				.wandX(-0.5f, 4.5f, 2.5f, 1)
				.wandX(4.5f, 6.5f, 4, -1)
				.wandX(4.5f, 6.5f, 4.5f, 1)
				.wandZ(2, 7.5f, 4, -1)
				.wandZ(2, 7.5f, 4.5f, 1)
				.wandZ(2, 2.5f, -0.5f, -1)
				.wandX(4, 4.5f, 7.5f, 1)
				.wandX(-5.5f, -2.5f, 2, -1)
				.wandX(-5.5f, -3, 10.5f, 1)
				.wandX(-5, -2.5f, 10, -1)
				.wandX(-5, 1.5f, 5, 1)
				.wandX(-2.5f, 2, 4.5f, -1)
				.wandX(-0.5f, 4, 10, 1)
				.wandX(-0.5f, 4, 9.5f, -1)
				.wandX(-3, 6.5f, 12.5f, 1)
				.wandX(-2.5f, 6.5f, 12, -1)
				.wandZ(2, 10.5f, -5.5f, -1)
				.wandZ(5, 10, -5, 1)
				.wandZ(10.5f, 12.5f, -3, -1)
				.wandZ(10, 12, -2.5f, 1)
				.wandZ(4.5f, 9.5f, 2, 1)
				.wandZ(5, 9.5f, 1.5f, -1)
				.wandZ(9.5f, 10, -0.5f, -1)
				.wandZ(9.5f, 10, 4, 1)
				.wandZ(2, 4.5f, -2.5f, 1);
	}

	public static Labyrinth eingang() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 14.5f)
				.boden(0, 2, -2, 0)
				.wandX(0, 2, -2, 1)
				.wandZ(-2, 0, 2, -1)
				.wandZ(-2, 0, 0, 1)
				.wandX(2, 6.5f, 0, 1)
				.wandX(-7.5f, 0, 0, 1)
				.wandZ(0, 14.5f, -7.5f, 1)
				.wandX(-7.5f, -5.5f, 14.5f, -1)
				.wandZ(0, 4, 6.5f, -1)
				.wandZ(12.5f, 14.5f, -5.5f, -1)
				.wandX(-5.5f, -5, 12.5f, -1)
				.wandX(-0.5f, 4.5f, 2, -1)
				.wandX(4.5f, 6.5f, 4, -1)
				.wandZ(2, 7.5f, 4.5f, 1)
				.wandZ(2, 7.5f, 4, -1)
				.wandZ(2, 2.5f, -0.5f, -1)
				.wandX(-5.5f, -2.5f, 2, -1)
				.wandX(-2.5f, 2, 4.5f, -1)
				.wandZ(2, 4.5f, -2.5f, 1);
	}

	public static Labyrinth linkerGang() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 14.5f)
				.boden(0, 2, -2, 0)
				.wandZ(-2, 0, 2, -1)
				.wandX(2, 6.5f, 0, 1)
				.wandX(-7.5f, 0, 0, 1)
				.wandZ(0, 14.5f, -7.5f, 1)
				.wandX(-7.5f, 6.5f, 14.5f, -1)
				.wandZ(0, 3, 6.5f, -1)
				.wandZ(12.5f, 14.5f, -5, 1)
				.wandZ(12.5f, 14.5f, -5.5f, -1)
				.wandX(-5.5f, -5, 12.5f, -1)
				.wandX(-0.5f, 4.5f, 2, -1)
				.wandZ(2, 2.5f, -0.5f, -1)
				.wandZ(2, 7.5f, 4, -1)
				.wandX(-5.5f, -2.5f, 2, -1)
				.wandX(-5.5f, -3, 10.5f, 1)
				.wandZ(2, 10.5f, -5.5f, -1)
				.wandZ(10.5f, 12.5f, -3, -1);
	}

	public static Labyrinth tuerGang() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 1, 14.5f)
				.wandZ(1, 14.5f, -7.5f, 1)
				.wandX(-5, 6.5f, 14.5f, -1)
				.wandZ(12.5f, 14.5f, -5, 1)
				.wandX(-5.5f, -5, 12.5f, -1)
				.wandX(-5.5f, -3, 10.5f, 1)
				.wandX(-3, 6.5f, 12.5f, 1)
				.wandZ(10.5f, 12.5f, -3, -1);
	}

	public static Labyrinth vorDerSpinne() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 14.5f)
				.boden(0, 2, -2, 0)
				.wandX(0, 2, -2, 1)
				.wandZ(-2, 0, 2, -1)
				.wandX(2, 6.5f, 0, 1)
				.wandX(-7.5f, 0, 0, 1)
				.wandZ(0, 14.5f, -7.5f, 1)
				.wandZ(0, 12, 6.5f, -1)
				.wandX(-0.5f, 4.5f, 2.5f, 1)
				.wandZ(2, 7.5f, 4, -1)
				.wandZ(2, 2.5f, -0.5f, -1)
				.wandX(-2.5f, 2, 4.5f, -1)
				.wandX(2, 4, 9.5f, -1)
				.wandX(4, 6.5f, 12, -1)
				.wandZ(4.5f, 9.5f, 2, 1)
				.wandZ(2, 4.5f, -2.5f, 1);
	}

	public static Labyrinth spinnenGang() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 12)
				.wandX(-7.5f, 0, 0, 1)
				.wandZ(4.5f, 12, 6.5f, -1)
				.wandX(-0.5f, 4.5f, 2.5f, 1)
				.wandX(4.5f, 6.5f, 4.5f, 1)
				.wandZ(2, 7.5f, 4, -1)
				.wandX(4, 4.5f, 7.5f, 1)
				.wandX(-0.5f, 4, 9.5f, -1)
				.wandX(-2.5f, 6.5f, 12, -1)
				.wandZ(4.5f, 9.5f, 2, 1)
				.wandZ(2, 4.5f, -2.5f, 1);
	}

	public static Labyrinth geisterGang() {
		return new Labyrinth()
				.boden(-7.5f, 6.5f, 0, 14.5f)
				.boden(0, 2, -2, 0)
				.wandZ(4.5f, 12, 6.5f, -1)
				.wandX(4.5f, 6.5f, 4.5f, 1)
				.wandZ(4.5f, 7.5f, 4.5f, 1)
				.wandX(4, 4.5f, 7.5f, 1)
				.wandX(-0.5f, 4, 2.5f, 1)
				.wandX(-0.5f, 4, 10, 1)
				.wandX(2, 4, 9.5f, -1)
				.wandX(-2.5f, 6.5f, 12, -1)
				.wandZ(5, 10, -5, 1)
				.wandZ(10, 12, -2.5f, 1)
				.wandZ(4.5f, 9.5f, 2, 1)
				.wandZ(9.5f, 10, 4, 1);
	}

	public static Labyrinth raum() {
		return new Labyrinth()
				.boden(-5, 6.5f, 4.5f, 12)
				.wandZ(4.5f, 12.5f, 6.5f, -1)
				.wandX(4.5f, 6.5f, 4.5f, 1)
				.wandX(4, 4.5f, 7.5f, 1)
				.wandX(-5, -2.5f, 10, -1)
				.wandX(-5, 1.5f, 5, 1)
				.wandX(-0.5f, 4, 10, 1)
				.wandX(-0.5f, 1.5f, 9.5f, -1)
				.wandX(-2.5f, 6.5f, 12, -1)
				.wandZ(5, 10, -5, 1)
				.wandZ(10, 12, -2.5f, 1)
				.wandZ(5, 9.5f, 1.5f, -1)
				.wandZ(9.5f, 10, -0.5f, -1);
	}
	private Labyrinth boden(float xa, float xe, float za, float ze) {
		boeden.add(new float[] { xa, xe, za, ze });
		return this;
	}

	/** Mauer entlang der x-Achse bei z. Die Seite (1 oder -1) gibt an, wohin sie sichtbar ist. */
	private Labyrinth wandX(float xa, float xe, float z, float seite) {
		waendeX.add(new float[] { xa, xe, z, seite });
		return this;
	}

	/** Mauer entlang der z-Achse bei x. */
	private Labyrinth wandZ(float za, float ze, float x, float seite) {
		waendeZ.add(new float[] { za, ze, x, seite });
		return this;
	}

	@Override
	public void erstellen() {
		if (kachelBoden == -1) {
			kachelBoden = kachel(() -> Formen.boden(0, KACHEL, 0, KACHEL, 0, FEIN));
			kachelWandX = kachel(() -> Formen.wandX(0, KACHEL, 0, HOEHE, 1, FEIN));
			kachelWandZ = kachel(() -> Formen.wandZ(0, KACHEL, 0, HOEHE, 1, FEIN));
		}
		super.erstellen();
	}

	private static int kachel(Runnable form) {
		int liste = glGenLists(1);
		glNewList(liste, GL_COMPILE);
		form.run();
		glEndList();
		return liste;
	}

	@Override
	protected void zeichnen() {
		BODEN.setzen();
		for (float[] b : boeden) {
			for (float x = b[0]; x < b[1]; x += KACHEL) {
				for (float z = b[2]; z < b[3]; z += KACHEL) {
					glPushMatrix();
					glTranslatef(x, 0, z);
					glCallList(kachelBoden);
					glPopMatrix();
				}
			}
		}

		MAUER.setzen();
		for (float[] w : waendeX) {
			for (float x = w[0]; x < w[1]; x += KACHEL) {
				glPushMatrix();
				glTranslatef(x, 0, w[2]);
				if (w[3] < 0) {
					glTranslatef(KACHEL, 0, 0);
					glRotatef(180, 0, 1, 0);
				}
				glCallList(kachelWandX);
				glPopMatrix();
			}
		}
		for (float[] w : waendeZ) {
			for (float z = w[0]; z < w[1]; z += KACHEL) {
				glPushMatrix();
				glTranslatef(w[2], 0, z);
				if (w[3] < 0) {
					glTranslatef(0, 0, KACHEL);
					glRotatef(180, 0, 1, 0);
				}
				glCallList(kachelWandZ);
				glPopMatrix();
			}
		}
	}
}
