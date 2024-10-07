package PrestaBanco.Crud.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "monthly_income", length = 10000 )
    // Que significa esto?
    // Es el ingreso mensual.
    private String monthlyIncome;

    @Column(name = "credits_history") 
    // Que significa esto? 
    // Es un historial de crédito.
    private Boolean creditsHistory;

    @Column(name = "requested_amount")
    // Que significa esto?
    // Es la cantidad solicitada.
    private int requestedAmount;

    @Column(name = "loan_term")
    // Que significa esto?
    // Es el plazo del préstamo.
    private int loanTerm;

    @Column(name = "annual_interest_rate")
    // Que significa esto?
    // Es la tasa de interés anual.
    private Double annualInterestRate;

    @Column(name = "lien_insurance")
    // Que significa esto?
    // Es el seguro de gravamen.
    private Double lienInsurance;

    @Column(name = "administration_fee")
    // Que significa esto?
    // Es la comisión de administración.    
    private Double administrationFee;

    @Lob
    private byte[] proofOfIncome;
    // Que significa esto?
    // Es la prueba de ingresos.

    @Lob
    private byte[] appraisalCertificate;
    // Que significa esto?
    // Es el certificado de tasación.

    @Lob
    private byte[] creditHistory;
    // Que significa esto?
    // Es el historial de crédito.

    @Lob
    private byte[] deedOfTheFirstHome;
    // Que significa esto?
    // Es la escritura de la primera vivienda.

    @Lob
    private byte[] financialStatusOfTheBusiness;
    // Que significa esto?
    // Es el estado financiero del negocio.

    @Lob
    private byte[] businessPlan;
    // Que significa esto?
    // Es el plan de negocios.

    @Lob
    private byte[] remodelingBudget;
    // Que significa esto?
    // Es el presupuesto de remodelación.

    @Lob
    private byte[] updatedAppraisalCertificate;
    // Que significa esto?
    // Es el certificado de tasación actualizado.

    @Column(name = "application_status")
    // Que significa esto?
    // Es el estado de la solicitud.
    private String applicationStatus;

    @Column(name = "first_installment")
    // Que significa esto?
    // Es la primera cuota.
    private int firstInstallment;

    @Column(name = "remaining_monthly_installments")
    // Que significa esto?
    // Son las cuotas mensuales restantes.
    private int remainingMonthlyInstallments;

    @Column(name = "type_of_loan")
    // Que significa esto?
    // Es el tipo de préstamo.
    private String typeOfLoan;

    @Column(name = "monthly_debt") 
    // Que significa esto? 
    // Es un deudas mensuales.
    private String monthlyDebt;

    @Column(name = "property_amount")
    // Que significa esto?
    // Es la cantidad de propiedad.
    private int propertyAmount;

    @Column(name = "total_amount")
    // Que significa esto?
    // Es la cantidad total.
    private int totalAmount;


    public CreditEntity(Long userId, String monthlyIncome, int requestedAmount, int loanTerm, double annualInterestRate, String typeOfLoan, Boolean creditsHistory, String monthlyDebt, int propertyAmount) {
        this.userId = userId;
        this.monthlyIncome = monthlyIncome;
        this.requestedAmount = requestedAmount;
        this.loanTerm = loanTerm;
        this.annualInterestRate = annualInterestRate;
        this.typeOfLoan = typeOfLoan;
        this.monthlyDebt = monthlyDebt;
        this.propertyAmount = propertyAmount;
    }
}
