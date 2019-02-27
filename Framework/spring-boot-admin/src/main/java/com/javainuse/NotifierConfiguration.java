package com.javainuse;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

/*import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;


@Configuration
public class NotifierConfiguration {

    @Autowired
    private Email notifier;
    
  
    
    @Primary
    @Bean
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remaindingNotifier = new RemindingNotifier(notifier);    	
        remaindingNotifier.setReminderPeriod(10);
        remaindingNotifier.setReminderPeriod(10);
        return remaindingNotifier;
    }

    }


/*@Configuration  
public class NotifierConfiguration {  
     @Bean  
     @Primary  
     public RemindingNotifier remindingNotifier() {  
          RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(loggerNotifier()));  
          notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));  
          return notifier;  
     }  
     @Scheduled(fixedRate = 1_000L)  
     public void remind() {  
          remindingNotifier().sendReminders();  
     }  
     @Bean  
     public FilteringNotifier filteringNotifier(Notifier delegate) {  
          return new FilteringNotifier(delegate);  
     }  
     @Bean  
     public LoggingNotifier loggerNotifier() { 
    	  System.out.println("Enter in to Login notifier");
          return new LoggingNotifier();  
     }  
}  
*/

