package de.ytendx.endcryption.ui;

import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import de.ytendx.endcryption.client.client.Client;
import de.ytendx.endcryption.client.packets.PacketC2SPublicKeyRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class IndexController {

    @FXML private TextField target;
    @FXML private TextField message;

    @FXML private Button send;

    @Getter
    @FXML private ListView<String> messages;

    @FXML
    public void initialize() {
        send.setOnAction(actionEvent -> {
            String target = this.target.getCharacters().toString();
            String message = this.message.getCharacters().toString();

            Client targetClient = Application.endCryptionClient.getClientRegister().getByName(target);

            if(targetClient == null){
                Application.endCryptionClient.getClientRegister().register(target, Application.endCryptionClient.getConnectionHandler(), Application.master);
            }

            try {
                Application.endCryptionClient.getConnectionHandler().getCryptionHandler().encrypt(message.getBytes(), targetClient.getPublicKey());
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }

        });
    }
}
