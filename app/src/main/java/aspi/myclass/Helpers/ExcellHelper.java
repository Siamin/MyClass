package aspi.myclass.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aspi.myclass.activity.AddStudentActivity;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.model.ImportExcellModel;
import aspi.myclass.model.ReportDataModel;


public class ExcellHelper {

    static String TAG = "TAG_ExcellHelper";

    public static boolean ExcelSaveDataClassByTypePage(Context context, List<ReportDataModel> model, String SheetName, String FileName, boolean StatusPage) {
        boolean success = false;
        //******************************************************************************New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell cell = null;
        //*****************************************************************Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //*********************************************************************************New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(SheetName);
        //******************************************************************Generate column headings
        try {

            List<ReportDataModel> modelCheck = new ArrayList<>();
            List<ReportDataModel> Datemodel = new ArrayList<>();
            Log.i(TAG, "Started");
            for (int row = -1; row < model.size(); row++) {
                org.apache.poi.ss.usermodel.Row Row = sheet1.createRow(row + 1);
                boolean statusSno = (row > -1 ? ValidationHelper.validationSnoInModel(modelCheck, model.get(row).sno) : true);

                if (statusSno) {

                    boolean Status_Date = true;
                    for (int fild = -1, fixedFild = 0; fild < model.size(); fild++) {

                        if (row == -1 && fild == -1) {
                            fixedFild++;
                            CreateCell(cell, sheet1, cs, Row, "نام و نام خانوادگی", fixedFild);

                        } else if (row > -1 && fild == -1) {
                            modelCheck.add(model.get(row));
                            CreateCell(cell, sheet1, cs, Row, model.get(row).name + " " + model.get(row).family, fixedFild);
                            fixedFild++;

                        } else if (row > -1 && fild > -1 && model.get(row).sno.equals(model.get(fild).sno)) {

                            String txt = "";
                            int CountDate = (Status_Date ? ValidationHelper.validationDateInModel(Datemodel, model.get(fild).date) : -1);
                            Status_Date = false;

                            if (CountDate > 0) {

                                for (int i = 0; i < CountDate; i++) {

                                    if (StatusPage)
                                        CreateCell(cell, sheet1, cs, Row, "-", fixedFild);

                                    else
                                        CreateCell(cell, sheet1, cs, Row, "", fixedFild);

                                    fixedFild++;
                                }
                            }

                            if (StatusPage) {
                                if (model.get(fild).status.equals("1")) {
                                    txt = "√";
                                } else if (model.get(fild).status.equals("0")) {
                                    txt = "غ";
                                } else {
                                    txt = "-";
                                }
                            } else if (!StatusPage) {
                                txt = model.get(fild).score;
                            }

                            CreateCell(cell, sheet1, cs, Row, txt, fixedFild);
                            fixedFild++;

                        } else if (row == -1 && fild > -1 && model.get(row + 1).sno.equals(model.get(fild).sno)) {
                            CreateCell(cell, sheet1, cs, Row, model.get(fild).date, fixedFild);
                            Datemodel.add(model.get(fild));
                            fixedFild++;

                        }
                    }
                }

            }

            File file = new java.io.File(MainActivity.Address_file_app, FileName + ".xls");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            wb.write(os);

            success = true;
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return success;
    }

    static void CreateCell(Cell cell, Sheet sheet1, CellStyle cs, org.apache.poi.ss.usermodel.Row Row, String cellValue, int fixedFild) {
        cell = Row.createCell(fixedFild);
        cell.setCellStyle(cs);
        cell.setCellValue(cellValue);
        sheet1.setColumnWidth(fixedFild, (15 * 350));
    }


    public static void readExcelFile(String filename, Context context, Dialog dialog) {


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return;
        }

        try {

            File file = new File(filename);

            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();

            int i = 0;
            List<ImportExcellModel> importExcellModels = new ArrayList<>();

            while (rowIter.hasNext()) {

                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                ImportExcellModel importExcellModel = new ImportExcellModel();

                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();

                    if (i == 0) {
                        importExcellModel.sno = myCell.toString();
                    } else if (i == 1) {
                        importExcellModel.name = myCell.toString();
                    } else if (i == 2) {
                        importExcellModel.family = myCell.toString();
                        importExcellModels.add(importExcellModel);
                        i = -1;
                    }
                    i++;
                }

            }
            AddStudentActivity.get_upload(importExcellModels);
            dialog.dismiss();
        } catch (Exception e) {
            MessageHelper.Toast(context, "لظفا فایل Excel را با فرمت 97-2003 ذخیره و سپس import کنید.");
            dialog.dismiss();
        }

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
