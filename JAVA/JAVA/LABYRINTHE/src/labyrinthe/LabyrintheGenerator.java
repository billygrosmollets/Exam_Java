package labyrinthe;

/*
 * MazeGenerator.java
 *
 * Created on September 24, 2008, 2:10 PM by Masoud Fallahpour
 *
 * This class generates a random maze
 *
 * The size of maze corresponds to the input array
 * parameter of method generateMaze
 * slightly modified on september 29 2022 by Stephane Ygorra to produce a StringArray
 */
import java.util.Stack;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class LabyrintheGenerator {

    private static class Pt2d {

        int i;
        int j;

        private Pt2d(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private static boolean peutEtreperce(int i, int j, String[][] s) {
        int[] dx = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] dy = new int[]{-1, 0, -1, -1, 0, 1, -1, 0, 1};
        String[] val = new String[]{".", ".", ".", "#", "#", "#", ".", ".", "."};
        boolean ok = true;
        // percage 
        for (int k = 0; k < dx.length; k++) {
            try {
                if (!s[i + dx[k]][j + dy[k]].equals(val[k])) {
                    ok = false;
                    k = dx.length;
                }
            } catch (Exception e) {
                ok = false;
                k = dx.length;
            }
        }
        if (ok) {
            return true;
        }

        for (int k = 0; k < dx.length; k++) {
            try {
                if (!s[i + dy[k]][j + dx[k]].equals(val[k])) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;

    }

    public static String[][] getLabyrintheAsStringArray(int rows, int cols) {
        String[][] s = LabyrintheGenerator.getLabyrintheSansTrousAsStringArray(rows, cols);
        ArrayList<Pt2d> pts = new ArrayList<Pt2d>();

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                if (peutEtreperce(i, j, s)) {
                    pts.add(new Pt2d(i, j));
                };
            }
        }
        // on perce au maximum 1/4 des points que peuvent l'etre
        Random r = new Random();
        for (int p = 0; p < pts.size() / 4; p++) {
            int k = r.nextInt(pts.size());
            int i = pts.get(k).i;
            int j = pts.get(k).j;
            if (peutEtreperce(i, j, s)) {
              s[i][j] = ".";
            }

        }
        return s;

    }

    private static String[][] getLabyrintheSansTrousAsStringArray(int rows, int cols) {
        // works only with odd number of rows ,cols

        if ((rows % 2 == 0) || (cols % 2 == 0)) {
            throw new LabyrintheGeneratorException(" This Generator works only for odd number of rows and cols");
        }

        String[][] array = new String[rows][cols];
        Stack< Point> stack;
        Random random;
        StringBuffer possible;
        Point location;
        final int ROWS;
        final int COLS;
        final int totalCells;
        int x;
        int y;
        int visitedCells;

        stack = new Stack< Point>();
        random = new Random();
        possible = new StringBuffer();
        location = new Point();
        ROWS = array.length;
        COLS = array[0].length;
        totalCells = (ROWS / 2) * (COLS / 2);
        x = 1 + (2 * random.nextInt((int) ROWS / 2));
        y = 1 + (2 * random.nextInt((int) COLS / 2));
        visitedCells = 1;

        // create a raw array contaiting only '#'s and '.'s
        createRawArray(array);

        // generate a random maze
        while (visitedCells < totalCells) {
            // clear StringBuffer "possible"
            possible.setLength(0);

            // check North neighbor of current cell
            if (y > 1) {
                if (checkWalls(x, y - 2, array) == true) {
                    possible.append('N');
                }
            }

            // check South neighbor of current cell            
            if (y < array[0].length - 2) {
                if (checkWalls(x, y + 2, array) == true) {
                    possible.append('S');
                }
            }

            // check East neighbor of current cell            
            if (x < array.length - 2) {
                if (checkWalls(x + 2, y, array) == true) {
                    possible.append('E');
                }
            }

            // check West neighbor of current cell            
            if (x > 1) {
                if (checkWalls(x - 2, y, array) == true) {
                    possible.append('W');
                }
            }

            // check whether there is at least one neighbor
            // with all walls intact
            if (possible.length() != 0) {
                // there is a neighbor

                // push the current location into stack
                stack.push(new Point(x, y));

                // select a random neighbor
                int direction = random.nextInt(possible.length());

                switch (possible.charAt(direction)) {
                    case 'N':
                        array[x][y - 1] = ".";
                        y = y - 2;
                        break;

                    case 'S':
                        array[x][y + 1] = ".";
                        y = y + 2;
                        break;

                    case 'E':
                        array[x + 1][y] = ".";
                        x = x + 2;
                        break;

                    case 'W':
                        array[x - 1][y] = ".";
                        x = x - 2;
                        break;

                } // end switch

                visitedCells++;
            } else {
                // there is no neighbor with all walls intact
                // so pop the most recent location entry off 
                // the stack  

                location = stack.pop();
                x = location.x;
                y = location.y;
            }

        } // end while

        // make exit point
        //array[1][0] = "+";
        array[array.length - 2][array[0].length - 1] = "+";
        return array;
    } // end method generateMaze

    // this method checks whether all the walls of input 
    // cell are intact
    private static boolean checkWalls(int x, int y, String[][] array) {
        if (array[x - 1][y] == "#") {
            if (array[x + 1][y] == "#") {
                if (array[x][y - 1] == "#") {
                    if (array[x][y + 1] == "#") {
                        return true;
                    }
                }
            }
        }

        // at least one wall is broken down
        // so return false
        return false;
    }

    // this method gets an empty array and creates
    // an array containing only '#' and '.' 
    private static void createRawArray(String[][] emptyArray) {
        int i, j;

        for (i = 0; i < emptyArray.length; i++) {
            for (j = 0; j < emptyArray[0].length; j++) {
                if ((i % 2) == 1 && (j % 2) == 1) {
                    emptyArray[i][j] = ".";
                } else {
                    emptyArray[i][j] = "#";
                }
            }
        }
    }
    public static void printLabyrinthe(String [][] lab){
        // affichage du labyrinthe , les passages sont reperes par des ., les murs par des #, les 2 issues par des +
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                System.out.print(lab[i][j]);
            }
            System.out.println();
        }
        
    }
    public static void main(String args[]) {
        // creation d'un nouveau labyrinthe sous forme de chaine de caracteres
        String[][] lab = getLabyrintheAsStringArray(15, 17);
        printLabyrinthe(lab);
    }

    private static class LabyrintheGeneratorException extends RuntimeException {

        public LabyrintheGeneratorException(String msg) {
            super(msg);
        }
    }
} // end class 
