package simpleweb.algorithm;

import java.util.Arrays;

/**
 * @author guo
 */
public class TreeArray {
    private int[] cArray;

    public TreeArray(int size) {
        cArray = new int[size + 1];
    }

    public void setAItem(int index, int deltaValue) {
        if (index <= 0 || index > cArray.length) {
            return;
        }

        while (index < cArray.length) {
            System.out.println(index);
            cArray[index] += deltaValue;
            index += (index) & ((~index) + 1);
        }

        System.out.println(Arrays.toString(cArray));
    }

    public int getPrefix(int index) {
        if (index <= 0 || index > cArray.length) {
            return 0;
        }

        int prefix = 0;

        while (index > 0) {
            prefix += cArray[index];
            index -= (index) & ((~index) + 1);
        }

        return prefix;
    }
}
