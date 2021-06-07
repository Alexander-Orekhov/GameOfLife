package practice.logiclayer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CellGrid {

    private int width;
    private int height;
    Cell[][] grid;

    public CellGrid(int width, int height, int cellQuantity) {
        this.width = width;
        this.height = height;
        generateRandomCells(cellQuantity);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCellStates(boolean[][] newStates) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].setAlive(newStates[i][j]);
            }
        }
    }

    public boolean[][] getCellStates() {
        boolean[][] states = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                states[i][j] = grid[i][j].isAlive();
            }
        }
        return states;
    }

    public List<Cell> getNeighborCells(int x, int y) {
        ArrayList<Cell> neighborCells = new ArrayList<>();
        int[] xPoses = calculatePos(x, width);
        int[] yPoses = calculatePos(y, height);
        neighborCells.add(grid[xPoses[0]][yPoses[0]]);
        neighborCells.add(grid[xPoses[0]][yPoses[1]]);
        neighborCells.add(grid[xPoses[0]][yPoses[2]]);
        neighborCells.add(grid[xPoses[1]][yPoses[0]]);
        neighborCells.add(grid[xPoses[1]][yPoses[2]]);
        neighborCells.add(grid[xPoses[2]][yPoses[0]]);
        neighborCells.add(grid[xPoses[2]][yPoses[1]]);
        neighborCells.add(grid[xPoses[2]][yPoses[2]]);
        return neighborCells;
    }

    private void generateRandomCells(int cellQuantity) {
        this.grid = new Cell[width][height];
        Random random = new SecureRandom();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = new Cell(random.nextBoolean());
            }
        }
        while (cellQuantity > 0) {
            sifting();
            cellQuantity--;
        }
    }

    private void sifting() {
        Random random = new SecureRandom();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean cellState = grid[i][j].isAlive();
                if (cellState && random.nextBoolean()) {
                    grid[i][j].setAlive(false);
                }
            }
        }
    }

    private int[] calculatePos(int pos, int vectorLength) {
        int[] positions = new int[3];
        positions[1] = pos;
        if (pos == 0) {
            positions[0] = (vectorLength - 1);
            positions[2] = pos + 1;
        } else if (pos == (vectorLength - 1)) {
            positions[0] = pos - 1;
        } else {
            positions[0] = pos - 1;
            positions[2] = pos + 1;
        }
        return positions;
    }
}
