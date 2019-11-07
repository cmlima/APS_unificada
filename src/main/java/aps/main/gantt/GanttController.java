/**
 * Sample Skeleton for 'Gantt.fxml' Controller Class
 */

package aps.main.gantt;

import aps.main.helpers.UIHelpers;
import aps.scheduler.ALGORITHM;
import aps.scheduler.PCB;
import aps.scheduler.Scheduler;
import aps.data_structures.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GanttController {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tab_log"
    private Tab tab_log; // Value injected by FXMLLoader

    @FXML // fx:id="pane_log"
    private AnchorPane pane_log; // Value injected by FXMLLoader

    @FXML // fx:id="txt_log"
    private TextArea txt_log; // Value injected by FXMLLoader

    @FXML // fx:id="tab_gantt"
    private Tab tab_gantt; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_algorithm"
    private Label lbl_algorithm; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_turnaround"
    private Label lbl_turnaround; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_waiting"
    private Label lbl_waiting; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_quantum"
    private Label lbl_quantum; // Value injected by FXMLLoader

    @FXML // fx:id="pane_grid"
    private ScrollPane pane_grid; // Value injected by FXMLLoader

    @FXML // fx:id="pane_gantt"
    private AnchorPane pane_gantt; // Value injected by FXMLLoader

    @FXML // fx:id="split_pane_main"
    private SplitPane split_pane_main; // Value injected by FXMLLoader

    @FXML // fx:id="anchor_pane_side"
    private AnchorPane anchor_pane_side; // Value injected by FXMLLoader

    @FXML // fx:id="pane_info"
    private AnchorPane pane_info; // Value injected by FXMLLoader

    @FXML // fx:id="split_pane_chart"
    private SplitPane split_pane_chart; // Value injected by FXMLLoader

    @FXML
    private TableView<PCB> table;
    
    @FXML
    private TableColumn<PCB, String> col_PID;

    @FXML
    private TableColumn<PCB, Integer> col_arrival;

    @FXML
    private TableColumn<PCB, Integer> col_duration;

    @FXML
    private TableColumn<PCB, Integer> col_priority;

    @FXML
    private TableColumn<PCB, String> col_iorequests;
    
    @FXML
    private TableColumn<PCB, Integer> col_turnaround;

    @FXML
    private TableColumn<PCB, Integer> col_waiting;

    public void setData(ALGORITHM algorithm, List<PCB> pcbs, int quantum) {
        var scheduler = new Scheduler(algorithm);
        scheduler.addProcesses(pcbs);
        scheduler.setQuantum(quantum);
        scheduler.execute();            
        this.setLog(scheduler);
        this.setInfo(scheduler);
        UIHelpers.setTableData(scheduler.getCompletedList(), this.table);
        this.renderGrid(scheduler);
        this.renderChart(scheduler);
    }
    
    private void setLog(Scheduler scheduler) {
        var builder = new StringBuilder();
        builder.append(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN ? "ROUND ROBIN" : "PRIORITY PREEMPTIVE");
        builder.append("\n");
        if (scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN) {
            builder.append("Quantum: ").append(scheduler.getQuantum()).append("\t");
        }
        builder.append("Avg. Turnaround: ").append(String.format("%.2f", scheduler.getAvgTurnaround())).append("\t");
        builder.append("Avg. Waiting: ").append(String.format("%.2f", scheduler.getAvgWaiting())).append("\n");
        builder.append("\n");
        builder.append(scheduler.getLogAsString());
        this.txt_log.setText(builder.toString());
    }
    
    private void setInfo(Scheduler scheduler) {
        this.lbl_algorithm.setText(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN ? "Round Robin" : "Priority Preemptive");
        this.lbl_turnaround.setText("Avg. Turnaround:\t" + String.format("%.2f", scheduler.getAvgTurnaround()));
        this.lbl_waiting.setText("Avg. Waiting:\t\t" + String.format("%.2f", scheduler.getAvgWaiting()));
        this.lbl_quantum.setText("Quantum:\t\t\t" + scheduler.getQuantum());
        this.lbl_quantum.setVisible(scheduler.getAlgorithm() == ALGORITHM.ROUND_ROBIN);
    }
    
    private void renderGrid(Scheduler scheduler) {
        
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(5, 10, 5, 10));
        
        var data = scheduler.getTimeLine();
        
        for (int i = 0; i < data.getSize(); i++) {
            var item = data.get(i);
            var col = new ColumnConstraints();
            col.setMinWidth(70.0 * (item.getEnd() - item.getStart()));
            grid.getColumnConstraints().add(col);
        }
        
        var lastCol = new ColumnConstraints();
        lastCol.setMinWidth(10);
        grid.getColumnConstraints().add(lastCol);
        
        for (int i = 0; i < data.getSize(); i++) {
            
            var item = data.get(i);
            
            Text pid = new Text(item.getPID());
            pid.setFont(Font.font("Arial", FontWeight.THIN, 12));
            pid.setTextAlignment(TextAlignment.CENTER);
            grid.add(pid, i, 0);

            StackPane chartPane = new StackPane();
            if (!item.isIdle()) {
                chartPane.setStyle("-fx-background-color: " + item.getColor());
            }
            Text empty = new Text(" ");
            GridPane.setFillHeight(chartPane, true);
            GridPane.setFillWidth(chartPane, true);
            chartPane.getChildren().addAll(empty);
            grid.add(chartPane, i, 1);

            Text burst = new Text(Integer.toString(item.getStart()));
            burst.setFont(Font.font("Arial", FontWeight.THIN, 12));
            burst.setTextAlignment(TextAlignment.LEFT);
            GridPane.setMargin(burst, new Insets(2, 5, 2, -3));
            grid.add(burst, i, 2);
            if (i == data.getSize() - 1) {
                Text end = new Text(Integer.toString(item.getEnd()));
                end.setFont(Font.font("Arial", FontWeight.THIN, 12));
                end.setTextAlignment(TextAlignment.LEFT);
                GridPane.setMargin(end, new Insets(2, 5, 2, -3));
                grid.add(end, i + 1, 2);                
            }
        }
        this.pane_grid.setContent(grid);
    }

    private void renderChart(Scheduler scheduler) {
        
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final var chart = new GanttChart<Number,String>(xAxis,yAxis);
        
        var data = scheduler.getTimeLine();
        
        for (int i = 0; i < data.getSize(); i++) {
            
            var item = data.get(i);
            XYChart.Series series = new XYChart.Series();
                String title = item.getPID();
                
            if (scheduler.getAlgorithm() == ALGORITHM.PRIORITY_PREEMPTIVE) {
                title += "\n(" + item.getPriority() + ")";
            }
            series.getData().add(new XYChart.Data(item.getStart(), title, new GanttChart.ExtraData(item.getEnd() - item.getStart(), item.getColor())));
            
            chart.getData().add(series);
        }
        chart.setLegendVisible(false);
        this.pane_gantt.getChildren().addAll(chart);
        AnchorPane.setBottomAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 0.0);
        AnchorPane.setTopAnchor(chart, 0.0);
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tab_log != null : "fx:id=\"tab_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_log != null : "fx:id=\"pane_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert txt_log != null : "fx:id=\"txt_log\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert tab_gantt != null : "fx:id=\"tab_gantt\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_algorithm != null : "fx:id=\"lbl_algorithm\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_turnaround != null : "fx:id=\"lbl_turnaround\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_waiting != null : "fx:id=\"lbl_waiting\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert lbl_quantum != null : "fx:id=\"lbl_quantum\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_grid != null : "fx:id=\"pane_grid\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_gantt != null : "fx:id=\"pane_gantt\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert split_pane_main != null : "fx:id=\"split_pane_main\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert anchor_pane_side != null : "fx:id=\"anchor_pane_side\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert pane_info != null : "fx:id=\"pane_info\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert split_pane_chart != null : "fx:id=\"split_pane_chart\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_PID != null : "fx:id=\"col_PID\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_turnaround != null : "fx:id=\"col_turnaround\" was not injected: check your FXML file 'Gantt.fxml'.";
        assert col_waiting != null : "fx:id=\"col_waiting\" was not injected: check your FXML file 'Gantt.fxml'.";
        SplitPane.setResizableWithParent(this.anchor_pane_side, Boolean.FALSE);
        SplitPane.setResizableWithParent(this.pane_info, Boolean.FALSE);
        SplitPane.setResizableWithParent(this.pane_grid, Boolean.FALSE);
        this.col_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        this.col_arrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        this.col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        this.col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        this.col_iorequests.setCellValueFactory(new PropertyValueFactory<>("ioRequestsString"));
        this.col_turnaround.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        this.col_waiting.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        this.col_PID.setStyle( "-fx-alignment: CENTER;");        
        this.col_PID.setCellFactory((TableColumn<PCB, String> col) -> new TableCell<PCB, String>() {
            @Override public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    String color = col.getTableView().getItems().get(indexProperty().getValue()).getColor();
                    this.setStyle("-fx-text-fill: white; -fx-background-color: " + color + ";");
                }
            }
        });
        this.col_arrival.setStyle( "-fx-alignment: CENTER;");        
        this.col_duration.setStyle( "-fx-alignment: CENTER;");        
        this.col_priority.setStyle( "-fx-alignment: CENTER;");        
        this.col_iorequests.setStyle( "-fx-alignment: CENTER;");
        this.col_turnaround.setStyle( "-fx-alignment: CENTER;");        
        this.col_waiting.setStyle( "-fx-alignment: CENTER;");
    }
}