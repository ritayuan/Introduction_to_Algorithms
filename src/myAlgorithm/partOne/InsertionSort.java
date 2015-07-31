package myAlgorithm.partOne;

import java.util.Arrays;

/**
 * 插入排序
 * 
 * @author Rita Yuan
 * @time 2015年7月22日 下午5:24:07
 */
public class InsertionSort {

	/**
	 * 从小到大插入排序
	 * 
	 * @param list
	 */
	public void insertionSort(int[] list) {
		for (int j = 1; j < list.length; j++) {
			int key = list[j];
			int i = j - 1;

			// 将大于key的所有元素后移一位
			while (i >= 0 && list[i] > key) {
				list[i + 1] = list[i];
				i--;
			}
			list[i + 1] = key;
		}
	}

	/**
	 * 查找数组中是否有某一数值
	 * 
	 * @param list
	 * @param v
	 * @return 如果有返回该数值位置，否则返回-1
	 */
	public int searchValue(int[] list, int v) {
		int index = -1;
		int i = 0;
		while (i < list.length) {
			if (list[i] == v) {
				index = i + 1;
				break;
			}
			i++;
		}
		return index;
	}

	public static void main(String[] args) {
		InsertionSort insertion = new InsertionSort();
		int[] test = { 5, 9, 8, 7, 4, 6, 1, 2, 0, 3 };
		System.out.println(insertion.searchValue(test, 13));
		insertion.insertionSort(test);
		System.out.println(Arrays.toString(test));

	}
}
