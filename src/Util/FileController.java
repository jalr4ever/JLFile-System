package Util;

import Entity.BitMap;
import Entity.SysFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Entity.BitMap.bitmap_height;
import static Entity.BitMap.bitmap_width;

/**
 * @program: JLFile-OS
 * @description: Controller for file operation include create、delete、open、update.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class FileController {

    private static FileController fileController = new FileController();
    private static String currentPath = "假装C:/users/loubth";

    private FileController() {
    }

    public static FileController getFileController() {
        return fileController;
    }

    //创建并初始化位示图
    BitMap bitMap = new BitMap();


    public void start() {
        System.out.print(currentPath + ">");
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        System.out.println(cmd);
    }


    public void createFile() {
        //创建当前的日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
        String currentTime = sdf.format(date);

        System.out.println("创建一个新的文件，请依次输入以下的信息然后回车！");


        //输入文件的一些基本信息
        Scanner scan = new Scanner(System.in);
        SysFile newfile = new SysFile();
        System.out.print("文件名：");
        newfile.setFilename(scan.nextLine());
        System.out.println();
        newfile.setFileDateTime(currentTime);
        newfile.setFileType("普通文件");
//        newfile.setOwners(//这里是 输入Username，还没创建先不写);
        System.out.print("文件数据：");
        newfile.setFileData(scan.nextLine());

        System.out.println("创建中......");

        //给文件分配空闲磁盘块
        int fileLength = newfile.getFileData().length();
        int fileSize = fileLength / bitMap.getFile_block_size();
        if ((fileLength % bitMap.getFile_block_size()) != 0) {
            fileSize++;
        }

        //查找位示图的空闲块
        while (fileSize > 0) {
            int availDiskNum = checkBitMap();
            int row = availDiskNum / bitMap.getDisk_block_num();
            int column = availDiskNum % bitMap.getDisk_block_num();

            try {
                bitMap.updateBitMap(row, column);//更新位示图
            } catch (Exception e) {
                e.printStackTrace();
            }

            fileSize--;
            showBitMap();
            System.out.println("\n-------\n");
        }

        System.out.println("创建文件成功！");

    }

    public void showBitMap() {
        for (int i = 0; i < bitmap_width; i++) {
            for (int j = 0; j < bitmap_height; j++) {
                System.out.print(bitMap.getBitmap()[i][j] + "   \t");
            }
            System.out.println();
        }
    }

    public int checkBitMap() {
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
