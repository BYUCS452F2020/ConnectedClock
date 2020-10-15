DROP TABLE IF EXISTS AuthToken;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Zone;
DROP TABLE IF EXISTS Status;
DROP TABLE IF EXISTS ClockGroup;



/* Create ClockGroup Table
*/
CREATE TABLE ClockGroup (
	groupID CHAR(36) NOT NULL PRIMARY KEY,
	groupName VARCHAR(50) NULL,
	groupPassword VARCHAR(30) NULL
    );


    
    
/* Create Status Table
*/
CREATE TABLE Status (
	statusID CHAR(36) NOT NULL PRIMARY KEY,
    clockHandAngle REAL,
    statusName VARCHAR(100),
    groupID CHAR(36),
    FOREIGN KEY (groupID) REFERENCES ClockGroup(groupID) ON DELETE CASCADE
    );
    
    
    
/* Create Zone Table
*/
CREATE TABLE Zone (
    zoneID CHAR(36) NOT NULL PRIMARY KEY,
    lat REAL,
    lng REAL,
    radius REAL,
    statusID CHAR(36),
    FOREIGN KEY (statusID) REFERENCES Status(statusID) ON DELETE CASCADE
);



/* Create User Table
*/
CREATE TABLE User (
	userID CHAR(36) NOT NULL PRIMARY KEY,
    groupID CHAR(36) NULL,
    userName VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL,
    clockHandIndex INT NOT NULL,
    currentZoneID CHAR(36) NULL,
    FOREIGN KEY (groupID) REFERENCES ClockGroup(groupID) ON DELETE CASCADE,
    FOREIGN KEY (currentZoneID) REFERENCES Zone(zoneID) ON DELETE SET NULL
    );



/* Create AuthToken Table
*/
CREATE TABLE AuthToken (
	authToken CHAR(36) NOT NULL PRIMARY KEY,
    userID CHAR(36) NULL,
    groupID CHAR(36) NULL,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (groupID) REFERENCES ClockGroup(groupID) ON DELETE CASCADE
);