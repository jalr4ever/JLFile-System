package Util;

import Entity.BitMap;
import Entity.FAT;
import Entity.FCB_List;
import Entity.SysFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


public class FCBController {
    FileController fileController = new FileController();

    public FCB_List ceateFolder(String folderName, String folderPath, FCB_List fatherFolder) {
        //0.创建当前的日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
        String currentTime = sdf.format(date);

        FCB_List newfolder = new FCB_List();
        newfolder.setFcb_list_name(folderName);
        newfolder.setFcb_list_type("普通文件夹");
        newfolder.setFatherFolder(fatherFolder);//新的文件夹要指明它的爸爸
        fatherFolder.addFile(newfolder);//爸爸也要指明它有新的孩子
        newfolder.setFolderPath(folderPath);
        newfolder.setFcbDateTime(currentTime);
        return newfolder;
    }

    //删除目录，如果目录下有文件夹，需要判断文件里是否有文件，有的话要一个个删除
    public void deleteFolder(String folderName, FCB_List fatherFolder, FAT fat_table, BitMap bitMap) {
        System.out.println("准备删除： " + folderName);
        //在当前目录下查找要删除的文件夹
        FCB_List _deleteFolder = fatherFolder.searchFCB_ListByName(folderName);

        for (int i = _deleteFolder.getFcb_list().size() - 1; i > -1; i--) {
            Object object = _deleteFolder.getFcb_list().get(i);
            try {
                SysFile file = (SysFile) object;
                System.out.println("要先删除： " + file.getFileName());
                fileController.deleteFile(file.getFileName(), _deleteFolder, fat_table, bitMap);
                System.out.println("删除成功： " + file.getFileName());
            } catch (Exception e) {
                FCB_List folder = (FCB_List) object;
                System.out.println("要先删除： " + folder.getFcb_list_name());
                deleteFolder(folder.getFcb_list_name(), folder.getFatherFolder(), fat_table, bitMap);
            }
        }

        fatherFolder.deleteFile(_deleteFolder);
        System.out.println("删除成功： " + _deleteFolder.getFcb_list_name());

    }

}
