package 细胞游戏;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMap extends JFrame {
    private final int length = 600;//鐣岄潰灏哄
    private final int cell_long = 30;//缁嗚優灏哄
    private final int cell_num = length / cell_long;//涓�鍒楁垨涓�琛岀粏鑳炴暟閲�
    private JPanel backPanel, centerPanel, bottomPanel;
    private JButton[][] cells;//鎵�鏈夌粏鑳�
    private CellControl cellControl;//缁嗚優鎺у埗鍣�
    private int[][] site;//浣嶇疆
    private JButton start, stop, flash;
    private Thread myThread;//绾跨▼
    private boolean running, isPause;//绾跨▼杩愯鐘舵��

    //鏋勯�犲嚱鏁帮紝璧勬簮鐨勫垵濮嬪寲鍑嗗
    GameMap() {
        initCellControl();//娣诲姞缁嗚優鎺у埗鍣�
        addJPanel();//娣诲姞闈㈡澘
        creatCells();//鍒濆鍖栫粏鑳炵粍
        addControl();//娣诲姞鎺у埗鎸夐挳

        this.setTitle("鐢熷懡娓告垙");//鏍囬
        this.setSize(length, length);//璁剧疆鐣岄潰闀垮
        this.setLocationRelativeTo(null);//璁剧疆鐣岄潰浣嶇疆
        this.setResizable(true);
        this.setVisible(true);//鍙
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//鐣岄潰鍏抽棴鏃舵暣涓▼搴忎篃鍏抽棴
    }

    //娣诲姞闈㈡澘
    void addJPanel() {
        backPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(cell_num, cell_num,0,0));
        bottomPanel = new JPanel(new FlowLayout());
        this.setContentPane(backPanel);
        backPanel.add(centerPanel, "Center");
        backPanel.add(bottomPanel, "South");
    }

    //娣诲姞鎺у埗鎸夐挳
    void addControl() {
        start = new JButton("寮�濮嬬箒琛�");
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //--------------------------------------------------------
                myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        running = true;
                        isPause = false;
                        while(running&&!isPause){
                            cellControl.nextCells();//绻佽涓嬩竴浠�
                            updateColor();//鏇存柊棰滆壊
                            try {
                                myThread.sleep(500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            running = isOver();//鏄惁鍏ㄩ儴姝讳骸
                        }
                        if(!running){
                            JFrame jf = new JFrame("鎻愮ず");
                            jf.add(new JLabel("鎵�鏈夌粏鑳炲凡姝讳骸锛�"));
                            jf.setSize(100,100);
                            jf.setLocationRelativeTo(null);
                            jf.setVisible(true);
                        }
                    }
                });
                myThread.start();//寮�鍚嚎绋�
            }
        });

        stop = new JButton("鏆傚仠绻佽");
        stop.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //--------------------------------------------------------
                isPause = true;
            }
        });

        flash = new JButton("鍒锋柊");
        flash.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //--------------------------------------------------------
                cellControl.flash();//鍒锋柊
                updateColor();
            }
        });
        bottomPanel.add(start);
        bottomPanel.add(stop);
        bottomPanel.add(flash);
    }

    //鍒濆鍖栫粏鑳�
    void creatCells() {
        cells = new JButton[cell_num][cell_num];
        for (int i = 0; i < cell_num; i++) {
            for (int j = 0; j < cell_num; j++) {
                cells[i][j] = new JButton(); //鎸夐挳鍐呭缃┖浠ヨ〃绀虹粏鑳�
                cells[i][j].setBackground(Color.WHITE); //鍒濆鏃舵墍鏈夌粏鑳炲潎涓虹櫧鑹诧紝鍗虫浜＄姸鎬�
                cellListener(cells[i][j], i, j);
                centerPanel.add(cells[i][j]);
            }
        }
    }

    //鍒ゆ柇缁嗚優鏄惁宸插叏閮ㄦ浜�
    boolean isOver(){
        boolean flag = false;
        for(int i=0;i<cell_num;i++){
            for(int j=0;j<cell_num;j++){
                if(site[i][j] == 1) //鍙鏈変竴涓繕娲荤潃
                    flag = true;
            }
        }
        return flag;
    }

    //鍒濆鍖栫粏鑳炴帶鍒跺櫒
    void initCellControl(){
        cellControl = new CellControl(cell_num);
    }

    //缁欑粏鑳炴寜閽鍔犵洃鍚�
    void cellListener(JButton cell, int i, int j) {
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //cell.setBackground(Color.blue);
                cellControl.setSite(i, j);
                updateColor();
            }
        });
    }

    //鏇存柊棰滆壊
    void updateColor(){
        site = cellControl.update();
        for(int i=0;i<cell_num;i++){
            for(int j=0;j<cell_num;j++){
                if(site[i][j] == 1){
                    cells[i][j].setBackground(Color.blue);
                }
                else{
                    cells[i][j].setBackground(Color.white);
                }
            }
        }
    }
}
