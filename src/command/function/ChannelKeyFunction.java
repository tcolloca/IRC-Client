package command.function;

import model.IRCChannel;
import util.Function;

public class ChannelKeyFunction implements Function<IRCChannel, String> {

	@Override
	public String evaluate(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		return channel.getKey();
	}
}
