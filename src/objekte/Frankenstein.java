package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Frankenstein extends Objekt {

	private static final float FEIN = 60;

	private static final Material HAUT = new Material(
			new float[] { 0.02f, 0.30f, 0.02f, 0.5f },
			new float[] { 0.08f, 0.61f, 0.08f, 0.5f },
			new float[] { 0.63f, 0.73f, 0.63f, 0.5f });

	private static final Material MUND = new Material(
			new float[] { 0, 0, 0, 0 },
			new float[] { 0, 0, 0, 0.5f },
			new float[] { 0.73f, 0.63f, 0.63f, 0.5f });

	@Override
	protected void zeichnen() {
		HAUT.setzen();
		quader(0.85f);

		Material.SCHWARZ.setzen();
		glPushMatrix();
		glTranslatef(0, 0.8f, 0);
		quader(0.35f);
		glPopMatrix();
		Formen.boden(1, 1.5f, 13, 14, 1.15f, FEIN);

		glPushMatrix();
		glTranslatef(0, 0.67f, 0);
		Formen.wandZ(13.25f, 13.45f, 0.95f, 0.05f, -1, FEIN);
		Formen.wandZ(13.55f, 13.75f, 0.95f, 0.05f, -1, FEIN);
		glPopMatrix();

		auge(13.65f);
		auge(13.35f);

		MUND.setzen();
		glPushMatrix();
		glTranslatef(0, 0.26f, 0);
		Formen.wandZ(13.35f, 13.65f, 0.95f, 0.06f, -1, FEIN);
		glPopMatrix();
	}

	private void quader(float hoehe) {
		Formen.wandZ(13, 14, 1, hoehe, -1, FEIN);
		Formen.wandZ(13, 14, 1.5f, hoehe, 1, FEIN);
		Formen.wandX(1, 1.5f, 13, hoehe, -1, FEIN);
		Formen.wandX(1, 1.5f, 14, hoehe, 1, FEIN);
	}

	private void auge(float z) {
		glPushMatrix();
		glTranslatef(1, 0.6f, z);
		glRotatef(90, 0, 1, 0);
		kleinesAuge();
		glPopMatrix();
	}
}
