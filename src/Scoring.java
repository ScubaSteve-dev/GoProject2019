import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Scoring{

    public int scoreBlack=0, scoreWhite=0;
    ArrayList<Character> traversedCharacter;
    char[][] currentBoard;

    public void calculateScore(char[][] currentBoard){
        this.currentBoard=currentBoard;
        int columns = currentBoard.length;
        int rows = currentBoard[0].length;
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){

            }
        }



    }

    public char checkUp(int x, int y){
        return currentBoard[x][y];
    }

    public char checkDown(int x, int y){
        return currentBoard[x][y];
    }

    public char checkRight(int x, int y){
        return currentBoard[x][y];

    }
    public char checkLeft(int x, int y){
        return currentBoard[x][y];

    }

    public static void main(String[] args) {

        char[][] currentBoard = new char[19][19];
        for (char[] c : currentBoard){
            Arrays.fill(c, ' ');
        }
        Scoring scoring=new Scoring();
        scoring.calculateScore(currentBoard);
    }

    public int getScoreBlack() {
        return scoreBlack;
    }

    public void setScoreBlack(int scoreBlack) {
        this.scoreBlack = scoreBlack;
    }

    public int getScoreWhite() {
        return scoreWhite;
    }

    public void setScoreWhite(int scoreWhite) {
        this.scoreWhite = scoreWhite;
    }
}
