package tetris;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;

//**************************************
//Đây là lớp được dùng để quản lý cửa sổ game
//Lấy luồng điều khuyển từ bàn phím
//**************************************


public class GameForm extends javax.swing.JFrame implements KeyListener {

    private GameArea gameArea;
    
    private BlockGenerator blockGenerator;
    
    //Luồng game
    private GameThread gameThread;

    //biến này sẽ chứa tốc độ điều khuyển
    private final int keyPressedDelay = 70;
    
    //Cho phép di chuyển hoặc không
    private boolean canMoveLeft, canMoveRight, canMoveDown, canRotate, canDrop;

     //Phương thức khởi tạo
    public GameForm() {
        initComponents();

        this.getContentPane().setBackground(Color.GRAY);

        //Game luôn ở trên cửa sổ khác
        this.setAlwaysOnTop(true);
        addKeyListener(this);

        gameArea = new GameArea(GameAreaPanel);
        blockGenerator = new BlockGenerator(BlockGeneratorPanel);
        gameArea.addBlockGenerator(blockGenerator);

        this.add(gameArea);
        this.add(blockGenerator);

        //Khởi tạo luồng game
        startGameThread();

        this.canRotate = true;
        this.canDrop = true;

        
        //***********
        //Phần này sẽ kiểm tra luồng từ bàn phím sau mỗi (keyPressedDelay) thời gian
        //Và được lặp lại liên tục
        //***********
        
        Timer timer = new Timer(keyPressedDelay, event -> {
            diChuyenGach();
        });
        timer.setRepeats(true);
        timer.start();  // Bắt đầu đếm ngược
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GameAreaPanel = new javax.swing.JPanel();
        NewGameButton = new javax.swing.JButton();
        BlockGeneratorPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris Project");
        setResizable(false);

        GameAreaPanel.setBackground(new java.awt.Color(4, 5, 5));

        javax.swing.GroupLayout GameAreaPanelLayout = new javax.swing.GroupLayout(GameAreaPanel);
        GameAreaPanel.setLayout(GameAreaPanelLayout);
        GameAreaPanelLayout.setHorizontalGroup(
            GameAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        GameAreaPanelLayout.setVerticalGroup(
            GameAreaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        NewGameButton.setText("New Game");
        NewGameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        NewGameButton.setFocusPainted(false);
        NewGameButton.setFocusable(false);
        NewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameButtonActionPerformed(evt);
            }
        });

        BlockGeneratorPanel.setBackground(new java.awt.Color(4, 5, 5));

        javax.swing.GroupLayout BlockGeneratorPanelLayout = new javax.swing.GroupLayout(BlockGeneratorPanel);
        BlockGeneratorPanel.setLayout(BlockGeneratorPanelLayout);
        BlockGeneratorPanelLayout.setHorizontalGroup(
            BlockGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        BlockGeneratorPanelLayout.setVerticalGroup(
            BlockGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(NewGameButton)
                .addGap(113, 113, 113)
                .addComponent(GameAreaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BlockGeneratorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(NewGameButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(GameAreaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(BlockGeneratorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Hành động sẽ được thực hiện khi ấn nút new game
    private void NewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameButtonActionPerformed
        gameArea.createNewGame();
        gameArea.createNewBlock();
    }//GEN-LAST:event_NewGameButtonActionPerformed

    //bắt đầu luồng game
    public void startGameThread() {
        gameThread = new GameThread(gameArea);
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
                gameArea.rotateBlock();
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
    }

    private void diChuyenGach() {
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
    private javax.swing.JPanel BlockGeneratorPanel;
    private javax.swing.JPanel GameAreaPanel;
    private javax.swing.JButton NewGameButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
