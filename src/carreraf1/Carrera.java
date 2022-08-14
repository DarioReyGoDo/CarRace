package carreraf1;

import java.util.Random;
import java.util.Scanner;

public class Carrera {
	
	private static Scanner sc = new Scanner(System.in);
	
	private Circuito circuito;
	// Indica cuántas vueltas llevamos ya recorridas
	private int vueltasRecorridas;
	
	public static void main(String[] args) {
		try {
			Carrera c = new Carrera();
			c.start();
			
			System.out.println("La carrera ha terminado. Felicidades al campeón.");
		}
		catch(UserInputException e) {
			System.out.println(e.getMessage());
		}
	}

	public Carrera() throws UserInputException {
		int num_coches_carrera = 4;
		
			circuito = new Circuito("Isla Cristina Grand Prix", 8.15, 57, 35, num_coches_carrera);
		
		
		for (int i = 1; i <= num_coches_carrera; i++) {
			circuito.agregarCocheAParrilla(generaCocheAleatorio(i));			
		}
		
	}
	
	/**
	 * Inicia la carrera. Preguntará al usuario cuántas vueltas quiere simular hasta
	 * que acabe u ocurra un problema que haga que se pause la simulación.
	 */
	public void start() {
		boolean finDeCarrera = false;
		
		
		/*
		 * Indica la última vuelta en la que se paró la simulación.
		 * Útil para indicar cuántas vueltas se han simulado 
		 */
		int ultimaParada = 0;
		
		while (!finDeCarrera) { 
			try {
				// El método simulaCarrera controla que no pasemos un número de vueltas mayor al total de vueltas del circuito
				boolean numeroCorrecto = false;
				int vueltasASimular = 0;
				
				// Este while podría sacarse a un método para mayor claridad
				while(!numeroCorrecto) {
					System.out.printf("Introduce un número de vueltas a simular (%d restantes): ", (this.circuito.getNumeroVueltas() - this.vueltasRecorridas));
					try {
						vueltasASimular = Integer.parseInt(sc.nextLine());
						if (vueltasASimular > 0) {
							numeroCorrecto = true;
						}
					}
					catch (NumberFormatException ex) {
						// No hacemos nada
					}
				}
				this.simulaCarrera(vueltasASimular);
			}
			catch (FinDeCarreraException e) {
				finDeCarrera = true;
				System.out.println(e.getMessage());
			}
			
			System.out.println("Fin de la simulación. Se han dado un total de " + (this.vueltasRecorridas - ultimaParada) + " vueltas.");
			// Actualizamos la última parada
			ultimaParada = this.vueltasRecorridas;
			
//			// Mostramos la parrilla actual
//			for (Coche c: this.circuito.getParrilla()) {
//				System.out.println(c);
//			}
			
			// Mostramos la parrilla actual con el método toString() de circuito.
			System.out.println(circuito);
			
			// ¿Se ha acabado ya la carrera?
			if (this.vueltasRecorridas == this.circuito.getNumeroVueltas()) {
				finDeCarrera = true;
			}
			
			if (!finDeCarrera) {
				// Preguntaremos si algún coche quiere entrar a boxes hasta que responda que no
				boolean continuarPreguntando = true;
				boolean respuestaCorrecta = false;
				String respuesta = "";
				
				while (continuarPreguntando) {
					while(!respuestaCorrecta) {
						// ¿Algún coche quiere entrar a boxes?
						System.out.println("¿Quiere algún coche pasar por boxes(s/n)? Quedan " + (this.circuito.getNumeroVueltas() - this.vueltasRecorridas) + " vueltas.");
						// Preguntamos por teclado
						respuesta = sc.nextLine().toUpperCase();
						if (respuesta.equals("S") || respuesta.equals("N")) {
							respuestaCorrecta = true;
						}
						
					}
					// La siguiente vez volveremos a preguntar
					respuestaCorrecta = false;
					
					// Hay coches que quieren pasar por boxes
					if (respuesta.equals("S")) {
						boolean numeroCorrecto = false;
						int numero = 0;
						
						while(!numeroCorrecto) {
							System.out.println("¿Qué número de coche?");
							// Preguntamos por teclado
							try {
								numero = Integer.parseInt(sc.nextLine());
								if (this.existeCocheNumero(numero)) {
									numeroCorrecto = true;
								}
								else {
									System.out.println("No existe el coche número " + numero + ".");
								}
							}
							catch (NumberFormatException ex) {
								// No hacemos nada
							}
							
						}
						
						// Mandamos el coche a boxes
						this.cocheABoxes(numero);
					}
					else {
						// Ya no hay más coches que quieran pasar por boxes
						continuarPreguntando = false;
					}
				}
			}
		}
		
	}
	
	/**
	 * Comprueba si existe un coche con número numeroCoche
	 * @param numeroCoche
	 * @return
	 */
	private boolean existeCocheNumero(int numeroCoche) {
		boolean encontrado = false;
		int i = 0;
		
		while (i < this.circuito.getParrilla().length && !encontrado) {
			if (this.circuito.getParrilla()[i].getNumero() == numeroCoche) {
				encontrado = true;
			}
			i++;
		}
		
		return encontrado;
	}
	
	/**
	 * Envía el coche número numeroCoche a boxes. En este método se presupone que el coche existe.
	 * @param numeroCoche
	 */
	private void cocheABoxes(int numeroCoche) {
		
		boolean respuestaCorrecta = false;
		int tipoRuedas = 0;
		
		while(!respuestaCorrecta) {
			// ¿Algún coche quiere entrar a boxes?
			System.out.println("¿Qué tipo de ruedas quieres poner?");
			System.out.println(Rueda.TIPO_BLANDO + ": Blandas");
			System.out.println(Rueda.TIPO_MEDIO + ": Medias");
			System.out.println(Rueda.TIPO_DURO + ": Duras");
			// Preguntamos por teclado
			try {
				tipoRuedas = Integer.parseInt(sc.nextLine());
				if (tipoRuedas == Rueda.TIPO_BLANDO || tipoRuedas == Rueda.TIPO_MEDIO || tipoRuedas == Rueda.TIPO_DURO) {
					respuestaCorrecta = true;
				}
			}
			catch (NumberFormatException ex) {
				// No hacemos nada
			}
			
		}
		
		// Utilizamos el mismo "switch" para el combustible
		respuestaCorrecta = false;
		double combustibleARepostar = 0;
		
		while(!respuestaCorrecta) {
			System.out.println("¿Cuánto combustible quieres repostar? (si introduces más de la capacidad, se llenará el depósito):");
			// Preguntamos por teclado
			try {
				combustibleARepostar = Double.parseDouble(sc.nextLine());
				// Permitimos repostar solo para cambiar neumáticos
				if (combustibleARepostar >= 0) {
					respuestaCorrecta = true;
				}
			}
			catch (NumberFormatException ex) {
				// No hacemos nada
			}
			
		}
		
		boolean encontrado = false;
		int i = 0;
				
		while (i < this.circuito.getParrilla().length && !encontrado) {
			if (this.circuito.getParrilla()[i].getNumero() == numeroCoche) {
				try {
					this.circuito.getParrilla()[i].repostar(combustibleARepostar, tipoRuedas, this.circuito.getTiempoPitLane());
				}
				catch (UserInputException ex) {
					// No hacemos nada pues hemos validado el parámetro antes de pasarlo
				}
				encontrado = true;
			}
			i++;
		}
	}
	
	public void simulaCarrera(int vueltasASimular) throws FinDeCarreraException {
		// Tendremos una variable que nos permitirá pausar la simulación en caso de pinchazos
		boolean pausarSimulacion = false;
		int vueltaFinalASimular = this.vueltasRecorridas + vueltasASimular;
		
		// No vamos a simular más vueltas de las que componen la carrera
		if (vueltaFinalASimular > this.circuito.getNumeroVueltas()) {
			vueltaFinalASimular = this.circuito.getNumeroVueltas();
		}
		
		// Nosotros empezamos en la vuelta 0, pero visualmente será la vuelta 1
		System.out.println("Simulando vueltas de la " + (this.vueltasRecorridas + 1) + " a la " + (vueltaFinalASimular) + ".");
		
		for (int i = this.vueltasRecorridas; i < vueltaFinalASimular && !pausarSimulacion; i++) {
			System.out.println("-------------------------------------------------------------------");
			System.out.printf("Vuelta %d\n", this.vueltasRecorridas + 1);
			for (Coche c: this.circuito.getParrilla()) {
				// Los coches accidentados ya no los tendremos en cuenta
				if (!c.getAccidentado()) {
					try {
						double tiempoVuelta = c.correVuelta(this.circuito.getLongitudKms()); 
						System.out.printf("El coche número %d ha dado una vuelta en %s segundos.\n", c.getNumero(), TimeHelper.getTiempoFormateado(tiempoVuelta));
						// La siguiente línea es idéntica a las dos anteriores
						// System.out.printf("El coche número %d ha dado una vuelta en %s segundos", c.getNumero(), TimeHelper.getTiempoFormateado(c.correVuelta(this.circuito.getLongitudKms()));
						
						if (c.getPinchado()) {
							// Pausar la simulación una vez que todos los coches completen esta vuelta
							System.out.println("El coche número " + c.getNumero() + " ha pinchado.");
							pausarSimulacion = true;
						}
						
						if (c.getCantidadRestanteCombustible() < 10) {
							// Pausar la simulación una vez que todos los coches completen esta vuelta
							System.out.println("El coche número " + c.getNumero() + " tiene menos de 10 litros de combustible.");
							pausarSimulacion = true;
						}
					} 
					catch (AccidenteException e) {
						System.out.println(e.getMessage());
					}
					catch (UserInputException e) {
						System.out.println(e.getMessage());
					}
					
				}
			}
			System.out.println("-------------------------------------------------------------------");
			// ¿quedan coches en competición?
			if (this.getCochesAccidentados() == this.circuito.getParrilla().length) {
				throw new FinDeCarreraException("La carrera ha terminado pues ya no hay coches en competición.");
			}
			// Hemos recorrido una vuelta más
			this.vueltasRecorridas++;
		}
	}
	
	private Coche generaCocheAleatorio(int num) {
		Random r = new Random();
		Coche coche = null;
		Piloto piloto;
		Motor motor;
		try {
			piloto = new Piloto ("Piloto " + r.nextInt(), 
					generaValorAleatorio(Piloto.PESO_MINIMO, Piloto.PESO_MAXIMO));
			
			motor = new Motor(
					generaValorAleatorio(Motor.POTENCIA_MINIMA, Motor.POTENCIA_MAXIMA),
					generaValorAleatorio(Motor.CONSUMO_MINIMO, Motor.CONSUMO_MAXIMO),
					generaValorAleatorio(Motor.PESO_MINIMO, Motor.PESO_MAXIMO),
					generaValorAleatorio(Motor.PROBABILIDAD_FALLO_MINIMA, Motor.PROBABILIDAD_FALLO_MAXIMA) //Valores más altos generarán muchos fallos de motor
					);
			
			coche = new Coche(
					num > 0 ? num: generaValorAleatorio(1, 25), 
					"Marca " + generaValorAleatorio(1, 25), 
					generaValorAleatorio(Coche.PESO_MINIMO, Coche.PESO_MAXIMO), 
					generaValorAleatorio(Coche.CAPACIDAD_DEPOSITO_MINIMA, Coche.CAPACIDAD_DEPOSITO_MAXIMA), 
					generaValorAleatorio(0.15, 0.30), 
					generaValorAleatorio(1, 3), 
					motor, 
					piloto);
		}
		catch (UserInputException e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return coche;
	}
	
	/**
	 * Devuelve el número de coches accidentados en la carrera hasta el momento
	 * @return
	 */
	private int getCochesAccidentados() {
		int nAccidentados = 0;
		for (Coche c: this.circuito.getParrilla()) {
			if (c.getAccidentado()) {
				nAccidentados++;
			}
		}
		
		return nAccidentados;
	}
	
	private double generaValorAleatorio(double inicio, double fin) {
		return Math.random() * (fin - inicio) + inicio;
	}
	
	private int generaValorAleatorio(int inicio, int fin) {
		// Redondeamos al entero más cercano para conseguir que fin también esté incluido. 
		return (int) (Math.round((Math.random() * (fin - inicio) + inicio)));
	}

}
