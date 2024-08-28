package tetris;

import java.awt.Color;
import java.awt.Font;
import tetris.TetrisBlock.OBlock;
import tetris.TetrisBlock.LBlock;
import tetris.TetrisBlock.JBlock;
import tetris.TetrisBlock.GachNo;
import tetris.TetrisBlock.TBlock;
import tetris.TetrisBlock.SBlock;
import tetris.TetrisBlock.ZBlock;
import tetris.TetrisBlock.IBlock;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.JPanel;

//**************************************
//Lớp này sẽ được sử dụng để sinh ra các khối gạch khác nhau.
//Đồng thời sẽ vẽ các khối gạch tiếp theo lên trên bảng xem trước (BlockGeneratorPanel)
//**************************************

public class BlockGenerator extends JPanel {
    
    //Để lấy khối gạch ở đầu đồng thời cho khối gạch khác vào cuối thì ta sử dụng hàng đợi
    private Queue<Block> nextBlocksQueue;
    
    private Font font;
    
    private int blockCellSize;
    //Phương thức khởi tạo
    public BlockGenerator() {

        this.setBackground(Color.BLACK);
        nextBlocksQueue = new LinkedList<>();
        
        font = new Font("Calibri",Font.PLAIN,16);
        
        blockCellSize = (int) (this.getWidth() * 0.8) / 4;

    }
    
    //Trả về khối đầu tiên trong hàng đợi đồng thời sinh ra khối khác để thay thế
    public Block getNextBlock() {
        if (nextBlocksQueue.isEmpty()) {
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
        }
        nextBlocksQueue.add(this.generateRandomBlock());
        Block newBlock = nextBlocksQueue.poll();
        
        //Sau khi lấy ra thì ta vẽ lại màn hình các khối mới
        repaint();
        return newBlock;
    }

    //Phương thức này sẽ vẽ các khối gạch có trong hàng đợi lên màn hình
    private void drawQueueBlocks(Graphics g) {

        //Chuyển các khối gạch trong hàng đợi sang dạng mảng
        Block[] queueBlocks = nextBlocksQueue.toArray(new Block[0]);
        
        //****************************
        //Tính toán kích thước khối dựa trên kích thước của sổ vẽ hiện tại
        //Sau đó lặp qua danh sách các khối ở trên rồi vẽ từng khối
        //Mỗi khối gạch các nhau một khối gạch con (blockCell)
        //****************************
        
        int drawingAreaWidth = this.getWidth();
        int drawingAreaY = blockCellSize*2;

        for (int t = 0; t < 5; t++) {

            int blockMatrixSize = queueBlocks[t].getBlockMatrixSize();
            int[][] blockShape = queueBlocks[t].getCurrentBlockShape();
            int drawingAreaX = (drawingAreaWidth - blockCellSize * blockMatrixSize) / 2;

            //Do khối I có kích cỡ ma trận là 4 và 2 ô trên trống nên ta lùi lại 1 ô để các ô cách nhau đúng 1 khối gạch con
            if (blockMatrixSize == 4) {
                drawingAreaY -= blockCellSize;
            }

            for (int i = 0; i < blockMatrixSize; i++) {
                for (int j = 0; j < blockMatrixSize; j++) {
                    if (blockShape[i][j] != 0) {
                        
                        //Gọi phương thức trong lớp Block để vẽ ô gạch con (blockcell)
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, queueBlocks[t].getBlockColor());
                    }
                }
            }
            drawingAreaY += blockCellSize * blockMatrixSize;
        }
    }

    public void dayKhoiGachNoVaoHangDoi() {
        nextBlocksQueue.poll();
        nextBlocksQueue.add(this.taoKhoiGachNo());
    }

    
    //Phương thức sinh ra một khối gạch bất kì
    public Block generateRandomBlock() {
        Random randomBlock = new Random();
        switch (randomBlock.nextInt(7)) {
            case 0:
                return this.generateIBlock();
            case 1:
                return this.generateLBlock();
            case 2:
                return this.generateJBlock();
            case 3:
                return this.generateTBlock();
            case 4:
                return this.generateOBlock();
            case 5:
                return this.generateZBlock();
            case 6:
                return this.generateSBlock();

            default:
                throw new AssertionError();
        }
    }

    public Block generateTBlock() {
        return new TBlock();
    }

    public Block generateIBlock() {
        return new IBlock();
    }

    public Block generateLBlock() {
        return new LBlock();
    }

    public Block generateJBlock() {
        return new JBlock();
    }

    public Block generateZBlock() {
        return new ZBlock();
    }

    public Block generateSBlock() {
        return new SBlock();
    }

    public Block generateOBlock() {
        return new OBlock();
    }

    public Block taoKhoiGachNo() {
        return new GachNo();
    }

    
    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       
        drawQueueBlocks(g);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), blockCellSize);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("NEXT", 10,10 );
    }

    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    void updateAreaSize(Rectangle gameAreaSize) {
        
        int blockGeneratorHeight = (int)(gameAreaSize.height*0.68);
        int blockGeneratorWidth = (int)(gameAreaSize.width*0.4);
        
        int blockGeneratorX = gameAreaSize.x+gameAreaSize.width + (int)(blockGeneratorWidth*0.1);
        int blockGeneratorY = gameAreaSize.y;
        
        this.setBounds(blockGeneratorX, blockGeneratorY, blockGeneratorWidth, blockGeneratorHeight);
        
        blockCellSize = (int)(blockGeneratorWidth * 0.8) / 4;
    }
}
