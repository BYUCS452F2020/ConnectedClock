/* Group Table Test Data */
INSERT INTO ClockGroup (groupID) VALUES ("98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO ClockGroup (groupID) VALUES ("2bc8f348-fce4-4df6-9795-deff8e721c7a");



/* User Table Test Data */
INSERT INTO User (userID, groupID) VALUES ("3ea0f56f-7864-4d49-a5ed-5f741a7969c8", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO User (userID, groupID) VALUES ("4c43ddce-64db-4c89-9797-909dcbd0a425", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO User (userID, groupID) VALUES ("f3b55f05-c33a-4ab2-9aa7-29ad8f6efa3d", "2bc8f348-fce4-4df6-9795-deff8e721c7a");



/* AuthToken Table Test Data */
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("e00f1c88-1d5b-4d32-be07-1018f39a26b2", "3ea0f56f-7864-4d49-a5ed-5f741a7969c8", null);
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("7af562ed-46bd-429b-8bcd-2d85e76d9a10", null, "2bc8f348-fce4-4df6-9795-deff8e721c7a");
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("cc95e238-1a1d-4238-b19b-0ef1dce406eb", "3ea0f56f-7864-4d49-a5ed-5f741a7969c8", null);
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("663feea8-e1e0-4cf3-89ff-ed4905ec4c7c", null, "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("dce470b2-0e48-4bf1-9274-0af847dab64b", "f3b55f05-c33a-4ab2-9aa7-29ad8f6efa3d", null);
INSERT INTO AuthToken (authToken, userID, groupID) VALUES ("ddecd217-a782-4674-8e60-9c2c015bfb7b", "4c43ddce-64db-4c89-9797-909dcbd0a425", null);


/* Status Table Test Data */  
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9", 60.0, "Home", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("d32b0786-c6f1-4c70-a31f-a9efed1ef1f6", 75.0, "Away", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("ec488303-1152-4d8d-af55-db9b323be17e", 90.5, "School", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("04e3b648-c3dd-41da-a430-1dacde995b7d", 23.2, "Work", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("12a20cb0-9e82-4d88-98f2-faaa9ff8c675", 11.0, "Shopping", "98729fce-0809-43fe-b953-f48b14b07616");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("92a3db1e-99b6-45d1-92e6-2c47720e620e", 42.9, "Home", "2bc8f348-fce4-4df6-9795-deff8e721c7a");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("32f85320-92e0-4382-a5ae-d71b562422c5", 12.7, "Mortal Peril", "2bc8f348-fce4-4df6-9795-deff8e721c7a");
INSERT INTO Status (statusID, clockHandAngle, statusName, groupID) VALUES ("ed9ceea8-b059-4e00-b35e-83be6e63c497", 101, "School", "2bc8f348-fce4-4df6-9795-deff8e721c7a");