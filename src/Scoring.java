import java.util.Arrays;

/**
 * Author:Manjul Shrestha
 * Created date: 11/01/2019
 * Modified dates: 11/07/2019
 * Class for scoring the game
 * Create a new object of Scoring, get the value of scoreBlack and scoreWhite for the scores
 */
public class Scoring {

    public int scoreBlack = 0, scoreWhite = 0;
    char[][] currentBoard;

    public void calculateScore(char[][] currentBoard) {
        this.currentBoard = currentBoard;
        int columns = currentBoard.length;
        int rows = currentBoard[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                try {
                    if (checkUp(i, j) == 'B') {
                        if (checkRight(i, j) == 'B') {
                            if (checkDown(i, j) == 'B') {
                                if (checkLeft(i, j) == 'B') {
                                    scoreBlack++;
                                } else {
                                    break;
                                }
                            }else if(checkDown(i, j) == ' '){

                            }else{
                                break;
                            }
                        }else if(checkRight(i, j) == ' '){

                        }else{
                            break;
                        }
                    } else if (checkUp(i, j) == 'W') {
                        if (checkRight(i, j) == 'W') {
                            if (checkDown(i, j) == 'W') {
                                if (checkLeft(i, j) == 'W') {
                                    scoreWhite++;
                                } else {
                                    break;
                                }
                            }else if(checkDown(i, j) == ' '){

                            }else{
                                break;
                            }
                        }else if(checkRight(i, j) == ' '){

                        }else{
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public char checkUp(int x, int y) {
        return currentBoard[x][y - 1];
    }

    public char checkDown(int x, int y) {
        return currentBoard[x][y + 1];
    }

    public char checkRight(int x, int y) {
        return currentBoard[x + 1][y];

    }

    public char checkLeft(int x, int y) {
        return currentBoard[x - 1][y];

    }

   public static void main(String[] args) {

        char[][] currentBoard = new char[19][19];
        for (char[] c : currentBoard) {
            Arrays.fill(c, ' ');
        }
        Scoring scoring = new Scoring();
        scoring.calculateScore(currentBoard);
        System.out.println("White:"+scoring.scoreWhite);
        System.out.println("Black:"+scoring.scoreBlack);
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
