package com.codemonkeys.server.authorization

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import java.util.*

class AuthorizationDynamoDAO : BaseDynamoDAO(), IAuthorizationDAO {
    override fun deleteAuthToken(authToken: String): Boolean {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            table.deleteItem("PK", "TOKEN-$authToken", "SK", "TOKEN")
        }
        return true
    }

    override fun insertAuthToken(authToken: String, userID: String?, groupID: String?): Boolean {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            val item = Item()
                .withPrimaryKey("PK", "TOKEN-$authToken")
                .withString("SK", "TOKEN")
                .withString("authToken", authToken)
            userID?.let {
                item.withString("userID", it)
            }
            groupID?.let {
                item.withString("groupID", it)
            }
            table.putItem(
                item
            )
        }
        return true
    }

    private fun getAuthTokenItem(db: DynamoDB, authToken: String): Item? {
        val table = db.getTable(TABLE_NAME)
        val spec = QuerySpec()
            .withKeyConditionExpression("PK = :pk and SK = :sk")
            .withValueMap(
                ValueMap()
                    .withString(":pk", "TOKEN-$authToken")
                    .withString(":sk", "TOKEN")
            )

        val queryResult = table.query(spec)
        val iterator = queryResult.iterator()
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            null
        }
    }

    override fun getUserIDFromAuthToken(authToken: String): String? {
        this.useDynamoConnection {
            val item = getAuthTokenItem(it, authToken)
            item?.let {
                return if (it.hasAttribute("userID")) {
                    it.getString("userID")
                } else {
                    null
                }
            }
        }
        return null
    }

    override fun getGroupIDFromAuthToken(authToken: String): String? {
        this.useDynamoConnection {
            val item = getAuthTokenItem(it, authToken)
            item?.let {
                return if (it.hasAttribute("groupID")) {
                    it.getString("groupID")
                } else {
                    null
                }
            }
        }
        return null
    }
}