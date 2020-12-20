package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Direction;


@Getter
@Setter
public class SimpleMove {
    private int turn;
    private Direction move;
}
