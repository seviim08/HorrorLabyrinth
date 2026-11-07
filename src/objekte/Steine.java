package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Steine extends Objekt {

	private static final Material DUNKEL = grau(0.25f);
	private static final Material SEHR_DUNKEL = grau(0.10f);

	@Override
	protected void zeichnen() {
		glPushMatrix();
		glTranslatef(1, 0, 8.5f);
		glRotatef(-45, 0, 1, 0);
		haufen();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(-4, 0.1f, 9);
		glRotatef(60, 0, 1, 0);
		haufen();
		glPopMatrix();
	}

	private void haufen() {
		stein(-0.2f, 0, 0.3f, Material.SILBER);
		stein(0, 0.4f, 0.25f, DUNKEL);
		stein(0.2f, 0, 0.25f, SEHR_DUNKEL);
	}

	private void stein(float x, float y, float radius, Material material) {
		material.setzen();
		glPushMatrix();
		glTranslatef(x, y, 0);
		Formen.kugel(radius, 50);
		glPopMatrix();
	}

	private static Material grau(float ambient) {
		return new Material(
				new float[] { ambient, ambient, ambient, 1 },
				new float[] { 0.51f, 0.51f, 0.51f, 1 },
				new float[] { 0.51f, 0.51f, 0.51f, 1 });
	}
}
