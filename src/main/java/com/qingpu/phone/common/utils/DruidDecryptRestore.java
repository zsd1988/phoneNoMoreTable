import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 数据库密码加密
 */
@SuppressWarnings("all")
public class DruidDecryptRestore {

    static String passord = "123456";

    public static String dateToString(Date date, int index) {
        SimpleDateFormat simpleDateFormat = DruidDecryptRestore.getSimpleDateFormatByIndes(index);
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static SimpleDateFormat getSimpleDateFormatByIndes(Integer index) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (index == 1) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (index == 2) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (index == 3) {
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        } else if (index == 4) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (index == 5) {
            dateFormat = new SimpleDateFormat("HH:mm");
        } else if (index == 6) {
            dateFormat = new SimpleDateFormat("MM-dd");
        } else if (index == 7) {
            dateFormat = new SimpleDateFormat("yyyyMM");
        } else if (index == 8) {
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        }else if (index == 9) {
            dateFormat = new SimpleDateFormat("yyyy-MM");
        }
        return dateFormat;
    }

    /**
     * 恢复数据库
     * @return 成功:TRUE 失败:FALSE
     */
    public static boolean restore(){
        String fPath="/home/qpcc/fastdfs2/phone/";
        try {
            String sqlName = DruidDecryptRestore.dateToString(new Date(), 0) + ".sql";
            fPath=  fPath + sqlName;
            Runtime runtime = Runtime.getRuntime();
            System.out.println("----------------------------------------数据库备份" + fPath);
            Process process = runtime
                    .exec("mysql -uroot -p" + passord + " --default-character-set=utf8 phone");
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fPath), "utf-8"));
            String str = null;
            OutputStreamWriter osw=new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter  bw= new BufferedWriter(osw);
            while ((str = br.readLine()) != null) {
                bw.write(str+"\t\n");
            }

            //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
            bw.close();
            osw.close();
            br.close();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final int ADDDAY = 5;
    public static Date add(Date date, int addType, int val) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(addType, val);
        return calendar.getTime();
    }

    public static List<String> executeNewFlow(List<String> commands) {
        List<String> rspList = new ArrayList<String>();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                System.out.println(rspLine);
                rspList.add(rspLine);
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rspList;
    }

    public static void main(String[] args) {
        try {
            List<String> commandList = new ArrayList<>();
            String command = "mysql -uroot -p" + passord + " --default-character-set=utf8 phone";
            commandList.add(command);
            System.out.println(command);
            Date now = new Date();
//            command = "drop database phone" + DruidDecryptRestore.dateToString(DruidDecryptRestore.add(now, DruidDecryptRestore.ADDDAY, -2), 3) + ";";
//            commandList.add(command);
//            System.out.println(command);
            String dateName = "phone" + DruidDecryptRestore.dateToString(new Date(), 3);
            command = "create database " + dateName + ";";
            commandList.add(command);
            System.out.println(command);
            command = "exit;";
            commandList.add(command);
            System.out.println(command);
//            command = "exit";
//            commandList.add(command);
            DruidDecryptRestore.executeNewFlow(commandList);

            DruidDecryptRestore.restore();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
