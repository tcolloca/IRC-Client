package model;

public class IRCChannelImpl implements IRCChannel {

	private String name;
	private String key;

	/**
	 * @param name
	 *            Name of the channel.
	 * @throws IllegalArgumentException
	 *             If name is null.
	 */
	public IRCChannelImpl(String name) {
		this(name, null);
	}

	/**
	 * @param name
	 *            Name of the channel.
	 * @param key
	 *            Key of the channel.
	 * @throws IllegalArgumentException
	 *             If name is null.
	 */
	public IRCChannelImpl(String name, String key) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.key = key;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getKey() {
		return key;
	}
}
