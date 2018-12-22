package Util;

import Entity.FCB_List;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: JLFile-OS
 * @description: It's the contoller for FCB_List.
 * <p>
 * Created by Jalr on 2018/12/22.
 */
public class FCBController {
    public FCB_List ceateFolder(String folderName, String folderPath, FCB_List fatherFolder){
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
}
