package pl.zespolowe.splix.domain.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.zespolowe.splix.config.validation.Colors;
import pl.zespolowe.splix.dto.UserSettingsDTO;

import javax.persistence.*;
import java.util.Random;

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
    @Column(length = 400)
    private String colorsInCSV;

    public UserSettings() {
        colorsInCSV = getRandomColors();
        boardAnimation = namesAbove = dyingAnimation = true;
    }

    public UserSettings(User user) {
        this();
        this.user = user;
    }

    public static String getRandomColors() {
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        String colorCode = String.format("\"#%06x\"", rand_num);
        return "{\"size\":1, \"color\":"+colorCode+", \"pattern\": [["+colorCode+"]]}";
    }

    public void updateFromDto(@NonNull UserSettingsDTO dto) {
        this.colorsInCSV = dto.getColorsInCSV();
        this.boardAnimation = dto.isBoardAnimation();
        this.dyingAnimation = dto.isDyingAnimation();
        this.namesAbove = dto.isNamesAbove();
    }
}
