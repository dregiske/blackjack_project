/**
 * This is the BJAbstract file that contains
 * the BJAbstract abstract class
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the BJAbstract abstract class that contains
 * components for the BJ game and
 * implements the BJInterface
 */
public abstract class BJAbstract implements BJInterface{

    // The deck
    protected List<String> deck = new ArrayList<>(List.of(
        "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H",
        "JH", "QH", "KH", "AH",
        "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D",
        "JD", "QD", "KD", "AD",
        "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C",
        "JC", "QC", "KC", "AC",
        "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S",
        "JS", "QS", "KS", "AS"
    ));

    // tracks the amount of cards dealt
    public int deckIndex;

    // The dealers and players hand
    protected List<String> playerHand = new ArrayList<>();
    protected List<String> dealerHand = new ArrayList<>();

    // The initial betting wallet
    public int initialWallet = 200;

    // The total wallet
    public int bettingWallet;

    // The moves allowed in each version of BJ
    String[] possibleMoves;

    // Moves for the player
    protected final static String[] DEFAULT_MOVES = 
        {"hit", "stand", "double", "surrender"};
    protected final static String[] SPLIT_OPTION = 
        {"hit", "stand", "split", "double", "surrender"};
    protected final static String[] POST_SPLIT = 
        {"hit", "stand", "double"};
    protected final static String[] POST_HIT = 
        {"hit", "stand"};
    protected final static String[] POST_DOUBLE =
        {"stand"};

    // Messages for the game.
    protected static final String INVALID_INPUT =
        "That is not a valid move. Please try again.";
    protected static final String PUSH =
        "You push.";
    protected static final String PLAYER_WIN =
        "You win!";
    protected static final String DEALER_WIN =
        "Dealer wins.";
    protected static final String DEALER_21 = 
        "Dealer has 21.";
    protected static final String THANKS =
        "Thanks for playing!";
    protected static final String PLACE_BETS = 
        "Place your bets!";
    protected static final String PROMPT_MOVE =
        "What's your move? (Type the move)";
    protected static final String PROMPT_PLAY = 
        "Want to play? (Type q to quit)";

    // Other controls
    protected static final String QUIT = "q";

    @Override
    public void printPlayerHand(List<String> playerHand){
        System.out.print("Your hand is: ");
        for(String card : playerHand){
            System.out.print(" " + card);
        }
        System.out.print("\n");
        System.out.println("Total score: " + handValue(playerHand));
    }

    @Override
    public void printDealerHalfHand(List<String> dealerHand){
        System.out.print("Dealer shows: ");
        String card = dealerHand.get(0);
        System.out.print(card + "\n");
    }

    @Override
    public void printDealerFullHand(List<String> dealerHand){
        System.out.print("Dealers hand is: ");
        for(String card : dealerHand){
            System.out.print(" " + card);
        }
        System.out.print("\n");
        System.out.println("Total score: " + handValue(dealerHand));
    }

    public abstract void hit(List<String> hand, List<String> deck);

    @Override
    public int handValue(List<String> hand){
        int total = 0;
        int aceCount = 0;
        int value = 0;

        for(String card : hand){
            value = parseValue(card);
            if(card.endsWith("A")){
                aceCount++;
            }
            total += value;
        }
        if(aceCount > 0 && total > 21){
            total -= 10;
            aceCount--;
        }
        return total;
    }

    @Override
    public int parseValue(String card){
        String value = card.substring(0, card.length() - 1);
        switch(value){
            case "J":
            case "Q":
            case "K":
                return 10;
            case "A":
                return 11;
            default:
                return Integer.parseInt(value);
        }
    }

    @Override
    public int parseBet(String input){
        String value = input.substring(0, input.length() - 1);
        return Integer.parseInt(value);
    }

    @Override
    public boolean isValidMove(String move){
        for(int i = 0; i < possibleMoves.length; i++){
            if(move.equals(possibleMoves[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    public void shuffleDeck(){
        deck.clear();  // remove any leftovers
        deck.addAll(List.of( // reinitialize the deck
        "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH",
        "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD",
        "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC",
        "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS"
    ));

    // Shuffle the deck again
    Collections.shuffle(deck);
    System.out.println("Deck has been shuffled.");
    }
}
