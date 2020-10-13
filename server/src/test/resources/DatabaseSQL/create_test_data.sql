/* Group Table Test Data */
INSERT INTO ClockGroup (groupID) VALUES ("98729fce-0809-43fe-b953-f48b14b07616"); /* Group 1 */
INSERT INTO ClockGroup (groupID) VALUES ("2bc8f348-fce4-4df6-9795-deff8e721c7a"); /* Group 2 */



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



/* Zone Table Test Data */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("1b92628d-a46e-400f-bfd8-c40af4b9eaa9", 40.252036, -111.649204, 524.52, "ec488303-1152-4d8d-af55-db9b323be17e"); /* BYU Campus */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("e3d835fa-1d1f-4c23-8f70-85dc6d39b68c", 40.272636, -111.710176, 172.27, "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"); /* Orem Walmart */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("991f5783-0e51-4cd1-9ee3-2f2d72909bfb", 40.252979, -111.670495, 93.83, "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"); /* Deseret Industries */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("0174a780-c11a-460f-bbb4-66a135eddd97", 40.238631, -111.661309, 90.15, "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"); /* Smiths Grocery */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("b821c605-182d-472c-a0df-cf0d838d77b9", 40.234978, -111.668599, 46.82, "04e3b648-c3dd-41da-a430-1dacde995b7d"); /* Sherwin-Williams Paint */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("021706f8-8a4f-4408-9180-ef7e4457bf96", 40.247222, -111.665663, 317.29, "32f85320-92e0-4382-a5ae-d71b562422c5"); /* Hospital */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("dffb8b27-ee34-41d9-8d54-37a08c7c300f", 40.242857, -111.668508, 102.46, "7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9"); /* Apartment */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("a7a71489-0506-4589-965f-ad8f32a7fc12", 40.261547, -111.660352, 185.72, "92a3db1e-99b6-45d1-92e6-2c47720e620e"); /* Wyview Apartments */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("679a3f92-131b-4fdb-be27-590cb5210bac", 40.234351, -111.647619, 118.45, "ed9ceea8-b059-4e00-b35e-83be6e63c497"); /* Elementary School */
INSERT INTO Zone (zoneID, lat, lng, radius, statusID) VALUES ("d00edf46-a5af-4dbc-a63d-ed6d6c2b2914", 40.275056, -111.680827, 224.96, "12a20cb0-9e82-4d88-98f2-faaa9ff8c675"); /* University Place Mall */
