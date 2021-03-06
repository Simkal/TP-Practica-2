package comandos;
import java.util.Scanner;

import logica.Mundo;

public class Iniciar implements Comando {
	
	@Override
	public String ejecuta(Mundo mundo, Scanner in){
		mundo.vaciar();
		mundo.generarCelulas();
		return "Iniciando el mundo" + System.getProperty("line.separator");
	}

	@Override
	public Comando parsea(String[] palabras) {
		if((palabras.length == 1) && (palabras[0].equalsIgnoreCase("iniciar"))){
			return new Iniciar();
		}
		else return null;
	}

	@Override
	public String textoAyuda() {
		return ("INICIAR : Inicia las celulas aleatoriamente" + System.getProperty("line.separator")); 
	}

}
