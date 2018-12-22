package Entity;

/**
 * @program: JLFile-OS
 * @description: It's a map for managing free disk block.
 * <p>
 * Created by Jalr on 2018/12/21.
 */

//磁盘大小 1MB,  磁盘块数 1024 个, 磁盘每块大小 1Kb, 也就是1024 b, 而文件长度为 5 时，占用 1 个磁盘块
public class BitMap {

    private int disk_block_num = 1024;          //磁盘块数
    public static final int disk_block_size = 1024;            //每一块磁盘块大小 1 kb, 即 1024 b
    private int file_block_size = 5;                    //每个文件数据块可用存 5 个长度的文件，而一个文件数据块就是 1Kb
    public static final int bitmap_width = 32;
    public static final int bitmap_height = 32;
    private int[][] bitmap = new int[bitmap_width][bitmap_height];

    public BitMap() {
        initBitMap();
    }

    public int[][] getBitmap() {    //返回一个指定为 32 x 32 大小的二维数组
        return bitmap;
    }

    public int getDisk_block_num() {
        return disk_block_num;
    }

    public void initBitMap() {
        for (int i = 0; i < bitmap_width; i++) {
            for (int j = 0; j < bitmap_height; j++) {
                bitmap[i][j] = 0; //初始化为0代表可用，当被分配掉后，要标记1代表占用状态
            }
        }
    }

    public int getFile_block_size() {
        return file_block_size;
    }

    public void updateBitMap(int row, int column) throws Exception{
        if(this.bitmap[row][column] == 0){
            this.bitmap[row][column] = 1;
        }
        else if(this.bitmap[row][column] == 1){
            this.bitmap[row][column] = 0;
        }
        else
            throw new Exception("更新位示图失败，请检查");
    }
}
