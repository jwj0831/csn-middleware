CREATE TABLE IF NOT EXISTS `csn_test`.`csn_snsr_meta` (
  `snsr_uri` VARCHAR(255) NOT NULL,
  `snsr_ip` VARCHAR(15) NOT NULL,
  `snsr_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`snsr_uri`),
  UNIQUE INDEX `snsr_uri_UNIQUE` (`snsr_uri` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `csn_test`.`csn_sn_meta` (
  `sn_id` INT NOT NULL AUTO_INCREMENT,
  `sn_name` VARCHAR(45) NOT NULL,
  `sn_create_date` VARCHAR(14) NULL,
  `sn_delete_date` VARCHAR(14) NULL,
  `sn_life` INT NULL DEFAULT 1,
  PRIMARY KEY (`sn_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `csn_test`.`csn_sn_members` (
  `sn_id` INT NOT NULL,
  `sn_member` VARCHAR(255) NOT NULL,
  INDEX `snsr_id_idx` (`sn_member` ASC),
  CONSTRAINT `sn_id`
    FOREIGN KEY (`sn_id`)
    REFERENCES `csn_test`.`csn_sn_meta` (`sn_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `snsr_id`
    FOREIGN KEY (`sn_member`)
    REFERENCES `csn_test`.`csn_snsr_meta` (`snsr_uri`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `csn_test`.`csn_snsr_tags` (
  `snsr_uri` VARCHAR(255) NOT NULL,
  `snsr_tag` VARCHAR(45) NOT NULL,
  INDEX `snsr_uri_for_tag_idx` (`snsr_uri` ASC),
  CONSTRAINT `snsr_uri_for_tag`
    FOREIGN KEY (`snsr_uri`)
    REFERENCES `csn_test`.`csn_snsr_meta` (`snsr_uri`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `csn_test`.`csn_snsr_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `snsr_uri` VARCHAR(255) NOT NULL,
  `timestamp` VARCHAR(14) NOT NULL,
  `val` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  INDEX `snsr_uri_idx` (`snsr_uri` ASC),
  CONSTRAINT `snsr_uri`
    FOREIGN KEY (`snsr_uri`)
    REFERENCES `csn_test`.`csn_snsr_meta` (`snsr_uri`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;