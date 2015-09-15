-- clean
drop table IF EXISTS tmp_event;
delete from participant;
delete from location;
delete from event;

-- events
INSERT INTO event(
  id,
  calendar_id,
  start_date,
  start_time,
  deleted
)
  SELECT
    node_id,
    node_channel_fk,
    node_date,
    TIME(node_date),
    node_deleted
  FROM is5.node
  WHERE node_type_fk=4
;

-- attributes
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET name=nattr_value WHERE nattr_name='title';
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET description=nattr_value WHERE nattr_name='description';
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET require_rsvp=if(nattr_value='1', true, false) WHERE nattr_name='rsvp';
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET recurrence_id=nattr_value WHERE nattr_name='reccurence_id';
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET type=
if(nattr_value='match', 1,
   if(nattr_value='training', 2,
      if(nattr_value='event', 3, 4)
   )
)
WHERE nattr_name='event_type';

-- end hour/time
CREATE TEMPORARY TABLE tmp_event(
  id BIGINT,
  end_hour INT,
  end_minute INT
);

INSERT INTO tmp_event(id, end_hour)
  SELECT nattr_node_fk, nattr_value FROM is5.nattr JOIN event ON nattr_node_fk=id
  WHERE nattr_name='end_hour'
ON DUPLICATE KEY UPDATE end_hour=nattr_value;

INSERT INTO tmp_event(id, end_minute)
  SELECT nattr_node_fk, nattr_value FROM is5.nattr JOIN event ON nattr_node_fk=id
  WHERE nattr_name='end_time'
ON DUPLICATE KEY UPDATE end_minute=nattr_value;

UPDATE event E JOIN tmp_event T ON E.id=T.id SET E.end_time=MAKETIME(T.end_hour, T.end_minute, 0) WHERE T.end_hour IS NOT NULL and T.end_minute IS NOT NULL;
