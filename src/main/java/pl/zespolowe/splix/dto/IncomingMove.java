package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
public class IncomingMove {
    private int turn;
    private Direction move;
}
