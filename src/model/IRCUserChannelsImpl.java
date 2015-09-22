package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IRCUserChannelsImpl implements IRCUserChannels {

	private List<IRCChannel> normalChannels;
	private List<IRCChannel> halfOpChannels;
	private List<IRCChannel> opChannels;
	private List<IRCChannel> superOpChannels;
	private List<IRCChannel> ownerChannels;
	private List<IRCChannel> voicedChannels;

	IRCUserChannelsImpl() {
	}

	@Override
	public Set<IRCChannel> getChannels() {
		Set<IRCChannel> channels = new HashSet<IRCChannel>();
		channels.addAll(normalChannels);
		channels.addAll(halfOpChannels);
		channels.addAll(opChannels);
		channels.addAll(superOpChannels);
		channels.addAll(ownerChannels);
		channels.addAll(voicedChannels);
		return channels;
	}

	@Override
	public List<IRCChannel> getNormalChannels() {
		return normalChannels;
	}

	@Override
	public List<IRCChannel> getHalfOpChannels() {
		return halfOpChannels;
	}

	@Override
	public List<IRCChannel> getOpChannels() {
		return opChannels;
	}

	@Override
	public List<IRCChannel> getSuperOpChannels() {
		return superOpChannels;
	}

	@Override
	public List<IRCChannel> getOwnerChannels() {
		return ownerChannels;
	}

	@Override
	public List<IRCChannel> getVoicedChannels() {
		return voicedChannels;
	}

}
