SELECT u.userID, u.currentZoneID, s.statusID, s.statusName, s.clockHandAngle
	FROM User u
    LEFT JOIN Zone z ON z.zoneID=u.currentZoneID
    LEFT JOIN Status s ON z.statusID=s.statusID
    WHERE userID = '3ea0f56f-7864-4d49-a5ed-5f741a7969c8';