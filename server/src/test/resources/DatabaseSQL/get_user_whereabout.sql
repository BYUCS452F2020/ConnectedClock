SELECT u.userID, u.currentZoneID, s.statusID, s.statusName, s.clockHandAngle
	FROM User u
    LEFT JOIN Zone z ON z.zoneID=u.currentZoneID
    LEFT JOIN Status s ON z.statusID=s.statusID
    WHERE userID = '90ee6ca0-344d-45da-afb7-c8c6b4fe5393';