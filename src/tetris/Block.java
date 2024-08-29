package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

//**************************************
//Đây là lớp chứa các thông tin liên quan đến Block
//**************************************

public class Block {

    //Ta sẽ chứa hình dạng hiện thời của khối dưới dạng ma trận với 1 là có ô gạch con và 0 ngược lại
    private int[][] blockShape;
    
    //biến này sẽ chứa hình dạng của khối gạch khi xoay ở các góc 90 độ khác nhau, tối đa có 4 hình dạng khác nhau
    private int[][][] blockShapesList;
    
    //Biến này dùng để xác định vị trí hình dạng hiện tại trong danh sách hình dạng ở trên
    private int currentBlockShapeIndex;
    
    //Màu của khối gạch
    private Color blockColor;
    
    //Toạ độ x,y trên ma trận màu sắc ở dưới (cái này khác với toạ độ trên màn hình)
    private int x, y;
    
    //Tạo độ y ban đầu khi được tạo ra (được dùng để reset khối về vị trí ban đầu)
    private int initX,initY;
    
    //Ma trận màu sắc để xác định các khối hiện có trong nền
    private Color[][] backgroundColorMatrix;
    
    private LoaiGach loaiGach;

    //Phương thức khởi tạo
    public Block(int[][] blockShape, Color blockColor, int x, int y, LoaiGach loaigach) {
        this.blockShape = blockShape;
        this.blockColor = blockColor;
        this.x = this.initX = x;
        this.y = this.initY = y;
        generateAllBlockShapes();
        this.currentBlockShapeIndex = 0;
        this.loaiGach = loaigach;
    }
    
    //Ma trận màu sắc gốc ở lớp GameArea nên ta sẽ tham chiếu đến nó
    //Ma trận này sẽ được dùng để xác định va chạm
    public void setBackgroundColorMatrix(Color[][] backgroundColorMatrix) {
        this.backgroundColorMatrix = backgroundColorMatrix;
    }

    //Sinh ra tất cả các hình dạng xoay có thể có và lưu vào danh sách
    private void generateAllBlockShapes() {
        blockShapesList = new int[4][][];
        blockShapesList[0] = blockShape;
        for (int BlockShapeIndex = 1, BlockMatrixSize = getBlockMatrixSize(); BlockShapeIndex < 4; BlockShapeIndex++) {
            int[][] rotateBlockShape = new int[BlockMatrixSize][BlockMatrixSize];
            for (int i = 0; i < BlockMatrixSize; i++) {
                for (int j = 0; j < BlockMatrixSize; j++) {
                    rotateBlockShape[j][BlockMatrixSize - i - 1] = blockShapesList[BlockShapeIndex - 1][i][j];
                }
            }
            blockShapesList[BlockShapeIndex] = rotateBlockShape;
        }
    }

    
    //phương thức vẽ ô gạch
    public static void drawBlockCells(Graphics g, int x, int y, int blockCellSize, Color cellColor) {

        //Tạo tam giác trái của hình vuông
        int[] leftTriangleX = {x, x + blockCellSize / 2, x};
        int[] leftTriangleY = {y, y + blockCellSize / 2, y + blockCellSize};
        Polygon leftTriangle = new Polygon(leftTriangleX, leftTriangleY, 3);

        //Tạo tam giác trên của hình vuông
        int[] upTriangleX = {x, x + blockCellSize, x + blockCellSize / 2};
        int[] upTriangleY = {y, y, y + blockCellSize / 2};
        Polygon upTriangle = new Polygon(upTriangleX, upTriangleY, 3);

        //Tạo tam giác phải của hình vuông
        int[] rightTriangleX = {x + blockCellSize, x + blockCellSize, x + blockCellSize / 2};
        int[] rightTriangleY = {y, y + blockCellSize, y + blockCellSize / 2};
        Polygon rightTriangle = new Polygon(rightTriangleX, rightTriangleY, 3);

        //Tạo tam giác dưới của hình vuông
        int[] bottomTriangleX = {x, x + blockCellSize / 2, x + blockCellSize};
        int[] bottomTriangleY = {y + blockCellSize, y + blockCellSize / 2, y + blockCellSize};
        Polygon bottomTriangle = new Polygon(bottomTriangleX, bottomTriangleY, 3);

        //Tô màu viền
        //Tam giác trên và bên trái để màu sáng hơn và bên phải và dưới để màu tối hơn
        g.setColor(cellColor.brighter());
        g.fillPolygon(leftTriangle);
        g.setColor(cellColor.brighter().brighter().brighter());
        g.fillPolygon(upTriangle);
        g.setColor(cellColor.darker());
        g.fillPolygon(rightTriangle);
        g.setColor(cellColor.darker().darker());
        g.fillPolygon(bottomTriangle);

        //Tô màu ô vuông
        g.setColor(cellColor);
        
        //Vẽ hình vuông bé hơn bên trong
        int innerMargin = (int)(blockCellSize*0.24);
        g.fillRect(x + innerMargin/2, y + innerMargin/2, blockCellSize - innerMargin, blockCellSize - innerMargin);

    }

    //Kiểm tra xem có thể xuống được nữa không
    //Ý tưởng ở đây là thử xuống 1 ô rồi kiểm tra va chạm
    public boolean canMoveDown() {
        y++;
        if (checkCollision()) {
            y--;
            return false;
        }
        y--;
        return true;
    }
    
    //Xuống 1 ô nếu có thể
    public boolean moveDown() {
        if (canMoveDown()) {
            y++;
            return true;
        }
        return false;
    }

    //Kiểm tra va chạm
    private boolean checkCollision() {
        int BlockMatrixSize = getBlockMatrixSize();

        for (int i = 0; i < BlockMatrixSize; i++) {
            for (int j = 0; j < BlockMatrixSize; j++) {
                if (x + j < 0 || x + j > 9 || y + i > 19) {    //Kiểm tra nếu toạ độ viền khối gạch ra ngoài nhưng khối gạch có thể chưa ra ngoài
                    //Kiểm tra trái, phải, dưới
                    if (blockShape[i][j] != 0) {
                        return true;
                    }
                } else if (y + i <= 0) {   //Nếu toạ độ trục Y âm thì không tính là va chạm
                    continue;
                } else if (backgroundColorMatrix[y + i][x + j] != null && blockShape[i][j] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    //Ý tưởng tương tự với đi xuống
    public boolean moveLeft() {
        x--;
        if (checkCollision()) {
            x++;
            return false;
        }
        return true;
    }

    //Ý tưởng tương tự với đi xuống
    public boolean moveRight() {
        x++;
        if (checkCollision()) {
            x--;
            return false;
        }
        return true;
    }

    //Thả khối xuống
    public boolean drop() {
        while (moveDown());
        return true;
    }

    //Xoay khối nếu có thể
    //Ý tưởng tương tự như đi xuống, ta sẽ xoay khối và kiếm tra va chạm
    //nếu không có va chạm thì trả true, nếu có thì quay lại hình dạng trước và trả false
    public boolean rotate() {
        int previousBlockShapeIndex = currentBlockShapeIndex;
        if (currentBlockShapeIndex == 3) {
            currentBlockShapeIndex = 0;
        } else {
            currentBlockShapeIndex++;
        }
        blockShape = blockShapesList[currentBlockShapeIndex];

        if (checkCollision()) {

            //Kỹ thuật đẩy khối khi xoay khối gần tường
            x++;
            if (checkCollision() == false) {
                return true;
            }
            x -= 2;
            if (checkCollision() == false) {
                return true;
            }
            x++;

            blockShape = blockShapesList[previousBlockShapeIndex];
            currentBlockShapeIndex = previousBlockShapeIndex;
            return false;
        }
        return true;
    }

    public void resetPosition() {
        y = initY;
        x = initX;
    }
    
    public int[][] getCurrentBlockShape() {
        return blockShape;
    }
    
    public int[][] getInitialBlockShape() {
        return blockShapesList[0];
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public int getBlockMatrixSize() {
        return blockShape.length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGhostBlockY() {
        int currentY = y, GhostBlockY = 0;
        drop();
        GhostBlockY = y;
        y = currentY;
        return GhostBlockY;
    }

    public LoaiGach layLoaiGach() {
        return loaiGach;
    }

}