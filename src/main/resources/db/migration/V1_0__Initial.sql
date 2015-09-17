CREATE TABLE address(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(1024),
    country_code VARCHAR(3),
    zip_code VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100),
    quality BIGINT,
    hash VARCHAR(32) NOT NULL
) ENGINE=INNODB;
CREATE INDEX idx_address__hash ON address (hash);


CREATE TABLE location(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    website VARCHAR(2048),
    name VARCHAR(1024),
    address_fk BIGINT,

    CONSTRAINT fk_location__address_fk FOREIGN KEY (address_fk) REFERENCES address(id)
) ENGINE=INNODB;


CREATE TABLE event(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    calendar_id BIGINT NOT NULL,
    recurrence_id VARCHAR(50),
    address_fk BIGINT,
    location_fk BIGINT,
    type INT,
    name VARCHAR(255),
    description TEXT,
    start_date DATE,
    start_time TIME,
    end_time TIME,
    require_rsvp BIT,
    deleted BIT,
    created DATETIME,
    updated DATETIME,

    CONSTRAINT fk_event__location_fk FOREIGN KEY (location_fk) REFERENCES location(id),
    CONSTRAINT fk_event__address_fk FOREIGN KEY (address_fk) REFERENCES address(id)
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
