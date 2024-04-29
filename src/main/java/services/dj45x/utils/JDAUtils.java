package services.dj45x.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

public class JDAUtils {
    public static TextChannel getTextChannelByName(Guild guild, String channelName){
        List<TextChannel> channelList = guild.getTextChannelsByName(channelName, true);
        if(!channelList.isEmpty()){
            return channelList.get(0);
        }
        return null;
    }

    public static Role getRoleByName(Guild guild, String roleName){
        List<Role> roles = guild.getRolesByName(roleName, true);
        if(!roles.isEmpty()){
            return roles.get(0);
        }
        return null;
    }

    public static Boolean memberHasRole(Member member, Role roleName){
        return member.getRoles().contains(roleName);
    }
}
