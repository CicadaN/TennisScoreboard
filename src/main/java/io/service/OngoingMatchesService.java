package io.service;

import io.entity.Player;
import io.exception.ValidationExeption;
import io.model.OngoingMatch;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class OngoingMatchesService {

    private final Map<UUID, OngoingMatch> matches = new ConcurrentHashMap<>();
    private final TennisScoreCalculator calculator = new TennisScoreCalculator();

    public UUID startMatch(Player firstPlayer, Player secondPlayer) {
        var match = new OngoingMatch(firstPlayer, secondPlayer);
        matches.put(match.getUuid(), match);
        return match.getUuid();
    }

    public void Point(UUID uuid, boolean first) {
        var m = get(uuid);
        boolean finished = calculator.pointWonBy(m, first);
        if (finished) {
            matches.remove(uuid);
        }
    }

    public OngoingMatch get(UUID uuid) {
        var m = matches.get(uuid);
        if (m == null) throw new ValidationExeption("OngoingMatchesService", "Not found your match", new Throwable());
        return m;
    }

//    public OngoingMatchDto getMatchDto(UUID uuid) {
//        var match = get(uuid);
//        return mapToDto(match);
//    }

//    public OngoingMatchDto mapToDto(OngoingMatch match) {
//        return new OngoingMatchDto(
//                match.getUuid(),
//                match.getPlayerFirst().getName(),
//                match.getPlayerSecond().getName(),
//                match.getScores(),
//                match.getCurrentGame(),
//                match.getStage().name(),
//                match.getWinner().getName()
//        );
//    }

}
