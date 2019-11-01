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

		boolean terminar = false;
		
		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");

		do {
			System.out.print("Introduce el valor de K: ");
			

			// Pedimos por teclado el valor de K y validamos que es un entero mayor que 0.
			if (teclado.hasNextInt()) {
				if ((dimen = teclado.nextInt()) <= 0) {
					System.out.println("K tiene que ser un número mayor que cero");
				} else terminar=true;
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

		/* NOTA:
		 * Bloques A,C,G,I son de dimensiónes dimMenor filas x dimMenor columnas Bloques
		 * B,H son de dimensiones dimMenor filas x dimMayor columnas Bloques D,F son de
		 * dimensiones dimMayor filas x dimMenor columnas Bloque E es de dimensión
		 * dimMayor filas x dimMayor columnas
		 */

		// Creamos matriz para el cuadro mágico
		cuadroMagico = new int[dimen][dimen];

		
		// Constantes que delimitan los bloques
		// primera fila, ultima fila, primera columna, ultima columna de cada bloque
		// fil1_A_B_C: primera fila de los bloques A, B y C
		// col1_A_D_G: primera columna de los bloques A, D, G
		// . . .

		
		final int f1_ABC, c1_ADG;
		f1_ABC = c1_ADG = 0;
		
		final int f2_ABC, c2_ADG;
		f2_ABC = c2_ADG = dimMenor -1;
		
		final int f1_DEF, c1_BEH;
		f1_DEF = c1_BEH = dimMenor;
		
		final int f2_DEF, c2_BEH;
		f2_DEF = c2_BEH = dimMenor + dimMayor -1;
		
		final int f1_GHI, c1_CFI;
		f1_GHI = c1_CFI = dimMenor + dimMayor;
		
		final int f2_GHI, c2_CFI;
		f2_GHI = c2_CFI = (2 * dimMenor) + dimMayor -1;
		
		// Copiamos bloques de cuadro inical al cuadro mágico.
		// Copia bloques A, C, E, G, I en la misma posición en la matriz cuadromagico.
		
		//imprimeMatriz(cuadroInicial);
		
		ponMatriz(extraeMatriz(f1_ABC, c1_ADG, f2_ABC, c2_ADG, cuadroInicial), cuadroMagico, f1_ABC, c1_ADG); //Bloq A
		
		ponMatriz(extraeMatriz(f1_ABC, c1_CFI, f2_ABC, c2_CFI, cuadroInicial), cuadroMagico,f1_ABC, c1_CFI); //Bloq C
		
		ponMatriz(extraeMatriz(f1_DEF, c1_BEH, f2_DEF, c2_BEH, cuadroInicial), cuadroMagico,f1_DEF, c1_BEH); //Bloq E
		
		ponMatriz(extraeMatriz(f1_GHI, c1_ADG, f2_GHI, c2_ADG, cuadroInicial), cuadroMagico,f1_GHI, c1_ADG); //Bloq G
		
		ponMatriz(extraeMatriz(f1_GHI, c1_CFI, f2_GHI, c2_CFI, cuadroInicial), cuadroMagico,f1_GHI, c1_CFI); //Bloq I

				
		// Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial
		// y volteados horizontal y verticalmente.

		// Bloq B volteado y puesto en el H
		ponMatriz(rotaMatrizHV(extraeMatriz(f1_ABC, c1_BEH, f2_ABC, c2_BEH, cuadroInicial)), cuadroMagico,f1_GHI,c1_BEH);
		
		//Bloq H volteado y puesto en el B		
		ponMatriz(rotaMatrizHV(extraeMatriz(f1_GHI, c1_BEH, f2_GHI, c2_BEH, cuadroInicial)), cuadroMagico,f1_ABC,c1_BEH);

		//Bloq D volteado y puesto en el F		
		ponMatriz(rotaMatrizHV(extraeMatriz(f1_DEF, c1_ADG, f2_DEF, c2_ADG, cuadroInicial)), cuadroMagico,f1_DEF,c1_CFI);
		
		//Bloq F volteado y puesto en el D		
		ponMatriz(rotaMatrizHV(extraeMatriz(f1_DEF, c1_CFI, f2_DEF, c2_CFI, cuadroInicial)), cuadroMagico,f1_DEF,c1_ADG);
		

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
	
	//*** Operaciones con matrices
	
	static int[][] rotaMatrizH(int [][]m){
		
		if(m == null) return null;
		
		int nFil = m.length;      
		int nCol = m[0].length;   
		
		int[][] mR = new int[nFil][nCol];
		
		for (int fil = 0; fil < nFil; fil++) {    // para cada fila
			int colDerecha = nCol;	
			for(int col = 0; col < nCol; col++)  { // para cada columna
				mR[fil][colDerecha - 1] = m[fil][col];
				colDerecha--;
			}
		}
		return mR;
	}
		
	static int[][] rotaMatrizV(int [][]m){
		
		int nFil = m.length;
		int nCol = m[0].length;
		
		int[][] mR = new int[nFil][nCol];
				
		for (int col = 0; col < nCol; col++) {    // para cada columna
			int filAbajo = nFil;
			for(int fil = 0; fil < nFil; fil++)  { // para cada fila
				mR[filAbajo - 1][col] = m[fil][col];
				filAbajo--;
			}
		}
		return mR;
	}
	
	
	static int[][] rotaMatrizHV(int[][] m){
		return rotaMatrizH(rotaMatrizV(m));
	}
	
	
	
	static int[][] extraeMatriz(int filDesde, int colDesde, int filHasta, int colHasta, int[][] m){
		if(m == null) return null;
		if (filDesde > filHasta || filHasta > m.length - 1) return null;
		if(colDesde > colHasta || colHasta > m[0].length) return null;
		
		int nFilas = filHasta - filDesde + 1;
		int nCols = colHasta - colDesde + 1;
		
		int[][] mR = new int[nFilas][nCols];
		
		for (int i = filDesde; i <= filHasta; i++) {
			for (int j = colDesde; j <= colHasta; j++) {
				mR[i-filDesde][j-colDesde] = m[i][j] ;
			}
		}
		return mR;
	}
	
	// coloca la matriz m1 dentro de la matriz m2 en las coordenadas indicadas
	// devuelve -1 si no se ha podido colocar la matriz y 1 en caso contrario
	static boolean ponMatriz(int[][] mOrg, int[][] mDest, int fila, int col) {
		if (mOrg == null) return false;
		
		int filsOrg = fila + mOrg.length;
		int colsOrg = col + mOrg[0].length;
		
		// Coordenada fuera de rango
		if (filsOrg > mDest.length || colsOrg > mDest[0].length )
			return false;
		
		for (int i = fila; i < filsOrg; i++) {
			for (int j = col; j < colsOrg; j++) {
				mDest[i][j]= mOrg[i-fila][j-col]; 
			}
		}
		
		return true;
	}
	
	static void imprimeMatriz(int [][] m) {
		for (int[] is : m) {
			for (int n : is) {
				System.out.printf("%6d", n);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n\n");
	}
	
}
