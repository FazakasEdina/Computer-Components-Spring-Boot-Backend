DROP DATABASE IF EXISTS `idde`;
CREATE DATABASE IF NOT EXISTS `idde`;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'feim1911';

CREATE TABLE `idde`.`hardwarecomponent` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(30) NULL,
  `Type` VARCHAR(30) NULL,
  `Date` DATE NULL,
  `Price` REAL NULL,
  `InStock` INT NULL,
  PRIMARY KEY (`Id`)
  );
  
  CREATE TABLE `idde`.`review` ( 
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `Description` VARCHAR(255) NULL,
  `Stars` REAL NULL,
  `ComponentId` BIGINT NOT NULL,
    PRIMARY KEY (`Id`),
    CONSTRAINT `FK_Review_Component`
    FOREIGN KEY (`ComponentId`)
    REFERENCES `idde`.`hardwarecomponent` (`Id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
  
INSERT INTO idde.hardwarecomponent VALUES (default, 'GPU', 'Asus',  '2020-12-10', 1234.99, 10);
INSERT INTO idde.hardwarecomponent VALUES (default, 'CPU', 'Intel',  '2018-12-10', 900.99, 5);
INSERT INTO idde.review VALUES (default, "Works well", 5, 1);
INSERT INTO idde.review VALUES (default, "Good", 3, 2);

SELECT Id, Name, Type, Date, Price, InStock FROM idde.hardwarecomponent;
SELECT Id, Description, Stars, ComponentId FROM idde.review;

-- DELETE FROM idde.hardwarecomponent WHERE Id = 1
-- DELETE FROM idde.review WHERE Id = 1

-- UPDATE idde.hardwarecomponent
-- SET Name = "uj", Type="uj"
-- WHERE Id = 1;

-- UPDATE idde.review
-- SET Description="Good", ComponentId=1, Stars = 4
-- WHERE Id = 2

-- SELECT Id, Name, Type, Date, Price, InStock FROM idde.hardwarecomponent WHERE Name = "GPU";
-- SELECT Id, Description, Stars, ComponentId FROM idde.review WHERE Stars = 3;
