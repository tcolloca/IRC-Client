package model;

import java.util.ArrayList;
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

	@Override
	public void initializeNormalChannels(List<IRCChannel> channels) {
		this.normalChannels = channels;
	}

	@Override
	public void initializeHalfOpChannels(List<IRCChannel> channels) {
		this.halfOpChannels = channels;
	}

	@Override
	public void initializeOpChannels(List<IRCChannel> channels) {
		this.opChannels = channels;
	}

	@Override
	public void initializeSuperOpChannels(List<IRCChannel> channels) {
		this.superOpChannels = channels;
	}

	@Override
	public void initializeOwnerChannels(List<IRCChannel> channels) {
		this.ownerChannels = channels;
	}

	@Override
	public void initializeVoicedChannels(List<IRCChannel> channels) {
		this.voicedChannels = channels;
	}

	@Override
	public void initialize() {
		initializeNormalChannels(new ArrayList<IRCChannel>());
		initializeHalfOpChannels(new ArrayList<IRCChannel>());
		initializeOpChannels(new ArrayList<IRCChannel>());
		initializeSuperOpChannels(new ArrayList<IRCChannel>());
		initializeOwnerChannels(new ArrayList<IRCChannel>());
		initializeVoicedChannels(new ArrayList<IRCChannel>());
	}
}
