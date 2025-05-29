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

    public final Player player1;
    public final Player player2;

    private final List<SetScore> scores = new ArrayList<>(3);

    private int currentSet;
    private final GameScore currentGame = new GameScore();

    private MatchStatus stage = MatchStatus.IN_PROGRESS;

    private static final int SETS_TO_WIN = 2;

    private Player winner;

    public OngoingMatch(@NonNull Player player1, @NonNull Player player2) {
        if (player1.equals(player2))
            throw new ValidationExeption("TennisMatch", "The player cannot play with himself", new Throwable());
        this.player1 = player1;
        this.player2 = player2;
    }

}
