public class Dealer extends Participant {
    
    public Dealer() {
        super();
    }

    public Card getFaceUpCard() {
        return heldCards.get(0);
    }

    public boolean dealerTurn(Player player, Deck deck) {
        Card drawCard;

        // Dealer must draw until they have 17
        while (this.getAddedValue() <= 16) {
            drawCard = deck.draw();
            System.out.println("Dealer drew: " + drawCard.toString());

            this.addCard(drawCard);


        }
        
        System.out.println("Dealer's hand: " + this.getCards() + " (" + this.getAddedValue() + ")");

        double risk = calculateRisk(player);

        if ((risk > 0.45) || (this.getAddedValue() > player.getAddedValue())) {
            return false;
        } else {
            drawCard = deck.draw();
            System.out.println("Dealer drew: " + drawCard.toString());
            this.addCard(drawCard);
            System.out.println("Dealer's hand: " + this.getCards() + " (" + this.getAddedValue() + ")");

            return true;
        }
    }

    private double calculateRisk(Player player) {
        double risk;

        double numCards = this.heldCards.size() + player.heldCards.size();
        double cardValues = this.getAddedValue() + player.getAddedValue();
        int meanCard = (int) Math.ceil(cardValues / numCards); // mean card in play

        int[] allPlayedCards = new int[(int) numCards];
        int medianCard;
        int index = 0;

        for (int i = 0; i < player.heldCards.size(); i++) {
            allPlayedCards[index] = player.heldCards.get(i).getRank();
            index++;
        }

        for (int i = 0; i < this.heldCards.size(); i++) {
            allPlayedCards[index] = this.heldCards.get(i).getRank();
            index++;
        }

        allPlayedCards = sort(allPlayedCards);
        medianCard = allPlayedCards[allPlayedCards.length / 2];

        risk = ((meanCard / 12.0) + (medianCard / 12.0)) / 2.0;

        return risk;
    }

    private int[] sort(int[] list) {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            int temp;

            for (int i = 0; i < list.length - 1; i++) {
                if (list[i] > list[i + 1]) {
                    temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;

                    sorted = false;
                }
            }
        }

        return list;
    }
}