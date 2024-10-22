package PrestaBanco.Crud.Services;

import PrestaBanco.Crud.Entities.CreditEntity;
import PrestaBanco.Crud.Entities.UserEntity;
import PrestaBanco.Crud.Repositories.CreditRepository;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CreditServiceTest {
    CreditService creditService = new CreditService();
    UserService userService = new UserService();
 // R1
    @Test
    void whenFeeIncomeRatioIsBelow35Percent_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();  // Instanciamos CreditService

        // Crear un CreditEntity con un UserEntity asociado
        CreditEntity credit = new CreditEntity(1L, "2024-10-03 200000,2024-09-03 200000,2024-08-03 200000,2024-07-03 200000,2024-06-03 20000000,2024-05-03 200000,2024-04-03 200000,2024-03-03 200000,2024-02-03 200000,2024-01-03 200000,2023-12-03 200000,2023-11-03 200000,2023-10-03 200000,2023-09-03 200000,2023-08-03 200000,2023-07-03 200000,2023-06-03 200000,2023-05-03 200000,2023-04-03 200000,2023-03-03 200000,2023-02-03 200000,2023-01-03 200000,2022-12-03 200000,2022-11-03 200000", 1000000, 20, 0.035, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R1(credit);  // Llama a la función R1

        // Then
        assertThat(result).isTrue();  // Verifica que el resultado sea True
    }

    @Test
    void whenFeeIncomeRatioIsExactly35Percent_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();

        CreditEntity credit = new CreditEntity(2L, "2023-01-01 8000,2023-02-01 8000,2023-03-01 8000", 50000, 15, 6.0, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R1(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea True
    }

    @Test
    void whenFeeIncomeRatioIsAbove35Percent_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();

        CreditEntity credit = new CreditEntity(3L, "2023-01-01 7000,2023-02-01 7000,2023-03-01 7000", 60000, 20, 5.0, "Segunda Vivienda", true, "500", 80000);

        // When
        Boolean result = creditService.R1(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea False
    }

    @Test
    void whenLowIncomeAndHighRequestedAmount_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();

        CreditEntity credit = new CreditEntity(4L, "2023-01-01 1500,2023-02-01 1500", 70000, 25, 7.5, "Propiedades Comerciales", true, "300", 120000);

        // When
        Boolean result = creditService.R1(credit);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenHighIncomeAndLowRequestedAmount_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();

        CreditEntity credit = new CreditEntity(5L, "2024-10-03 200000,2024-09-03 200000,2024-08-03 200000,2024-07-03 200000,2024-06-03 20000000,2024-05-03 200000,2024-04-03 200000,2024-03-03 200000,2024-02-03 200000,2024-01-03 200000,2023-12-03 200000,2023-11-03 200000,2023-10-03 200000,2023-09-03 200000,2023-08-03 200000,2023-07-03 200000,2023-06-03 200000,2023-05-03 200000,2023-04-03 200000,2023-03-03 200000,2023-02-03 200000,2023-01-03 200000,2022-12-03 200000,2022-11-03 200000", 1000000, 20, 0.035, "Remodelación", true, "600", 200000);

        // When
        Boolean result = creditService.R1(credit);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenLongLoanTermAndLowRequestedAmount_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();

        CreditEntity credit = new CreditEntity(6L, "2023-01-01 5000,2023-02-01 5000,2023-03-01 5000", 30000, 30, 5.0, "Primera Vivienda", true, "400", 90000);

        // When
        Boolean result = creditService.R1(credit);

        // Then
        assertThat(result).isFalse();
    }

    //R2

    @Test
    void whenCreditsHistoryIsNull_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(1L, "2023-01-01 8000,2023-02-01 8000,2023-03-01 8000", 50000, 15, 6.0, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea False
    }

    @Test
    void whenCreditsHistoryIsTrue_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(5L, "2023-01-01 12000,2023-02-01 12000,2023-03-01 12000", 40000, 10, 4.0, "Remodelación", true, "600", 200000);
        credit.setCreditsHistory(true);
        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isTrue();  // Verifica que el resultado sea True
    }

    @Test
    void whenCreditsHistoryIsFalse_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(3L, "2023-01-01 7000,2023-02-01 7000,2023-03-01 7000", 60000, 20, 5.0, "Segunda Vivienda", false, "500", 80000);

        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea False
    }

    @Test
    void whenCreditsHistoryIsNullWithValidOtherProperties_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(4L, "2023-01-01 5000,2023-02-01 5000,2023-03-01 5000", 30000, 20, 7.0, "Propiedades Comerciales", null, "300", 90000);

        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea False
    }

    @Test
    void whenCreditsHistoryIsTrueWithValidOtherProperties_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(5L, "2023-01-01 12000,2023-02-01 12000,2023-03-01 12000", 40000, 10, 4.0, "Remodelación", true, "600", 200000);
        credit.setCreditsHistory(true);
        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isTrue();  // Verifica que el resultado sea True
    }

    @Test
    void whenCreditsHistoryIsFalseWithValidOtherProperties_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(6L, "2023-01-01 7000,2023-02-01 7000,2023-03-01 7000", 60000, 15, 5.5, "Primera Vivienda", false, "500", 100000);

        // When
        Boolean result = creditService.R2(credit);

        // Then
        assertThat(result).isFalse();  // Verifica que el resultado sea False
    }

    //R3

    //R4

    @Test
    void whenDebtSumPlusMonthlyFeeIsLessThan50PercentOfIncome_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(1L, "2024-10-03 200000,2024-09-03 200000,2024-08-03 200000,2024-07-03 200000,2024-06-03 20000000,2024-05-03 200000,2024-04-03 200000,2024-03-03 200000,2024-02-03 200000,2024-01-03 200000,2023-12-03 200000,2023-11-03 200000,2023-10-03 200000,2023-09-03 200000,2023-08-03 200000,2023-07-03 200000,2023-06-03 200000,2023-05-03 200000,2023-04-03 200000,2023-03-03 200000,2023-02-03 200000,2023-01-03 200000,2022-12-03 200000,2022-11-03 200000", 1000000, 20, 0.035, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isTrue();  // La suma de deudas más cuota mensual es menor al 50% del ingreso
    }

    @Test
    void whenDebtSumPlusMonthlyFeeEquals50PercentOfIncome_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(1L, "2024-10-03 200000,2024-09-03 200000,2024-08-03 200000,2024-07-03 200000,2024-06-03 20000000,2024-05-03 200000,2024-04-03 200000,2024-03-03 200000,2024-02-03 200000,2024-01-03 200000,2023-12-03 200000,2023-11-03 200000,2023-10-03 200000,2023-09-03 200000,2023-08-03 200000,2023-07-03 200000,2023-06-03 200000,2023-05-03 200000,2023-04-03 200000,2023-03-03 200000,2023-02-03 200000,2023-01-03 200000,2022-12-03 200000,2022-11-03 200000", 1000000, 20, 0.035, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isTrue();  // La suma de deudas más cuota mensual es igual al 50% del ingreso
    }

    @Test
    void whenDebtSumPlusMonthlyFeeIsGreaterThan50PercentOfIncome_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(3L, "2023-01-01 4000,2023-02-01 4000", 50000, 15, 6.0, "Primera Vivienda", true, "4000,4000", 100000);

        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isFalse();  // La suma de deudas más cuota mensual es mayor al 50% del ingreso
    }

    @Test
    void whenDebtContainsOneDebtLessThan50PercentOfIncome_thenReturnTrue() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(1L, "2024-10-03 200000,2024-09-03 200000,2024-08-03 200000,2024-07-03 200000,2024-06-03 20000000,2024-05-03 200000,2024-04-03 200000,2024-03-03 200000,2024-02-03 200000,2024-01-03 200000,2023-12-03 200000,2023-11-03 200000,2023-10-03 200000,2023-09-03 200000,2023-08-03 200000,2023-07-03 200000,2023-06-03 200000,2023-05-03 200000,2023-04-03 200000,2023-03-03 200000,2023-02-03 200000,2023-01-03 200000,2022-12-03 200000,2022-11-03 200000", 1000000, 20, 0.035, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isTrue();  // La deuda es menor al 50% del ingreso más la cuota mensual
    }

    @Test
    void whenDebtSumIsGreaterThan50PercentOfIncome_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(5L, "2023-01-01 3000,2023-02-01 3000,2023-03-01 4000", 50000, 15, 6.0, "Propiedades Comerciales", true, "3000,3000,4000", 100000);

        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isFalse();  // La suma de deudas es mayor al 50% del ingreso más la cuota mensual
    }

    @Test
    void whenDebtIsNullButMonthlyFeeIsGreaterThan50PercentOfIncome_thenReturnFalse() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(6L, null, 50000, 15, 6.0, "Remodelación", true, null, 100000);


        // When
        Boolean result = creditService.R4(credit);

        // Then
        assertThat(result).isFalse();  // La deuda es nula pero la cuota mensual es mayor al 50% del ingreso
    }
    //R5
    @Test
    void whenFirstHomeLoanAndRequestedAmountIsLessThan80Percent_thenReturnTrue() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 70000, 30, 5.0, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenFirstHomeLoanAndRequestedAmountIsMoreThan80Percent_thenReturnFalse() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 90000, 30, 5.0, "Primera Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenSecondHomeLoanAndRequestedAmountIsLessThan70Percent_thenReturnTrue() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 60000, 30, 5.0, "Segunda Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenSecondHomeLoanAndRequestedAmountIsMoreThan70Percent_thenReturnFalse() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 80000, 30, 5.0, "Segunda Vivienda", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenCommercialPropertyLoanAndRequestedAmountIsLessThan60Percent_thenReturnTrue() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 50000, 30, 5.0, "Propiedades Comerciales", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenRemodelingLoanAndRequestedAmountIsMoreThan50Percent_thenReturnFalse() {
        // Given
        CreditEntity credit = new CreditEntity(1L, "3000", 60000, 30, 5.0, "Remodelación", true, "500", 100000);

        // When
        Boolean result = creditService.R5(credit);

        // Then
        assertThat(result).isFalse();
    }

    //R6

    //R7
    @Test
    void whenAllCriteriaAreTrue_thenReturnApproved() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return true; }
        };

        CreditEntity credit = new CreditEntity(1L, "2023-01-01 1000,2023-02-01 1000", 50000, 15, 6.0, "Primera Vivienda", true, "1000,1000", 100000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Aprobado");
    }

    @Test
    void whenFourCriteriaAreTrueAndOneFalse_thenReturnReview() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return false; }
        };

        CreditEntity credit = new CreditEntity(2L, "2023-01-01 2000,2023-02-01 2000", 50000, 15, 6.0, "Segunda Vivienda", true, "2000,2000", 100000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Revisión");
    }

    @Test
    void whenThreeCriteriaAreTrueAndTwoFalse_thenReturnReview() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return false; }
        };

        CreditEntity credit = new CreditEntity(3L, "2023-01-01 1500,2023-02-01 1500", 45000, 12, 5.0, "Propiedades Comerciales", true, "1500,1500", 90000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Revisión");
    }

    @Test
    void whenTwoCriteriaAreTrueAndThreeFalse_thenReturnRejected() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return false; }
        };

        CreditEntity credit = new CreditEntity(4L, "2023-01-01 2000,2023-02-01 2000", 50000, 10, 6.5, "Remodelación", true, "2000,2000", 110000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Rechazado");
    }

    @Test
    void whenOneCriteriaIsTrueAndFourFalse_thenReturnRejected() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return true; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return false; }
        };

        CreditEntity credit = new CreditEntity(5L, "2023-01-01 2500,2023-02-01 2500", 60000, 18, 7.0, "Primera Vivienda", true, "2500,2500", 120000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Rechazado");
    }

    @Test
    void whenNoCriteriaAreTrue_thenReturnRejected() {
        // Given
        CreditService creditService = new CreditService() {
            @Override
            public Boolean R71(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R72(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R73(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R74(CreditEntity creditFound) { return false; }
            @Override
            public Boolean R75(CreditEntity creditFound) { return false; }
        };

        CreditEntity credit = new CreditEntity(6L, "2023-01-01 1000,2023-02-01 1000", 50000, 20, 5.0, "Segunda Vivienda", false, "1000,1000", 110000);

        // When
        String result = creditService.R7(credit);

        // Then
        assertThat(result).isEqualTo("Rechazado");
    }

    //R71

    //
    @Test
    void whenLowInterestRate_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(1L, "2023-01-01 1000,2023-02-01 1000", 50000, 10, 2.0, "Primera Vivienda", true, "1000", 100000);
        credit.setLienInsurance(0.01); // Seguro de prenda
        credit.setAdministrationFee(0.02); // Tarifa administrativa

        // When
        creditService.totalCost(credit);

        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(20960);  // Expected value
        assertThat(credit.getFirstInstallment()).isEqualTo(21960);  // Expected value
        assertThat(credit.getTotalAmount()).isEqualTo(2516200);  // Expected value
    }

    @Test
    void whenHighInterestRate_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(2L, "2023-01-01 1000,2023-02-01 1000", 100000, 15, 10.0, "Segunda Vivienda", true, "1000", 200000);
        credit.setLienInsurance(0.02); // Seguro de prenda
        credit.setAdministrationFee(0.05); // Tarifa administrativa

        // When
        creditService.totalCost(credit);

        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(23075);  // Expected value
        assertThat(credit.getFirstInstallment()).isEqualTo(28075);  // Expected value
        assertThat(credit.getTotalAmount()).isEqualTo(4158500);  // Expected value
    }

    @Test
    void whenLienInsuranceIsZero_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(3L, "2023-01-01 1500,2023-02-01 1500", 30000, 5, 4.5, "Propiedades Comerciales", true, "1500", 30000);
        credit.setLienInsurance(0.0); // Seguro de prenda
        credit.setAdministrationFee(0.03); // Tarifa administrativa

        // When
        creditService.totalCost(credit);

        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(20559);  // Expected value (500 + 20000)
        assertThat(credit.getFirstInstallment()).isEqualTo(21459);  // Expected value (20500 + 350)
        assertThat(credit.getTotalAmount()).isEqualTo(1234440);  // Expected value
    }

    @Test
    void whenAdministrationFeeIsZero_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(4L, "2023-01-01 5000,2023-02-01 5000", 100000, 20, 5.0, "Remodelación", true, "5000", 100000);
        credit.setLienInsurance(0.01); // Seguro de prenda
        credit.setAdministrationFee(0.0); // Tarifa administrativa


        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(0);  // Expected value
        assertThat(credit.getFirstInstallment()).isEqualTo(0);  // Expected value
        assertThat(credit.getTotalAmount()).isEqualTo(0);  // Expected value
    }

    @Test
    void whenAllValuesAreHigh_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(5L, "2023-01-01 10000,2023-02-01 10000", 1000000, 30, 15.0, "Propiedades Comerciales", true, "10000", 1000000);
        credit.setLienInsurance(0.05); // Seguro de prenda
        credit.setAdministrationFee(0.1); // Tarifa administrativa

        // When
        creditService.totalCost(credit);

        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(82644);  // Expected value
        assertThat(credit.getFirstInstallment()).isEqualTo(182644);  // Expected value
        assertThat(credit.getTotalAmount()).isEqualTo(29851840);  // Expected value
    }

    @Test
    void whenValuesAreNormal_thenCorrectTotalCostCalculation() {
        // Given
        CreditService creditService = new CreditService();
        CreditEntity credit = new CreditEntity(6L, "2023-01-01 1000,2023-02-01 1000", 50000, 10, 5.5, "Primera Vivienda", true, "1000", 50000);
        credit.setLienInsurance(0.02); // Seguro de prenda
        credit.setAdministrationFee(0.04); // Tarifa administrativa

        // When
        creditService.totalCost(credit);

        // Then
        assertThat(credit.getRemainingMonthlyInstallments()).isEqualTo(21543);  // Expected value
        assertThat(credit.getFirstInstallment()).isEqualTo(23543);  // Expected value
        assertThat(credit.getTotalAmount()).isEqualTo(2587160);  // Expected value
    }
}
