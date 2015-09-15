INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
  VALUES(100, 1000, null, 1, 'vs Arsenal', '2015/11/10', '10:30', '11:30', FALSE , false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a game');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(101, 1000, '43094039', 2, 'Practice101', '2015/11/12', '11:30', '18:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice101');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(102, 1001, null, 2, 'Practice102', '2015/11/12', '9:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice102');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(103, 1003, null, 2, 'Excluded - Practice103', '2015/11/14', '18:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice103');

INSERT INTO event(id,calendar_id,recurrence_id,type,name,start_date,start_time,end_time,require_rsvp,deleted,created,updated,description)
VALUES(104, 1001, null, 2, 'Excluded - Practice104', '2014/11/15', '18:30', '11:30', true, false, '2015/10/01 10:30', '2015/10/01 10:30', 'This is a practice104');
