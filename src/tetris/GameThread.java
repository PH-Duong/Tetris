package tetris;

//**************************************
//Đây là lớp được dùng để di chuyển khối gạch xuống sau khoản thời gian nhất định
//**************************************

public class GameThread extends Thread {

    private GameArea gameArea;
    private SpeedAndLevelSystem speedAndLevelSystem;
    private ScoreAndTimeSystem scoreAndTimeSystem;
    
    private volatile int gameSpeed;
    private volatile boolean isBlockAlive;

    public GameThread(GameArea gameArea, SpeedAndLevelSystem speedAndLevelSystem,ScoreAndTimeSystem scoreAndTimeSystem) {
        this.gameArea = gameArea;
        this.speedAndLevelSystem = speedAndLevelSystem;
        this.scoreAndTimeSystem = scoreAndTimeSystem;
    }
    
    //Kiểm tra xem Block có còn 'sống' không
    //Tránh trường hợp xảy ra các lỗi không mong muốn khi Thread đang
    //trong quá trình chạy hiệu ứng nháy thì người chơi nhấn xoay khối
    public boolean checkBlockAlive() {
        return isBlockAlive;
    }
    
    //Khởi tạo game mới
    public void newGame() {
        gameArea.newGame();
        speedAndLevelSystem.newGame();
        scoreAndTimeSystem.newGame();
        this.gameSpeed = 1000;
    }
    
    @Override
    public void run() {
        this.newGame();
        while (true) {
            
            isBlockAlive = false;
            //Đếm xem có hàng nào full không
            int FullLinesNum = gameArea.checkAndCountFullLines();
            
            //Nếu có
            if (FullLinesNum!=0) {
                
                //Tạo hiệu ứng nhấp nháy xoá hàng
                for (int i=0;i<50;i++) {
                    gameArea.startClearLinesEffect();
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException ex) {
                    }
                }
                
                //Sau khi nháy xong thì đẩy các khối bên trên xuống
                gameArea.dropBlocksAboveClearedLines();
                
                //TÍnh điểm và cập nhật tốc độ game nếu có
                gameSpeed = speedAndLevelSystem.addLinesAndCheckNextLevel(FullLinesNum);
                scoreAndTimeSystem.addPointsForClearLines(FullLinesNum,speedAndLevelSystem.getLevel());
            }
            
            if (gameArea.checkGameOver()) {
                this.newGame();
            }
            
            //Tạo khối mới
            gameArea.createNewBlock();
            isBlockAlive = true;
            
            //Tăng điểm khi khối mới được tạo
            scoreAndTimeSystem.addPointsForNewBlock(speedAndLevelSystem.getLevel());
            
            //Kiểm tra game kết thúc, nếu có thì làm mới game

            
            //Cho khối game đi xuống 1 ô nếu có thể.
            //Sau mỗi lần di chuyển 1 ô thì nghỉ gameSpeed thời gian
            while (gameArea.moveBlockDown()) {
                try {
                    Thread.sleep(gameSpeed);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}
