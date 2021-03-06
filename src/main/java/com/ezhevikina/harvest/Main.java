package com.ezhevikina.harvest;

import java.util.concurrent.CountDownLatch;

public class Main {

  public static void main(String[] args) {
    int numberOfThreads = 500;
    WareHouse wareHouse = new WareHouse();
    CountDownLatch allCollectorsAreDone = new CountDownLatch(numberOfThreads);
    CountDownLatch collectorsCanGetSalary = new CountDownLatch(1);

    Thread collectorReport = new Thread(new CollectorReport(wareHouse, allCollectorsAreDone, collectorsCanGetSalary));
    collectorReport.start();

    for (int i = 0; i < numberOfThreads; i++) {
      Thread thread = new Thread(new AppleCollector("Collector" + i,
          allCollectorsAreDone, collectorsCanGetSalary, wareHouse));
      thread.start();
    }
  }
}
