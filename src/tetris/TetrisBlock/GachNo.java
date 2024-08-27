package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class GachNo extends Block {
    
    public GachNo() {
        int[][] hinhdang = {{1,1},
                            {1,1}};
        super(hinhdang,Color.RED,4,-1,LoaiGach.GACH_NO);
    }
    
    public static void noGach(Color[][] maTranNen, int x, int y) {
        int[][] khuVucNo = {{0,1,1,1,1,0},
                            {1,1,1,1,1,1},
                            {1,1,1,1,1,1},
                            {0,1,1,1,1,0},
                            {0,0,1,1,0,0}};
        x-=2;
        for (int i=0;i<5;i++) {
            for (int j=0;j<6;j++) {
                if (x+j>=0 && y+i>=0 && khuVucNo[i][j]!=0) {
                    maTranNen[y+i][x+j]=null;
                }
            }
        }
    }
}