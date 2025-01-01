package orgVfinal.Game;

public class Player {
    private final byte color;

    public Player(byte color) {
        this.color = color;
    }

    /**
     * Get the color of the player
     * @return the color of the player
     */
    public byte getColor() {
        return color;
    }
}
