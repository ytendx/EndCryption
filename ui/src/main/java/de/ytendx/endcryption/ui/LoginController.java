package de.ytendx.endcryption.ui;

import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;

public class LoginController {

    @FXML private TextField serverAdress;
    @FXML private TextField preferedUsername;

    @FXML private Button loginButton;

    @FXML
    public void initialize() {
        System.out.println("Hey");
        loginButton.setOnAction(actionEvent -> {
            try {
                if(InetAddress.getByName(serverAdress.getCharacters().toString()).isReachable(1000)){
                    Application.master = new SocketAdapter(serverAdress.getCharacters().toString(), 4000);
                    Application.endCryptionClient.getConnectionHandler().sendPacketData(Application.master,
                            new PacketC2SOutHandshake(1, PublicKeySerialization.toString(Application.endCryptionClient.getConnectionHandler().getCryptionHandler().getKeyPair().getPublic()),
                                    "", 4000, preferedUsername.getCharacters().toString()));
                    try {
                        Application.switchToIndex();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }else{
                    //JOptionPane.showMessageDialog(null, "The givven server is not reachable!", "Server", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception exception){
                //JOptionPane.showMessageDialog(null, "The givven server is not reachable!", "Server", JOptionPane.ERROR_MESSAGE);
            }
            //JOptionPane.showMessageDialog(null, "The givven server is not reachable!", "Server", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void addMessage(String s){

    }


}