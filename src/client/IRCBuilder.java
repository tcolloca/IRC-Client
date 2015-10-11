package client;

/**
 * Builder of IRC interfaces implementations.
 * 
 * @author Tomas
 */
public class IRCBuilder {

	/**
	 * Builds an IRCConfiguration. The default values are the following:
	 * <ul>
	 * <li>Multithread: false</li>
	 * <li>ConnectionHandler: Will use Java nio with blocking set as false</li>
	 * <li>Nickname: IRCClient</li>
	 * <li>Username: IRCClient</li>
	 * <li>Realname: IrcClient 1.0 developed by Tomas Colloca :)</li>
	 * <li>Charset: UTF-</li>
	 * </ul>
	 * 
	 * @param server
	 *            Server to which the client will try to connect.
	 * @return the IRCConfiguration built.
	 * @throws IllegalArgumentException
	 *             If server is null.
	 */
	public static IRCConfiguration buildConfiguration(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		return new IRCConfigurationImpl(server);
	}

	/**
	 * Builds an IRCClient.
	 * 
	 * @param configuration
	 *            IRCConfiguration to be used.
	 * @return the IRCClient built.
	 * @throws IllegalArgumentException
	 *             If configuration is null.
	 */
	public static IRCClient buildClient(IRCConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		return new IRCClientImpl(configuration);
	}
}
