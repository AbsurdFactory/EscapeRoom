USE `escape_room` ;
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

--INSERT INTO escape VALUES (1),(2),(3);

INSERT INTO escape_has_room VALUES
(1,1),(1,2),(2,3),(3,4);

INSERT INTO ticket VALUES
(1,30.00,'2025-11-25 23:01:34',1,1,30.00),
(2,25.00,'2025-11-25 23:01:34',2,2,25.00),
(3,35.00,'2025-11-25 23:01:34',3,3,35.00),
(4,20.00,'2025-11-25 23:01:34',4,4,20.00);
