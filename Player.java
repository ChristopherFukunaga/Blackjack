public class Player extends Participant {
    private final int STARTING_MONEY = 1000;
    private int currentBet;
    private int totalMoney;
    
    public Player() {
        super();

        currentBet = 0;
        totalMoney = STARTING_MONEY;
    }

    public int getMoney() {
        return totalMoney;
    }
    
    public int getBet() {
        return currentBet;
    }

    public boolean setBet(int amount) {
        if (amount > totalMoney) {
            System.out.println("You don't have enough money to make that bet.");
            return false;
        }

        totalMoney -= amount;
        currentBet = amount;
        return true;
    }
    
    public void doubleBet() {
        totalMoney -= currentBet;
        currentBet *= 2;
    }

    public void payout(boolean blackjack, boolean push) {
        if (blackjack) {
            totalMoney += currentBet * 2.5;
        } else if (push) {
            totalMoney += currentBet;
        }else {
            totalMoney += currentBet * 2;
        }

        currentBet = 0;
    }
}