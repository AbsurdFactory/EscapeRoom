CREATE SCHEMA IF NOT EXISTS escape_room DEFAULT CHARACTER SET utf8;
USE escape_room;

CREATE TABLE room (
  id_room INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  difficulty_level VARCHAR(45),
  PRIMARY KEY (id_room, price),
  UNIQUE KEY name_UNIQUE (name)
) ENGINE=InnoDB;

CREATE TABLE clue (
  id_clue INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  text TEXT NOT NULL,
  theme VARCHAR(45) NOT NULL,
  price DECIMAL(10,2),
  PRIMARY KEY (id_clue),
  UNIQUE KEY name_UNIQUE (name)
) ENGINE=InnoDB;

CREATE TABLE decoration_object (
  id_decoration_object INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  description VARCHAR(250),
  material VARCHAR(45),
  price DECIMAL(10,2),
  PRIMARY KEY (id_decoration_object),
  UNIQUE KEY name_UNIQUE (name)
) ENGINE=InnoDB;

CREATE TABLE player (
  id_player INT NOT NULL AUTO_INCREMENT,
  nick_name VARCHAR(45) NOT NULL,
  email VARCHAR(120) NOT NULL,
  subscribed BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id_player),
  UNIQUE KEY nick_name_UNIQUE (nick_name),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE certificate (
  id_certificate INT NOT NULL AUTO_INCREMENT,
  text_body TEXT,
  reward VARCHAR(45),
  player_id_player INT NOT NULL,
  room_id_room INT NOT NULL,
  PRIMARY KEY (id_certificate, player_id_player, room_id_room),
  FOREIGN KEY (player_id_player) REFERENCES player(id_player),
  FOREIGN KEY (room_id_room) REFERENCES room(id_room)
) ENGINE=InnoDB;

CREATE TABLE ticket (
  id_ticket INT NOT NULL AUTO_INCREMENT,
  price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  date_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  player_id_player INT NOT NULL,
  room_id_room INT NOT NULL,
  room_price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_ticket, player_id_player, room_id_room, room_price),
  FOREIGN KEY (player_id_player) REFERENCES player(id_player),
  FOREIGN KEY (room_id_room, room_price) REFERENCES room(id_room, price)
) ENGINE=InnoDB;

CREATE TABLE room_has_clues (
  clue_id_clue INT NOT NULL,
  room_id_room INT NOT NULL,
  PRIMARY KEY (clue_id_clue, room_id_room),
  FOREIGN KEY (clue_id_clue) REFERENCES clue(id_clue),
  FOREIGN KEY (room_id_room) REFERENCES room(id_room)
) ENGINE=InnoDB;

CREATE TABLE room_has_decoration_object (
  room_id_room INT NOT NULL,
  decoration_object_id_decoration_object INT NOT NULL,
  PRIMARY KEY (room_id_room, decoration_object_id_decoration_object),
  FOREIGN KEY (room_id_room) REFERENCES room(id_room),
  FOREIGN KEY (decoration_object_id_decoration_object) REFERENCES decoration_object(id_decoration_object)
) ENGINE=InnoDB;


CREATE TABLE escape (
  id_escape INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_escape)
) ENGINE=InnoDB;


CREATE TABLE escape_has_room (
  escape_id_escape INT NOT NULL,
  room_id_room INT NOT NULL,
  PRIMARY KEY (escape_id_escape, room_id_room),
  FOREIGN KEY (escape_id_escape) REFERENCES escape(id_escape),
  FOREIGN KEY (room_id_room) REFERENCES room(id_room)
) ENGINE=InnoDB;

INSERT INTO escape (id_escape, name) VALUES
(1, 'Haunted Escape'),
(2, 'Science Escape'),
(3, 'Fantasy Escape');

INSERT INTO room VALUES
(1,'Haunted Mansion',30.00,'Hard'),
(2,'Pirate Cove',25.00,'Medium'),
(3,'Secret Laboratory',35.00,'Hard'),
(4,'Magic Forest',20.00,'Easy');

INSERT INTO clue VALUES
(1,'Bloody Key','A key covered in something red… opens a metal box.','Horror',4.00),
(2,'Ancient Map','A map showing hidden tunnels.','Adventure',2.00),
(3,'Chemical Formula','A ruined formula needed for solving the puzzle.','Science',4.00),
(4,'Magic Rune','A glowing rune with mysterious symbols.','Fantasy',3.00);

INSERT INTO decoration_object VALUES
(1,'Skull Candle','A candle shaped like a skull.','Wax',10.00),
(2,'Treasure Chest','A wooden pirate-style chest.','Wood',25.00),
(3,'Lab Tubes Set','A set of colorful test tubes.','Glass',15.00),
(4,'Enchanted Tree','A small glowing fantasy tree.','Plastic',30.00);

INSERT INTO player VALUES
(1,'GhostHunter','ghost@example.com',FALSE),
(2,'CaptainJack','jack@example.com',FALSE),
(3,'LabMaster','lab@example.com',FALSE),
(4,'FairyQueen','fairy@example.com',FALSE);

INSERT INTO certificate VALUES
(1,'Completed Haunted Mansion in 58 minutes!','Bronze Medal',1,1),
(2,'Found all hidden treasures in Pirate Cove!','Silver Medal',2,2),
(3,'Solved the lab mystery perfectly!','Gold Medal',3,3),
(4,'Escaped the Magic Forest with magic!','Magic Medal',4,4);

INSERT INTO room_has_clues VALUES
(1,1),(2,2),(3,3),(4,4);

INSERT INTO room_has_decoration_object VALUES
(1,1),(2,2),(3,3),(4,4);

INSERT INTO escape VALUES (1),(2),(3);

INSERT INTO escape_has_room VALUES
(1,1),(1,2),(2,3),(3,4);

INSERT INTO ticket VALUES
(1,30.00,'2025-11-25 23:01:34',1,1,30.00),
(2,25.00,'2025-11-25 23:01:34',2,2,25.00),
(3,35.00,'2025-11-25 23:01:34',3,3,35.00),
(4,20.00,'2025-11-25 23:01:34',4,4,20.00);
