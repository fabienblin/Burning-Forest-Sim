import java.util.Scanner;
import java.lang.System;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

import test.*;

public class App {
    private static double propagation = 0.0;
    private static String file = null;
    private static int h = 0;
    private static int l = 0;
    private static ArrayList<Position> fires = null;

    /**
     * Main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        App.parseArgs(args);

        Forest forest;

        if(App.file == null){
            forest = new Forest(App.propagation, App.h, App.l);
            forest.initFires(App.fires);
        }
        else{
            forest = App.loadFile(App.file);
        }


        int i = 0;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Press enter to simulate step " + i);
            if(i != 0){
                if(forest.simulateFires()){ // end simulation if fires stoped propagating
                    System.exit(0);
                }
            }
            System.out.println(forest.toString());
            i++;
        }while(sc.nextLine() != null);
    }

    /**
     * Parse input using regex and initialize settings
     * @param args from Main
     * @throws Exception
     */
    public static void parseArgs(String[] args) throws Exception{
        try {
            // first arg is the propagation rate
            double propagation = Double.parseDouble(args[0]);
            if(propagation < 0 || propagation > 1){
                throw new Exception("Propagation definition "+args[0]+" is invalid.");
            }
            App.propagation = propagation;

            // second arg is a forest file (maps/file.forest) or size ([5, 5])
            // if size is given, get fire positions from following args
            String map =  new String(args[1]);
            map = map.replaceAll(" ", "");

            boolean file = Pattern.matches(".*[a-zA-Z]+.forest$", map);
            boolean size = Pattern.matches("\\[[0-9]+,[0-9]+\\]", map);

            if(file){
                App.file = map;
            }
            else if(size){
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(map);

                m.find();
                App.h = Integer.parseInt(m.group());

                m.find();
                App.l = Integer.parseInt(m.group());

                // define fire positions
                App.fires = new ArrayList<Position>();

                for(int i = 2; i < args.length; i++){
                    map = args[i].replaceAll(" ", "");
                    if(Pattern.matches("\\[[0-9]+,[0-9]+\\]", map)){
                        Pattern posP = Pattern.compile("\\d+");
                        Matcher posM = posP.matcher(map);

                        posM.find();
                        int y = Integer.parseInt(posM.group());
                        
                        posM.find();
                        int x = Integer.parseInt(posM.group());

                        App.fires.add(new Position(y, x));
                    }
                    else{
                        throw new Exception("Position definition "+args[i]+" is invalid.");
                    }
                }
            }
            else{
                throw new Exception("Map definition "+args[1]+" is invalid.");
            }
        } catch (Exception e) {
            App.usage();
            throw e;
        }
    }

    /**
     * Usage
     */
    public static void usage(){
        System.out.println("USAGE:");
        System.out.println("java -jar test.jar (propagation rate) (map file)");
        System.out.println("java -jar test.jar (propagation rate) (map size) ...(fire init positions)");
        System.out.println("\t- propagation rate : a floating point value ranging between 0 and 1, a low value is a high propagation prbability ex.: 0.5");
        System.out.println("\t- map file : a file from the maps/ folder ex.: maps/empty.forest");
        System.out.println("\t- map size : size of initial map as [h, l] ex.: [100, 100]");
        System.out.println("\t- fire init positions : a list of starting positions for the fire as [y, x] (positions that are out of the map will not trigger a fire) ex.: [0, 0] [100, 100]");
    }

    public static Forest loadFile(String file) throws Exception{
        Forest forest;
        try {
            File forestfile = new File(file);
            Scanner sizeScanner = new Scanner(forestfile);

            int l = sizeScanner.nextLine().length();
            int h = 1;
            while (sizeScanner.hasNextLine()) {
                sizeScanner.nextLine();
                h++;
            }
            sizeScanner.close();

            App.l = l;
            App.h = h;

            forest = new Forest(App.propagation, h, l);

            Scanner forestScanner = new Scanner(forestfile);
            int y = 0;
            while (forestScanner.hasNextLine()) {
                String row = forestScanner.nextLine();
                for (int x = 0; x < row.length(); x++) {
                    forest.loadChar(y, x, row.charAt(x));
                }
                y++;
            }
            forestScanner.close();
        } catch (Exception e) {
            throw e;
        };
        return forest;
    }
}

