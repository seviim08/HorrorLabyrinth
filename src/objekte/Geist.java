package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Material;

public class Geist extends Objekt {

	private static final float R = 0.1f;
	private static final float H = 1.75f;
	private static final float A = 0.75f;
	private static final float B = 0.5f;

	@Override
	protected void zeichnen() {
		Material.HELL.setzen();
		glPushMatrix();
		glTranslatef(5.5f, 0.5f, 5.5f);
		koerper();
		glPopMatrix();

		auge(5.65f);
		auge(5.35f);
	}

	private void auge(float x) {
		glPushMatrix();
		glTranslatef(x, 1.25f, 5.8f);
		glRotatef(180, 1, 0, 0);
		kleinesAuge();
		glPopMatrix();
	}

	private void koerper() {
		int segmente = 200;
		float du = (float) (2 * Math.PI) / segmente;
		float dv = 1f / segmente;

		for (int i = 0; i < segmente; i++) {
			float u0 = i * du;
			float u1 = u0 + du;
			for (int j = 0; j < segmente; j++) {
				float v0 = j * dv;
				float v1 = v0 + dv;
				float nx = x(u0, v0) / (R - A);
				float ny = v0;
				glBegin(GL_TRIANGLE_FAN);
				glNormal3f(nx, ny, z(u0, v0) / B);
				glVertex3f(x(u0, v0), v0 * H, z(u0, v0));
				glNormal3f(nx, ny, z(u1, v0) / B);
				glVertex3f(x(u1, v0), v0 * H, z(u1, v0));
				glNormal3f(nx, ny, z(u1, v1) / B);
				glVertex3f(x(u1, v1), v1 * H, z(u1, v1));
				glNormal3f(nx, ny, z(u0, v1) / B);
				glVertex3f(x(u0, v1), v1 * H, z(u0, v1));
				glEnd();
			}
		}
	}

	private float x(float u, float v) {
		return (float) ((A + v * (R - A)) * Math.cos(u));
	}

	private float z(float u, float v) {
		return (float) ((B + v * (R - B)) * Math.sin(u));
	}
}
