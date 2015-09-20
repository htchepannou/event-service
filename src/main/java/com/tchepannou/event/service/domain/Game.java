package com.tchepannou.event.service.domain;

public class Game {
    public enum Outcome {
        win('W'),
        loss('L'),
        draw('D');

        final char code;

        Outcome (final char code){
            this.code = code;
        }

        public char code (){
            return code;
        }

        public static Outcome fromCode (char code){
            for (final Outcome type : Outcome.values()){
                if (type.code == code){
                    return type;
                }
            }
            return null;
        }
    };

    //-- Attributes
    private long id;
    private String opponent;
    private Integer score1;
    private Integer score2;
    private String jerseyColor;
    private Boolean home;
    private Boolean overtime;
    private Outcome outcome;
    private Integer duration;


    //-- Getter/Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public String getJerseyColor() {
        return jerseyColor;
    }

    public void setJerseyColor(String jerseyColor) {
        this.jerseyColor = jerseyColor;
    }

    public Boolean getHome() {
        return home;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public Boolean getOvertime() {
        return overtime;
    }

    public void setOvertime(Boolean overtime) {
        this.overtime = overtime;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
