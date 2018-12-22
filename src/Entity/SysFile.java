package Entity;

import java.util.HashSet;

/**
 * @program: JLFile-OS
 * @description: Each time user create a file would create a file obj.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class SysFile {

    private String fileName;
    private String fileDateTime;
    private String fileType;
    private int shareCounter = 0;
    private String fileData;
    private int firstBlockNum = -1;
    private HashSet<String> owners;
    private String filePath;
    private FCB_List folderFather; //上一级目录
    private int fileSize = 0;                   //记录占用多少个磁盘块，一开始默认是 0

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDateTime() {
        return fileDateTime;
    }

    public void setFileDateTime(String fileDateTime) {
        this.fileDateTime = fileDateTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public int getShareCounter() {
        return shareCounter;
    }

    public void setShareCounter(int shareCounter) {
        this.shareCounter = shareCounter;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public HashSet<String> getOwners() {
        return owners;
    }

    public void setOwners(String ownersName) {
        this.owners.add(ownersName);
    }

    public int getFirstBlockNum() {
        return firstBlockNum;
    }

    public void setFirstBlockNum(int firstBlockNum) {
        this.firstBlockNum = firstBlockNum;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FCB_List getFolderFather() {
        return folderFather;
    }

    public void setFolderFather(FCB_List folderFather) {
        this.folderFather = folderFather;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
