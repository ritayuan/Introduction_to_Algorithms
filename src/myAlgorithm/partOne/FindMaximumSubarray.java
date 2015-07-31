package myAlgorithm.partOne;

import java.util.Arrays;

/**
 * 求最大子数组
 * 
 * @author Rita Yuan
 * @time 2015年7月28日 下午1:40:17
 */
public class FindMaximumSubarray {
	private static class Result {
		int leftIndex;
		int rightIndex;
		int max;
	}

	/**
	 * 找到跨越中间的最大的子数组
	 * 
	 * @param low
	 * @param high
	 * @param mid
	 * @param nums
	 * @return
	 */
	public Result findMaxCrossingSubarray(int low, int high, int mid, int[] nums) {
		int leftSum = 0;
		int rightSum = 0;
		int sum = 0;

		Result result = new Result();
		for (int i = mid; i >= low; i--) {
			sum += nums[i];
			if (sum > leftSum) {
				leftSum = sum;
				result.leftIndex = i;
			}
		}

		sum = 0;
		for (int i = mid + 1; i <= high; i++) {
			sum += nums[i];
			if (sum > rightSum) {
				rightSum = sum;
				result.rightIndex = i;
			}
		}
		result.max = leftSum + rightSum;
		return result;
	}

	/**
	 * 递归查找最大子数组 时间复杂度O(nlgn)
	 * @param nums
	 * @param low
	 * @param high
	 * @return
	 */
	public Result findMaximumSubarray(int[] nums, int low, int high) {
		Result result = new Result();
		if (high == low) {
			result.leftIndex = result.rightIndex = low;
			result.max = nums[low];
			return result;
		} else {
			int mid = (low + high) / 2;

			Result left = findMaximumSubarray(nums, low, mid);
			Result right = findMaximumSubarray(nums, mid + 1, high);
			Result cross = findMaxCrossingSubarray(low, high, mid, nums);
			if (left.max >= right.max && left.max >= cross.max) {
				result = left;
			} else if (right.max >= left.max && right.max >= cross.max) {
				result = right;
			} else {
				result = cross;
			}
			return result;
		}
	}
	
	/**
	 * 线性查找最大子数组 时间复杂度O(n)
	 * @param nums
	 * @param low
	 * @param high
	 * @return
	 */
	public Result findMaximumSubarrayLinear(int[] nums, int low, int high) {
		Result result = new Result();
		int tempMax = 0;

		if (nums.length == 1) {
			result.leftIndex = result.rightIndex = 0;
			result.max = nums[0];
			return result;
		}

		for (int i = 0; i < nums.length; i++) {
			tempMax += nums[i];
			if (tempMax < 0) {
				tempMax = 0;
				result.leftIndex = i + 1;
			}
			if (tempMax > result.max) {
				result.max = tempMax;
				result.rightIndex = i;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[] nums = new int[] { 1, 12, 5, -30, -9, 15, 24, -18, -17, 27, 16, 4, -3, 5, -4, 14, 19, -21, 4 };
		FindMaximumSubarray find = new FindMaximumSubarray();
		Result resultA = find.findMaximumSubarray(nums, 0, nums.length - 1);
		Result resultB = find.findMaximumSubarrayLinear(nums, 0, nums.length - 1);
		System.out.println(Arrays.toString(nums));
		System.out.println(resultB.leftIndex + " " + resultB.rightIndex + " " + resultB.max);
		System.out.println(resultA.leftIndex + " " + resultA.rightIndex + " " + resultA.max);
	}
}
