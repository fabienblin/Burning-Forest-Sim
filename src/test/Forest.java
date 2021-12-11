package test;

import java.lang.Math;
import java.util.ArrayList;

/**
 * A Forest is a a grid [][] of chars
 */
public class Forest {
    /**
     * Using char type for memory optimisation
     */
    private char[][] grid;

    /**
     * Symbols
     */
    private static final char FIRE = '$';
    private static final char TREE = 'Y';
    private static final char ASHES = '~';

    /**
     * Fire propagation rate
     * Ranges from 0 to 1
     * A low number is a big propagation probability
     */
    private double propagation = 0.5;

    /**
     * @param h is heigh
     * @param l is length
     * @param propagation is the fire's propagation probability
     * @throws Exception for negative h or l
     */
    public Forest(double propagation, Integer h, Integer l) throws Exception{
        if(h <= 0 || l <= 0){
            throw new Exception("Forest size must be at least [1, 1].");
        }
        if(propagation < 0 || propagation > 1){
            throw new Exception("Propagation rate is invalid (0 <= prpagation <= 1), given : "+propagation+".");
        }
        this.grid = new char[h][l];
        this.propagation = propagation;
        this.init(h, l);
    }

    /**
     * Initialize grid/Forest
     * @param h height
     * @param l length
     */
    private void init(Integer h, Integer l){
        for(int i = 0; i < h; i++){
            for(int j = 0; j < l; j++){
                this.grid[i][j] = Forest.TREE;
            }
        }
    }

    /**
     * Initialize fire sting positions
     * @param fires is a variadic number of positions
     * @throws Exception if fire position is out of boundries
     */
    public void initFires(ArrayList<Position> fires) throws Exception {
        for (Position fire: fires){
            if (!isOutOfBoundries(fire, true)){
                this.grid[fire.get()[0]][fire.get()[1]] = Forest.FIRE;
            }
        }
    }

    /**
     * Verify if the given position is valid in x and y for the forest
     * @param pos is the Posiotion to verify relative to the current grid/Forest
     * @return true if the position is not in the grid/Forest
     */
    private boolean isOutOfBoundries(Position pos, boolean log){
        boolean isOut = (
            pos.get()[0] < 0 ||
            pos.get()[1] < 0 ||
            pos.get()[1] >= this.grid[0].length ||
            pos.get()[0] >= this.grid.length
            );
        if (log && isOut){
            System.out.println("Position out of boundries : " + pos.toString());
        }
        return isOut;
    }

    /**
     * Simulate fire propagation
     * @return true if fires are all extinguished
     */
    public boolean simulateFires() {
        int h = this.grid.length;
        int l = this.grid[0].length;
        boolean exit = false;

        // list fire positions
        ArrayList<Position> fires = new ArrayList<Position>();

        for(int i = 0; i < h; i++){
            for(int j = 0; j < l; j++){
                if(this.grid[i][j] == Forest.FIRE){

                    this.grid[i][j] = Forest.ASHES;

                    fires.add(new Position(i, j));
                }
            }
        }

        // tell program to exit or propagate fires
        if(fires.isEmpty()){
            exit = true;
        }
        else{
            fires.forEach((position) -> {
                Position up = new Position(position.get()[0] - 1, position.get()[1]);
                Position right = new Position(position.get()[0], position.get()[1] + 1);
                Position down = new Position(position.get()[0] + 1, position.get()[1]);
                Position left = new Position(position.get()[0], position.get()[1] - 1);
    
                this.propagate(up);
                this.propagate(right);
                this.propagate(down);
                this.propagate(left);
            });
        }

        return exit;
    }

    private void propagate(Position pos) {
        if(!isOutOfBoundries(pos, false) && this.grid[pos.get()[0]][pos.get()[1]] == Forest.TREE){
            if(Math.random() >= this.propagation){
                this.grid[pos.get()[0]][pos.get()[1]] = Forest.FIRE;
            }
        }
    }

    public void loadChar(int y, int x, char c){
        this.grid[y][x] = c;
    }

    @Override
    public String toString() {
        String buffer = new String();
        for (char[] row : grid) {
            buffer = buffer + new String(row) + "\n";
        }
        return buffer;
    }
}
