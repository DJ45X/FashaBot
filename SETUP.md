## Setup Prerequisites

Navigate to a directory where you will store project files. For example, `C:\docker\fashabot`. Follow the steps below to get setup

1. Make a `docker-compose.yaml` file that contains the following:
    ```yaml
    services:
      fashabot:
         image: ghcr.io/dj45x/fashabot:latest
         env_file:
           - .env
   ```
   **NOTE:** Click the copy button in the top right so as not to disturb thf formatting. Indentations are crucial to the correct functionality of YAML. 

2. Create a `.env` file in the same directory as `docker-compose.yaml` and insert the following:
    ```.env
   TOKEN=insert_your_bot_token_here
   
   LAIR_ID=lair_discord_id
   DUNGEON_ID=dungeon_discord_id
   
   IMAGE_CHANNEL=insert_channel_name_here
    ```
   **Note:** To get the ``GUILD_ID``, go into discord and right click the server icon, then click "Copy ID". You may need to enable developer settings to perform this. Instructions found [here](https://www.partitionwizard.com/partitionmagic/discord-developer-mode.html)


## Now it's time run start your bot
1. Open Powershell (no "run as admin" required)
2. Run the command `docker compose up -d`. This will automatically pull the image from the repo and start the application in docker

That's it! You will see your bot come online within a few minutes. If there are any errors, you can open Docker Desktop, go to "Containers" tab and click on the container name. You'll see the logs printing out. You can copy these logs and send them to DJ for diagnosing. Alternatively, you can create an "Issue" in the github repo and past your logs there. 

# Updating

There may be times I need to update the bot. When informed to do so, follow these steps to update your bot:
1. Open a PowerShell terminal (not ran as admin)
2. Navigate to your project location
3. Run `docker compose pull`
4. Run `docker compose up -d`

This will update and restart the bot for you. 