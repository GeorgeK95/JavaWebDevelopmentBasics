import java.util.Scanner;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class JessysFriend {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int startInterval = in.nextInt();
        int endInterval = in.nextInt();
        int magicNumber = in.nextInt();
        int stepCounter = 0;
        boolean isFound = false;

        for (int startIntervalIndex = startInterval; startIntervalIndex <= endInterval; startIntervalIndex++) {
            for (int endIntervalIndex = startInterval; endIntervalIndex <= endInterval; endIntervalIndex++) {
                stepCounter++;
                if (startIntervalIndex + endIntervalIndex == magicNumber) {
                    printFoundedResult(stepCounter, startIntervalIndex, endIntervalIndex, magicNumber);
                    isFound = true;
                    break;
                }
            }

            if (isFound) {
                break;
            }
        }

        if (!isFound)
            printFailedResult(stepCounter, magicNumber);
    }

    private static void printFailedResult(int stepCounter, int magicNumber) {
        System.out.println(String.format("%d combinations - neither equals %d", stepCounter, magicNumber));
    }

    private static void printFoundedResult(int counter, int i, int j, int num) {
        System.out.println(String.format("Combination N:%d (%d + %d = %d)", counter, i, j, num));
    }

}
