package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class TBlock extends Block {
    
    public static final Color blockColor=new Color(205 , 58, 191);

    public TBlock() {
        int[][] blockShape = {{0,1,0},
                            {1,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,LoaiGach.GACH_THUONG);
    }
    
}
