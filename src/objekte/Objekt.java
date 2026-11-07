package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

/**
 * Basis für alle statischen Objekte. Die Geometrie wird einmal in eine
 * Display-Liste kompiliert und danach nur noch abgespielt.
 */
public abstract class Objekt {

	private int displayList = -1;

	protected abstract void zeichnen();

	public void erstellen() {
		displayList = glGenLists(1);
		glNewList(displayList, GL_COMPILE);
		zeichnen();
		glEndList();
	}

	public void anzeigen() {
		glCallList(displayList);
	}

	/** Auge aus Augapfel, Iris und Pupille, die nach vorne (-z) versetzt sind. */
	protected static void auge(float radius, float radiusIris, float radiusPupille,
			float abstandIris, float abstandPupille, Material augapfel, Material iris) {
		augapfel.setzen();
		Formen.kugel(radius, 50);

		iris.setzen();
		glPushMatrix();
		glTranslatef(0, 0, -abstandIris);
		Formen.kugel(radiusIris, 50);
		glPopMatrix();

		Material.PUPILLE.setzen();
		glPushMatrix();
		glTranslatef(0, 0, -abstandPupille);
		Formen.kugel(radiusPupille, 50);
		glPopMatrix();
	}

	protected static void kleinesAuge() {
		auge(0.075f, 0.055f, 0.025f, 0.03f, 0.07f, Material.AUGAPFEL, Material.IRIS);
	}
}
