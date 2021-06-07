package practice.logiclayer;

import java.util.List;

public class CellController {

    private CellGrid cellGrid;

    public CellController(CellGrid cellGrid) {
        this.cellGrid = cellGrid;
    }

    public CellGrid getCellGrid() {
        return cellGrid;
    }

    public void updateCellStates() {
        boolean[][] oldStates = cellGrid.getCellStates();
        boolean[][] newStates = new boolean[cellGrid.getWidth()][cellGrid.getHeight()];
        for (int i = 0; i < cellGrid.getWidth(); i++) {
            for (int j = 0; j < cellGrid.getHeight(); j++) {
                int liveNeighbors = countingLiveNeighbors(cellGrid.getNeighborCells(i, j));
                newStates[i][j] = determiningCellState(liveNeighbors, oldStates[i][j]);
            }
        }
        cellGrid.setCellStates(newStates);
    }

    private boolean determiningCellState(int liveNeighbors, boolean oldState) {
        boolean newState = oldState;
        if (oldState) {
            if (liveNeighbors < 2 || liveNeighbors > 3) {
                newState = false;
            }
        } else if (liveNeighbors == 3) {
            newState = true;
        }
        return newState;
    }

    private int countingLiveNeighbors(List<Cell> neighbors) {
        return (int) neighbors.stream().filter(Cell::isAlive).count();
    }

}
