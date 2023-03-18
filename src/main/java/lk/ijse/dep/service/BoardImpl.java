//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.dep.service;

public class BoardImpl implements Board {
    private final Piece[][] pieces = new Piece[6][5];
    private final BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j < 5; ++j) {
                this.pieces[i][j] = Piece.EMPTY;
            }
        }

        this.boardUI = boardUI;
    }

    public BoardUI getBoardUI() {
        return this.boardUI;
    }

    public int findNextAvailableSpot(int col) {
        for(int i = 0; i < 5; ++i) {
            if (this.pieces[col][i] == Piece.EMPTY) {
                return i;
            }
        }

        return -1;
    }

    public boolean isLegalMove(int col) {
        return this.findNextAvailableSpot(col) != -1;
    }

    public boolean existLegalMoves() {
        for(int col = 0; col < 6; ++col) {
            if (this.isLegalMove(col)) {
                return true;
            }
        }

        return false;
    }

    public void updateMove(int col, Piece move) {
        this.pieces[col][this.findNextAvailableSpot(col)] = move;
    }

    public void updateMove(int col, int row, Piece move) {
        this.pieces[col][row] = move;
    }

    public Winner findWinner() {
        int count;
        int row;
        int col;
        for(row = 0; row < 6; ++row) {
            count = 1;
            col = this.findNextAvailableSpot(row);

            for(col=1;col < (col == -1 ? 5 : col); ++col) {
                if (this.pieces[col][row] == this.pieces[col][row - 1]) {
                    ++count;
                    if (count == 4) {
                        return new Winner(this.pieces[col][row], col, row - 3, col, row);
                    }
                } else {
                    count = 1;
                    if (col >= 2) {
                        break;
                    }
                }
            }
        }

        for(row = 0; row < 5; ++row) {
            count = 1;

            for(col = 1; col < 6; ++col) {
                if (this.pieces[col][row] == this.pieces[col - 1][row] && this.pieces[col][row] != Piece.EMPTY) {
                    ++count;
                    if (count == 4) {
                        return new Winner(this.pieces[col][row], col - 3, row, col, row);
                    }
                } else {
                    count = 1;
                    if (col >= 3) {
                        break;
                    }
                }
            }
        }

        return new Winner(Piece.EMPTY);
    }
}
