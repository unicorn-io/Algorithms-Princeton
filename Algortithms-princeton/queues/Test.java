/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Test {

    public static int sortAtN(int[] arr, int low, int high) {
        int rightSwapable = high, leftSwapable = low+1;
        leftSwapable = Test.moveSwapable(arr, low, high, leftSwapable, 1);
        rightSwapable = Test.moveSwapable(arr, low, high, rightSwapable, -1);
        while (leftSwapable < rightSwapable) {
            swap(arr, leftSwapable, rightSwapable);
            leftSwapable = Test.moveSwapable(arr, low, high, leftSwapable, 1);
            rightSwapable = Test.moveSwapable(arr, low, high, rightSwapable, -1);
        }
        swap(arr, low, leftSwapable - 1);
        return leftSwapable - low;
    }


    private static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    private static int moveSwapable(int[] arr, int low, int high, int arrIndex, int dir) {
        if (dir > 0) {
            while (arr[arrIndex] < arr[low] && arrIndex != high) {
                arrIndex++;
            }
            return arrIndex;
        } else {
            while (arr[arrIndex] >= arr[low] && arrIndex != low+1) {
                arrIndex--;
            }
            return arrIndex;
        }

    }

    public static int findNthSmallest(int[] arr, int m) {
        int total = 0, left = 0, right = arr.length-1, finalAnswer;
        while (total < m && left < right) {
            int temp = sortAtN(arr, left, right);
            if (total+temp >= m) {
                finalAnswer = arr[left + m - total - 1];
                return finalAnswer;
            }
            total += temp;
            left += temp;
        }
        if (total < m - 1) {
            return Integer.MIN_VALUE;
        }
        else {
            return arr[total];
        }

    }



    public static void main(String[] args) {

        int[] x = {16, 3, 5, 6, 2, 10, 14, 10, 15};
        int m  = sortAtN(x, 0, x.length -1);
        for (int i : x) {
            System.out.println(i);
        }
         System.out.println("left index is :" + m);
        m  = sortAtN(x, 0, x.length -1);
        for (int i : x) {
            System.out.println(i);
        }
        System.out.println("left index is :" + m);
        //int a = findNthSmallest(x, 5);
        //System.out.println(a);
        //for (int i : x) {
        //    System.out.println(i);
        //}
    }
}
