package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ItemDetailController {
    @FXML private ImageView detailImage;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;

    public void setItem(Item item) {
        nameLabel.setText(item.getName());
        priceLabel.setText(String.format("%.2f₪", item.getPrice()));
        descriptionLabel.setText(item.getDescription());
        if (item.getImageData() != null) {
            detailImage.setImage(new Image(new ByteArrayInputStream(item.getImageData())));
        }
    }
}