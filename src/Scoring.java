import java.util.Arrays;

public class Scoring{

    public void calculateScore(char[][] currentBoard){
        for(char[] c:currentBoard ){

        }
    }

    public static void main(String[] args) {

        char[][] currentBoard = new char[19][19];
        for (char[] c : currentBoard){
            Arrays.fill(c, ' ');
        }
        Scoring scoring=new Scoring();
        scoring.calculateScore(currentBoard);
    }
}
