import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const AddEdituser = () => {
  const [identifyingDocument, setIdentifyingDocument] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [jobSeniority, setJobSeniority] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const [file, setFile] = useState(null);

  const { id } = useParams();
  const [titleuserForm, setTitleuserForm] = useState("");
  const navigate = useNavigate();

  const saveuser = (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("identifyingDocument", identifyingDocument);
    formData.append("name", name);
    formData.append("email", email);
    formData.append("password", password);
    formData.append("jobSeniority", jobSeniority);
    formData.append("birthdate", birthdate);

    if (file) {
      formData.append("file", file);
    }

    if (id) {
      userService
        .update(id, formData)
        .then((response) => {
          console.log("Empleado ha sido actualizado.", response.data);
          navigate("/user/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos del empleado.",
            error
          );
        });
    } else {
      userService
        .create(formData)
        .then((response) => {
          console.log("Usuario ha sido añadido.", response.data);
          navigate("/user/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar crear nuevo usuario.",
            error
          );
        });
    }
  };

  useEffect(() => {
    if (id) {
      setTitleuserForm("Editar Empleado");
      userService
        .get(id)
        .then((user) => {
          setIdentifyingDocument(user.data.identifyingDocument);
          setName(user.data.name);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleuserForm("Nuevo Empleado");
    }
  }, [id]);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <h3> {titleuserForm} </h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="identifyingDocument"
            label="Rut"
            value={identifyingDocument}
            variant="standard"
            onChange={(e) => setIdentifyingDocument(e.target.value)}
            helperText="Ej. 12.587.698-8"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="name"
            label="Name"
            value={name}
            variant="standard"
            onChange={(e) => setName(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="email"
            label="Email"
            value={email}
            variant="standard"
            onChange={(e) => setEmail(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="Password"
            label="Password"
            value={password}
            variant="standard"
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="jobSeniority"
            label="Job Seniority"
            value={jobSeniority}
            variant="standard"
            onChange={(e) => setJobSeniority(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="birthdate"
            label="Birthdate"
            value={birthdate}
            variant="standard"
            onChange={(e) => setBirthdate(e.target.value)}
          />
        </FormControl>

        <FormControl fullWidth>
          <input
             type="file"
             id="file"
             onChange={(e) => setFile(e.target.files[0])} // Captura el archivo
          />
        </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveuser(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Grabar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/user/list">Back to List</Link>
    </Box>
  );
};

export default AddEdituser;
