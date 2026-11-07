package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Totenkopf extends Objekt {

	private static final Material KIEFER = new Material(
			new float[] { 0.19f, 0.19f, 0.19f, 1 },
			new float[] { 0.31f, 0.31f, 0.31f, 1 },
			new float[] { 0, 0, 0, 1 });

	@Override
	protected void zeichnen() {
		Material.SILBER.setzen();
		glPushMatrix();
		glTranslatef(-6.5f, 1, 13.5f);
		Formen.kugel(0.5f, 100);
		KIEFER.setzen();
		glTranslatef(0, -0.4f, 0);
		Formen.kugel(0.3f, 100);
		glPopMatrix();

		auge(-6.35f);
		auge(-6.65f);
	}

	private void auge(float x) {
		glPushMatrix();
		glTranslatef(x, 0.9f, 13.05f);
		auge(0.1f, 0.075f, 0.055f, 0.05f, 0.1f, Material.PUPILLE, Material.IRIS);
		glPopMatrix();
	}
}
