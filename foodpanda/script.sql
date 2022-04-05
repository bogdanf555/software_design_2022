-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema foodpanda
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema foodpanda
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `foodpanda` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `foodpanda` ;

-- -----------------------------------------------------
-- Table `foodpanda`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`user` (
  `id` INT NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`restaurant` (
  `id` INT NOT NULL,
  `location` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `admin_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_i6u3x7opncroyhd755ejknses` (`name` ASC) VISIBLE,
  INDEX `FKj37ybdgkcgg0xg4hgw2jmdoql` (`admin_id` ASC) VISIBLE,
  CONSTRAINT `FKj37ybdgkcgg0xg4hgw2jmdoql`
    FOREIGN KEY (`admin_id`)
    REFERENCES `foodpanda`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`food` (
  `id` INT NOT NULL,
  `category` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `restaurant_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_qkhr2yo38c1g9n5ss0jl7gxk6` (`name` ASC) VISIBLE,
  INDEX `FKm9xrxt95wwp1r2s7andom1l1c` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKm9xrxt95wwp1r2s7andom1l1c`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `foodpanda`.`restaurant` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`order` (
  `id` INT NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `customer_id` INT NULL DEFAULT NULL,
  `restaurant_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKoy0jl3x9b9iklefjfvxebe2ch` (`customer_id` ASC) VISIBLE,
  INDEX `FKcxidsvnoyt6wt7fs54y01edd8` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKcxidsvnoyt6wt7fs54y01edd8`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `foodpanda`.`restaurant` (`id`),
  CONSTRAINT `FKoy0jl3x9b9iklefjfvxebe2ch`
    FOREIGN KEY (`customer_id`)
    REFERENCES `foodpanda`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`order_food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`order_food` (
  `food_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`food_id`, `order_id`),
  INDEX `FKqasuiiaiilrdjf11j964gxcyg` (`order_id` ASC) VISIBLE,
  CONSTRAINT `FKey0a06m123d6wkkcwvtdijwvs`
    FOREIGN KEY (`food_id`)
    REFERENCES `foodpanda`.`food` (`id`),
  CONSTRAINT `FKqasuiiaiilrdjf11j964gxcyg`
    FOREIGN KEY (`order_id`)
    REFERENCES `foodpanda`.`order` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `foodpanda`.`restaurant_delivery_zones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `foodpanda`.`restaurant_delivery_zones` (
  `restaurant_id` INT NOT NULL,
  `delivery_zones` VARCHAR(255) NULL DEFAULT NULL,
  INDEX `FKtntot2nieyv9ofb3nh2n3u00w` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `FKtntot2nieyv9ofb3nh2n3u00w`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `foodpanda`.`restaurant` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
