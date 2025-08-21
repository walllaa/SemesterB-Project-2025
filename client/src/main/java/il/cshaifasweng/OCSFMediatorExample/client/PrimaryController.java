package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.awt.*;
import java.awt.Dialog;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.EventBus;
import org.hibernate.sql.Update;

import static com.sun.xml.bind.v2.schemagen.Util.equal;


public class PrimaryController {
	public int flowersnum2 = 6;
	public int workernum2 =0;
	public int managernum2 =0;
	static boolean returnedFromSecondaryController = false;
	boolean firstRun = true;
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="adminEditCatalog"
	private ComboBox<String> adminEditCatalog; // Value injected by FXMLLoader

	@FXML // fx:id="adminEditCatalog"
	private ComboBox<String> worker_edit; // Value injected by FXMLLoader

	@FXML // fx:id="logout"
	private Button logout; // Value injected by FXMLLoader


	@FXML
	private Button checkout;


	@FXML
	private AnchorPane container1;

	@FXML
	private AnchorPane container2;

	@FXML
	private AnchorPane container3;

	@FXML
	private AnchorPane container4;

	@FXML
	private AnchorPane container5;

	@FXML
	private AnchorPane container6;

	@FXML
	private AnchorPane init_container;

	@FXML // fx:id="EditItemDesc"
	private TextField EditItemDesc; // Value injected by FXMLLoader

	@FXML // fx:id="EditItemPrice"
	private TextField EditItemPrice; // Value injected by FXMLLoader

	@FXML // fx:id="EditItemType"
	private TextField EditItemType; // Value injected by FXMLLoader


	@FXML // fx:id="EditItemExtra"
	private TextField EditItemExtra; // Value injected by FXMLLoader

	@FXML // fx:id="customColor"
	private TextField customColor; // Value injected by FXMLLoader

	@FXML // fx:id="customPrice"
	private TextField customPrice; // Value injected by FXMLLoader

	@FXML
	private TextField customid;

	@FXML // fx:id="customType"
	private TextField customType; // Value injected by FXMLLoader

	@FXML // fx:id="CreateCustomItem"
	public Button CreateCustomItem; // Value injected by FXMLLoader@FXML // fx:id="CreateCustomItem"

	@FXML
	public Button infoo;
	@FXML
	public Button cartt; // Value injected by FXMLLoader

	@FXML // fx:id="CancelCustomItem"
	private Button CancelCustomItem; // Value injected by FXMLLoader

	@FXML // fx:id="FinishCustomItem"
	private Button FinishCustomItem; // Value injected by FXMLLoader


	@FXML // fx:id="flower_button1"
	private ImageView flower_button1; // Value injected by FXMLLoader

	@FXML // fx:id="flower_button2"
	private ImageView flower_button2; // Value injected by FXMLLoader

	@FXML // fx:id="flower_button3"
	private ImageView flower_button3; // Value injected by FXMLLoader

	@FXML // fx:id="flower_button4"
	private ImageView flower_button4; // Value injected by FXMLLoader

	@FXML // fx:id="flower_button5"
	private ImageView flower_button5; // Value injected by FXMLLoader

	@FXML // fx:id="flower_button6"
	private ImageView flower_button6; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name1"
	private javafx.scene.control.Label flower_name1; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name2"
	private javafx.scene.control.Label flower_name2; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name3"
	private javafx.scene.control.Label flower_name3; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name4"
	private javafx.scene.control.Label flower_name4; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name5"
	private javafx.scene.control.Label flower_name5; // Value injected by FXMLLoader

	@FXML // fx:id="flower_name6"
	private javafx.scene.control.Label flower_name6; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price1"
	private DialogPane flower_price1; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price2"
	private DialogPane flower_price2; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price3"
	private DialogPane flower_price3; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price4"
	private DialogPane flower_price4; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price5"
	private DialogPane flower_price5; // Value injected by FXMLLoader

	@FXML // fx:id="flower_price6"
	private DialogPane flower_price6; // Value injected by FXMLLoader


	@FXML // fx:id="AddItem"
	private Button AddItem; // Value injected by FXMLLoader

	//@FXML // fx:id="AddItem"
	//private Button remID;

	@FXML // fx:id="RemoveItem"
	private Button RemoveItem; // Value injected by FXMLLoader

	@FXML // fx:id="UpdateItem"
	private Button UpdateItem; // Value injected by FXMLLoader

	@FXML
	private Button printProd;

	@FXML
	private Button justButton;

	@FXML
	private Text justText;

	@FXML
	private Button nextPage;

	@FXML
	private Button prevPage;


	@FXML
	private ComboBox<String> chooseCustomColor;

	@FXML
	private ComboBox<String> chooseCustomType;

	@FXML
	private Button flower1_addCart;

	@FXML
	private Button flower2_addCart;

	@FXML
	private Button flower3_addCart;

	@FXML
	private Button flower4_addCart;

	@FXML
	private Button flower5_addCart;

	@FXML
	private Button flower6_addCart;

	@FXML
	private Button viewCart;

	@FXML
	private TextField cartTextDiscount;

	@FXML
	private TextField cartTextPrice;

	@FXML
	private Text cartTextPriceDiscount;

	@FXML
	private Text cartTextPriceFinal;


	@FXML // fx:id="customError"
	private Text customError; // Value injected by FXMLLoader

	@FXML
	private Text cartTopText;

	@FXML
	private Button viewMyComplaints;

	@FXML
	private Button viewMyOrders;


	@FXML
	private ListView<String> CartItemsList;


	@FXML // fx:id="adminControlButtton"
	private Button adminControlButtton; // Value injected by FXMLLoader

	@FXML // fx:id="openComplaints"
	private Button openComplaints; // Value injected by FXMLLoader


	@FXML // fx:id="inboxList"
	private ListView<String> inboxList; // Value injected by FXMLLoader

	@FXML // fx:id="openMessage"
	private Button openMessage; // Value injected by FXMLLoader

	@FXML // fx:id="viewInboxPlz"
	private Button viewInboxPlz; // Value injected by FXMLLoader

	@FXML // fx:id="deliveryButton"
	private Button deliveryButton; // Value injected by FXMLLoader

	@FXML // fx:id="messageField"
	private TextField messageField; // Value injected by FXMLLoader

	@FXML
	void viewMessage(ActionEvent event)
	{
		System.out.println("viewMessage 1");
		messageField.setVisible(true);
		int i;
		String bb = "";
		String aa = inboxList.getSelectionModel().getSelectedItem();
		int selected = 0;
		for(i = 0 ; aa.charAt(i) != '-'; i++)
		{
		}
		i = i + 2;
		for(i = i ; i < aa.length() ; i++)
		{
			bb = bb + Character.toString(aa.charAt(i));
		}
		selected = Integer.parseInt(bb);
		System.out.println("Selected =  " + selected);
		for(i = 0 ; i < MessageList.size() ; i++)
		{
			if(MessageList.get(i).getMessageID() == selected)
			{
				messageField.setText(MessageList.get(i).getMsgText());
				System.out.println("Setting Text : " +MessageList.get(i).getMsgText());
				break;
			}
		}

	}

	@FXML
	void openInbox(ActionEvent event) {

		inboxList.getItems().clear();
		if(viewInboxPlz.getText().equals("Open Inbox"))
		{
			String MsgTemp = "";
			for (int i = 0; i < MessageList.size(); i++)
			{
				if (MessageList.get(i).getCustomerID() == currentLoggedAccount.getAccountID())
				{
					MsgTemp = "";
					int j = 0;
					while (MessageList.get(i).getMsgText().charAt(j) != ':') {
						MsgTemp = MsgTemp + MessageList.get(i).getMsgText().charAt(j);
						j++;
					}
					inboxList.getItems().add(MsgTemp);
				}
			}
			viewInboxPlz.setText("Close Inbox");
			inboxList.setVisible(true);
			openMessage.setVisible(true);

		}
		else
		{
			viewInboxPlz.setText("Open Inbox");
			inboxList.setVisible(false);
			openMessage.setVisible(false);
		}

	}

	@FXML
	void goLogOut(ActionEvent event) throws IOException {



		LogOut logOutObject = new LogOut();
		logOutObject.setMail(currentLoggedAccount.getEmail());

		try {
			System.out.println("before sending the logout object" );
			SimpleClient.getClient().sendToServer(logOutObject);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPrim.fxml"));
		Parent roott = loader.load();
		LogInPrimary cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Welcome");
		stage.show();
		Stage stagee = (Stage)logout.getScene().getWindow();
		stagee.close();

	}

	@FXML
	void ReplyToComplaints(ActionEvent event) throws IOException {

		PassAccountEventReplyComplaint recievedAcc = new PassAccountEventReplyComplaint(currentLoggedAccount);

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("replycomplaint.fxml"));
		Parent roott = loader.load();
		ReplyComplaintController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Reply Complaint");
		stage.show();
		Stage stagee = (Stage)openComplaints.getScene().getWindow();
		stagee.close();

	}


	@FXML
	void openControlPanel(ActionEvent event) throws IOException {

		PassAccountEventAdmin recievedAcc = new PassAccountEventAdmin(currentLoggedAccount);

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("admincontrol.fxml"));
		Parent roott = loader.load();
		AdminControlController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Delivery Panel");
		stage.show();
		Stage stagee = (Stage)adminControlButtton.getScene().getWindow();
		stagee.close();
	}

	@FXML
	void addToCartFlower1(ActionEvent event)
	{
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower2(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex+1).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex+1).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex+1));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower3(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex+2).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex+2).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex+2));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower4(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex+3).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex+3).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex+3));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower5(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex+4).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex+4).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex+4));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower6(ActionEvent event) 	{
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex+5).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex+5).getName());
		basePrice = basePrice + addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex+5));
		if(currentLoggedAccount.isSubscription() == true && basePrice > 50)
			cartTextDiscount.setText("" + basePrice*0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}
	@FXML
	private TextField cartText;

	@FXML
	void openDelivery(ActionEvent event) throws IOException
	{
		PassAccountEventDelivery recievedAcc = new PassAccountEventDelivery(currentLoggedAccount);
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("delivery.fxml"));
		Parent roott = loader.load();
		DeliveryController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Delivery Panel");
		stage.show();
		Stage stagee = (Stage)infoo.getScene().getWindow();
		stagee.close();
	}


	int cartViewBinary = 0;
	@FXML
	void viewUserCart(ActionEvent event)
	{
		boolean mode;
		if(cartViewBinary == 0)
		{
			nextPage.setVisible(false);
			prevPage.setVisible(false);
			mode = false;
			cartViewBinary++;
			viewCart.setText("Close Cart");

			CartItemsList.setVisible(true);
			cartTopText.setVisible(true);
			cartTextPrice.setVisible(true);
			cartTextDiscount.setVisible(true);
			cartTextPriceDiscount.setVisible(true);
			cartTextPriceFinal.setVisible(true);
		}
		else
		{
			nextPage.setVisible(true);
			prevPage.setVisible(true);
			mode = true;
			cartViewBinary--;
			viewCart.setText("View Cart");

			CartItemsList.setVisible(false);
			cartTopText.setVisible(false);
			cartTextPrice.setVisible(false);
			cartTextDiscount.setVisible(false);
			cartTextPriceDiscount.setVisible(false);
			cartTextPriceFinal.setVisible(false);
		}
		ViewItems(mode);
		viewAdminGUI(mode);
		CartItemsList.setVisible(!mode);
	}
	@FXML
	void openComplaintManager(ActionEvent event) throws IOException {

		GetAllComplaints allComplaints = new GetAllComplaints();
		System.out.println("send request for complaints !!");
		try {
			System.out.println("before sending the getAllComplaints " );
			SimpleClient.getClient().sendToServer(allComplaints);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// added 30/7
		PassAccountEventLogManager recievedAcc = new PassAccountEventLogManager(currentLoggedAccount);

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);


		FXMLLoader loader = new FXMLLoader(getClass().getResource("log_manager.fxml"));
		Parent roott = loader.load();
		LogManagerController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Complaint Manager");
		stage.show();
		Stage stagee = (Stage)infoo.getScene().getWindow();
		stagee.close();
	}
	@FXML
	void openCheckout(ActionEvent event) throws IOException
	{
		System.out.println("arrived to checkout 1");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
		Parent roott = loader.load();
		CheckoutController cc = loader.getController();
		System.out.println("arrived to checkout 2");
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Checkout");
		stage.show();
		Stage stagee = (Stage)checkout.getScene().getWindow();
		System.out.println("arrived to checkout 3");
		stagee.close();
		PassAccountEventCheckout recievedAcc = new PassAccountEventCheckout(currentLoggedAccount);
		recievedAcc.productsToCheckout = userCart;

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

		System.out.println("arrived to checkout 4 (posted the event)");
	}


	@Subscribe
	public void messageEvent(passAllMessagesEvent allMessages){ // added new 21/7
		System.out.println("arrived to MESSAGE Event Subscriber in primary!!!!!");
		List<Message> recievedMessagess = allMessages.getMessagesToPassToPass();

		for(int i=0;i<recievedMessagess.size();i++){
			System.out.println(recievedMessagess.get(i));
		}
	}

	@FXML
	void nextPageUpate(ActionEvent event)
	{
		int difference = allProducts.size() - CatalogENDIndex;
		if(difference == 0)
		{

		}
		else
		{
			CatalogSTARTIndex = CatalogENDIndex;
			if (difference < 7) {
				CatalogENDIndex = CatalogENDIndex + difference;
			} else {
				CatalogENDIndex = CatalogENDIndex + 6;
			}
			updateFields(1);
		}
	}


	@FXML
	void openMyComplaints(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mycomplaints.fxml"));
		Parent roott = loader.load();
		MyComplaintsController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("My Complaints");
		stage.show();
		Stage stagee = (Stage)viewMyComplaints.getScene().getWindow();
		stagee.close();

		PassAccountEventComplaints recievedAcc = new PassAccountEventComplaints(currentLoggedAccount);

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

		// added new 30/7
		GetAllComplaints allComplaints = new GetAllComplaints();
		System.out.println("send request for complaints !!");
		try {
			System.out.println("before sending the getAllComplaints " );
			SimpleClient.getClient().sendToServer(allComplaints);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void openMyOrders(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("myorders.fxml"));
		Parent roott = loader.load();
		MyOrdersController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("My Orders");
		stage.show();
		Stage stagee = (Stage)viewMyOrders.getScene().getWindow();
		stagee.close();
		PassAccountEventOrders recievedAcc = new PassAccountEventOrders(currentLoggedAccount);

		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(recievedAcc);
						System.out.println("the server sent me the account , NICE 4 !!");
					}
				},4000
		);

	}


	@FXML
	void prevPageUpate(ActionEvent event)
	{
		int difference = CatalogSTARTIndex - 6;
		if(difference >= 6)
		{
			CatalogENDIndex = CatalogSTARTIndex;
			CatalogSTARTIndex = CatalogSTARTIndex - 7;
		}
		else
		{
			if(difference > -1 )
			{
				CatalogENDIndex = CatalogSTARTIndex;
				CatalogSTARTIndex = 0;
			}
		}
		updateFields(1);
	}
	int justViewMode = 0;
	@FXML
	void justView(ActionEvent event) {
		GetAllMessages allMessages = new GetAllMessages();
		System.out.println("send request for messages !!");
		try {
			System.out.println("before sending the getAllMessagaes " );
			SimpleClient.getClient().sendToServer(allMessages);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init_container.setVisible(false);
		justText.setVisible(false);
		if(justViewMode == 0) {
			updateFields(2);
			justViewMode++;
		}
		else
		{
			updateFields(1);
		}
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run()
					{
						// Unused Timer, Please Keep
					}
				},0
		);
		justText.setVisible(false);
		justButton.setVisible(false);
		System.out.println(catalog_flag.getFlagg());
		System.out.println("Just view 0");
		System.out.println("Current Logged Priv : " + currentLoggedAccount.getPrivialge());
		if(currentLoggedAccount.getPrivialge() == 1)
		{
			System.out.println("Just view 1");
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
			//inboxList.setVisible(true);
			//openMessage.setVisible(true);
			checkout.setVisible(true);
			cartTextPrice.setVisible(true);
			cartTextDiscount.setVisible(true);
			cartTextPriceDiscount.setVisible(true);
			cartTextPriceFinal.setVisible(true);
			CartItemsList.setVisible(true);
			cartTopText.setVisible(true);
			flower1_addCart.setVisible(true);
			flower2_addCart.setVisible(true);
			flower3_addCart.setVisible(true);
			flower4_addCart.setVisible(true);
			flower5_addCart.setVisible(true);
			flower6_addCart.setVisible(true);
			CreateCustomItem.setVisible(true);
		}
		if(currentLoggedAccount.getPrivialge() == 2)
		{
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
			//inboxList.setVisible(true);
			//openMessage.setVisible(true);
			checkout.setVisible(true);
			cartTextPrice.setVisible(true);
			cartTextDiscount.setVisible(true);
			cartTextPriceDiscount.setVisible(true);
			cartTextPriceFinal.setVisible(true);
			CartItemsList.setVisible(true);
			cartTopText.setVisible(true);
			flower1_addCart.setVisible(true);
			flower2_addCart.setVisible(true);
			flower3_addCart.setVisible(true);
			flower4_addCart.setVisible(true);
			flower5_addCart.setVisible(true);
			flower6_addCart.setVisible(true);
			CreateCustomItem.setVisible(true);
			deliveryButton.setVisible(true);
			openComplaints.setVisible(true);
		}
		if(currentLoggedAccount.getPrivialge() == 3)
		{
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
			//inboxList.setVisible(true);
			//openMessage.setVisible(true);
			checkout.setVisible(true);
			cartTextPrice.setVisible(true);
			cartTextDiscount.setVisible(true);
			cartTextPriceDiscount.setVisible(true);
			cartTextPriceFinal.setVisible(true);
			CartItemsList.setVisible(true);
			cartTopText.setVisible(true);
			flower1_addCart.setVisible(true);
			flower2_addCart.setVisible(true);
			flower3_addCart.setVisible(true);
			flower4_addCart.setVisible(true);
			flower5_addCart.setVisible(true);
			flower6_addCart.setVisible(true);
			CreateCustomItem.setVisible(true);
			deliveryButton.setVisible(true);
			openComplaints.setVisible(true);
			infoo.setVisible(true);
			adminControlButtton.setVisible(true);
			adminEditCatalog.setVisible(true);
		}
	}
	@FXML
	void printProducts(ActionEvent event)
	{
		for(int i = 0 ; i < allProducts.size() ; i++)
		{
			System.out.println("ID: " + allProducts.get(i).getID());
			System.out.println("Name: " + allProducts.get(i).getName());
			System.out.println("Price: " + allProducts.get(i).getPrice());
			System.out.println("### END ###");
		}
	}
	public static String current_button;

	@FXML
	Product createCustomitem(ActionEvent event) {

		FinishCustomItem.setText("Add Custom Item To Cart");
		CancelCustomItem.setText("Cancel Custom Item Designer");
		flower_button1.setVisible(true);
		flower_button2.setVisible(true);
		flower_button3.setVisible(true);
		flower_button4.setVisible(true);
		flower_button5.setVisible(true);
		flower_button6.setVisible(true);

		flower_price1.setVisible(true);
		flower_price2.setVisible(true);
		flower_price3.setVisible(true);
		flower_price4.setVisible(true);
		flower_price5.setVisible(true);
		flower_price6.setVisible(true);

		flower_name1.setVisible(true);
		flower_name2.setVisible(true);
		flower_name3.setVisible(true);
		flower_name4.setVisible(true);
		flower_name5.setVisible(true);
		flower_name6.setVisible(true);

		CreateCustomItem.setVisible(false);
		adminEditCatalog.setVisible(false);
		//	remID.setVisible(false);

		EditItemExtra.setVisible(false);
		EditItemDesc.setVisible(false);
		EditItemType.setVisible(false);
		EditItemPrice.setVisible(false);
		AddItem.setVisible(false);
		RemoveItem.setVisible(false);
		UpdateItem.setVisible(false);

		chooseCustomColor.setVisible(true);
		customPrice.setVisible(true);
		customid.setVisible(true);
		chooseCustomType.setVisible(true);

		CancelCustomItem.setVisible(true);
		FinishCustomItem.setVisible(true);

		Product newProduct = new Product(0, "test", "test", "test", "test");  // Please Insert Values
		//CREATE A NEW PRODUCT DYNAMICALLY
		// Tips: A global variable called ProductID which is incremented after each product created.
		// A function GetNextProductID that returns a fresh ID for the new product to be added.
		// Static fields and functions.
		return newProduct;
	}

	@FXML
	void cancelCustomitem(ActionEvent event) {
		//.setVisible(false);
		flower_button1.setVisible(true);
		flower_button2.setVisible(true);
		flower_button3.setVisible(true);
		flower_button4.setVisible(true);
		flower_button5.setVisible(true);
		flower_button6.setVisible(true);

		flower_price1.setVisible(true);
		flower_price2.setVisible(true);
		flower_price3.setVisible(true);
		flower_price4.setVisible(true);
		flower_price5.setVisible(true);
		flower_price6.setVisible(true);

		flower_name1.setVisible(true);
		flower_name2.setVisible(true);
		flower_name3.setVisible(true);
		flower_name4.setVisible(true);
		flower_name5.setVisible(true);
		flower_name6.setVisible(true);

		CreateCustomItem.setVisible(true);
		adminEditCatalog.setVisible(true);

		customid.setVisible(false);
		chooseCustomType.setVisible(false);
		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);
	}

	@FXML
	void addCartCustomitem(ActionEvent event)
	{
		customError.setVisible(false);
		String customPriceString = "";
		boolean fail = false;
		if(chooseCustomType.getSelectionModel().getSelectedIndex() == -1)
		{
			customError.setText("Please choose a type");
			customError.setVisible(true);
			fail = true;
		}
		if(chooseCustomColor.getSelectionModel().getSelectedIndex() == -1)
		{
			customError.setText("Please choose a color");
			customError.setVisible(true);
			fail = true;
		}
		customPriceString = customPrice.getText();
		for(int i = 0 ; i < customPriceString.length() ; i++)
		{
			if(customPriceString.charAt(i) < '0' || customPriceString.charAt(i) > '9')
			{
				fail = true;
				customError.setText("Please enter a valid price");
				customError.setVisible(true);

			}
		}

		if(fail == false)
		{
			String color = chooseCustomColor.getSelectionModel().getSelectedItem();
			String Type = chooseCustomType.getSelectionModel().getSelectedItem();
			String avg = customPrice.getText();
			Product product = new Product(0, "btn", "Custom Item", "A " + chooseCustomType + " With dominant color " + chooseCustomColor, avg);

			int basePrice = Integer.parseInt(cartTextPrice.getText());
			int addedPrice = Integer.parseInt(product.getPrice());
			CartItemsList.getItems().add(product.getName());
			basePrice = basePrice + addedPrice;
			cartTextPrice.setText(String.valueOf(basePrice));
			userCart.add(product);
			if (currentLoggedAccount.isSubscription() == true && basePrice > 50)
				cartTextDiscount.setText("" + basePrice * 0.9);
			else
				cartTextDiscount.setText("" + basePrice);


			customid.setVisible(false);

			CancelCustomItem.setVisible(false);
			FinishCustomItem.setVisible(false);

			chooseCustomType.setVisible(false);
			chooseCustomColor.setVisible(false);
			customPrice.setVisible(false);
			CreateCustomItem.setVisible(true);
			customError.setVisible(false);
		}

	}


	@FXML
	void adminAddItemFunc(ActionEvent event) {

		String newType = EditItemType.getText();
		String newDesc = EditItemDesc.getText();
		String newPrice = EditItemPrice.getText();
		Product new_flower = new Product();
		new_flower.setPrice(newPrice);
		new_flower.setName(newType);
		new_flower.setDetails(newDesc);
		flowersnum2++;
		new_flower.setID(flowersnum2);
		UpdateMessage updateMessage1 = new UpdateMessage("product", "add");
		updateMessage1.setProduct(new_flower);
		updateMessage1.setId(flowersnum2);
		System.out.println("before try - edit");
		try {
			System.out.println("before sending updateMessage to server ");
			SimpleClient.getClient().sendToServer(updateMessage1); // sends the updated product to the server class
			System.out.println("afater sending updateMessage to server ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateCustomItem.setVisible(true);
		adminEditCatalog.setVisible(true);

		//	remID.setVisible(false);
		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		EditItemExtra.setVisible(false);

		if(CatalogENDIndex - CatalogSTARTIndex < 6)
		{
			CatalogENDIndex++;
		}
		init_container.setVisible(true);
		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
		//	AddItem.setVisible(false);
	}

	@FXML
	void adminRemoveItemFunc(ActionEvent event) {

		String deleteID = EditItemExtra.getText();
		// Remove the item with the currnet ID from the catalog
		// create removeItem object
		// give the deleteID to the removeItem object
		// send the object to the server
		//System.out.println(deleteID);
		//	System.out.println(deleteID);
		UpdateMessage removeType = new UpdateMessage("product", "remove");
		removeType.setDelteId(deleteID);

		flowersnum2--;
		try {
			SimpleClient.getClient().sendToServer(removeType); // sends the updated product to the server class
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateCustomItem.setVisible(true);
		adminEditCatalog.setVisible(true);

		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		EditItemType.setVisible(false);
		EditItemDesc.setVisible(false);
		EditItemPrice.setVisible(false);
		EditItemExtra.setVisible(false);
		//	RemoveItem.setVisible(false);
		//.setVisible(false);

		init_container.setVisible(true);
		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
		RemoveItem.setVisible(false);

		allProducts.remove(Integer.parseInt(EditItemExtra.getText())-1);

		updateFields(2);

	}

	@FXML
	void adminUpdateItemFunc(ActionEvent event) {

		String TempType = allProducts.get(0).getName();
		String TempDesc = allProducts.get(0).getDetails();
		String TempPrice = allProducts.get(0).getPrice();

		String newType = EditItemType.getText();
		System.out.println(newType);
		String newDesc = EditItemDesc.getText();
		System.out.println(newDesc);
		String newPrice = EditItemPrice.getText();
		System.out.println(newPrice);
		String updateID = EditItemExtra.getText();
		System.out.println(updateID);

		List<Product> tempList = allProducts;
		int TargerID = Integer.parseInt(updateID) - 1;

		allProducts.get(TargerID).setName(newType);
		allProducts.get(TargerID).setPrice(newPrice);
		allProducts.get(TargerID).setDetails(newDesc);

		Product currtProduct  = il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.getCurrent_button();


		currtProduct.setPrice(newPrice);
		currtProduct.setDetails(newDesc);
		currtProduct.setName(newType);
		int castedID = Integer.parseInt(updateID);
		currtProduct.setID(castedID);

		UpdateMessage updateMessage1 = new UpdateMessage("product","edit");
		updateMessage1.setProduct(currtProduct);


		updateMessage1.setId(castedID);
		System.out.println("arrived here before sending the updatemessage1");
		try {
			SimpleClient.getClient().sendToServer(updateMessage1); // sends the updated product to the server class
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		allProducts.get(0).setName(TempType);
		allProducts.get(0).setPrice(TempPrice);
		allProducts.get(0).setDetails(TempDesc);

		// Update the item with the current ID with the new variables
		//remID.setVisible(false);
		CreateCustomItem.setVisible(true);
		adminEditCatalog.setVisible(true);

		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		EditItemType.setVisible(false);
		EditItemDesc.setVisible(false);
		EditItemPrice.setVisible(false);
		EditItemExtra.setVisible(false);
		//	UpdateItem.setVisible(false);

		updateFields(2);

		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
	}

	@FXML
	void chooseAdminEditCatalog(ActionEvent event) {
		//	remID.setVisible(false);
		CreateCustomItem.setVisible(false);
		adminEditCatalog.setVisible(true);

		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		String chosen = adminEditCatalog.getSelectionModel().getSelectedItem();
		System.out.println(chosen);
		System.out.println("OKAY OKAY");
		if (chosen == "Add Item") {
			EditItemType.setText("New Item Type");
			EditItemDesc.setText("New Item Desc");
			EditItemPrice.setText("New Item Price");
			EditItemType.setVisible(true);
			EditItemDesc.setVisible(true);
			EditItemPrice.setVisible(true);
			EditItemExtra.setVisible(false);
			System.out.println("WENT ADD");
			RemoveItem.setVisible(false);
			UpdateItem.setVisible(false);
			AddItem.setVisible(true);
		}
		if (chosen == "Remove Item") {
			//EditItemExtra.setText("Item ID To Remove");
			//UpdateItem.setText("Remove");
			EditItemType.setVisible(false);
			EditItemDesc.setVisible(false);
			EditItemPrice.setVisible(false);
			EditItemExtra.setVisible(true);
			EditItemExtra.setText("ID to remove");
			//.setVisible(true);
			System.out.println("WENT REMOVE");
			AddItem.setVisible(false);
			UpdateItem.setVisible(false);
			RemoveItem.setVisible(true);
			//send id to server
		}
		if (chosen == "Edit Item") {
			EditItemType.setText("New Item Type");
			EditItemDesc.setText("New Item Desc");
			EditItemPrice.setText("New Item Price");
			EditItemExtra.setText("Item ID To Update");
			EditItemType.setVisible(true);
			EditItemDesc.setVisible(true);
			EditItemPrice.setVisible(true);
			EditItemExtra.setVisible(true);
			System.out.println("WENT UPDATE");
			AddItem.setVisible(false);
			RemoveItem.setVisible(false);
			UpdateItem.setVisible(true);
		}
	}


	int updateFieldsBounds = 0;
	public void updateFields(int mode)
	{
		System.out.println("START INDEX = " + CatalogSTARTIndex);
		System.out.println("END INDEX = " + CatalogENDIndex);
		System.out.println("Length = " + allProducts.size());
		if (mode == 0) {
			flower_name1.setText(allProducts.get(0).getName());
			flower_name2.setText(allProducts.get(1).getName());
			flower_name3.setText(allProducts.get(2).getName());
			flower_name4.setText(allProducts.get(3).getName());
			flower_name5.setText(allProducts.get(4).getName());
			flower_name6.setText(allProducts.get(5).getName());

			flower_price1.setContentText(allProducts.get(0).getPrice());
			flower_price2.setContentText(allProducts.get(1).getPrice());
			flower_price3.setContentText(allProducts.get(2).getPrice());
			flower_price4.setContentText(allProducts.get(3).getPrice());
			flower_price5.setContentText(allProducts.get(4).getPrice());
			flower_price6.setContentText(allProducts.get(5).getPrice());

			flower_price1.setContentText(allProducts.get(0).getPrice());
			flower_price2.setContentText(allProducts.get(1).getPrice());
			flower_price3.setContentText(allProducts.get(2).getPrice());
			flower_price4.setContentText(allProducts.get(3).getPrice());
			flower_price5.setContentText(allProducts.get(4).getPrice());
			flower_price6.setContentText(allProducts.get(5).getPrice());
		}
		else
		{
			if(mode == 2)
			{
				// MODE = 1 Does Normal Updating , CatalogSTARTIndex and CatalogENDIndex
				CatalogSTARTIndex = 0;          // Were updated from outside the function.
				if (allProducts.size() > 5)    // MODE = 2, Do not use Mode 2, Ramiz knows what this shit does, ask him
					CatalogENDIndex = 6;
				else
					CatalogENDIndex = allProducts.size();
				System.out.println("START INDEX = " + CatalogSTARTIndex);
				System.out.println("END INDEX = " + CatalogENDIndex);
			}

			flower_price1.setContentText("/");
			flower_price2.setContentText("/");
			flower_price3.setContentText("/");
			flower_price4.setContentText("/");
			flower_price5.setContentText("/");
			flower_price6.setContentText("/");

			flower_name1.setText("/");
			flower_name2.setText("/");
			flower_name3.setText("/");
			flower_name4.setText("/");
			flower_name5.setText("/");
			flower_name6.setText("/");

			for(int i = 0 ; i < allProducts.size() ; i++)
			{
				System.out.println("ID: " + allProducts.get(i).getID());
				System.out.println("Name: " + allProducts.get(i).getName());
				System.out.println("Price: " + allProducts.get(i).getPrice());
				System.out.println("### END ###");
			}
			ViewItems(false);


			if(CatalogENDIndex - CatalogSTARTIndex > 0)
			{
				flower_name1.setText(allProducts.get(CatalogSTARTIndex).getName());
				flower_price1.setContentText(allProducts.get(CatalogSTARTIndex).getPrice());
				flower_button1.setVisible(true);
				flower_price1.setVisible(true);
				flower_name1.setVisible(true);
				flower1_addCart.setVisible(true);
				container1.setVisible(true);
			}

			if (CatalogENDIndex - CatalogSTARTIndex > 1)
			{
				flower_name2.setText(allProducts.get(CatalogSTARTIndex + 1).getName());
				flower_price2.setContentText(allProducts.get(CatalogSTARTIndex + 1).getPrice());
				flower_button2.setVisible(true);
				flower_price2.setVisible(true);
				flower_name2.setVisible(true);
				flower2_addCart.setVisible(true);
				container2.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 2)
			{
				flower_name3.setText(allProducts.get(CatalogSTARTIndex + 2).getName());
				flower_price3.setContentText(allProducts.get(CatalogSTARTIndex + 2).getPrice());
				flower_button3.setVisible(true);
				flower_price3.setVisible(true);
				flower_name3.setVisible(true);
				flower3_addCart.setVisible(true);
				container3.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 3)
			{
				flower_name4.setText(allProducts.get(CatalogSTARTIndex + 3).getName());
				flower_price4.setContentText(allProducts.get(CatalogSTARTIndex + 3).getPrice());
				flower_button4.setVisible(true);
				flower_price4.setVisible(true);
				flower_name4.setVisible(true);
				flower4_addCart.setVisible(true);
				container4.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 4)
			{
				flower_name5.setText(allProducts.get(CatalogSTARTIndex + 4).getName());
				flower_price5.setContentText(allProducts.get(CatalogSTARTIndex + 4).getPrice());
				flower_button5.setVisible(true);
				flower_price5.setVisible(true);
				flower_name5.setVisible(true);
				flower5_addCart.setVisible(true);
				container5.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 5)
			{
				flower_name5.setText(allProducts.get(CatalogSTARTIndex + 4).getName());
				flower_price5.setContentText(allProducts.get(CatalogSTARTIndex + 4).getPrice());
				flower_button5.setVisible(true);
				flower_price5.setVisible(true);
				flower_name5.setVisible(true);
				flower5_addCart.setVisible(true);
				container5.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex == 6)
			{
				flower_name6.setText(allProducts.get(CatalogSTARTIndex + 5).getName());
				flower_price6.setContentText(allProducts.get(CatalogSTARTIndex + 5).getPrice());
				flower_button6.setVisible(true);
				flower_price6.setVisible(true);
				flower_name6.setVisible(true);
				flower6_addCart.setVisible(true);
				container6.setVisible(true);
			}
		}
	}
	int CatalogSTARTIndex;
	int CatalogENDIndex;

	static List<Product> allProducts = new ArrayList<>();

	@FXML
	void product_clicked(javafx.scene.input.MouseEvent event) throws IOException {
		current_button = ((ImageView) event.getSource()).getId();
		App.setRoot("secondary");
	}


	public void ViewItems(boolean mode)
	{
		flower_button1.setVisible(mode);
		flower_button2.setVisible(mode);
		flower_button3.setVisible(mode);
		flower_button4.setVisible(mode);
		flower_button5.setVisible(mode);
		flower_button6.setVisible(mode);

		flower_price1.setVisible(mode);
		flower_price2.setVisible(mode);
		flower_price3.setVisible(mode);
		flower_price4.setVisible(mode);
		flower_price5.setVisible(mode);
		flower_price6.setVisible(mode);

		flower_name1.setVisible(mode);
		flower_name2.setVisible(mode);
		flower_name3.setVisible(mode);
		flower_name4.setVisible(mode);
		flower_name5.setVisible(mode);
		flower_name6.setVisible(mode);

		flower1_addCart.setVisible(mode);
		flower2_addCart.setVisible(mode);
		flower3_addCart.setVisible(mode);
		flower4_addCart.setVisible(mode);
		flower5_addCart.setVisible(mode);
		flower6_addCart.setVisible(mode);

		container1.setVisible(mode);
		container2.setVisible(mode);
		container3.setVisible(mode);
		container4.setVisible(mode);
		container5.setVisible(mode);
		container6.setVisible(mode);


	}
	public void viewAdminGUI(boolean mode)
	{
		adminEditCatalog.setVisible(mode);
		worker_edit.setVisible(mode);
		CreateCustomItem.setVisible(mode);
	}
	int cartPrice = 0;
	Account currentLoggedAccount;
	boolean availableProducts = false;
	List<Product> userCart = new ArrayList<>();

	@FXML
	void initialize() throws MalformedURLException {
		justText.setVisible(true);
		justButton.setVisible(false);

		System.out.println("arrived to initialize 1");
		EventBus.getDefault().register(this);
		assert flower_button1 != null : "fx:id=\"flower_button1\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_button2 != null : "fx:id=\"flower_button2\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_button3 != null : "fx:id=\"flower_button3\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_button4 != null : "fx:id=\"flower_button4\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_button5 != null : "fx:id=\"flower_button5\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_button6 != null : "fx:id=\"flower_button6\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name1 != null : "fx:id=\"flower_name1\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name2 != null : "fx:id=\"flower_name2\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name3 != null : "fx:id=\"flower_name3\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name4 != null : "fx:id=\"flower_name4\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name5 != null : "fx:id=\"flower_name5\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_name6 != null : "fx:id=\"flower_name6\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price1 != null : "fx:id=\"flower_price1\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price2 != null : "fx:id=\"flower_price2\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price3 != null : "fx:id=\"flower_price3\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price4 != null : "fx:id=\"flower_price4\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price5 != null : "fx:id=\"flower_price5\" was not injected: check your FXML file 'primary.fxml'.";
		assert flower_price6 != null : "fx:id=\"flower_price6\" was not injected: check your FXML file 'primary.fxml'.";
		assert deliveryButton != null : "fx:id=\"deliveryButton\" was not injected: check your FXML file 'primary.fxml'.";
		assert messageField != null : "fx:id=\"messageField\" was not injected: check your FXML file 'primary.fxml'.";
		assert customError != null : "fx:id=\"customError\" was not injected: check your FXML file 'primary.fxml'.";

		customError.setVisible(false);
		messageField.setVisible(false);
		viewMyOrders.setVisible(false);
		viewMyComplaints.setVisible(false);
		deliveryButton.setVisible(false);
		openComplaints.setVisible(false);
		infoo.setVisible(false);
		adminControlButtton.setVisible(false);
		viewInboxPlz.setVisible(false);
		inboxList.setVisible(false);
		openMessage.setVisible(false);
		checkout.setVisible(false);
		cartTextPrice.setVisible(false);
		cartTextDiscount.setVisible(false);
		cartTextPriceDiscount.setVisible(false);
		cartTextPriceFinal.setVisible(false);
		CartItemsList.setVisible(false);
		cartTopText.setVisible(false);
		flower1_addCart.setVisible(false);
		flower2_addCart.setVisible(false);
		flower3_addCart.setVisible(false);
		flower4_addCart.setVisible(false);
		flower5_addCart.setVisible(false);
		flower6_addCart.setVisible(false);
		CreateCustomItem.setVisible(false);
		adminEditCatalog.setVisible(false);

		compln.setVisible(false);
		customid.setVisible(false);

		switch (catalog_flag.getFlagg()){
			case 0:{
				break;
			}
			case 1:{
				System.out.println("case 1");
				break;
			}
			case 2:{
				System.out.println("case 2");
				break;
			}
			case 3:{
				System.out.println("case 3");
				break;
			}
		}
		chooseCustomType.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomColor.setVisible(false);

		EditItemType.setVisible(false);
		EditItemDesc.setVisible(false);
		EditItemPrice.setVisible(false);
		EditItemExtra.setVisible(false);

		AddItem.setVisible(false);
		RemoveItem.setVisible(false);
		UpdateItem.setVisible(false);
		//remID.setVisible(false);
		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		flower_button1.setVisible(false);
		flower_button2.setVisible(false);
		flower_button3.setVisible(false);
		flower_button4.setVisible(false);
		flower_button5.setVisible(false);
		flower_button6.setVisible(false);

		flower_price1.setVisible(false);
		flower_price2.setVisible(false);
		flower_price3.setVisible(false);
		flower_price4.setVisible(false);
		flower_price5.setVisible(false);
		flower_price6.setVisible(false);

		flower_name1.setVisible(false);
		flower_name2.setVisible(false);
		flower_name3.setVisible(false);
		flower_name4.setVisible(false);
		flower_name5.setVisible(false);
		flower_name6.setVisible(false);
		init_container.setVisible(false);

		//CartItemsList.setVisible(false);

		adminEditCatalog.getItems().add("Add Item");
		adminEditCatalog.getItems().add("Remove Item");
		adminEditCatalog.getItems().add("Edit Item");

		chooseCustomColor.getItems().add("Red");
		chooseCustomColor.getItems().add("Blue");
		chooseCustomColor.getItems().add("Yellow");
		chooseCustomColor.getItems().add("White");
		chooseCustomColor.getItems().add("Purple");

		chooseCustomType.getItems().add("Arrangement");
		chooseCustomType.getItems().add("Bloom & Pot");
		chooseCustomType.getItems().add("Bouquet");
		chooseCustomType.getItems().add("Colletion");

		worker_edit.getItems().add("Add worker");
		worker_edit.getItems().add("Remove worker");
		worker_edit.getItems().add("Edit worker");

		viewCart.setVisible(false);
		//cartTopText.setVisible(false);
		//cartTextPrice.setVisible(false);
		//cartTextDiscount.setVisible(false);
		//cartTextPriceDiscount.setVisible(false);
		//cartTextPriceFinal.setVisible(false);

		initializeData();
		if (returnedFromSecondaryController) {
			updateFields(0);
		}
		init_container.setVisible(true);
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						justButton.setVisible(true);
					}
				},5000
		);
		System.out.println("PRINTING FLAG");
		System.out.println(catalog_flag.getFlagg());
		cartTextPrice.setText("0");
		cartTextDiscount.setText("0");
		worker_edit.setVisible(false);

		inboxList.setVisible(false);
		openMessage.setVisible(false);

	}


	void initializeData() {

		try {
			SimpleClient.getClient().sendToServer("first entry"); // sends the updated product to the server class
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Subscribe
	public void updateGui(UpdateGuiEvent upEvent){
		System.out.println("arrived to the update GUI  event");
		allProducts = upEvent.getRecievedList();
		availableProducts = true;
	}
	@Subscribe
	public void complaintEvent(PassAllComplaintsEvent allComps){ // added new 21/7
		System.out.println("arrived to complaintEvent Subscriber in primary!!!!!");
		List<Complaint> recievedComplaints = allComps.getComplaintsToPass();

		for(int i=0;i<recievedComplaints.size();i++){
			System.out.println(recievedComplaints.get(i).getDate());
		}
	}

	List<Message> MessageList = new ArrayList<>();

	@Subscribe
	public void messageEventFunction(passAllMessagesEvent allMsg){ // added new 21/7
		System.out.println("arrived to messageEvent Subscriber in primary!!!!!");
		List<Message> recievedMessages = allMsg.getMessagesToPassToPass();
		System.out.println("ReceviedMessages = " + recievedMessages.size());
		MessageList = recievedMessages;
	}

	@Subscribe
	public void PassAccountEvent(PassAccountEvent passAcc){ // added today
		System.out.println("arrived to passAccountToPrimary sucessfuly");
		Account recvAccount = passAcc.getRecievedAccount();
		System.out.println(recvAccount.getPassword());
		if(recvAccount.getPrivialge() == 1)
		{
			//TO:DO Adjust the buttons
		}
		System.out.println(recvAccount.getAccountID());
		System.out.println(recvAccount.getEmail());
		System.out.println(recvAccount.getFullName());
		//System.out.println(recvAccount.getAddress());
		//System.out.println(recvAccount.getCreditCardNumber());
		//System.out.println(recvAccount.getCreditMonthExpire());
		System.out.println("Acc Priv: " + recvAccount.getPrivialge());
		currentLoggedAccount = recvAccount;
		System.out.println(" Current Priv : " + currentLoggedAccount.getPrivialge());

	}
	@Subscribe
	public void retRieveDatabase(RetrieveDataBaseEvent rtEvent) {

		System.out.println("arrived to the retreivedatabse event");
		System.out.println("the current table is:");
		for (int i = 0; i < rtEvent.getRecievedList().size(); i++) {
			System.out.println(rtEvent.getRecievedList().get(i).getButton());
		}
		allProducts = rtEvent.getRecievedList();


		/*
		// TESTING THE ACCOUNTS ADD MANUALLY
		UpdateMessage new_msg=new UpdateMessage("account","add");
		Date date=new Date();
		Account new_acc=new Account("khaled","sakhnin","@eee","332",457,889,1,2,445,2);
		new_msg.setAccount(new_acc);
		try {
			System.out.println("before sending updateMessage to server ");
			SimpleClient.getClient().sendToServer(new_msg); // sends the updated product to the server class
			System.out.println("afater sending updateMessage to server ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/*new_acc=new Account("abu-nebal","haifa","@nebal.com","111",457,889,date,445,2);
		new_msg.setAccount(new_acc);
		try {
			System.out.println("before sending updateMessage to server ");
			SimpleClient.getClient().sendToServer(new_msg); // sends the updated product to the server class
			System.out.println("afater sending updateMessage to server ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		new_acc=new Account("molazem-ra2oof","haifa","@ra2of.com","111",457,889,date,445,2);
		new_msg.setAccount(new_acc);
		try {
			System.out.println("before sending updateMessage to server ");
			SimpleClient.getClient().sendToServer(new_msg); // sends the updated product to the server class
			System.out.println("afater sending updateMessage to server ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	@Subscribe
	public void initDatabase(InitDatabaseEvent event) {
		/*
		UpdateMessage new_msg=new UpdateMessage("account","add");
		Date date=new Date();
		Account new_acc=new Account("khaled","sakhnin","@eee","332",457,889,1,2,445,2);
		new_msg.setAccount(new_acc);
		try {
			System.out.println("before sending updateMessage to server ");
			SimpleClient.getClient().sendToServer(new_msg); // sends the updated product to the server class
			System.out.println("afater sending updateMessage to server ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 */
		System.out.println("arrived to databaseInit");
		Product flower1 = new Product(1, flower_button1.getId(), flower_name1.getText(), "", flower_price1.getContentText());
		allProducts.add(flower1);
		Product flower2 = new Product(2, flower_button2.getId(), flower_name2.getText(), "", flower_price2.getContentText());
		allProducts.add(flower2);
		Product flower3 = new Product(3, flower_button3.getId(), flower_name3.getText(), "", flower_price3.getContentText());
		allProducts.add(flower3);
		Product flower4 = new Product(4, flower_button4.getId(), flower_name4.getText(), "", flower_price4.getContentText());
		allProducts.add(flower4);
		Product flower5 = new Product(5, flower_button5.getId(), flower_name5.getText(), "", flower_price5.getContentText());
		allProducts.add(flower5);
		Product flower6 = new Product(6, flower_button6.getId(), flower_name6.getText(), "", flower_price6.getContentText());
		allProducts.add(flower6);

		List<Product> productList = new ArrayList<Product>();
		productList.add(flower1);
		productList.add(flower2);
		productList.add(flower3);
		productList.add(flower4);
		productList.add(flower5);
		productList.add(flower6);
		try {
			SimpleClient.getClient().sendToServer(productList); // sends the updated product to the server class
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static Product getCurrent_button() {
		for (int i = 0; i < allProducts.size(); i++) {
			if (equal(allProducts.get(i).getButton(), current_button)) {
				return allProducts.get(i);
			}
		}
		return allProducts.get(0); // need to change this
	}

	static void setReturnedFromSecondaryController(boolean retFromSecond) {
		returnedFromSecondaryController = retFromSecond;
	}

	static boolean getReturnedFromSecondaryController() {
		return returnedFromSecondaryController;
	}

	@FXML
	private Button compln;

	@FXML
	void complinstart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("complaint.fxml"));
		Parent roott = loader.load();
		ComplaintController cc = loader.getController();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("complaint application");
		stage.show();
	}

	@FXML // fx:id="RemoveItem"
	private Button accbtn; // Value injected by FXMLLoader

	@FXML
	void accbtnlogin(ActionEvent event) throws IOException {
		System.out.println(".");
	}
}
