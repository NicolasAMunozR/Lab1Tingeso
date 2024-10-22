import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import creditService from "../services/credit.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import dayjs from "dayjs";

const AddCredit = () => {
    const [monthlyIncome, setMonthlyIncome] = useState(Array(12).fill(""));
    const [requestedAmount, setRequestedAmount] = useState("");
    const [loanTerm, setLoanTerm] = useState("");
    const [annualInterestRate, setAnnualInterestRate] = useState("");
    const [typeOfLoan, setTypeOfLoan] = useState("");
    const [creditsHistory, setCreditsHistory] = useState("");
    const [monthlyDebt, setMonthlyDebt] = useState("");
    const [propertyAmount, setPropertyAmount] = useState("");   
    const [proofOfIncome, setProofOfIncome] = useState(null);
    const [appraisalCertificate, setAppraisalCertificate] = useState(null);
    const [creditHistory, setCreditHistory] = useState(null);
    const [deedOfTheFirstHome, setDeedOfTheFirstHome] = useState(null);
    const [financialStatusOfTheBusiness, setFinancialStatusOfTheBusiness] = useState(null);
    const [businessPlan, setBusinessPlan] = useState(null);
    const [remodelingBudget, setRemodelingBudget] = useState(null);
    const [updatedAppraisalCertificate, setUpdatedAppraisalCertificate] = useState(null);

    const { id } = useParams();
    const [titleuserForm, setTitleuserForm] = useState("");
    const navigate = useNavigate();

    const handleIncomeChange = (index, value) => {
        const updatedIncomes = [...monthlyIncome];
        const date = updatedIncomes[index].split(" ")[0]; // Mantener la fecha
        updatedIncomes[index] = `${date} ${value}`;
        setMonthlyIncome(updatedIncomes);
    };

    const saveuser = (e) => {
        e.preventDefault();
       
        
        const formData = new FormData();
        monthlyIncome.forEach((income, index) => {
            formData.append(`monthlyIncome${index + 1}`, income);
            console.log(`monthlyIncome${index + 1}`, income);
        });

        formData.append("userId", id);
        console.log("id", formData.get("id"));
        formData.append("requestedAmount", requestedAmount);
        console.log("requestedAmount", requestedAmount);
        formData.append("loanTerm", loanTerm);
        console.log("loanTerm", loanTerm);
        formData.append("annualInterestRate", annualInterestRate);
        console.log("annualInterestRate", annualInterestRate);
        formData.append("typeOfLoan", typeOfLoan);
        console.log("typeOfLoan", typeOfLoan);
        formData.append("creditsHistory", creditsHistory);
        console.log("creditsHistory", creditsHistory);
        formData.append("monthlyDebt", monthlyDebt);
        console.log("monthlyDebt", monthlyDebt);
        formData.append("propertyAmount", propertyAmount);
        console.log("propertyAmount", propertyAmount);

        console.log("monthlyIncome", monthlyIncome);
        console.log("monthlyIncome", formData.get("monthlyIncome1"));
        console.log("monthlyIncome", formData.get("monthlyIncome2"));
        console.log("monthlyIncome", formData.get("monthlyIncome3"));
        console.log("monthlyIncome", formData.get("monthlyIncome4"));

        if (proofOfIncome) formData.append("proofOfIncome", proofOfIncome);
        if (appraisalCertificate) formData.append("appraisalCertificate", appraisalCertificate);
        if (creditHistory) formData.append("creditHistory", creditHistory);
        if (deedOfTheFirstHome) formData.append("deedOfTheFirstHome", deedOfTheFirstHome);
        if (financialStatusOfTheBusiness) formData.append("financialStatusOfTheBusiness", financialStatusOfTheBusiness);
        if (businessPlan) formData.append("businessPlan", businessPlan);
        if (remodelingBudget) formData.append("remodelingBudget", remodelingBudget);
        if (updatedAppraisalCertificate) formData.append("updatedAppraisalCertificate", updatedAppraisalCertificate);
        
        creditService.create(formData)
    .then(response => {
        console.log("Crédito ha sido añadido.", response.data);
        navigate("/credit/list");
    })
    .catch(error => {
        console.error("Ha ocurrido un error al intentar crear nuevo crédito.", error);
        if (error.response) {
            // Muestra el mensaje de error del servidor
            console.error("Respuesta del servidor:", error.response.data);
        }
    });
    };

    useEffect(() => {
        if (id) {
            setTitleuserForm("Nuevo Crédito");
        }
        // Autocompletar las fechas para los ingresos mensuales
        const today = dayjs();
        const newMonthlyIncome = monthlyIncome.map((_, index) => {
            const date = today.subtract(index + 1, 'month').format('YYYY-MM-DD');
            return `${date} `;
        });
        setMonthlyIncome(newMonthlyIncome);
    }, [id]);
    
    return (
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" component="form">
            <h3> {titleuserForm} </h3>
            <hr />
            <form>
                {monthlyIncome.map((income, index) => (
                    <FormControl fullWidth key={index}>
                        <TextField
                            id={`monthlyIncome${index + 1}`}
                            label={`Monthly Income ${index + 1}`}
                            value={income.split(" ")[1]} // Solo mostrar la cantidad
                            variant="standard"
                            onChange={(e) => handleIncomeChange(index, e.target.value)}
                        />
                    </FormControl>
                ))}

                <FormControl fullWidth>
                    <TextField
                        id="requestedAmount"
                        label="Requested Amount"
                        value={requestedAmount}
                        variant="standard"
                        onChange={(e) => setRequestedAmount(e.target.value)}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="loanTerm"
                        label="Loan Term"
                        value={loanTerm}
                        variant="standard"
                        onChange={(e) => setLoanTerm(e.target.value)}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="annualInterestRate"
                        label="Annual Interest Rate"
                        value={annualInterestRate}
                        variant="standard"
                        onChange={(e) => setAnnualInterestRate(e.target.value)}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="creditsHistory"
                        label="Credits History"
                        value={creditsHistory}
                        variant="standard"
                        onChange={(e) => setCreditsHistory(e.target.value)}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="monthlyDebt"
                        label="Monthly Debt"
                        value={monthlyDebt}
                        variant="standard"
                        onChange={(e) => setMonthlyDebt(e.target.value)}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="propertyAmount"
                        label="Property Amount"
                        value={propertyAmount}
                        variant="standard"
                        onChange={(e) => setPropertyAmount(e.target.value)}
                    />
                </FormControl>
                
                <FormControl fullWidth>
                    <TextField
                        id="typeOfLoan"
                        label="Type Of Loan"
                        value={typeOfLoan}
                        variant="standard"
                        onChange={(e) => setTypeOfLoan(e.target.value)}
                    />
                </FormControl>

                {typeOfLoan === "Primera Vivienda" && (
                        <>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="proofOfIncome" style={{ marginRight: "20px", minWidth: "150px", textAlign: "left" }}>Proof of Income</label>
                                </Box>
                                <input type="file" id="proofOfIncome" onChange={(e) => setProofOfIncome(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="appraisalCertificate">Appraisal Certificate</label>
                                </Box>
                                <input type="file" id="appraisalCertificate" onChange={(e) => setAppraisalCertificate(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="creditHistory">Credit History</label>
                                </Box>
                                <input type="file" id="creditHistory" onChange={(e) => setCreditHistory(e.target.files[0])} />
                            </FormControl>
                        </>
                        )}

                        {typeOfLoan === "Segunda Vivienda" && (
                        <>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="proofOfIncome">Proof of Income</label>
                                </Box>
                                <input type="file" id="proofOfIncome" onChange={(e) => setProofOfIncome(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="appraisalCertificate">Appraisal Certificate</label>
                                </Box>
                                <input type="file" id="appraisalCertificate" onChange={(e) => setAppraisalCertificate(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="deedOfTheFirstHome">Deed of the First Home</label>
                                </Box>
                                <input type="file" id="deedOfTheFirstHome" onChange={(e) => setDeedOfTheFirstHome(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="creditHistory">Credit History</label>
                                </Box>
                                <input type="file" id="creditHistory" onChange={(e) => setCreditHistory(e.target.files[0])} />
                            </FormControl>
                        </>
                        )}

                        {typeOfLoan === "Propiedades Comerciales" && (
                        <>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="proofOfIncome">Proof of Income</label>
                                </Box>
                                <input type="file" id="proofOfIncome" onChange={(e) => setProofOfIncome(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="appraisalCertificate">Appraisal Certificate</label>
                                </Box>
                                <input type="file" id="appraisalCertificate" onChange={(e) => setAppraisalCertificate(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="financialStatusOfTheBusiness">Financial Status of the Business</label>
                                </Box>
                                <input type="file" id="financialStatusOfTheBusiness" onChange={(e) => setFinancialStatusOfTheBusiness(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="businessPlan">Business Plan</label>
                                </Box>
                                <input type="file" id="businessPlan" onChange={(e) => setBusinessPlan(e.target.files[0])} />
                            </FormControl>
                        </>
                        )}

                        {typeOfLoan === "Remodelación" && (
                        <>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="proofOfIncome">Proof of Income</label>
                                </Box>
                                <input type="file" id="proofOfIncome" onChange={(e) => setProofOfIncome(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="updatedAppraisalCertificate">Updated Appraisal Certificate</label>
                                </Box>
                                <input type="file" id="updatedAppraisalCertificate" onChange={(e) => setUpdatedAppraisalCertificate(e.target.files[0])} />
                            </FormControl>
                            <FormControl fullWidth>
                                <Box display="flex" alignItems="center" justifyContent="space-between" mb={2}>
                                    <label htmlFor="remodelingBudget">Remodeling Budget</label>
                                </Box>
                                <input type="file" id="remodelingBudget" onChange={(e) => setRemodelingBudget(e.target.files[0])} />
                            </FormControl>
                        </>
                        )}

                <FormControl fullWidth>
                    <Button variant="contained" color="info" onClick={(e) => saveuser(e)} style={{ marginLeft: "0.5rem" }} startIcon={<SaveIcon />}>
                        Grabar
                    </Button>
                </FormControl>
            </form>
            <hr />
            <Link to="/credit/list">Back to List</Link>
        </Box>
    );
};

export default AddCredit;