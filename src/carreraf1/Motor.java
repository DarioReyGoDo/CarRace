/**
 * Clase que simula un motor.
 */
package carreraf1;

import java.util.Random;

/**
 * @author Darío
 *
 */
public class Motor {

	public static final int POTENCIA_MAXIMA = 950;
	public static final int POTENCIA_MINIMA = 930;
	public static final double CONSUMO_MAXIMO = 37;
	public static final double CONSUMO_MINIMO = 28;
	public static final double PROBABILIDAD_FALLO_MAXIMA = 0.005;
	public static final double PROBABILIDAD_FALLO_MINIMA = 0;
	public static final double PESO_MAXIMO = 55;
	public static final double PESO_MINIMO = 45;
	
	private final int potencia;
	private final double consumo_100km;
	private final double prob_fallo;
	private final double peso;
	
	// Clase que se usará para obtener valores aleatorios en los cálculos de probabilidad de fallo de motor.
	private Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * El constructor de Motor. Debe dar valor a todos los atributos del objeto ya que esos mismos
	 * atributos son constantes. No se considera que ningún atributo de motor debiera poder modificarse
	 * una vez creado.
	 * 
	 * @param potencia - Los CV del motor.
	 * @param consumo - El consumo de combustible por cada 100Km del motor.
	 * @param peso - El peso del motor.
	 * @param prob_fallo - La probabilidad que tiene el motor de fallar.
	 * @throws UserInputException - La excepción se lanza si algún dato no está entre los intervalos definidos.
	 */
	public Motor(int potencia, double consumo, double peso, double prob_fallo) throws UserInputException {
		if (potencia < POTENCIA_MINIMA || potencia > POTENCIA_MAXIMA) {
			throw new UserInputException("La potencia introducida se sale del intervalo permitido.");
		} 
		else {
			this.potencia = potencia;
		}
		if (consumo < CONSUMO_MINIMO || consumo > CONSUMO_MAXIMO) {
			throw new UserInputException("El consumo introducido se sale del intervalo permitido.");
		} 
		else {
			this.consumo_100km = consumo;
		}
		if (prob_fallo < PROBABILIDAD_FALLO_MINIMA || prob_fallo > PROBABILIDAD_FALLO_MAXIMA) {
			throw new UserInputException("La probabilidad de fallo introducida se sale del intervalo permitido.");
		}
		else {
			this.prob_fallo = prob_fallo;
		}
		if (peso < PESO_MINIMO || peso > PESO_MAXIMO) {
			throw new UserInputException("El peso introducido se sale del intervalo permitido.");
		}
		else {
			this.peso = peso;
		}
	}
	
	/**
	 * @return - La probabilidad de fallo.
	 */
	private double getProb_fallo() {
		return prob_fallo;
	}

	/**
	 * @return - La potencia.
	 */
	public int getPotencia() {
		return potencia;
	}

	/**
	 * @return - El consumo cada 100Km.
	 */
	public double getConsumo_100km() {
		return consumo_100km;
	}

	/**
	 * @return - El peso del motor.
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Un método que simula una vuelta. Determina si se producirá un
	 * fallo de motor o no.
	 * 
	 * @throws FalloDeMotorException - Un fallo que se propaga cuando se cumple la probabilidad de que el motor falle.
	 */
	public void motorVuelta() throws FalloDeMotorException {
		// Se comprueba si el siguiente número aleatorio entre 0 y 1 se encuentra en el intervalo que podríamos llamar "zona de fallo".
		if (rnd.nextDouble() <= this.getProb_fallo()) {
			throw new FalloDeMotorException("El motor ha fallado, el coche debe abandonar la carrera.");
		}
	}

	@Override
	/**
	 * Método que devuelve una cadena con el contenido del objeto cuando se llama al mismo directamente.
	 * En este caso se llamará desde la clase coche, así que se ha diseñado para que muestre sólo los datos relevantes
	 * para la simulación y quede agradable a la vista dentro del toString() de la clase coche.
	 */
	public String toString() {
		return "CV = " + this.potencia + " | " + "Consumo cada 100Km = " + String.format("%.2f", this.consumo_100km) + "l";
	}
	
}
