/*  Author: Christopher Fukunaga
 *  File: BlackJack.java
 *  Purpose: The main class for the whole Blackjack project, handling the state of the
 *  game while mananging all of the objects it utilizes. User inputs are handeled through
 *  the console using a Scanner object. Uses a shared Deck object which the Participants
 *  can draw form and discard to. First Participants are dealt cards and a check is run
 *  to see if the Player has Blackjack (21 on first turn). Then the player gets their turn 
 *  where they can double (first turn only), hit, or stand. Then the dealer will go through
 *  their turn, continually weighing the risk if they were to draw a card. Then the winner
 *  is decided, and if the user chooses to they may play again.
 */


import java.util.Scanner;
import java.util.ArrayList;


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
            boolean playerActive = initializeGame(player, dealer, deck, scnr);

            System.out.println(deck.toString());
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
                
                boolean dealerActive = true;

                while (dealerActive) {
                    dealerActive = dealer.dealerTurn(player, deck);
                }

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
                        gamestate = false;
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
    public static boolean initializeGame(Player player, Dealer dealer, Deck deck, Scanner scnr) {
        ArrayList<Card> oldCards = new ArrayList<Card>();
        oldCards.addAll(player.clearCards());
        oldCards.addAll(dealer.clearCards());
        
        for (int i = 0; i < oldCards.size(); i++) {
            deck.discard(oldCards.get(i));
        }

        deck.shuffle();

        boolean amountSelected = false;

        while (!amountSelected) {
            System.out.printf("Total Money: %d%nHow much will you bet? ", player.getMoney());
            amountSelected = player.setBet(scnr.nextInt());
        }

        dealCards(deck, player, dealer);
        return true;
    }

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