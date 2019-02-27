package com.techmango.springbootadminserver;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;

@Configuration
public class NotifierConfiguration {

	@Autowired
	private Email notifier;

	@Primary
	@Bean
	public RemindingNotifier remindingNotifier() {
		RemindingNotifier remaindingNotifier = new RemindingNotifier(notifier, null);
		remaindingNotifier.setReminderPeriod(Duration.ofMinutes(10));
		remaindingNotifier.setCheckReminderInverval(Duration.ofSeconds(10));
		remaindingNotifier.setReminderPeriod(Duration.ofMinutes(10));
		return remaindingNotifier;
	}
}
