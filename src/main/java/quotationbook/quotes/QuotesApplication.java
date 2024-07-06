package quotationbook.quotes;

import models.Quote;
import org.hibernate.annotations.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import services.BashParse;
import services.QuoteService;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class QuotesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
