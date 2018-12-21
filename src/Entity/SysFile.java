package Entity;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @program: JLFile-OS
 * @description: Each time user create a file would create a file obj.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class SysFile {

    private String filename;
    private String fileDateTime;
    private String fileType;
    private int shareCounter = 0;
    private String fileData;
    private HashSet<String> owners;
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

}
