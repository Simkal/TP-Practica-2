package logica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuffer;
import java.util.Scanner;
import celula.Celula;
import celula.CelulaCompleja;
import celula.CelulaSimple;

public class Mundo{
	
	private static final int NUMEROCELULASSIMPLES = 4;
	private static final int NUMEROCELULASCOMPLEJAS = 2;
	private static final int NUMEROFILAS = 3;
	private static final int NUMEROCOLUMNAS = 4;
	private boolean simulacionTerminada = false;
	private Superficie superficie;

		
	/**
	 * La clase constructor de Mundo, genera una superficie con una longitud
	 * especifica de filas y columnas, y genera NUMEROCELULAS en posiciones
	 * aleatorias para esa superficie.
	 */
	public Mundo(){
		this.superficie = new Superficie(NUMEROFILAS, NUMEROCOLUMNAS);
		this.generarCelulas();
	}
	/**
	 * Mundo vacio de dimensiones pasadas por parametro usado para cargar de fichero
	 * @param f Numero de filas que tiene la matriz
	 * @param c Numero de columnas que tiene la matriz
	 */
	public Mundo(int f, int c) {
		this.superficie = new Superficie(f, c);
		vaciar();
	}

	/**
	 * Metodo que aleatoriamente coloca las celulas en las casillas
	 */
	public void generarCelulas(){
		int contCelulas = 0;
		while (contCelulas < NUMEROCELULASSIMPLES){
			int f = (int) (Math.random()* getFilas());
			int c = (int) (Math.random()* getColumnas());
			if (this.superficie.casillaVacia(f, c)) {
				this.superficie.llenarCasilla(f, c, new CelulaSimple());
				contCelulas++;
			}
		}
		contCelulas = 0;
		while (contCelulas < NUMEROCELULASCOMPLEJAS){
			int f = (int) (Math.random()* getFilas());
			int c = (int) (Math.random()* getColumnas());
			if (this.superficie.casillaVacia(f, c)) {
				this.superficie.llenarCasilla(f, c, new CelulaCompleja());
				contCelulas++;
			}
		}
	}
	
	/**
	 * Metodo String que llama a la clase Superficie para generar la matriz del juego
	 */
	public StringBuffer toStringBuffer(){
		return superficie.toStringBuffer();
	}
	/*
	public String toString(){
		return superficie.toString();
	}
	*/
	/**
	 * Metodo que evoluciona segun las reglas de la vida.
	 * Si la celula se puede mover a otra casilla aleatoria colindante a ella, entonces deja una nueva celula
	 * Si no puede moverse, tiene un maximo de paso para poder hacerlo, si no muere
	 */
	public void evoluciona(){		
		boolean movido[][] = matriz();
		for(int i = 0; i < this.getFilas(); i++){
    		for(int j = 0; j < this.getColumnas(); j++){
    			if (!superficie.casillaVacia(i, j) && !movido[i][j]){
    				Casilla casilla = this.superficie.ejecutaMovimiento(i, j);
    				movido[casilla.getFila()][casilla.getColumna()] = true;
    			}
    		}
		}
	}
	
	/**
	 * Crea e inicializa una matriz de booleanos con el tama�o de la matriz de celulas, para saber que posicion se ha movido durante el juego
	 * @return La matriz booleana de dimension del tablero, con todas las posiciones inicializadas a falso
	 */
	private boolean[][] matriz(){
		boolean [][] movido = new boolean [this.getFilas()][this.getColumnas()];
		for(int i = 0; i < this.getFilas(); i++){
    		for(int j = 0; j < this.getColumnas(); j++){
    			movido[i][j] = false;
		    }
		}
		return movido;
	}
	
	/**
	 * Se pasan la posicion de la celula a comprobar si se reproduce, moviendo la celula y creando la nueva celula en la 
	 * posicion antigua si lo hace, tambien reinicia el contador de pasosReproduccion de la celula
	 * @param i Entero que representa la fila de la celula a reproducirse
	 * @param j Entero que representa la columna de la celula a reproducirse
	 * @param f Entero que representa la fila de la nueva celula creada por la reproduccion
	 * @param c Entero que representa la columna de la nueva celula creada por la reproduccion
	 * @return TRUE si se ha reproducido la nueva celula, FALSE si no lo ha hecho
	 */
	/*
	private boolean reproducirse(int i, int j, int f, int c){
		boolean hecho = false;
		if (superficie.getReproducir(i,j) < 0){
			System.out.println("Movimiento de (" + i + "," + j + ") a (" + f + "," + c + ")");
			superficie.reiniciarReproducir(i, j);
			superficie.moverCelula (f, c, i, j);
			this.crearCelulaSuperficie(i,j);
			System.out.println("Nace nueva celula en (" + i + "," + j + ")" + " cuyo padre ha sido (" + f + "," + c + ")");
			hecho = true;
		}
		return hecho;
	}
	*/
	

	/**
	 * Metodo que reinicia la matriz del tablero dejando las casillas vacias
	 */
	public void vaciar(){
		superficie.reset();
	}
	
	/**
	 * Crea una celula en la posicion (f,c) de la superficie
	 * @param f Valor entero positivo fila de la matriz
	 * @param c Valor entero positivo columna de la matriz
	 * @param celula 
	 * @return TRUE se ha hecho el proceso de crear la celula
	 */
	public boolean crearCelulaSuperficie(int f, int c, Celula celula){ 
		return superficie.llenarCasilla(f, c, celula);  
	}
	
	/**
	 * Elimina la celula en la posicion (f,c) de la superficie
	 * @param f Valor entero positivo fila de la matriz
	 * @param c Valor entero positivo columna de la matriz
	 * @return TRUE si se ha hecho el proceso de eliminar la celula. False para los dem�s casos
	 */
	public boolean eliminarCelulaSuperficie(int f, int c){
		return superficie.vaciarCasilla(f,c);
	}
	
	/**
	 * Metodo que devuelve el valor entero positivo de las filas de la superficie
	 * @return valor entero positivo de las filas de la Superficie en el Mundo
	 */
	public int getFilas(){
		return this.superficie.getFilas();
	}
	
	/**
	 * Metodo que devuelve el valor entero positivo de las columnas de la superficie
	 * @return valor entero positivo de las columnas de la Superficie en el Mundo
	 */
	public int getColumnas(){
		return this.superficie.getColumnas();
	}
	
	public void esSimulacionTerminada(boolean simulacionTerminada){  
		this.simulacionTerminada = simulacionTerminada;  
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean getSimulacionTerminada(){
		return simulacionTerminada;
	}
	
	/**
	 * Se encarga de guardar las dimensiones del tablero y luego todo el tablero con el estado actual del juego en juego.txt
	 * @throws IOException
	 */
	public void guardar() throws IOException{
		File archivo=new File("juego.txt");
		FileWriter escribir = new FileWriter(archivo);
		String dim = this.getFilas() + " " + this.getColumnas() + System.getProperty("line.separator");
		escribir.append(dim);
		escribir.append(this.toStringBuffer());
		escribir.close();
	}
	
	/**
	 * Abre el fichero juego.txt, carga las dimensiones del tablero del fichero y las celulas que habia en un nuevo mundo
	 * @return El nuevo mundo que hemos cargado del fichero
	 * @throws IOException
	 */
	public Mundo cargar() throws IOException{
		File archivo=new File("juego.txt");
		Scanner entrada = new Scanner(archivo);
		int fila = entrada.nextInt(), columna = entrada.nextInt();
		Mundo mundo = new Mundo(fila, columna);
		for (int i=0; i < fila; i++){
			for (int j=0; j < columna; j++){
				String cadena = entrada.next();
				if (!cadena.equals("-")){
					//Crear nueva celula
					String [] posicion = cadena.split("-");
					int SinMover = Integer.parseInt(posicion[0]);
					int Reproduccion = Integer.parseInt(posicion[1]);
					//MIRAR QUE TIPO DE CELULA CREO
					mundo.crearCelulaSuperficie(i, j, new CelulaSimple (SinMover, Reproduccion));
				}
			}
		}
		entrada.close();
		return mundo;
	}
	
	/**
	 * Metodo que valida que los valores de fila y columna que pasa el usuario son validos
	 * @param f valores enteros positivos de fila
	 * @param c valores enteros positivos de columna
	 * @return TRUE si los valores de fila y columna es valido
	 * FALSE si los valores de fila y columna no son correctos, no estan dentro de los parametros definidos
	 */
	public boolean validarDatos(int f, int c){
		boolean valido = false;
		if(f >= 0 && f < getFilas()){
			if(c >= 0 && c < getColumnas()){
				valido = true;
			}
		}
		return valido;
	}
	
	public void terminaSimulacion() {
		this.simulacionTerminada = true;
	}
	
	
}

