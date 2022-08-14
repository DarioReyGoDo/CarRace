/**
 * Clase que simula una rueda.
 */
package carreraf1;

import java.util.Random;

/**
 * @author Darío
 *
 */
public class Rueda {

	public static final int TIPO_BLANDO = 1;
	public static final int TIPO_MEDIO = 2;
	public static final int TIPO_DURO = 3;
	
	public static final double COEFICIENTE_AUMENTO_VELOCIDAD_BLANDO = 0.07;
	public static final double COEFICIENTE_AUMENTO_VELOCIDAD_MEDIO = 0.035;
	public static final double COEFICIENTE_AUMENTO_VELOCIDAD_DURO = 0;
	
	public static final double RITMO_DESGASTE_BLANDO = 0.35;
	public static final double RITMO_DESGASTE_MEDIO = 0.25;
	public static final double RITMO_DESGASTE_DURO = 0.05;
	
	public static final double DESGASTE_MAXIMO = 100;
	public static final double DESGASTE_MINIMO = 0;
	
	public static final double PROB_PINCHAZO_MAXIMO = 0.05;
	public static final double PROB_PINCHAZO_MEDIO = 0.10;
	public static final double PROB_PINCHAZO_MINIMO = 0.20;
	
	private double desgaste;
	private boolean pinchazo;
	private int tipo;
	
	// Clase random que servirá para dejar al azar en cada vuelta si la rueda se pincha.
	private Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Constructor de Rueda. Llama al método boxes directamente ya que boxes le da a los
	 * atributos los valores de una rueda nueva.
	 * 
	 * @param tipo - El tipo de rueda introducido. Entre 1 y 3.
	 * @throws UserInputException - Si el usuario introduce un tipo inválido swe producirá un error.
	 */
	public Rueda(int tipo) throws UserInputException {
		// Como lo que hace el método boxes es "cambiar" la rueda puesta por otra haciendo que sus valores
		// vuelvan a los de una rueda nueva, podemos utilizarlo en el constructor, ya que el código que tienen sería el mismo.
		this.boxes(tipo);
	}
	
	/**
	 * @return - El porcentaje de desgaste del neumático la rueda.
	 */
	public double getDesgaste() {
		return desgaste;
	}

	/**
	 * Método que añade un desgaste pedido por parámetro al desgaste actual de la rueda.
	 * 
	 * @param desgaste - El desgaste a añadir.
	 */
	private void addDesgaste(double desgaste) {
		this.desgaste += desgaste;
		if (this.desgaste > DESGASTE_MAXIMO) {
			this.desgaste = DESGASTE_MAXIMO;
		}
	}
	
	/**
	 * @return - Un booleano que indica si la rueda se ha pinchado.
	 */
	public boolean isPinchazo() {
		return pinchazo;
	}

	/**
	 * @return - El tipo de la rueda.
	 */
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Método que devuelve el tipo de la rueda pero, en vez de devolviendo el entero que lo
	 * representa, devolverá una cadena.
	 * 
	 * @return - El tipo de la rueda como cadena legible.
	 */
	private String getTipoFormateado() {
		String tipo = null;
		switch(this.tipo) {
		case TIPO_BLANDO:
			tipo = "Blando";
			break;
		case TIPO_MEDIO:
			tipo = "Medio";
			break;
		case TIPO_DURO:
			tipo = "Duro";
			break;
		}
		return tipo;
	}
	
	/**
	 * Método que modifica el tipo de la rueda asegurándose antes de que el tipo
	 * introducido sea válido.
	 * 
	 * @param tipo - El tipo de rueda al que se desea cambiar.
	 * @throws UserInputException - Una excepción que se lanzará si el tipo introducido no es válido.
	 */
	private void setTipo(int tipo) throws UserInputException {
		if (tipo == TIPO_BLANDO || tipo == TIPO_MEDIO || tipo == TIPO_DURO) {
			this.tipo = tipo;
		}
		else {
			throw new UserInputException("El tipo de rueda introducido no es válido.");
		}
	}
	
	/**
	 * Método que devolverá el coeficiente de velocidad de la rueda en función de su tipo.
	 * 
	 * @return - El coeficiente de velocidad de la rueda.
	 */
	public double getCoef_Velocidad() {
		double coef_Vel = 0;
		switch(this.tipo) {
		case TIPO_BLANDO:
			coef_Vel = COEFICIENTE_AUMENTO_VELOCIDAD_BLANDO;
			break;
		case TIPO_MEDIO:
			coef_Vel = COEFICIENTE_AUMENTO_VELOCIDAD_MEDIO;
			break;
		case TIPO_DURO:
			coef_Vel = COEFICIENTE_AUMENTO_VELOCIDAD_DURO;
			break;
		}
		return coef_Vel;
	}
	
	/**
	 * Método que recibe la longitud del circuito y calcula el desgasto de las ruedas en una vuelta en
	 * función de la longitud y del ritmo de desgaste de su tipo.
	 * 
	 * @param km - La distancia del circuito que se va a recorrer.
	 * @throws UserInputException - El error se lanzará si se introduce un dato no válido, como que la longitud del circuito sea 0.
	 */
	public void calculaDesgaste(double km) throws UserInputException {
		if (km > 0) {
			switch (this.tipo) {
			case TIPO_BLANDO:
				this.addDesgaste(RITMO_DESGASTE_BLANDO * km);
				break;
			case TIPO_MEDIO:
				this.addDesgaste(RITMO_DESGASTE_MEDIO * km);
				break;
			case TIPO_DURO:
				this.addDesgaste(RITMO_DESGASTE_DURO * km);
			}
		}
		else {
			throw new UserInputException("La longitud de pista introducida no es válida.");
		}
	}
	
	/**
	 * Método que, cada vez que se ejecute (lo que entendemos como una vuelta al circuito en nuestro programa),
	 * comprobará las posibilidades que tiene la rueda de pinchar y realizará un cálculo de azar actuando en consecuencia.
	 */
	public void comprobarPinchazo() {
		// El método sólo realizará la comprobación si la rueda no está pinchada. Si ya está pinchada, sería una
		// pérdida de tiempo.
		if (!this.isPinchazo()) {
			if (desgaste >= 50 && desgaste <= 75) {
				// Se comprueba si el número aleatorio entre 0 y 1 está dentro
				// de la probabilidad de pinchazo.
				if (rnd.nextDouble() <= PROB_PINCHAZO_MINIMO) {
					this.pinchazo = true;
				}
			}
			else if (desgaste > 75 && desgaste <= 90) {
				// Se comprueba si el número aleatorio entre 0 y 1 está dentro
				// de la probabilidad de pinchazo.
				if (rnd.nextDouble() <= PROB_PINCHAZO_MEDIO) {
					this.pinchazo = true;
				}
			}
			else if (desgaste > 90 && desgaste < 100) {
				// Se comprueba si el número aleatorio entre 0 y 1 está dentro
				// de la probabilidad de pinchazo.
				if (rnd.nextDouble() <= PROB_PINCHAZO_MAXIMO) {
					this.pinchazo = true;
				}
			}
			// En este último caso, el desgaste máximo, siempre se produce el pinchazo.
			else if (desgaste == 100) {
				this.pinchazo = true;
			}
		}
	}
	
	/**
	 * Este método simulará un intercambio de rueda en boxes. Restaurará todos los valores
	 * a valores de rueda nueva.
	 * 
	 * @param tipo - El tipo de rueda por el que se desea cambiar.
	 * @throws UserInputException - Una excepción que se lanzará si se introduce un tipo de rueda incorrecto.
	 */
	public void boxes(int tipo) throws UserInputException {
		this.setTipo(tipo);
		this.desgaste = DESGASTE_MINIMO;
		this.pinchazo = false;
	}

	@Override
	/**
	 * Método que devuelve una cadena con el contenido del objeto cuando se llama al mismo directamente.
	 * En este caso se llamará desde la clase coche, así que se ha diseñado para que muestre sólo los datos relevantes
	 * para la simulación y quede agradable a la vista dentro del toString() de la clase coche.
	 */
	public String toString() {
		return "Desgaste de los neumáticos = " + String.format("%.2f", this.desgaste) + "% | " + "Tipo de neumático = " + this.getTipoFormateado();
	}
	
	
	
}
