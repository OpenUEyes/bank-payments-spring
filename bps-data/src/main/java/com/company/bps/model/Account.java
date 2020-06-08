package com.company.bps.model;

import com.company.bps.validators.constraints.LoginConstraint;
import com.company.bps.validators.constraints.PasswordConstraint;
import com.company.bps.validators.constraints.PhoneNumberConstraint;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @LoginConstraint
    @Column(name = "login", unique = true)
    private String login;

    @PasswordConstraint
    @Column(name = "password")
    private String password;

    @Email
    @NotBlank(message = "Invalid email. Can't be empty")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Invalid name. Can't be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Invalid surname. Can't be empty")
    @Column(name = "surname")
    private String surname;

    @PhoneNumberConstraint
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