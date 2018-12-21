package Entity;

import java.util.HashMap;

/**
 * @program: JLFile-OS
 * @description: It apparently save the file block location in the which disk block
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class FAT  {

    private HashMap<Integer, Integer> file_allocate_table = new HashMap<>();

    public HashMap<Integer, Integer> getFile_allocate_table() {
        return file_allocate_table;
    }

    public void setKeyMap(Integer blockNum, Integer nextBlockNum) throws Exception{

        if(file_allocate_table.size() < ( BitMap.bitmap_height * BitMap.bitmap_width)){
            file_allocate_table.put(blockNum, nextBlockNum);
        }
        else{
            throw new Exception("FAT 表的表项已达到最大值，无法继续存盘");
        }
    }
}
