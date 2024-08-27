package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class OBlock extends Block {

    public static final Color blockColor=new Color(178, 152, 49);

    public OBlock() {
        int[][] blockShape = {{0,0,0,0},
                            {0,1,1,0},
                            {0,1,1,0},
                            {0,0,0,0}};
        super(blockShape,blockColor,4,-1,LoaiGach.GACH_THUONG);
    }
    
}
