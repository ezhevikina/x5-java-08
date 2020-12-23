package com.ezhevikina.harvest;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class CollectorReport implements Runnable {
  private final WareHouse wareHouse;
  private CountDownLatch allCollectorsAreDone;
  private CountDownLatch collectorsCanGetSalary;

  public CollectorReport(WareHouse wareHouse,
                         CountDownLatch allCollectorsAreDone,
                         CountDownLatch collectorsCanGetSalary) {
    this.wareHouse = wareHouse;
    this.allCollectorsAreDone = allCollectorsAreDone;
    this.collectorsCanGetSalary = collectorsCanGetSalary;
  }

  private void printReport(WareHouse wareHouse) {
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<Double[]> collectorParameters = new ArrayList<>(wareHouse.getCollectorReports().values());

    for (Double[] parameters : collectorParameters) {
      double applesCollected = parameters[1];
      double workedFor = parameters[0];
      String collectionSpeed = String.format("%.1f", applesCollected / workedFor);
      stringBuilder.append(collectionSpeed).append(", ");
    }

    String report = stringBuilder.toString();
    System.out.println(report.substring(0, report.length() - 2));
  }

  @Override
  public void run() {
    synchronized (wareHouse) {
      try {
        allCollectorsAreDone.await();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      printReport(wareHouse);
      collectorsCanGetSalary.countDown();
    }
  }
}
