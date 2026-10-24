import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> drawPile;
    private ArrayList<Card> discardPile;

    public Deck() {
        drawPile = new ArrayList<Card>();
        discardPile = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                discardPile.add(new Card(j, i));
            }
        }

        shuffle();
    }

    public void shuffle() {
        int rand;

        while (discardPile.isEmpty() != true) {
            rand = (int) (Math.random() * discardPile.size());
            drawPile.add(discardPile.remove(rand));
        }
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    public Card draw() {
        if (drawPile.isEmpty()) {
            shuffle();
        }

        return drawPile.remove(0);
    }
}