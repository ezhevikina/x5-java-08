package com.ezhevikina.harvest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class AppleCollector implements Runnable {
  private String name;
  private CountDownLatch latch1;
  private CountDownLatch latch2;
  private WareHouse wareHouse;

  // время, которое поток тратит на сбор - от 0,9 до 3,5 секунд, у каждого потока оно случайное
  private double workedFor = (new Random().nextInt((35 - 9) + 1) + 9) / 10.0;

  // случайный фактор от 0,8 до 1,5
  private double factor = (new Random().nextInt((15 - 8) + 1) + 8) / 10.0;

  // количество яблок - потраченное время * 10 * PI * случайный фактор
  private double applesCollected = Math.floor(workedFor * factor * 10 * Math.PI);

  public AppleCollector(String name, CountDownLatch latch1, CountDownLatch latch2, WareHouse wareHouse) {
    this.name = name;
    this.latch1 = latch1;
    this.latch2 = latch2;
    this.wareHouse = wareHouse;
  }

  private void reportToWarehouse() {
    wareHouse.getReport(name, new Double[]{workedFor, applesCollected});
  }

  private void getSalary() {
    System.out.println(
        String.format("%s worked for %.1f seconds, collected %d apples. Got %.1f$",
            name, workedFor, (int) applesCollected, wareHouse.paySalary(name)));
  }

  @Override
  public void run() {
    try {
      Thread.sleep((long) (workedFor * 1000));
      reportToWarehouse();
      latch1.countDown();
      latch1.await();
      latch2.await();
      getSalary();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
