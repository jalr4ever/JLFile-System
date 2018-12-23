package Util;

import Entity.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static Entity.BitMap.bitmap_height;
import static Entity.BitMap.bitmap_width;
import static Entity.BitMap.disk_block_size;

public class FileController {



    public void createFile(String fileName, String fileData, String filePath, FCB_List fatherFolder, FAT fat_table, BitMap bitMap) {

        //0.创建当前的日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
        String currentTime = sdf.format(date);

        System.out.println("创建一个新的文件，请依次输入以下的信息然后回车！");


        //1.创建文件，并输入文件的信息
        SysFile newfile = new SysFile();
        System.out.print("文件名：");
        newfile.setFileName(fileName);
        System.out.println(fileName);
        newfile.setFileDateTime(currentTime);
        newfile.setFileType("普通文件");
//        newfile.setOwners(//这里是 输入Username，还没创建先不写);
        newfile.setFirstBlockNum(checkBitMap(bitMap));//这里是第一次调用 checkBitMap ，那么就是该文件的首块号
        newfile.setFilePath(filePath);
        newfile.setFolderFather(fatherFolder); //文件的父目录
        System.out.print("文件数据：");
        newfile.setFileData(fileData);
        System.out.println("创建中......");

        //2.给文件分配空闲磁盘块
        int fileLength = newfile.getFileData().length();
        int fileSize = fileLength / bitMap.getFile_block_size();
        if ((fileLength % bitMap.getFile_block_size()) != 0) {
            fileSize++;
        }

        newfile.setFileSize(fileSize*disk_block_size);

        //3.查找位示图的空闲块, 更新相应的位示图与FAT表
        while (fileSize > 0) {
            int availDiskNum = checkBitMap(bitMap);
            int row = availDiskNum / bitMap.getDisk_block_num();
            int column = availDiskNum % bitMap.getDisk_block_num();
            try {
                bitMap.updateBitMap(row, column);//更新位示图
            } catch (Exception e) {
                e.printStackTrace();
            }
            int nextAvailDiskNum = checkBitMap(bitMap);
            if (fileSize == 1) {
                fat_table.updateFileAllocateTable(availDiskNum, -1);//更新 FAT , 最后一个表示 -1
            } else {
                fat_table.updateFileAllocateTable(availDiskNum, nextAvailDiskNum);//更新 FAT , 用现在的号，链接到下一个号
            }
            fileSize--;
            /*----打印位示图测试一下---*/
            showBitMap(bitMap);
            System.out.println("\n-------\n");

            /*----打印FAT测试一下---*/
            showFAT(fat_table);
            System.out.println("\n-------\n");
        }

        //4.给文件分配默认的目录项
        fatherFolder.addFile(newfile);
        System.out.println("创建文件成功！");

    }

    public void deleteFile(String fileName, FCB_List fatherFolder, FAT fat_table, BitMap bitMap) {

        //1.找到当前目录下要删除的文件物理位置
        SysFile _deletefile = fatherFolder.searchFileByName(fileName);

        //2.回收该文件的盘块号，更新位示图

    }

    public void showBitMap(BitMap bitMap) {
        for (int i = 0; i < bitmap_width; i++) {
            for (int j = 0; j < bitmap_height; j++) {
                System.out.print(bitMap.getBitmap()[i][j] + "   \t");
            }
            System.out.println();
        }
    }

    public void showFAT(FAT fat_table) {
        System.out.println("物理块号\t" + "下一块号");
        Iterator iterator = fat_table.getFile_allocate_table().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object blockNum = entry.getKey();
            Object nextBlockNum = entry.getValue();
            System.out.println(blockNum.toString() + "\t\t\t" + nextBlockNum.toString());
        }
    }

    public int checkBitMap(BitMap bitMap) {
        for (int i = 0; i < bitmap_width; i++) {
            for (int j = 0; j < bitmap_height; j++) {
                if (bitMap.getBitmap()[i][j] == 0) {
                    return bitMap.getDisk_block_num() * i + j;
                }
            }
        }
        return -1; //代表没有可用的
    }

}
