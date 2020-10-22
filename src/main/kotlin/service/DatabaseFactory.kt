package service
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime


object DatabaseFactory {
    init{
        Database.connect("jdbc:mysql://localhost:3306/test", driver = "com.mysql.jdbc.Driver",
            user = "root", password = Config.prop.getProperty("DB_PASSWORD"))
        transaction{
            SchemaUtils.create(Users)
        }
    }


    suspend fun <T> dbQuery(
        block: suspend () -> T): T =
        newSuspendedTransaction { block() }
}


object Users : Table() {
    val id = long("id")// Column<String>
    val name = varchar("name", length = 50) // Column<String>
    val guildId = (long("guild_id") )
    val pearlPoints = (integer("pearl_points")).default(0)
    val givePoints = (integer("give_points")).default(10)
    val lastGiven = (datetime("last_given")).clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(id, guildId, name = "PK_User_ID")// name is optional here

}

data class UserDto (
    val id : Long,
    val name: String,
    val guildId: Long,
    var pearlPoints: Int,
    var givePoints: Int,
    var lastGiven : LocalDateTime

)


