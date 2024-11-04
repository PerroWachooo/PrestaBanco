import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import loanaplicationservices from "../services/LoanAplication.service";
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

const LoanAplicationList = () => {
    const [loans, setLoans] = useState([]);
    const [filteredLoans, setFilteredLoans] = useState([]);
    const [searchRut, setSearchRut] = useState("");

    const navigate = useNavigate();

    const init = () => {
        loanaplicationservices.getAllLoanAplications().then((response) => {
            console.log("Mostrando listado de las aplicaciones de préstamo");
            setLoans(response.data);
            setFilteredLoans(response.data);
        })
        .catch((error) => {
            console.log("Ocurrió un error al cargar las aplicaciones de préstamo", error);
        });
    };

    useEffect(() => {
        init();
    }, []);

    const handleSearch = () => {
        if (searchRut) {
            const filtered = loans.filter(loan => loan.rutUser === searchRut);
            setFilteredLoans(filtered);
            console.log("Imprimiendo filtered", filtered);
            console.log("Mostrando rut:", searchRut);
        } else {
            setFilteredLoans(loans);
            console.log("No filtra", loans);
        }
    };

    const handleDelete = (id) => {
        console.log("Imprimiendo id", id);
        const confirm = window.confirm("¿Estás seguro de eliminar esta aplicación de préstamo?");
        if(confirm){
            loanaplicationservices.deleteLoanAplication(id).then((response) => {
                console.log("Aplicación de préstamo eliminada", response.data);
                init();
            })
            .catch((error) => {
                console.log("Ocurrió un error al eliminar la aplicación de préstamo", error);
            });
        }
    };

    const handleEdit = (id) => {
        console.log("Imprimiendo id", id);
        navigate(`/aplication/${id}`);
    }

    useEffect(() => {
        console.log("Filtered loans updated", filteredLoans);
    }, [filteredLoans]);

    return (
        <TableContainer component={Paper}>
          <br />
          <input
            type="text"
            value={searchRut}
            onChange={(e) => setSearchRut(e.target.value)}
            placeholder="Buscar por Rut"
          />
          <Button variant="contained" color="primary" onClick={handleSearch}>
            Buscar
          </Button>
          <br /> <br />
          <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  ID
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Rut Cliente
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Monto
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Plazo
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Estado
                </TableCell>
                <TableCell align="center" sx={{ fontWeight: "bold" }}>
                  Acciones
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredLoans.map((loan) => (
                <TableRow
                  key={loan.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell align="left">{loan.id}</TableCell>
                  <TableCell align="left">{loan.rutUser}</TableCell>
                  <TableCell align="right">{loan.amount}</TableCell>
                  <TableCell align="right">{loan.term}</TableCell>
                  <TableCell align="right">{loan.state}</TableCell>

                  <TableCell align="center">
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(loan.id)}
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<EditIcon />}
                    >
                      Editar
                    </Button>
    
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(loan.id)}
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

    export default LoanAplicationList;
