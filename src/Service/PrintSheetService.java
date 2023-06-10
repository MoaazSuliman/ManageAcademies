package Service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;

public class PrintSheetService {
    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private Cell cell;

    public void printTableAsExcel(String name, JTable table) {
        // Create a new Excel workbook
        createNewExcelWorkBook(name);
        // Copy JTable data to Excel sheet
        defaultLoop(table);
        // Save the workbook to a file
        saveWorkBookToFile(name);
    }

    public void printTableAsExcelWithTotal(String name, JTable table, double total) {
        // Create a new Excel workbook
        createNewExcelWorkBook(name);
        // Copy JTable data to Excel sheet
        defaultLoopWithTotal(table, total);
        // Save the workbook to a file
        saveWorkBookToFile(name);
    }

    private void createNewExcelWorkBook(String name) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(name);
    }

    private void defaultLoop(JTable table) {
        row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("StudentName");
        cell = row.createCell(1);
        cell.setCellValue("Course Name");
        cell = row.createCell(2);
        cell.setCellValue("Level");
        cell = row.createCell(3);
        cell.setCellValue("Payed Money");
        cell = row.createCell(4);
        cell.setCellValue("Course Money");
        cell = row.createCell(5);
        cell.setCellValue("Outstanding balance");
        cell = row.createCell(6);
        cell.setCellValue("Months");
        cell = row.createCell(7);
        cell.setCellValue("Date");

        for (int i = 0; i < table.getRowCount(); i++) {
            row = sheet.createRow(i + 1);

            for (int j = 0; j < table.getColumnCount(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(table.getValueAt(i, j).toString());
            }
        }
    }

    private void defaultLoopWithTotal(JTable table, double total) {
        int columns = 0;
        int rows = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            row = sheet.createRow(i);
            columns++;
            for (int j = 0; j < table.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(table.getValueAt(i, j).toString());
                rows++;
            }
        }
        row = sheet.createRow(columns + 1);
        Cell cell = row.createCell(0);
        cell.setCellValue("Total");
        cell = row.createCell(1);
        cell.setCellValue(total);
    }

    private void saveWorkBookToFile(String name) {
        try (FileOutputStream fileOut = new FileOutputStream(name + ".xlsx")) {
            workbook.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
