package PrestaBanco.Crud.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import PrestaBanco.Crud.Entities.CreditEntity;
import PrestaBanco.Crud.Services.CreditService;

@RestController
@RequestMapping("/credits")
@CrossOrigin(origins = "*")
public class CreditController {
    
    @Autowired
    private CreditService creditService;
    
    // Credit application
    /**
     * Controller that allows applying for a loan.
     * @param id A Long with the client's id to apply for the loan.
     * @param body A Map with the data of the loan to apply for.
     * @return A UserEntity with the client's data updated.
     */
    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<CreditEntity> applyForCredit(@RequestParam("userId") Long id,
    @RequestParam(value = "proofOfIncome", required = false) MultipartFile file,
    @RequestParam(value = "appraisalCertificate", required = false) MultipartFile file2,
    @RequestParam(value = "creditHistory", required = false) MultipartFile file3,
    @RequestParam(value = "deedOfTheFirstHome", required = false) MultipartFile file4,
    @RequestParam(value = "financialStatusOfTheBusiness", required = false) MultipartFile file5,
    @RequestParam(value = "businessPlan", required = false) MultipartFile file6,
    @RequestParam(value = "remodelingBudget", required = false) MultipartFile file7,
    @RequestParam(value = "updatedAppraisalCertificate", required = false) MultipartFile file8,
    @RequestParam("monthlyIncome1") String monthlyIncome1,
    @RequestParam("monthlyIncome2") String monthlyIncome2,
    @RequestParam("monthlyIncome3") String monthlyIncome3,
    @RequestParam("monthlyIncome4") String monthlyIncome4,
    @RequestParam("monthlyIncome5") String monthlyIncome5,
    @RequestParam("monthlyIncome6") String monthlyIncome6,
    @RequestParam("monthlyIncome7") String monthlyIncome7,
    @RequestParam("monthlyIncome8") String monthlyIncome8,
    @RequestParam("monthlyIncome9") String monthlyIncome9,
    @RequestParam("monthlyIncome10") String monthlyIncome10,
    @RequestParam("monthlyIncome11") String monthlyIncome11,
    @RequestParam("monthlyIncome12") String monthlyIncome12,
    @RequestParam("requestedAmount") int requestedAmount,
    @RequestParam("loanTerm") int loanTerm,
    @RequestParam("annualInterestRate") double annualInterestRate,
    @RequestParam("typeOfLoan") String typeOfLoan,
    @RequestParam("creditsHistory") Boolean creditsHistory,
    @RequestParam("monthlyDebt") String monthlyDebt,
    @RequestParam("propertyAmount") int propertyAmount) {
        try {
            String monthlyIncome = monthlyIncome1 + "," + monthlyIncome2 + "," + monthlyIncome3 + "," + monthlyIncome4 + "," + monthlyIncome5 + "," + monthlyIncome6 + "," + monthlyIncome7 + "," + monthlyIncome8 + "," + monthlyIncome9 + "," + monthlyIncome10 + "," + monthlyIncome11 + "," + monthlyIncome12;
            CreditEntity credit = new CreditEntity(id, monthlyIncome, requestedAmount, loanTerm, annualInterestRate, typeOfLoan, creditsHistory, monthlyDebt, propertyAmount);
            byte[] pdfBytes = null;
            if (file != null && !file.isEmpty()) {
                pdfBytes = file.getBytes();
            }
            credit.setProofOfIncome(pdfBytes);
            byte[] pdfBytes2 = null;
            if (file2 != null && !file2.isEmpty()) {
                pdfBytes2 = file2.getBytes();
            }
            credit.setAppraisalCertificate(pdfBytes2);
            byte[] pdfBytes3 = null;
            if (file3 != null && !file3.isEmpty()) {
                pdfBytes3 = file3.getBytes();
            }
            credit.setCreditHistory(pdfBytes3);
            byte[] pdfBytes4 = null;
            if (file4 != null && !file4.isEmpty()) {
                pdfBytes4 = file4.getBytes();
            }
            credit.setDeedOfTheFirstHome(pdfBytes4);
            byte[] pdfBytes5 = null;
            if (file5 != null && !file5.isEmpty()) {
                pdfBytes5 = file5.getBytes();
            }
            credit.setFinancialStatusOfTheBusiness(pdfBytes5);
            byte[] pdfBytes6 = null;
            if (file6 != null && !file6.isEmpty()) {
                pdfBytes6 = file6.getBytes();
            }
            credit.setBusinessPlan(pdfBytes6);
            byte[] pdfBytes7 = null;
            if (file7 != null && !file7.isEmpty()) {
                pdfBytes7 = file7.getBytes();
            }
            credit.setRemodelingBudget(pdfBytes7);
            byte[] pdfBytes8 = null;
            if (file8 != null && !file8.isEmpty()) {
                pdfBytes8 = file8.getBytes();
            }
            credit.setCreditsHistory(creditsHistory);
            credit.setUpdatedAppraisalCertificate(pdfBytes8);
    
            CreditEntity creditSaved = creditService.applicationStatus(credit);
            return ResponseEntity.ok(creditSaved);
        } catch (Exception e) {
            // If the user is not found, return null.
            return null;
        }
    }
    
    // Evaluate credit
    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A CreditEntity with the loan evaluated.
     */
    @PutMapping("/evaluateCredit/{id}")
    public ResponseEntity<CreditEntity> evaluateCredit(@PathVariable Long id) {
        try{CreditEntity creditFound = creditService.findById(id);
            CreditEntity approved = creditService.evaluateCredit(creditFound);
            return ResponseEntity.ok(approved);
        } 
        catch (Exception e) {    
            return null;
        }
    }
    
    // Update status
    /**
     * Method that allows updating the status of a loan.
     * @param id A Long with the id of the loan to update.
     * @return A CreditEntity with the loan updated.
     */
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<CreditEntity> updateStatus(@PathVariable Long id) {
        try{
            CreditEntity credit = creditService.findById(id);
            if (credit.getApplicationStatus().equals("En evaluación")) {
                credit.setApplicationStatus("Pre-aprobado");
            } 
            else if (credit.getApplicationStatus().equals("Pre-aprobado")) {
                credit.setApplicationStatus("Aprobación final");
            }
            else if (credit.getApplicationStatus().equals("Aprobación final")) {
                credit.setApplicationStatus("Aprobada");
            }
            else if (credit.getApplicationStatus().equals("Aprobada")) {
                credit.setApplicationStatus("Desembolso");
                creditService.Disbursement(credit);
            }
            else {
                return null; 
            }
            CreditEntity creditSaved = creditService.saveCredit(credit);
            return ResponseEntity.ok(creditSaved);
        }
        catch (Exception e) {
            return null;
        }
    }

    // Update terms
    /**
     * Method that allows updating the terms of a loan.
     * @param id A Long with the id of the loan to update.
     * @param lienInsurance A Double with the lien insurance of the loan.
     * @param administrationFee A Double with the administration fee of the loan.
     * @return A CreditEntity with the loan updated.
     */
    @PutMapping("/updateTerms/{id}")
    public ResponseEntity<CreditEntity> updateTerms(@PathVariable Long id, 
    @RequestParam("lienInsurance") Double lienInsurance,
    @RequestParam("administrationFee") Double administrationFee) {
        try{
            CreditEntity credit = creditService.findById(id);
            credit.setLienInsurance(lienInsurance);
            credit.setAdministrationFee(administrationFee);
            creditService.totalCost(credit);
            CreditEntity creditSaved = creditService.saveCredit(credit);
            return ResponseEntity.ok(creditSaved);
        }
        catch (Exception e) {
            return null;
        }
    }

    // Rejection of terms
    /**
     * Method that allows rejecting the terms of a loan.
     * @param id A Long with the id of the loan to reject.
     * @return A CreditEntity with the loan rejected.
     */
    @PutMapping("/rejectTerms/{id}")
    public ResponseEntity<CreditEntity> rejectTerms(@PathVariable Long id) {
        try{
            CreditEntity credit = creditService.findById(id);
            credit.setApplicationStatus("Cancelada");
            CreditEntity creditSaved = creditService.saveCredit(credit);
            return ResponseEntity.ok(creditSaved);
        }
        catch (Exception e) {
            return null;
        }
    }

    //Obtener creditos todos
    /**
     * Controller that allows obtaining all the loans.
     * @return A List with all the loans.
     */
    @GetMapping("/all")
    public ResponseEntity<List<CreditEntity>> getAllCredits() {
        List<CreditEntity> credits = creditService.getAllCredits();
        return ResponseEntity.ok(credits);
    }
}
