package com.dhl.Controllers;

import com.dhl.Data.InputCSVData;
import com.dhl.PDFConvertToCSV.TurnitInPDFReportProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private Button SelectInputDicID;
    @FXML
    private Button SelectHWBDicID;
    @FXML
    private Button SelectOutputDicID;
    @FXML
    private Button GenerateCsvID;
    @FXML
    private TextField inputDirPathID;
    @FXML
    private TextField batchHWBQueryID;
    @FXML
    private TextField outputDirPathID;
    @FXML
    private Label ActionTarget;

    public static String inputDirPath;
    public static String hwbDirPath;
    public static String outputDirPath;

//    Logger LOG = LoggerFactory.getLogger(Controller.class);

    @FXML
    public void SelectInputDicAction() {
        DirectoryChooser inputDirChooser = new DirectoryChooser();
        inputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = inputDirChooser.showDialog((Stage) SelectInputDicID.getScene().getWindow());
        if (selectedDirectory != null) {
            inputDirPath = selectedDirectory.getAbsolutePath();
            if (!inputDirPath.equals("")) {
                inputDirPathID.setText(inputDirPath);
//                LOG.info("选择PDF路径:" + inputDirPath);
            }
        }
    }

    @FXML
    public void SelectHWBDicAction() {
        DirectoryChooser inputDirChooser = new DirectoryChooser();
        inputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = inputDirChooser.showDialog((Stage) SelectHWBDicID.getScene().getWindow());
        if (selectedDirectory != null) {
            hwbDirPath = selectedDirectory.getAbsolutePath();
            // 检索当前文件夹下的hwb文件
            File dir = new File(hwbDirPath);
            File[] directoryListing = dir.listFiles();
            String hwbFileName = "";
            int count = 0;
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    int extentionIndex = child.getName().lastIndexOf(".");
                    if (extentionIndex == -1 || !child.getName().contains("Batch HWB Query")) {
//                    LOG.info("当前文件不是pdf");
                        continue;
                    }
                    if (child.getName().contains("Batch HWB Query")) {
                        hwbFileName = child.getName();
                        count++;
                    }
                }
            } else {
                if (!dir.isDirectory()) {
//                LOG.info("Not a directory");
                    System.out.println("Not a directory");
                    ActionTarget.setText("Not a directory");
                }
            }
            hwbDirPath = hwbDirPath + "\\" + hwbFileName;
            if (count == 1) {
                batchHWBQueryID.setText(hwbDirPath);
//                LOG.info("选择PDF路径:" + inputDirPath);
            } else if (count < 1) {
                ActionTarget.setText("请确认当前文件夹是否有[Batch HWB Query_.....xlsx] ! !");
            } else {
                ActionTarget.setText("请确认[Batch HWB Query_.....xlsx]的数量 ! !");
            }
        }
    }

    @FXML
    public void SelectOutputDicAction() {
        DirectoryChooser outputDirChooser = new DirectoryChooser();
        outputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = outputDirChooser.showDialog((Stage) SelectOutputDicID.getScene().getWindow());
        if (selectedDirectory != null) {
            outputDirPath = selectedDirectory.getAbsolutePath();
            if (!outputDirPath.equals("")) {
                outputDirPathID.setText(outputDirPath);
//                LOG.info("选择CSV路径:" + inputDirPath);
            }
        }
    }

    @FXML
    public void ConvertPDFTOCSV() {
        if (!inputDirPath.equals("") && !hwbDirPath.equals("") && !outputDirPath.equals("")) {
//            LOG.info("开始转换");
            ActionTarget.setText("Processing...");
            TurnitInPDFReportProcessor pdfProcessor = new TurnitInPDFReportProcessor(inputDirPath, hwbDirPath, outputDirPath);
            pdfProcessor.startProcess();
            ActionTarget.setText("Done!");
        } else {
            ActionTarget.setText("Please specify input/hwbDirPath/output directories.");
        }
    }
}
