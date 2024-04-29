package services.dj45x.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import services.dj45x.utils.Logging;

import java.util.concurrent.TimeUnit;

@Component
public class CommandRegistration extends ListenerAdapter {
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);

        JDA jda = event.getJDA();

        jda.updateCommands().addCommands(
                Commands.slash("help", "Get information on how to use the bot")
        ).queueAfter(5, TimeUnit.SECONDS, success -> Logging.info("Commands registered!"));
    }
}
