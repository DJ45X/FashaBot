package services.dj45x.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import services.dj45x.utils.Logging;

import java.util.concurrent.TimeUnit;

@Component
public class CommandRegistration extends ListenerAdapter {
    private final String guildId;

    public CommandRegistration(
            @Value("${discord.guildId}") String GUILDID
    ) {
        this.guildId = GUILDID;
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);

        Guild guild = event.getGuild();
        if(guild.getId().equals(guildId)){
            Logging.info("Registering commands...");
        } else {
            throw new IllegalArgumentException("Failed to update Commands! Guild ID does not match.");
        }

        guild.updateCommands().addCommands(
                Commands.slash("admin", "Admin only commands")
                        .addSubcommands(
                                new SubcommandData("help", "Display list of admin commands")
                        )
        ).queueAfter(5, TimeUnit.SECONDS, success -> Logging.info("Commands registered!"));
    }
}
