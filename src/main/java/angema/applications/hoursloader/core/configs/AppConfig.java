package angema.applications.hoursloader.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // Configura el tamaño del pool de hilos
        scheduler.setThreadNamePrefix("TaskScheduler-"); // Prefijo para los nombres de los hilos
        scheduler.initialize();
        return scheduler;
    }

    // Configura el programador de tareas para utilizar el TaskScheduler
    @Bean
    public ScheduledTaskRegistrar scheduledTaskRegistrar(ThreadPoolTaskScheduler taskScheduler) {
        ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();
        taskRegistrar.setTaskScheduler(taskScheduler);
        return taskRegistrar;
    }

    // Otras configuraciones y beans de la aplicación...

}
