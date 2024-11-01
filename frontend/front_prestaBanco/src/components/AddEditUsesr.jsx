import userservices from "../services/User.service";
import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import { Select, MenuItem, InputLabel } from "@mui/material";
import Typography from "@mui/material/Typography";

const AddEditUser = () => {
    const [rut, setRut] = useState("");
    const [password, setPassword] = useState("");
    const [mail, setMail] = useState("");
    const [name, setName] = useState("");
    const [age, setAge] = useState("");
    const [state_hist, setState_hist] = useState("");
    const [work, setWork] = useState("");
    const [years_working, setYears_working] = useState("");
    const [income, setIncome] = useState("");
    const [debt, setDebt] = useState("");
    const [yearsAccount, setYearsAccount] = useState("");
    const [balanceAccount, setBalanceAccount] = useState("");
    const { id } = useParams();
    const [titleUserForm, setTitleUserForm] = useState("Crear Usuario");
    const navigate = useNavigate();

    const saveUser = (e) => {
        e.preventDefault();
        const user = {
            id,
            rut,
            password,
            mail,
            name,
            age,
            state_hist,
            work,
            years_working,
            income,
            debt,
            yearsAccount,
            balanceAccount,
        };

        if(id){
            userservices.updateUser(user).then((response) => {
                console.log("Usuario actualizado",response.data);
                navigate("/user/list"); //Cambiar a user list
            })
            .catch((error) => {
                console.log("Ocurrio un error al actualizar el usuario", error);
            });
            //Actualizar Datos Empelado


        }else{
            //Crear Usuario
            userservices.registerUser(user).then((response) => {
                console.log("Usuario creado", response.data);
                navigate("/home"); //Cambiar a user list
            })
            .catch((error) => {
                console.log("Ocurrio un error al crear el usuario", error);
            });
        }
    }

        


    useEffect(() => {
        if(id){
            setTitleUserForm("Editar Usuario");
            userservices.getUserById(id).then((response) => {
                const user = response.data;
                setRut(user.rut);
                setPassword(user.password);
                setMail(user.mail);
                setName(user.name);
                setAge(user.age);
                setState_hist(user.state_hist);
                setWork(user.work);
                setYears_working(user.years_working);
                setIncome(user.income);
                setDebt(user.debt);
                setYearsAccount(user.yearsAccount);
                setBalanceAccount(user.balanceAccount);
            })
            .catch((error) => {
                console.log("Ocurrio un error al obtener el usuario", error);
            });
        } else {
            setTitleUserForm("Crear Usuario");
        }
    }, []);
        
    return (
        <Box>
            <Typography variant="h4">{titleUserForm}</Typography>
            <hr />
            <form>
                <FormControl>
                    <TextField
                        label="Rut"
                        value={rut|| ""}
                        onChange={(e) => setRut(e.target.value)}
                    />
                    <TextField
                        label="Contraseña"
                        value={password|| ""}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <TextField
                        label="Correo"
                        value={mail|| ""}
                        onChange={(e) => setMail(e.target.value)}
                    />
                    <TextField
                        label="Nombre"
                        value={name|| ""}
                        onChange={(e) => setName(e.target.value)}
                    />
                    <TextField
                        label="Edad"
                        value={age|| ""}
                        onChange={(e) => setAge(e.target.value)}
                    />
                    <TextField
                        label="Estado Historico"
                        value={state_hist|| ""}
                        onChange={(e) => setState_hist(e.target.value)}
                    />
                    <TextField
                        label="Trabajo"
                        value={work|| ""}
                        onChange={(e) => setWork(e.target.value)}
                    />
                    <TextField
                        label="Años trabajando"
                        value={years_working|| ""}
                        onChange={(e) => setYears_working(e.target.value)}
                    />
                    <TextField
                        label="Ingresos"
                        value={income|| ""}
                        onChange={(e) => setIncome(e.target.value)}
                    />
                    <TextField
                        label="Deuda"
                        value={debt|| ""}
                        onChange={(e) => setDebt(e.target.value)}
                    />
                    <TextField
                        label="Años en cuenta"
                        value={yearsAccount|| ""}
                        onChange={(e) => setYearsAccount(e.target.value)}
                    />
                    <TextField
                        label="Saldo"
                        value={balanceAccount|| ""}
                        onChange={(e) => setBalanceAccount(e.target.value)}
                    />
                    <Button
                        variant="contained"
                        color="info"
                        startIcon={<SaveIcon />}
                        onClick={(e) => saveUser(e)}
                        style = {{marginLeft: "0.5rem"}}
                    >
                        Guardar
                    </Button>
                </FormControl>
            </form>
        </Box>
    );
    
};

export default AddEditUser;