package com.qingpu.phone.common.utils;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportDataUtil {

    /**
     * 数据导出
     * @param objectList：数据列表
     * @param templateName：导出模板名称
     * @param exportName：导出文件名
     * @param request
     * @param response
     */
    public static void exportData(List<?> objectList, String templateName, String exportName, HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String,Object> parameters = new HashMap<String,Object>();
            String realPath = request.getSession().getServletContext().getRealPath("/");
            String reportFileName = realPath + "/platform/template/"+templateName+".xls";
            XLSTransformer transformer = new XLSTransformer();
            parameters.put("listData", objectList);
            InputStream is  = new FileInputStream(reportFileName);
            HSSFWorkbook wb = (HSSFWorkbook) transformer.transformXLS(is, parameters);
            exportName = new String(exportName.getBytes("gb2312"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + exportName +".xls");
            response.setContentType("application/xls");
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
