package cribbage;

// Cribbage.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cribbage extends CardGame {
	static Cribbage cribbage;  // Provide access to singleton
	public static Cribbage getInstance(){
		return cribbage;
	}

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	public enum Rank {
		// Order of cards is tied to card images
		ACE(1,1), KING(13,10), QUEEN(12,10), JACK(11,10), TEN(10,10), NINE(9,9), EIGHT(8,8), SEVEN(7,7), SIX(6,6), FIVE(5,5), FOUR(4,4), THREE(3,3), TWO(2,2);
		public final int order;
		public final int value;
		Rank(int order, int value) {
			this.order = order;
			this.value = value;
		}
	}

	static int cardValue(Card c) { return ((Cribbage.Rank) c.getRank()).value; }

	/*
	Canonical String representations of Suit, Rank, Card, and Hand
	*/
	String canonical(Suit s) { return s.toString().substring(0, 1); }

	String canonical(Rank r) {
		switch (r) {
			case ACE:case KING:case QUEEN:case JACK:case TEN:
				return r.toString().substring(0, 1);
			default:
				return String.valueOf(r.value);
		}
	}

    String canonical(Card c) { return canonical((Rank) c.getRank()) + canonical((Suit) c.getSuit()); }

    String canonical(Hand h) {
		Hand h1 = new Hand(deck); // Clone to sort without changing the original hand
		for (Card C: h.getCardList()) h1.insert(C.getSuit(), C.getRank(), false);
		h1.sort(Hand.SortType.POINTPRIORITY, false);
		return "[" + h1.getCardList().stream().map(this::canonical).collect(Collectors.joining(",")) + "]";
    }

	class MyCardValues implements Deck.CardValues { // Need to generate a unique value for every card
		public int[] values(Enum suit) {  // Returns the value for each card in the suit
			return Stream.of(Rank.values()).mapToInt(r -> (((Rank) r).order-1)*(Suit.values().length)+suit.ordinal()).toArray();
		}
	}

	static Random random;

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

	static boolean ANIMATE;

	void transfer(Card c, Hand h) {
		if (ANIMATE) {
			c.transfer(h, true);
		} else {
			c.removeFromHand(true);
			h.insert(c, true);
		}
  }
  
  private void dealingOut(Hand pack, Hand[] hands) {
	  for (int i = 0; i < nStartCards; i++) {
		  for (int j=0; j < nPlayers; j++) {
			  Card dealt = randomCard(pack);
			  dealt.setVerso(false);  // Show the face
			  transfer(dealt, hands[j]);
		  }
	  }
  }

	static int SEED;

	public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }

  private final String version = "0.1";
  static public final int nPlayers = 2;
  public final int nStartCards = 6;
  public final int nDiscards = 2;
  private final int handWidth = 400;
  private final int cribWidth = 150;
  private final int segmentWidth = 180;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover", new MyCardValues());
  private final Location[] handLocations = {
			  new Location(360, 75),
			  new Location(360, 625)
	  };
  private final Location[] scoreLocations = {
			  new Location(590, 25),
			  new Location(590, 675)
	  };
  private final Location[] segmentLocations = {  // need at most three as 3x31=93 > 2x4x10=80
			new Location(150, 350),
			new Location(400, 350),
			new Location(650, 350)
	};
  private final Location starterLocation = new Location(50, 625);
  private final Location cribLocation = new Location(700, 625);
  private final Location seedLocation = new Location(5, 25);
  // private final TargetArea cribTarget = new TargetArea(cribLocation, CardOrientation.NORTH, 1, true);
  private final Actor[] scoreActors = {null, null}; //, null, null };
  private final Location textLocation = new Location(350, 450);
  private final Hand[] hands = new Hand[nPlayers];
  private Hand starter;
  private Hand crib;

  public static void setStatus(String string) { cribbage.setStatusText(string); }

static private final IPlayer[] players = new IPlayer[nPlayers];
private final int[] scores = new int[nPlayers];

final Font normalFont = new Font("Serif", Font.BOLD, 24);
final Font bigFont = new Font("Serif", Font.BOLD, 36);

private void initScore() {
	 for (int i = 0; i < nPlayers; i++) {
		 scores[i] = 0;
		 scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}

//#####################################
//SOME NEW FUNCTIONS + ATTRIBUTES BELOW

private int currentDealer;
private Card starterCard;
private final Hand[] handsCopy = new Hand[nPlayers];
//during play, cards are removed by player hands, hence this copy is required for use in the show

//function to get the current total score for a player
public int getScore(int playerNum){
	return scores[playerNum];
}
//function to update the player score
public void addScorePoints(int playerNum, int newPoints){
	scores[playerNum]+=newPoints;
	updateScore(playerNum); //update the score visualiser on screen
}
//function to create a new empty hand
public Hand makeHand(){return new Hand(deck);}
//function to clone a hand i.e. copy card values but have a new object reference (new object)
public Hand cloneHand(Hand originalHand){
	Hand clone = new Hand(deck);
	for (Card card : originalHand.getCardList()){
		clone.insert(card.clone(), false);
		//insert clone of card, don't draw on-screen
	}
	return clone;
}

//END OF NEW FUNCTIONS + ATTRIBUTES
//#################################

private void deal(Hand pack, Hand[] hands) {
	for (int i = 0; i < nPlayers; i++) {
		hands[i] = new Hand(deck);
		// players[i] = (1 == i ? new HumanPlayer() : new RandomPlayer());
		players[i].setId(i);
		players[i].startSegment(deck, hands[i]);
	}
	RowLayout[] layouts = new RowLayout[nPlayers];
	for (int i = 0; i < nPlayers; i++)
	{
		layouts[i] = new RowLayout(handLocations[i], handWidth);
		layouts[i].setRotationAngle(0);
		// layouts[i].setStepDelay(10);
		hands[i].setView(this, layouts[i]);
		hands[i].draw();
	}
	layouts[0].setStepDelay(0);

	dealingOut(pack, hands);
	for (int i = 0; i < nPlayers; i++) {
		hands[i].sort(Hand.SortType.POINTPRIORITY, true);
	}
	layouts[0].setStepDelay(0);
}

private void discardToCrib() {
	crib = new Hand(deck);
	RowLayout layout = new RowLayout(cribLocation, cribWidth);
	layout.setRotationAngle(0);
	crib.setView(this, layout);
	// crib.setTargetArea(cribTarget);
	crib.draw();
	int playerCtr = 0;
	for (IPlayer player: players) {
		Hand bothDiscards = new Hand(deck); //this exclusively serves purpose of passing to the Log class
		for (int i = 0; i < nDiscards; i++) {
			transfer(player.discard(), crib); //problem is here
			bothDiscards.insert(crib.getLast(), false);
		}
		bothDiscards.sort(Hand.SortType.POINTPRIORITY, false);
		try {
			Log.getInstance().discarded(playerCtr, bothDiscards);
		} catch (IOException e) {
			System.out.println("Discard log failed");
			e.printStackTrace();
		}
		crib.sort(Hand.SortType.POINTPRIORITY, true);
		playerCtr++;
	}
}

private void starter(Hand pack) {
	starter = new Hand(deck);  // if starter is a Jack, the dealer gets 2 points
	RowLayout layout = new RowLayout(starterLocation, 0);
	layout.setRotationAngle(0);
	starter.setView(this, layout);
	starter.draw();
	Card dealt = randomCard(pack);
	dealt.setVerso(false);
	transfer(dealt, starter);
	starterCard = dealt; //store the starter card so it can be eventually used for the show() methods
	try {
		Log.getInstance().starterCard(starterCard); //log the starter card that was played
		ScoreController.run(null, currentDealer, ScoreController.STRATEGY_START, starterCard);
			//pass stuff into controller which will check if it starter card was a jack. If yes, it would log that
	} catch (IOException e) {
		System.out.println("Log for starter rule failed");
		e.printStackTrace();
	}
}

public static int total(Hand hand) {
	int total = 0;
	for (Card c: hand.getCardList()) total += cardValue(c);
	return total;
}

class Segment {
		Hand segment;
		boolean go;
		int lastPlayer;
		boolean newSegment;

		void reset(final List<Hand> segments) {
			segment = new Hand(deck);
			segment.setView(Cribbage.this, new RowLayout(segmentLocations[segments.size()], segmentWidth));
			segment.draw();
			go = false;        // No-one has said "go" yet
			lastPlayer = -1;   // No-one has played a card yet in this segment
			newSegment = false;  // Not ready for new segment yet
		}
}

private void play() {
	final int thirtyone = 31;
	final int fifteen = 15;
	List<Hand> segments = new ArrayList<>();
	int currentPlayer = 0; // Player 1 is dealer
	currentDealer = 1;
	int turnCtr = 0;
	int mostRecentGo = -1;
	Segment s = new Segment();
	s.reset(segments);
	while (!(players[0].emptyHand() && players[1].emptyHand())) {
		// System.out.println("segments.size() = " + segments.size());
//		System.out.println("P" + currentPlayer + " hand = " + canonical(players[currentPlayer].hand));
		Card nextCard = players[currentPlayer].lay(thirtyone-total(s.segment));
		turnCtr++;
//		System.out.println(nextCard);
		if (nextCard == null) {
			if (s.go) {
				// Another "go" after previous one with no intervening cards
				// lastPlayer gets 1 point for a "go"
				scores[s.lastPlayer]+=1;
				try {
					mostRecentGo = turnCtr;
					Log.getInstance().scored(s.lastPlayer, scores[s.lastPlayer], 1, "go");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("'Go' score logging failed");
				}
				updateScore(s.lastPlayer); //update score display on screen
				s.newSegment = true;
			} else {
				// currentPlayer says "go"
				s.go = true;
//				System.out.println("current player says go");
			}
			currentPlayer = (currentPlayer+1) % 2;
		} else {
			s.lastPlayer = currentPlayer; // last Player to play a card in this segment
			transfer(nextCard, s.segment);
//			System.out.println("new P" + currentPlayer + " hand = " + canonical(players[currentPlayer].hand));
			try {
				Log.getInstance().played(currentPlayer, total(s.segment), nextCard);
				ScoreController.run(s.segment, currentPlayer, ScoreController.STRATEGY_PLAY, null);
			} catch (IOException e) {
				System.out.println("Log for played card failed");
				e.printStackTrace();
			}
			if (total(s.segment) == thirtyone) {
				// lastPlayer gets 2 points for a 31
				scores[s.lastPlayer]+=2;
				try {
					Log.getInstance().scored(s.lastPlayer, scores[s.lastPlayer], 2, "thirtyone");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("'Thirty One' score logging failed");
				}
				updateScore(s.lastPlayer); //update score display on screen
				s.newSegment = true;
				currentPlayer = (currentPlayer+1) % 2;
			} else {
				// if total(segment) == 15, lastPlayer gets 2 points for a 15
				if (total(s.segment) == fifteen) {
					scores[s.lastPlayer]+=2;
					try {
						Log.getInstance().scored(s.lastPlayer, scores[s.lastPlayer], 2, "fifteen");
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("'Fifteen' score logging failed");
					}
					updateScore(s.lastPlayer); //update score display on screen
				}
				if (!s.go) { // if it is "go" then same player gets another turn i.e. skip this 'if' statement
					currentPlayer = (currentPlayer+1) % 2;
				}
			}
		}
		if (s.newSegment) {
			segments.add(s.segment);
			s.reset(segments);
		}
	}

	if (mostRecentGo != turnCtr){ //sanity check that there's no double up of 'go' scoring
		//last card played always gets a 'go' point
		try {
			scores[s.lastPlayer]+=1;
			Log.getInstance().scored(s.lastPlayer, scores[s.lastPlayer], 1, "go");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("'Go' score logging failed");
		}
		updateScore(s.lastPlayer); //update score display on screen
	}
}

void showHandsCrib() throws IOException {
	//Hand hand, int playerNum, String choice, Card starterCard
	// score player 0 (non dealer)
	Log.getInstance().cardsShown(0, starterCard, handsCopy[0]);
	ScoreController.run(handsCopy[0], 0, ScoreController.STRATEGY_SHOW, starterCard);
	// score player 1 (dealer)
	Log.getInstance().cardsShown(1, starterCard, handsCopy[1]);
	ScoreController.run(handsCopy[1], 1, ScoreController.STRATEGY_SHOW, starterCard);
	// score crib (for dealer)
	Log.getInstance().cardsShown(currentDealer, starterCard, crib);
	ScoreController.run(crib, currentDealer, ScoreController.STRATEGY_SHOW, starterCard);
		//note since this is a single-round program, currentDealer always = 1
}

  public Cribbage()
  {
    super(850, 700, 30);
    cribbage = this;
    setTitle("Cribbage (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    initScore();

	  Hand pack = deck.toHand(false);
	  RowLayout layout = new RowLayout(starterLocation, 0);
	  layout.setRotationAngle(0);
	  pack.setView(this, layout);
	  pack.setVerso(true);
	  pack.draw();
	  addActor(new TextActor("Seed: " + SEED, Color.BLACK, bgColor, normalFont), seedLocation);

	  /* Play the round */
	  deal(pack, hands);
	  try {
		  Log.getInstance().dealtHand(hands[0], hands[1]);
	  } catch (IOException e) {
	  	  System.out.println("Logging for dealing failed");
	  	  e.printStackTrace();
	  }
	  discardToCrib();

	  handsCopy[0] = cloneHand(hands[0]);
	  handsCopy[1] = cloneHand(hands[1]);

	  starter(pack);
	  play();
	  try {
		  showHandsCrib();
	  } catch (IOException e) {
	  	  System.out.println("The show process including scoring and logging has failed");
		  e.printStackTrace();
	  }

	  addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText("Game over.");
    refresh();
  }

  public static void main(String[] args)
		  throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
		  	InstantiationException, IllegalAccessException {
	  /* Handle Properties */
	  // System.out.println("Working Directory = " + System.getProperty("user.dir"));
	  Properties cribbageProperties = new Properties();
	  // Default properties
	  cribbageProperties.setProperty("Animate", "true");
	  cribbageProperties.setProperty("Player0", "cribbage.RandomPlayer");
	  cribbageProperties.setProperty("Player1", "cribbage.HumanPlayer");

	  // Read properties
	  try (FileReader inStream = new FileReader("cribbage.properties")) {
		  cribbageProperties.load(inStream);
	  }

	  // Control Graphics
	  ANIMATE = Boolean.parseBoolean(cribbageProperties.getProperty("Animate"));

	  // Control Randomisation
	  /* Read the first argument and save it as a seed if it exists */
	  if (args.length > 0 ) { // Use arg seed - overrides property
		  SEED = Integer.parseInt(args[0]);
	  } else { // No arg
		  String seedProp = cribbageProperties.getProperty("Seed");  //Seed property
		  if (seedProp != null) { // Use property seed
			  SEED = Integer.parseInt(seedProp);
		  } else { // and no property
			  SEED = new Random().nextInt(); // so randomise
		  }
	  }
	  random = new Random(SEED);

	  // Control Player Types
	  Class<?> clazz;
	  clazz = Class.forName(cribbageProperties.getProperty("Player0"));
	  players[0] = (IPlayer) clazz.getConstructor().newInstance();
	  clazz = Class.forName(cribbageProperties.getProperty("Player1"));
	  players[1] = (IPlayer) clazz.getConstructor().newInstance();
	  // End properties

	  //log the start of the game setup
	  Log.getInstance().roundStart(SEED, cribbageProperties.getProperty("Player0"), cribbageProperties.getProperty("Player1"));
	  new Cribbage();
	  Log.getInstance().closeFile();
  }

}