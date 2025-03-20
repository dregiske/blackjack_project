/**
 * This is the BJInterface file that contains
 * the interface for BJAbstract file
 */
import java.util.List;

/**
 * This is the BJInterface interface that specifies the
 * move sets and game logistics
 */
public interface BJInterface {

    /**
     * Print the players hand
     * 
     * @param playerHand An ArrayList of the players hand
     */
    void printPlayerHand(List<String> playerHand);

    /**
     * Prints the first card of dealers hand
     * 
     * @param dealerHand an ArrayList of the dealers hand
     */
    void printDealerHalfHand(List<String> dealerHand);

    /**
     * Prints the dealers full hand
     * 
     * @param dealerHand an ArrayList of the dealers hand
     */
    void printDealerFullHand(List<String> dealerHand);

    /**
     * Deal another card from the deck
     * 
     * @param hand An ArrayList of the hand to deal to
     * @param deck An ArrayList of the deck to deal a card from
     */
    void hit(List<String> hand, List<String> deck);

    /**
     * Calculates the value of the hand
     * 
     * @param hand an ArrayList of the hand to calculate
     * @return int value of the total hand
     */
    int handValue(List<String> hand);

    /**
     * Turns the card into an int value
     * to be calculated in handValue()
     * 
     * @param card the string of a card to parse
     * @return int value of the card
     */
    int parseValue(String card);

    /**
     * Turns the bet input into a int value
     * 
     * @param input a string value from user
     * @return the int value of the input
     */
    int parseBet(String input);

    /**
     * Checks if bet value is valid
     * 
     * @param bet a int value from user
     * @return true if bet is valid, false otherwise
     */
    boolean isValidBet(int bet);

    /**
     * Checks if move was valid
     * 
     * @param move Move input from the player
     * @return true if move is valid, false otherwise
     */
    boolean isValidMove(String move);

    /**
     * Shuffles the deck
     */
    void shuffleDeck();
}
