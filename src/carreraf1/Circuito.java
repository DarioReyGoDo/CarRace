/**
 * Clase que simula un circuito.
 */
package carreraf1;

/**
 * @author Darío
 *
 */
public class Circuito {

	private final String nombre;
	// La longitud se considerará en metros.
	private final double longitud;
	private final int vueltas;
	// El tiempo de parada en boxes será en segundos.
	private int tiempoPitLane; 
	private Coche[] parrilla;
	
	/**
	 * Constructor de circuito. Se asegurará de que los datos introducidos sean válidos.
	 * 
	 * @param nombre - El nombre que tendrá el circuito.
	 * @param longitud - La longitud del circuito.
	 * @param vueltas - El número de vueltas que componen la carrera.
	 * @param tiempoPitLane - El tiempo de penalización que se suma a cada corredor que haya.
	 * @param numCoches - El número de coches que participarán en una competición.
	 * @throws UserInputException - Se lanzará una excepción si algún dato introducido es incorrecto.
	 */
	public Circuito(String nombre, double longitud, int vueltas, int tiempoPitLane, int numCoches) throws UserInputException {
		// Comprueba que la cadena de nombre contenga algo.
		if (nombre.length() != 0) {
			this.nombre = nombre;
		}
		else {
			throw new UserInputException("El nombre del circuito no es válido.");
		}
		// Comprueba que la longitud del circuito sea mayor que 0.
		if (longitud > 0) {
			this.longitud = longitud;
		}
		else {
			throw new UserInputException("La longitud del circuito no es válida.");
		}
		// Comprueba que, al menos, haya que darle una vuelta al circuito.
		if (vueltas > 0) {
			this.vueltas = vueltas;
		}
		else {
			throw new UserInputException("El número de vueltas al circuito no es válido.");
		}
		// Comprueba que el tiempo en boxes deba ser positivo usando un setter.
		this.setTiempoPitLane(tiempoPitLane);
		// Comprueba que haya, al menos, un coche.
		if (numCoches > 0) {
			this.parrilla = new Coche[numCoches];		
		}
		else {
			throw new UserInputException("El número de coches introducido no es válido.");
		}
	}

	/**
	 * @return - El nombre del circuito.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return - La longitud del circuito en Kms.
	 */
	public double getLongitudKms() {
		return longitud;
	}

	/**
	 * @return - Las vueltas que se deben dar al circuito.
	 */
	public int getNumeroVueltas() {
		return vueltas;
	}

	/**
	 * @return - El tiempo de penalización por entrar en boxes.
	 */
	public int getTiempoPitLane() {
		return tiempoPitLane;
	}

	/**
	 * Método que modifica el tiempo de penalización por entrar en boxes. Se asegura
	 * de que el tiempo con el que se penaliza sea positivo.
	 * 
	 * @param tiempoPitLane - El tiempo de penalización por entrar en boxes.
	 * @throws UserInputException - Se lanza cuando el tiempo introducido no es positivo.
	 */
	public void setTiempoPitLane(int tiempoPitLane) throws UserInputException {
		if (tiempoPitLane > 0) {
			this.tiempoPitLane = tiempoPitLane;
		}
		else {
			throw new UserInputException("El tiempo de boxes no es válido.");
		}
	}

	/**
	 * @return - La parrilla de coches.
	 */
	public Coche[] getParrilla() {
		return parrilla;
	}
	
	/**
	 * @return - La parrilla de coches ordenada por tiempo y por seguir en la carrera.
	 */
	public Coche[] getParrillaOrdenada() {
		this.ordenaParrilla();
		return parrilla;
	}
	
	/**
	 * Método que agrega un coche en el siguiente huevo disponible de la parrilla.
	 * Si no quedan huecos, devuelve una excepción IndexOutOfBoundsException.
	 * 
	 * @param coche - El coche que se quiere almacenar en la parrilla.
	 */
	public void agregarCocheAParrilla(Coche coche) {
		int posicion = 0;
		// Se recorre la parrilla.
		for (int i = 0; i < parrilla.length; i++) {
			// Por cada elemento encontrado, sumamos 1 al contador.
			// De esta manera, sabremos cuál es la siguiente posición disponible.
			if (parrilla[i] != null) {
				posicion++;
			}
		}
		// Si la posición no se sale del array, se almacenará el coche.
		if (posicion < parrilla.length) {
			parrilla[posicion] = coche;
		}
		// Si la posición se sale del array, se lanzará la excepción IndexOutOfBoundsException.
		else {
			throw new IndexOutOfBoundsException("Se han intentado introducir más coches de los posibles en la parrilla.");
		}	
	}

	/**
	 * Método que utiliza el algoritmo de ordenamiento de burbuja para ordenar a los coches en la parrilla
	 * teniendo en cuenta su tiempo total y si están accidentados.
	 */
	private void ordenaParrilla() {
		final int mayor = 1;
		Coche aux;
		for (int i = 1; i < parrilla.length; i++) {
			for (int j = 0; j < parrilla.length - i; j++) {
				if (parrilla[j].compareTo(parrilla[j + 1]) == mayor) {
					aux = parrilla[j];
					parrilla[j] = parrilla[j + 1];
					parrilla[j + 1] = aux;
				}
			}
		}
	}
	
	@Override
	/**
	 * Método que mostrará por pantalla de forma agradable los datos relevantes del circuito además de los datos
	 * relevantes de cada uno de los coches que están participando.
	 */
	public String toString() {
		// Se almacena el output de los métodos toString de cada uno de los coches de la carrera en un StringBuilder
		// para facilitar el mostrarlos por pantalla con el resto de la cadena.
		Coche[] cochesOrdenados = this.getParrillaOrdenada();
		StringBuilder parrilla = new StringBuilder();
		for (int i = 0; i < this.getParrilla().length; i++) {
			parrilla.append(cochesOrdenados[i]);
		}
		return "###################################################################\n" + 
				this.nombre + "\n" +
				"Longitud = " + this.longitud + "km | " + "Vueltas = " + this.vueltas + "\n" +
				"Número de participantes = " + this.parrilla.length + "\n" +
				"Tiempo de boxes = " + this.tiempoPitLane + "s\n" +
				"###################################################################\n" + 
				parrilla.toString();
	}
	
}
