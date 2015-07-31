package myAlgorithm.partOne;

import java.util.Arrays;

/**
 * 归并排序
 * 
 * @author Rita Yuan
 * @time 2015年7月27日 上午10:41:01
 */
public class MergeSort {

	/**
	 * 归并排序
	 * 
	 * @param nums
	 * @param low
	 * @param mid
	 * @param high
	 */
	public void merge(int[] nums, int low, int mid, int high) {
		int[] temp = new int[high - low + 1];
		int i = low;
		int j = mid + 1;
		int k = 0;

		// 把较小的数先移到新数组中
		while (i <= mid && j <= high) {
			if (nums[i] < nums[j]) {
				temp[k++] = nums[i++];
			} else {
				temp[k++] = nums[j++];
			}
		}

		// 把左边剩余的数移入数组
		while (i <= mid) {
			temp[k++] = nums[i++];
		}

		// 把右边边剩余的数移入数组
		while (j <= high) {
			temp[k++] = nums[j++];
		}

		// 把新数组中的数覆盖nums数组
		for (int k2 = 0; k2 < temp.length; k2++) {
			nums[k2 + low] = temp[k2];
		}
	}

	/**
	 * 递归进行归并排序
	 * 
	 * @param nums
	 * @param low
	 * @param high
	 * @return
	 */
	public int[] sort(int[] nums, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			sort(nums, low, mid);
			sort(nums, mid + 1, high);
			merge(nums, low, mid, high);
		}
		return nums;
	}

	public static void main(String[] args) {
		MergeSort merge = new MergeSort();
		int[] nums = { 2, 7, 8, 3, 1, 6, 9, 0, 5, 4 };
		merge.sort(nums, 0, nums.length - 1);
		System.out.println(Arrays.toString(nums));
	}
}
