
/**
 * Science Project Java Code
 * @author Rohan, Rosemarie
 */
import java.util.*;

enum Player { 
    COMP, USER, NONE; 
    
    @Override public String toString() {
        if (this == USER) return "O";
        else if (this == COMP) return "X";
        else return " ";
    }
}

interface GameType {
    int getType();
    Player[] getBoard();
    Player getBoardAt(int i);
    int compMoves();
    int userMoves();
    int totalMoves();
}

abstract class Game implements GameType {
    
    protected final int type;
    protected Player[] board;
    protected boolean active = true;
    
    
    Game(int type) { 
        this.type = type; 
        board = new Player[type];
        for (int i = 0; i < type; i++)  {
            board[i] = Player.NONE;
        }
    }
    
    
    boolean isActive() { return active; }
    @Override public int getType() { return board.length; }
    @Override public Player[] getBoard() { return board; }
    @Override public Player getBoardAt(int i) { return board[i]; }
    
    void setBoardAt(int i, Player pos) { if (active) board[i] = pos; }
    void flipBoard() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == Player.COMP) board[i] = Player.USER;
            else if (board[i] == Player.USER) board[i] = Player.COMP;
            else board[i] = Player.NONE;
        }
    }
   
    void deActivate() { active = false; }
    
    @Override public int compMoves() {
        int compMoves = 0;
        for (Player p : board) if (p == Player.COMP) compMoves++;
        return compMoves;
    }
    @Override public int userMoves() {
        int userMoves = 0;
        for (Player p : board) if (p == Player.USER) userMoves++;
        return userMoves;
    }
    @Override public int totalMoves() {
        int totalMoves = 0;
        for (Player p : board) if (p != Player.NONE) totalMoves++;
        return totalMoves;
    }
    
    int getRandomMove() {
        int random = 0;
        try {
            random = new Random().nextInt(type - totalMoves());
        } catch (IllegalArgumentException exc) {}
        int position, count = 0;
        for (position = 0; position < type; position++) {
            if (board[position] == Player.NONE)
                if (count++ == random) break; // loop should break before ending
        }
        return position;
    }
    void printBoard() {
        for (Player p : board) System.out.print(p + "-");
        System.out.println();
    }
    /** if not active, getWinner() == Player.NONE */
    abstract Player getWinner();

}

class TicTacToe extends Game {
    
    TicTacToe() { super(9); }
    
    int[][] winGroups = {
        {0,1,2}, {3,4,5}, {6,7,8},
        {0,3,6}, {1,4,7}, {2,5,8},
        {0,4,8}, {2,4,6}
    };
    
    @Override public Player getWinner() {
        // the one method that depends on the specifications of the game
        // not preset
        // currently not set
        // who is the winner? or is the game not over?
        for(int[] winGroup : winGroups) {
            int count = 0;
            for(int pos : winGroup) {
                if (getBoardAt(pos) == Player.COMP) count++;
            }
            if (count == 3) return Player.COMP;
        }
        for(int[] winGroup : winGroups) {
            int count = 0;
            for(int pos : winGroup) {
                if (getBoardAt(pos) == Player.USER) count++;
            }
            if (count == 3) return Player.USER;
        }
        return Player.NONE; // this is to be returned if game = not over
    }
}
class BigTacToe extends Game {
    
    BigTacToe() { super(25); }
    
    /**int[][] winGroups = {
        {0,1,2,3,4}, {5,6,7,8,9}, {10,11,12,13,14}, {15,16,17,18,19}, {20,21,22,23,24},
        {0,5,10,15,20}, {1,6,11,16,21}, {2,7,12,17,22}, {3,8,13,18,21}, {4,9,14,19,24},
        {0,6,12,18,24}, {4,8,12,16,20}
    };*/
    int[][] winGroups = {
        {0,1,2,3}, {1,2,3,4}, {5,6,7,8}, {6,7,8,9}, {10,11,12,13}, {11,12,13,14},
        {15,16,17,18}, {16,17,18,19}, {20,21,22,23}, {21,22,23,24},
        {0,5,10,15}, {5,10,15,20}, {1,6,11,16}, {6,11,16,21}, {2,7,12,17},
        {7,12,17,22}, {3,8,13,18}, {8,13,18,21}, {4,9,14,19}, {9,14,19,24},
        {0,6,12,18}, {6,12,18,24}, {4,8,12,16}, {8,12,16,20}
    };
    
    @Override public Player getWinner() {
        // the one method that depends on the specifications of the game
        // not preset
        // currently not set
        // who is the winner? or is the game not over?
        for(int[] winGroup : winGroups) {
            int count = 0;
            for(int pos : winGroup) {
                if (getBoardAt(pos) == Player.COMP) count++;
            }
            if (count == 4) return Player.COMP;
        }
        for(int[] winGroup : winGroups) {
            int count = 0;
            for(int pos : winGroup) {
                if (getBoardAt(pos) == Player.USER) count++;
            }
            if (count == 4) return Player.USER;
        }
        return Player.NONE; // this is to be returned if game = not over
    }
}
class FinGame implements GameType {
    
    private Player[] board;
    private final int type;
    private final boolean isDraw;
    
    /** Game must be inactive and have a winner. */
    FinGame(Game game) throws Exception {
        if (game.isActive()) throw new Exception("Game not complete");
        else if (game.getWinner() ==  Player.USER) game.flipBoard();
        board = game.getBoard();
        type = board.length;
        if (game.getWinner() == Player.NONE) isDraw = true;
        else isDraw = false;
    }
    @Override public int getType() { return type; }
    @Override public Player[ ] getBoard() { return board; }
    @Override public Player getBoardAt(int i) { return board[i]; }
    
    boolean getIsDraw() { return isDraw; }

    @Override public int compMoves() {
        int compMoves = 0;
        for (Player p : board) if (p == Player.COMP) compMoves++;
        return compMoves;
    }

    @Override public int userMoves() {
        int userMoves = 0;
        for (Player p : board) if (p == Player.COMP) userMoves++;
        return userMoves;
    }
    @Override public int totalMoves() {
        int totalMoves = 0;
        for (Player p : board) if (p == Player.COMP) totalMoves++;
        return totalMoves;
    }
    boolean contains(FinGame game) throws Exception {
        if (game.getType() != type) throw new Exception("Contains method must have"
                + " games of same type");
        for (int i = 0; i < type; i++) {
            if ((game.getBoardAt(i) == Player.COMP) && 
                    (game.getBoardAt(i) != board[i])) return false;
        }
        return true;
    }
}

class Database {
    final List<FinGame> gameList = new LinkedList<>();
    // private final List<FinGame> compressedList = new LinkedList<>();
    private final int type;
    
    Database(int type) { this.type = type; }
    
    int getSize() { return gameList.size(); }
    
    void addFinGame(FinGame cgame) throws Exception {
        if (type != cgame.getType()) throw new Exception("Wrong game type!");
        if (cgame.getIsDraw()) return;
        boolean isNew = true;
        for (int i = 0; i < gameList.size(); i++) {
            if (gameList.get(i).contains(cgame)) {
                gameList.remove(i);
            }
            else if (cgame.contains(gameList.get(i))) isNew = false;
        }
        if (isNew) gameList.add(cgame);      
    }
    /** 
     * the main algorithm of the program... using the database and analyzing
     * it to make an intelligent move i.e. learn from the data
     * 
     * returns integer position of move, i.e. array index
     */
    int selectMove(Game game) {
        if (gameList.size() == 0) 
        {
            return game.getRandomMove();          
        } 

        double[] weights = new double[type];
        // OFFENSE CHECK
        for (int i = 0; i < gameList.size(); i++) {
            int totalNumber = 0;
            for (int j = 0; j < type; j++)
                if (gameList.get(i).getBoardAt(j) == Player.COMP)
                    if (game.getBoardAt(j) != Player.COMP) totalNumber++;
            for (int j = 0; j < type; j++)
                if (gameList.get(i).getBoardAt(j)== Player.COMP)
                    if (game.getBoardAt(j) == Player.NONE)
                        if (totalNumber == 1) return j;
                        else weights[j] += (double)1/totalNumber;
        }
       // DEFENSE CHECK
        for (int i = 0; i < gameList.size(); i++) {
            int totalOppNumber = 0;
            for (int j = 0; j < type; j++)
                if (gameList.get(i).getBoardAt(j) == Player.COMP)
                    if (game.getBoardAt(j) != Player.USER) totalOppNumber++;
            for (int j = 0; j < type; j++)
                if (gameList.get(i).getBoardAt(j) == Player.COMP)
                    if (game.getBoardAt(j) == Player.NONE)
                        if (totalOppNumber == 1) return j;
        }
        int bestIndex = 0;
        double maxValue = weights[0];
        for (int i = 1; i < type; i++) {
            if (game.getBoardAt(i) == Player.NONE) 
                if ((bestIndex == 0) && game.getBoardAt(0) != Player.NONE) {
                    bestIndex = i; maxValue = weights[i];
                } else if (weights[i] > maxValue) {
                    bestIndex = i; maxValue = weights[i];
                }
        }
        //for (double d : weights) System.out.print(d + "-");
        //System.out.println("ReachedBottom");
        return bestIndex;
    }
}

class GameManager {
    
    Database database;
    Game currentGame;
    int type;
    int i = 0;
    
    GameManager(int type) {
        database = new Database(type);
        this.type = type;
    }
    
    void activate(Game game) throws Exception {
        if (game.getType() != type) throw new Exception("wrong type for game manager");
        currentGame = game;
    }
    void makeUserMove(int position) {
        //System.out.println("User 'O' will now play in spot " + position);
        currentGame.setBoardAt(position, Player.USER);
        //currentGame.printBoard();
    }
    void makeCompMove() {
        int position = database.selectMove(currentGame);
        //System.out.println("Comp 'X' will now play in spot " + position);
        currentGame.setBoardAt(position, Player.COMP);
        //currentGame.printBoard();
        
    }
    void deActivate() {
        currentGame.deActivate();
        try {            
            if (i++%200 == 0) System.out.println(database.gameList.size());
            database.addFinGame(new FinGame(currentGame));
        } catch (Exception exc) {} 
    }
    Game getCurrentGame() { return currentGame; }
    
    Player[] getCurrentBoard() {
        return currentGame.getBoard();
    }
    Player getCurrentBoardAt(int i) {
        return currentGame.getBoardAt(i);
    }
}

class GameSimulator {
    
    Random rand = new Random();
    GameManager gm = new GameManager(25);
    
    int gameNumber = 0;
    final int nOG = 10000;
    Player[] winHistory = new Player[nOG];
    
    GameSimulator() {        
    }
    
    void startNewGame() throws Exception {
        BigTacToe ttt = new BigTacToe();
        gm.activate(ttt);
        
        int moveCount = 0;
        do {
            gm.makeUserMove(ttt.getRandomMove()); moveCount++;              
            if (ttt.getWinner() != Player.NONE) break;
        if (++moveCount > 25) break;
            gm.makeCompMove();        
        } while(ttt.getWinner() == Player.NONE);
        
        //ttt.printBoard();
        
        winHistory[gameNumber++] = ttt.getWinner();
        gm.deActivate();
    }
    void playGames() throws Exception {
        System.out.println("Start.");
        for(int i = 0; i < nOG; i++) {
            startNewGame();
        }
        System.out.println();
        for(Player p : winHistory) System.out.print(p);
        System.out.println("\nEnd.");
        
        //
        
        DataAnalyzer da = new DataAnalyzer(winHistory);
        da.printfunction(50);
        
        for (FinGame fg : gm.database.gameList) {
            for (Player p : fg.getBoard()) System.out.print(p + "-");
            System.out.println();
        }
        System.out.println(gm.database.getSize());
        
    }  
}

class DataAnalyzer {
    Player[] data;
    
    DataAnalyzer(Player[] winHistory) { data = winHistory; }
        
    void printfunction(int div) {
        float[] pointArray = new float[div];
        for (int i = 0; i < div; i++) {
            float points = 0;
            for (int j = 0; j < data.length/div; j++) {
                if (data[(data.length/div)*i + j] == Player.COMP) points++;
                else if (data[(data.length/div)*i + j] == Player.NONE) points += .5;
            }
            pointArray[i] = points;
            System.out.println(points + " ");
        }
    }
}


public class Synopsys2014 {
    
    public static void main(String[] args) throws Exception {   
        GameSimulator gs = new GameSimulator();
        gs.playGames();
    }    
}
