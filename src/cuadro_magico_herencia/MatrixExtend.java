/* Clase que hereda de Matrix y le añade funcionalidad para
 * transponer matrices.
 */

package cuadro_magico_herencia;

import Jama.Matrix;

/**
 * @author Profesor
 *
 */
public class MatrixExtend extends Matrix {

	//private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public MatrixExtend(double[][] arr) {
		super(arr);
		
	}
	
	public  Matrix getMatrixTransposeRows(int f1,int f2, int c1,int c2) {

		
		int rows = f2 - f1 + 1;
		
		int[] rowsOrder = new int[rows];
		
		for(int i = rows+f1-1, j = 0; i >= f1; i--, j++)
			rowsOrder[j] = i;
		
		return  getMatrix(rowsOrder, c1, c2);
	}
	
	public Matrix getMatrixTransposeColumns(int f1, int f2, int c1, int c2) {
		int cols = c2 - c1 +1 ;
		int[] columsOrder = new int[cols];
		
		for (int i = cols+c1 -1,  j=0 ; i>=c1; i--, j++)
			columsOrder[j]=i;
		
		
		return  this.getMatrix(f1,f2,columsOrder);
		
		
	}
	
	public Matrix getMatrixTransposeRowsColumns(int f1, int f2, int c1, int c2) {
		return  getMatrixTransposeColumns(getMatrixTransposeRows(f1, f2, c1, c2));
	}
	
	
	
	/**
	 * @param m source Matrix
	 * @return new mantrix equal to m with its rows transposed
	 */
	static public Matrix getMatrixTransposeRows(Matrix m) {
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
	 * Imprime el cuadro mágico por consola dejando un linea en blaco
	 * entre cada par de filas
	 * 
	 * @param arg0 width of number field 
	 * @param arg1	number of decimal digits
	 */
	public void printMagic(int arg0, int arg1) {
		int[] indRow = new int[1];
		int columsIndex = getColumnDimension() -1 ;
		int rowsIndex = getRowDimension() -1 ;
		
		for (int row = 0; row <= rowsIndex; row++) {
			indRow[0] = row; 
			getMatrix(indRow, 0, columsIndex).print(arg0, arg1);
			//System.out.println();
		
		}
	}
	
	
	
	/**
	 * @param m source Matrix
	 * @return new mantrix equal to m with its colums transposed
	 */
	static public Matrix getMatrixTransposeColumns(Matrix m) {
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
	 * @param m source Matrix
	 * @return new mantrix equal to m with its rows and colums transposed
	 */
	static public Matrix getMatrixTransposeRowCol(Matrix m) {
		return getMatrixTransposeRows(getMatrixTransposeColumns(m));
	}

}
