import java.util.Scanner;
import java.util.Random;

public class BattleshipGame1 {
    private static final int GRID_SIZE = 10;
    private static final char WATER = '~';
    private static final char SHIP = 'S';
    private static final char HIT = 'X';
    private static final char MISS = 'O';

    private char[][] playerGrid = new char[GRID_SIZE][GRID_SIZE];
    private char[][] computerGrid = new char[GRID_SIZE][GRID_SIZE];
    private boolean[][] computerShips = new boolean[GRID_SIZE][GRID_SIZE];

    private int[] shipSizes = {5, 4, 3, 3, 2}; // Example ship sizes (carrier, battleship, cruiser, submarine, destroyer)
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public static void main(String[] args) {
        BattleshipGame1 game = new BattleshipGame1();
        game.initializeGrid(game.playerGrid);
        game.initializeGrid(game.computerGrid);
        game.placeShips(game.playerGrid, "player");
        game.placeShips(game.computerGrid, "computer");
        game.playGame();
    }

    private void initializeGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = WATER;
            }
        }
    }

    private void printGrid(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void placeShips(char[][] grid, String owner) {
        for (int size : shipSizes) {
            if (owner.equals("player")) {
                System.out.println("Placing ship of size " + size);
                printGrid(grid);
            }

            int x = 0, y = 0;
            char direction = 'H'; // H for horizontal, V for vertical

            boolean validPlacement = false;
            while (!validPlacement) {
                if (owner.equals("player")) {
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
                            if (canPlaceShip(grid, x, y, size, direction)) {
                                placeShip(grid, x, y, size, direction);
                                validPlacement = true;
                            } else {
                                System.out.println("Invalid placement, try again.");
                            }
                            break;
                    }
                } else {
                    x = random.nextInt(GRID_SIZE);
                    y = random.nextInt(GRID_SIZE);
                    direction = random.nextBoolean() ? 'H' : 'V';
                    if (canPlaceShip(grid, x, y, size, direction)) {
                        placeShip(grid, x, y, size, direction);
                        validPlacement = true;
                    }
                }
            }
        }
    }

    private boolean canPlaceShip(char[][] grid, int x, int y, int size, char direction) {
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

    private void placeShip(char[][] grid, int x, int y, int size, char direction) {
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

    private void playGame() {
        boolean gameOver = false;
        while (!gameOver) {
            playerTurn();
            if (checkWin(computerGrid)) {
                System.out.println("You win!");
                gameOver = true;
                break;
            }
            computerTurn();
            if (checkWin(playerGrid)) {
                System.out.println("Computer wins!");
                gameOver = true;
            }
        }
    }

    private void playerTurn() {
        System.out.println("Your turn. Enter coordinates to fire (e.g., 3 4):");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        if (computerGrid[x][y] == SHIP) {
            System.out.println("Hit!");
            computerGrid[x][y] = HIT;
        } else {
            System.out.println("Miss!");
            computerGrid[x][y] = MISS;
        }
    }

    private void computerTurn() {
        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE);
        System.out.println("Computer fires at (" + x + ", " + y + ")");
        if (playerGrid[x][y] == SHIP) {
            System.out.println("Computer hits!");
            playerGrid[x][y] = HIT;
        } else {
            System.out.println("Computer misses!");
            playerGrid[x][y] = MISS;
        }
    }

    private boolean checkWin(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}