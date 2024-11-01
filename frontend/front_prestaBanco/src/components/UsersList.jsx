import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userservices from "../services/User.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const UsersList = () => {
    const [users, SetUsers] = useState([]);

    const navigate = useNavigate();

    const init = () => {
        userservices.getAllUsers().then((response) => {
            console.log("Mostrando listado de los usuarios");
            SetUsers(response.data);
        })
        .catch((error) => {
            console.log("Ocurrio un error al cargar los usuarios", error);
        });
    };


    useEffect(() => {
        init();
    }, []);

    const handleDelete = (id) => {
        console.log("Imprimiendo id", id);
        const confirm = window.confirm("¿Estas seguro de eliminar este usuario?");
        if(confirm){
            userservices.deleteUser(id).then((response) => {
                console.log("Usuario eliminado", response.data);
                init();
            })
            .catch((error) => {
                console.log("Ocurrio un error al eliminar el usuario", error);
            });
        }
    };

    const handleEdit = (id) => {
        console.log("Imprimiendo id", id);
        navigate(`/user/register/${id}`);
    }

    return (
        <TableContainer component={Paper}>
          <br />
          <Link
            to="/user/register"
            style={{ textDecoration: "none", marginBottom: "1rem" }}
          >
            <Button
              variant="contained"
              color="primary"
              startIcon={<PersonAddIcon />}
            >
              Añadir Usuario
            </Button>
          </Link>
          <br /> <br />
          <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Rut
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Nombre
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Trabajo
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Años en el trabajo
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Deuda
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Saldo de Cuenta
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {users.map((user) => (
                <TableRow
                  key={user.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell align="left">{user.rut}</TableCell>
                  <TableCell align="left">{user.name}</TableCell>
                  <TableCell align="right">{user.work}</TableCell>
                  <TableCell align="right">{user.years_working}</TableCell>
                  <TableCell align="right">{user.debt}</TableCell>
                  <TableCell align="right">{user.balanceAccount}</TableCell>

                  <TableCell>
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(user.id)}
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<EditIcon />}
                    >
                      Editar
                    </Button>
    
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(user.id)}
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<DeleteIcon />}
                    >
                      Eliminar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      );
    };

    export default UsersList;




