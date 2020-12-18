package pl.zespolowe.splix.domain.user;

import lombok.Getter;
import lombok.Setter;

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

    @Column(length = 294)
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
        String colorCode = String.format("#%06x;", rand_num);
        String temp = colorCode.repeat(6) + "\n";
        return temp.repeat(6);
    }
}
