package Effect;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.GameArea;

//**************************************
//Đây là lớp được dùng để tạo hiệu ứng khi khối gạch được thả
//**************************************
public class DropBlockEffect extends Thread {

    private GameArea gameArea;
    private volatile boolean isDrop;

    public DropBlockEffect(GameArea gameArea) {
        this.gameArea = gameArea;
        this.isDrop = false;
    }

    @Override
    public void run() {
        while (true) {

            if (!isDrop) {
                continue;
            }

            isDrop = false;

            Point f = gameArea.getLocation();

            int n = 3;
            while (n-- > 0) {
                f.y++;
                gameArea.setLocation(f);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }
            n = 3;
            while (n-- > 0) {
                f.y--;
                gameArea.setLocation(f);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep(120);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void setDrop() {
        isDrop = true;
    }
}
