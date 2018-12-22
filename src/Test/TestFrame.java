package Test;

import Entity.FAT;
import Util.FileController;
import Util.SysController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TestFrame {
    public static void main(String[] args) {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
//        String time = sdf.format(date);
//        System.out.println(time);
//        controller.showBitMap();
//        controller.createFile("JALRHOME","HAHAHA");

        SysController sysController=new SysController();
        sysController.start();

    }
}
