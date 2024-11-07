import loanAplicationServices from "../services/LoanAplication.service";
import simulationServices from "../services/Simulation.service";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button, Paper, Typography, Slider, MenuItem } from "@mui/material";
import CalculateIcon from "@mui/icons-material/Calculate";
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import SimulationService from "../services/Simulation.service";

// Función auxiliar para obtener las restricciones basadas en el tipo de préstamo
const getLoanRestrictions = (loanType) => {
    switch (loanType) {
        case "Primera vivienda":
            return { rateRange: { min: 3.5, max: 5.0 }, maxPercentage: 0.8, maxTerm: 30 };
        case "Segunda vivienda":
            return { rateRange: { min: 4.0, max: 6.0 }, maxPercentage: 0.7, maxTerm: 20 };
        case "Propiedades comerciales":
            return { rateRange: { min: 5.0, max: 7.0 }, maxPercentage: 0.60, maxTerm: 25 };
        case "Remodelacion":
            return { rateRange: { min: 4.5, max: 6.0 }, maxPercentage: 0.50, maxTerm: 15 };
        default:
            return { rateRange: { min: 0, max: 0 }, maxPercentage: 0, maxTerm: 0 };
    }
};



const SimulateLoan = () => {
    const [simulation, setSimulation] = useState(true);
    const [rutUser, setRutUser] = useState("");
    const [state, setState] = useState(""); // Estado de la aplicación (ej: E1, E2, ..., E7)
    const [amount, setAmount] = useState(0);
    const [anualInterestRate, setAnualInterestRate] = useState(0.0); // Porcentaje anual de interés
    const [term, setTerm] = useState(0); // Años del préstamo
    const [loan_type, setLoan_type] = useState("");
    const [fee, setFee] = useState(0.0); // Pago mensual
    const [creditInsurance, setCreditInsurance] = useState(0.0); // Seguro de crédito
    const [monthlyFireInsurance, setMonthlyFireInsurance] = useState(0.0); // Seguro contra incendios mensual
    const [administrationCommission, setAdministrationCommission] = useState(0.0); // Comisión de administración
    const [propertyValue, setPropertyValue] = useState(""); // Valor de la propiedad

    const [consistentSaveCheck, setConsistentSaveCheck] = useState(false); // R7: tiene ahorros consistentes
    const [periodicDepositsCheck, setPeriodicDepositsCheck] = useState(false); // R7: realiza depósitos periódicos
    const [recentWithdrawCheck, setRecentWithdrawCheck] = useState(false); // R7: no ha hecho retiros recientes grandes
    const [saveCapacity, setSaveCapacity] = useState(""); // Capacidad de ahorro: sólida, moderada o insuficiente

    const [incomeFile, setIncomeFile] = useState(null); // Archivo de ingresos
    const [certificadoAvaluo, setCertificadoAvaluo] = useState(null);
    const [historialCrediticio, setHistorialCrediticio] = useState(null);
    const [escrituraPrimeraVivienda, setEscrituraPrimeraVivienda] = useState(null);
    const [estadoFinancieroNegocio, setEstadoFinancieroNegocio] = useState(null);
    const [planNegocios, setPlanNegocios] = useState(null);
    const [presupuestoRemodelacion, setPresupuestoRemodelacion] = useState(null);
    const [certificadoAvaluoActualizado, setCertificadoAvaluoActualizado] = useState(null);

    const { id } = useParams();
    const navigate = useNavigate();

    // Obtener restricciones basadas en el tipo de préstamo seleccionado
    const { rateRange, maxPercentage, maxTerm } = getLoanRestrictions(loan_type);

    // Calcular el monto máximo de financiamiento
    const maxFinancingAmount = propertyValue ? (propertyValue * maxPercentage).toFixed(0) : 0;

    // Manejar el cambio en el monto solicitado 
    const handleAmountChange = (e) => {
        const value = e.target.value;
        if (value > maxFinancingAmount) {
            setAmount(maxFinancingAmount); // Si se excede, establece el máximo permitido
        } else {
            setAmount(value);
        }
    };
    
    // Manejar el cambio en el plazo
    const handleTermChange = (e) => {
        const value = e.target.value;
        if (value > maxTerm) {
            setTerm(maxTerm); // Si se excede, establece el máximo permitido
        } else {
            setTerm(value);
        }
    };
    

   
    // Calcular el monto mensual a pagar
    const loanCalculation = () => {
        const loanSimulation = {
            amount,
            term,
            anualInterestRate,
        };

        // Llamar al backend para la simulación del préstamo
        SimulationService.SimulateLoan(loanSimulation)
            
            .then((response) => {
                console.log(amount, term, anualInterestRate);
                console.log("Simulación realizada:", response.data);
                setFee(response.data); // Actualizar el estado con la cuota mensual
            })
            .catch((error) => {
                console.error("Error al calcular la simulación:", error);
            });
    };

    const saveLoanAplication = (e) => {
        e.preventDefault();
        const loanAplication = {
            id,
            simulation,
            rutUser,
            state,
            amount,
            anualInterestRate,
            term,
            loan_type,
            fee,
            creditInsurance,
            monthlyFireInsurance,
            administrationCommission,
            propertyValue,
            consistentSaveCheck,
            periodicDepositsCheck,
            recentWithdrawCheck,
            saveCapacity,
            incomeFile,
            certificadoAvaluo,
            historialCrediticio,
            escrituraPrimeraVivienda,
            estadoFinancieroNegocio,
            planNegocios,
            presupuestoRemodelacion,
            certificadoAvaluoActualizado,

        };

        loanAplicationServices.create(loanAplication)
            .then((response) => {
                console.log("Simulación creada", response.data);
                navigate(`/aplication/${response.data.id}`); // Redirigir usando el id de la respuesta
            })
            .catch((error) => {
                console.log("Ocurrió un error al crear la simulación", error);
            });
    };

    useEffect(() => {
        console.log("Simulación cargada");
    }, []);

    return (
        <Box display="flex" justifyContent="space-between" p={2}>
            {/* Parte izquierda: Formulario */}
            <Box width="50%" pr={2}>
                <Typography variant="h4">Simular Préstamo</Typography>
                <hr />
                <form>
                    
                    <FormControl fullWidth>
                        <TextField
                            id="category"
                            label="Categoria"
                            value={loan_type}
                            select
                            variant="standard"
                            onChange={(e) => setLoan_type(e.target.value)}
                        >
                            <MenuItem value="Primera vivienda">Primera vivienda</MenuItem>
                            <MenuItem value="Segunda vivienda">Segunda vivienda</MenuItem>
                            <MenuItem value="Propiedades comerciales">Propiedades comerciales</MenuItem>
                            <MenuItem value="Remodelacion">Remodelación</MenuItem>
                        </TextField>
                    </FormControl>

                    <FormControl fullWidth>
                        <TextField
                            label="Valor de la propiedad"
                            type="number"
                            value={propertyValue}
                            onChange={(e) => setPropertyValue(e.target.value)}
                        />
                    </FormControl>

                    <FormControl fullWidth>
                        <TextField
                            label="Monto solicitado"
                            type="number"
                            value={amount}
                            onChange={handleAmountChange} // Llama a la función de validación
                            helperText={`El monto máximo permitido es ${maxFinancingAmount}`}
                            inputProps={{ max: maxFinancingAmount }}
                            disabled={!propertyValue} // Deshabilitar si no hay valor de la propiedad
                        />
                    </FormControl>


                    <FormControl fullWidth>
                        <Typography gutterBottom>Tasa de Interés ({anualInterestRate}%)</Typography>
                        <Slider
                            value={Number(anualInterestRate)}
                            min={rateRange.min}
                            max={rateRange.max}
                            step={0.1}
                            onChange={(e, newValue) => setAnualInterestRate(newValue)}
                            valueLabelDisplay="auto"
                            marks={[
                                { value: rateRange.min, label: `${rateRange.min}%` },
                                { value: rateRange.max, label: `${rateRange.max}%` },
                            ]}
                        />
                    </FormControl>

                    <FormControl fullWidth>
                        <TextField
                            label="Plazo (años)"
                            type="number"
                            value={term}
                            onChange={handleTermChange} // Llama a la función de validación
                            helperText={`El plazo máximo permitido es ${maxTerm} años`}
                            inputProps={{ max: maxTerm }}
                        />
                    </FormControl>


                    <FormControl>
                        <Button
                            variant="contained"
                            color="info"
                            startIcon={<CalculateIcon />}
                            onClick={(e) => {
                                if (amount > maxFinancingAmount || term > maxTerm || !propertyValue) {
                                    alert("Verifica los valores ingresados, algunos no cumplen con las restricciones");
                                } else {
                                    loanCalculation(); // Llama a la función si los valores son válidos
                                
                                }
                            }}
                            >
                            Simular Credito
                        </Button>
                    </FormControl>
                </form>
            </Box>

            <Box width="40%" pl={2}>
                <Paper elevation={3} style={{ padding: "20px", textAlign: "center" }}>
                    <Typography variant="h5">Resultado de la Simulación</Typography>
                    <hr />
                    <Typography variant="h6" color="primary">
                        Monto solicitado: ${amount}
                    </Typography>
                    <Typography variant="h6" color="primary">
                        Tasa de interés: {anualInterestRate}%
                    </Typography>
                    <Typography variant="h6" color="primary">
                        Plazo: {term} años
                    </Typography>
                    <Typography variant="h6" color="secondary">
                        Cuota Mensual: (${fee.toFixed(0)})
                    </Typography>
                </Paper>

                <Button
                    variant="contained"
                    color="info"
                    startIcon={<AttachMoneyIcon />}
                    onClick={(e)=>{
                        saveLoanAplication(e);}} //Cambiar a solicitar
                    >
                    Solicitar Credito
                </Button>
            </Box>
        </Box>
    );
};

export default SimulateLoan;
