package com.ezhevikina.harvest;

import java.util.concurrent.ConcurrentHashMap;

public class WareHouse {
  private ConcurrentHashMap<String, Double[]> collectorReports = new ConcurrentHashMap<>();

  public ConcurrentHashMap<String, Double[]> getCollectorReports() {
    return collectorReports;
  }

  public void getReport(String threadName, Double[] parameters) {
    collectorReports.put(threadName, parameters);
  }

  public double paySalary(String threadName) {
    return collectorReports.get(threadName)[1] * 7;
  }
}
