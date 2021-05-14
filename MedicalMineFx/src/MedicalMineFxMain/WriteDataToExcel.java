package MedicalMineFxMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class WriteDataToExcel extends ProcessInputFiles {     
   private Map < String, Object[] > fileRecord = new TreeMap < >();     
   
    /*
    example from https://www.baeldung.com/java-microsoft-excel
    */   
   public String SaveExcelSpreadSheet(Map<Integer, Map<String,String> > mpFinalMedical) throws FileNotFoundException, IOException
   {
        // Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        boolean bIsHeader = true;


        // Create a blank sheet
        Sheet sheet = sheet = workbook.createSheet("Search Result");
        Row header  = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);
       
       // Cycle through files
       for(Map.Entry<Integer, Map<String,String>> itrFile : mpFinalMedical.entrySet())
       {
            Map<String, String> mpMedical = itrFile.getValue();
            XSSFRow row = (XSSFRow) sheet.createRow(itrFile.getKey());
            int iIndex = 0;
            
            for (Map.Entry<String,String> itrCategory : mpMedical.entrySet()) 
            {
                
                // Populate category row (1)
                if(bIsHeader)
                {
                    Cell headerCell = header.createCell(iIndex);
                    headerCell.setCellValue(itrCategory.getKey());                    
                    headerCell.setCellStyle(headerStyle);
                }
                
                // Insert data in each cell in row
                Cell cell = row.createCell(iIndex);
                cell.setCellValue(itrCategory.getValue());

                iIndex++;           
            }
            
            // Populate Category row once.
            if(itrFile.getKey() == 1)
            {
                bIsHeader = false;            
            }
        }
       
        // Write content to file in current directory and close workbook:
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = UtlityClass.RESULT_FOLDER_LOC + UtlityClass.SEARCH_FILE_LOC + "Search Result" + resultTime + ".xlsx";    
       
        // Write and close excel file
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        outputStream.flush();
        workbook.write(outputStream);
        workbook.close();

        return path;
    }       
}
