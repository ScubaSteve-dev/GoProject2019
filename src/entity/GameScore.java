package entity;

public class GameScore {
    private int id;
    private Player winningPlayer;
    private Player losingPlayer;
    private int winningPlayerScore;
    private int losingPlayerScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public Player getLosingPlayer() {
        return losingPlayer;
    }

    public void setLosingPlayer(Player losingPlayer) {
        this.losingPlayer = losingPlayer;
    }

    public int getWinningPlayerScore() {
        return winningPlayerScore;
    }

    public void setWinningPlayerScore(int winningPlayerScore) {
        this.winningPlayerScore = winningPlayerScore;
    }

    public int getLosingPlayerScore() {
        return losingPlayerScore;
    }

    public void setLosingPlayerScore(int losingPlayerScore) {
        this.losingPlayerScore = losingPlayerScore;
    }
}
