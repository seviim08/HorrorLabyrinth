package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Tuer extends Objekt {

	private static final float FEIN = 50;

	private static final Material MESSING = new Material(
			new float[] { 0.33f, 0.22f, 0.03f, 1 },
			new float[] { 0.78f, 0.57f, 0.11f, 1 },
			new float[] { 0.99f, 0.94f, 0.81f, 1 });

	@Override
	protected void zeichnen() {
		MESSING.setzen();
		Formen.wandZ(12.5f, 14.5f, 6.5f, 3, -1, FEIN);

		Material.SILBER.setzen();
		glPushMatrix();
		glTranslatef(6.5f, 1.2f, 13);
		Formen.kugel(0.1f, 50);
		glPopMatrix();

		Material.SCHWARZ.setzen();
		glPushMatrix();
		glTranslatef(6.45f, 1, 13);
		glRotatef(90, 0, 1, 0);
		Formen.kreis(0.06f, -1);
		glPopMatrix();

		glPushMatrix();
		glTranslatef(0, 0.83f, 0);
		Formen.wandZ(12.97f, 13.03f, 6.45f, 0.16f, -1, FEIN);
		glPopMatrix();
	}
}
