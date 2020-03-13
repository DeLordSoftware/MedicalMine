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

public class WriteDataToExcel {  
   //private static final XSSFWorkbook workbook = new XSSFWorkbook(); 
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
        String fileLocation = path.substring(0, path.length() - 1) + "MedicalTest.xlsx";    
        File flOrginalFile =  new File(fileLocation);
         
        // Delete Orginal Exile file
        if (flOrginalFile.exists())
        {
           flOrginalFile.delete();
        }

        // Write and close excel file
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        outputStream.flush();
        workbook.write(outputStream);
        workbook.close();

        return path;
    }       
}
/*  PDF to Text
We created a method named generateTxtFromPDF(…) and divided it into three main parts: loading of the PDF file, extraction of text, and final file creation.

Let’s start with loading part:

File f = new File(filename);
String parsedText;
PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
parser.parse();
In order to read a PDF file, we use PDFParser, with an “r” (read) option. Moreover, we need to use the parser.parse() method that will cause the PDF to be parsed as a stream and populated into the COSDocument object.

Let’s take a look at the extracting text part:

COSDocument cosDoc = parser.getDocument();
PDFTextStripper pdfStripper = new PDFTextStripper();
PDDocument pdDoc = new PDDocument(cosDoc);
parsedText = pdfStripper.getText(pdDoc);
In the first line, we’ll save COSDocument inside the cosDoc variable. It will be then used to construct PDocument, which is the in-memory representation of the PDF document. Finally, we will use PDFTextStripper to return the raw text of a document. After all of those operations, we’ll need to use close() method to close all the used streams.

In the last part, we’ll save text into the newly created file using the simple Java PrintWriter:

PrintWriter pw = new PrintWriter("src/output/pdf.txt");
pw.print(parsedText);
pw.close();
Please note that you cannot preserve formatting in a plain text file because it contains text only.
*/