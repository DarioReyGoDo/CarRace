/**
 * Clase auxiliar que sirve para formatear el tiempo.
 */
package carreraf1;

/**
 * @author Darío
 *
 */
public class TimeHelper {

	/**
	 * Método estático que recibe el tiempo en segundos y lo formatea como una cadena agradable a la vista.
	 * La cadena será del tipo hh:mm:ss. El bloque de los segundos contará, además, con 3 decimales.
	 * 
	 * @param time - El tiempo introducido en segundos.
	 * @return - La cadena formateada con el tiempo en formato hh:mm:ss,sss
	 */
	public static String getTiempoFormateado (double time) {
		//Declaramos una variable de cadena en la que devolveremos el tiempo formateado
		String formatTime = "";
		//Variables de tiempo
		int h = 0;
		int m = 0;
		double s;
		
		// Comenzamos asignando los segundos a su variable correspondiente.
		s = time;
		
		//Pasamos a almacenar el tiempo
		//Comparamos si ha pasado suficiente tiempo para almacenar horas
		if ((int) (s / 3600) > 0) {
			//Si podemos almacenar horas, convertimos los segundos a horas
			h = (int) (s / 3600);
			//Los segundos sobrantes que no se pueden pasar a horas, se almacenan en la variable de segundos
			s = s % 3600;
		}
		//Comparamos si ha pasado suficiente tiempo para almacenar minutos
		if ((int) (s / 60) > 0) {
			//Si podemos almacenar minutos, convertimos los segundos a minutos
			m = (int) (s / 60);
			//Los segundos sobrantes que no se pueden pasar a minutos, se almacenan en la variable de segundos
			s = s % 60;
		}
		
		//Formateamos el tiempo de forma que añada 0 a la izquierda en cada variable hasta que haya dos dígitos
		//(de la forma 00:00:00.000)
		formatTime = String.format("%02d:%02d:%06.3f", h, m ,s);
		
		//Devolvemos la cadena de tiempo formateada
		return formatTime;
	}

}
