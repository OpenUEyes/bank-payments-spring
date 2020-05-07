package com.company.bps.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill implements Comparable<Bill> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "validity")
    private LocalDate validity;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
//    private Long accountId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bill")
    private Deposit deposit;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bill")
    private Credit credit;

    @Builder
    public Bill(Long id, Type type, Double balance, LocalDate validity, Account account) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.validity = validity;

        if (account != null) {
            account.getBills().add(this);
            this.account = account;
        }
    }

    @Override
    public int compareTo(Bill bill) {
        return Long.compare(id, bill.getId());
    }
}