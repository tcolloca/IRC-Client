package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import event.IRCEventAdapter;

public class IRCSender extends IRCEventAdapter {

	private static final int BUFFER_SIZE = 512;

	// private SocketChannel channel;
	//
	// /**
	// * @param channel
	// * Channel where the appropriate responses are going to be sent.
	// * @throws IllegalArgumentException
	// * If channel is null.
	// */
	// public IRCSender() {
	// if (channel == null) {
	// throw new IllegalArgumentException();
	// }
	// this.channel = channel;
	// }

	// /**
	// * A normal user uses the OPER command to obtain operator privileges.
	// *
	// * @param name
	// * Name of the user.
	// * @param password
	// * Password of the user.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If any of the arguments is
	// null.
	// */
	// public void oper(String name, String password) throws IRCException {
	// if (name == null || password == null) {
	// throw new IllegalArgumentException();
	// }
	// String message = String.format(OPER_COMMAND, name, password);
	// send(message);
	// }
	//
	// // TODO : Extra params for channel's mode
	// /**
	// * Changes the sender's mode to the ones chosen, or the channel's mode
	// * depending on the first argument. Example: MODE Angel +i ; Command from
	// * Angel to make herself invisible. MODE #Finnish +o Kilroy ; Command to
	// * give 'chanop' privileges to Kilroy on channel #Finnish.
	// *
	// * @param nicknameOrChannel
	// * Nickname of the user, or name of the channel.
	// * @param add
	// * true if adding the modes, false if not.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If any of the arguments is
	// null.
	// */
	// public void mode(String nicknameOrChannel, boolean add, String modes)
	// throws IRCException {
	// if (nicknameOrChannel == null || modes == null) {
	// throw new IllegalArgumentException();
	// }
	// String message = String.format(MODE_COMMAND, nicknameOrChannel,
	// add ? "+" : "-", modes);
	// send(message);
	// }
	//
	// /**
	// * Used to register a new service. The <distribution> parameter is used to
	// * specify the visibility of a service. The service may only be known to
	// * servers which have a name matching the distribution. For a matching
	// * server to have knowledge of the service, the network path between that
	// * server and the server on which the service is connected MUST be
	// composed
	// * of servers which names all match the mask. The <type> parameter is
	// * currently reserved for future usage. (2000)
	// *
	// * @param serviceNickname
	// * @param distribution
	// * @param type
	// * @param info
	// * @throws IRCException
	// */
	// public void service(String serviceNickname, String distribution,
	// String type, String info) throws IRCException {
	// if (serviceNickname == null || distribution == null) {
	// throw new IllegalArgumentException();
	// }
	// String message = String.format(SERVICE_COMMAND, serviceNickname,
	// distribution, type != null ? type : "0", info != null ? info
	// : "*");
	// send(message);
	// }
	//
	// /**
	// * The session is terminated.
	// *
	// * @throws IRCException
	// */
	// public void quit() throws IRCException {
	// quit(null);
	// }
	//
	// /**
	// * The session is terminated with a quit message.
	// *
	// * @param quitMessage
	// * Message to be shown.
	// * @throws IRCException
	// */
	// public void quit(String quitMessage) throws IRCException {
	// String message = String.format(QUIT_COMMAND,
	// quitMessage != null ? quitMessage : "");
	// send(message);
	// }
	//
	// /**
	// * Joins the channels.
	// *
	// * @param channels
	// * List of channels to be joined.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channels is null.
	// */
	// public void join(List<String> channels) throws IRCException {
	// if (channels == null) {
	// throw new IllegalArgumentException();
	// }
	// for (String channel : channels) {
	// join(channel);
	// }
	// }
	//
	// /**
	// * Joins the channels with the given keys. A key should be null or an
	// empty
	// * String if the corresponding channel has no key.
	// *
	// * @param channels
	// * List of channels to be joined.
	// * @param keys
	// * List with the keys of the channels to be joined.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channels or keys are null,
	// or
	// * if their sizes don't match.
	// */
	// public void join(List<String> channels, List<String> keys)
	// throws IRCException {
	// if (channels == null || keys == null || channels.size() != keys.size()) {
	// throw new IllegalArgumentException();
	// }
	// for (int i = 0; i < channels.size(); i++) {
	// join(channels.get(i), keys.get(i));
	// }
	// }
	//
	// /**
	// * Joins the channel.
	// *
	// * @param channel
	// * Channel to be joined.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channel is null.
	// */
	// public void join(String channel) throws IRCException {
	// join(channel, null);
	// }
	//
	// /**
	// * Joins the channel with the given key. Key should be null if it has no
	// * key.
	// *
	// * @param channel
	// * Channel to be joined.
	// * @param key
	// * Key of the channel.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channel is null.
	// */
	// public void join(String channel, String key) throws IRCException {
	// if (channel == null) {
	// throw new IllegalArgumentException();
	// }
	// String message = String.format(JOIN_COMMAND, channel, key != null ? key
	// : "");
	// send(message);
	// }
	//
	// /**
	// * Leaves the channels.
	// *
	// * @param channels
	// * List of channels to be left.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channels is null.
	// */
	// public void part(List<String> channels) throws IRCException {
	// if (channels == null) {
	// throw new IllegalArgumentException();
	// }
	// for (String channel : channels) {
	// part(channel);
	// }
	// }
	//
	// /**
	// * Leaves the channels with the given leaving messages. A leaving message
	// * should be null or an empty String if you don't want any leaving message
	// * for that channel.
	// *
	// * @param channels
	// * List of channels to be left.
	// * @param leavingMessages
	// * List with the leavingMessages for the channels to be left.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channels or leavingMessages
	// * are null, or if their sizes don't match.
	// */
	// public void part(List<String> channels, List<String> leavingMessages)
	// throws IRCException {
	// if (channels == null || leavingMessages == null
	// || channels.size() != leavingMessages.size()) {
	// throw new IllegalArgumentException();
	// }
	// for (int i = 0; i < channels.size(); i++) {
	// part(channels.get(i), leavingMessages.get(i));
	// }
	// }
	//
	// /**
	// * Leaves the channel.
	// *
	// * @param channel
	// * Channel to be left.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channel is null.
	// */
	// public void part(String channel) throws IRCException {
	// part(channel, null);
	// }
	//
	// /**
	// * Leaves the channel with the given leaving message. The leaving message
	// * should be null if there is not going to be any leaving message.
	// *
	// * @param channel
	// * Channel to be left.
	// * @param leavingMessage
	// * Leaving message for the channel.
	// * @throws IRCException
	// * @throws {@link IllegalArgumentException} If channel is null.
	// */
	// public void part(String channel, String leavingMessage) throws
	// IRCException {
	// if (channel == null) {
	// throw new IllegalArgumentException();
	// }
	// String message = String.format(PART_COMMAND, channel,
	// leavingMessage != null ? leavingMessage : "");
	// send(message);
	// }
	//
	// @Override
	// public void onConnect() throws IRCException {
	// if (SEND_PASS) {
	// pass(PASSWORD);
	// }
	// nick(NICKNAME);
	// user(USERNAME, REALNAME);
	// }
	//

	//
	// @Override
	// public void onLogin() throws IRCException {
	// join(Arrays.asList(CHANNEL));
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void send(SocketChannel channel, String message) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		byte[] byteArr = message.getBytes();
		int bytesRead = 0, bytesToBeRead;
		while (bytesRead < message.length()) {
			bytesToBeRead = Math.min(buffer.capacity(), message.length()
					- bytesRead);
			buffer.put(byteArr, bytesRead, bytesToBeRead);
			bytesRead += bytesToBeRead;
			System.out.println("SENDING: " + new String(buffer.array()));
			buffer.flip();
			if (bytesRead < message.length()) {
				buffer.compact();
			}
			channel.write(buffer);
		}
		while (buffer.hasRemaining()) {
			System.out.println("2.SENDING: " + new String(buffer.array()));
			channel.write(buffer);
		}
	}
}
