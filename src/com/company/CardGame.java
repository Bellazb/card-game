package com.company;

import java.util.*;

public class CardGame {

    private List<String> cards;
    private Map<Integer, List<String>> players;
    private Scanner scanner;

    public CardGame() {
        initializeCard();
        players = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    private void initializeCard() {
        cards = new ArrayList<>(List.of("2@", "2#", "2^", "2*", "3@", "3#", "3^", "3*", "4@", "4#", "4^", "4*",
                "5@", "5#", "5^", "5*", "6@", "6#", "6^", "6*", "7@", "7#", "7^", "7*",
                "8@", "8#", "8^", "8*", "9@", "9#", "9^", "9*", "10@", "10#", "10^", "10*",
                "J@", "J#", "J^", "J*", "Q@", "Q#", "Q^", "Q*", "K@", "K#", "K^", "K*",
                "A@", "A#", "A^", "A*"));
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Card Game Menu ---");
            System.out.println("Please complete each step in sequence:");
            System.out.println("1. Shuffle Cards");
            System.out.println("2. Distribute Cards");
            System.out.println("3. Find Winner");
            System.out.println("4. Exit");
            System.out.print("Choose an option to start with 1: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    shuffleCard();
                    break;
                case 2:
                    distributeCard();
                    break;
                case 3:
                    findWinner();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please select again");
            }
        }
    }

    private void shuffleCard() {
        Collections.shuffle(cards);
        System.out.println("\nShuffled Card: " + String.join(",", cards));
    }

    private void distributeCard() {
        players.clear();

        // create new player
        for (int i = 0; i < 4; i++) {
            players.put(i + 1, new ArrayList<>());
        }

        // assign card to the player
        for (int i = 0; i < cards.size(); i++) {
            players.get((i % 4) + 1).add(cards.get(i));
        }

        // display cards for each player
        for (int i = 1; i <= 4; i++) {
            System.out.println("Player " + i + "'s card: " + players.get(i));
        }

    }

    private void findWinner() {
        int winner = determineWinner();
        if (winner < 0) {
            System.out.println("The winner is not determined yet, please choose option 1 & 2 first");
        } else {
            System.out.println("The winner is Player " + winner + "!");
        }
    }

    private int determineWinner() {
        int winner = -1;
        int maxCount = 0;
        String bestRank = "";

        for (Map.Entry<Integer, List<String>> entry : players.entrySet()) {
            int playerId = entry.getKey();
            List<String> playerCards = entry.getValue();

            Map<String, Integer> rankCount = getRankCount(playerCards);

            String playerBestRank = "";
            int playerMaxCount = 0;

            for (Map.Entry<String, Integer> countEntry : rankCount.entrySet()) {
                String rank = countEntry.getKey();
                int count = countEntry.getValue();

                if (count > playerMaxCount || count == playerMaxCount && compareRank(rank, playerBestRank) > 0) {
                    playerBestRank = rank;
                    playerMaxCount = count;
                }
            }

            if (playerMaxCount > maxCount || playerMaxCount == maxCount && compareRank(playerBestRank, bestRank) > 0) {
                maxCount = playerMaxCount;
                bestRank = playerBestRank;
                winner = playerId;
            }
        }
        return winner;
    }

    // count each rank
    private Map<String, Integer> getRankCount(List<String> playerCards) {
        Map<String, Integer> rankCount = new HashMap<>();
        for (String card : playerCards) {
            String rank = card.replaceAll("[@#^*]", "");
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }
        return rankCount;
    }

    // compare the rank
    private int compareRank(String rank1, String rank2) {
        List<String> rankOrder = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        return Integer.compare(rankOrder.indexOf(rank1), rankOrder.indexOf(rank2));
    }

}
