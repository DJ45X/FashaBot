package services.dj45x;

import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashaBot {
    public static void main(String[] args) {
        SpringApplication.run(FashaBot.class, args);
    }
    private final JDA jda;
    @Autowired
    public FashaBot(JDA jda) {
        this.jda = jda;
    }
}