package pl.zespolowe.splix.dto;

import lombok.*;
import pl.zespolowe.splix.config.validation.Colors;
import pl.zespolowe.splix.domain.user.UserSettings;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDTO {
    @NonNull
    private boolean boardAnimation;

    @NonNull
    private boolean namesAbove;

    @NonNull
    private boolean dyingAnimation;

    @Colors
    @NonNull
    private String colorsInCSV;

    public UserSettingsDTO(UserSettings settings) {
        this.boardAnimation = settings.isBoardAnimation();
        this.namesAbove = settings.isNamesAbove();
        this.dyingAnimation = settings.isDyingAnimation();
        this.colorsInCSV = settings.getColorsInCSV();
    }
}
