package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class ZBlock extends Block {

    public static final Color blockColor=new Color(179 , 51, 58);

    public ZBlock() {
                int[][] blockShape = {{1,1,0},
                            {0,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
