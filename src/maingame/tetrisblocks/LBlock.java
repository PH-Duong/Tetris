package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class LBlock extends Block {
    
    public static final Color blockColor=new Color(180, 100, 50);

    public LBlock() {
        int[][] blockShape = {{0,0,1},
                            {1,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
