package pl.zespolowe.splix.domain.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.zespolowe.splix.config.validation.Colors;
import pl.zespolowe.splix.dto.UserSettingsDTO;

import javax.persistence.*;
import java.util.Random;

/**
 * User preferences
 *
 * @author Tomasz
 */
@Getter
@Setter
@Entity
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToOne(mappedBy = "settings")
    private User user;

    private boolean boardAnimation;

    private boolean namesAbove;

    private boolean dyingAnimation;

    @Colors
    @Column(length = 1000)
    private String colorsInCSV;

    public UserSettings() {
        colorsInCSV = getRandomColors();
        boardAnimation = namesAbove = dyingAnimation = true;
    }

    public UserSettings(User user) {
        this();
        this.user = user;
    }

    /**
     * @return random pattern
     */
    public static String getRandomColors() {
        Random obj = new Random();
        String colorCode = hslColor(obj.nextFloat(), 1.0f, 0.5f);
        return "{\"size\":1, \"color\":" + colorCode + ", \"pattern\": [[" + colorCode + "]]}";
    }

    static public String hslColor(float h, float s, float l) {
        float q, p, r, g, b;

        if (s == 0) {
            r = g = b = l; // achromatic
        } else {
            q = l < 0.5 ? (l * (1 + s)) : (l + s - l * s);
            p = 2 * l - q;
            r = hue2rgb(p, q, h + 1.0f / 3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1.0f / 3);
        }
        return String.format("\"#%02x%02x%02x\"",Math.round(r * 255), Math.round(g * 255) ,Math.round(b * 255));
    }

    private static float hue2rgb(float p, float q, float h) {
        if (h < 0) {
            h += 1;
        }
        if (h > 1) {
            h -= 1;
        }
        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }
        if (2 * h < 1) {
            return q;
        }
        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }
        return p;
    }

    /**
     * Update class fields from Data Transfer Object
     */
    public void updateFromDto(@NonNull UserSettingsDTO dto) {
        this.colorsInCSV = dto.getColorsInCSV();
        this.boardAnimation = dto.isBoardAnimation();
        this.dyingAnimation = dto.isDyingAnimation();
        this.namesAbove = dto.isNamesAbove();
    }
}
