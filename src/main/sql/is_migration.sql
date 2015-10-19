-- clean
delete from participant;
delete from game;
delete from event;
delete from place;
delete from address;



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
UPDATE event JOIN is5.nattr ON id=nattr_node_fk SET description=nattr_value WHERE nattr_name='notes';
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


-- address
INSERT INTO address(id, street, hash)
  SELECT nattr_node_fk, nattr_value, '-' FROM is5.nattr JOIN event ON id=nattr_node_fk WHERE nattr_name='street';

UPDATE address JOIN is5.nattr ON id=nattr_node_fk
SET city=nattr_value
WHERE nattr_name='city';

UPDATE address JOIN is5.nattr ON id=nattr_node_fk
SET state=nattr_value
WHERE nattr_name='state';

UPDATE address JOIN is5.nattr ON id=nattr_node_fk
SET country_code=nattr_value
WHERE nattr_name='country';

UPDATE address JOIN is5.nattr ON id=nattr_node_fk
SET zip_code=nattr_value
WHERE nattr_name='zip_code';

UPDATE address JOIN is5.nattr ON id=nattr_node_fk
SET country_code=nattr_value
WHERE nattr_name='country';

UPDATE address
SET hash=MD5(
    LOWER(
        CONCAT(
          IF(LENGTH(state)>0,          state, ''),
          IF(LENGTH(city)>0,           city,  ''),
          IF(LENGTH(country_code)>0,   country_code, ''),
          IF(LENGTH(zip_code)>0,       zip_code, ''),
          IF(LENGTH(street)>0,         street, '')
      )
    )
);

UPDATE address
SET quality=IF(LENGTH(state)>0,            1, 0)
            + IF(LENGTH(city)>0,           2, 0)
            + IF(LENGTH(country_code)>0,   4, 0)
            + IF(LENGTH(zip_code)>0,       8, 0)
            + IF(LENGTH(street)>0,         16, 0)
;

UPDATE event E JOIN address L ON E.id=L.id SET E.address_fk=L.id;

-- game
INSERT INTO game(id)
  SELECT id FROM event WHERE type=1;

UPDATE game JOIN is5.nattr ON id=nattr_node_fk
SET score1=nattr_value
WHERE nattr_name='score1';

UPDATE game JOIN is5.nattr ON id=nattr_node_fk
SET score2=nattr_value
WHERE nattr_name='score2';

UPDATE game JOIN is5.nattr ON id=nattr_node_fk
SET opponent=nattr_value
WHERE nattr_name='opponent';

UPDATE game JOIN is5.nattr ON id=nattr_node_fk
SET outcome=nattr_value
WHERE nattr_name='outcome';

UPDATE game JOIN is5.nattr ON id=nattr_node_fk
SET jersey_color=nattr_value
WHERE nattr_name='jersey_color';



-- place
INSERT INTO place(id, name)
  SELECT DISTINCT nattr_node_fk, nattr_value
  FROM is5.nattr JOIN event ON nattr_node_fk=id
  WHERE nattr_name='location';

UPDATE place JOIN is5.nattr ON id=nattr_node_fk
SET website=nattr_value
WHERE nattr_name='url';

UPDATE event E JOIN place L ON E.id=L.id SET E.place_fk=L.id;



-- hash_id
CREATE TEMPORARY TABLE tmp_hash_id(
  hash VARCHAR(50) PRIMARY KEY,
  address_fk BIGINT
);

INSERT INTO tmp_hash_id(hash, address_fk)
  SELECT hash, id
  FROM address
  GROUP BY hash
  ORDER BY quality DESC
;

UPDATE event E
  JOIN address A ON E.address_fk=A.id
  JOIN tmp_hash_id H ON H.hash=A.hash
SET E.address_fk=H.address_fk;

UPDATE place E
  JOIN address A ON E.address_fk=A.id
  JOIN tmp_hash_id H ON H.hash=A.hash
SET E.address_fk=H.address_fk;

UPDATE place L JOIN event E ON E.id=L.id SET L.address_fk=E.address_fk;

