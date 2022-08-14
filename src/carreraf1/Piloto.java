/**
 * Clase que simula un piloto.
 */
package carreraf1;

/**
 * @author Darío
 *
 */
public class Piloto {

	public static final double PESO_MAXIMO = 82;
	public static final double PESO_MINIMO = 60;
	
	private String nombre;
	private double peso;
	
	/**
	 * El constructor de Piloto.
	 * 
	 * @param nombre - El nombre del piloto.
	 * @param peso - El peso del piloto.
	 * @throws UserInputException - Esta excepción se lanzará si se introduce algún dato erróneo.
	 */
	public Piloto(String nombre, double peso) throws UserInputException {
		// Lanzamos una excepción si el nombre está vacío.
		if (nombre.length() == 0) {
			throw new UserInputException("No ha introducido ningún nombre.");
		} else {
			this.nombre = nombre;
		}
		// Si el peso se encuentra fuera del intervalo permitido, se lanza una excepción.
		if (peso < PESO_MINIMO || peso > PESO_MAXIMO) {
			throw new UserInputException("El peso introducido se sale del intervalo permitido.");
		}
		else {
			this.peso = peso;
		}
	}

	/**
	 * @return - El nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return - El peso.
	 */
	public double getPeso() {
		return peso;
	}

	@Override
	/**
	 * Método que devuelve una cadena con el contenido del objeto cuando se llama al mismo directamente.
	 * En este caso se llamará desde la clase coche, así que se ha diseñado para que muestre sólo los datos relevantes
	 * para la simulación y quede agradable a la vista dentro del toString() de la clase coche.
	 */
	public String toString() {
		return "Piloto = " + this.nombre + " | " + "Peso = " + String.format("%.2f", this.peso) + "Kg";
	}
	
}
