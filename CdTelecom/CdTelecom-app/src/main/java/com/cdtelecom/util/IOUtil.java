package com.cdtelecom.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
	
    /**
     * 
     * 描述：导出
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据（data1,data2,data3...）
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList){
        FileOutputStream out= null;
        OutputStreamWriter osw = null;
        BufferedWriter bfw= null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out, "gbk");
            bfw = new BufferedWriter(osw);
            if(dataList != null && !dataList.isEmpty()){
                for(String data : dataList){
                    bfw.append(data).append("\r");
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }finally{
            try {
				bfw.close();
	            osw.close();
	            out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    /**
     * 
     * 描述：导入
     * @param file csv文件(路径+文件名)
     * @return
     */
    public static List<String> importCsv(File file){
        List<String> dataList = new ArrayList<String>();
        BufferedReader br = null;
        try { 
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) { 
                dataList.add(line);
            }
        }catch (Exception e) {
            
        }finally{
            try {
            	if(br != null){
            		br.close();
            	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return dataList;
    }
    
}

