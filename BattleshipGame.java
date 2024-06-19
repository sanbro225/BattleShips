 import java.util.Scanner;

public class BattleshipGame {
    private static final int GRID_SIZE = 10;
    private static final char WATER = '.';
    private static final char SHIP = 'S';
    private static final char EMPTY = ' ';

    private char[][] grid = new char[GRID_SIZE][GRID_SIZE];
    private int[] shipSizes = {5, 4, 3, 3, 2}; //ship size

    public static void main(String[] args) {
        BattleshipGame game = new BattleshipGame();
        game.initializeGrid();
        game.placeShips();
        game.printGrid();
    }

    private void initializeGrid() { //make the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = WATER;
            }
        }
    }

    private void printGrid() { //print the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void placeShips() { //place ships
        Scanner scanner = new Scanner(System.in);

        for (int size : shipSizes) {
            System.out.println("Placing ship of size " + size);
            printGrid();
            int x = 0, y = 0;
            char direction = 'H'; // H for horizontal, V for vertical

            boolean validPlacement = false;
            while (!validPlacement) {
                System.out.println("Use WASD to move, R to rotate. Enter 'P' to place.");
                System.out.println("Current position: (" + x + ", " + y + "), Direction: " + (direction == 'H' ? "Horizontal" : "Vertical"));

                char command = scanner.next().toUpperCase().charAt(0);
                switch (command) {
                    case 'W': if (x > 0) x--; break;
                    case 'A': if (y > 0) y--; break;
                    case 'S': if (x < GRID_SIZE - 1) x++; break;
                    case 'D': if (y < GRID_SIZE - 1) y++; break;
                    case 'R': direction = (direction == 'H') ? 'V' : 'H'; break;
                    case 'P':
                        if (canPlaceShip(x, y, size, direction)) {
                            placeShip(x, y, size, direction);
                            validPlacement = true;
                        } else {
                            System.out.println("Invalid placement, try again.");
                        }
                        break;
                }
            }
        }
        scanner.close();
    }

    private boolean canPlaceShip(int x, int y, int size, char direction) {
        if (direction == 'H') {
            if (y + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[x][y + i] != WATER) return false;
            }
        } else {
            if (x + size > GRID_SIZE) return false;
            for (int i = 0; i < size; i++) {
                if (grid[x + i][y] != WATER) return false;
            }
        }
        return true;
    }

    private void placeShip(int x, int y, int size, char direction) {
        if (direction == 'H') {
            for (int i = 0; i < size; i++) {
                grid[x][y + i] = SHIP;
            }
        } else {
            for (int i = 0; i < size; i++) {
                grid[x + i][y] = SHIP;
            }
        }
    }
}