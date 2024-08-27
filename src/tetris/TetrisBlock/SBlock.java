package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class SBlock extends Block {

    public static final Color blockColor=new Color(130 , 179, 49);

    public SBlock() {
        int[][] blockShape = {{1,1,0},
                            {0,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,LoaiGach.GACH_THUONG);
    }
    
}
