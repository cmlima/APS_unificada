/**
 * Sample Skeleton for 'AddProcess.fxml' Controller Class
 */

package aps.main.addProcess;

import aps.main.helpers.UIHelpers;
import aps.scheduler.Burst;
import aps.scheduler.PCB;
import aps.data_structures.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProcessController {
    
    private boolean cancelled = true;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="spn_arrival"
    private Spinner<Integer> spn_arrival; // Value injected by FXMLLoader

    @FXML // fx:id="spn_priority"
    private Spinner<Integer> spn_priority; // Value injected by FXMLLoader

    @FXML // fx:id="table"
    private TableView<Burst> table; // Value injected by FXMLLoader

    @FXML // fx:id="col_burst"
    private TableColumn<Burst, Integer> col_burst; // Value injected by FXMLLoader

    @FXML // fx:id="col_cpu"
    private TableColumn<Burst, Boolean> col_cpu; // Value injected by FXMLLoader

    @FXML // fx:id="col_io"
    private TableColumn<Burst, Boolean> col_io; // Value injected by FXMLLoader

    @FXML // fx:id="spn_bursts"
    private Spinner<Integer> spn_bursts; // Value injected by FXMLLoader

    @FXML // fx:id="btn_done"
    private Button btn_done; // Value injected by FXMLLoader

    @FXML
    void handleDone(ActionEvent event) {
        this.cancelled = false;
        ((Stage)this.btn_done.getScene().getWindow()).close();
    }
    
    void handleBurstsChange(int bursts) {
        int rows = this.table.getItems().size();
        if (bursts > 0 && rows != bursts) {
            int diff = Math.abs(rows - bursts);
            List<Burst> list = UIHelpers.getTableData(table);
            for (int i = 0; i < diff; i++) {
                if (bursts > rows) {
                    list.add(new Burst(list.getSize()));
                } else {
                    list.removeAt(list.getSize() - 1);
                }
            }
            UIHelpers.setTableData(list, table); 
        }        
    }
    
    public PCB getData() {
        List<Burst> bursts = UIHelpers.getTableData(table);
        int arrival = this.spn_arrival.valueProperty().getValue();
        int priority = this.spn_priority.valueProperty().getValue();
        int duration = bursts.getSize();
        if (this.cancelled || duration == 0) {
            return null;
        }
        int counter = 0;
        for (int i = 0; i < duration; i++) {
            if (bursts.get(i).isIo()) {
                counter++;
            }
        }
        int ioRequests[] = new int[counter];
        int index = 0;
        for (int i = 0; i < duration; i++) {
            if (bursts.get(i).isIo()) {
                ioRequests[index++] = bursts.get(i).getTime() + 1;
            }
        }
        return new PCB(arrival, duration, ioRequests, priority);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert spn_arrival != null : "fx:id=\"spn_arrival\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert spn_priority != null : "fx:id=\"spn_priority\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert col_burst != null : "fx:id=\"col_burst\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert col_cpu != null : "fx:id=\"col_cpu\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert col_io != null : "fx:id=\"col_io\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert spn_bursts != null : "fx:id=\"spn_bursts\" was not injected: check your FXML file 'AddProcess.fxml'.";
        assert btn_done != null : "fx:id=\"btn_done\" was not injected: check your FXML file 'AddProcess.fxml'.";
        this.col_burst.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.col_cpu.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        this.col_io.setCellValueFactory(new PropertyValueFactory<>("io"));
        this.col_burst.setCellFactory(param -> {
            var cell = new TableCell<Burst, Integer>() {
                @Override
                public void updateItem(Integer time, boolean empty) {
                    if (empty || time == null) {
                        setText(null);
                    } else {
                        setText(Integer.toString(getIndex() + 1));
                    }                    
                }
            };
            cell.setAlignment(Pos.CENTER);           
            return cell ;
        });        
        this.col_cpu.setCellFactory(param -> {
            var checkBox = new CheckBox();
            var cell = new TableCell<Burst, Boolean>() {
                @Override
                public void updateItem(Boolean checked, boolean empty) {
                    if (empty || checked == null) {
                        setGraphic(null);
                        this.setStyle("-fx-background-color: transparent;");
                    } else {
                        String color = checked ? "#B3E5FC" : "transparent";
                        this.setStyle("-fx-text-fill: white; -fx-background-color: " + color + ";");
                        checkBox.setSelected(checked);
                        setGraphic(checkBox);
                    }                    
                }
            };
            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                Burst burst = ((Burst)cell.getTableRow().getItem());
                if (burst != null) {
                    burst.setCpu(isSelected);                
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);           
            return cell ;
        });        
        this.col_io.setCellFactory(param -> {
            var checkBox = new CheckBox();
            var cell = new TableCell<Burst, Boolean>() {
                @Override
                public void updateItem(Boolean checked, boolean empty) {
                    if (empty || checked == null) {
                        setGraphic(null);
                    } else {
                        String color = checked ? "#B3E5FC" : "transparent";
                        this.setStyle("-fx-text-fill: white; -fx-background-color: " + color + ";");
                        checkBox.setSelected(checked);
                        setGraphic(checkBox);
                    }                    
                }
            };
            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                Burst burst = ((Burst)cell.getTableRow().getItem());
                if (burst != null) {
                    burst.setIo(isSelected);                
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);           
            return cell ;
        });        
        this.col_burst.setStyle( "-fx-alignment: CENTER;");
        this.col_cpu.setStyle( "-fx-alignment: CENTER;");
        this.col_io.setStyle( "-fx-alignment: CENTER;");
        this.spn_priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 0));
        this.spn_arrival.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
        this.spn_bursts.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));
        this.handleBurstsChange(1);
        this.spn_bursts.valueProperty().addListener((obs, oldValue, newValue) -> {
            this.handleBurstsChange(newValue);
        });
    }
}