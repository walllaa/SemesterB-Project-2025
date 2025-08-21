package il.cshaifasweng.OCSFMediatorExample.client;
import com.mysql.cj.log.Log;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.jfr.Event;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class LogInSecondary {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Customer_login;

    @FXML
    private TextField Email;

    @FXML
    private Button Employee_login;

    @FXML
    private Button Manager_login;

    @FXML
    private Label ErrorMsg;

    @FXML
    private Button LogIn;

    @FXML
    private Button bak;

    @FXML
    private TextField Password;

    @FXML
    private Label ErrorMsgPass;

    @FXML
    private Text logSucc;

    @FXML
    private Button OpenCatalogplz;

    String login_flag = "";

    @FXML
    void initialize() {
        logSucc.setVisible(false);
        OpenCatalogplz.setVisible(false);
        EventBus.getDefault().register(this);
        assert Customer_login != null : "fx:id=\"Customer_login\" was not injected: check your FXML file 'LogInSecond.fxml'.";
        assert Employee_login != null : "fx:id=\"Employee_login\" was not injected: check your FXML file 'LogInSecond.fxml'.";
        Customer_login.setVisible(true);
        Employee_login.setVisible(true);
        bak.setVisible(true);
        Email.setVisible(false);
        Password.setVisible(false);
        LogIn.setVisible(false);
        ErrorMsg.setVisible(false);
        ErrorMsgPass.setVisible(false);

    }
    @FXML
    void openCatalogFunc(ActionEvent event) throws IOException {

        String theEmail = Email.getText();
        System.out.println("the email is " + theEmail);
        MailClass mailObject = new MailClass(theEmail);
        // 1. send to server the current email
        try {
            System.out.println("before sending the mailObject " + theEmail);
            SimpleClient.getClient().sendToServer(mailObject); // sends the updated product to the server class

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GetAllComplaints allComplaints = new GetAllComplaints();
        System.out.println("send request for complaints !!");
        try {
            System.out.println("before sending the getAllComplaints " );
            SimpleClient.getClient().sendToServer(allComplaints);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent roott = loader.load();
        PrimaryController cc = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.setTitle("Catalog");
        stage.show();
        Stage stagee = (Stage)LogIn.getScene().getWindow();
        stagee.close();
    }



    @FXML
    void CustomerLogIn(ActionEvent event) throws IOException {
        login_flag = "customer";
        catalog_flag.setFlagg(1);
      /*  UpdateMessage new_msg=new UpdateMessage("account","add");
        Date date=new Date();
        Account new_acc=new Account("khaled","sakhnin","@eee","332",457,889,date,445,2);
        new_msg.setAccount(new_acc);
        try {
            System.out.println("before sending updateMessage to server ");
            SimpleClient.getClient().sendToServer(new_msg); // sends the updated product to the server class
            System.out.println("afater sending updateMessage to server ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        Customer_login.setVisible(false);
        Employee_login.setVisible(false);
        ErrorMsg.setVisible(false);
        ErrorMsgPass.setVisible(false);
        //bak.setVisible(true);
        Email.setVisible(true);
        Password.setVisible(true);
        LogIn.setVisible(true);
    }

    @FXML
    void EmployeeLogIn(ActionEvent event) throws IOException {
        catalog_flag.setFlagg(2);
        login_flag = "employee";
        Customer_login.setVisible(false);
        Employee_login.setVisible(false);
        Email.setVisible(true);
        Password.setVisible(true);
        LogIn.setVisible(true);
        ErrorMsg.setVisible(false);
      //  bak.setVisible(true);
        ErrorMsgPass.setVisible(false);
    }

    @FXML
    void ManagerLogIn(ActionEvent event) throws IOException {
        catalog_flag.setFlagg(3);
        login_flag = "employee";
        Customer_login.setVisible(false);
        Employee_login.setVisible(false);
        Email.setVisible(true);
        Password.setVisible(true);
        LogIn.setVisible(true);
        ErrorMsg.setVisible(false);
      //  bak.setVisible(true);
        ErrorMsgPass.setVisible(false);
    }

    @FXML
    void backkk(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInPrim.fxml"));
        Parent roott = loader.load();
        LogInPrimary cc = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.setTitle("Login");
        stage.show();
        Stage stagee = (Stage) bak.getScene().getWindow();
        // do what you have to do
        stagee.close();
    }

    public void LogIn(javafx.event.ActionEvent actionEvent) throws IOException {
        CheckMail checkML  = new CheckMail(Email.getText(),login_flag,Password.getText()); // check if employee's/customer's email exists
        try
        {
            SimpleClient.getClient().sendToServer(checkML); // send the mail to the server to check if exists
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if(itWorked == true)
                            {
                                logSucc.setVisible(true);
                                OpenCatalogplz.setVisible(true);
                            }
                        }
                    },2000
            );

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Subscribe
    public void checkMailInDB(MailChecker checkML) throws IOException
    {
        System.out.println("IM HERE :DDD");
        if(checkML.getExistsMail() == false){ // case incorrect email
            System.out.println("arrived to case incorrect email succesfully");
            ErrorMsg.setVisible(true);

            /*MailPassMatch checkEmailPass = new MailPassMatch(Email.getText(),Password.getText(),login_flag);
            try {
                SimpleClient.getClient().sendToServer(checkEmailPass);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
        else if(checkML.getExistsPassword() == false)
        { // case email found but the password is incorrect
            System.out.println("arrived to case incorrect password  succesfully");
            ErrorMsgPass.setVisible(true);
        }
        else if(checkML.isLoggedIn() == false)
        {
            System.out.println("WE GOT HERE, GOOD EMAIL");
            itWorked = true;
        }
        else
        {
            System.out.println("Already Logged In");
            //TO:DO ...
        }
    }
    boolean itWorked = false;

    @Subscribe
    public void checkMailPass(MailPassMatch checkEmailPass) throws IOException {
        System.out.println("Checking Mail IN DB");
        if(checkEmailPass.getexists()==true)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent roott = loader.load();
            PrimaryController cc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(roott));
            stage.setTitle("Catalog");
            stage.show();
            Stage stagee = (Stage)LogIn.getScene().getWindow();
            stagee.close();
        }
        else{
            ErrorMsgPass.setVisible(true);
        }
    }

}
