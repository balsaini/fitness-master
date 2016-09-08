CREATE TABLE `fitness`.`runs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `version` INT NOT NULL DEFAULT 0,
  `start_time` TIMESTAMP NOT NULL DEFAULT NOW(),
  `stop_time` TIMESTAMP NULL DEFAULT NULL,
  `device_id` INT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT NOW(),
  `modified` TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_devices_v7`
  FOREIGN KEY (`device_id`)
  REFERENCES `fitness`.`devices` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
