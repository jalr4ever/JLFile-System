package Entity;

import java.util.ArrayList;

/**
 * @program: JLFile-OS
 * @description: It's a set of fcb, the num of fcb is the same as the disk block.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class FCB_List {

    private String fcb_list_name;           //文件夹的名字

    private String fcb_list_type;             //文件夹的类型

    private ArrayList fcb_list;       //存放文件项


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
}
