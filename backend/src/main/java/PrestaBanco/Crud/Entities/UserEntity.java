package PrestaBanco.Crud.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifying_document")
    private String identifyingDocument;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Lob
    private byte[] file;

    @Column(name = "job_seniority")
    // Que significa esto?
    // Es la antigüedad en el trabajo.
    private int jobSeniority;

    @Column(name = "current_savings_balance")
    // Que significa esto?
    // Es el saldo actual de ahorros.
    private int currentSavingsBalance;

    @Column(name = "savings_account_history")
    // Que significa esto?
    // Es el historial de la cuenta de ahorros.
    private String savingsAccountHistory;

    @Column(name = "creation_date")
    // Que significa esto?
    // Es la fecha de creación.
    // tipo de dato fecha
    private LocalDate creationDate;

    @Column(name = "deposit_account")
    // Que significa esto?
    // Es la cuenta de depósito.
    private String depositAccount;

    @Column(name = "withdrawal account")
    // Que significa esto?
    // Es la cuenta de retiro.
    private String withdrawalAccount;

    @Column(name = "birthdate")
    // Que significa esto?
    // Es la fecha de nacimiento.
    private LocalDate birthdate;

    public UserEntity(String identifyingDocument, String name, String email, String password, byte[] file, int jobSeniority, LocalDate birthdate) {
        this.identifyingDocument = identifyingDocument;
        this.name = name;
        this.email = email;
        this.password = password;
        this.file = file;
        this.jobSeniority = jobSeniority;
        this.birthdate = birthdate;
    }
}