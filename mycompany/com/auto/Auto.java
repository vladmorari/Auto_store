package com.mycompany.com.auto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum Tip {
    diesel, benzina
}

public class Auto {

    private String producator;
    private String model;
    private LocalDate anProducere;
    private Integer capMotor;
    private Tip tip;
    private DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter formatDate2 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public Auto(String producator, String model, String an, Integer capMotor, Tip tip) {
        this.producator = producator;
        this.model = model;
        this.capMotor = capMotor;
        this.tip = tip;
        this.anProducere = LocalDate.parse(an, formatDate);
    }

    @Override
    public String toString() {
        return "Producator: " + this.producator + "\nModel: " + this.model + "\nAn: " + this.anProducere.format(formatDate2) + "\nCapacitate motor: "
                + this.capMotor + "cm3\nTip combustibil: " + this.tip + "\n";
    }

    public LocalDate getAnProducere() {
        return anProducere;
    }

    public Integer getCapMotor() {
        return capMotor;
    }

    public String getProducator() {
        return producator;
    }

    public Tip getTip() {
        return tip;
    }

}

class AppAuto {

    List<Auto> autoList = new ArrayList<>();

    public static void main(String[] args) {
        AppAuto app = new AppAuto();
        app.start();
    }

    private void start() {

        String ds = "diesel";
        String bz = "benzina";

        add(new Auto("BMW", "x5", "10-09-2020", 3000, Tip.valueOf(ds)));
        add(new Auto("BMW", "x6", "22-02-2001", 2000, Tip.valueOf(bz)));
        add(new Auto("Audi", "Q6", "19-03-2016", 3500, Tip.valueOf(ds)));
        add(new Auto("Vaz", "2101", "02-04-1995", 4000, Tip.valueOf(bz)));
        add(new Auto("Volvo", "xc90", "01-08-2017", 2500, Tip.valueOf(ds)));

        System.out.println("Lista auto ----------");
        show(autoList);
        System.out.println("\n****Sort an *****\n");
        show(sort_an(autoList));
        System.out.println("\n***sort motor****\n");
        show(sort_capMotor(autoList));
        System.out.println("\n***sort nume****\n");
        show(sort_Nume(autoList));
        System.out.println("\nmasini nu mai vechi de 10 ani");
        show(getCarByAge(autoList, 10));
        System.out.println("\nmasini cu motor mai mare de 3000 cm3");
        show(getCarByEngine(autoList, 3000));
        System.out.println("\nmasini cu grupate dupa tip-combustibil");
        getCarByTypeOfFuel(autoList);
     
    }

    private void add(Auto obj) {
        autoList.add(obj);
    }

    private void show(List carList) {
        carList.stream().forEach(p -> System.out.println(p));
    }

    private List<Auto> sort_an(List<Auto> carList) {
        List<Auto> sortedList = carList.stream()
                .sorted(Comparator.comparing(Auto::getAnProducere))
                .collect(Collectors.toList());
        return sortedList;
    }

    private List<Auto> sort_capMotor(List<Auto> carList) {
        List<Auto> sortedList = carList.stream()
                .sorted(Comparator.comparingDouble(Auto::getCapMotor))
                .collect(Collectors.toList());
        return sortedList;
    }

    private List<Auto> sort_Nume(List<Auto> carList) {
        List<Auto> sortedList = carList.stream()
                .sorted(Comparator.comparing(Auto::getProducator))
                .collect(Collectors.toList());
        return sortedList;
    }

    private List<Auto> getCarByAge(List<Auto> carList, int age) {
        LocalDate localDateNow = LocalDate.now();
        List<Auto> list = carList.stream().filter((Auto car) -> {
            return car.getAnProducere().isAfter(localDateNow.minusYears(age));
        }).collect(Collectors.toList());
        return list;
    }

    private List<Auto> getCarByEngine(List<Auto> carList, Integer engineCapacity) {
        LocalDate localDateNow = LocalDate.now();
        List<Auto> list = carList.stream().filter((Auto car) -> {
            return car.getCapMotor() > engineCapacity;
        }).collect(Collectors.toList());
        return list;
    }

    private void getCarByTypeOfFuel(List<Auto> carList) {
        Map<Tip, List<Auto>> fuelType = carList.stream().collect(Collectors.groupingBy(p -> p.getTip()));
        fuelType.forEach((fuel, p) -> System.out.format("Fuel Type [%s]\n%s\n", fuel, p));
    }

}
