import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const SimulationCredit = () => {
  const [amount, setamount] = useState("");
  const [term, setterm] = useState("");
  const [interestRate, setinterestRate] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setResult(null);

    try {
        const response = await userService.simulation({
            amount,
            term,
            interestRate,
        });

        setResult(response.data);
    } catch (err) {
        setError('Error al simular el préstamo.');
    }
};

  useEffect(() => {
  }, []);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="amount"
            label="amount"
            value={amount}
            variant="standard"
            onChange={(e) => setamount(e.target.value)}
            helperText="Ej. 12.587.698-8"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="term"
            label="term"
            value={term}
            variant="standard"
            onChange={(e) => setterm(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="interestRate"
            label="interestRate"
            value={interestRate}
            variant="standard"
            onChange={(e) => setinterestRate(e.target.value)}
          />
        </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => handleSubmit(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
        {result !== null && (
                <div className="alert alert-success mt-3">
                    Cuota mensual de su prestamo: {result}
                </div>
            )}

            {error && (
                <div className="alert alert-danger mt-3">
                    {error}
                </div>
            )}
      </form>
      <hr />
      <Link to="/user/list">Back to List</Link>
    </Box>
  );
};

export default SimulationCredit;