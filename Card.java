public class Card {
    private int rank;
    private int suit;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public String toString() {
        String cardString = "";

        switch (rank) {
            case 1:
                cardString += "Ace";
                break;
            case 11:
                cardString += "Jack";
                break;
            case 12:
                cardString += "Queen";
                break;
            case 13:
                cardString += "King";
                break;
            default:
                cardString += rank;
                break;
        }

        cardString += " of ";

        switch (suit) {
            case 0:
                cardString += "Hearts";
                break;
            case 1:
                cardString += "Diamonds";
                break;
            case 2:
                cardString += "Clubs";
                break;
            case 3:
                cardString += "Spades";
                break;
        }

        return cardString;
    }
}
