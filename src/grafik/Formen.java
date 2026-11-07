package grafik;

import static org.lwjgl.opengl.GL11.*;

/**
 * Grundformen, aus denen alle Objekte zusammengesetzt werden.
 * Flächen werden fein unterteilt, damit das Spotlicht der Taschenlampe sauber aussieht.
 */
public final class Formen {

	private Formen() {
	}

	/** Senkrechte Fläche parallel zur x-Achse bei z. */
	public static void wandX(float xa, float xe, float z, float hoehe, float normale, float feinheit) {
		float m = feinheit * (xe - xa);
		float n = feinheit * hoehe;
		float dx = (xe - xa) / m;
		float dy = hoehe / n;

		glNormal3f(0, 0, normale);
		glBegin(GL_QUADS);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				float x = xa + i * dx;
				float y = j * dy;
				glVertex3f(x, y, z);
				glVertex3f(x + dx, y, z);
				glVertex3f(x + dx, y + dy, z);
				glVertex3f(x, y + dy, z);
			}
		}
		glEnd();
	}

	/** Senkrechte Fläche parallel zur z-Achse bei x. */
	public static void wandZ(float za, float ze, float x, float hoehe, float normale, float feinheit) {
		float m = feinheit * (ze - za);
		float n = feinheit * hoehe;
		float dz = (ze - za) / m;
		float dy = hoehe / n;

		glNormal3f(normale, 0, 0);
		glBegin(GL_QUADS);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				float z = za + i * dz;
				float y = j * dy;
				glVertex3f(x, y, z);
				glVertex3f(x, y, z + dz);
				glVertex3f(x, y + dy, z + dz);
				glVertex3f(x, y + dy, z);
			}
		}
		glEnd();
	}

	/** Waagrechte Fläche bei y, Normale zeigt nach oben. */
	public static void boden(float xa, float xe, float za, float ze, float y, float feinheit) {
		float m = feinheit * (xe - xa);
		float n = feinheit * (ze - za);
		float dx = (xe - xa) / m;
		float dz = (ze - za) / n;

		glNormal3f(0, 1, 0);
		glBegin(GL_QUADS);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				float x = xa + i * dx;
				float z = za + j * dz;
				glVertex3f(x, y, z);
				glVertex3f(x + dx, y, z);
				glVertex3f(x + dx, y, z + dz);
				glVertex3f(x, y, z + dz);
			}
		}
		glEnd();
	}

	public static void kugel(float radius, int segmente) {
		ellipsoid(radius, radius, radius, segmente);
	}

	public static void ellipsoid(float a, float b, float c, int segmente) {
		float du = (float) Math.PI / segmente;
		float dv = (float) (2 * Math.PI) / segmente;

		for (int i = 0; i < segmente; i++) {
			float u0 = i * du;
			float u1 = u0 + du;
			for (int j = 0; j < segmente; j++) {
				float v0 = j * dv;
				float v1 = v0 + dv;
				glBegin(GL_TRIANGLE_FAN);
				ellipsoidPunkt(a, b, c, u0, v0);
				ellipsoidPunkt(a, b, c, u1, v0);
				ellipsoidPunkt(a, b, c, u1, v1);
				ellipsoidPunkt(a, b, c, u0, v1);
				glEnd();
			}
		}
	}

	private static void ellipsoidPunkt(float a, float b, float c, float u, float v) {
		float nx = (float) (Math.sin(u) * Math.cos(v));
		float ny = (float) (Math.sin(u) * Math.sin(v));
		float nz = (float) Math.cos(u);
		glNormal3f(nx, ny, nz);
		glVertex3f(a * nx, b * ny, c * nz);
	}

	/** Zylindermantel entlang der z-Achse von 0 bis laenge. */
	public static void zylinder(float radius, float laenge, int segmente) {
		float du = (float) (2 * Math.PI) / segmente;
		float dv = laenge / segmente;

		for (int i = 0; i < segmente; i++) {
			float u0 = i * du;
			float u1 = u0 + du;
			float cos0 = (float) Math.cos(u0);
			float sin0 = (float) Math.sin(u0);
			float cos1 = (float) Math.cos(u1);
			float sin1 = (float) Math.sin(u1);
			for (int j = 0; j < segmente; j++) {
				float v0 = j * dv;
				float v1 = v0 + dv;
				glBegin(GL_TRIANGLE_FAN);
				glNormal3f(cos0, sin0, 0);
				glVertex3f(radius * cos0, radius * sin0, v0);
				glVertex3f(radius * cos1, radius * sin1, v0);
				glVertex3f(radius * cos1, radius * sin1, v1);
				glVertex3f(radius * cos0, radius * sin0, v1);
				glEnd();
			}
		}
	}

	/** Kreisscheibe in der xy-Ebene. */
	public static void kreis(float radius, float normale) {
		int segmente = 20;
		float du = (float) (2 * Math.PI) / segmente;

		glNormal3f(0, 0, normale);
		glBegin(GL_POLYGON);
		for (int i = 0; i < segmente; i++) {
			float u = i * du;
			glVertex3f((float) (radius * Math.cos(u)), (float) (radius * Math.sin(u)), 0);
		}
		glEnd();
	}
}
