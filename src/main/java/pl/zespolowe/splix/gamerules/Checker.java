package pl.zespolowe.splix.gamerules;

public class Checker {
    Player player;
    private int possition_x;
    private int possition_y;
    public int score=0;
    private Direction direction = Direction.EAST;

    Checker(Player p, int x, int y){
        player=p;
        possition_x = x;
        possition_y=y;
    }

    private enum Direction {
            WEST,
            EAST,
            NORTH,
            SOUTH
        }

        public void next_turn(){

        }

}
