/**
 * Clase que simula un coche.
 */
package carreraf1;

/**
 * @author Darío
 *
 */
public class Coche {

	public static final double PESO_MAXIMO = 520;
	public static final double PESO_MINIMO = 500;
	public static final int CAPACIDAD_DEPOSITO_MAXIMA = 120;
	public static final int CAPACIDAD_DEPOSITO_MINIMA = 100;
	public static final double MAX_COEF_AERODINAMICO = 0.30;
	public static final double MIN_COEF_AERODINAMICO = 0.15;
	
	private final int id;
	private Piloto piloto;
	private final String marca;
	private final double peso;
	private final Motor motor;
	private Rueda[] ruedas;
	private final int cap_deposito;
	private double comb_restante;
	private final double coef_aerodinamico;
	private double tiempo_ult_vuelta;
	private double tiempo_total;
	private boolean accidentado;
	private boolean boxes;
	
	/**
	 * Constructor de coche. Realizará una serie de comprobaciones para asegurarse de que
	 * los datos introducidos son correctos. En caso de que haya un dato incorrecto, se lanzará una
	 * excepción UserInputException.
	 * 
	 * @param id - El identificador de coche. Se comprobará que sea positivo.
	 * @param marca - La marca del coche. Se comprobará que no esté vacío.
	 * @param peso - El peso total del coche junto con el piloto y el motor (sin tener el combustible en cuenta). Se comprobará que el peso del chásis está entre un intervalo.
	 * @param cap_deposito - La capacidad del depósito de combustible. Se comprobará que se encuentre dentro de unos intervalos adecuados.
	 * @param coef_aerodinamico - El coeficiente aerodinámico del coche. Se comprobará que se encuentre dentro de un intervalo.
	 * @param tipo_rueda - El tipo de rueda que tendrá el coche. Se comprobará que el tipo d e rueda exista.
	 * @param motor - El motor del coche.
	 * @param piloto - El piloto del coche.
	 * @throws UserInputException - Excepción que se lanza cuando algún dato recibido no es correcto.
	 */
	public Coche(int id, String marca, double peso, int cap_deposito, double coef_aerodinamico, int tipo_rueda, Motor motor, Piloto piloto) throws UserInputException {
		if (id > 0) {
			this.id = id;
		}
		else {
			throw new UserInputException("El número del coche no puede ser 0 o negativo.");
		}
		if (marca.length() != 0) {
			this.marca = marca;
		}
		else {
			throw new UserInputException("El coche debe tener una marca.");
		}
		if (peso >= PESO_MINIMO && peso <= PESO_MAXIMO) {
			this.peso = peso + piloto.getPeso() + motor.getPeso();
		}
		else {
			throw new UserInputException("El peso introducido no está permitido.");
		}
		if (cap_deposito >= CAPACIDAD_DEPOSITO_MINIMA && cap_deposito <= CAPACIDAD_DEPOSITO_MAXIMA) {
			this.cap_deposito = cap_deposito;
			// El coche comenzará con el depósito lleno.
			this.comb_restante = cap_deposito;
		}
		else {
			throw new UserInputException("La capacidad de depósito introducida no está permitida.");
		}
		if (coef_aerodinamico >= MIN_COEF_AERODINAMICO && coef_aerodinamico <= MAX_COEF_AERODINAMICO) {
			this.coef_aerodinamico = coef_aerodinamico;
		}
		else {
			throw new UserInputException("El coeficiente aeródinámico introducido no está permitido.");
		}
		if (tipo_rueda == Rueda.TIPO_BLANDO || tipo_rueda == Rueda.TIPO_MEDIO || tipo_rueda == Rueda.TIPO_DURO) {
			// Este array almacenará las 4 ruedas del coche.
			ruedas = new Rueda[] {new Rueda(tipo_rueda), new Rueda(tipo_rueda), new Rueda(tipo_rueda), new Rueda(tipo_rueda)};
		}
		else {
			throw new UserInputException("El tipo de rueda introducido no existe.");
		}
		this.motor = motor;
		this.piloto = piloto;
		// Al comenzar, el coche no estará accidentado.
		this.accidentado = false;
		// Al inicializar, el coche no habrá pasado por boxes aún.
		this.boxes = false;
	}
	
	/**
	 * @return - El número que identifica a coche.
	 */
	public int getNumero() {
		return this.id;
	}
	
	/**
	 * @return - El piloto que lleva el coche.
	 */
	public String getPiloto() {
		return this.piloto.toString();
	}
	
	/**
	 * @return - La marca del coche.
	 */
	public String getMarca() {
		return this.marca;
	}
	
	/**
	 * @return - El peso total del coche, incluyendo el peso del combustible restante.
	 */
	public double getPeso_total() {
		return this.peso + this.comb_restante;
	}
	
	/**
	 * @return - Una cadena con el contenido de motor tal como está definido en su método toString().
	 */
	public String getMotor() {
		return this.motor.toString();
	}
	
	/**
	 * @return - La capacidad del depósito.
	 */
	public int getCap_deposito() {
		return this.cap_deposito;
	}
	
	/**
	 * @return - La cantidad restante de combustible.
	 */
	public double getCantidadRestanteCombustible() {
		return this.comb_restante;
	}
	
	/**
	 * @return - El coeficiente aerodinámico del coche.
	 */
	public double getCoef_aerodinamico() {
		return this.coef_aerodinamico;
	}
	
	/**
	 * @return - El tiempo que el coche ha tardado en dar la última vuelta.
	 */
	public double getTiempo_ult_vuelta() {
		return this.tiempo_ult_vuelta;
	}
	
	/**
	 * @return - El tiempo total que el coche lleva en la carrera.
	 */
	public double getTiempo_total() {
		return this.tiempo_total;
	}
	
	/**
	 * @return - Un booleano que indica si el coche ha sufrido un accidente.
	 */
	public boolean getAccidentado() {
		return this.accidentado;
	}
	
	/**
	 * @return - Un booleano que indica si el coche ha pasado por boxes en la última vuelta.
	 */
	public boolean getBoxes() {
		return this.boxes;
	}
	
	/**
	 * Método que comprueba si alguna de las ruedas del coche está pinchada.
	 * 
	 * @return - Un booleano que será true si hay algún pinchazo o false si las ruedas están perfectas.
	 */
	public boolean getPinchado() {
		boolean pinchada = false;
		// Se recorre el array y se sale del bucle cuando no queden más elementos o se haya encontrado un pinchazo.
		for (int i = 0; i < ruedas.length && !pinchada; i++) {
			if (ruedas[i].isPinchazo()) {
				pinchada = true;
			}
		}
		return pinchada;
	}
	
	/**
	 * Método que devuelve la velocidad máxima del coche. El cálculo se hace mediante una fórmula.
	 * - Sea c el coeficiente aerodinámico
	 * - p la potencia del motor
	 * - peso, la suma de todos los pesos (chásis, piloto, motor y litros de
	 * - combustible restantes. Supón que 1 litro de combustible pesa 1 kilo).
	 * 	r el coeficiente de los neumáticos
	 * - Si el coche tiene una rueda pinchada, su velocidad se reduce a la
	 *	mitad.
	 *		velocidad = (1+r) * 350 - ((potencia máxima motor - p) * 0.6 + (75 * c)
	 *		+ 0.5 * (peso - 705))
	 * 
	 * @return - La velocidad máxima del coche.
	 */
	public double getVelocidadMaxima() {
		double velocidad;
		
		velocidad = (1 + ruedas[0].getCoef_Velocidad()) * 350 - ((Motor.POTENCIA_MAXIMA - motor.getPotencia()) 
				* 0.6 + (75 * this.getCoef_aerodinamico()) + 0.5 * (this.getPeso_total() - 705));

		// Si hay alguna rueda pinchada, la velocidad se reduce a la mitad.
		if (getPinchado()) {
			velocidad /= 2;
		}
		return velocidad;
	}
	
	/**
	 * Método que simula una vuelta de la carrera.
	 * 
	 * @param km - La longitud del circuito.
	 * @return - El tiempo que de la última vuelta.
	 * @throws AccidenteException - Se lanza cuando el coche ha tenido un accidente y debe abandonar la carrera.
	 * @throws UserInputException - Se lanza cuando algún dato introducido es erróneo.
	 */
	public double correVuelta(double km) throws AccidenteException, UserInputException {
		// Al comenzar la vuelta el coche no habrá pasado por boxes aún.
		this.boxes = false;
		// Al principio de cada vuelta se restablece el tiempo de la anterior.
		this.tiempo_ult_vuelta = 0;
		// El coche consume combustible al dar la vuelta.
		this.consumeCombustible(km);
		// Se comprueba si el motor falla.
		this.pruebaMotor();
		// Se comprueba si algún/os neumático/s pincha.
		this.pruebaPinchazo(km);
		// Se devuelve el tiempo de la última vuelta.
		return this.calculaTiempoVuelta(km, this.getVelocidadMaxima());
	}
	
	/**
	 * Método que comprueba en todas las ruedas si en esta vuelta se pinchan.
	 * 
	 * @param km - La longitud del circuito.
	 * @throws AccidenteException - Se lanza cuando el coche ha tenido un accidente y debe abandonar la carrera.
	 * @throws UserInputException - Se lanza cuando algún dato introducido es erróneo.
	 */
	private void pruebaPinchazo(double km) throws AccidenteException, UserInputException {
		int num_pinchadas = 0;
		// Se recorren todas las ruedas.
		for (int i = 0; i < ruedas.length; i++) {
			// Se calcula el desgaste que sufren.
			ruedas[i].calculaDesgaste(km);
			// Una vez se tiene el desgaste, se procede a comprobar si les ha llegado el momento de pincharse.
			ruedas[i].comprobarPinchazo();
			// Se llevará un contador por rueda pinchada para saber si el coche se tendrá que salir de la carrera por pinchazo múltiple.
			if (ruedas[i].isPinchazo()) {
				num_pinchadas++;
			}
		}
		// Si se produce un pinchazo múltiple, el coche pasa su atributo accidentado a true y manda la excepción a la clase Carrera.
		if (num_pinchadas > 1) {
			this.accidentado = true;
			throw new AccidenteException(String.format("El coche número %d ha tenido un pinchazo múltiple. No podrá continuar la carrera.", id));
		}
	}
	
	/**
	 * Método que comprueba si el motor falla en la vuelta actual.
	 * 
	 * @throws AccidenteException - Se lanza cuando el coche ha tenido un accidente y debe abandonar la carrera.
	 */
	private void pruebaMotor() throws AccidenteException {
		try {
			this.motor.motorVuelta();
		}
		// Si el método del motor devuelve la excepción es porque ha fallado, se recoge la misma. Una vez en el catch,
		// se pasa accidentado a true y se devuelve un AccidenteException para la clase carrera.
		catch (FalloDeMotorException e) {
			this.accidentado = true;
			throw new AccidenteException(String.format("El motor del coche %d ha fallado. No podrá continuar la carrera.", id));
		}
	}
	
	/**
	 * Método que controla el combustible que consume el coche en una vuelta.
	 * 
	 * @param km - La longitud del circuito.
	 * @throws AccidenteException - Se lanza cuando el coche ha tenido un accidente y debe abandonar la carrera.
	 */
	private void consumeCombustible(double km) throws AccidenteException {
		// Regla de 3.
		double combARestar = (km * motor.getConsumo_100km() / 100);
		// Si la cantidad restante de combustible que quedaría al gastar el necesario fuera negativa
		// se cambia el depósito a vacío. Tras eso, se cambia accidentado a true y se lanza una excepción de Accidente, el coche tiene que salir de la carrera.
		if (this.getCantidadRestanteCombustible() - combARestar <= 0) {
			this.comb_restante = 0;
			this.accidentado = true;
			throw new AccidenteException(String.format("El coche número %d se quedó sin combustible. No podrá continuar la carrera.", id));
		}
		// Si no se produce ningún problema, por otro lado, simplemente se resta el combustible.
		else {
			this.comb_restante -= combARestar;
		}
	}
	
	/**
	 * Calcula y devuelve el tiempo que tarda el coche en dar una vuelta. 
	 * De paso, actualiza los campos de tiempo en coche.
	 * 
	 * @param km - La longitud del circuito.
	 * @param velocidad - La velocidad máxima del coche.
	 * @return - El tiempo transcurrido en la última vuelta.
	 */
	private double calculaTiempoVuelta(double km, double velocidad) {
		// La fórmula del tiempo es t = d / v. Como devuelve horas, se pasa a segundos multiplicándolo por 3600.
		double tiempo_transcurrido = km / velocidad * 3600;
		// Se almacena el tiempo en cada variable y se devuelve.
		this.tiempo_ult_vuelta += tiempo_transcurrido;
		this.tiempo_total += tiempo_transcurrido;
		return tiempo_transcurrido;
		
	}
	
	/**
	 * Método que simula la entrada del coche a boxes.
	 * 
	 * @param num_lit - La cantidad de litros de gasolina que se van a echar.
	 * @param tipo_rueda - El nuevo tipo de rueda para el coche.
	 * @param tiempo_penalizacion - El tiempo de penalización que tendrá el coche por entra a boxes.
	 * @throws UserInputException - Se lanza cuando algún dato introducido es erróneo.
	 */
	public void repostar(double num_lit, int tipo_rueda, int tiempo_penalizacion) throws UserInputException {
		// Los coches sólo entrarán por boxes si no han sufrido un accidente o no han pasado aún por boxes en la vuelta actual.
		if (this.getAccidentado()) {
			throw new UserInputException("No es posible pasar un coche accidentado por boxes.");
		}
		if (this.getBoxes()) {
			throw new UserInputException("No es posible pasar por boxes dos veces en la misma vuelta.");
		}
		// Se comprueba que el combustible sea un número válido.
		if (num_lit < 0) {
			throw new UserInputException("No es posible introducir una cantidad negativa de combustible.");
		}
		// Si el combustible que se quiere introducir es mayor que la capacidad del depósito, el valor del combustible
		// actual pasará a ser el máximo.
		else if (this.comb_restante + num_lit > this.cap_deposito){
			this.comb_restante = this.cap_deposito;
		}
		// Si no se pasa del tope, simplemente se almacena en el depósito.
		else {
			this.comb_restante += num_lit;
		}
		// Se colocan las ruedas nuevas según el tipo introducido.
		for (int i = 0; i < ruedas.length; i++) {
			ruedas[i].boxes(tipo_rueda);
		}
		// Se suma la penalización de tiempo.
		this.tiempo_ult_vuelta += tiempo_penalizacion;
		this.tiempo_total += tiempo_penalizacion;
		// Se le indica al coche que ya ha pasado por boxes en la vuelta actual.
		this.boxes = true;
	}

	/**
	 * Método cuyo propósito es mostrar un booleano introducido como Sí o No.
	 * 
	 * @param accidentado - Booleano que indica si el coche ha sufrido un accidente.
	 * @return - La cadena que indica si el coche ha sufrido un accidente con anterioridad y se encuentra fuera de la carrera.
	 */
	private static String formatAccidentado (boolean accidentado) {
		String respuesta;
		if (accidentado) {
			respuesta = "Sí";
		}
		else {
			respuesta = "No";
		}
		return respuesta;
	}
	
	/**
	 * Método que compara dos objetos coche y devuelve:
	 * 	1 si el primer coche es mayor.
	 * 	-1 si el primer coche es menor.
	 * 	0 si ambos son iguales.
	 * 
	 * @param coche El coche a comparar.
	 * @return Un entero que indica el resultado.
	 */
	public int compareTo(Coche coche) {
		final int mayor = 1;
		final int menor = -1;
		int resultado = 0;
		// Consideraremos que los coches accidentados son mayores y se pasarán hacia la derecha en el array.
		if (this.getAccidentado() && !coche.getAccidentado()) {
			resultado = mayor;
		}
		else if (!this.getAccidentado() && coche.getAccidentado()) {
			resultado = menor;
		}
		else if (!this.getAccidentado() && !coche.getAccidentado()) {
			resultado = this.compararSemejantes(coche);
		}
		else {
			resultado = this.compararSemejantes(coche);
		}
		return resultado;
	}
	
	/**
	 * Método complementario al CompareTo(). Se ha sacado para facilitar la lectura.
	 * 
	 * @param coche El coche a comparar.
	 * @return Un entero que indica el resultado.
	 */
	private int compararSemejantes(Coche coche) {
		final int mayor = 1;
		final int menor = -1;
		final int igual = 0;
		int resultado = 0;
		// Se considerará que el coche con mayor tiempo total será mayor, así que se pasará a la derecha del array.
		// En caso de empate se ordenarán por su id en orden alfabético.
		if (this.getTiempo_total() < coche.getTiempo_total()) {
			resultado = menor;
		}
		else if (this.getTiempo_total() > coche.getTiempo_total()) {
			resultado = mayor;
		}
		else if (this.getTiempo_total() == coche.getTiempo_total()) {
			if (this.getNumero() < coche.getNumero()) {
				resultado = menor;
			}
			else if (this.getNumero() > coche.getNumero()) {
				resultado = mayor;
			}
			else {
				resultado = igual;
			}
		}
		return resultado;
	}
	
	@Override
	/**
	 * Método que mostrará por pantalla los datos relevantes del coche de manera agradable.
	 * Para mejorar la visibilidad del estado de todas las partes del coche se llama al método
	 * toString() de sus clases contenidas. De esta manera, se consigue una amplia visión del estado de cada coche.
	 */
	public String toString() {
		return "-------------------------------------------------------------------\n" +
				"Coche " + id + " | " + marca + "\n" +
				"Velocidad = " + String.format("%.2f", this.getVelocidadMaxima()) + "Km/h\n" +
				"Capacidad del depósito = " + this.cap_deposito + "l\n" +
				"Combustible restante = " + String.format("%.2f", this.comb_restante) + "l\n" +
				"Última vuelta = " + TimeHelper.getTiempoFormateado(this.tiempo_ult_vuelta) + " | " + "Tiempo total = " + TimeHelper.getTiempoFormateado(this.tiempo_total) + "\n" +
				ruedas[0] + "\n" + piloto + "\n" + motor + "\n" +
				"Peso actual = " + String.format("%.2f", this.getPeso_total()) + "Kg\n" +
				"Pinchazo = " + Coche.formatAccidentado(this.getPinchado()) + "\n" +
				"Accidentado = " + Coche.formatAccidentado(this.accidentado) + "\n" +
				"-------------------------------------------------------------------\n";
	}
	
}
