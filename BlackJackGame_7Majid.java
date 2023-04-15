/*
Ahmed Majid
Inherits-A7: Blackjack Game
*/

import java.util.ArrayList;
import java.util.Scanner;

/*
@Author Ahmed Majid
The main class which runs the entire game
*/

public class BlackJackGame_7Majid {
   public static void main (String[] args) {
      Blackjack blackjack = new Blackjack();
      blackjack.play();
   }
}

/*
Blackjack class, creates the method
which runs the game 
*/
class Blackjack {
   private Scanner scan;
   private boolean playAgain;
   private int playerWins;
   private int dealerWins;
   private int ties;
      
   /*
   constructor which defines the values
   for all the instance variables      
   */
   public Blackjack() {
      scan  = new Scanner(System.in);
      playAgain  = true;
      playerWins = 0;
      dealerWins = 0;
      ties = 0;
   }
      
   /*
   play method, creates deck and dealer objects
   uses a while loop to make sure the player wants
   to continue playing
   
   creates new players, asks if they want to hit or stay
   checks if player one or if dealer one, adjusts score accordingly
  */
   public void play() {     
      while (playAgain) {
         Deck theDeck1 = new Deck();
         ArrayList<Card> deck2 = theDeck1.getDeck();
         deck2.clear();
      
         while (deck2.size() < 52) {
            Deck theDeck = new Deck();
            ArrayList<Card> deck = theDeck.getDeck();
            deck2.add(theDeck.deal());   
         }
            
         System.out.println("Welcome to Victorious 21 Casino!"); 
         Dealer d = new Dealer();
         d.addPlayer();
      
         Player dealer = new Player("Dealer");
         dealer.addCard(theDeck1.deal());
         dealer.addCard(theDeck1.deal());
      
         for (Player player : d.getPlayers()) {
            player.addCard(theDeck1.deal());
            player.addCard(theDeck1.deal());
            System.out.println("\n" + player.toString());
         }
      
         if (dealer.hand.getHandValue() == 21) {
            System.out.println("The Dealer has BLACKJACK!!!");
            for (Player p : d.getPlayers()) {
               if (p.hand.getHandValue() < 21) {
                  System.out.println("\n" + p.getName() + " lost, the dealer wins!\n");
                  dealerWins++;
               }
            
               else if (p.hand.getHandValue() == 21) {
                  System.out.println("\n" + p.getName() + " tied with the dealer!\n");
                  ties++;
               }
            } 
            
            System.out.println("\nPlayer Wins: " + playerWins +"\nDealer Wins: " + dealerWins + "Ties: " + ties + "\n");
      
            System.out.println("Thanks for playing in the Victorious 21 Casino!!");
            System.out.println("\nWould you like to play again? (y/n)");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("n")) {
               System.exit(1);
            }
            
            else {
               continue;
            }
         }
      
         for (Player p : d.getPlayers()) {
            boolean bust = false;
            while (!bust) {
               System.out.println("\n" + p.getName() +"'s hand " + p.getHandValue());
               System.out.println(p.getName() + " would you like to hit or stay?: ");
               String answer1 = scan.nextLine();
                  if (answer1.equalsIgnoreCase("Hit")) {
                     p.addCard(theDeck1.deal());
                     if (p.hand.getHandValue() > 21) {
                        System.out.print("\nOoooh... unlucky,  " + p.getName() + " busted!\n");
                        bust = true;
                        break;
                     }
                  } else if (answer1.equalsIgnoreCase("Stay")) {
                     break;
                  }
            }
         }
      
         while (dealer.hand.getHandValue() < 20) {
            dealer.addCard(theDeck1.deal());
         }
      
         System.out.println("\n" + dealer.getName() + "'s " + dealer.getHandValue());
      
         for (Player p : d.getPlayers()) {
            if (p.hand.getHandValue() > 21) {
               System.out.println("\n" + p.getName() + " busts! The Dealer Wins!");
               dealerWins++;
            }
         
            else if (dealer.hand.getHandValue() > 21) {
               System.out.println("\n" + dealer.getName() + " busts, " + p.getName() + " wins!");
               playerWins++;
            }
         
            else if (p.hand.getHandValue() > dealer.hand.getHandValue()) {
               System.out.println("\n"  + p.getName() + " won, the dealer lost!");
               playerWins++;
            }
         
            else if (p.hand.getHandValue() < dealer.hand.getHandValue()) {
               System.out.println("\n" + p.getName() + " lost, the dealer wins!");
               dealerWins++;
            }
         
            else if (p.hand.getHandValue() == dealer.hand.getHandValue()) {
               System.out.println("\n" + p.getName() + " ties with the Dealer!");
               ties++;
            }
         
            else if (p.hand.getHandValue() > 21 && dealer.hand.getHandValue() > 21) {
               System.out.println("Both " + p.getName() + " and the Dealer bust!");
            }
         }
         
         System.out.println("\nDealer Wins: " + dealerWins +"\nPlayer Wins: " + playerWins + "\nTies: " + ties + "\n");
         
         System.out.println("\nWould you like to play again? (y/n)");
         String input = scan.nextLine();
         if (input.equalsIgnoreCase("n")) {
            playAgain = false;
         }
      }
      System.out.println("\nThanks for playing at Victorious 21 Casino!!");   }
}

/*
creates a player object and methods for the object
*/   
class Player{
   public Hand hand = new Hand();
   private String name;
   
   public Player(String name){
      this.name = name;
   }
   
   public void addCard(Card card){
      hand.addCard(card);
   }
      
   /*
   finds out whether or not the player wants to hit or stay
   @returns the user's choice
   */   
   public String getUserChoice(){
      Scanner input = new Scanner(System.in);
      System.out.println("Would you like to hit or stay? (hit/stay)");
      String userChoice = input.nextLine().toLowerCase();
      return userChoice;
   }
   
   public String getName(){
      return name;
   }
   
   public int getHandValue(){
      return hand.getHandValue();
   }
     
   public String toString(){
      return "\nPlayer: " + name + "\nPlayer Hand: " + hand.getHandValue();  
   }
   
   public void resetHand(){
      hand.reset();
   }
}

/*
creates a dealer objet based on the player object
holds methods that are used with the dealer object
*/
class Dealer {
   private ArrayList<Player> players;
   
   public Dealer() {
      players = new ArrayList<Player>();
   }
   
   public ArrayList<Player> getPlayers() {
      return players;
   }
   
   /*
   prints out each individual player
   */
   public void getEachPlayer() { 
      for (Player i : players) {
         System.out.println(i);
      }
   }  
   /*
   sees how many users will be playing and creaets
   the appropriate number of player objects
   */
   public void addPlayer() {
      Scanner scan = new Scanner(System.in);
      System.out.println("How many people are playing Blackjack? (1-5): ");
      int answer = scan.nextInt();
      while (answer <= 0 || answer > 7) {
         System.out.println("\nThe game must have at least one player and a maximum of 5.");
         System.out.println("How many people are playing? (enter a number 1-5): ");
         answer = scan.nextInt();
      }
         
      for (int i = 0; i < answer; i++) {
         Scanner name = new Scanner(System.in);
         System.out.println("\nWhat is player " + (i+1)+"'s " + " name?");
         String playerName = name.nextLine();
         System.out.println("Welcome to The Victorious 21 Casino, " + playerName);
         Player player = new Player(playerName);
         players.add(player);
      }
   }   
}

/*
creates the hand that will be used throughout the program
*/

class Hand{
   private ArrayList<Card> hand = new ArrayList<Card>();
   
   public void addCard(Card card){
      hand.add(card);
   }
   
   /*
   determines what the user/dealer's hand value is 
   @returns it as an int
   */
   public int getHandValue(){
      int total = 0;
      for (Card card : hand){ 
         if (card.getValue() < 11){
            total += card.getValue(); 
         }
      }
      for (Card cards : hand){
         if (cards.getValue() == 11){
            if (total + 11 > 21){
               total += 1;
            } else { 
               total += 11; 
            } 
         }
      }
      return total;
   } 
   
   /*
   @returns the strings that are in
   the user's hands
   */
   public String toString(){
      String cards = "";
      for (Card card : hand){
         cards += card.toString() + "\n";
      }
      return cards; 
   }
   
   public void reset(){
      hand = new ArrayList<Card>();
   }
}

/*
creates a deck of cards
*/
class Deck {
   private ArrayList<Card> deck;
   private Card cardDeck;
   
   /*
   creates a card deck
   assigns each card with a suit value
   shuffles the entire card deck
   */
   public Deck() {
      String[] temp1 = {"H", "S", "D", "C"};
      deck = new ArrayList<Card>();
      for (int i = 0; i < temp1.length; i++) {
         for (int j = 1; j < 14; j++) {
            cardDeck = new Card(j, temp1[i]);
            deck.add(cardDeck);
         }
      }
      shuffle();
   }
   
   /*
   prints every card within the deck
   */
   public void printDeck() {
      for (Card card : deck) {
         System.out.println(card);
      }
   }
   
   
   public Card deal() {
      return deck.remove(deck.size()-1);   
   }
   
   /*
   shuffles the entire deck
   */
   public void shuffle(){
      for ( int i = deck.size()-1; i > 0; i--){
        int randomIndex = (int)(Math.random()*(i));
        deck.set(i, deck.get(randomIndex));
        deck.set(randomIndex, deck.get(i));
      }
  }
  
  public ArrayList<Card> getDeck() {
      return deck;
   }
  
}

/*
creates a card
*/
class Card {
   private int rank;
   private String suit;
   private String fullSuit;
   private int value;
   
   /*
   constructor, assigns each card with
   a value and suit
   */
   public Card(int rank, String suit) {
      this.rank = rank;
      this.suit = suit;
      if (suit.equals("H")) {
         fullSuit = "Hearts";
      }
      if (suit.equals("D")) {
         fullSuit = "Diamonds";
      }
      if (suit.equals("C")) {
         fullSuit = "Clubs";
      }
      if (suit.equals("S")) {
         fullSuit = "Spades";
      }
      if (rank > 10) {
         value = 10;
      }
      if (rank < 10) {
         value = rank;
      }         
   }
   
   /*
   assigns cards with proper names (i.e.
   13 with King)
   @returns the card
   */
   public String toString() {
      String suitValue = "";
      if (rank == 1) {
         suitValue = "Ace";
      }
      if (rank == 11) {
         suitValue = "Jack";
      }
      if (rank == 12) {
         suitValue = "Queen";
      }
      if (rank == 13) {
         suitValue = "King";
      }
            
      if (rank > 10) {
         return suitValue + " of " + fullSuit + " (point value = " +
         getValue() + ")\n----------------------------------";
      }
      else {
         return rank + " of " + fullSuit + " (point value = " +
         getValue() + ")\n----------------------------------";
      }
   }
   
   /*
   gets the value of each card
   */
   public int getValue() {
      if (rank >= 10) {
         return 10;
      } else {
      return value;
      }
   }
}