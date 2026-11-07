package objekte;

import static org.lwjgl.opengl.GL11.*;

import grafik.Formen;
import grafik.Material;

public class Blut extends Objekt {

	private static final Material BLUT = new Material(
			new float[] { 0.17f, 0.01f, 0.01f, 0.5f },
			new float[] { 0.61f, 0.04f, 0.04f, 0.5f },
			new float[] { 0.73f, 0.63f, 0.63f, 0.5f });

	private static final float[][] EINGANG = {
			{ 1, 1, 0.05f }, { 1.5f, 0.3f, 0.025f }, { 1.2f, 1.5f, 0.05f }, { 1.3f, 0.7f, 0.01f },
			{ 0.9f, 1.8f, 0.05f }, { 0.5f, 0.2f, 0.05f }, { 1.1f, 2.2f, 0.02f }, { 0.9f, 0.1f, 0.04f } };

	private static final float[][] RAUM = {
			{ -2, 1, 0.05f }, { -1.5f, 0.3f, 0.025f }, { -1.8f, 1.5f, 0.04f }, { -1.7f, 0.7f, 0.001f },
			{ -2.1f, 1.8f, 0.05f }, { -2.5f, 0.2f, 0.025f }, { -1.9f, 2.2f, 0.01f }, { -2.1f, 0.1f, 0.05f } };

	private final float z;
	private final float normale;
	private final float[][] flecken;

	private Blut(float z, float normale, float[][] flecken) {
		this.z = z;
		this.normale = normale;
		this.flecken = flecken;
	}

	public static Blut eingang() {
		return new Blut(1.98f, -1, EINGANG);
	}

	public static Blut raum() {
		return new Blut(5.02f, 1, RAUM);
	}

	@Override
	protected void zeichnen() {
		BLUT.setzen();
		for (float[] fleck : flecken) {
			glPushMatrix();
			glTranslatef(fleck[0], fleck[1], z);
			Formen.kreis(fleck[2], normale);
			glPopMatrix();
		}
	}
}
