package myAlgorithm.partOne;

import java.util.Scanner;

/**
 * 矩阵乘法的Strassen算法
 * 
 * @author Administrator
 * @time 2015年7月31日 下午4:00:20
 */

public class MatrixStrassen {

	private static final int LENGTH = 32;

	private static class Matrix {
		int[][] data = new int[LENGTH][LENGTH];
	}

	/**
	 * 矩阵分块
	 * 
	 * @param m11
	 * @param m12
	 * @param m21
	 * @param m22
	 * @param m
	 *            被分块的矩阵
	 * @param order
	 *            矩阵的阶
	 */
	public void divide(Matrix m11, Matrix m12, Matrix m21, Matrix m22, Matrix m, int order) {
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				m11.data[i][j] = m.data[i][j];
				m12.data[i][j] = m.data[i][j + order];
				m21.data[i][j] = m.data[i + order][j];
				m22.data[i][j] = m.data[i + order][j + order];
			}
		}
	}

	/**
	 * 两个矩阵相加
	 * 
	 * @param a
	 * @param b
	 * @param order
	 *            矩阵的阶
	 * @return a + b
	 */
	public Matrix add(Matrix a, Matrix b, int order) {
		Matrix result = new Matrix();
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				result.data[i][j] = a.data[i][j] + b.data[i][j];
			}
		}
		return result;
	}

	/**
	 * 两个矩阵相减
	 * 
	 * @param a
	 * @param b
	 * @param order
	 *            矩阵的阶
	 * @return a - b
	 */
	public Matrix subtract(Matrix a, Matrix b, int order) {
		Matrix result = new Matrix();
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				result.data[i][j] = a.data[i][j] - b.data[i][j];
			}
		}
		return result;
	}

	/**
	 * 将矩阵拼接
	 * 
	 * @param m11
	 * @param m12
	 * @param m21
	 * @param m22
	 * @param order
	 *            矩阵的阶
	 * @return
	 */
	public Matrix merge(Matrix m11, Matrix m12, Matrix m21, Matrix m22, int order) {
		Matrix result = new Matrix();
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				result.data[i][j] = m11.data[i][j];
				result.data[i][j + order] = m12.data[i][j];
				result.data[i + order][j] = m21.data[i][j];
				result.data[i + order][j + order] = m22.data[i][j];
			}
		}
		return result;
	}

	/**
	 * 二阶矩阵的乘法
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public Matrix multiply(Matrix m1, Matrix m2) {
		Matrix result = new Matrix();
		result.data[0][0] = m1.data[0][0] * m2.data[0][0] + m1.data[0][1] * m2.data[1][0];
		result.data[0][1] = m1.data[0][0] * m2.data[0][1] + m1.data[0][1] * m2.data[1][1];
		result.data[1][0] = m1.data[1][0] * m2.data[0][0] + m1.data[1][1] * m2.data[1][0];
		result.data[1][1] = m1.data[1][0] * m2.data[0][1] + m1.data[1][1] * m2.data[1][1];
		return result;
	}

	/**
	 * Strassen算法，递归计算矩阵乘法
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return
	 */
	public Matrix strassen(Matrix a, Matrix b, int order) {
		Matrix a11 = new Matrix();
		Matrix a12 = new Matrix();
		Matrix a21 = new Matrix();
		Matrix a22 = new Matrix();

		Matrix b11 = new Matrix();
		Matrix b12 = new Matrix();
		Matrix b21 = new Matrix();
		Matrix b22 = new Matrix();

		Matrix c = new Matrix();
		if (order == 2) {
			c = multiply(a, b);
			return c;
		} else {
			order /= 2;
			divide(a11, a12, a21, a22, a, order);
			divide(b11, b12, b21, b22, b, order);

			Matrix p1 = strassen(a11, subtract(b12, b22, order), order); // p1 = a11 * (b12 - b22)
			Matrix p2 = strassen(add(a11, a12, order), b22, order); // p2 = (a11 + a12) * b22
			Matrix p3 = strassen(add(a21, a22, order), b11, order); // p3 = (a21 + a22) * b11
			Matrix p4 = strassen(a22, subtract(b21, b11, order), order); // p4 = a22 * (b21 - b11)
			Matrix p5 = strassen(add(a11, a22, order), add(b11, b22, order), order); // p5 = (a11 + a22) * (b11 + b22)
			Matrix p6 = strassen(subtract(a12, a22, order), add(b21, b22, order), order); // p6 = (a12 - a22) * (b21 + b22)
			Matrix p7 = strassen(subtract(a11, a21, order), add(b11, b12, order), order); // p7 = (a11 - a21) * (b11 + b12)

			Matrix c11 = add(add(p4, p5, order), subtract(p6, p2, order), order); // c11 = p4 + p5 + p6 - p2
			Matrix c12 = add(p1, p2, order); // c12 = p1 + p2
			Matrix c21 = add(p3, p4, order); // c21 = p3 + p4
			Matrix c22 = add(p1, subtract(subtract(p5, p3, order), p7, order), order); // c22 = p1 + p5 - p3 - p7
			c = merge(c11, c12, c21, c22, order);
			return c;
		}
	}

	/**
	 * 判断矩阵阶数是否是2的幂
	 * 
	 * @param order
	 * @return
	 */
	public boolean judgeOrder(int order) {
		while (order % 2 == 0) {
			order /= 2;
		}
		return (order == 1 ? true : false);
	}

	public static void main(String[] args) {
		Matrix a = new Matrix();
		Matrix b = new Matrix();
		Matrix c = new Matrix();
		MatrixStrassen instance = new MatrixStrassen();
		Scanner input = new Scanner(System.in);
		System.out.print("输入矩阵的阶数: ");
		int order = input.nextInt();

		if (instance.judgeOrder(order)) {
			System.out.println("矩阵阶数为2的幂");
			System.out.println("输入矩阵A:");

			for (int i = 0; i < order; i++) {
				for (int j = 0; j < order; j++) {
					a.data[i][j] = input.nextInt();
				}
			}

			System.out.println("输入矩阵B:");
			for (int i = 0; i < order; i++) {
				for (int j = 0; j < order; j++) {
					b.data[i][j] = input.nextInt();
				}
			}

			if (order == 1) { // 矩阵阶数为1时的特殊处理
				c.data[0][0] = a.data[0][0] * b.data[0][0];
			} else {
				c = instance.strassen(a, b, order);
			}

			System.out.println("矩阵C为:");
			for (int i = 0; i < order; i++) {
				for (int j = 0; j < order; j++) {
					System.out.print(c.data[i][j] + "     ");
				}
				System.out.println();
			}
		} else {
			System.out.println("矩阵阶数不是2的幂");
		}
		input.close();
	}

}
