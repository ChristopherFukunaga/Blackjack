import java.util.ArrayList;

public class Participant {
    protected ArrayList<Card> heldCards;
    
    public Participant() {
        heldCards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        heldCards.add(card);
    }

    public void clearCards() {
        heldCards = new ArrayList<Card>();
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