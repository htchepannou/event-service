package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.GameResponse;
import com.tchepannou.event.service.domain.Game;

public class GameResponseMapper {
    private Game game;

    public GameResponse map (){
        GameResponse response = new GameResponse();
        response.setDuration(game.getDuration());
        response.setHome(game.getHome());
        response.setJerseyColor(game.getJerseyColor());
        response.setOpponent(game.getOpponent());
        response.setOutcome(game.getOutcome() != null ? game.getOutcome().name() : null);
        response.setOvertime(game.getOvertime());
        response.setScore1(game.getScore1());
        response.setScore2(game.getScore2());
        return response;
    }

    public GameResponseMapper withGame(Game game) {
        this.game = game;
        return this;
    }
}
