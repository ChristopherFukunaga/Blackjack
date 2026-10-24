/*  Author: Christopher Fukunaga
 *  File: Participant.java
 *  Purpose: The parent class for other fully implemented types of participants. Creates an
 *  an ArrayList of Card objects representing the participant's held cards. The Participant
 *  can addCard to their heldCards, clearCards, getCards (gets a String representation of cards)
 *  and get an added value of all cards.
 */


import java.util.ArrayList;

public class Participant {
    protected ArrayList<Card> heldCards;
    
    public Participant() {
        heldCards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        heldCards.add(card);
    }

    public ArrayList<Card> clearCards() {
        ArrayList<Card> oldCards = new ArrayList<Card>();

        for (int i = 0; i < heldCards.size(); i++) {
            oldCards.add(heldCards.get(i));
        }

        heldCards = new ArrayList<Card>();

        return oldCards;
    }

    public String getCards() {
        String cards = "";

        for (int i = 0; i < heldCards.size(); i++) {
            cards += heldCards.get(i).toString() + ", ";
        }

        return cards;
    }

    public int getAddedValue() {
        int sum = 0;
        int aceCount = 0;

        for (int i = 0; i < heldCards.size(); i++) {
            if (heldCards.get(i).getRank() == 1) {
                sum += 11;
                aceCount++;
            } else if (heldCards.get(i).getRank() > 10) {
                sum += 10;
            } else {
                sum += heldCards.get(i).getRank();
            }
        }

        // Adjust for aces if the sum is over 21
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }

        return sum;
    }
}