package oleborn.weatherchatbot.updatehandler.proceesingupdate;

import lombok.RequiredArgsConstructor;
import oleborn.weatherchatbot.updatehandler.channelpost.ChannelPostProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class BaseChannelPostProcessor implements ChannelPostProcessor {

    @Override
    public void process(Message channelPost) {
        System.out.println("BaseChannelPostProcessor.process");
    }
}