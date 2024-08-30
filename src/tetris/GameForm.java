package tetris;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

//**************************************
//Đây là lớp được dùng để quản lý cửa sổ game
//Lấy luồng điều khuyển từ bàn phím
//**************************************


public class GameForm extends javax.swing.JFrame implements KeyListener {

    private GameForm gameForm;

    private HoldingBlock storedBlock;
    private GameArea gameArea;
    private BlockGenerator blockGenerator;
    private SpeedAndLevelSystem speedAndLevelSystem;
    private ScoreAndTimeSystem scoreAndTimeSystem;
    
    //Luồng game
    private GameThread gameThread;

    //biến này sẽ chứa tốc độ điều khuyển
    private final int keyPressedDelay = 70;
    
    //Cho phép di chuyển hoặc không
    private boolean canMoveLeft, canMoveRight, canMoveDown, canRotate, canDrop, canStore;

     //Phương thức khởi tạo
    public GameForm() {
        initComponents();
        
        //Thêm ảnh vào Background của cửa sổ game
         JLabel jl = null;
        try {
            jl = new JLabel(new ImageIcon(ImageIO.read( new File("Test.jpg"))));
        } catch (IOException ex) {
            System.out.println("Lỗi tải ảnh");
        }
        this.setContentPane(jl);
        
        //Thêm tính năng vào cửa sổ
        this.setAlwaysOnTop(true);
        
        //Thêm chức năng di chuyển từ bàn phím
        addKeyListener(this);

        gameForm = this;
        
        gameArea = new GameArea();
        blockGenerator = new BlockGenerator();
        storedBlock = new HoldingBlock();
        speedAndLevelSystem = new SpeedAndLevelSystem();
        scoreAndTimeSystem = new ScoreAndTimeSystem();
        
        gameArea.addBlockGenerator(blockGenerator);
        gameArea.addStoredBlock(storedBlock);
        
        this.add(gameArea);
        this.add(blockGenerator);
        this.add(storedBlock);
        this.add(speedAndLevelSystem);
        this.add(scoreAndTimeSystem);
        
        //Khởi tạo luồng game
        startGameThread();

        this.canRotate = true;
        this.canDrop = true;
        this.canStore = true;
        
        //Lắng nghe thay đổi cửa sổ
        //Nếu có thay đổi thì cập nhật lại thông số cho các cửa sổ con bên trong
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameArea.updateAreaSize(gameForm.getBounds());
                blockGenerator.updateAreaSize(gameArea.getBounds());
                storedBlock.updateAreaSize(gameArea.getBounds());
                speedAndLevelSystem.updateAreaSize(gameArea.getBounds());
                scoreAndTimeSystem.updateAreaSize(gameArea.getBounds());
            }
            
        });
        
        //***********
        //Phần này sẽ kiểm tra luồng từ bàn phím sau mỗi (keyPressedDelay) thời gian
        //Và được lặp lại sau mỗi (keyPressedDelay) thời gian
        //***********
        
        Timer timer = new Timer(keyPressedDelay, event -> {
            moveBlock();
        });
        timer.setRepeats(true);
        timer.start();  // Bắt đầu đếm ngược
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NewGameButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris Project");
        setMinimumSize(new java.awt.Dimension(440, 480));

        NewGameButton.setText("New Game");
        NewGameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        NewGameButton.setFocusPainted(false);
        NewGameButton.setFocusable(false);
        NewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(NewGameButton)
                .addContainerGap(623, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(NewGameButton)
                .addContainerGap(452, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//Hành động sẽ được thực hiện khi ấn nút new game
    private void NewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameButtonActionPerformed
        gameArea.newGame();
        gameArea.createNewBlock();
    }//GEN-LAST:event_NewGameButtonActionPerformed

    //bắt đầu luồng game
    public void startGameThread() {
        gameThread = new GameThread(gameArea,speedAndLevelSystem,scoreAndTimeSystem);
        gameThread.start();
    }

    //********************************
    //Phía dưới liên quan đến sử lý luồng vào của bàn phím
    //Đối với xoay khối gạch và thả khối gạch thì chỉ được thực hiện 1 lần mỗi lần ấn
    //tức 1 lần ấn và thả chỉ tính 1 lần, còn sang trái,phải,xuống thì cho phép giữ
    //*******************************
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if (canRotate) {
                canRotate = false;
                gameArea.rotateBlockClockWise();
            }
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            canMoveLeft = true;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            canMoveRight = true;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            canMoveDown = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if (canDrop) {
                canDrop = false;
                gameArea.dropBlock();
                gameThread.interrupt();
            }
        }
        if (key == KeyEvent.VK_C) {
            if (canDrop) {
                canStore = false;
                if (gameArea.holdOrSwapBlock()) {
                    gameThread.interrupt();
                }
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            canRotate = true;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            canMoveLeft = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            canMoveRight = false;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            canMoveDown = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            canDrop = true;
        }
        if (key == KeyEvent.VK_C) {
            canStore = true;
        }
    }

    private void moveBlock() {
        if (canMoveLeft) {
            gameArea.moveBlockLeft();
        }
        if (canMoveRight) {
            gameArea.moveBlockRight();
        }
        if (canMoveDown) {
            gameArea.moveBlockDown();
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NewGameButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
