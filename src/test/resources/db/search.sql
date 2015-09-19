INSERT INTO address(id, street, country_code, zip_code, city, state, quality, hash)
  VALUES(90, '3030 Linton', 'CA', 'H0H 0H0', 'Montreal', 'QC', 31, 'address#1');

INSERT INTO address(id, street, country_code, zip_code, city, state, quality, hash)
  VALUES(91, '340 Pascal', 'CA', 'H1K 1C6', 'Laval', 'QC', 31, 'address#2');


INSERT INTO place(id, address_fk, name, website)
    VALUES(190, 90, 'Location 1', 'http://location1.com');

INSERT INTO place(id, address_fk, name, website)
  VALUES(191, 91, 'Location 2', 'http://location2.com');



INSERT INTO event(id,calendar_id, address_fk, place_fk, recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
  VALUES(100, 1000, 90, 190, null, 1, 'vs Arsenal', '2015/11/10', '10:30', '11:30', FALSE , false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a game');

INSERT INTO event(id,calendar_id, address_fk, place_fk, recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(101, 1000, 91, 191, '43094039', 2, 'Practice101', '2015/11/12', '11:30', '18:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice101');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(102, 1001, null, 2, 'Practice102', '2015/11/12', '9:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice102');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(103, 1003, null, 2, 'Excluded - Practice103', '2015/11/14', '18:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice103');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(104, 1001, null, 2, 'Excluded - Practice104', '2014/11/15', '18:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice104');
