package com.company.bps.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Comparable<Account> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Set<Bill> bills = new HashSet<>();

    @Builder
    public Account(Long id, String login, String password, String email, String name, String surname, String phoneNumber, Set<Bill> bills) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;

        if (bills != null) {
            bills.forEach(bill -> bill.setAccount(this));
            this.bills = bills;
        }
    }

    @Override
    public int compareTo(Account account) {
        return Long.compare(id, account.getId());
    }
}