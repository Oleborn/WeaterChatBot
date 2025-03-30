package oleborn.weatherchatbot.updatehandler.channelpost;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface ChannelPostProcessor {
    void process(Message channelPost);
}