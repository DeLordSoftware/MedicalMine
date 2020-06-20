/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmllicenseencoder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author RW Simmons
 */
public class FXMLLicenseEncoderController implements Initializable {

    final String YEAR_LIC = "1 Year";
    final String MONTH_9_LIC = "9 Months";
    final String MONTH_6_LIC = "6 Months";
    final String MONTH_3_LIC = "3 Months";
    final String MONTH_1_LIC = "1 Months";
    final String CUSTOM_LIC = "Custom";
    final int MONTH_VAL = 0;
    final int DAY_VAL = 1;
    final int YEAR_VAL = 2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set combo box
        cmbSelectLength.getItems().add("");
        cmbSelectLength.getItems().add(YEAR_LIC);
        cmbSelectLength.getItems().add(MONTH_9_LIC);
        cmbSelectLength.getItems().add(MONTH_6_LIC);
        cmbSelectLength.getItems().add(MONTH_3_LIC);
        cmbSelectLength.getItems().add(MONTH_1_LIC);
        cmbSelectLength.getItems().add(CUSTOM_LIC);

        // Get current date
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        txtSystDate.setText(formatter.format(date));
    }

    /**
     *
     * @param selected
     * @param date
     */
    private void createEncoderString(String selected, String date) throws IOException {
        // create instance of Random class 
        int[] lstDataLocation = setDateLocations();
        int iMonth = lstDataLocation[MONTH_VAL];
        int iDay = lstDataLocation[DAY_VAL];
        int iYear = lstDataLocation[YEAR_VAL];
        System.out.println("Month " + iMonth + " Day " + iDay + " Year " + iYear);

        // Prefill encoder list
        String[] lstDate = date.split("/");
        int[] lstEncoder = new int[12];
        Random random = new Random();

        for (int ii = 2; ii < lstEncoder.length; ii++) {
            lstEncoder[ii] = random.nextInt(9);
        }

        lstEncoder[0] = iMonth;
        lstEncoder[1] = iDay;
        lstEncoder[2] = iYear;
        presetEncoderList(lstDate, lstEncoder, lstDataLocation);
        int iChangeDay0 = lstEncoder[iMonth];
        int iChangeDay1 = lstEncoder[iMonth + 1];

        // Create encoder data
        switch (selected) {
            case YEAR_LIC:
                // One Year
                setYear(lstEncoder, iYear);
                break;
            case MONTH_9_LIC:
                // Nine Month
                if (iChangeDay0 == 0 && iChangeDay1 == 1) {
                    // One - 10
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 0;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 2) {
                    // Two - 11
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 1;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 3) {
                    // Three - 12
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 2;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 4) {
                    // Four - 01
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 1;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 5) {
                    // Five - 02
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 2;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 6) {
                    // Six - 03
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 3;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 7) {
                    // Seven - 04    
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 4;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 8) {
                    // Eight - 05
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 5;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 9) {
                    // Nine - 06
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 6;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 0) {
                    // Ten - 07
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 7;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 1) {
                    // Eleven - 08   
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 8;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 2) {
                    // Twelve - 09
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 9;
                    setYear(lstEncoder, iYear);
                } else {
                    lstEncoder[iMonth + 1] += 9;
                }
                break;
            case MONTH_6_LIC:
                // Six Month
                if (iChangeDay0 == 0 && iChangeDay1 == 4) {
                    // Four - 10 
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 0;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 5) {
                    // Five - 11
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 1;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 6) {
                    // Six - 12
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 2;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 7) {
                    // Seven - 01    
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 1;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 8) {
                    // Eight - 02
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 2;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 0 && iChangeDay1 == 9) {
                    // Nine - 03
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 3;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 0) {
                    // Ten - 04 
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 4;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 1) {
                    // Eleven - 05   
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 5;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 2) {
                    // Twelve - 06
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 6;
                    setYear(lstEncoder, iYear);
                } else {
                    lstEncoder[iMonth + 1] += 6;
                }
                break;
            case MONTH_3_LIC:
                // Three Month
                if (iChangeDay0 == 0 && iChangeDay1 == 7) {
                    // Seven - 10    
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 0;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 8) {
                    // Eight - 11
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 1;
                } else if (iChangeDay0 == 0 && iChangeDay1 == 9) {
                    // Nine - 12
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 2;
                } else if (iChangeDay0 == 1 && iChangeDay1 == 0) {
                    // Ten - 01
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 1;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 1) {
                    // Eleven - 02   
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 2;
                    setYear(lstEncoder, iYear);
                } else if (iChangeDay0 == 1 && iChangeDay1 == 2) {
                    // Twelve - 03
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 3;
                    setYear(lstEncoder, iYear);
                } else {
                    lstEncoder[iMonth + 1] += 3;
                }
                break;
            case MONTH_1_LIC:
                // One Month
                if (iChangeDay0 == 0 && iChangeDay1 == 9) {
                    // Nine
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 0;
                } else if (iChangeDay0 == 1 && iChangeDay1 == 0) {
                    // Ten
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 1;
                } else if (iChangeDay0 == 1 && iChangeDay1 == 1) {
                    // Eleven    
                    lstEncoder[iMonth] = 1;
                    lstEncoder[iMonth + 1] = 2;
                } else if (iChangeDay0 == 1 && iChangeDay1 == 2) {
                    // Twelve 
                    lstEncoder[iMonth] = 0;
                    lstEncoder[iMonth + 1] = 1;
                    setYear(lstEncoder, iYear);
                } else {
                    lstEncoder[iMonth + 1] += 1;
                }
                break;
            case CUSTOM_LIC:
                // TODO
                break;
        }

        // Create Encoder license 
        String strFinalEncoder = createEncoderFile(lstEncoder);

        // Display results
        txtEncoderResult.setText("Encoder # - " + strFinalEncoder);
        String strExpire = lstEncoder[iMonth] + lstEncoder[iMonth + 1] + "/" + lstEncoder[iDay] + lstEncoder[iDay + 1] + "/" + lstEncoder[iYear] + lstEncoder[iYear + 1];
        txtExpire.setText("Expires: " + strExpire);

        // Print to file
        FileWriter flEncoderLic = null;
        try {
            flEncoderLic = new FileWriter("License.dsc");
            flEncoderLic.write(strExpire + "\n" + strFinalEncoder);
        } catch (IOException ex) {
            Logger.getLogger(FXMLLicenseEncoderController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            flEncoderLic.close();
        }
    }

    /**
     *
     * @param iMonth
     * @param iDay
     * @param iYear
     * @return
     */
    private int[] setDateLocations() {
        int iMonth;
        int iDay;
        int iYear;
        Random random = new Random();

        // Get month
        do {
            iMonth = random.nextInt(11);
        } while (iMonth < 3);

        // Get Day
        boolean bHasMonth = true;
        do {
            iDay = random.nextInt(11);;
            if (iDay > 3) {
                if ((iMonth != iDay) && (iMonth + 1 != iDay) && (iMonth - 1 != iDay)) {
                    bHasMonth = false;
                }
            }
        } while (bHasMonth);

        // Get year
        boolean bHasYear = true;
        do {
            iYear = random.nextInt(11);
            if (iYear > 3) {
                if ((iYear != iDay) && (iYear + 1 != iDay) && (iYear - 1 != iDay)) {
                    if ((iYear != iMonth) && (iYear + 1 != iMonth) && (iYear - 1 != iMonth)) {
                        bHasYear = false;
                    }
                }
            }
        } while (bHasYear);

        int[] returnList = {iMonth, iDay, iYear};
        return returnList;
    }

    /**
     *
     * @param lstDate
     * @param lstEncode
     * @param date
     */
    private void presetEncoderList(String[] lstDate, int[] lstEncode, int[] date) {
        int iMonth = date[MONTH_VAL];
        int iDay = date[DAY_VAL];
        int iYear = date[YEAR_VAL];

        char[] strList = lstDate[MONTH_VAL].toCharArray();
        lstEncode[iMonth] = Integer.parseInt(Character.toString(strList[0]));
        lstEncode[iMonth + 1] = Integer.parseInt(Character.toString(strList[1]));

        strList = lstDate[DAY_VAL].toCharArray();
        lstEncode[iDay] = Integer.parseInt(Character.toString(strList[0]));
        lstEncode[iDay + 1] = Integer.parseInt(Character.toString(strList[1]));

        strList = lstDate[YEAR_VAL].toCharArray();
        lstEncode[iYear] = Integer.parseInt(Character.toString(strList[0]));
        lstEncode[iYear + 1] = Integer.parseInt(Character.toString(strList[1]));
    }

    /**
     *
     * @param lst
     * @return
     */
    private String createEncoderFile(int[] lst) {
        String strFinalEncoder = new String();
        int count = 0;
        for (int ii : lst) {
            if (ii > 9) {
                switch (ii) {
                    case 10:
                        strFinalEncoder = strFinalEncoder.concat("A");
                        break;
                    case 11:
                        strFinalEncoder = strFinalEncoder.concat("B");
                        break;
                }
            } else {
                //System.out.println(count++ + " Encoder - " + ii);
                strFinalEncoder = strFinalEncoder.concat(String.valueOf(ii));
            }
        }

        return strFinalEncoder;
    }

    /**
     *
     * @param list
     * @param year
     */
    private void setYear(int[] list, int year) {
        if (list[year + 1] == 9) {
            list[year] += 1;
            list[year + 1] = 0;
        } else {
            list[year + 1] += 1;
        }
    }

    @FXML
    private ComboBox<String> cmbSelectLength;
    @FXML
    private Label txtSystDate;
    @FXML
    private Button btnCreateLic;
    @FXML
    private Label txtEncoderResult;
    @FXML
    private Label txtExpire;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String strSelectedItem = cmbSelectLength.getSelectionModel().getSelectedItem();
        String strDate = txtSystDate.getText();
        createEncoderString(strSelectedItem, strDate);
    }

    @FXML
    private void handleComboBox(ActionEvent event) {
    }
}
