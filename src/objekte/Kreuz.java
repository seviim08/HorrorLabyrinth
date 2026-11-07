package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Kreuz extends Objekt {

	private static final float FEIN = 100;

	@Override
	protected void zeichnen() {
		Material.HELL.setzen();

		Formen.wandX(5.3f, 5.8f, 2.5f, 2, -1, FEIN);
		Formen.boden(5.3f, 5.8f, 2.5f, 3, 2, FEIN);
		Formen.wandZ(2.5f, 3, 5.3f, 2, -1, FEIN);
		Formen.wandZ(2.5f, 3, 5.8f, 2, 1, FEIN);

		glPushMatrix();
		glTranslatef(0, 1, 0);
		Formen.wandX(4.75f, 6.25f, 2.5f, 0.5f, -1, FEIN);
		Formen.boden(4.75f, 6.25f, 2.5f, 3, 0.5f, FEIN);
		Formen.boden(4.75f, 6.25f, 2.5f, 3, 0, FEIN);
		Formen.wandZ(2.5f, 3, 4.75f, 0.5f, -1, FEIN);
		Formen.wandZ(2.5f, 3, 6.25f, 0.5f, 1, FEIN);
		glPopMatrix();
	}
}
