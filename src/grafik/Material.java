package grafik;

import static org.lwjgl.opengl.GL11.*;

/**
 * Materialeigenschaften für die Beleuchtung. Häufig genutzte Materialien sind als Konstanten hinterlegt.
 */
public final class Material {

	public static final Material AUGAPFEL = new Material(
			new float[] { 1, 1, 1, 1 },
			new float[] { 0.5f, 0.212f, 0.071f, 1 },
			new float[] { 0.58f, 0, 0, 1 });

	public static final Material IRIS = new Material(
			new float[] { 0.10f, 0.19f, 0.17f, 0.8f },
			new float[] { 0.40f, 0.74f, 0.69f, 0.8f },
			new float[] { 0.30f, 0.31f, 0.31f, 0.8f });

	public static final Material PUPILLE = new Material(
			new float[] { 0, 0, 0, 0 },
			new float[] { 0.5f, 0.212f, 0.071f, 1 },
			new float[] { 0.58f, 0, 0, 1 });

	public static final Material HELL = new Material(
			new float[] { 1, 1, 1, 1 },
			new float[] { 0.71f, 0.43f, 0.18f, 1 },
			new float[] { 0.39f, 0.27f, 0.17f, 1 });

	public static final Material SILBER = new Material(
			new float[] { 0.19f, 0.19f, 0.19f, 1 },
			new float[] { 0.51f, 0.51f, 0.51f, 1 },
			new float[] { 0.51f, 0.51f, 0.51f, 1 });

	public static final Material SCHWARZ = new Material(
			new float[] { 0, 0, 0, 1 },
			new float[] { 0, 0, 0, 1 },
			new float[] { 0.51f, 0.51f, 0.51f, 1 });

	private final float[] ambient;
	private final float[] diffus;
	private final float[] spekular;
	private final float glanz;

	public Material(float[] ambient, float[] diffus, float[] spekular) {
		this(ambient, diffus, spekular, 51);
	}

	public Material(float[] ambient, float[] diffus, float[] spekular, float glanz) {
		this.ambient = ambient;
		this.diffus = diffus;
		this.spekular = spekular;
		this.glanz = glanz;
	}

	public void setzen() {
		glMaterialfv(GL_FRONT, GL_AMBIENT, ambient);
		glMaterialfv(GL_FRONT, GL_DIFFUSE, diffus);
		glMaterialfv(GL_FRONT, GL_SPECULAR, spekular);
		glMaterialf(GL_FRONT, GL_SHININESS, glanz);
	}
}
