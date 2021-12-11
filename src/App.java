import java.util.Scanner;
import test.*;

public class App {
    public static void main(String[] args) throws Exception {
        Position fire0 = new Position(5, 25);
        Forest forest = new Forest(11, 51);

        forest.initFires(fire0);

        int i = 0;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Press enter to simulate step " + i);
            if(i != 0)
                forest.simulateFires();
            System.out.println(forest.toString());
            i++;
        }while(sc.nextLine() != null);
    }
}

