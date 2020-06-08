package com.company.bps.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"bill"})
@NoArgsConstructor
@Entity
@Table(name = "credit")
public class Credit implements Comparable<Credit> {

    @Id
    private Long id;

    @Column(name = "debt")
    private Double debt;

    @Column(name = "`limit`")
    private Double limit;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "deadline")
    private LocalDate deadline;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Bill bill;

    @Builder
    public Credit(Double debt, Double limit, Double percentage, LocalDate start, LocalDate deadline, Bill bill) {
        this.debt = debt;
        this.limit = limit;
        this.percentage = percentage;
        this.start = start;
        this.deadline = deadline;

        if (bill != null) {
            this.id = bill.getId();
            this.bill = bill;
            bill.setCredit(this);
        }
    }

    @Override
    public int compareTo(Credit credit) {
        return bill.compareTo(credit.getBill());
    }
}