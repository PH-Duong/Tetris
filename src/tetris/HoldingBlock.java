package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class HoldingBlock extends JPanel {

    private int blockCellSize;
    private Block block;

    private boolean hasSwappedHoldingBlock;

    private Font font;

    public HoldingBlock() {
        this.setBackground(Color.black);
        this.hasSwappedHoldingBlock = false;

        block = null;

        font = new Font("Calibri", Font.PLAIN, 16);
    }

    //Phương thức lưu hoặc tráo đổi khối hiện đang điều khiển
    //Nếu chưa có khối nào lưu thì ta lưu khối hiện tai và trả true
    //Nếu không thì ta swap hai khối và trả false
    public Block holdOrSwapBlock(Block block) {
        if (this.block == null) {
            this.block = block;
            repaint();
            return null;
        } else {
            hasSwappedHoldingBlock = true;
            Block temp = this.block;
            this.block = block;
            repaint();
            return temp;
        }
    }

    public boolean hasSwappedHoldingBlock() {
        return hasSwappedHoldingBlock;
    }

    public void resetHasSwappedHoldingBlock() {
        hasSwappedHoldingBlock = false;
        repaint();
    }

    //Phương thức này sẽ vẽ khối gạch đang cầm lên màn hình
    //Ý tưởng tương tự với vẽ các khối gạch kế tiếp ở lớp BlockGenerator
    private void drawHoldingBlock(Graphics g) {

        if (block == null) {
            return;
        }

        int drawingAreaWidth = this.getWidth();
        int blockMatrixSize = block.getBlockMatrixSize();
        int[][] blockShape = block.getInitialBlockShape();

        int drawingAreaX = (drawingAreaWidth - blockCellSize * blockMatrixSize) / 2;
        int drawingAreaY = blockCellSize * 2;

        if (blockMatrixSize == 4) {
            drawingAreaY -= blockCellSize;
        }

        Color ghostBlockColor = new Color(255, 225, 225, 50);

        for (int i = 0; i < blockMatrixSize; i++) {
            for (int j = 0; j < blockMatrixSize; j++) {
                if (blockShape[i][j] != 0) {

                    if (hasSwappedHoldingBlock) {
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, ghostBlockColor);
                    } else {
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, block.getBlockColor());
                    }
                }
            }
        }
    }

    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    void updateAreaSize(Rectangle gameAreaSize) {

        int blockGeneratorHeight = (int) (gameAreaSize.height * 0.19);
        int blockGeneratorWidth = (int) (gameAreaSize.width * 0.4);

        int blockGeneratorX = gameAreaSize.x - blockGeneratorWidth - (int) (blockGeneratorWidth * 0.1);
        int blockGeneratorY = gameAreaSize.y;

        this.setBounds(blockGeneratorX, blockGeneratorY, blockGeneratorWidth, blockGeneratorHeight);

        blockCellSize = (int) (blockGeneratorWidth * 0.8) / 4;
    }

    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawHoldingBlock(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), blockCellSize);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("HOLD", 10, 10);
    }
}
