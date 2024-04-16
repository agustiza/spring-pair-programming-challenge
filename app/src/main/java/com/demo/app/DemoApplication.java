package com.demo.app;

import com.demo.carsales.analytics.service.MonthlyCarSaleByBrandRow;
import com.demo.carsales.analytics.service.SalesSummarizer;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

  private static final Logger LOGGER = LoggerFactory
    .getLogger(DemoApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Autowired
  private SalesSummarizer summarizer;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    LOGGER.info("This is a simple demo app showcasing the use of a "
      + "Spring lib with Autoconfiguration.");
    LOGGER.info("DemoApplication started with args: {}",
      String.join(" ", args.getSourceArgs()));

    // Not how you usually parse and validate args.
    if (!args.containsOption("start") || !args.containsOption("end")) {
      throw new IllegalArgumentException("Missing required options");
    }

    ZonedDateTime start = ZonedDateTime.parse(
      args.getOptionValues("start").getFirst());
    ZonedDateTime end = ZonedDateTime.parse(
      args.getOptionValues("end").getFirst());

    var summedRows = summarizer.sumTotalSalesByMonthAndBrand(start, end);

    try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(System.out))) {
      writer.writeNext(new String[]{"Date", "Brand", "TotalSales"});
      for (var row : summedRows) {
        writer.writeNext(new String[]{
          row.yearMonth().toString(),
          row.brand(),
          row.totalPurchase().toString()});
      }
    }

  }

}
