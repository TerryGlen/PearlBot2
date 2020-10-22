package service

import com.jessecorbett.diskord.api.model.User
import java.time.LocalDateTime
import java.time.ZoneOffset

object PointsService{

    fun givePoints(donner: User, recipient: User, guildId: Long, points: Int) : Boolean {

        val donnerDto = UserService.getUser(donner, guildId)
        val recipientDto = UserService.getUser(recipient, guildId)

        refreshPoints(donnerDto)
        if (donnerDto.givePoints < points) return false

        recipientDto.pearlPoints = points
        donnerDto.givePoints -= points

        UserService.updateUser(recipientDto)
        UserService.updateUser(donnerDto)


        return true

    }

    private fun refreshPoints(donnerDto: UserDto) {
        val offset = ZoneOffset.UTC
        val currentTime = LocalDateTime.now().toEpochSecond(offset)
        val lastRefreshed = donnerDto.lastGiven.toEpochSecond(offset)

        if (currentTime- lastRefreshed >= 86400) {
            donnerDto.lastGiven = LocalDateTime.now()
            donnerDto.givePoints = 10
        }
    }
}