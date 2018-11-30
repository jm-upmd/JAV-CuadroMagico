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

public class CuadroMagico4k_v3 {

	// La dimensión de cuadro ha de ser N = 4K
	static final int DIMEN_BASE = 4;
	static int Dimen; // Representa el K
	static int[][] cuadroInicial;
	static int[][] cuadroMagico;

	
	// Clase interna que representa los inices x,y donde esta el primer elemento de los 
	// bloques A,B,C,D,E,F,G,H,I en que se divide el cuadro original para su transformación
	
	static class Bloque {
		int x;
		int y;
		int nFil;
		int nCol;

		Bloque(int comienzoX, int comienzoY, int numFilas, int numColumnas) {
			x = comienzoX;
			y = comienzoY;
			nFil = numFilas;
			nCol = numColumnas;
		}

	}

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		int dimen = 0;

		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");
		System.out.println("Introduce el valor de K: ");

		if (teclado.hasNextInt()) {
			dimen = teclado.nextInt();
			teclado.close();
			if (dimen <= 0) {
				System.out.println("El dato introducido no es un numero entero positivo mayor que cero");
				System.exit(0);
			}
		}
		
		dimen*=DIMEN_BASE;

		

		//CuadroMagico4k cuadroMagico4k = new CuadroMagico4k();

		generaCuadroInicial(dimen);

		// Genera información de coordenadas x,y dentro del cuadro y dimensión de cada
		// uno de los nueve bloques en que se divide el cuadro mágico.
		
		int d4 = dimen / 4;
		int d2 = dimen / 2;
		int d42 = d2 + d4 + 1;
		
		Bloque bloqueA = new Bloque(1, 1, d4, d4);
		Bloque bloqueB = new Bloque(d4 + 1, 1, d4, d2);
		Bloque bloqueC = new Bloque(d42, 1, d4, d4);
		Bloque bloqueD = new Bloque(1, d4 + 1, d2, d4);
		Bloque bloqueE = new Bloque(d4 + 1, d4 + 1, d2, d2);
		Bloque bloqueF = new Bloque(d42, d4 + 1, d2, d4);
		Bloque bloqueG = new Bloque(1, d42, d4, d4);
		Bloque bloqueH = new Bloque(d4 + 1, d42, d4, d2);
		Bloque bloqueI = new Bloque(d42, d42, d4, d4);

		//muestraCuadro(cuadroInicial);
		
		// Crea cuadro donde se irán copiando bloques del cuadro cuadroInicial

		cuadroMagico = new int[dimen][dimen];

		// Copia a cuadroMagico los bloques A,C,E,G,I tal cual están en el cuadroInicial
		copiaBloque(cuadroInicial, cuadroMagico, bloqueA);
		copiaBloque(cuadroInicial, cuadroMagico, bloqueC);
		copiaBloque(cuadroInicial, cuadroMagico, bloqueE);
		copiaBloque(cuadroInicial, cuadroMagico, bloqueG);
		copiaBloque(cuadroInicial, cuadroMagico, bloqueI);

		// Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial
		copiaBloqueSimetrico(cuadroInicial, cuadroMagico, bloqueB, bloqueH);
		copiaBloqueSimetrico(cuadroInicial, cuadroMagico, bloqueH, bloqueB);
		copiaBloqueSimetrico(cuadroInicial, cuadroMagico, bloqueD, bloqueF);
		copiaBloqueSimetrico(cuadroInicial, cuadroMagico, bloqueF, bloqueD);

		muestraCuadro(cuadroMagico);
	}


	private static void generaCuadroInicial(int n) {
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
	private static void copiaBloque(int[][] cuadroOrigen, int[][] cuadrodestino, Bloque bloque) {

		for (int i = bloque.y; i <= bloque.y + bloque.nFil - 1; i++)
			for (int j = bloque.x; j <= bloque.x + bloque.nCol - 1; j++)
				cuadrodestino[i - 1][j - 1] = cuadroOrigen[i - 1][j - 1];

	}

	// Copia los elementos en orden simétrico del bloque de cuadroOrigen a cuadroDestino
	// a	b                			 d	 c
	//          copia simetrica -->
	// c    d                			 b 	 a
	private static void copiaBloqueSimetrico(int[][] cuadroOrigen, int[][] cuadrodestino, Bloque bloqDesde,
			Bloque bloqHasta) {
		int yDest = bloqHasta.y - 1;
		int xDest = bloqHasta.x - 1;
		int yOrg = bloqDesde.y + bloqDesde.nFil - 2;
		int xOrg = bloqDesde.x + bloqDesde.nCol - 2;
		for (int i = yOrg; i >= bloqDesde.y - 1; i--) {
			for (int j = xOrg; j >= bloqDesde.x - 1; j--)
				cuadrodestino[yDest][xDest++] = cuadroOrigen[i][j];
			yDest++;
			xDest = bloqHasta.x - 1;

		}

	}
	
	@SuppressWarnings("unused")
	private static void muestraSubCuadro(int[][] cuadro, Bloque bloque) {

		for (int i = bloque.y; i <= bloque.y + bloque.nFil - 1; i++) {
			System.out.println();

			for (int j = bloque.x; j <= bloque.x + bloque.nCol - 1; j++)
				System.out.printf("%5d", cuadro[i - 1][j - 1]);
		}
		System.out.println();

	}

}
