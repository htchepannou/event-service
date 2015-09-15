CREATE TABLE location(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    street1 VARCHAR(100),
    street2 VARCHAR(100),
    city VARCHAR(50),
    zip_code VARCHAR(30),
    state_code VARCHAR(30),
    country_code VARCHAR(3),
    website VARCHAR(2048)
) ENGINE=INNODB;

CREATE TABLE event(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    calendar_id BIGINT NOT NULL,
    recurrence_id VARCHAR(50),
    type INT,
    location_fk BIGINT,
    name VARCHAR(255),
    description TEXT,
    start_date DATE,
    start_time TIME,
    end_time TIME,
    require_rsvp BIT,
    deleted BIT,
    created DATETIME,
    updated DATETIME,

    CONSTRAINT fk_event__location_fk FOREIGN KEY (location_fk) REFERENCES location(id)
) ENGINE=INNODB;
CREATE INDEX idx_event__calendar_id ON event (calendar_id);
CREATE INDEX idx_event__start_date ON event (start_date);
CREATE INDEX idx_event__recurrence_id ON event (recurrence_id);


CREATE TABLE participant(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    event_fk BIGINT NOT NULL,
    party_id BIGINT NOT NULL,
    role_id INT,
    status INT,

    CONSTRAINT fk_participant_event_fk FOREIGN KEY (event_fk) REFERENCES event(id)
) ENGINE=INNODB;
