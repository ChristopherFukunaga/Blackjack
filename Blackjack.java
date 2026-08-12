/*
 * Author: Christopher Fukunaga
 * File: BlackJack.java
 * Purpose: ...
 */

import java.util.Scanner;
// add insurance

public class Blackjack {
    public static void main(String[] args) {
        // Start Game: initialize deck and shuffle
        System.out.println("\n\nWelcome to Blackjack! Shuffling deck...\n");

        Scanner scnr = new Scanner(System.in);
        Deck deck = new Deck();
        Player player = new Player();
        Dealer dealer = new Dealer();
        boolean gamestate = true;
        boolean gamewon = false;
        String choice;

        // Deal Cards: player gets dealt then dealer then player again
        while (gamestate) {
            player.clearCards();
            dealer.clearCards();
            boolean amountSelected = false;

            while (!amountSelected) {
                System.out.printf("Total Money: %d%nHow much will you bet? ", player.getMoney());
                amountSelected = player.setBet(scnr.nextInt());
            }

            dealCards(deck, player, dealer);
            boolean playerActive = true;

            System.out.println("\nDealer's hand: " + dealer.getFaceUpCard().toString() + ", [Hidden Card]");
            System.out.println("Your hand: " + player.getCards() + " (" + player.getAddedValue() +")\n");

            // Player Choice: stand, hit, split, double
            if (player.getAddedValue() == 21) {
                System.out.println("Blackjack! You win!");
                player.payout(true, false);
                gamewon = true;
            }

            boolean firstTurn = true;

            while (playerActive && !gamewon) {
                System.out.println("What is your move?");
                choice = scnr.next();

                playerActive = playerMove(player, deck, choice, firstTurn);
                firstTurn = false;

                if (player.getAddedValue() > 21) {
                    System.out.println("Bust! You lose.\n\n");
                    gamewon = true;
                } else if (player.getAddedValue() == 21) {
                    System.out.println("You have 21! Let's see what the dealer has...");
                    gamewon = true;
                }

                if (!gamewon) {
                    System.out.println("Your hand: " + player.getCards() + " (" + player.getAddedValue() + ")\n");
                }
            }

            if (!gamewon) {
                System.out.println("Dealer's hand: " + dealer.getCards() + " (" + dealer.getAddedValue() + ")");
                if (player.getAddedValue() < dealer.getAddedValue()) {
                    System.out.println("You lose.\n\n");
                } else if (player.getAddedValue() > dealer.getAddedValue()) {
                    System.out.println("You win!\n\n");
                    player.payout(false, false);
                } else {
                    System.out.println("Push. It's a tie.\n\n");
                    player.payout(false, true);
                }
            }   

            boolean againSelected = false;

            if (player.getMoney() <= 0) {
                System.out.println("You are out of money. Game over.");
                break;
            }
            
            while (!againSelected) {
                System.out.println("Play Again?");
                choice = scnr.next();
                againSelected = true;

                switch (choice.toLowerCase()) {
                    case "yes":
                        break;
                    case "no":
                        break;
                    default:
                        againSelected = false;
                        System.out.println("Invalid choice. Please type 'yes' or 'no'.");
                        break;
                }

                System.out.println("---------------------------------------------");
            }
        }

        scnr.close();

    }   

    /* 
     * dealCards(deck, player, dealer) -- Takes a Deck object deck and two Particapant objects player and dealer. Deal back and
     *                                    to each Participant one at a time.
     */
    public static void dealCards(Deck deck, Player player, Dealer dealer) {
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }
    }

    public static boolean playerMove(Player player, Deck deck, String choice, boolean firstTurn) {
        switch (choice.toLowerCase()) {
            case "hit":
                Card card = deck.draw();
                System.out.println("You drew: " + card.toString());
                player.addCard(card);
                return true;

            case "stand":
                return false;
  
            case "double":
                // double bet and add card to hand only available on first move
                if (!firstTurn) {
                    System.out.println("You can only double on your first turn.");
                    return true;
                }

                if (player.getMoney() < player.getBet()) {
                    System.out.println("You don't have enough money to double your bet.");
                    return true;
                }

                player.doubleBet();
                player.addCard(deck.draw());
                return false;
                
           default:
                System.out.println("Invalid choice.");
                return true;
        }
    }
}