package tetris;

import maingame.GameForm;

public class Tetris {

    private static GameForm gameForm;
    
    //Khởi tạo khung cửa sổ game
    public static void startGame(int gameLevel) {
        gameForm.setGameLevel(gameLevel);
        gameForm.setVisible(true);
        gameForm.startGameThread();
    }
    
    //Chuyển cửa sổ game đến level kế tiếp
    public static void nextLevel(int currentLevel) {
        gameForm.setGameLevel(currentLevel+1);
    }
    
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gameForm = new GameForm();
                startGame(1);
            }
        });
    }

}
