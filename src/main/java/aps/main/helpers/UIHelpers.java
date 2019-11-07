/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.main.helpers;

import aps.data_structures.IComparable;
import aps.data_structures.IEquatable;
import aps.data_structures.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author cmlima
 */
public class UIHelpers {
    
    private static int colorIndex = -1;
    private static final String[] COLORS = {
        "#D32F2F", // red darken-2 
        "#7B1FA2", // purple darken-2 
        "#303F9F", // indigo darken-2
        "#0288D1", // light-blue darken-2
        "#00796B", // teal darken-2
        "#689F38", // light-green darken-2
        "#FBC02D", // yellow darken-2
        "#F57C00", // orange darken-2
        "#5D4037", // brown darken-2
        "#C2185B", // pink darken-2
        "#512DA8", // deep-purple darken-2
        "#1976D2", // blue darken-2
        "#0097A7", // cyan darken-2
        "#388E3C", // green darken-2
        "#AFB42B", // lime darken-2
        "#FFA000", // amber darken-2
        "#E64A19", // deep-orange darken-2
        "#455A64", // blue-grey darken-2
        "#616161"  // grey darken-2
    };

    public static void showErrorMessage(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();    
    } 

    public static <T extends IComparable & IEquatable> void setTableData(List<T> list, TableView<T> table) {
        ObservableList data = FXCollections.observableArrayList();
        for (int i = 0; i < list.getSize(); i++) {
            data.add(list.get(i));
        }
        table.setItems(data);
    }
    
    public static <T extends IComparable & IEquatable> List<T> getTableData(TableView<T> table) {
        ObservableList<T> oList = table.getItems();
        List<T> list = new List<>();
        for (int i = 0; i < oList.size(); i++) {
            list.add(oList.get(i));
        }
        return list;
    }
    
    public static String getColor() {
        return COLORS[++colorIndex % COLORS.length];
    }
}
