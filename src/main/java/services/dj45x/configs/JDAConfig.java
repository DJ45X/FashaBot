package services.dj45x.configs;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDAConfig {

    @Bean
    public JDA jda(
            @Value("${discord.token}") String token,
            ObjectProvider<ListenerAdapter> listenerAdapters
    ){
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);

        JDA jda = jdaBuilder
                .setMemberCachePolicy(MemberCachePolicy.DEFAULT)
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS
                ).build();

        /*jda.awaitReady();*/

        for(var adapter : listenerAdapters){
            jda.addEventListener(adapter);
        }

        jda.getPresence().setActivity(Activity.customStatus("DM me pics!"));

        return jda;
    }
}
