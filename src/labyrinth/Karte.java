package labyrinth;

/**
 * Raster über dem Labyrinth für Kollision und Sichtbarkeit. Jede Zelle ist
 * 5 x 5 cm groß und gehört zu einem Bereich (Mauer, Gang, Hindernis).
 */
public class Karte {

	public static final int MAUER = 1;
	public static final int GEIST = 2;
	public static final int SCHLUESSEL = 3;
	public static final int SPINNE = 4;
	public static final int FRANKENSTEIN = 5;
	public static final int EINGANG = 6;
	public static final int LINKER_GANG = 7;
	public static final int TUER_GANG = 8;
	public static final int TUER = 9;
	public static final int VOR_DER_SPINNE = 10;
	public static final int SPINNEN_GANG = 11;
	public static final int GEISTER_GANG = 12;
	public static final int RAUM = 13;

	private final int[][] zellen = new int[300][350];

	public Karte() {
		fuellen(0, 300, 0, 10, MAUER);
		fuellen(0, 160, 10, 50, MAUER);
		fuellen(200, 300, 10, 50, MAUER);
		fuellen(0, 10, 50, 350, MAUER);
		fuellen(290, 300, 50, 350, MAUER);
		fuellen(10, 290, 340, 350, MAUER);
		fuellen(10, 60, 300, 340, MAUER);
		fuellen(150, 250, 90, 100, MAUER);
		fuellen(240, 250, 100, 200, MAUER);
		fuellen(250, 290, 100, 140, MAUER);
		fuellen(50, 110, 90, 150, MAUER);
		fuellen(110, 200, 140, 150, MAUER);
		fuellen(190, 200, 150, 240, MAUER);
		fuellen(150, 240, 240, 250, MAUER);
		fuellen(50, 60, 150, 250, MAUER);
		fuellen(50, 110, 250, 260, MAUER);
		fuellen(100, 110, 260, 300, MAUER);
		fuellen(110, 290, 290, 300, MAUER);
		fuellen(250, 290, 90, 100, MAUER);

		fuellen(160, 200, 10, 50, EINGANG);
		fuellen(50, 290, 50, 90, EINGANG);
		fuellen(10, 50, 50, 300, LINKER_GANG);
		fuellen(50, 100, 260, 300, LINKER_GANG);
		fuellen(60, 170, 300, 340, TUER_GANG);
		fuellen(170, 195, 300, 340, FRANKENSTEIN);
		fuellen(195, 220, 300, 340, TUER_GANG);
		fuellen(220, 290, 300, 340, TUER);
		fuellen(110, 150, 90, 140, VOR_DER_SPINNE);
		fuellen(150, 240, 100, 140, VOR_DER_SPINNE);
		fuellen(200, 240, 140, 158, SPINNEN_GANG);
		fuellen(200, 240, 158, 183, SPINNE);
		fuellen(200, 240, 183, 240, SPINNEN_GANG);
		fuellen(240, 250, 200, 290, GEISTER_GANG);
		fuellen(250, 290, 140, 290, GEIST);
		fuellen(110, 240, 250, 290, RAUM);
		fuellen(60, 150, 240, 250, RAUM);

		// Hindernisse im Raum (Knochen, Steine) und die Lücken dazwischen
		fuellen(160, 190, 150, 175, MAUER);
		fuellen(160, 190, 175, 210, RAUM);
		fuellen(160, 190, 210, 230, MAUER);
		fuellen(160, 190, 230, 240, RAUM);
		fuellen(60, 90, 220, 240, MAUER);
		fuellen(60, 90, 150, 220, RAUM);
		fuellen(90, 160, 150, 230, SCHLUESSEL);
		fuellen(90, 160, 230, 240, RAUM);
	}

	private void fuellen(int x0, int x1, int z0, int z1, int bereich) {
		for (int i = x0; i < x1; i++) {
			for (int j = z0; j < z1; j++) {
				zellen[i][j] = bereich;
			}
		}
	}

	/** Bereich an der Spielerposition (x, z relativ zum Start). */
	public int bereich(float x, float z) {
		return zellen[(int) (180 + 20 * x)][(int) (30 + 20 * z)];
	}
}
