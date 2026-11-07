package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Mond extends Objekt {

	@Override
	protected void zeichnen() {
		Material.HELL.setzen();
		glPushMatrix();
		glTranslatef(-10, 9, 17);
		Formen.kugel(1, 50);
		glPopMatrix();
	}
}
