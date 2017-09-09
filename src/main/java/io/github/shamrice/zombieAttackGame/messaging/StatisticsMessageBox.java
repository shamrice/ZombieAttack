package io.github.shamrice.zombieAttackGame.messaging;

import io.github.shamrice.zombieAttackGame.actors.actorStats.PlayerStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InformationBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.messaging.StatisticsBoxConfig;
import org.newdawn.slick.Color;

import java.util.ArrayList;

/**
 * Created by Erik on 9/9/2017.
 */
public class StatisticsMessageBox {

    private InformationBoxConfig statisticsBoxConfig;

    public StatisticsMessageBox(InformationBoxConfig statisticsBoxConfig) {
        this.statisticsBoxConfig = statisticsBoxConfig;
    }

    public void draw(PlayerStatistics playerStatistics) {
        statisticsBoxConfig
                .getAssetConfiguration()
                .getAnimation(ImageTypes.IMAGE_UP)
                .draw(
                        statisticsBoxConfig.getxPos(),
                        statisticsBoxConfig.getyPos()
                );

        int lineHeight = 15;

        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("Name: " + playerStatistics.getName());
        textLines.add("Level: " + playerStatistics.getLevel());
        textLines.add("Health: " + playerStatistics.getCurrentHealth() + " / " + playerStatistics.getBaseHealth());
        textLines.add("Attack: " + playerStatistics.getAttackDamage() +
                " (" + playerStatistics.getBaseAttackDamage() + ")"
        );
        textLines.add("Defense: " + playerStatistics.getCurrentDefense() +
                " (" + playerStatistics.getBaseDefense() + ")"
        );
        textLines.add("Experience: " + playerStatistics.getCurrentExperience() + " / " + playerStatistics.getExperienceToNextLevel());
        textLines.add("Equipped Weapon: " + playerStatistics.getCurrentProjectile().getName());

        for (String text : textLines) {

            lineHeight += statisticsBoxConfig.getTrueTypeFont().getLineHeight();

            statisticsBoxConfig.getTrueTypeFont().drawString(
                    statisticsBoxConfig.getxPos() + 10,
                    statisticsBoxConfig.getyPos() + lineHeight,
                    text,
                    Color.white
            );
        }
    }

}
