package tetris;

import SoundGame.AudioPlayer;
import Soundgame.AudioBackground;
import gamemenu.MenuForm;
import java.awt.Rectangle;
import maingame.GameForm;

public class Tetris {

    private static GameForm gameForm;
    private static MenuForm menuForm;
    private static Rectangle currentBonds;
    private static AudioPlayer audio;
    private static AudioBackground audioBackground;

    //Khởi tạo khung cửa sổ game
    public static void startGame(int gameLevel) {
        currentBonds = menuForm.getBounds();
        gameForm.setGameLevel(gameLevel);
        gameForm.setBounds(currentBonds);
        gameForm.setVisible(true);
        gameForm.startGameThread();
        menuForm.setVisible(false);
    }

    //Chuyển cửa sổ game đến level kế tiếp
    public static void nextLevel(int currentLevel) {
        gameForm.setGameLevel(currentLevel + 1);
    }

    public static void showMenu() {
        menuForm.setVisible(true);
        gameForm.setVisible(false);
    }
    public static void playClear(){
        audio.playClearLine();
    }
    
    public static void playBlockdown(){
        audio.playBlockDown();
    }
    
    public static void playRotateblock(){
        audio.playRotateBlock();
    }
    
    public static void startBackGroundMusic() {
        audioBackground.start();
    }
    public static void stopBackGroundMusic() {
        audioBackground.stop();
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                audioBackground = new AudioBackground();
                currentBonds = new Rectangle(0, 0, 1280, 720);
                gameForm = new GameForm();
                menuForm = new MenuForm(currentBonds);
                audio = new AudioPlayer();
                showMenu();
                startBackGroundMusic();
            }
        });
    }

}
