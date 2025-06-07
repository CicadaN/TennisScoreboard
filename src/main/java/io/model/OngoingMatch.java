package io.model;

import io.entity.Player;
import io.exception.ValidationExeption;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OngoingMatch {

    private final UUID uuid = UUID.randomUUID();

    public final Player playerFirst;
    public final Player playerSecond;

    private final List<SetScore> scores = new ArrayList<>(3);

    private SetScore setScore = new SetScore();
    private GameScore currentGame = new GameScore();

    private MatchStatus stage = MatchStatus.IN_PROGRESS;

    private Player winner;

    public OngoingMatch(@NonNull Player player1, @NonNull Player player2) {
        if (player1.equals(player2))
            throw new ValidationExeption("TennisMatch", "The player cannot play with himself", new Throwable());
        this.playerFirst = player1;
        this.playerSecond = player2;
    }

}
