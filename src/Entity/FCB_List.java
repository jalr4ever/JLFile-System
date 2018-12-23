package Entity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @program: JLFile-OS
 * @description: It's a set of fcb, the num of fcb is the same as the disk block.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class FCB_List {

    private String fcb_list_name;           //文件夹的名字

    private String fcb_list_type;             //文件夹的类型

    private ArrayList fcb_list = new ArrayList();       //存放文件项

    private String folderPath;

    private FCB_List fatherFolder;

    private String fcbDateTime;

    public FCB_List() {

    }

    public FCB_List(String fcb_list_name) {
        this.fcb_list_name = fcb_list_name;
    }

    public String getFcb_list_name() {
        return fcb_list_name;
    }

    public void setFcb_list_name(String fcb_list_name) {
        this.fcb_list_name = fcb_list_name;
    }

    public String getFcb_list_type() {
        return fcb_list_type;
    }

    public void setFcb_list_type(String fcb_list_type) {
        this.fcb_list_type = fcb_list_type;
    }

    public ArrayList getFcb_list() {
        return fcb_list;
    }

    public void setFcb_list(ArrayList fcb_list) {
        this.fcb_list = fcb_list;
    }


    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public FCB_List getFatherFolder() {
        return fatherFolder;
    }

    public void setFatherFolder(FCB_List fatherFolder) {
        this.fatherFolder = fatherFolder;
    }

    public String getFcbDateTime() {
        return fcbDateTime;
    }

    public void setFcbDateTime(String fcbDateTime) {
        this.fcbDateTime = fcbDateTime;
    }

    public void addFile(SysFile file) {
        this.fcb_list.add(file);
    }

    public void addFile(FCB_List folder) {
        this.fcb_list.add(folder);
    }

    public void deleteFile(SysFile file) {
        this.fcb_list.remove(file);
    }

    public void deleteFile(FCB_List folder) {
        this.fcb_list.remove(folder);
    }

    //通过名称寻找指定文件夹
    public FCB_List searchFCB_ListByName(String folderName) {
        FCB_List folder = null;
        Iterator iterator = fcb_list.iterator();
        while (iterator.hasNext()) {
            FCB_List tempFolder;
            try {
                tempFolder = (FCB_List) (iterator.next());
                if (tempFolder.fcb_list_name.equals(folderName)) {
                    folder = tempFolder;
                    break;
                }
            } catch (Exception e) {

            }
        }
        return folder;
    }

    //通过名称寻找指定文件
    public SysFile searchFileByName(String fileName) {
        SysFile file = null;
        Iterator iterator = fcb_list.iterator();
        while (iterator.hasNext()) {
            SysFile tempFile;
            try {
                tempFile = (SysFile) (iterator.next());
                if (tempFile.getFileName().equals(fileName)) {
                    file = tempFile;
                    break;
                }
            } catch (Exception e) {

            }
        }
        return file;
    }

}
