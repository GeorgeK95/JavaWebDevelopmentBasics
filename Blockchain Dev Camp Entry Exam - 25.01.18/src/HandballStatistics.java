import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class HandballStatistics {
    private static String TEAM_SPLITTER = "\\s\\|\\s";
    private static String POINTS_SPLITTER = ":";

    private static Map<String, Team> group = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String inputLine = in.readLine();
            if ("stop".equals(inputLine)) {
                printGroupResult();
                break;
            }

            processMatchData(inputLine);
        }
    }

    private static void processMatchData(String inputLine) {
        //get teams
        String[] matchData = generateAndFilterMatchData(inputLine);
        String homeTeamName = matchData[0];
        String awayTeamName = matchData[1];
        String firstMatchResult = matchData[2];
        String secondMatchResult = matchData[3];
        updateGroup(homeTeamName, awayTeamName);
        //find winner
        //update teams info
        processMatchData(homeTeamName, awayTeamName, firstMatchResult, secondMatchResult);
    }

    private static String[] generateAndFilterMatchData(String inputLine) {
        List<String> matchDataList = Arrays.stream(inputLine.split(TEAM_SPLITTER)).filter(data -> !data.equals("|")).collect(Collectors.toList());
        return matchDataList.toArray(new String[0]);
    }

    private static void processMatchData(String homeTeamName, String awayTeamName, String firstMatchResult, String secondMatchResult) {
        String winner = getWinner(homeTeamName, awayTeamName, firstMatchResult, secondMatchResult);
        String loser = getLoser(winner, homeTeamName, awayTeamName);

        group.get(winner).addWin();
        group.get(winner).addOponent(loser);

        group.get(loser).addOponent(winner);
    }

    private static String getWinner(String homeTeamName, String awayTeamName, String firstMatchResult, String secondMatchResult) {
        int homeTeamTotalPoints = Integer.parseInt(firstMatchResult.split(POINTS_SPLITTER)[0]) + Integer.parseInt(secondMatchResult.split(POINTS_SPLITTER)[1]);
        int awayTeamTotalPoints = Integer.parseInt(firstMatchResult.split(POINTS_SPLITTER)[1]) + Integer.parseInt(secondMatchResult.split(POINTS_SPLITTER)[0]);

        if (homeTeamTotalPoints == awayTeamTotalPoints) {
            int homeTeamAwayPoints = Integer.parseInt(secondMatchResult.split(POINTS_SPLITTER)[1]);
            int awayTeamAwayPoints = Integer.parseInt(firstMatchResult.split(POINTS_SPLITTER)[1]);

            return homeTeamAwayPoints > awayTeamAwayPoints ? homeTeamName : awayTeamName;
        }

        return homeTeamTotalPoints > awayTeamTotalPoints ? homeTeamName : awayTeamName;
    }

    private static String getLoser(String winner, String homeTeamName, String awayTeamName) {
        if (winner.equals(homeTeamName)) {
            return awayTeamName;
        } else {
            return homeTeamName;
        }
    }

    private static void updateGroup(String homeTeamName, String awayTeamName) {
        if (!group.containsKey(homeTeamName)) {
            group.put(homeTeamName, new Team(homeTeamName));
        }
        if (!group.containsKey(awayTeamName)) {
            group.put(awayTeamName, new Team(awayTeamName));
        }
    }

    private static void printGroupResult() {
        List<Team> sortedTeams = group.values().stream().collect(Collectors.toList())
                .stream().sorted(Comparator.comparing(Team::getWins).reversed().thenComparing(Team::getName)).collect(Collectors.toList());
        for (Team currentTeam : sortedTeams) {
            System.out.println(String.format("%s", currentTeam.getName()));
            System.out.println(String.format("- Wins: %d", currentTeam.getWins()));
            System.out.println(String.format("- Opponents: %s", currentTeam.getOpponents().toString()
                    .replace("[", "").replace("]", "")));
        }
    }
}

class Team {
    private String name;
    private int wins;
    private List<String> opponents;

    public Team(String name) {
        this.opponents = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public List<String> getOpponents() {
        return opponents.stream().sorted().collect(Collectors.toList());
    }

    public void addWin() {
        this.wins++;
    }

    public void addOponent(String team) {
        this.opponents.add(team);
    }
}
