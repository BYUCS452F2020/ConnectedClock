package com.codemonkeys.server.core.dao

import com.codemonkeys.shared.core.dao.ColumnAnnotation
import java.lang.reflect.Field

open class BaseDAO {
    protected fun <T> getColumnNamesAndFields(clazz: Class<T>): List<Pair<String, Field>> {
        val fields = clazz.declaredFields
        return fields.mapNotNull { field ->
            field.getAnnotation(ColumnAnnotation::class.java)?.let { annotation ->
                annotation.columnName to field
            }
        }
    }
}

