package tetris;

import Effect.DropBlockEffect;
import tetris.TetrisBlock.GachNo;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

//**************************************
//Đây là lớp được dùng để vẽ màn hình game cũng như di chuyển gạch
//**************************************

public class GameArea extends JPanel {

    //biến này sẽ được sử dụng để lưu kích thước của một ô gạch (blockcell)
    private int blockCellsSize;

    //Khối gạch đang được điều khuyển
    private Block block;
    
    //Ma trận màu sắc để xác định các khối hiện có trong nền
    private Color[][] backgroundColorMatrix;
    
    //lớp sinh khối
    private BlockGenerator blockGenerator;
    
    //lớp hiệu ứng thả khối
    private DropBlockEffect dropBlockEffect;
    
    //biến kiểm tra game kết thúc chưa
    private boolean gameOver;

    //Phương thức khởi tạo
    public GameArea(JPanel jp) {

        //Do ta chỉ lấy hình dạng của panel nên ta sẽ không hiện nó lên màn hình
        jp.setVisible(false);

        //Lấy kích thước và màu sắc chuyền vào Khu vực nền
        this.setBounds(jp.getBounds());
        this.setBackground(jp.getBackground());

        //Lấy kích thước của ô vuông
        blockCellsSize = this.getWidth() / 10;

        //Khởi tạo các ô trong khu vực game là rỗng và ở ngoài là đã tồn tại
        this.backgroundColorMatrix = new Color[30][20];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                if (i >= 20 || j >= 10) {
                    backgroundColorMatrix[i][j] = Color.black;
                }
            }
        }

        this.gameOver = false;
        this.dropBlockEffect = new DropBlockEffect(this);
        this.dropBlockEffect.start();
    }

    //Tham chiếu đến lớp sinh khối
    public void addBlockGenerator(BlockGenerator blockGenerator) {
        this.blockGenerator = blockGenerator;
    }

    //Tạo khối gạch mới
    public void createNewBlock() {
        block = blockGenerator.getNextBlock();
        
        //Cho block tham chiếu đến ma trận màu sắc
        block.setBackgroundColorMatrix(backgroundColorMatrix);
    }

    //Khởi tạo game mới
    public void createNewGame() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                backgroundColorMatrix[i][j] = null;
            }
        }
        gameOver = false;
    }

    //Di chuyển gạch xuống dưới
    public boolean moveBlockDown() {
        if (block.moveDown()) {
            repaint();  //Sau khi di chuyển xuống thì "vẽ" lại màn hình
            return true;
        }
        //Nếu gạch không xuống được dưới nữa thì chuyển vào nền
        embedBlockIntoMatrix();
        return false;
    }

    //Di chuyển gạch sang trái
    public boolean moveBlockLeft() {
        if (block.moveLeft()) {
            repaint();  //Sau khi di chuyển trái thì "vẽ" lại màn hình
            return true;
        }
        return false;
    }
    //Di chuyển gạch sang phải
    public boolean moveBlockRight() {
        if (block.moveRight()) {
            repaint();  //Sau khi di chuyển phải thì "vẽ" lại màn hình
            return true;
        }
        return false;
    }

    //Thả khối gạch xuống
    public boolean dropBlock() {
        if (block.drop()) {
            dropBlockEffect.setDrop();  //Hiệu ứng thả gạch
            embedBlockIntoMatrix(); //Cho khối gạch vào ma trận nền
            repaint();
            return true;
        }
        return false;
    }

    //Xoay khối gạch
    public void rotateBlock() {
        if (block.rotate()) {
            repaint();
        }
    }

    //Kiểm tra xem có hàng nào đầy không và đẩy các khác phía trên xuống
    public void checkAndClearFullLines() {
        for (int i = 19; i > 0; i--) {
            boolean check = true;
            for (int j = 0; j < 10; j++) {
                if (backgroundColorMatrix[i][j] == null) {
                    check = false;
                    break;
                }
            }
            if (check) {
                dropBlocksAboveClearedLines(i);
                i++;
            }
        }
        repaint();
    }

    public boolean checkGameOver() {
        return gameOver;
    }

    
    //Đẩy các hàng ở phía trên hàng bị xoá xuống
    private void dropBlocksAboveClearedLines(int rowToRemove) {
        for (int i = rowToRemove; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                backgroundColorMatrix[i][j] = backgroundColorMatrix[i - 1][j];
            }
        }
        for (int j = 0; j < 10; j++) {
            backgroundColorMatrix[0][j] = null;
        }
    }

    //Chuyển khối gạch vào nền
    private void embedBlockIntoMatrix() {
        if (block.layLoaiGach() == LoaiGach.GACH_NO) {
            GachNo.noGach(backgroundColorMatrix, block.getX(), block.getY());
            repaint();
            return;
        }

        //Lấy các tham số của khối gạch
        int blockMatrixSize = block.getBlockMatrixSize();
        int[][] blockShape = block.getBlockShape();
        int blockX = block.getX();
        int blockY = block.getY();

        //Lặp qua ma trận hình dạng của khối gạch
        //Nếu là 1 thì cho màu của khối gạch vào ma trận nền tại ô đó
        for (int i = 0; i < blockMatrixSize; i++) {
            for (int j = 0; j < blockMatrixSize; j++) {
                if (blockShape[i][j] == 1) {
                    if (blockY + i < 0 || blockX + j < 0) {
                        gameOver = true;
                        return;
                    }
                    backgroundColorMatrix[blockY + i][blockX + j] = block.getBlockColor();
                }
            }
        }
    }

    //Vẽ khối gạch hiện tại đang được điều khuyển
    private void drawActiveBlock(Graphics g) {

        int BlockMatrixSize = block.getBlockMatrixSize();
        int[][] BlockShape = block.getBlockShape();
        int blockX = block.getX();
        int blockY = block.getY();
        int ghostBlockY = block.getGhostBlockY();
        Color ghostBlockColor = new Color(255, 225, 225, 50);

        for (int i = 0; i < BlockMatrixSize; i++) {
            for (int j = 0; j < BlockMatrixSize; j++) {
                if (BlockShape[i][j] != 0) {

                    //Vẽ khối gạch ảo
                    Block.drawBlockCells(g, (blockX + j) * blockCellsSize, (ghostBlockY + i) * blockCellsSize, blockCellsSize, ghostBlockColor);

                    //Vẽ khối gạch thật
                    Block.drawBlockCells(g, (blockX + j) * blockCellsSize, (blockY + i) * blockCellsSize, blockCellsSize, block.getBlockColor());

                }
            }
        }
    }

    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int Y = 0; Y < 20; Y++) {
            for (int X = 0; X < 10; X++) {

                //Vẽ khung nền
                g.setColor(new Color(32, 33, 30));
                g.drawRect(X * blockCellsSize, Y * blockCellsSize, blockCellsSize, blockCellsSize);

                if (backgroundColorMatrix[Y][X] != null) {

                    //Vẽ khối
                    Block.drawBlockCells(g, X * blockCellsSize, Y * blockCellsSize, blockCellsSize, backgroundColorMatrix[Y][X]);
                }
            }
        }

        drawActiveBlock(g);
    }
}
