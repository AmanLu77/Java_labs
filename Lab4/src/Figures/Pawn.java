package Figures;

public class Pawn extends Figure
{
    private boolean isFirstStep = true;

    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure square[][]) {
        if (this.isFirstStep) {
            if ((Math.abs(row - row1) == 2 || Math.abs(row - row1) == 1) && col == col1) {
                this.isFirstStep = false;
                return true;
            }
        }
        else {
            if ((Math.abs(row - row1) == 1) && col == col1) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure nextSquare)
    {
        if (Math.abs(row - row1) == 1 && Math.abs(col - col1) == 1) {
            if (nextSquare.getColor() != this.getColor()) {
                return true;
            }
        }
        return false;
    }
}