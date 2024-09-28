package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class JBlock extends Block {
    
    public static final Color BLOCK_COLOR=new Color(78, 61, 164);

    public JBlock() {
        int[][] blockShape = {{1,0,0},
                            {1,1,1},
                            {0,0,0}};
        super(blockShape,BLOCK_COLOR,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
