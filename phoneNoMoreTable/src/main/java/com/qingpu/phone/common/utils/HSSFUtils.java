package com.qingpu.phone.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class HSSFUtils {

    public static String getValue(HSSFCell hssfCell) {
        if(hssfCell!=null){
            if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(hssfCell.getBooleanCellValue()).trim();
            }else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                return String.valueOf(hssfCell.getNumericCellValue()).trim();
            }else {
                return String.valueOf(hssfCell.getStringCellValue()).trim();
            }

        }
        return "";
    }
}
