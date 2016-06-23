
public class State {
    private final Player[] players;
    private final Bullet[] bullets;

    public State(Player[] players, Bullet[] bullets) {
        this.players = players;
        this.bullets = bullets;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Bullet[] getBullets() {
        return bullets;
    }
}
