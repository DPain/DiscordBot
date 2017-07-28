package com.dpain.DiscordBot.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.dpain.DiscordBot.enums.Group;
import com.dpain.DiscordBot.plugin.anime.AnimeTorrentFinder;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ProfanityGuardPlugin extends Plugin {
	private boolean activated = false;
	private HashMap<String, File> emoteMap;
	
	/**
	 * @TODO In future, implement strike out system and save into a file
	 */
	
	public ProfanityGuardPlugin() {
		super("ProfanityGuardPlugin", Group.MODERATOR);
		
		super.helpString = "**Profanity Guard Plugin Usage:** \n" +
				"-profguard *\"enable/disable\"* : Enables or disables Profanity Guard\n" +
				"-profguard *\"offenders\"* : PMs the list of offenders to the one who issued the command\n";
		EssentialsPlugin.appendHelpString(super.helpString);
	}

	@Override
	public void handleEvent(Event event) {
		if(event instanceof GuildMessageReceivedEvent) {
			try {
				GuildMessageReceivedEvent castedEvent = (GuildMessageReceivedEvent) event;
				String message = castedEvent.getMessage().getContent();
		        
				if((castedEvent.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) || canAccessPlugin(castedEvent.getMember())) {
					
					if(message.startsWith("-")) {
		                
		                if(message.equals("-profguard")) {
		                	//Incorrect usage of profguard
		                	castedEvent.getChannel().sendMessage("*Try -help for correct syntax!*");
		                	
		                } else if(message.startsWith("-profguard ")) {
		                	String param = message.substring(11);
		                	if(param.toLowerCase().equals("enable")) {
		                		activated = true;
		                		castedEvent.getChannel().sendMessage("*Profanity Guard is ENABLED!*");
		                	} else if(param.equals("disable")) {
		                		activated = false;
		                		castedEvent.getChannel().sendMessage("*Profanity Guard is DISABLED!*");
		                	} else if(param.equals("offenders")) {
		                		castedEvent.getAuthor().openPrivateChannel().complete().sendMessage("WIP");
		                	} else {
		                		//Incorrect usage of profguard
			                	castedEvent.getChannel().sendMessage("*Try -help for correct syntax!*");
		                	}
		                }
					}
					/*
					if() {
						
					}
					*/
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
