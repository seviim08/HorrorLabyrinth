package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Knochen extends Objekt {

	private static final float RADIUS = 0.11f;
	private static final float LAENGE = 1.5f;

	private final float x, y, z, drehung;

	private Knochen(float x, float y, float z, float drehung) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.drehung = drehung;
	}

	public static Knochen beimTotenkopf() {
		return new Knochen(-7, 1.4f, 13.5f, 0);
	}

	public static Knochen imRaum() {
		return new Knochen(0.5f, 1.26f, 5.25f, -45);
	}

	@Override
	protected void zeichnen() {
		Material.HELL.setzen();

		glPushMatrix();
		glTranslatef(x, y, z);
		glRotatef(drehung, 0, 1, 0);

		glPushMatrix();
		glRotatef(90, 0, 1, 0);
		glRotatef(45, 1, 0, 0);
		knochen();
		glPopMatrix();

		glPushMatrix();
		glTranslatef(0, -1.05f, 0);
		glRotatef(90, 0, 1, 0);
		glRotatef(-45, 1, 0, 0);
		knochen();
		glPopMatrix();

		glPopMatrix();
	}

	private void knochen() {
		Formen.zylinder(RADIUS, LAENGE, 100);
		ende(-0.1f, -0.02f);
		ende(0.1f, -0.02f);
		ende(-0.1f, LAENGE + 0.02f);
		ende(0.1f, LAENGE + 0.02f);
	}

	private void ende(float y, float z) {
		glPushMatrix();
		glTranslatef(0, y, z);
		Formen.kugel(RADIUS * 1.4f, 50);
		glPopMatrix();
	}
}
