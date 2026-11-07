package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Auge extends Objekt {

	private static final Material IRIS = new Material(
			new float[] { 0.14f, 0.22f, 0.16f, 0.9f },
			new float[] { 0.54f, 0.89f, 0.63f, 0.9f },
			new float[] { 0.32f, 0.32f, 0.32f, 0.9f });

	@Override
	protected void zeichnen() {
		kugel(0.5f, 0.5f, Material.AUGAPFEL);
		kugel(0.575f, 0.45f, IRIS);
		kugel(0.7f, 0.35f, Material.PUPILLE);
	}

	private void kugel(float x, float radius, Material material) {
		material.setzen();
		glPushMatrix();
		glTranslatef(x, 1, 11);
		Formen.kugel(radius, 100);
		glPopMatrix();
	}
}
