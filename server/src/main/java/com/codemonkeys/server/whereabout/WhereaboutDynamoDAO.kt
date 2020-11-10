package com.codemonkeys.server.whereabout

import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.whereabout.Whereabout

class WhereaboutDynamoDAO : BaseDynamoDAO(), IWhereaboutDAO {
    override fun getWhereabouts(groupID: String): List<Whereabout> {
        TODO("Not yet implemented")
    }

    override fun updateUserWhereabout(userID: String, currentZoneID: String?) {
        TODO("Not yet implemented")
    }
}