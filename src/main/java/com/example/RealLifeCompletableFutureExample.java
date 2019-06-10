package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class RealLifeCompletableFutureExample {

    public static void main(String[] args) {
        RealLifeCompletableFutureExample ex = new RealLifeCompletableFutureExample();
        ex.syncCars();
    }


    public void syncCars(){
        long start = System.currentTimeMillis();

        cars().thenCompose(cars -> {
            List<CompletionStage<Car>> updatedCars = cars.stream()
                    .map(car -> rating(car.manufacturerId).thenApply(r -> {

                        car.setRating(r);
                        System.out.println("Done with "+car);
                        return car;
                    })).collect(Collectors.toList());

            CompletableFuture<Void> done = CompletableFuture
                    .allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]));

            return done.thenApply(v -> updatedCars.stream().map(CompletionStage::toCompletableFuture)
                    .map(CompletableFuture::join).collect(Collectors.toList()));
        }).toCompletableFuture().join();



        long end = System.currentTimeMillis();

        System.out.println("Took " + (end - start) + " ms.");
    }

      CompletionStage<Float> rating(int manufacturer) {
        return CompletableFuture.supplyAsync(() -> {


            try {
                System.out.println("Setting rating for "+manufacturer);
                simulateDelay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            switch (manufacturer) {
            case 2:
                return 4f;
            case 3:
                return 4.1f;
            case 7:
                return 4.2f;
            default:
                return 5f;
            }
        }) ;
    }

      CompletionStage<List<Car>> cars() {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(1, 3, "Fiesta", 2017));
        carList.add(new Car(2, 7, "Camry", 2014));
        carList.add(new Car(3, 2, "M2", 2008));
        return CompletableFuture.supplyAsync(() -> carList);
    }

    private   void simulateDelay() throws InterruptedException {
        Thread.sleep(5000);
    }
}
