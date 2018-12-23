package Util;

import Entity.BitMap;
import Entity.FAT;
import Entity.FCB_List;
import Entity.SysFile;

import java.util.Iterator;
import java.util.Scanner;

/**
 * @program: JLFile-OS
 * @description: Control system command and the other function.
 * <p>
 * Created by Jalr on 2018/12/22.
 */
public class SysController {

    private FileController fileController = new FileController();

    private FCBController fcbController = new FCBController();

    public static String currentPath = "Users:";        //当前目录逻辑路径
    public static FCB_List _currentPath = new FCB_List("Users:");           //当前目录物理路径

    //磁盘共用的 FAT 分配表
    public static FAT file_allocate_table = new FAT();

    //磁盘共用的位示图
    public static BitMap bitMap = new BitMap();

    //根目录标记
    private FCB_List rootFolderMark;

    private Scanner scanner = new Scanner(System.in);

    public SysController() {
        _currentPath.setFolderPath("Users:");
        rootFolderMark = _currentPath;
    }

    public String getCurrentPath() {
        return this.currentPath;
    }

    public void setCurrentPath(String currentPath) {
        currentPath = currentPath;
    }

    //开始解析命令
    public void start() {

        String cmd;

        System.out.println("*********恭喜您成功打开文件系统，祝您操作愉快！！！*********");
        printBlank("输入 help 查看命令帮助信息", null);

        while (true) {
            System.out.print("\n\n" + SysController.currentPath + ">");
            cmd = scanner.nextLine().toLowerCase().trim();
            String[] param = cmd.split(" +");
            if (param[0].equals("help")) {
                processHelp(param);
            } else if (param[0].equals("exit") || param[0].equals("quit") || param[0].equals("esc")) {
                boolean isEnd = processExit(param);
                if (isEnd) {
                    break;
                }
            } else if (param[0].equals("sf")) {     //显示FAT表
                processShowFat(param);
            } else if (param[0].equals("sb")) {     //显示位视图
                processShowBitMap(param);
            } else if (param[0].equals("cf")) {     //创建文件
                processCreateFile(param);
            } else if (param[0].equals("of")) {     //打开文件
                processOpenFile(param);
            } else if (param[0].equals("mfn")) {     //更改文件名
                processModifyFileName(param);
            } else if (param[0].equals("mf")) {     //修改文件内容
                processModifyFile(param);
            }else if (param[0].equals("del")) {     //删除文件
                processDeleteFile(param);
            } else if (param[0].equals("cd")) {     //进入目标目录
                processChangeDir(param);
            } else if (param[0].equals("mdn")) {     //更改文件夹名称
                processModifyFolderName(param);
            } else if (param[0].equals("cdir")) {      //创建目录
                processCreateDir(param);
            } else if (param[0].equals("deldir")) {     //删除目录
                processDeleteDir(param);
            } else if (param[0].equals("ls")) {   //显示当前目录下的文件和文件夹
                processList(param);
            } else {
                processError(param);
            }
        }

    }


    //此方法可以使打印的说明格式良好（使所有说明对齐），注意在instruction存在的情况下command最好不要使用中文，否则影响效果
    public void printBlank(String command, String instruction) {      //打印空格
        //command   命令
        //instruction   说明
        StringBuilder commandSb = new StringBuilder(command);
        int commandLength = command.getBytes().length;
        int length = 40;  //期望的命令长度
        while (true) {
            if (commandLength < length) {
                for (int i = 0; i < length - commandLength; i++) {
                    commandSb.append(" ");
                }
                break;
            } else {
                length += 20;
            }
        }

        System.out.print("** " + commandSb);

        if (instruction != null && instruction.length() > 0) {
            System.out.println("-" + instruction);
        }
    }

    //显示fat表
    private void processShowFat(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("SF", "显示fat表");
            } else {
                printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
            }
        } else {
            fileController.showFAT(file_allocate_table);
        }
    }

    //显示位视图
    private void processShowBitMap(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("SB", "显示位视图");
            } else {
                printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
            }
        } else {
            fileController.showBitMap(bitMap);
        }
    }

    //显示文件夹下所有文件
    private void processList(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("LS", "显示该文件夹下所有文件夹和文件");
            } else {
                printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
            }
        } else if (param.length > 0) {
            Iterator iterator = _currentPath.getFcb_list().iterator();
            SysFile tempFile;
            FCB_List tempFolder;
            StringBuilder sbDir = new StringBuilder();
            StringBuilder sbFile = new StringBuilder();
            while (iterator.hasNext()) {
                Object obj = iterator.next();

                try {
                    tempFile = (SysFile) obj;
                    int fileLength = tempFile.getFileName().getBytes().length;
                    StringBuilder tempFileName = new StringBuilder(tempFile.getFileName());
                    for (int i = 0; i < 25 - fileLength; i++) {
                        tempFileName.append(" ");
                    }
                    sbFile.append("** "
                            + "文件名称:\t" + tempFileName + "\t\t"
                            + "创建日期: " + tempFile.getFileDateTime() + "\t\t"
                            + "大小: " + tempFile.getFileSize() * BitMap.disk_block_size + " b\n");
                } catch (Exception e) {
                    tempFolder = (FCB_List) obj;
                    int folderLength = tempFolder.getFcb_list_name().getBytes().length;
                    StringBuilder tempFolderName = new StringBuilder(tempFolder.getFcb_list_name());
                    for (int i = 0; i < 25 - folderLength; i++) {
                        tempFolderName.append(" ");
                    }
                    sbDir.append("** "
                            + "文件夹名:\t" + tempFolderName + "\t\t"
                            + "创建日期: " + tempFolder.getFcbDateTime() + "\t\t"
                            + "文件夹类型: " + tempFolder.getFcb_list_type() + "\n");
                }

            }
            System.out.println(sbDir);
            System.out.print(sbFile);
        } else {
            printBlank("HELP", "获取帮助信息");
        }
    }

    //更改目录
    private void processChangeDir(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("CD folderName", "进入名字为folderName的目录，..为父目录");
            } else {
                FCB_List targetDir = _currentPath.searchFCB_ListByName(param[1]);
                if (param[1].equals("..")) {
                    if (SysController._currentPath != rootFolderMark) {
                        SysController._currentPath = _currentPath.getFatherFolder();
                        SysController.currentPath = _currentPath.getFolderPath();
                    } else {
                        printBlank("无法再返回上一级，这里是根目录！", null);
                    }
                } else if (targetDir == null) {
                    printBlank("指定的目录不存在", null);
                } else {
                    SysController._currentPath = targetDir;
                    SysController.currentPath = targetDir.getFolderPath();
                }
            }
        } else {
            printBlank("CD需要一个参数作为想要进入的目录的名称", null);
        }
    }


    //创建文件夹
    private void processCreateDir(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("CDIR folderName", "创建名字为folderName(不超过25个字符)的文件夹");
            } else if (param[1].getBytes().length > 25) {
                printBlank("指定的文件夹名称超出规定长度，请更换名称", null);
            } else {
                if (_currentPath.searchFCB_ListByName(param[1]) == null) {
                    fcbController.ceateFolder(param[1], currentPath + "/" + param[1], SysController._currentPath);
                } else {
                    printBlank("该名称已被占用，请更换名称重新创建文件夹", null);
                }
            }
        } else {
            printBlank("CDIR需要一个参数作为文件夹名称", null);
        }
    }

    //修改文件夹名称
    private void processModifyFolderName(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("MDN folderName", "修改名字为folderName的文件夹的名称");
            } else {
                FCB_List folder=_currentPath.searchFCB_ListByName(param[1]);
                if (folder != null) {
                    System.out.println("请输入新文件夹名称：");
                    String newFolderName = scanner.nextLine();
                    fcbController.updateFolderName(folder.getFcb_list_name(),folder.getFatherFolder(),newFolderName);
                } else {
                    printBlank("指定名字的文件夹不存在，请检查文件夹名称是否输入正确", null);
                }
            }
        } else {
            printBlank("MDN需要一个文件夹名称参数指定文件夹", null);
        }

    }

    //删除文件夹
    private void processDeleteDir(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("DELDIR folderName", "删除名字为folderName的文件夹");
            } else {
                if (_currentPath.searchFCB_ListByName(param[1]) != null) {
                    String folderName = param[1];
                    fcbController.deleteFolder(folderName, _currentPath, file_allocate_table, bitMap);

                } else {
                    printBlank("该文件夹下没有您指定的文件夹，请检查文件夹名是否输入正确", null);
                }
            }
        } else {
            printBlank("DEL需要一个参数指定要删除的文件名", null);
        }
    }

    //创建文件
    private void processCreateFile(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("CF fileName", "创建名字为fileName(不超过25个字符)的文本文件，并等待输入内容,输入:end结束内容");
            } else if (param[1].getBytes().length > 25) {
                printBlank("指定的文件名称超出规定长度，请更换名称", null);
            } else {
                if (_currentPath.searchFileByName(param[1]) == null) {
                    System.out.println("请开始输入文件内容,输入:end结束内容");
                    StringBuilder sb = new StringBuilder();
                    String tempLine = "";
                    do {
                        sb.append(tempLine + "\n");
                        tempLine = scanner.nextLine();
                    } while (!tempLine.trim().equalsIgnoreCase(":end"));
                    fileController.createFile(param[1], sb.toString(), currentPath, _currentPath, file_allocate_table, bitMap);
                } else {
                    printBlank("该名称已被占用，请更换名称重新创建文件", null);
                }
            }
        } else {
            printBlank("CF需要一个参数作为文件名", null);
        }

    }


    //打开文件
    private void processOpenFile(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("OF fileName", "打开名字为fileName的文本文件");
            } else {
                SysFile file=_currentPath.searchFileByName(param[1]);
                if (file != null) {
                    String fileContent = fileController.openFile(file.getFileName(),file.getFolderFather());
                    System.out.println(fileContent);
                } else {
                    printBlank("指定名字的文件不存在，请检查文件名是否输入正确", null);
                }
            }
        } else {
            printBlank("OF需要一个参数作为文件名", null);
        }

    }

    //修改文件名
    private void processModifyFileName(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("MFN fileName", "修改名字为fileName的文本文件的名称");
            } else {
                SysFile file=_currentPath.searchFileByName(param[1]);
                if (file != null) {
                    System.out.println("请输入新文件名：");
                    String newFileName = scanner.nextLine();
                    fileController.updateFileName(file.getFileName(),file.getFolderFather(),newFileName);
                } else {
                    printBlank("指定名字的文件不存在，请检查文件名是否输入正确", null);
                }
            }
        } else {
            printBlank("MFN需要一个文件名参数指定文件", null);
        }

    }

    //修改文件内容
    private void processModifyFile(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("MF fileName", "修改名字为fileName的文本文件的内容");
            } else {
                SysFile file=_currentPath.searchFileByName(param[1]);
                if (file != null) {
                    System.out.println(file.getFileData());
                    System.out.println("开始修改文件，输入:end结束修改，您要把文件内容修改为：");
                    StringBuilder sb = new StringBuilder();
                    String tempLine = "";
                    do {
                        sb.append(tempLine + "\n");
                        tempLine = scanner.nextLine();
                    } while (!tempLine.trim().equalsIgnoreCase(":end"));

                   file.setFileData(sb.toString());

                } else {
                    printBlank("指定名字的文件不存在，请检查文件名是否输入正确", null);
                }
            }
        } else {
            printBlank("MF需要一个文件名参数指定文件", null);
        }

    }


    //删除文件
    private void processDeleteFile(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("DEL fileName", "删除名字为fileName的文本文件");
            } else {
                if (_currentPath.searchFileByName(param[1]) != null) {
                    String fileName = param[1];
                    fileController.deleteFile(fileName, _currentPath, file_allocate_table, bitMap);

                } else {
                    printBlank("该文件夹下没有您指定的文件，请检查文件名是否输入正确", null);
                }
            }
        } else {
            printBlank("DEL需要一个参数指定要删除的文件名", null);
        }
    }


    //处理错误命令
    public void processError(String[] param) {
        if (param[0].length() == 0) {
            return;
        }
        printBlank(param[0] + "命令没有定义，输入 HELP 查看命令帮助信息", null);
    }

    //处理帮助命令
    public void processHelp(String[] param) {
        if (param.length > 2) {
            printBlank(param[0] + "命令使用错误，输入HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                helpInformation();
            } else {
                printBlank(param[0] + "命令使用错误，输入HELP查看命令使用方法", null);
            }
        } else {
            helpInformation();
        }
    }

    //打印帮助信息
    public void helpInformation() {
        printBlank("HELP", "获取帮助信息");
        printBlank("anyCommand -HELP", "获取该命令的使用方法");
        printBlank("EXIT or QUIT or ESC", "退出该系统");
        printBlank("SF", "显示fat表");
        printBlank("SB", "显示位视图");
        printBlank("CF fileName", "创建名字为fileName(不超过25个字符)的文本文件");
        printBlank("OF fileName", "打开名字为fileName的文本文件");
        printBlank("MF fileName", "修改名字为fileName的文本文件的内容");
        printBlank("MFN fileName", "修改名字为fileName的文本文件的名称");
        printBlank("DEL fileName", "删除名字为fileName的文本文件");
        printBlank("CDIR folderName", "创建名字为folderName(不超过25个字符)的文件夹");
        printBlank("CD folderName", "进入名字为folderName的文件夹");
        printBlank("MDN folderName", "修改名字为folderName的文件夹的名称");
        printBlank("DELDIR folderName", "删除名字为folderName的文件夹");
        printBlank("LS", "显示当前文件夹下的所有文件");
    }

    //处理退出命令
    public boolean processExit(String[] param) {
        if (param.length > 2) {
            printBlank("命令使用错误，输入-HELP查看命令使用方法", null);
        } else if (param.length > 1) {
            if (param[1].equals("-help")) {
                printBlank("EXIT or QUIT or ESC", "退出该系统");
            }
        } else {
            System.out.println("                      __------__\n" +
                    "                    /~    结束  ~\\\n" +
                    "                   |    //^\\\\//^\\|\n" +
                    "                 /~~\\  ||  o| |o|:~\\\n" +
                    "                | |6   ||___|_|_||:|\n" +
                    "                 \\__.  /      o  \\/'\n" +
                    "                  |   (       O   )\n" +
                    "         /~~~~\\    `\\  \\         /\n" +
                    "        | |~~\\ |     )  ~------~`\\\n" +
                    "       /' |  | |   /     ____ /~~~)\\\n" +
                    "      (_/'   | | |     /'    |    ( |\n" +
                    "             | | |     \\    /   __)/ \\\n" +
                    "             \\  \\ \\      \\/    /' \\   `\\\n" +
                    "               \\  \\|\\        /   | |\\___|\n" +
                    "                 \\ |  \\____/     | |\n" +
                    "                 /^~>  \\        _/ <\n" +
                    "                |  |         \\       \\\n" +
                    "                |  | \\        \\        \\\n" +
                    "                -^-\\  \\       |        )\n" +
                    "                     `\\_______/^\\______/");
            return true;
        }
        return false;
    }


}
