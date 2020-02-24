
package com.softcloud.grid.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.softcloud.grid.common.exception.GridRuntimeException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;

/**
 * ClassName:ExcelFile <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON. <br/>
 * Date: 2018年7月21日 上午11:34:43 <br/>
 *
 * @author st-wg-pwz7240
 * @version
 * @since JDK 1.8
 * @see
 */
public class ExcelFile {

    /** * log: 日志对象. */
    private Logger log = LoggerFactory.getLogger(ExcelFile.class);
    private File excel;
    private BufferedInputStream bufferedInputStream = null;
    private Workbook wb = null;

    /**
     * ExcelFiel: 初始化ExcelFile. <br/>
     *
     * @Date 2018年10月23日下午4:02:59
     * @param filePath
     *            文件路径
     * @since JDK 1.8
     */
    public ExcelFile(String filePath) {
        excel = new File(filePath);
    }

    /**
     * ExcelFile: 初始化ExcelFile. <br/>
     *
     * @Date 2018年10月23日下午4:02:57
     * @param file
     *            文件
     * @since JDK 1.8
     */
    public ExcelFile(File file) {
        this.excel = file;
    }

    /**
     * open: 打开文件流. <br/>
     *
     * @Date 2018年10月23日下午4:57:54
     * @throws GridRuntimeException
     *             e
     * @since JDK 1.8
     */
    public void open() throws GridRuntimeException {
        try {
            //获取输入流
            bufferedInputStream = new BufferedInputStream(new FileInputStream(excel));
            //获取workbook
            if (excel.getName().endsWith(".xlsx")) {
                wb = new XSSFWorkbook(bufferedInputStream);
            } else if (excel.getName().endsWith("xls")) {
                try (POIFSFileSystem fs = new POIFSFileSystem(bufferedInputStream)) {
                    wb = new HSSFWorkbook(fs);
                    fs.close();
                }
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new GridRuntimeException("file not found: " + excel.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GridRuntimeException("打开excel文件异常");
        }
    }

    /**
     * close: 关闭文件流. <br/>
     *
     * @Date 2018年10月23日下午4:58:12
     * @throws GridRuntimeException
     *             e
     * @since JDK 1.8
     */
    public void close() throws GridRuntimeException {
        try {
            if (null != wb) {
                wb.close();
                wb = null;
            }
            if (null != bufferedInputStream) {
                bufferedInputStream.close();
                bufferedInputStream = null;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GridRuntimeException("关闭excel文件异常");
        }
    }

    /**
     * getWorkbook: 获取工作簿. <br/>
     *
     * @Date 2018年10月23日下午4:02:34
     * @return Workbook
     * @since JDK 1.8
     */
    public Workbook getWorkbook() {
        return wb;
    }

    /**
     * readSheet:(获取指定sheet数据). <br/>
     *
     * Date:2018年7月21日下午12:38:34
     *
     * @param sheetNum
     *            sheet编号,从0开始
     * @param col
     *            列数(第一行历史不匹配时扔出异常),从0开始
     * @param sheetName
     *            sheet 名称 (名称错误时扔出异常)
     * @return JSONArray sheet数据
     * @throws GridRuntimeException
     *             e
     * @since JDK 1.8
     */
    public JSONArray readSheet(int sheetNum, int col, String sheetName) throws GridRuntimeException {
        Sheet st = wb.getSheetAt(sheetNum);

        //验证sheet名称、列数是否正确
        if (sheetName != null && !sheetName.equalsIgnoreCase(st.getSheetName())) {
            throw new GridRuntimeException("请确定是否是正确模板，检查到第" + (sheetNum + 1) + "个sheet名不是'" + sheetName + "'");
        }
        if (st.getRow(0).getLastCellNum() < (col + 1)) {
            throw new GridRuntimeException("请确定是否是正确模板，检查到第" + (sheetNum + 1) + "个sheet表标题不是" + (col + 1) + "列");
        }

        //读取sheet文件内容
        JSONArray sheetData = new JSONArray();
        JSONObject rowData = null;
        Cell cell = null;
        short firstRowSize = st.getRow(0).getLastCellNum();
        for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
            rowData = new JSONObject();
            Row row = st.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            // 判断是否是空行
            String rowValue = "";
            for (short columnIndex = 0, size = firstRowSize; columnIndex < size; columnIndex++) {
                cell = row.getCell(columnIndex);
                Object value = getCellValue(cell);
                value = value == null ? "" : value;
                rowData.put(String.valueOf(columnIndex), value);
                rowValue += value;
            }

            // 判断是否是空行
            if (rowValue.length() <= 0) {
                continue;
            }
            sheetData.add(rowData);
        }

        return sheetData;
    }

    /**
     * getCellValue: 获取单元格内容. <br/>
     *
     * @Date 2018年10月23日下午4:36:55
     * @param cell
     *            excel单元格
     * @return String
     * @since JDK 1.8
     */
    private Object getCellValue(Cell cell) {
        if (cell != null) {
            // 注意：一定要设成这个，否则可能会出现乱码
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    } else {
                        return new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                case Cell.CELL_TYPE_FORMULA:
                    // 导入时如果为公式生成的数据则无值
                    return cell.getNumericCellValue();
                case Cell.CELL_TYPE_BLANK:
                    // value = "";
                    return "";
                case Cell.CELL_TYPE_ERROR:
                    return "";
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                default:
                    return "";
            }
        }
        return null;
    }

    /**
     *
     * @author wxq1999
     * @Date 2018年4月22日下午1:57:17
     *
     * @param sheet
     *            sheet
     * @param firstRow
     *            firstRow
     * @param lastRow
     *            lastRow
     * @param firstCol
     *            firstCol
     * @param lastCol
     *            lastCol
     * @param DATA_LIST
     *            DATA_LIST
     * @param msg
     *            msg
     * @return XSSFDataValidation
     * @since JDK 1.8
     */
    public static XSSFDataValidation setSelectDataValidation(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol,
                                                             String[] DATA_LIST, String msg) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dVConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(DATA_LIST);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        XSSFDataValidation dataValidation = (XSSFDataValidation) dvHelper.createValidation(dVConstraint, addressList);
        dataValidation.createErrorBox("输入提示", msg);
        dataValidation.setShowErrorBox(true);
        dataValidation.setShowPromptBox(true);
        return dataValidation;

    }

    /**
     *
     * @author wxq1999
     * @Date 2018年4月22日下午2:16:06
     * @param wb
     *            wb
     * @param sheetName
     *            sheetName
     * @param headData
     *            headData
     * @return XSSFSheet
     * @since JDK 1.8
     */
    public static XSSFSheet creatSheet(XSSFWorkbook wb, String sheetName, String[] headData) {

        XSSFSheet sheet = wb.createSheet(sheetName);
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headData.length; i++) {
            row.createCell(i).setCellValue(headData[i]);
        }
        return sheet;
    }
}
