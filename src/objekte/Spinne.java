package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Spinne extends Objekt {

	private static final float BREITE_MUND = 0.5f;

	@Override
	protected void zeichnen() {
		Material.SILBER.setzen();
		glPushMatrix();
		glTranslatef(3, 0.6f, 6);
		Formen.ellipsoid(0.6f, 0.5f, 0.45f, 100);
		glPopMatrix();

		auge(3.15f);
		auge(2.85f);

		Material.PUPILLE.setzen();
		glPushMatrix();
		glTranslatef(3, 0.3f, 5.56f);
		mund(0.1f, 0.2f);
		glPopMatrix();

		Material.SCHWARZ.setzen();
		for (int i = 0; i < 3; i++) {
			float z = 5.75f + i * 0.25f;
			float winkel = 60 + i * 30;
			bein(3.75f, z, -winkel);
			bein(2.25f, z, winkel);
		}
	}

	private void auge(float x) {
		glPushMatrix();
		glTranslatef(x, 0.6f, 5.55f);
		kleinesAuge();
		glPopMatrix();
	}

	private void mund(float hoehe, float tiefe) {
		int segmente = 50;
		float ul = -BREITE_MUND / 4;
		float du = (BREITE_MUND / 2) / segmente;
		float dv = tiefe / segmente;

		glNormal3f(0, 0, -1);
		for (int i = 0; i < segmente; i++) {
			float u0 = ul + i * du;
			float u1 = u0 + du;
			float y0 = hoehe * (1 - 4 * u0 * u0 / (BREITE_MUND * BREITE_MUND));
			float y1 = hoehe * (1 - 4 * u1 * u1 / (BREITE_MUND * BREITE_MUND));
			for (int j = 0; j < segmente; j++) {
				float v0 = j * dv;
				float v1 = v0 + dv;
				glBegin(GL_TRIANGLE_FAN);
				glVertex3f(u0, y0, v0);
				glVertex3f(u1, y1, v0);
				glVertex3f(u1, y1, v1);
				glVertex3f(u0, y0, v1);
				glEnd();
			}
		}
	}

	private void bein(float x, float z, float winkel) {
		glPushMatrix();
		glTranslatef(x, 0.45f, z);
		glRotatef(winkel, 0, 1, 0);
		Formen.zylinder(0.03f, 0.5f, 50);
		glRotatef(-240, 1, 0, 0);
		Formen.zylinder(0.03f, 0.5f, 50);
		glPopMatrix();
	}
}
