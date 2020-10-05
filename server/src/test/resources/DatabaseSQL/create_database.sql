DROP TABLE IF EXISTS Zone;
DROP TABLE IF EXISTS Status;
DROP TABLE IF EXISTS AuthToken;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS ClockGroup;


/* Create ClockGroup Table
*/
CREATE TABLE ClockGroup (
	groupID CHAR(36) NOT NULL PRIMARY KEY,
	groupName VARCHAR(50) NULL,
	groupPassword VARCHAR(20) NULL
    );



/* Create User Table
TODO: Obviously, you still need to add a bunch to this, I just put in the basic attributes I needed for authorization.
*/
CREATE TABLE User (
	userID CHAR(36) NOT NULL PRIMARY KEY,
    groupID CHAR(36),
    FOREIGN KEY (groupID) REFERENCES ClockGroup(groupID) ON DELETE CASCADE
    );



/* Create AuthToken Table
*/
CREATE TABLE AuthToken (
	authToken CHAR(36) NOT NULL PRIMARY KEY,
    userID CHAR(36) NULL,
    groupID CHAR(36) NULL,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE,
    FOREIGN KEY (groupID) references ClockGroup(groupID) ON DELETE CASCADE
);

    
    
/* Create Status Table
*/
CREATE TABLE Status (
	statusID CHAR(36) NOT NULL PRIMARY KEY,
    clockHandAngle REAL,
    statusName VARCHAR(100),
    groupID VARCHAR(36),
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