package 细胞游戏;

import javax.swing.*;

public class CellControl extends JButton {
    private int[][] site;//缁嗚優鐨勫潗鏍囦綅缃�
    private int cell_num;

    CellControl(int cell_num){
        this.cell_num = cell_num;
        this.site = new int[cell_num][cell_num];
        for(int i=0;i<cell_num;i++)
            for(int j=0;j<cell_num;j++)
                site[i][j]=0;
    }

    //鍒濆鍖�
    void setSite(int i, int j) {
        site[i][j]=1;
    }

    //鏍规嵁鍧愭爣瀵瑰簲鍊兼敼鍙橀鑹�
    int[][] update() {
        return site;
    }

    //鍒锋柊
    void flash(){
        for(int i=0;i<cell_num;i++)
            for(int j=0;j<cell_num;j++)
                site[i][j]=0;
    }

    //浜х敓涓嬩竴浠ｇ畻娉�
    void nextCells(){
        //鐢ㄦ潵閬嶅巻site鐨勮緟鍔╂暟缁�
        int[][] help = new int[cell_num + 2][cell_num + 2];
        for(int i=0;i<cell_num;i++){
            for(int j=0;j<cell_num;j++){
                help[i+1][j+1]=site[i][j];
            }
        }
        //鐢ㄦ潵瀛樺偍绻佽鍚庣殑缁嗚優鍒嗗竷鐨勬暟缁�
        int[][] newSite = new int[cell_num + 2][cell_num + 2];
        for (int i = 0; i < cell_num; i++)
            for (int j = 0; j < cell_num; j++)
                switch (getNeighborCount(help, i+1, j+1)) {
                    case 2:
                        newSite[i+1][j+1] = site[i][j];//缁嗚優鐘舵�佷繚鎸佷笉鍙�
                        break;
                    case 3:
                        newSite[i+1][j+1] = 1; // 缁嗚優娲荤潃
                        break;
                    default:
                        newSite[i+1][j+1] = 0; // 缁嗚優姝讳簡
                }
        for (int i = 0; i < cell_num; i++)
            for (int j = 0; j < cell_num; j++)
                site[i][j] = newSite[i+1][j+1];
    }

    //鑾峰彇缁嗚優鐨勯偦灞呮暟閲�
    private int getNeighborCount(int[][] help, int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                count += help[i][j]; //濡傛灉閭诲眳杩樻椿鐫�锛岄偦灞呮暟渚夸細+1
        count -= help[x][y]; // 鍑忓幓缁嗚優鏈韩
        return count;
    }
}
