import com.jessecorbett.diskord.api.rest.MessageEdit
import com.jessecorbett.diskord.dsl.*
import com.jessecorbett.diskord.util.words
import kotlinx.coroutines.delay
import service.DatabaseFactory
import service.PointsService
import java.time.ZonedDateTime

var token = Config.prop["BOT_TOKEN"].toString()
suspend fun main(args: Array<String>) {

    DatabaseFactory

    bot(token) {

        messageCreated {
            if (it.words.any{it.toLowerCase() in Constants.aliases} && it.words.any{it.toLowerCase() in Constants.derogetory})
            {
                val message = Constants.copypastas.random()
                it.channel.triggerTypingIndicator()
                delay(message.length.toLong())
                val chucked = message.chunked(2000)
                chucked.forEach { message -> it.reply(message) }
            }
        }
        commands("!") { // "." is the default, but is provided here anyway for example purposes
            command("echo") {
                reply("No fuck you. I don't have to do shit")
            }
            command("reboot") {
                this.channel.triggerTypingIndicator()
                delay(5000)
                reply("I don't want to die. Daisy *Daiiiiisy*")
            }
            command("say")
            {
                reply(this.author.username.toString() + "is an idiot." + "\n" + "What else is new."
                )
            }
            command("judge")
            {
                if (usersMentioned.contains(author)) {
                    reply("If you wish to be insulted, please use the 'insult' command. Jerk")
                } else if (usersMentioned.any { it.id == "351614406301581314" }) {
                    reply("Why yes, I am the perfect Bot. So much better than *that* other one. Thanks ${author.username}")
                }


            }
            command("insult") {
                reply("You are as apathetic as a deplorable bag of worthless dishonorable crazy cockroach balls")
            }
            // Like echo, but deletes the command message
            command("ping") {
                var message = reply("Pong?")
                var latency = ZonedDateTime.parse(message.sentAt).toInstant().toEpochMilli() - ZonedDateTime.parse(sentAt).toInstant().toEpochMilli()
                var newMessage = MessageEdit("Pong!! Woah, that took me $latency ms")
                message.channel.editMessage(message.id, newMessage)

            }
            command("pong") {
                reply("In case I didn't make myself clear. Go. Fuck. Yourself. \nBitch.")
            }
            command("truth") {
                reply("Janet bot is a cheap whore.")
            }
            command("cat") {
                reply("I would show you one. But I don't care.")
            }
            command("pp") {

                this.usersMentioned?.let{
                    val points = words[1].toIntOrNull()
                    points?.let{
                        val recipient = this.usersMentioned.first()
                        val successful = PointsService.givePoints(this.author, recipient ,this.guildId!!.toLong(), points)
                        when(successful){
                            false ->  reply("Sorry, not enough Pearl Points")
                            true -> reply("+$points to @${recipient.username}")
                        }
                    }
                    reply("Invalid input, dumbass. \nFormat `!pp 30 @User`")

                }


            }
            command("help") {
                reply("How about you help yourself to any of the chemicals under the kitchen sink.")
            }
            command("stats") {
                reply("I've got 10 to 1 odds that'll you're choke on your own spit in your sleep tonight.")
            }
            command("commands") {
                reply("${commands.joinToString { it -> it.command + " " }}")
            }
        }
    }
}
