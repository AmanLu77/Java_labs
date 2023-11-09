package Figures;

public class Knight extends Figure //конь
{
    public Knight(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure square[][]) {
        if ((Math.abs(row - row1) == 1 && Math.abs(col - col1) == 2) || (Math.abs(row - row1) == 2 && Math.abs(col - col1) == 1)) {
            return true;
        }
        return false;
    }
}