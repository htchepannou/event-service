-- get game
INSERT INTO address(id, street, country_code, zip_code, city, state, quality, hash)
VALUES(90, '3030 Linton', 'CA', 'H0H 0H0', 'Montreal', 'QC', 31, 'address#1');

INSERT INTO place(id, name, website)
VALUES(190, 'Location 1', 'http://location1.com');

INSERT INTO event(id,calendar_id, address_fk, place_fk, recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(100, 1000, 90, 190, null, 1, 'vs Arsenal', '2015/11/10', '10:30', '11:30', FALSE , false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a game');

INSERT INTO game(id, opponent, score1, score2, jersey_color, home, overtime, outcome, duration)
VALUES(100, 'Arsenal', 1, 0, 'red', true, false, 'W', 90);

