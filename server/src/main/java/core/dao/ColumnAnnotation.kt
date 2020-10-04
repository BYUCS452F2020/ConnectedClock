package core.dao


// https://www.baeldung.com/kotlin-annotations
// https://stackoverflow.com/a/46359673/6634972 Automatically mapping from ResultSet to Java Object
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ColumnAnnotation(val columnName: String)