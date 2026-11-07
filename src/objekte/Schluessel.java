package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Schluessel extends Objekt {

	private static final Material BRONZE = new Material(
			new float[] { 0.21f, 0.13f, 0.05f, 1 },
			new float[] { 0.71f, 0.43f, 0.18f, 1 },
			new float[] { 0.39f, 0.27f, 0.17f, 1 },
			25.6f);

	private static final Material BRONZE_DUNKEL = new Material(
			new float[] { 0.21f, 0.13f, 0.05f, 1 },
			new float[] { 0.17f, 0.10f, 0.04f, 1 },
			new float[] { 0, 0, 0, 1 },
			25.6f);

	private static final float RADIUS_HALM = 0.06f;
	private static final float LAENGE_HALM = 0.6f;
	private static final float RADIUS_GRIFF = 0.15f;
	private static final float LAENGE_BART = 0.18f;

	@Override
	protected void zeichnen() {
		BRONZE.setzen();

		Formen.zylinder(RADIUS_HALM, LAENGE_HALM, 100);

		glPushMatrix();
		glTranslatef(0, 0, LAENGE_HALM * 0.99f);
		Formen.kugel(RADIUS_HALM, 50);
		glPopMatrix();

		glPushMatrix();
		glTranslatef(-RADIUS_HALM, 0, -RADIUS_GRIFF);
		glRotatef(90, 0, 1, 0);
		Formen.zylinder(RADIUS_GRIFF, 2 * RADIUS_HALM, 100);
		glPopMatrix();

		BRONZE_DUNKEL.setzen();
		bart(0.9f);
		bart(0.7f);
	}

	private void bart(float position) {
		glPushMatrix();
		glTranslatef(0, -LAENGE_BART, LAENGE_HALM * position);
		glRotatef(270, 1, 0, 0);
		Formen.zylinder(RADIUS_HALM * 0.6f, LAENGE_BART, 100);
		glPopMatrix();
	}
}
