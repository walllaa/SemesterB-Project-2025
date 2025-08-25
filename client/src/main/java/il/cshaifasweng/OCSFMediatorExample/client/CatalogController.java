package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogController {
    @FXML private GridPane catalogGrid;
    @FXML private ComboBox<String> categoryCombo;

    private List<Item> allItems = new ArrayList<>();

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("getCatalog");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onCatalogReceived(CatalogItemsEvent event) {
        allItems = event.getItems();
        categoryCombo.getItems().clear();
        categoryCombo.getItems().add("All");
        categoryCombo.getItems().addAll(allItems.stream().map(Item::getCategory).distinct().collect(Collectors.toList()));
        categoryCombo.getSelectionModel().selectFirst();
        updateCatalogDisplay(allItems);
    }

    @FXML
    public void onCategorySelected(ActionEvent e) {
        String category = categoryCombo.getValue();
        List<Item> filtered = "All".equals(category) ? allItems : allItems.stream().filter(i -> i.getCategory().equals(category)).collect(Collectors.toList());
        updateCatalogDisplay(filtered);
    }

    private void updateCatalogDisplay(List<Item> items) {
        catalogGrid.getChildren().clear();
        int col = 0;
        int row = 0;
        for (Item item : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("item_card.fxml"));
                AnchorPane card = loader.load();
                ItemCardController controller = loader.getController();
                controller.setData(item);
                catalogGrid.add(card, col, row);
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
