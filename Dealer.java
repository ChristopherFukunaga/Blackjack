public class Dealer extends Participant {
    
    public Dealer() {
        super();
    }

    public Card getFaceUpCard() {
        return heldCards.get(0);
    }
}