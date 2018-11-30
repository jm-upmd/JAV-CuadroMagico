/* Esta versión utiliza la clase MatrixExtend que está implementada 
 * dentro del paquete cuadro_magico_herencia.
 * MatrixExtend hereda de Matrix y añade tres metodos státicos: 
 * 
 * 		static public Matrix getMatrixTransposeRows(Matrix m)
 * 		static public Matrix getMatrixTransposeColums(Matrix m)
 * 		static public Matrix getMatrixTransposeRowCol(Matrix m)
 * 
 * que devuelven las matrices pasadas como parámetro con sus filas, columnas, 
 * o ambas transpuestas respectivamente.
 * 
 */

package cuadro_magico;

import java.util.Scanner;

import Jama.Matrix;
import cuadro_magico_herencia.MatrixExtend;

public class CuadroMagicoV4 {
	static final int DIMEN_BASE = 4;
	static double[][] cuadroInicial;  // array bidimensional con valores del cuadro inicial
	static double[][] cuadroMagico;  // array bidimensional con el cuadro mágico


	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int dimen = 0;

		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");
		System.out.println("Introduce el valor de K: ");
		
				
		// Pedimos por teclado el valor de K
		if (teclado.hasNextInt()) {
			dimen = teclado.nextInt();
			teclado.close();
			if (dimen <= 0) {
				System.out.println("El dato introducido no es un numero entero positivo mayor que cero");
				System.exit(0);
			}
		}

		dimen *= DIMEN_BASE; // dimensión del cuadro. cuadro es de dimen * dimen elementos.


		// Genera cuadro inicial con valores consecutivos 
		generaArrayInicial(dimen);
		
		
		// Crea objeto matriz de tipo MatrixExtend

		
		MatrixExtend matrizInicial = new MatrixExtend(cuadroInicial);
	
		
		// Crea matriz cuadroMagico inicial
		// Esta matriz inicialmente está rellena con el valor que java asigna 
		// por defecto al tipo double (un cero).
		
		MatrixExtend matrizCuadroMagico = new MatrixExtend(new double[dimen][dimen]);
		
		
		final int P = dimen / 4 ;  		// P es la dimensión más Pequeña de los sub-bloques
		final int G = dimen / 2 ;       // G es la dimensión más Grande de los sub-bloques
		
		
		/* 
		 * Bloques A,C,G,I son de dimensiónes      P filas x P columnas
		 * Bloques B,H     son de dimensiones      P filas x G columnas 
		 * Bloques D,F     son de dimensiones      G filas x P columnas
		 * Bloque  E       es de dimensión 		   G files x G columnas
		 */
		
		// Métodos setMatrix y getMatrix reciben parámetros (fil1,fil2,col1,col2)
		// Primera fila y columna comienzan en posición 0
		
		// Copia del bloque A desde matrizInicial hasta matrizCuadroMagico
		matrizCuadroMagico.setMatrix(0,P-1,0,P-1,matrizInicial.getMatrix(0,P-1,0,P-1));
		// Copia del bloque C
		matrizCuadroMagico.setMatrix(0,P-1,P+G,P+G-1+P,matrizInicial.getMatrix(0,P-1,P+G,P+G-1+P));
		// Copia del bloque G 
		matrizCuadroMagico.setMatrix(P+G,P+G+P-1,0,P-1,matrizInicial.getMatrix(P+G,P+G+P-1,0,P-1));
		// Copia del bloque I
		matrizCuadroMagico.setMatrix(P+G,P+G+P-1,P+G,P+G+P-1,matrizInicial.getMatrix(P+G,P+G+P-1,P+G,P+G+P-1));
		// Copia del bloque E
		matrizCuadroMagico.setMatrix(P,P+G-1,P,P+G-1,matrizInicial.getMatrix(P,P+G-1,P,P+G-1));

		
		// Copia las sub matrices transpuestas... 
		
		// Transpone y copia bloque B al H
		
		
		Matrix m = matrizInicial.getMatrixTransposeRowsColumns(0,P-1,P,P+G-1);
		matrizCuadroMagico.setMatrix(P+G,P+G+P-1,P,P+G-1,m);
			
		// Transpone y copia bloque H al B
		m = matrizInicial.getMatrixTransposeRowsColumns(P+G, P+G+P-1,P, P+G-1);
		matrizCuadroMagico.setMatrix(0,P-1,P,P+G-1, m);

				
		// Transpone y copia bloque D al F
		m = matrizInicial.getMatrixTransposeRowsColumns(P,P+G-1,0,P-1);
		matrizCuadroMagico.setMatrix(P,P+G-1,P+G,P+G+P-1, m);

				
		// Transpone y copia bloque F al D
		m = matrizInicial.getMatrixTransposeRowsColumns(P,P+G-1,P+G,P+G+P-1);
		matrizCuadroMagico.setMatrix(P,P+G-1,0,P-1, m);
		
			
		Matrix matrizTranspuesta = MatrixExtend.getMatrixTransposeRowCol(m);
		
		
		
		
		// Imprime matrizCuadroMagico por consola
		matrizCuadroMagico.printMagic(5, 0);
	}
	
	
	
	// Genera array de dimensión nxn y la rellena por filas 
		// con números cosecutivos de 0 a (n*n)-1
		private static void generaArrayInicial(int n) {
			cuadroInicial = new double[n][n];
			int contador = 1;
			for (int fil = 1; fil <= n; fil++)
				for (int col = 1; col <= n; col++)
					cuadroInicial[fil - 1][col - 1] = contador++;
		}
		
	
}
