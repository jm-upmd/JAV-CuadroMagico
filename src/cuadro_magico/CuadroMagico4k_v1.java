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

/**
 * @author Jose
 *
 */
public class CuadroMagico4k_v1 {

	// La dimensión de cuadro ha de ser N = 4K
	static final int DIMEN_BASE = 4;

	static int[][] cuadroInicial; // array bidimensional con valores del cuadro inicial
	static int[][] cuadroMagico; // array bidimensional con el cuadro mágico

	static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int dimen = 0;

		boolean terminar = false;

		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");

		do {
			System.out.print("Introduce el valor de K: ");

			// Pedimos por teclado el valor de K y validamos que es un entero mayor que 0.
			if (teclado.hasNextInt()) {
				if ((dimen = teclado.nextInt()) <= 0) {
					System.out.println("K tiene que ser un número mayor que cero");
				} else
					terminar = true;
			} else {
				System.out.println("k tiene que ser un número entero");

				teclado.next(); // LEE/SACA EL VALOR INCORRECTO DEL BUFFER

			}

		} while (!terminar);

		dimen *= DIMEN_BASE; // dimensión del cuadro. cuadro es de dimen * dimen elementos.

		// Genera cuadro inicial con valores consecutivos
		generaCuadroInicial(dimen);

		// Constantes que contienen la dimensión mayor y la menor de cada uno de los
		// bloques en que se divide el cuadro

		final int dimMenor = dimen / 4;
		final int dimMayor = dimen / 2;

		/*
		 * NOTA: 
		 * Bloques A,C,G,I son de dimensiónes dimMenor filas x dimMenor columnas
		 * Bloques B,H son de dimensiones dimMayor filas x dimMenor columnas 
		 * Bloques D,F son de dimensiones dimMayor filas x dimMenor columnas 
		 * Bloque  E es de dimensión dimMayor filas x dimMayor columnas
		 */

		// Creamos matriz para el cuadro mágico
		cuadroMagico = new int[dimen][dimen];

		// Constantes que delimitan los bloques.
		// Tener dibujo a la vista para entender.
		// Momenclatura usada en nombre variables para simplificar:
		// f1_ABC: primera fila de los bloques A, B y C
		// f2_ABC: última fila de los bloques A, B y C
		// c1_ADG: primera columna de los bloques A, D, G
		// . . .

		final int f1_ABC, c1_ADG;
		f1_ABC = c1_ADG = 0;

		final int f2_ABC, c2_ADG;
		f2_ABC = c2_ADG = dimMenor - 1;

		final int f1_DEF, c1_BEH;
		f1_DEF = c1_BEH = dimMenor;

		final int f2_DEF, c2_BEH;
		f2_DEF = c2_BEH = dimMenor + dimMayor - 1;

		final int f1_GHI, c1_CFI;
		f1_GHI = c1_CFI = dimMenor + dimMayor;

		final int f2_GHI, c2_CFI;
		f2_GHI = c2_CFI = (2 * dimMenor) + dimMayor - 1;

		// Copiamos bloques de cuadro inical al cuadro mágico.
		// Copia bloques A, C, E, G, I en la misma posición en la matriz cuadromagico.

		// imprimeMatriz(cuadroInicial);

		ponMatriz(extraeMatriz(f1_ABC, c1_ADG, f2_ABC, c2_ADG, cuadroInicial), cuadroMagico, f1_ABC, c1_ADG); // Bloq A

		ponMatriz(extraeMatriz(f1_ABC, c1_CFI, f2_ABC, c2_CFI, cuadroInicial), cuadroMagico, f1_ABC, c1_CFI); // Bloq C

		ponMatriz(extraeMatriz(f1_DEF, c1_BEH, f2_DEF, c2_BEH, cuadroInicial), cuadroMagico, f1_DEF, c1_BEH); // Bloq E

		ponMatriz(extraeMatriz(f1_GHI, c1_ADG, f2_GHI, c2_ADG, cuadroInicial), cuadroMagico, f1_GHI, c1_ADG); // Bloq G

		ponMatriz(extraeMatriz(f1_GHI, c1_CFI, f2_GHI, c2_CFI, cuadroInicial), cuadroMagico, f1_GHI, c1_CFI); // Bloq I

		// Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial
		// y volteados horizontal y verticalmente.

		// Bloq B volteado y puesto en el H
		ponMatriz(volteaMatriz(extraeMatriz(f1_ABC, c1_BEH, f2_ABC, c2_BEH, cuadroInicial)), cuadroMagico, f1_GHI,
				c1_BEH);

		// Bloq H volteado y puesto en el B
		ponMatriz(volteaMatriz(extraeMatriz(f1_GHI, c1_BEH, f2_GHI, c2_BEH, cuadroInicial)), cuadroMagico, f1_ABC,
				c1_BEH);

		// Bloq D volteado y puesto en el F
		ponMatriz(volteaMatriz(extraeMatriz(f1_DEF, c1_ADG, f2_DEF, c2_ADG, cuadroInicial)), cuadroMagico, f1_DEF,
				c1_CFI);

		// Bloq F volteado y puesto en el D
		ponMatriz(volteaMatriz(extraeMatriz(f1_DEF, c1_CFI, f2_DEF, c2_CFI, cuadroInicial)), cuadroMagico, f1_DEF,
				c1_ADG);

		// Muestra el cuadro mágico por consola
		imprimeMatriz(cuadroMagico);

	} // fin main()

	private static void generaCuadroInicial(int n) {
		cuadroInicial = new int[n][n];
		int contador = 1;

		for (int fil = 0; fil < n; fil++) // Para cada fila
			for (int col = 0; col < n; col++) // Para cada columna de la fila
				cuadroInicial[fil][col] = contador++;

	}
	
	

	/**
	 * Voltea una matriz en el plano vertical y horizontal
	 * @param a matriz origen
	 * @return  un nueva matriz que es la matriz a volteada.
	 */
	static int[][] volteaMatriz(int[][] a) {

		int nFil, nCol;
		nFil = a.length;      // Número de filas de a
		nCol = a[0].length;   // Número de columnas de a

		// Matriz destino en la que se copiaran los valores de a en las posiciones
		// correspondientes para que queden volteados.
		int[][] b = new int[nFil][nCol]; 

		// Contadores que van recorriendo la matriz b para colocar cada valor.
		// Se inicializan para que apunten a la última fila y columna de la matriz
		// Se irán moviendo hacia atrás en el bucle que copia los valores.
		int fb = nFil - 1;
		int cb = nCol - 1;

		// Bucle que va recorriendo la matriz origen a de izquierda a derecha
		// y de arriba a abajo.
		for (int fa = 0; fa < nFil; fa++) {
			for (int ca = 0; ca < nCol; ca++) {
				b[fb][cb] = a[fa][ca];
				cb--; // contador columna de b se mueve a la col de la izquierda
			}
			cb = nCol - 1; // contador columna de b se pone en última columna
			fb--; // contador fila de b sube a la fila anterior
		}

		return b; // devuelve la matriz con valores volteados respecta a matriz de entrada a.
	}

	/**
	 * Extrae una submatriz de la matriz m
	 * @param filDesde Número de 1ª fila de la submatriz
	 * @param colDesde Número de la 1ª columna de la submatriz
	 * @param filHasta Número de la  última fila de la submatriz
	 * @param colHasta Número de la última columna de la submatriz
	 * @param m Matriz de la cual extraer la submatriz.
	 * @return Array bidimensional conteniendo la submatriz extraida de m
	 */
	static int[][] extraeMatriz(int filDesde, int colDesde, int filHasta, int colHasta, int[][] m) {
		
		// Verificar que la submatriz a extraer está contenida dentro de m, y que la matriz origen  no es null
		if (m == null)
			return null;
		if (filDesde > filHasta || filHasta > m.length - 1)
			return null;
		if (colDesde > colHasta || colHasta > m[0].length)
			return null;

		int nFilas = filHasta - filDesde + 1;
		int nCols = colHasta - colDesde + 1;

		// Crea array para guardar la submatriz
		int[][] mR = new int[nFilas][nCols];

		// Copia los elementos ubicados en las coordenadas de la matriz m a la submatriz 
		for (int i = filDesde; i <= filHasta; i++) {
			for (int j = colDesde; j <= colHasta; j++) {
				mR[i - filDesde][j - colDesde] = m[i][j];
			}
		}
		return mR;
	}


	/**
	 * Coloca la matriz mOrg dentro de la matriz mDest en las coordenadas indicadas
	 * Devuelve false si no se ha podido colocar la matriz y true en caso contrario
	 * @param mOrg Matriz a colocar
	 * @param mDest Matriz en la que colocar mOrg
	 * @param fila Fila a partir de la cual colocar en mDest
	 * @param col Columna a partir de la cual colocar en mDest
	 * @return true si se ha podido colocar mOrg en mDest; false en caso contrario.
	 */
	static boolean ponMatriz(int[][] mOrg, int[][] mDest, int fila, int col) {
		if (mOrg == null)
			return false;

		int filsOrg = fila + mOrg.length;
		int colsOrg = col + mOrg[0].length;

		// Si coordenadas fuera de rango termina y devuelve false
		if (filsOrg > mDest.length || colsOrg > mDest[0].length)
			return false;

		// Coloca mOrg en mDest
		for (int i = fila; i < filsOrg; i++) {
			for (int j = col; j < colsOrg; j++) {
				mDest[i][j] = mOrg[i - fila][j - col];
			}
		}

		return true;
	}

	static void imprimeMatriz(int[][] m) {

		// Ancho del mayor elemento de la matriz + 2
		int ancho = longNumero(mayor(m)) + 2;

		String cadFormat = "%" + String.valueOf(ancho) + "d";
		for (int[] is : m) {
			for (int n : is) {
				System.out.printf(cadFormat, n);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n\n");
	}

	
	/**
	 * Devuelve el número de dígitos de un numero entero mayor o igual que cero.
	 * @param n Numero entero cero o positivo
	 * @return Numero de digitos del número; -1 Si el número no es cero o mayor
	 */
	static int longNumero(int n) {

		return n >= 0 ? String.valueOf(n).length() : -1;

	}

	/**
	 * Devuelve en número mayor contenido en la matriz m
	 * @param m Matriz 
	 * @return Número m
	 */
	static int mayor(int[][] m) {
		int mayor = 0;
		for (int[] fil : m)
			for (int n : fil)
				mayor = n > mayor ? n : mayor;

		return mayor;

	}

}
