import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Stream;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class TheWall {
    private static final String SPACE_SEPARATOR = " ";
    private static final int FOOT_COST = 195;
    private static final int ICE_COST = 1900;
    private static final int TARGET_HEIGHT = 30;

    public static void main(String[] args) throws IOException {
        long coins = 0L;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[] sectors = Stream.of(in.readLine().split(SPACE_SEPARATOR)).mapToInt(Integer::parseInt).toArray();
        int[] dailyConsummation = initializeDailyConsumationArray(sectors);

        int dayIndex = 0;
        while (!allSectorsAre30FootsHeight(sectors)) {
            long dailyCost = 0L;
            for (int i = 0; i < sectors.length; i++) {
                if (sectors[i] < TARGET_HEIGHT) {
                    dailyCost += FOOT_COST;
                    sectors[i]++;
                }
            }
            dailyConsummation[dayIndex++] += dailyCost;
            coins += dailyCost * ICE_COST;
        }

        printResult(dailyConsummation, coins);
    }

    private static int[] initializeDailyConsumationArray(int[] sectors) {
        OptionalInt min = Arrays.stream(sectors).min();
        if (min.isPresent()) {
            return new int[TARGET_HEIGHT - min.getAsInt()];
        }
        return null;
    }

    private static void printResult(int[] dailyConsummation, long coins) {
        System.out.println(Arrays.toString(dailyConsummation).replace("]", "").replace("[", "")/*.replace(",", "")*/);
        System.out.println(String.format("%d coins", coins));
    }

    private static boolean allSectorsAre30FootsHeight(int[] sectors) {
        for (int sector : sectors) {
            if (sector < TARGET_HEIGHT) {
                return false;
            }
        }

        return true;
    }
}
