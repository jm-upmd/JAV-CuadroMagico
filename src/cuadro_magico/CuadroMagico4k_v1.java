/**
 * Programa que genera un Cuadro Mágico de dimensiones NxN, donde N=4k
 * Utiliza el método de los 9 bloques.
 * Un Cuadro Mágico es aquel que cumple que la suma de los elementos de cada una 
 * de sus filas es identica y coincide con la suma de los elementos de cada una 
 * de sus columnas.
 * 
 * Ejemplo (para K=1):
 * 
 *  	 1    15    14     4
    	12     6     7     9
     	 8    10    11     5
    	13     3     2    16
    	
   Cada fila y cada columna suma 34
   
   Mas info en:
   http://www.ehu.eus/~mtpalezp/descargas/magiacuadrada.pdf
   (Página 15. Apartado: "Cuadrados de orden n = 4k)
   
 */
package cuadro_magico;

import java.util.Scanner;

public class CuadroMagico4k_v1 {

	// La dimensión de cuadro ha de ser N = 4K
	static final int DIMEN_BASE = 4;
	
	static int[][] cuadroInicial; // array bidimensional con valores del cuadro inicial
	static int[][] cuadroMagico; // array bidimensional con el cuadro mágico
	
	static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int dimen = 0;

		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");
		System.out.println("Introduce el valor de K: ");

		// Pedimos por teclado el valor de K y validamos que es un entero mayor que 0.
		if (teclado.hasNextInt() ) {
			if ( (dimen = teclado.nextInt()) <= 0   ) {
				terminar("K tiene que ser mayor o igual que 1");
			}
		} else {
			terminar("El dato introducido no es un numero entero");

		}

		dimen *= DIMEN_BASE; // dimensión del cuadro. cuadro es de dimen * dimen elementos.

		// Genera cuadro inicial con valores consecutivos
		generaCuadroInicial(dimen);
		// muestraCuadro(cuadroInicial);

		// Constantes que contienen la dimensión mayor y la menor de los bloques en que se divide el cuadro
		

		final int dimMenor = dimen / 4;
		final int dimMayor = dimen / 2;
		
		// Columna  donde comienza bloques más a la derecha  (C,F,G) o fila
		// donde comienzan bloques más abajo (G, H, I)
		
		final int posBloqDerAbaj = dimMayor + dimMenor + 1; 

		/*
		 * Bloques A,C,G,I son de dimensiónes dimMenor filas x dimMenor columnas Bloques B,H son de
		 * dimensiones dimMenor filas x dimMayor columnas Bloques D,F son de dimensiones dimMayor filas x
		 * dimMenor columnas Bloque E es de dimensión dimMayor files x dimMayor columnas
		 */

		// Creamos matriz para el cuadro mágico
		cuadroMagico = new int[dimen][dimen];

		
		// Copiamos bloques de cuadro inical al cuadro mágico.
		
		// Copia bloques A, C, E, G, I en la misma posición en la matriz cuadromagico.
		
		//copiaBloque(columna, fila, numFilas, numColumnas)
	

		copiaBloque(1, 1, dimMenor, dimMenor);   // Bloque A. Copia bloque que comieza columna 1, fila 1, y tiene dimMenor filas y dimMenor columnas
		copiaBloque(posBloqDerAbaj, 1, dimMenor, dimMenor); // Bloque C. 
		copiaBloque(dimMenor + 1, dimMenor + 1, dimMayor, dimMayor);  //Bloque E
		copiaBloque(1, posBloqDerAbaj, dimMenor, dimMenor); // Bloque G
		copiaBloque(posBloqDerAbaj, posBloqDerAbaj, dimMenor, dimMenor); // Bloque I

		// Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial
		//copiaBloqueSimetrico(colOrigen, filaOrigen, numFilas, numCol,	colDestino, filaDestino)

		// Bloque B a bloque H 
		copiaBloqueSimetrico(dimMenor + 1, 1, dimMenor, dimMayor, dimMenor + 1, posBloqDerAbaj); 
		// Bloque H a bloque B
		copiaBloqueSimetrico(dimMenor + 1, posBloqDerAbaj, dimMenor, dimMayor, dimMenor + 1, 1);
		// Bloque D a bloque F
		copiaBloqueSimetrico(1, dimMenor + 1, dimMayor, dimMenor, posBloqDerAbaj, dimMenor + 1);
		// Bloque F a bloque D
		copiaBloqueSimetrico(posBloqDerAbaj, dimMenor + 1, dimMayor, dimMenor, 1, dimMenor + 1);

		muestraCuadro(cuadroMagico);

	}

	private static void generaCuadroInicial(int n) {
		cuadroInicial = new int[n][n];
		int contador = 1;

		for (int fil = 0; fil < n; fil++) // Para cada fila
			for (int col = 0; col < n; col++) // Para cada columna de la fila
				cuadroInicial[fil][col] = contador++;

	}

	// Otra forma de rellenar el cuadro con valores consecutivos
	private static void generaCuadroInicialV2(int n) {
		cuadroInicial = new int[n][n];
		for (int fil = 1; fil <= n; fil++)
			for (int col = 1; col <= n; col++)
				cuadroInicial[fil - 1][col - 1] = col + (n * (fil - 1));

	}

	private static void muestraCuadro(int[][] cuadro) {
		int n = cuadro[0].length;

		for (int fil = 0; fil < n; fil++) {

			System.out.println();
			for (int col = 0; col < n; col++) {
				System.out.printf("%6d", cuadro[fil][col]);
			}
		}
		System.out.println();

	}

	// Copia los elementos del bloque de cuadroOrigen a cuadroDestino
	private static void copiaBloque(int columna, int fila, int numFilas, int numColumnas) {

		for (int i = fila; i <= fila + numFilas - 1; i++)
			for (int j = columna; j <= columna + numColumnas - 1; j++)
				cuadroMagico[i - 1][j - 1] = cuadroInicial[i - 1][j - 1];

	}

	// Copia los elementos en orden simétrico del bloque de cuadroOrigen a
	// cuadroDestino
	// 						a b 
	//						e f 
	// copia simetrica -->  
	// 						f e
	//                      b a

	
	private static void copiaBloqueSimetrico(int colOrigen, int filaOrigen, int numFilas, int numCol,
			int colDestino, int filaDestino) {
		
		int yDest = filaDestino - 1;
		int xDest = colDestino - 1;
		int yOrg = filaOrigen + numFilas - 2;
		int xOrg = colOrigen + numCol - 2;
		
		for (int i = yOrg; i >= filaOrigen - 1; i--) {
			
			for (int j = xOrg; j >= colOrigen - 1; j--) {
				cuadroMagico[yDest][xDest] = cuadroInicial[i][j];
				xDest++;
			}
			
			yDest++;
			xDest = colDestino - 1;

		}

	}
	
	private static void terminar(String mensaje) {
		System.out.println(mensaje);
		System.out.println("Programa finalizado");
		teclado.close();
		System.exit(0);
		
	}

}
