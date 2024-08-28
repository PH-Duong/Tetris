package tetris;

//**************************************
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
            gameArea.checkAndClearFullLines();
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
