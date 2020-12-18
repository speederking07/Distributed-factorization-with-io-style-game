package pl.zespolowe.splix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zespolowe.splix.domain.user.UserSettings;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDTO {
    private boolean boardAnimation;
    private boolean namesAbove;
    private boolean dyingAnimation;
    private String colorsInCSV;

    public UserSettingsDTO(UserSettings settings) {
        this.boardAnimation = settings.isBoardAnimation();
        this.namesAbove = settings.isNamesAbove();
        this.dyingAnimation = settings.isDyingAnimation();
        this.colorsInCSV = settings.getColorsInCSV();
    }
}
