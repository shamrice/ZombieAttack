package io.github.shamrice.zombieAttackGame.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InformationBoxConfig;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 8/7/2017.
 */
public class MessageBox {

    private final int MAX_LINES = 7;

    private InformationBoxConfig messageBoxConfig;
    private List<String> currentText;

    public MessageBox(InformationBoxConfig messageBoxConfig) {
        this.messageBoxConfig = messageBoxConfig;

        this.currentText = new ArrayList<String>();
    }

    public void draw() {
        messageBoxConfig
                .getAssetConfiguration()
                .getAnimation(ImageTypes.DEFAULT)
                .draw(
                        messageBoxConfig.getxPos(),
                        messageBoxConfig.getyPos()
                );

        int lineHeight = 0;
        for (String text : currentText) {

            lineHeight += messageBoxConfig.getTrueTypeFont().getLineHeight();

            messageBoxConfig.getTrueTypeFont().drawString(
                    messageBoxConfig.getxPos() + 15,
                    messageBoxConfig.getyPos() + lineHeight,
                    text,
                    Color.white
            );
        }
    }

    public void write(String text) {

        if (currentText.size() > MAX_LINES) {
            currentText.remove(0);
        }

        currentText.add(text);

    }
}
