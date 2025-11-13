-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `escape_room` ;

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `escape_room` DEFAULT CHARACTER SET utf8 ;
USE `escape_room` ;

-- -----------------------------------------------------
-- Table `escape_room`.`room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`room` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`room` (
    `id_room` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` DECIMAL NOT NULL DEFAULT 0.0,
    `difficulty_level` VARCHAR(45) NULL,
    PRIMARY KEY (`id_room` , `price`)
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `idroom_UNIQUE` ON `escape_room`.`room` (`id_room` ASC) ;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`room` (`name` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`theme`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`theme` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`theme` (
    `id_theme` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_theme`)
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_theme_UNIQUE` ON `escape_room`.`theme` (`id_theme` ASC) ;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`theme` (`name` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`clue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`clue` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`clue` (
    `id_clue` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `text` TEXT(250) NOT NULL,
    `theme` VARCHAR(45) NULL,
    `price` DECIMAL NULL,
    `theme_id_theme` INT NOT NULL,
    PRIMARY KEY (`id_clue` , `theme_id_theme`),
    CONSTRAINT `fk_clue_theme1` FOREIGN KEY (`theme_id_theme`)
        REFERENCES `escape_room`.`theme` (`id_theme`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `idclue_UNIQUE` ON `escape_room`.`clue` (`id_clue` ASC) ;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`clue` (`name` ASC) ;

CREATE INDEX `fk_clue_theme1_idx` ON `escape_room`.`clue` (`theme_id_theme` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`material` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`material` (
    `id_material` INT NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_material`)
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_material_UNIQUE` ON `escape_room`.`material` (`id_material` ASC) ;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`material` (`name` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`decoration_object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`decoration_object` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`decoration_object` (
    `id_decoration_object` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `description` VARCHAR(250) NOT NULL,
    `material` VARCHAR(45) NULL,
    `price` DECIMAL NULL,
    `material_id_material` INT NOT NULL,
    PRIMARY KEY (`id_decoration_object` , `material_id_material`),
    CONSTRAINT `fk_decoration_object_material1` FOREIGN KEY (`material_id_material`)
        REFERENCES `escape_room`.`material` (`id_material`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_decoration_object_UNIQUE` ON `escape_room`.`decoration_object` (`id_decoration_object` ASC) ;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`decoration_object` (`name` ASC) ;

CREATE INDEX `fk_decoration_object_material1_idx` ON `escape_room`.`decoration_object` (`material_id_material` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`player` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`player` (
    `id_player` INT NOT NULL AUTO_INCREMENT,
    `nick_name` VARCHAR(45) NOT NULL,
    `email` VARCHAR(120) NOT NULL,
    PRIMARY KEY (`id_player`)
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_player_UNIQUE` ON `escape_room`.`player` (`id_player` ASC) ;

CREATE UNIQUE INDEX `nick_name_UNIQUE` ON `escape_room`.`player` (`nick_name` ASC) ;

CREATE UNIQUE INDEX `email_UNIQUE` ON `escape_room`.`player` (`email` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`certificate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`certificate` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`certificate` (
    `id_certificate` INT NOT NULL AUTO_INCREMENT,
    `text_body` TEXT NULL,
    `reward` VARCHAR(45) NULL,
    `player_id_player` INT NOT NULL,
    `room_id_room` INT NOT NULL,
    `room_price` DECIMAL NOT NULL,
    PRIMARY KEY (`id_certificate` , `player_id_player` , `room_id_room` , `room_price`),
    CONSTRAINT `fk_certificate_player1` FOREIGN KEY (`player_id_player`)
        REFERENCES `escape_room`.`player` (`id_player`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_certificate_room1` FOREIGN KEY (`room_id_room` , `room_price`)
        REFERENCES `escape_room`.`room` (`id_room` , `price`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_certificate_UNIQUE` ON `escape_room`.`certificate` (`id_certificate` ASC) ;

CREATE INDEX `fk_certificate_player1_idx` ON `escape_room`.`certificate` (`player_id_player` ASC) ;

CREATE INDEX `fk_certificate_room1_idx` ON `escape_room`.`certificate` (`room_id_room` ASC, `room_price` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`ticket` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`ticket` (
    `id_ticket` INT NOT NULL AUTO_INCREMENT,
    `price` DECIMAL NOT NULL DEFAULT 0.0,
    `date_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP (),
    `player_id_player` INT NOT NULL,
    `room_id_room` INT NOT NULL,
    `room_price` DECIMAL NOT NULL,
    PRIMARY KEY (`id_ticket` , `player_id_player` , `room_id_room` , `room_price`),
    CONSTRAINT `fk_ticket_player` FOREIGN KEY (`player_id_player`)
        REFERENCES `escape_room`.`player` (`id_player`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_ticket_room1` FOREIGN KEY (`room_id_room` , `room_price`)
        REFERENCES `escape_room`.`room` (`id_room` , `price`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE UNIQUE INDEX `id_ticket_UNIQUE` ON `escape_room`.`ticket` (`id_ticket` ASC) ;

CREATE INDEX `fk_ticket_player_idx` ON `escape_room`.`ticket` (`player_id_player` ASC) ;

CREATE INDEX `fk_ticket_room1_idx` ON `escape_room`.`ticket` (`room_id_room` ASC, `room_price` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`room_has_clues`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`room_has_clues` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`room_has_clues` (
    `clue_id_clue` INT NOT NULL,
    `room_id_room` INT NOT NULL,
    `room_price` DECIMAL NOT NULL,
    PRIMARY KEY (`clue_id_clue` , `room_id_room` , `room_price`),
    CONSTRAINT `fk_clue_has_room_clue1` FOREIGN KEY (`clue_id_clue`)
        REFERENCES `escape_room`.`clue` (`id_clue`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_clue_has_room_room1` FOREIGN KEY (`room_id_room` , `room_price`)
        REFERENCES `escape_room`.`room` (`id_room` , `price`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE INDEX `fk_clue_has_room_room1_idx` ON `escape_room`.`room_has_clues` (`room_id_room` ASC, `room_price` ASC) ;

CREATE INDEX `fk_clue_has_room_clue1_idx` ON `escape_room`.`room_has_clues` (`clue_id_clue` ASC) ;


-- -----------------------------------------------------
-- Table `escape_room`.`room_has_decoration_object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `escape_room`.`room_has_decoration_object` ;

CREATE TABLE IF NOT EXISTS `escape_room`.`room_has_decoration_object` (
    `room_id_room` INT NOT NULL,
    `room_price` DECIMAL NOT NULL,
    `decoration_object_id_decoration_object` INT NOT NULL,
    PRIMARY KEY (`room_id_room` , `room_price` , `decoration_object_id_decoration_object`),
    CONSTRAINT `fk_room_has_decoration_object_room1` FOREIGN KEY (`room_id_room` , `room_price`)
        REFERENCES `escape_room`.`room` (`id_room` , `price`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_room_has_decoration_object_decoration_object1` FOREIGN KEY (`decoration_object_id_decoration_object`)
        REFERENCES `escape_room`.`decoration_object` (`id_decoration_object`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;

CREATE INDEX `fk_room_has_decoration_object_decoration_object1_idx` ON `escape_room`.`room_has_decoration_object` (`decoration_object_id_decoration_object` ASC) ;

CREATE INDEX `fk_room_has_decoration_object_room1_idx` ON `escape_room`.`room_has_decoration_object` (`room_id_room` ASC, `room_price` ASC) ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
