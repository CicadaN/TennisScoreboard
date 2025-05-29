package io.service;

import io.model.OngoingMatch;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, OngoingMatch> ongoingMatch;

    public OngoingMatchesService(Map<UUID, OngoingMatch> ongoingMatch) {
        this.ongoingMatch = new ConcurrentHashMap<>();
    }

    // Здесь мы создаем класс, обновляем ег счет и в случае окончания матча - закрываем его
    // передаем счет в GameScore а дальше уже обновляется в




}
