package Test;

import Entity.FAT;
import Util.FileController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * @program: JLFile-OS
 * @description: For testing my module function.
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class TestFrame {
    public static void main(String[] args) {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
//        String time = sdf.format(date);
//        System.out.println(time);

        FileController controller = FileController.getFileController();
//        controller.showBitMap();
        controller.createFile();
    }
}
