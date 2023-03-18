//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lk.ijse.dep.service;

public interface Board {
    int NUM_OF_ROWS = 5;
    int NUM_OF_COLS = 6;

    BoardUI getBoardUI();

    int findNextAvailableSpot(int var1);

    boolean isLegalMove(int var1);

    boolean existLegalMoves();

    void updateMove(int var1, Piece var2);

    void updateMove(int var1, int var2, Piece var3);

    Winner findWinner();
}
