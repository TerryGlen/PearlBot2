package service

import com.jessecorbett.diskord.api.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.ZoneOffset

object UserService {



    fun mapToUserDto(row: ResultRow) = UserDto(
        id = row[Users.id],
        name = row[Users.name],
        guildId = row[Users.guildId],
        pearlPoints = row[Users.pearlPoints],
        givePoints = row[Users.givePoints],
        lastGiven = row[Users.lastGiven]
    )

    fun updateUser(dto: UserDto) {
        transaction {
            Users.update({ Users.id eq dto.id and (Users.guildId eq dto.guildId) }) {
                it[id] = dto.id
                it[name] = dto.name
                it[Users.guildId] = dto.guildId
                it[pearlPoints] = dto.pearlPoints
                it[givePoints] = dto.givePoints
                it[lastGiven] = dto.lastGiven
            }

        }
    }

    fun getUser(user: User, guildId: Long) : UserDto {
        //Creates user if does not exists
        addUser(user, guildId)
        return transaction {
            Users.select { (Users.id eq user.id.toLong()) and (Users.guildId eq guildId) }
                .map {
                    mapToUserDto(it)
                }
                .single()
        }
    }

    fun addUser(user: User, guildId: Long) {
        transaction{
            //If already in table ignores
            //This might be bad pratice
            Users.insertIgnore {
                it[id] = user.id.toLong()
                it[name] = user.username
                it[Users.guildId] = guildId
            }
        }


    }


}