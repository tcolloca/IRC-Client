package model;

import java.util.List;

import util.IRCValues;
import client.IRCClient;
import event.IRCEventAdapter;

public class IRCChannelImpl extends IRCEventAdapter implements IRCChannel,
		IRCValues {

	private IRCClient client;

	private String name;
	private String key;
	private IRCTopic topic;
	private long creationTime;

	private IRCChannelUsers users;
	private IRCChannelMasks masks;
	private IRCChannelModes modes;

	private int limit;
	private IRCFloodLimit floodLimit;

	/**
	 * @param client
	 *            IRCClient that has a reference to this channel.
	 * @param name
	 *            Name of the channel.
	 * @throws IllegalArgumentException
	 *             If client or name are null.
	 */
	public IRCChannelImpl(IRCClient client, String name) {
		this(client, name, null);
	}

	/**
	 * @param client
	 *            IRCClient that has a reference to this channel.
	 * @param name
	 *            Name of the channel.
	 * @param key
	 *            Key of the channel.
	 * @throws IllegalArgumentException
	 *             If client or name are null.
	 */
	public IRCChannelImpl(IRCClient client, String name, String key) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.client = client;
		this.name = name;
		this.key = key;
		this.users = new IRCChannelUsersImpl(this);
		this.modes = new IRCChannelModesImpl(this);
		this.masks = new IRCChannelMasksImpl(this);
		client.addListener(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public IRCTopic getTopic() {
		return topic;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public IRCFloodLimit getFloodLimit() {
		return floodLimit;
	}

	@Override
	public IRCChannelUsers getChannelUsers() {
		return users;
	}

	@Override
	public IRCChannelModes getChannelModes() {
		return modes;
	}

	@Override
	public IRCChannelMasks getChannelMasks() {
		return masks;
	}

	@Override
	public boolean hasKey() {
		return modes.getChannelModes().contains(IRCChannelMode.KEY);
	}

	@Override
	public boolean hasLimit() {
		return modes.getChannelModes().contains(IRCChannelMode.USERS_LIMIT);
	}

	@Override
	public void setKey(String key) {
		modes.setChannelMode(IRCChannelMode.KEY, key);
	}

	@Override
	public void setTopic(String message) {
		// TODO : Set topic
	}

	@Override
	public void setLimit(int limit) {
		modes.setChannelMode(IRCChannelMode.USERS_LIMIT, String.valueOf(limit));
	}

	@Override
	public void setFloodLimit(IRCFloodLimit floodLimit) {
		modes.setChannelMode(IRCChannelMode.FLOOD_LIMIT, floodLimit.getString());
	}

	@Override
	public void removeKey() {
		modes.unsetChannelMode(IRCChannelMode.KEY, key);
	}

	@Override
	public void removeTopic() {
		// TODO : Remove topic

	}

	@Override
	public void removeLimit() {
		modes.unsetChannelMode(IRCChannelMode.USERS_LIMIT);
	}

	@Override
	public void removeFloodLimit() {
		modes.unsetChannelMode(IRCChannelMode.FLOOD_LIMIT);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IRCChannelImpl other = (IRCChannelImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public IRCClient getClient() {
		return client;
	}

	@Override
	public void onJoin(IRCUser user, IRCChannel channel) {
		if (channel.equals(this)) {
			users.addNormalUser(user);
		}
	}

	@Override
	public void onMode(IRCChannel channel,
			List<IRCModeAction> channelModeActions) {
		if (channel.equals(this)) {
			// TODO : Set all the new modes.
		}
	}
}
