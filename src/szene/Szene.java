package szene;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import grafik.Licht;
import labyrinth.Karte;
import labyrinth.Labyrinth;
import objekte.*;

/**
 * Die Spielszene. Enthält Kamera, Steuerung, Beleuchtung und das Verhalten
 * von Geist, Auge und Schlüssel.
 * <p>
 * Die Spielerposition (x, z) ist relativ zum Startpunkt (1, -1) angegeben.
 */
public class Szene {

	private static final float SCHRITT = 0.5f;
	private static final float DREHUNG = 10;
	private static final float SPRUNG_TEMPO = 0.1f;
	private static final float SPRUNG_HOEHE = 1.5f;
	private static final float GEIST_TEMPO = 0.03f;
	private static final float AUGE_TEMPO = 0.007f;

	private static final float[] GRAU = { 0.714f, 0.714f, 0.714f, 1 };
	private static final float[] WEISS = { 1, 1, 1, 1 };

	private final Karte karte = new Karte();

	private final Labyrinth komplett = Labyrinth.komplett();
	private final Labyrinth eingang = Labyrinth.eingang();
	private final Labyrinth linkerGang = Labyrinth.linkerGang();
	private final Labyrinth tuerGang = Labyrinth.tuerGang();
	private final Labyrinth vorDerSpinne = Labyrinth.vorDerSpinne();
	private final Labyrinth spinnenGang = Labyrinth.spinnenGang();
	private final Labyrinth geisterGang = Labyrinth.geisterGang();
	private final Labyrinth raum = Labyrinth.raum();

	private final Auge auge = new Auge();
	private final Blut blutEingang = Blut.eingang();
	private final Blut blutRaum = Blut.raum();
	private final Frankenstein frankenstein = new Frankenstein();
	private final Geist geist = new Geist();
	private final Knochen knochenTotenkopf = Knochen.beimTotenkopf();
	private final Knochen knochenRaum = Knochen.imRaum();
	private final Kreuz kreuz = new Kreuz();
	private final Mond mond = new Mond();
	private final Schluessel schluessel = new Schluessel();
	private final Spinne spinne = new Spinne();
	private final Steine steine = new Steine();
	private final Taschenlampe taschenlampe = new Taschenlampe();
	private final Totenkopf totenkopf = new Totenkopf();
	private final Tuer tuer = new Tuer();

	private final Licht taschenlampenLicht = new Licht(GL_LIGHT1);
	private final Licht schluesselLicht = new Licht(GL_LIGHT2);
	private final Licht mondLicht = new Licht(GL_LIGHT3);

	private final Matrix4f matrix = new Matrix4f();
	private final FloatBuffer puffer = BufferUtils.createFloatBuffer(16);

	private float seitenverhaeltnis = 1;
	private boolean gestartet = false;
	private boolean beendet = false;

	private float x, y, z, rotation;
	private boolean springt, faellt;

	private boolean aufgehoben, aufgeschlossen;
	private float schluesselX, schluesselDrehung;

	private float geistZ;
	private float augeZ;
	private boolean augeNachRechts = true;

	/** Muss aufgerufen werden, sobald der OpenGL-Kontext existiert. */
	public void init() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glShadeModel(GL_FLAT);
		glClearColor(0, 0, 0.1f, 0);
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { 0.6f, 0.6f, 0.6f, 1 });
		mondLicht.setAn(true);

		List<Objekt> objekte = List.of(komplett, eingang, linkerGang, tuerGang, vorDerSpinne, spinnenGang,
				geisterGang, raum, auge, blutEingang, blutRaum, frankenstein, geist, knochenTotenkopf,
				knochenRaum, kreuz, mond, schluessel, spinne, steine, taschenlampe, totenkopf, tuer);
		for (Objekt objekt : objekte) {
			objekt.erstellen();
		}

		setSeitenverhaeltnis(seitenverhaeltnis);
	}

	public void setSeitenverhaeltnis(float verhaeltnis) {
		seitenverhaeltnis = verhaeltnis;
		matrix.setPerspective(1, verhaeltnis, 0.1f, 20).get(puffer);
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(puffer);
		glMatrixMode(GL_MODELVIEW);
	}

	public boolean istBeendet() {
		return beendet;
	}

	public void zeichnen() {
		float blickX = (float) Math.cos(Math.toRadians(90 + rotation));
		float blickZ = (float) Math.sin(Math.toRadians(90 + rotation));
		matrix.setLookAt(1 + x, 1 + y, -1 + z, 1 + x + blickX, 1 + y, -1 + z + blickZ, 0, 1, 0).get(puffer);
		glLoadMatrixf(puffer);

		lichter(blickX, blickZ);

		mond.anzeigen();
		schluessel();

		// Pro Gang wird nur gezeichnet, was von dort aus zu sehen ist
		switch (karte.bereich(x, z)) {
			case Karte.EINGANG -> {
				eingang.anzeigen();
				blutEingang.anzeigen();
				kreuz.anzeigen();
				totenkopf.anzeigen();
				knochenTotenkopf.anzeigen();
			}
			case Karte.LINKER_GANG -> {
				linkerGang.anzeigen();
				frankenstein.anzeigen();
				tuer.anzeigen();
				totenkopf.anzeigen();
				knochenTotenkopf.anzeigen();
			}
			case Karte.TUER_GANG, Karte.FRANKENSTEIN, Karte.TUER -> {
				tuerGang.anzeigen();
				frankenstein.anzeigen();
				tuer.anzeigen();
			}
			case Karte.VOR_DER_SPINNE -> {
				vorDerSpinne.anzeigen();
				spinne.anzeigen();
			}
			case Karte.SPINNEN_GANG, Karte.SPINNE -> {
				spinnenGang.anzeigen();
				spinne.anzeigen();
			}
			case Karte.GEISTER_GANG, Karte.GEIST -> {
				geisterGang.anzeigen();
				spinne.anzeigen();
				auge();
				geist();
			}
			case Karte.RAUM, Karte.SCHLUESSEL -> {
				raum.anzeigen();
				blutRaum.anzeigen();
				steine.anzeigen();
				knochenRaum.anzeigen();
				auge();
			}
			case Karte.MAUER -> komplett.anzeigen();
			default -> {
			}
		}

		taschenlampe();
		springen();
	}

	private void lichter(float blickX, float blickZ) {
		taschenlampenLicht.spot(new float[] { 1 + x, 1 + y, -1 + z, 1 }, new float[] { blickX, 0, blickZ, 1 },
				GRAU, 20, 70);
		schluesselLicht.spot(new float[] { -2, 3, 7, 1 }, new float[] { 0, -1, 0, 1 }, GRAU, 14, 128);
		mondLicht.spot(new float[] { -10, 9, 17, 1 }, new float[] { 16.5f, -10, -3.5f, 1 }, WEISS, 9, 80);
		schluesselLicht.setAn(!aufgehoben);
	}

	private void taschenlampe() {
		float abstand = seitenverhaeltnis == 1 ? 0.3f : 0.4f;
		float winkel = seitenverhaeltnis == 1 ? 120 : 130;
		glPushMatrix();
		glTranslatef(1 + x + abstand * (float) Math.cos(Math.toRadians(winkel + rotation)), 0.8f + y,
				-1 + z + abstand * (float) Math.sin(Math.toRadians(winkel + rotation)));
		glRotatef(-rotation, 0, 1, 0);
		taschenlampe.anzeigen();
		glPopMatrix();
	}

	private void springen() {
		if (springt) {
			if (y < SPRUNG_HOEHE) {
				y += SPRUNG_TEMPO;
			} else {
				springt = false;
				faellt = true;
			}
		}

		if (faellt) {
			// Auf Spinne und Frankenstein bleibt man oben stehen
			float boden = switch (karte.bereich(x, z)) {
				case Karte.SPINNE -> 0.8f;
				case Karte.FRANKENSTEIN -> 1.2f;
				default -> 0;
			};
			if (y > boden) {
				y = Math.max(boden, y - SPRUNG_TEMPO);
			} else if (boden == 0) {
				faellt = false;
			}
		}
	}

	private void auge() {
		if (augeNachRechts) {
			if (augeZ < 0.5f) {
				augeZ += AUGE_TEMPO;
			} else {
				augeNachRechts = false;
			}
		} else {
			if (augeZ > -0.5f) {
				augeZ -= AUGE_TEMPO;
			} else {
				augeNachRechts = true;
			}
		}

		glPushMatrix();
		glTranslatef(0, 0, augeZ);
		auge.anzeigen();
		glPopMatrix();

		boolean imAugenGang = karte.bereich(x, z) == Karte.RAUM && x < 0.2f && x > -1.2f;
		if (imAugenGang) {
			if (augeNachRechts ? 11.5f + augeZ < z + 0.03f : 12.5f + augeZ > z - 0.03f) {
				zurueckZumStart();
			}
		}
	}

	/** Der Geist verfolgt den Spieler in seinem Gang und schickt ihn zurück zum Start. */
	private void geist() {
		if (karte.bereich(x, z) == Karte.GEIST) {
			float ziel = (-1 + z) - 6.5f;
			if (geistZ < ziel) {
				geistZ += GEIST_TEMPO;
				if (geistZ < ziel && geistZ + 0.5f >= ziel) {
					zurueckZumStart();
				}
			}
		} else {
			geistZ = 0;
		}

		glPushMatrix();
		glTranslatef(0, 0, geistZ);
		geist.anzeigen();
		glPopMatrix();
	}

	private void schluessel() {
		glPushMatrix();
		if (!aufgehoben) {
			glTranslatef(-2, 1.2f, 7);
			glRotatef(50, 0, 1, 0);
		} else if (!aufgeschlossen) {
			glTranslatef(1 + x + 0.4f * (float) Math.cos(Math.toRadians(60 + rotation)), 0.8f + y,
					-1 + z + 0.4f * (float) Math.sin(Math.toRadians(60 + rotation)));
			glRotatef(-rotation, 0, 1, 0);
			glScalef(0.3f, 0.3f, 0.3f);
		} else {
			// Schlüssel fährt ins Schloss und dreht sich dann um 90 Grad
			glTranslatef(1 + schluesselX, 1, 13);
			if (schluesselX < 5.35f) {
				schluesselX += 0.05f;
			} else {
				glRotatef(schluesselDrehung, 1, 0, 0);
				if (schluesselDrehung < 90) {
					schluesselDrehung++;
				}
				beendet = schluesselDrehung >= 90;
			}
			glRotatef(90, 0, 1, 0);
			glScalef(0.3f, 0.3f, 0.3f);
		}
		schluessel.anzeigen();
		glPopMatrix();
	}

	public void taste(int taste, int aktion) {
		if (aktion == GLFW_RELEASE) {
			return;
		}
		if (taste == GLFW_KEY_ENTER && !gestartet) {
			gestartet = true;
			taschenlampenLicht.setAn(true);
		}
		if (!gestartet) {
			return;
		}

		switch (taste) {
			case GLFW_KEY_SPACE -> {
				if (!springt && !faellt) {
					springt = true;
				}
			}
			case GLFW_KEY_E -> taschenlampenLicht.setAn(true);
			case GLFW_KEY_A -> taschenlampenLicht.setAn(false);
			case GLFW_KEY_LEFT -> rotation -= DREHUNG;
			case GLFW_KEY_RIGHT -> rotation += DREHUNG;
			case GLFW_KEY_UP -> laufen(1);
			case GLFW_KEY_DOWN -> laufen(-1);
			default -> {
			}
		}
	}

	public void klick() {
		int bereich = karte.bereich(x, z);
		if (bereich == Karte.SCHLUESSEL) {
			aufgehoben = true;
		}
		if (bereich == Karte.TUER && aufgehoben) {
			aufgeschlossen = true;
		}
	}

	/** Geht einen Schritt vor (1) oder zurück (-1), außer der Weg ist versperrt. */
	private void laufen(int richtung) {
		float dx = richtung * SCHRITT * (float) Math.cos(Math.toRadians(90 + rotation));
		float dz = richtung * SCHRITT * (float) Math.sin(Math.toRadians(90 + rotation));
		x += dx;
		z += dz;
		if (versperrt(karte.bereich(x + 0.7f * dx, z + 0.7f * dz))) {
			x -= dx;
			z -= dz;
		}
	}

	private boolean versperrt(int vorne) {
		return switch (vorne) {
			case Karte.MAUER -> true;
			case Karte.SPINNE -> y < 0.7f;
			case Karte.FRANKENSTEIN -> y < 1.1f;
			case Karte.RAUM -> x < 0.5f && x > -1.5f
					&& (augeNachRechts ? 11.5f + augeZ < z : 12.5f + augeZ > z);
			default -> false;
		};
	}

	private void zurueckZumStart() {
		x = 0;
		z = 0;
		rotation = 0;
	}
}
