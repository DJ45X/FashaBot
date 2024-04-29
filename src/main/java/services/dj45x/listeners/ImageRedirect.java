package services.dj45x.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import services.dj45x.utils.JDAUtils;
import services.dj45x.utils.Logging;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ImageRedirect extends ListenerAdapter {
    private final String lairId;
    private final String dungeonId;
    private final String imageChannelName;
    @Autowired
    public ImageRedirect(
            @Value("${discord.guildId.lair}") String LAIRID,
            @Value("${discord.guildId.dungeon}") String DUNGEONID,
            @Value("${discord.channels.imageChannel}") String IMAGECHANNEL
    ) {
        this.lairId = LAIRID;
        this.dungeonId = DUNGEONID;
        this.imageChannelName = IMAGECHANNEL;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        Guild lairGuild = event.getJDA().getGuildById(lairId);
        Guild dungeonGuild = event.getJDA().getGuildById(dungeonId);

        if(lairGuild == null || dungeonGuild == null){
            Logging.warn("Guild not found!");
            return;
        }

        TextChannel dungeonChannel = JDAUtils.getTextChannelByName(dungeonGuild, imageChannelName);
        TextChannel lairChannel = JDAUtils.getTextChannelByName(lairGuild, imageChannelName);

        if(dungeonChannel == null || lairChannel == null){
            Logging.warn("Image channel not found!");
            event.getAuthor().openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessage("Image channel not found! Please contact an admin."))
                    .queue();
            return;
        }

        User bot = event.getAuthor();
        if(bot.isBot()) return;

        if(!event.isFromType(ChannelType.PRIVATE)){
            Logging.warn("Received message in non-private channel!");
        } else {
            User author = event.getMessage().getAuthor();
            List<Message.Attachment> attachmentsList = event.getMessage().getAttachments();
            if(attachmentsList.isEmpty()){
                event.getAuthor().openPrivateChannel()
                        .flatMap(privateChannel -> privateChannel.sendMessage("I did not receive any images in your message. Please attach an image and try again!"))
                        .delay(10, TimeUnit.SECONDS)
                        .flatMap(Message::delete)
                        .queue();

                return;
            }

            if(attachmentsList.size() > 1){
                String joinedUrls = attachmentsList.stream()
                        .map(Message.Attachment::getUrl)
                        .collect(Collectors.joining("\n"));
                lairChannel.sendMessage("## Images sent from:\n" + author.getAsMention() + "\n\n" + joinedUrls).queue();
                dungeonChannel.sendMessage("## Images sent from:\n" + author.getAsMention() + "\n\n" + joinedUrls).queue();
            } else {
                for(Message.Attachment attachment : attachmentsList){
                    String url = attachment.getUrl();
                    lairChannel.sendMessage("## Images sent from:\n" + author.getAsMention() + "\n\n" + url).queue();
                    dungeonChannel.sendMessage("## Images sent from:\n" + author.getAsMention() + "\n\n" + url).queue();
                }
            }
        }
    }
}
