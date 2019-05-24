/* Esta versión utiliza un librería java de libre distribución.
 * La librería se denomina JAMA (A Jama Matrix Package).
 * Para su uso hemos descargado el paquete Jama-1.0.3.jar de su sitio de distribución:
 * 				https://math.nist.gov/javanumerics/jama/
 * y la hemos añadido a nuestro proyecto:
 * 		a) Creando directorio lib debajo del directorio del proyecto.
 * 		b) Copiando la librería a este directorio.
 *  	c) Añadiendo la librería al Build Path:
 *  		En Eclipse click derecho sobre la librería --> Build Path --> Add to Build Path
 * 
 * Más información sobre como añadir una librería a un proyecto Eclipse puedes consular este link de Stack Overflow:
 * 		https://stackoverflow.com/questions/3280353/how-to-import-a-jar-in-eclipse
 * 
 */

package cuadro_magico;

import java.util.Scanner;

import Jama.Matrix;


public class CuandroMagico_v2 {
	static final int DIMEN_BASE = 4;
	static double[][] cuadroInicial;  // array bidimensional con valores del cuadro inicial
	static double[][] cuadroMagico;  // array bidimensional con el cuadro mágico
	
	// Objetos matrix con los que trabajaremos para respresentar los cuadros.
	// Se instanciarán pasando a sus constructores los arrays anteriores.
	static Matrix matrizInicial; // objeto matrix que representa el cuadro inicial
	static Matrix matrizCuadroMagico; // objeto matrix que representa el cuadro mágico final.
	


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
		
		
		// Crea objeto matriz de la librería JAMA con array anterior
		
		matrizInicial = new Matrix(cuadroInicial);
	
		
		// Crea matriz cuadroMagico inicial
		// Esta matriz inicialmente está rellena con el valor que java asigna 
		// por defecto al tipo double (un cero).
		
		matrizCuadroMagico = new Matrix(new double[dimen][dimen]);
		
		
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
		copiaBloque(0, P-1, 0, P-1);
		// Copia del bloque C
		copiaBloque(0, P-1, P+G, P+G-1+P);
		// Copia del bloque G 
		copiaBloque(P+G, P+G+P-1, 0, P-1);
		// Copia del bloque I
		copiaBloque(P+G, P+G+P-1, P+G, P+G+P-1);
		// Copia del bloque E
		copiaBloque(P, P+G-1 ,P , P+G-1);

		
		// Copia las sub matrices transpuestas... 
		
		// Transpone y copia bloque B al H
		copiaTranspuesta(0, P-1,P, P+G-1, P+G, P+G+P-1, P, P+G-1);
			
		// Transpone y copia bloque H al B
		copiaTranspuesta(P+G, P+G+P-1, P, P+G-1, 0, P-1, P, P+G-1);
				
		// Transpone y copia bloque D al F
		copiaTranspuesta(P, P+G-1, 0, P-1, P, P+G-1, P+G, P+G+P-1);
				
		// Transpone y copia bloque F al D
		copiaTranspuesta(P, P+G-1, P+G, P+G+P-1, P, P+G-1, 0, P-1);
		
		
		// Imprime matrizCuadroMagico por consola
		matrizCuadroMagico.print(6, 0);
		
	
	}
	
	/**
	 * Copia el bloque de dimesiones especificada según los parámetros de entrada, 
	 * desde el cuadro inicial al cuadro mágico. Los copia en la misma posición y
	 * orden que están en el cuadro inicial
	 * 
	 * @param fIni	posición primera fila del bloque
	 * @param fFin	posición última fila del bloque
	 * @param cIni	posición primera columna del bloque
	 * @param cFin	posición última columna del bloque
	 */
	private static void copiaBloque(int fIni, int fFin, int cIni,int cFin) {
		matrizCuadroMagico.setMatrix(fIni,fFin,cIni,cFin,matrizInicial.getMatrix(fIni,fFin,cIni, cFin));
	}
	
	/**
	 * @param 	m matriz de entrada
	 * @return	la matriz m con las filas transpuestas
	 */
	private static Matrix transponerFilas(Matrix m) {
		int filas = m.getRowDimension();
		int columnas = m.getColumnDimension();
		int[] f = new int[filas];
		
		// Rellena array f con los índices de las filas de la matriz m
		// en orden inverso. getMatrix creará otra matriz copiando las filas
		// de m en el orden indicado en el array f.
		
		for (int i = filas-1, j=0; i>=0; i--, j++)
			f[j]=i;
		
		return m.getMatrix(f,0,columnas-1);
	}
	
	
	/**
	 * @param 	m matriz de entrada
	 * @return	la matriz m con las columnas transpuestas
	 */
	private static Matrix transponerColumnas(Matrix m) {
		int filas = m.getRowDimension();
		int columnas = m.getColumnDimension();
		int[] c = new int[columnas];
		
		// Rellena array c con los índices de las columnas de la matriz m
		// en orden inverso. getMatrix creará otra matriz copiando las columnas
		// de m en el orden indicado en el array c.

		for (int i = columnas-1,  j=0 ; i>=0; i--, j++)
			c[j]=i;
		
		return m.getMatrix(0,filas-1,c);
	}
	
	
	/**
	 * @param m 	matriz
	 * @return		m con las filas y las columnas transpuestas
	 */
	private static Matrix TransponerFilCol(Matrix m) {
		
		return transponerColumnas(transponerFilas(m));
	
	}
	
	/**
	 * Copia el bloque ubicado en el cuadro inicial,  con ubicación y dimensiones 
	 * especificados por los primeros cuatro parametros del método.
	 * La copia la realiza sobre el cuadro mágico en la ubicación especificada por los parámetros quinto
	 * al octavo del método. Previa a la copia del bloque este es volteado en e plano horizontal y vertical.
	 * 
	 * @param f1		primera fila de la submatriz origen
	 * @param f2		última fila de la submatriz origen
	 * @param col1		primera columna de la submatriz origen
	 * @param col2		útima columna de la submatriz origen
	 * @param fd1	    primera fila de la submatriz destino
	 * @param fd2       última fila de la submatriz destino
	 * @param cold1     primera columna de la submatriz destino
	 * @param cold2     última columna de la submatriz destino

	 */
	
	private static void copiaTranspuesta(int f1,int f2, int col1, int col2, int fd1, int fd2,int cold1,int cold2) {
		
		matrizCuadroMagico.setMatrix(fd1,fd2,cold1,cold2,TransponerFilCol(matrizInicial.getMatrix(f1,f2,col1,col2)));
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
