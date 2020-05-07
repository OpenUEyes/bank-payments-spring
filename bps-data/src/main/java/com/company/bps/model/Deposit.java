package com.company.bps.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"bill"})
@NoArgsConstructor
@Entity
@Table(name = "deposit")
public class Deposit implements Comparable<Deposit> {

    @Id
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "finish")
    private LocalDate finish;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Bill bill;

    @Builder
    public Deposit(Double amount, Double rate, LocalDate start, LocalDate finish, Bill bill) {
        this.amount = amount;
        this.rate = rate;
        this.start = start;
        this.finish = finish;

        if (bill != null) {
            this.id = bill.getId();
            this.bill = bill;
            bill.setDeposit(this);
        }
    }

    @Override
    public int compareTo(Deposit deposit) {
        return bill.compareTo(deposit.getBill());
    }
}