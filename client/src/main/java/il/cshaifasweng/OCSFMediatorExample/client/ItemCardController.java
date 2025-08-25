package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ItemCardController {
    @FXML private ImageView itemImage;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;

    private Item item;

    public void setData(Item item) {
        this.item = item;
        nameLabel.setText(item.getName());
        priceLabel.setText(String.format("%.2f₪", item.getPrice()));
        if (item.getImageData() != null) {
            itemImage.setImage(new Image(new ByteArrayInputStream(item.getImageData())));
        }
    }

    @FXML
    private void handleClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("item_detail.fxml"));
            VBox root = loader.load();
            ItemDetailController controller = loader.getController();
            controller.setItem(item);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle(item.getName());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}