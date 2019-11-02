import java.util.Arrays;

public class Scoring{

    public void calculateScore(char[][] currentBoard){
        for (int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(currentBoard[i][j] ==' '){
                    System.out.print("Blank     ");
                }else if(currentBoard[i][j] =='b'){
                    System.out.print("Black     ");
                }else{
                    System.out.print("White     ");
                }
            }
            System.out.println("\n");
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
