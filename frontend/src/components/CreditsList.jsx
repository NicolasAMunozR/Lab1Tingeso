import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import creditService from "../services/credit.service";
import userService from "../services/user.service"; 
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";


const CreditsList = () => {
  const [credits, setCredits] = useState([]);
  const [users, setUsers] = useState({}); 
  const { id } = useParams(); // Asegúrate de que el `id` sea el correcto para la consulta
  const navigate = useNavigate();

  // Función para obtener créditos por usuario
  const init = () => {
    creditService
      .getcredits(id) 
      .then((response) => {
        console.log("Mostrando listado de créditos del usuario.", response.data);
        setCredits(response.data); // Guardamos los créditos en el estado

        // Obtener la información de los usuarios para cada crédito
        response.data.forEach((creditItem) => {
          if (!users[creditItem.userId]) {  // Si no tenemos ya el usuario, hacer la consulta
            userService.getUserById(creditItem.userId) // Llama a un servicio que devuelva un usuario por ID
              .then((userResponse) => {
                console.log("Mostrando datos del usuario.", userResponse.data);
                setUsers((prevUsers) => ({
                  ...prevUsers,
                  [creditItem.userId]: userResponse.data,  // Guardamos el usuario en el estado
                  console
                }));
              })
              .catch((error) => {
                console.log("Error al obtener datos del usuario", error);
              });
          }
        });
      })
      .catch((error) => {
        console.log("Error al mostrar créditos.", error);
      });
  };
  useEffect(() => {
    init();
  }, [id]);


  const handleCancel = (id) => {
    console.log("Cancelar crédito con id:", id);
    const confirmDelete = window.confirm("¿Está seguro que desea cancelar este crédito?");
    if (confirmDelete) {
      creditService
        .rejectTerms(id) // Llamamos al método remove de creditService
        .then((response) => {
          console.log("Crédito cancelado", response.data);
          init(); // Recargamos la lista después de eliminar
        })
        .catch((error) => {
          console.log("Error al cancelar crédito", error);
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Editando crédito con id:", id);
    navigate(`/Credit/edit/${id}`); // Navegar a la página de edición
  };

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Rut</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Nombre</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Correo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Estado</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo de prestamo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Plazo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Cantidad</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tasa de interes</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {credits.map((creditItem) => (
            <TableRow key={creditItem.id}>
              <TableCell align="left">{users[creditItem.userId] ? users[creditItem.userId].identifyingDocument : "Cargando..."}</TableCell>
              <TableCell align="left">{users[creditItem.userId] ? users[creditItem.userId].name : "Cargando..."}</TableCell>
              <TableCell align="left">{users[creditItem.userId] ? users[creditItem.userId].email : "Cargando..."}</TableCell>
              <TableCell align="left">{creditItem.userId ? creditItem.applicationStatus : "Cargando..."}</TableCell>
              <TableCell align="left">{creditItem.userId ? creditItem.typeOfLoan : "Cargando..."}</TableCell>
              <TableCell align="left">{creditItem.userId ? creditItem.loanTerm : "Cargando..."}</TableCell>
              <TableCell align="left">{creditItem.userId ? creditItem.requestedAmount : "Cargando..."}</TableCell>
              <TableCell align="left">{creditItem.userId ? creditItem.annualInterestRate : "Cargando..."}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(creditItem.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleCancel(creditItem.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<DeleteIcon />}
                >
                  Cancelar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CreditsList;