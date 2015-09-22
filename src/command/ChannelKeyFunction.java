package command;

import model.IRCChannel;
import util.Function;

public class ChannelKeyFunction implements Function<IRCChannel, String> {

	ChannelKeyFunction() {
	}

	@Override
	public String evaluate(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		return channel.getKey();
	}
}
