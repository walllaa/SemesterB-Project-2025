package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class PrimaryController {
	public int flowersnum2 = 6;
	public int workernum2 = 0;
	public int managernum2 = 0;
	static boolean returnedFromSecondaryController = false;
	boolean firstRun = true;

	@FXML private ResourceBundle resources;
	@FXML private URL location;

	@FXML private ComboBox<String> adminEditCatalog;
	@FXML private ComboBox<String> worker_edit;
	@FXML private Button logout;

	@FXML private Button checkout;

	@FXML private AnchorPane container1;
	@FXML private AnchorPane container2;
	@FXML private AnchorPane container3;
	@FXML private AnchorPane container4;
	@FXML private AnchorPane container5;
	@FXML private AnchorPane container6;
	@FXML private AnchorPane init_container;

	@FXML private TextField EditItemDesc;
	@FXML private TextField EditItemPrice;
	@FXML private TextField EditItemType;
	@FXML private TextField EditItemExtra;

	@FXML private TextField customColor;
	@FXML private TextField customPrice;
	@FXML private TextField customid;
	@FXML private TextField customType;

	@FXML public Button CreateCustomItem;
	@FXML public Button infoo;
	@FXML public Button cartt;

	@FXML private Button CancelCustomItem;
	@FXML private Button FinishCustomItem;

	@FXML private ImageView flower_button1;
	@FXML private ImageView flower_button2;
	@FXML private ImageView flower_button3;
	@FXML private ImageView flower_button4;
	@FXML private ImageView flower_button5;
	@FXML private ImageView flower_button6;

	@FXML private Label flower_name1;
	@FXML private Label flower_name2;
	@FXML private Label flower_name3;
	@FXML private Label flower_name4;
	@FXML private Label flower_name5;
	@FXML private Label flower_name6;

	@FXML private DialogPane flower_price1;
	@FXML private DialogPane flower_price2;
	@FXML private DialogPane flower_price3;
	@FXML private DialogPane flower_price4;
	@FXML private DialogPane flower_price5;
	@FXML private DialogPane flower_price6;

	@FXML private Button AddItem;
	@FXML private Button RemoveItem;
	@FXML private Button UpdateItem;

	@FXML private Button printProd;
	@FXML private Button justButton;
	@FXML private Text justText;
	@FXML private Button nextPage;
	@FXML private Button prevPage;

	@FXML private ComboBox<String> chooseCustomColor;
	@FXML private ComboBox<String> chooseCustomType;

	@FXML private Button flower1_addCart;
	@FXML private Button flower2_addCart;
	@FXML private Button flower3_addCart;
	@FXML private Button flower4_addCart;
	@FXML private Button flower5_addCart;
	@FXML private Button flower6_addCart;

	@FXML private Button viewCart;

	@FXML private TextField cartTextDiscount;
	@FXML private TextField cartTextPrice;
	@FXML private Text cartTextPriceDiscount;
	@FXML private Text cartTextPriceFinal;

	@FXML private Text customError;
	@FXML private Text cartTopText;

	@FXML private Button viewMyComplaints;
	@FXML private Button viewMyOrders;

	@FXML private ListView<String> CartItemsList;

	@FXML private Button adminControlButtton;
	@FXML private Button openComplaints;

	@FXML private ListView<String> inboxList;
	@FXML private Button openMessage;
	@FXML private Button viewInboxPlz;

	@FXML private Button deliveryButton;
	@FXML private TextField messageField;

	@FXML
	void viewMessage(ActionEvent event) {
		System.out.println("viewMessage 1");
		messageField.setVisible(true);
		int i;
		String bb = "";
		String aa = inboxList.getSelectionModel().getSelectedItem();
		int selected = 0;
		for (i = 0; aa.charAt(i) != '-'; i++) { }
		i = i + 2;
		for (; i < aa.length(); i++) {
			bb = bb + aa.charAt(i);
		}
		selected = Integer.parseInt(bb);
		System.out.println("Selected =  " + selected);
		for (i = 0; i < MessageList.size(); i++) {
			if (MessageList.get(i).getMessageID() == selected) {
				messageField.setText(MessageList.get(i).getMsgText());
				System.out.println("Setting Text : " + MessageList.get(i).getMsgText());
				break;
			}
		}
	}

	@FXML
	void openInbox(ActionEvent event) {
		inboxList.getItems().clear();
		if (viewInboxPlz.getText().equals("Open Inbox")) {
			String MsgTemp = "";
			for (int i = 0; i < MessageList.size(); i++) {
				if (MessageList.get(i).getCustomerID() == currentLoggedAccount.getAccountID()) {
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
		} else {
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
			System.out.println("before sending the logout object");
			SimpleClient.getClient().sendToServer(logOutObject);
		} catch (IOException e) { e.printStackTrace(); }

		FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPrim.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Welcome");
		stage.show();
		Stage stagee = (Stage) logout.getScene().getWindow();
		stagee.close();
	}

	@FXML
	void ReplyToComplaints(ActionEvent event) throws IOException {
		PassAccountEventReplyComplaint recievedAcc = new PassAccountEventReplyComplaint(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("replycomplaint.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Reply Complaint");
		stage.show();
		Stage stagee = (Stage) openComplaints.getScene().getWindow();
		stagee.close();
	}

	@FXML
	void openControlPanel(ActionEvent event) throws IOException {
		PassAccountEventAdmin recievedAcc = new PassAccountEventAdmin(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("admincontrol.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Delivery Panel");
		stage.show();
		Stage stagee = (Stage) adminControlButtton.getScene().getWindow();
		stagee.close();
	}

	@FXML
	void addToCartFlower1(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower2(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex + 1).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex + 1).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex + 1));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower3(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex + 2).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex + 2).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex + 2));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower4(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex + 3).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex + 3).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex + 3));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower5(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex + 4).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex + 4).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex + 4));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML
	void addToCartFlower6(ActionEvent event) {
		int basePrice = Integer.parseInt(cartTextPrice.getText());
		int addedPrice = Integer.parseInt(allProducts.get(CatalogSTARTIndex + 5).getPrice());
		CartItemsList.getItems().add(allProducts.get(CatalogSTARTIndex + 5).getName());
		basePrice += addedPrice;
		cartTextPrice.setText(String.valueOf(basePrice));
		userCart.add(allProducts.get(CatalogSTARTIndex + 5));
		if (currentLoggedAccount.isSubscription() && basePrice > 50)
			cartTextDiscount.setText("" + basePrice * 0.9);
		else
			cartTextDiscount.setText("" + basePrice);
	}

	@FXML private TextField cartText;

	@FXML
	void openDelivery(ActionEvent event) throws IOException {
		PassAccountEventDelivery recievedAcc = new PassAccountEventDelivery(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("delivery.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Delivery Panel");
		stage.show();
		Stage stagee = (Stage) infoo.getScene().getWindow();
		stagee.close();
	}

	int cartViewBinary = 0;

	@FXML
	void viewUserCart(ActionEvent event) {
		boolean mode;
		if (cartViewBinary == 0) {
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
		} else {
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
			System.out.println("before sending the getAllComplaints ");
			SimpleClient.getClient().sendToServer(allComplaints);
		} catch (IOException e) { e.printStackTrace(); }

		PassAccountEventLogManager recievedAcc = new PassAccountEventLogManager(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("log_manager.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Complaint Manager");
		stage.show();
		Stage stagee = (Stage) infoo.getScene().getWindow();
		stagee.close();
	}

	@FXML
	void openCheckout(ActionEvent event) throws IOException {
		System.out.println("arrived to checkout 1");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
		Parent roott = loader.load();
		System.out.println("arrived to checkout 2");
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("Checkout");
		stage.show();
		Stage stagee = (Stage) checkout.getScene().getWindow();
		System.out.println("arrived to checkout 3");
		stagee.close();

		PassAccountEventCheckout recievedAcc = new PassAccountEventCheckout(currentLoggedAccount);
		recievedAcc.productsToCheckout = userCart;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		System.out.println("arrived to checkout 4 (posted the event)");
	}

	@Subscribe
	public void messageEvent(passAllMessagesEvent allMessages) {
		System.out.println("arrived to MESSAGE Event Subscriber in primary!!!!!");
		List<Message> recievedMessagess = allMessages.getMessagesToPassToPass();
		for (int i = 0; i < recievedMessagess.size(); i++) {
			System.out.println(recievedMessagess.get(i));
		}
	}

	@FXML
	void nextPageUpate(ActionEvent event) {
		int difference = allProducts.size() - CatalogENDIndex;
		if (difference != 0) {
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
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("My Complaints");
		stage.show();
		Stage stagee = (Stage) viewMyComplaints.getScene().getWindow();
		stagee.close();

		PassAccountEventComplaints recievedAcc = new PassAccountEventComplaints(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);

		GetAllComplaints allComplaints = new GetAllComplaints();
		System.out.println("send request for complaints !!");
		try {
			System.out.println("before sending the getAllComplaints ");
			SimpleClient.getClient().sendToServer(allComplaints);
		} catch (IOException e) { e.printStackTrace(); }
	}

	@FXML
	void openMyOrders(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("myorders.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("My Orders");
		stage.show();
		Stage stagee = (Stage) viewMyOrders.getScene().getWindow();
		stagee.close();

		PassAccountEventOrders recievedAcc = new PassAccountEventOrders(currentLoggedAccount);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				EventBus.getDefault().post(recievedAcc);
				System.out.println("the server sent me the account , NICE 4 !!");
			}
		}, 4000);
	}

	@FXML
	void prevPageUpate(ActionEvent event) {
		int difference = CatalogSTARTIndex - 6;
		if (difference >= 6) {
			CatalogENDIndex = CatalogSTARTIndex;
			CatalogSTARTIndex = CatalogSTARTIndex - 7;
		} else {
			if (difference > -1) {
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
			System.out.println("before sending the getAllMessagaes ");
			SimpleClient.getClient().sendToServer(allMessages);
		} catch (IOException e) { e.printStackTrace(); }

		init_container.setVisible(false);
		justText.setVisible(false);
		if (justViewMode == 0) {
			updateFields(2);
			justViewMode++;
		} else {
			updateFields(1);
		}
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() { }
		}, 0);
		justText.setVisible(false);
		justButton.setVisible(false);

		if (currentLoggedAccount.getPrivialge() == 1) {
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
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
		if (currentLoggedAccount.getPrivialge() == 2) {
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
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
		if (currentLoggedAccount.getPrivialge() == 3) {
			viewMyOrders.setVisible(true);
			viewMyComplaints.setVisible(true);
			viewInboxPlz.setVisible(true);
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
	void printProducts(ActionEvent event) {
		for (int i = 0; i < allProducts.size(); i++) {
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

		return new Product(0, "test", "test", "test", "test");
	}

	@FXML
	void cancelCustomitem(ActionEvent event) {
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
	void addCartCustomitem(ActionEvent event) {
		customError.setVisible(false);
		String customPriceString = "";
		boolean fail = false;
		if (chooseCustomType.getSelectionModel().getSelectedIndex() == -1) {
			customError.setText("Please choose a type");
			customError.setVisible(true);
			fail = true;
		}
		if (chooseCustomColor.getSelectionModel().getSelectedIndex() == -1) {
			customError.setText("Please choose a color");
			customError.setVisible(true);
			fail = true;
		}
		customPriceString = customPrice.getText();
		for (int i = 0; i < customPriceString.length(); i++) {
			if (customPriceString.charAt(i) < '0' || customPriceString.charAt(i) > '9') {
				fail = true;
				customError.setText("Please enter a valid price");
				customError.setVisible(true);
			}
		}

		if (!fail) {
			String color = chooseCustomColor.getSelectionModel().getSelectedItem();
			String Type = chooseCustomType.getSelectionModel().getSelectedItem();
			String avg = customPrice.getText();
			Product product = new Product(0, "btn", "Custom Item",
					"A " + chooseCustomType + " With dominant color " + chooseCustomColor, avg);

			int basePrice = Integer.parseInt(cartTextPrice.getText());
			int addedPrice = Integer.parseInt(product.getPrice());
			CartItemsList.getItems().add(product.getName());
			basePrice += addedPrice;
			cartTextPrice.setText(String.valueOf(basePrice));
			userCart.add(product);
			if (currentLoggedAccount.isSubscription() && basePrice > 50)
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

	// ===================== أهم التعديلات هنا =====================

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
		// بدل setId -> استعملنا الاسم الصحيح في UpdateMessage
		updateMessage1.setEntityId(flowersnum2);

		try {
			SimpleClient.getClient().sendToServer(updateMessage1);
		} catch (IOException e) { e.printStackTrace(); }

		CreateCustomItem.setVisible(true);
		adminEditCatalog.setVisible(true);

		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);
		EditItemExtra.setVisible(false);

		if (CatalogENDIndex - CatalogSTARTIndex < 6) {
			CatalogENDIndex++;
		}
		init_container.setVisible(true);
		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
	}

	@FXML
	void adminRemoveItemFunc(ActionEvent event) {
		String deleteID = EditItemExtra.getText();

		UpdateMessage removeType = new UpdateMessage("product", "remove");
		// بدل setDelteId -> استخدمنا setDeleteKey المطابقة للكلاس الجديد
		removeType.setDeleteKey(deleteID);

		flowersnum2--;
		try {
			SimpleClient.getClient().sendToServer(removeType);
		} catch (IOException e) { e.printStackTrace(); }

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

		init_container.setVisible(true);
		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
		RemoveItem.setVisible(false);

		allProducts.remove(Integer.parseInt(EditItemExtra.getText()) - 1);
		updateFields(2);
	}

	@FXML
	void adminUpdateItemFunc(ActionEvent event) {
		String TempType = allProducts.get(0).getName();
		String TempDesc = allProducts.get(0).getDetails();
		String TempPrice = allProducts.get(0).getPrice();

		String newType = EditItemType.getText();
		String newDesc = EditItemDesc.getText();
		String newPrice = EditItemPrice.getText();
		String updateID = EditItemExtra.getText();

		int TargerID = Integer.parseInt(updateID) - 1;
		allProducts.get(TargerID).setName(newType);
		allProducts.get(TargerID).setPrice(newPrice);
		allProducts.get(TargerID).setDetails(newDesc);

		Product currtProduct = il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.getCurrent_button();
		currtProduct.setPrice(newPrice);
		currtProduct.setDetails(newDesc);
		currtProduct.setName(newType);
		int castedID = Integer.parseInt(updateID);
		currtProduct.setID(castedID);

		UpdateMessage updateMessage1 = new UpdateMessage("product", "edit");
		updateMessage1.setProduct(currtProduct);
		// بدل setId -> استعمل setEntityId
		updateMessage1.setEntityId(castedID);

		try {
			SimpleClient.getClient().sendToServer(updateMessage1);
		} catch (IOException e) { e.printStackTrace(); }

		allProducts.get(0).setName(TempType);
		allProducts.get(0).setPrice(TempPrice);
		allProducts.get(0).setDetails(TempDesc);

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

		updateFields(2);
		justText.setVisible(true);
		justText.setText("Catalog Updated Successfully - 0 Errors");
		justButton.setVisible(true);
	}
	// =================== نهاية التعديلات الأساسية ===================

	@FXML
	void chooseAdminEditCatalog(ActionEvent event) {
		CreateCustomItem.setVisible(false);
		adminEditCatalog.setVisible(true);

		chooseCustomColor.setVisible(false);
		customPrice.setVisible(false);
		customid.setVisible(false);
		chooseCustomType.setVisible(false);

		CancelCustomItem.setVisible(false);
		FinishCustomItem.setVisible(false);

		String chosen = adminEditCatalog.getSelectionModel().getSelectedItem();
		if (chosen == "Add Item") {
			EditItemType.setText("New Item Type");
			EditItemDesc.setText("New Item Desc");
			EditItemPrice.setText("New Item Price");
			EditItemType.setVisible(true);
			EditItemDesc.setVisible(true);
			EditItemPrice.setVisible(true);
			EditItemExtra.setVisible(false);
			RemoveItem.setVisible(false);
			UpdateItem.setVisible(false);
			AddItem.setVisible(true);
		}
		if (chosen == "Remove Item") {
			EditItemType.setVisible(false);
			EditItemDesc.setVisible(false);
			EditItemPrice.setVisible(false);
			EditItemExtra.setVisible(true);
			EditItemExtra.setText("ID to remove");
			AddItem.setVisible(false);
			UpdateItem.setVisible(false);
			RemoveItem.setVisible(true);
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
			AddItem.setVisible(false);
			RemoveItem.setVisible(false);
			UpdateItem.setVisible(true);
		}
	}

	int updateFieldsBounds = 0;
	public void updateFields(int mode) {
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
		} else {
			if (mode == 2) {
				CatalogSTARTIndex = 0;
				if (allProducts.size() > 5)
					CatalogENDIndex = 6;
				else
					CatalogENDIndex = allProducts.size();
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

			ViewItems(false);

			if (CatalogENDIndex - CatalogSTARTIndex > 0) {
				flower_name1.setText(allProducts.get(CatalogSTARTIndex).getName());
				flower_price1.setContentText(allProducts.get(CatalogSTARTIndex).getPrice());
				flower_button1.setVisible(true);
				flower_price1.setVisible(true);
				flower_name1.setVisible(true);
				flower1_addCart.setVisible(true);
				container1.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 1) {
				flower_name2.setText(allProducts.get(CatalogSTARTIndex + 1).getName());
				flower_price2.setContentText(allProducts.get(CatalogSTARTIndex + 1).getPrice());
				flower_button2.setVisible(true);
				flower_price2.setVisible(true);
				flower_name2.setVisible(true);
				flower2_addCart.setVisible(true);
				container2.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 2) {
				flower_name3.setText(allProducts.get(CatalogSTARTIndex + 2).getName());
				flower_price3.setContentText(allProducts.get(CatalogSTARTIndex + 2).getPrice());
				flower_button3.setVisible(true);
				flower_price3.setVisible(true);
				flower_name3.setVisible(true);
				flower3_addCart.setVisible(true);
				container3.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 3) {
				flower_name4.setText(allProducts.get(CatalogSTARTIndex + 3).getName());
				flower_price4.setContentText(allProducts.get(CatalogSTARTIndex + 3).getPrice());
				flower_button4.setVisible(true);
				flower_price4.setVisible(true);
				flower_name4.setVisible(true);
				flower4_addCart.setVisible(true);
				container4.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex > 4) {
				flower_name5.setText(allProducts.get(CatalogSTARTIndex + 4).getName());
				flower_price5.setContentText(allProducts.get(CatalogSTARTIndex + 4).getPrice());
				flower_button5.setVisible(true);
				flower_price5.setVisible(true);
				flower_name5.setVisible(true);
				flower5_addCart.setVisible(true);
				container5.setVisible(true);
			}
			if (CatalogENDIndex - CatalogSTARTIndex == 6) {
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

	public void ViewItems(boolean mode) {
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

	public void viewAdminGUI(boolean mode) {
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

		// (asserts كما هي في كودك الأصلي)
		// ...

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

		adminEditCatalog.getItems().addAll("Add Item", "Remove Item", "Edit Item");

		chooseCustomColor.getItems().addAll("Red", "Blue", "Yellow", "White", "Purple");
		chooseCustomType.getItems().addAll("Arrangement", "Bloom & Pot", "Bouquet", "Colletion");

		worker_edit.getItems().addAll("Add worker", "Remove worker", "Edit worker");

		viewCart.setVisible(false);

		initializeData();
		if (returnedFromSecondaryController) {
			updateFields(0);
		}
		init_container.setVisible(true);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override public void run() {
				justButton.setVisible(true);
			}
		}, 5000);

		cartTextPrice.setText("0");
		cartTextDiscount.setText("0");
		worker_edit.setVisible(false);

		inboxList.setVisible(false);
		openMessage.setVisible(false);
	}

	void initializeData() {
		try {
			SimpleClient.getClient().sendToServer("first entry");
		} catch (IOException e) { e.printStackTrace(); }
	}

	@Subscribe
	public void updateGui(UpdateGuiEvent upEvent) {
		System.out.println("arrived to the update GUI  event");
		allProducts = upEvent.getRecievedList();
		availableProducts = true;
	}

	@Subscribe
	public void complaintEvent(PassAllComplaintsEvent allComps) {
		System.out.println("arrived to complaintEvent Subscriber in primary!!!!!");
		List<Complaint> recievedComplaints = allComps.getComplaintsToPass();
		for (int i = 0; i < recievedComplaints.size(); i++) {
			System.out.println(recievedComplaints.get(i).getDate());
		}
	}

	List<Message> MessageList = new ArrayList<>();

	@Subscribe
	public void messageEventFunction(passAllMessagesEvent allMsg) {
		System.out.println("arrived to messageEvent Subscriber in primary!!!!!");
		List<Message> recievedMessages = allMsg.getMessagesToPassToPass();
		System.out.println("ReceviedMessages = " + recievedMessages.size());
		MessageList = recievedMessages;
	}

	@Subscribe
	public void PassAccountEvent(PassAccountEvent passAcc) {
		System.out.println("arrived to passAccountToPrimary sucessfuly");
		Account recvAccount = passAcc.getRecievedAccount();
		currentLoggedAccount = recvAccount;
		System.out.println(" Current Priv : " + currentLoggedAccount.getPrivialge());
	}

	@Subscribe
	public void retRieveDatabase(RetrieveDataBaseEvent rtEvent) {
		System.out.println("arrived to the retreivedatabse event");
		for (int i = 0; i < rtEvent.getRecievedList().size(); i++) {
			System.out.println(rtEvent.getRecievedList().get(i).getButton());
		}
		allProducts = rtEvent.getRecievedList();
	}

	@Subscribe
	public void initDatabase(InitDatabaseEvent event) {
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

		List<Product> productList = new ArrayList<>();
		productList.add(flower1);
		productList.add(flower2);
		productList.add(flower3);
		productList.add(flower4);
		productList.add(flower5);
		productList.add(flower6);
		try {
			SimpleClient.getClient().sendToServer(productList);
		} catch (IOException e) { e.printStackTrace(); }
	}

	static Product getCurrent_button() {
		for (int i = 0; i < allProducts.size(); i++) {
			// استبدال equal(..) بـ Objects.equals(..)
			if (Objects.equals(allProducts.get(i).getButton(), current_button)) {
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

	@FXML private Button compln;

	@FXML
	void complinstart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("complaint.fxml"));
		Parent roott = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(roott));
		stage.setTitle("complaint application");
		stage.show();
	}

	@FXML private Button accbtn;

	@FXML
	void accbtnlogin(ActionEvent event) throws IOException {
		System.out.println(".");
	}
}
