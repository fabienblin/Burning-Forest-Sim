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
    private static final char FIRE = '§';
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
     * @throws Exception for negative h or l
     */
    public Forest(Integer h, Integer l) throws Exception{
        if(h <= 0 || l <= 0){
            throw new Exception("Forest size must be at least [1, 1].");
        }
        this.grid = new char[h][l];
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
    public void initFires(Position... fires) throws Exception {
        for (Position fire: fires){
            if (!isOutOfBoundries(fire)){
                this.grid[fire.get()[0]][fire.get()[1]] = Forest.FIRE;
            }
        }
    }

    /**
     * Verify if the given position is valid in x and y for the forest
     * @param pos is the Posiotion to verify relative to the current grid/Forest
     * @return true if the position is not in the grid/Forest
     */
    private boolean isOutOfBoundries(Position pos){
        boolean isOut = (
            pos.get()[0] < 0 ||
            pos.get()[1] < 0 ||
            pos.get()[1] >= this.grid[0].length ||
            pos.get()[0] >= this.grid.length
            );
        if (isOut){
            System.out.println("Position out of boundries : " + pos.toString());
        }
        return isOut;
    }

    public void simulateFires() {
        int h = this.grid.length;
        int l = this.grid[0].length;

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

        // propagate fires
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

    private void propagate(Position pos) {
        if(!isOutOfBoundries(pos) && this.grid[pos.get()[0]][pos.get()[1]] == Forest.TREE){
            if(Math.random() >= this.propagation){
                this.grid[pos.get()[0]][pos.get()[1]] = Forest.FIRE;
            }
        }
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
