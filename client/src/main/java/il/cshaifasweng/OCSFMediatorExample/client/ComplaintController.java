package il.cshaifasweng.OCSFMediatorExample.client;


import java.awt.*;
import java.awt.Dialog;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.RemovedProduct;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.hibernate.sql.Update;

import static com.sun.xml.bind.v2.schemagen.Util.equal;

public class  ComplaintController{

    @FXML
    private Button cancelcomp;

    @FXML
    private TextField comptxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private TextField nametxt;

    @FXML
    private Button submitcomp;

    @FXML
    private TextField topictxt;

    @FXML
    void cancelcmp(ActionEvent event){
        Stage stage = (Stage) cancelcomp.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void subcomp(ActionEvent event){
        Stage stage = (Stage) submitcomp.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void initialize() throws MalformedURLException
    {
    }



}
