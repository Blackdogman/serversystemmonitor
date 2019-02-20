package org.rockbdm.serversystemmonitor.tools;

import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SystemInfoTools {

    public Map<String, Double> getSystemInfo() throws IOException {
        Map<String, Double> results = new HashMap<>();
        results.put("cpu", getCpuInfo());
        results.put("ram", getRamInfo());
        results.put("rom", getRomInfo());
        return results;
    }


    private Double getCpuInfo() throws IOException {
        double cpuUsed = 0;

        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("top -b -n 1");// 调用系统的“top"命令

        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String str = null;
            String[] strArray = null;

            while ((str = in.readLine()) != null) {
                int m = 0;

                if (str.contains(" R ")) {// 只分析正在运行的进程，top进程本身除外 &&

                    strArray = str.split(" ");
                    for (String tmp : strArray) {
                        if (tmp.trim().length() == 0)
                            continue;
                        if (++m == 9) {// 第9列为cpu的使用百分比(RedHat

                            cpuUsed += Double.parseDouble(tmp);

                        }

                    }

                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return cpuUsed;
    }

    private Double getRamInfo() throws IOException {
        double menUsed = 0;
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("top -b -n 1");// 调用系统的“top"命令

        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String str = null;
            String[] strArray = null;

            while ((str = in.readLine()) != null) {
                int m = 0;

                if (str.contains(" R ")) {// 只分析正在运行的进程，top进程本身除外 &&
                    //
                    // System.out.println("------------------3-----------------");
                    strArray = str.split(" ");
                    for (String tmp : strArray) {
                        if (tmp.trim().length() == 0)
                            continue;

                        if (++m == 10) {
                            // 9)--第10列为mem的使用百分比(RedHat 9)

                            menUsed += Double.parseDouble(tmp);

                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menUsed;
    }

    private Double getRomInfo() throws IOException {
        double totalhd = 0;
        double usedhd = 0;
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("df -hl /home");//df -hl 查看硬盘空间

        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String str = null;
            String[] strArray = null;
            while ((str = in.readLine()) != null) {
                int m = 0;
                strArray = str.split(" ");
                for (String tmp : strArray) {
                    if (tmp.trim().length() == 0)
                        continue;
                    ++m;
                    System.out.println("----tmp----" + tmp);
                    if (tmp.contains("G")) {
                        if (m == 2) {
                            if (!tmp.equals("") && !tmp.equals("0"))
                                totalhd += Double.parseDouble(tmp
                                        .substring(0, tmp.length() - 1)) * 1024;

                        }
                        if (m == 3) {
                            if (!tmp.equals("none") && !tmp.equals("0"))
                                usedhd += Double.parseDouble(tmp.substring(
                                        0, tmp.length() - 1)) * 1024;

                        }
                    }
                    if (tmp.contains("M")) {
                        if (m == 2) {
                            if (!tmp.equals("") && !tmp.equals("0"))
                                totalhd += Double.parseDouble(tmp
                                        .substring(0, tmp.length() - 1));

                        }
                        if (m == 3) {
                           if (!tmp.equals("none") && !tmp.equals("0"))
                                usedhd += Double.parseDouble(tmp.substring(
                                        0, tmp.length() - 1));
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (totalhd / usedhd) * 100;
    }

    private Double getNetInfo(){
        return null;
    }


}
