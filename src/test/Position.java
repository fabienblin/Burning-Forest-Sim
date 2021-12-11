package test;

/**
 * A Position is defined by an array [y, x]
 * It's use is realtive to grids [][]
 */
public class Position {
    private Integer[] position;

    /**
     * If negative values are given, it is set to 0.
     * @param y coordinate
     * @param x coordinate
     */
    public Position(Integer y, Integer x){
        if(y < 0){
            y = 0;
        }
        if(x < 0){
            x = 0;
        }
        this.position = new Integer[]{y, x};
    }

    /**
     * Accessor
     * @return an array of [y, x] coordiantes
     */
    public Integer[] get(){
        return this.position;
    }

    @Override
    public String toString() {
        return new String("["+this.position[0]+", "+this.position[1]+"]");
    }
}
