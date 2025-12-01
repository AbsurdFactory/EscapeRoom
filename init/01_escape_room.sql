-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS escape_room DEFAULT CHARACTER SET utf8 ;
USE `escape_room` ;


-- -----------------------------------------------------
-- Table `escape_room`.`room`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`room` (
  `id_room` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0.0,
  `difficulty_level` VARCHAR(45) NULL,
  PRIMARY KEY (`id_room`, `price`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idroom_UNIQUE` ON `escape_room`.`room` (`id_room` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`room` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`clue`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`clue` (
  `id_clue` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `text` TEXT(250) NOT NULL,
  `theme` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id_clue`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idclue_UNIQUE` ON `escape_room`.`clue` (`id_clue` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`clue` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`decoration_object`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`decoration_object` (
  `id_decoration_object` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(250) NULL,
  `material` VARCHAR(45) NULL,
  `price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id_decoration_object`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_decoration_object_UNIQUE` ON `escape_room`.`decoration_object` (`id_decoration_object` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `escape_room`.`decoration_object` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`player`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`player` (
  `id_player` INT NOT NULL AUTO_INCREMENT,
  `nick_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(120) NOT NULL,
  `subscribed` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id_player`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_player_UNIQUE` ON `escape_room`.`player` (`id_player` ASC) VISIBLE;

CREATE UNIQUE INDEX `nick_name_UNIQUE` ON `escape_room`.`player` (`nick_name` ASC) VISIBLE;

CREATE UNIQUE INDEX `email_UNIQUE` ON `escape_room`.`player` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`certificate`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`certificate` (
  `id_certificate` INT NOT NULL AUTO_INCREMENT,
  `text_body` TEXT NULL,
  `reward` VARCHAR(45) NULL,
  `player_id_player` INT NOT NULL,
  `room_id_room` INT NOT NULL,
  PRIMARY KEY (`id_certificate`, `player_id_player`, `room_id_room`),
  CONSTRAINT `fk_certificate_player1`
    FOREIGN KEY (`player_id_player`)
    REFERENCES `escape_room`.`player` (`id_player`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_certificate_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_certificate_UNIQUE` ON `escape_room`.`certificate` (`id_certificate` ASC) VISIBLE;

CREATE INDEX `fk_certificate_player1_idx` ON `escape_room`.`certificate` (`player_id_player` ASC) VISIBLE;

CREATE INDEX `fk_certificate_room1_idx` ON `escape_room`.`certificate` (`room_id_room` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`ticket`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`ticket` (
  `id_ticket` INT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL NOT NULL DEFAULT 0.0,
  `date_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  `player_id_player` INT NOT NULL,
  `room_id_room` INT NOT NULL,
  `room_price` DECIMAL NOT NULL,
  PRIMARY KEY (`id_ticket`, `player_id_player`, `room_id_room`, `room_price`),
  CONSTRAINT `fk_ticket_player`
    FOREIGN KEY (`player_id_player`)
    REFERENCES `escape_room`.`player` (`id_player`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_ticket_UNIQUE` ON `escape_room`.`ticket` (`id_ticket` ASC) VISIBLE;

CREATE INDEX `fk_ticket_player_idx` ON `escape_room`.`ticket` (`player_id_player` ASC) VISIBLE;

CREATE INDEX `fk_ticket_room1_idx` ON `escape_room`.`ticket` (`room_id_room` ASC, `room_price` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`room_has_clues`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`room_has_clues` (
  `clue_id_clue` INT NOT NULL,
  `room_id_room` INT NOT NULL,
  PRIMARY KEY (`clue_id_clue`, `room_id_room`),
  CONSTRAINT `fk_clue_has_room_clue1`
    FOREIGN KEY (`clue_id_clue`)
    REFERENCES `escape_room`.`clue` (`id_clue`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_clue_has_room_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_clue_has_room_room1_idx` ON `escape_room`.`room_has_clues` (`room_id_room` ASC) VISIBLE;

CREATE INDEX `fk_clue_has_room_clue1_idx` ON `escape_room`.`room_has_clues` (`clue_id_clue` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`room_has_decoration_object`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`room_has_decoration_object` (
  `room_id_room` INT NOT NULL,
  `decoration_object_id_decoration_object` INT NOT NULL,
  PRIMARY KEY (`room_id_room`, `decoration_object_id_decoration_object`),
  CONSTRAINT `fk_room_has_decoration_object_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_has_decoration_object_decoration_object1`
    FOREIGN KEY (`decoration_object_id_decoration_object`)
    REFERENCES `escape_room`.`decoration_object` (`id_decoration_object`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_room_has_decoration_object_decoration_object1_idx` ON `escape_room`.`room_has_decoration_object` (`decoration_object_id_decoration_object` ASC) VISIBLE;

CREATE INDEX `fk_room_has_decoration_object_room1_idx` ON `escape_room`.`room_has_decoration_object` (`room_id_room` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`escape`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `escape_room`.`escape` (
  `id_escape` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_escape`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idescape_UNIQUE` ON `escape_room`.`escape` (`id_escape` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `escape_room`.`escape_has_room`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `escape_room`.`escape_has_room` (
  `escape_id_escape` INT NOT NULL,
  `room_id_room` INT NOT NULL,
  PRIMARY KEY (`escape_id_escape`, `room_id_room`),
  CONSTRAINT `fk_escape_has_room_escape1`
    FOREIGN KEY (`escape_id_escape`)
    REFERENCES `escape_room`.`escape` (`id_escape`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_escape_has_room_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_escape_has_room_room1_idx` ON `escape_room`.`escape_has_room` (`room_id_room` ASC) VISIBLE;

CREATE INDEX `fk_escape_has_room_escape1_idx` ON `escape_room`.`escape_has_room` (`escape_id_escape` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
