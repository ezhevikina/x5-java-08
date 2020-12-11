package com.ezhevikina.harvest;

import java.util.ArrayList;

public class CollectorReport implements Runnable {
  private final WareHouse wareHouse;

  public CollectorReport(WareHouse wareHouse) {
    this.wareHouse = wareHouse;
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
      printReport(wareHouse);
    }
  }
}
