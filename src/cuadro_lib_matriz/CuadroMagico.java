/* Versión que utiliza la librería matrizJMD.jar 
 * implementada como ejemplo para el curso.
 * El código fuente de esta librería esta también disponible
 * en github
 */

package cuadro_lib_matriz;

import java.util.Scanner;

import org.aytoalcobendas.jmdominguez.matriz.FueraDeRangoException;
import org.aytoalcobendas.jmdominguez.matriz.Matriz;


public class CuadroMagico {

	// La dimensión de cuadro ha de ser N = 4K
	static final int DIMEN_BASE = 4;

	static Matriz cuadroOrigen, cuadroMagico;

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

		// Rellenamos una array bidi con los valores inciales de la martriz origen

		int[][] arrayIni = generaArray(dimen);

		// Crea matriz origen con los numeros iniciales

		cuadroOrigen = new Matriz(arrayIni);
		
		// Crea matriz destino vacia

		cuadroMagico = new Matriz(dimen);

		// Constantes que contienen la dimensión mayor y la menor de cada uno de los
		// bloques en que se divide el cuadro

		final int dimMenor = dimen / 4;
		final int dimMayor = dimen / 2;

		/*
		 * NOTA: Bloques A,C,G,I son de dimensiónes dimMenor filas x dimMenor columnas
		 * Bloques B,H son de dimensiones dimMenor filas x dimMayor columnas Bloques D,F
		 * son de dimensiones dimMayor filas x dimMenor columnas Bloque E es de
		 * dimensión dimMayor filas x dimMayor columnas
		 */

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

		try {
			cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_ABC, c1_ADG, f2_ABC, c2_ADG), f1_ABC, c1_ADG); //Bloq A
			cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_ABC, c1_CFI, f2_ABC, c2_CFI), f1_ABC, c1_CFI); //Bloq C               
			cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_DEF, c1_BEH, f2_DEF, c2_BEH), f1_DEF, c1_BEH); //Bloq E   
			cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_GHI, c1_ADG, f2_GHI, c2_ADG), f1_GHI, c1_ADG); //Bloq G            
			cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_GHI, c1_CFI, f2_GHI, c2_CFI), f1_GHI, c1_CFI); //Bloq I  
		
                                                                                                                       
	        // Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial                      
	        // y volteados horizontal y verticalmente.                                           
	        
	        // Bloq B volteado y puesto en el H
	        cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_ABC, c1_BEH, f2_ABC, c2_BEH).rotaMatrizHV(), f1_GHI, c1_BEH);
	            
	        // Bloq H volteado y puesto en el B
	        cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_GHI, c1_BEH, f2_GHI, c2_BEH).rotaMatrizHV(), f1_ABC, c1_BEH);
	            
	        // Bloq D volteado y puesto en el F
	        cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_DEF, c1_ADG, f2_DEF, c2_ADG).rotaMatrizHV(), f1_DEF, c1_CFI);
	            
	        // Bloq F volteado y puesto en el D
	        cuadroMagico.ponMatriz(cuadroOrigen.extraeMatriz(f1_DEF, c1_CFI, f2_DEF, c2_CFI).rotaMatrizHV(), f1_DEF, c1_ADG);
	        
	        // Muestra cuadro mágico por consola
	        cuadroMagico.imprimeMatriz();
		
		} catch (FueraDeRangoException e) {
			System.out.println(e.getMessage());
			
		}

	}

	static int[][] generaArray(int dimen) {
		int[][] a = new int[dimen][dimen];
		int n = 1;

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				a[i][j] = n++;
			}
		}

		return a;

	}

}
