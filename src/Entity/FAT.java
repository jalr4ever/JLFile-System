package Entity;

import java.util.HashMap;

import static Entity.BitMap.bitmap_height;
import static Entity.BitMap.bitmap_width;

/**
 * @program: JLFile-OS
 * @description: It apparently save the file block location in the which disk block
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class FAT {

    private HashMap<Integer, Integer> file_allocate_table = new HashMap<>();

    public HashMap<Integer, Integer> getFile_allocate_table() {
        return file_allocate_table;
    }

    public FAT() {
        initFileAllocateTable();
    }

    public void setKeyMap(Integer blockNum, Integer nextBlockNum) throws Exception {

        if (file_allocate_table.size() <= (bitmap_height * bitmap_width)) {
            file_allocate_table.put(blockNum, nextBlockNum);
        } else {
            System.out.println("-------------FAT 表异常时的大小：  "+file_allocate_table.size());
            throw new Exception("FAT 表的表项已达到最大值，无法继续存盘");
        }
    }

    public void initFileAllocateTable() {
        int disk_block_num = bitmap_height * bitmap_width;
        for (int i = 0; i < disk_block_num; i++) {
            file_allocate_table.put(new Integer(i), new Integer(-1));
        }
    }

    public void updateFileAllocateTable(Integer blockNum, Integer nextBlockNum) {
        file_allocate_table.put(new Integer(blockNum), new Integer(nextBlockNum));
    }

    public Integer searchNextBlock(Integer blockNum){
        Integer nextBlock = file_allocate_table.get(blockNum);
        return nextBlock;
    }
}
