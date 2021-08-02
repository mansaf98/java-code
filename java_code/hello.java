
/**
 * hello
 */
import java.util.Arrays;

public class hello {

    public static void main(String[] arg) {
        int[] a = new int[] { 2, 8, 7, 1, 3, 5, 6, 4 };
        // mergeSort(a, a.length);
        quickSort(a, 0, a.length - 1);
        System.out.println("your organized array is :");
        for (int element : a) {
            System.out.print(element + ", ");
        }

    }

    public static void heapSort(int[] array) {
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            heapify(array, i, array.length - 1);
        }

        for (int i = array.length - 1; i > 0; i--) {
            swapKeys(array, 0, i);
            heapify(array, 0, i - 1);
        }
        System.out.println();
        System.out.println("your organized array is :");
        for (int element : array) {
            System.out.print(element + ", ");
        }
        System.out.println();
    }

    private static void heapify(int[] array, int i, int m) {
        System.out.println();
        System.out.println("working");
        for (int element : array) {
            System.out.print(element + ", ");
        }
        System.out.println();
        int j;
        while (2 * i + 1 <= m) {
            j = 2 * i + 1;
            if (j < m) {
                if (array[j] < array[j + 1])
                    j++;
            }
            if (array[i] < array[j]) {
                swapKeys(array, i, j);
                i = j;
            } else
                i = m;
        }

    }

    public static void swapKeys(int[] array, int i, int j) {
        int temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void InsertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            System.out.println();
            System.out.println("working");
            for (int element : array) {
                System.out.print(element + ", ");
            }
            System.out.println();

            int key = array[i];
            int j = i - 1;

            /*
             * Move elements of arr[0..i-1], that are greater than key, to one position
             * ahead of their current position
             */
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }

    }

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            System.out.println();
            System.out.println("working");
            for (int element : a) {
                System.out.print(element + ", ");
            }
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            System.out.println();
            System.out.println("working");
            for (int element : a) {
                System.out.print(element + ", ");
            }
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        System.out.println();
        System.out.println("working");
        for (int element : a) {
            System.out.print(element + ", ");
        }
        mergeSort(r, n - mid);
        System.out.println();
        System.out.println("working");
        for (int element : a) {
            System.out.print(element + ", ");
        }
        merge(a, l, r, mid, n - mid);
        System.out.println();
        System.out.println("working");
        for (int element : a) {
            System.out.print(element + ", ");
        }
        System.out.println();
        System.out.println("your organized array is :");
        for (int element : a) {
            System.out.print(element + ", ");
        }
        System.out.println();
    }

    public static void merge(int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    // divide the array on the basis of pivot
    public static int partition(int array[], int low, int high) {
        System.out.println("working at quick sort ");
        for (int element : array) {
            System.out.print(element + ", ");
        }

        // select last element as pivot
        int pivot = array[high];

        // initialize the second pointer
        int i = (low - 1);

        // Put the elements smaller than pivot on the left and
        // greater than pivot on the right of pivot
        for (int j = low; j < high; j++) {

            // compare all elements with pivot
            // swap the element greater than pivot
            // with element smaller than pivot
            // to sort in descending order
            // if (array[j] >= pivot)
            if (array[j] <= pivot) {

                // increase the second pointer if
                // smaller element is swapped with greater
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // put pivot in position
        // so that element on left are smaller
        // element on right are greater than pivot
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return (i + 1);
    }

    public static void quickSort(int array[], int low, int high) {

        if (low < high) {

            // Select pivot position and put all the elements smaller
            // than pivot on the left and greater than pivot on right
            int pi = partition(array, low, high);

            // sort the elements on the left of the pivot
            quickSort(array, low, pi - 1);

            // sort the elements on the right of pivot
            quickSort(array, pi + 1, high);

        }

    }

}