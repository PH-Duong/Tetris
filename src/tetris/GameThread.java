package tetris;

//**************************************

import java.util.logging.Level;
import java.util.logging.Logger;

//Đây là lớp được dùng để di chuyển khối gạch xuống sau khoản thời gian nhất định
//**************************************

public class GameThread extends Thread {

    private GameArea gameArea;

    public GameThread(GameArea gameArea) {
        this.gameArea = gameArea;
    }

    @Override
    public void run() {
        while (true) {
            if (gameArea.checkAndClearFullLines()) {
                for (int i=0;i<10;i++) {
                    gameArea.startClearLinesEffect();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                    }
                }
                gameArea.dropBlocksAboveClearedLines();
            }
            gameArea.createNewBlock();
            if (gameArea.checkGameOver()) {
                gameArea.createNewGame();
            }
            while (gameArea.moveBlockDown()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}
