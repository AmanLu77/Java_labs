import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
    private Figure  fields[][] = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);

    public char getColorGaming() {
        return colorGaming;
    }
    public void setColorGaming(char colorGaming) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'),new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'),new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
        };
    }

    public String getCell(int row, int col) {
        Figure figure = this.fields[row][col];
        if (figure == null) {
            return "    ";
        }
        return " "+figure.getColor()+figure.getName()+" ";
    }

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }
    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    public void move_figure(int row1, int col1, int row2, int col2 ) throws Error{
        Figure figure;

        try {
            figure =  this.fields[row1][col1];
        }
        catch (Exception err) {
            throw new Error("Это поле пусто");
        }

        if (figure.getColor() != getColorGaming()) {
            throw new Error("На этом поле чужая фигура");
        }

        if (figure.getClass().getSimpleName().equals("Pawn")) {
            Figure nextSquare;

            try {
                nextSquare = this.fields[row2][col2];
            } catch (IndexOutOfBoundsException err) {
                throw new Error("Выход за пределы доски");
            }

            if (nextSquare != null && figure.canAttack(row1, col1, row2, col2, nextSquare)) {
                this.figureAttack(row1, col1, row2, col2, figure);
                return;
            }
            else if (nextSquare != null) {
                throw new Error("Поле занято вашей фигурой");
            }
            else if (figure.canMove(row1, col1, row2, col2, fields)) {
                System.out.println("move");
                this.fields[row2][col2] = figure;
                this.fields[row1][col1] = null;
                return;
            }
            throw new Error("Сюда нельзя пойти этой фигурой");
        }

        if (figure.canMove(row1, col1, row2, col2, this.fields)) {
            Figure nextField = this.fields[row2][col2];

            if (figure.getClass().getSimpleName().equals("King") && this.isCheck(row2, col2, this.getColorGaming())) {
                throw new Error("Угроза королю");
            }

            if (nextField != null) {
                if (figure.canAttack(row1, col1, row2, col2, nextField)) {
                    this.figureAttack(row1, col1, row2, col2, figure);

                    char enemyKingColor = this.getColorGaming() == 'w' ? 'b' : 'w';
                    System.out.println(enemyKingColor);
                    int[] enemyKingPos = this.getKingPosition(enemyKingColor);

                    if (this.isCheck(enemyKingPos[0], enemyKingPos[1], enemyKingColor)) {
                        System.out.println("Врагу шах");

                        if (this.isMat(row2, col2, enemyKingPos[0], enemyKingPos[1], enemyKingColor))
                        {
                            System.out.println("Врагу мат. Игра завершена");
                        }
                    }
                    return;
                }
                throw new Error("Поле уже занято вашей фигурой");
            }

            System.out.println("move");
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;

            char enemyKingColor = this.getColorGaming() == 'w' ? 'b' : 'w';
            System.out.println(enemyKingColor);
            int[] enemyKingPos = this.getKingPosition(enemyKingColor);

            if (this.isCheck(enemyKingPos[0], enemyKingPos[1], enemyKingColor)) {
                System.out.println("Врагу шах");

                if (this.isMat(row2, col2, enemyKingPos[0], enemyKingPos[1], enemyKingColor)) {
                    System.out.println("Врагу мат. Игра завершена");
                }
            }
            return;
        }
        throw new Error("Сюда нельзя пойти этой фигурой");
    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for(int row = 7; row > -1; row--){
            System.out.print(row);
            for(int col = 0; col< 8; col++){
                System.out.print("|"+ getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }
        for(int col = 0; col < 8; col++){
            System.out.print("    "+col);
        }
    }

    public void figureAttack(int row1, int col1, int row2, int col2, Figure figure) {
        System.out.println("attack");
        switch (this.fields[row2][col2].getColor()){
            case 'w': this.takeWhite.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
            case 'b': this.takeBlack.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
        }
        this.fields[row2][col2] = figure;
        this.fields[row1][col1] = null;
    }

    public boolean isCheck(int kingRow, int kingCol, char kingColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];

                if (figure == null || figure.getColor() == kingColor) {
                    continue;
                }
                if (figure.getClass().getSimpleName().equals("Pawn") && (Math.abs(i - kingRow) == 1 && Math.abs(j - kingCol) == 1)) {
                    return true;
                }
                if (figure.canMove(i, j, kingRow, kingCol, fields)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMat(int row, int col, int kingRow, int kingCol, char kingColor) {
        // Есть ли пути отступления
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (kingRow + i >= 8 || kingRow + i < 0 || kingCol + j >= 8 || kingCol + j < 0) continue;
                if (i == 0 && j == 0) continue;

                if (this.fields[kingRow + i][kingCol + j] == null && !(this.isCheck(kingRow + i, kingCol + j, kingColor))) {
                    return false;
                }
            }
        }
        // Можно ли закрыться или взятьь нападающую фигуру
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];

                if (figure == null || figure.getColor() != kingColor) {
                    continue;
                }
                if (figure.getClass().getSimpleName().equals("King") && this.isCheck(row, col, kingColor)) {
                    continue;
                }
                if (figure.getClass().getSimpleName().equals("Pawn") && figure.canAttack(i, j, row, col, this.fields[row][col])) {
                    return false;
                }
                if (figure.canMove(i, j, row, col, this.fields) && figure.canAttack(i, j, row, col, this.fields[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] getKingPosition(char color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Figure figure = this.fields[i][j];
                if (figure != null && figure.getClass().getSimpleName().equals("King") && figure.getColor() == color) {
                    int[] kingPosition = {i, j};
                    return kingPosition;
                }
            }
        }
        return null;
    }
}