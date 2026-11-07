import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import szene.Outro;
import szene.Szene;

/**
 * Einstiegspunkt. Erstellt das GLFW-Fenster, leitet Tastatur und Maus an die
 * Szene weiter und führt die Spielschleife aus.
 */
public class Main {

	private static final int BREITE = 600;
	private static final int HOEHE = 600;
	private static final String TITEL = "Horror-Labyrinth: Finde den Schlüssel, um dem Horror zu entkommen! "
			+ "(Starten = Enter, Laufen = Pfeiltasten, Hüpfen = Leertaste, "
			+ "Schlüssel nehmen / Tür öffnen = Mausklick, Taschenlampe an = E / aus = A)";

	public static void main(String[] args) {
		Szene szene = new Szene();

		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("GLFW konnte nicht gestartet werden");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		long fenster = glfwCreateWindow(BREITE, HOEHE, TITEL, NULL, NULL);
		if (fenster == NULL) {
			throw new IllegalStateException("Fenster konnte nicht erstellt werden");
		}

		glfwSetKeyCallback(fenster, (f, taste, scancode, aktion, mods) -> {
			if (taste == GLFW_KEY_ESCAPE && aktion == GLFW_RELEASE) {
				glfwSetWindowShouldClose(f, true);
			}
			szene.taste(taste, aktion);
		});
		glfwSetMouseButtonCallback(fenster, (f, knopf, aktion, mods) -> {
			if (knopf == GLFW_MOUSE_BUTTON_LEFT && aktion == GLFW_PRESS) {
				szene.klick();
			}
		});
		glfwSetFramebufferSizeCallback(fenster, (f, breite, hoehe) -> {
			if (breite > 0 && hoehe > 0) {
				glViewport(0, 0, breite, hoehe);
				szene.setSeitenverhaeltnis((float) breite / hoehe);
			}
		});

		GLFWVidMode monitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (monitor != null) {
			glfwSetWindowPos(fenster, (monitor.width() - BREITE) / 2, (monitor.height() - HOEHE) / 2);
		}

		glfwMakeContextCurrent(fenster);
		glfwSwapInterval(1);
		GL.createCapabilities();
		szene.init();
		glfwShowWindow(fenster);

		while (!glfwWindowShouldClose(fenster) && !szene.istBeendet()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			szene.zeichnen();
			glfwSwapBuffers(fenster);
			glfwPollEvents();
		}

		glfwFreeCallbacks(fenster);
		glfwDestroyWindow(fenster);
		glfwTerminate();
		glfwSetErrorCallback(null).free();

		if (szene.istBeendet()) {
			Outro.zeigen();
		}
	}
}
