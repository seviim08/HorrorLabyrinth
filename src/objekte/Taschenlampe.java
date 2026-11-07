package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Taschenlampe extends Objekt {

	private static final Material GEHAEUSE = new Material(
			new float[] { 0, 0, 0, 1 },
			new float[] { 0.71f, 0.43f, 0.18f, 1 },
			new float[] { 0, 0, 0, 1 },
			32);

	@Override
	protected void zeichnen() {
		GEHAEUSE.setzen();
		glPushMatrix();
		glTranslatef(0, 0, 0.2f);
		kegel(0.1f, 0.04f, 0.3f);
		glPopMatrix();
		Formen.zylinder(0.04f, 0.2f, 100);
	}

	private void kegel(float radiusVorne, float radiusHinten, float laenge) {
		int segmente = 100;
		float du = (float) (2 * Math.PI) / segmente;
		float dv = laenge / segmente;

		for (int i = 0; i < segmente; i++) {
			float u0 = i * du;
			float u1 = u0 + du;
			for (int j = 0; j < segmente; j++) {
				float v0 = j * dv;
				float v1 = v0 + dv;
				glBegin(GL_TRIANGLE_FAN);
				kegelPunkt(radiusVorne, radiusHinten, laenge, u0, v0);
				kegelPunkt(radiusVorne, radiusHinten, laenge, u1, v0);
				kegelPunkt(radiusVorne, radiusHinten, laenge, u1, v1);
				kegelPunkt(radiusVorne, radiusHinten, laenge, u0, v1);
				glEnd();
			}
		}
	}

	private void kegelPunkt(float r1, float r2, float laenge, float u, float v) {
		float r = r2 + v * (r1 - r2);
		float x = (float) (r * Math.cos(u));
		float y = (float) (r * Math.sin(u));
		float z = v * laenge;
		glNormal3f(x / (r1 - r2), y / r2, z / laenge);
		glVertex3f(x, y, z);
	}
}
