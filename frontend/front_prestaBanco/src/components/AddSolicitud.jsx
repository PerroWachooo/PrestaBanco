import LoanAplicationService from "../services/LoanAplication.service";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import Typography from "@mui/material/Typography";
import { Select, MenuItem, InputLabel, Slider} from "@mui/material";



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

const AddSolicitud = () => {
    const [simulation, setSimulation] = useState(false);
    const [rutUser, setRutUser] = useState("");
    const [state, setState] = useState(""); // Estado de la aplicación (ej: E1, E2, ..., E7)
    const [amount, setAmount] = useState(0);
    const [anualInterestRate, setAnualInterestRate] = useState(0.0); // Porcentaje anual de interés
    const [term, setTerm] = useState(0); // Años del préstamo
    const [loan_type, setLoan_type] = useState("");
    const [fee, setFee] = useState(0.0); // Pago mensual
    const [creditInsurance, setCreditInsurance] = useState(0.0); // Seguro de crédito
    const [monthlyFireInsurance, setMonthlyFireInsurance] = useState(0); // Seguro contra incendios mensual
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
    const [formDisabled, setFormDisabled] = useState(false);
    const [formDisabledRut, setFormDisabledRut] = useState(false);

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

     const isFormValid = () => {
        const baseValidation = rutUser && state && amount > 0 && anualInterestRate > 0 &&
                              term > 0 && loan_type && fee > 0 && propertyValue > 0 && incomeFile;
        
        const loanTypeValidation = (loan_type === "Primera vivienda" && certificadoAvaluo && historialCrediticio) ||
                                   (loan_type === "Segunda vivienda" && escrituraPrimeraVivienda && historialCrediticio && certificadoAvaluo) ||
                                   (loan_type === "Remodelacion" && certificadoAvaluoActualizado && presupuestoRemodelacion) ||
                                   (loan_type === "Propiedades comerciales" && certificadoAvaluo && estadoFinancieroNegocio && planNegocios);
        
        return baseValidation && loanTypeValidation;
    };

     const feeCalculation = () => {
        const feeData = {
            amount,
            term,
            anualInterestRate
        };
        LoanAplicationService.calculateFee(feeData)
        .then((response) => {
            console.log("Cuota calculada", response.data);
            setFee(response.data);
        })
        .catch((error) => {
            console.error("Ocurrio un error al calcular la cuota", error);
        });
    };


    const saveLoanAplication = (e) => {
        e.preventDefault();

        setState("E3");
        
        if (!isFormValid()) {
            alert("Por favor complete todos los campos obligatorios");
            return;
        }

        
        setAdministrationCommission(0.01 * amount);
        setCreditInsurance(0.0003 * amount);
        setMonthlyFireInsurance(20000);

        const formData = new FormData();
        formData.append("id", id);
        formData.append("simulation", simulation);
        formData.append("rutUser", rutUser);
        formData.append("state", state);
        formData.append("amount", amount);
        formData.append("anualInterestRate", anualInterestRate);
        formData.append("term", term);
        formData.append("loan_type", loan_type);
        formData.append("fee", fee);
        formData.append("creditInsurance", creditInsurance);
        formData.append("monthlyFireInsurance", monthlyFireInsurance);
        formData.append("administrationCommission", administrationCommission);
        formData.append("propertyValue", propertyValue);
        formData.append("consistentSaveCheck", consistentSaveCheck);
        formData.append("periodicDepositsCheck", periodicDepositsCheck);
        formData.append("recentWithdrawCheck", recentWithdrawCheck);
        formData.append("saveCapacity", saveCapacity);
        
        // Añadir archivos PDF si fueron seleccionados
        if (incomeFile) formData.append("income_file", incomeFile);
        if (certificadoAvaluo) formData.append("certificadoAvaluo", certificadoAvaluo);
        if (historialCrediticio) formData.append("historialCrediticio", historialCrediticio);
        if (escrituraPrimeraVivienda) formData.append("escrituraPrimeraVivienda", escrituraPrimeraVivienda);
        if (estadoFinancieroNegocio) formData.append("estadoFinancieroNegocio", estadoFinancieroNegocio);
        if (planNegocios) formData.append("planNegocios", planNegocios);
        if (presupuestoRemodelacion) formData.append("presupuestoRemodelacion", presupuestoRemodelacion);
        if (certificadoAvaluoActualizado) formData.append("certificadoAvaluoActualizado", certificadoAvaluoActualizado);
    

        

        if(id){ 
            LoanAplicationService.update(formData,id).then((response) => {
                console.log("Solicitud actualizada",response.data);
                navigate("/aplication/list"); //Cambiar a solicitud list
            })
        
            .catch((error) => {
                console.log("Ocurrio un error al actualizar la solicitud", error);
            });

        }else{
            LoanAplicationService.create(formData).then((response) => {
                console.log("Solicitud creada", response.data);
                navigate("/aplication/list"); //Cambiar a solicitud list
            })
            .catch((error) => {
                console.log("Ocurrio un error al crear la solicitud", error);
        });
        
    }
}

    useEffect(() => {
        if(id){
            LoanAplicationService.getLoanAplicationById(id).then((response) => {
                const loanAplication = response.data;
                setRutUser(loanAplication.rutUser);
                setPropertyValue(loanAplication.propertyValue);
                setAmount(loanAplication.amount);
                setAnualInterestRate(loanAplication.anualInterestRate);
                setTerm(loanAplication.term);
                setLoan_type(loanAplication.loan_type);
                setFee(loanAplication.fee);
                // Disable form fields when id is present
                setFormDisabled(true);
            })
            .catch((error) => {
                console.log("Ocurrio un error al obtener la solicitud", error);
            });
        }
        if(rutUser !== ""){
            setFormDisabledRut(true);
        }

        if (amount && anualInterestRate && term) {
            feeCalculation();
        }

    }, [amount, anualInterestRate, term]);

    return(
        <Box>
            <Typography variant="h4">Solicitud de Préstamo</Typography>
            <hr />
            <form>
                <FormControl>
                    <TextField
                    label="Rut Usuario"
                    value={rutUser}
                    onChange={(e) => setRutUser(e.target.value)}
                    error={!rutUser}
                    disabled={formDisabledRut}
                    />
                
                    <TextField
                        id="category"
                        label="Categoria"
                        value={loan_type}
                        select
                        variant="standard"
                        onChange={(e) => setLoan_type(e.target.value)}
                        error={!loan_type && !isFormValid()}
                        disabled={formDisabled}
                    >
                        <MenuItem value="Primera vivienda">Primera vivienda</MenuItem>
                        <MenuItem value="Segunda vivienda">Segunda vivienda</MenuItem>
                        <MenuItem value="Propiedades comerciales">Propiedades comerciales</MenuItem>
                        <MenuItem value="Remodelacion">Remodelación</MenuItem>
                    </TextField>
                
                <TextField
                    label="Valor de la propiedad"
                    type="number"
                    value={propertyValue}
                    onChange={(e) => setPropertyValue(e.target.value)}
                    error={!propertyValue && !isFormValid()}
                    disabled={formDisabled}
                />
                
                <TextField
                    label="Monto solicitado"
                    type="number"
                    value={amount}
                    onChange={handleAmountChange} // Llama a la función de validación
                    helperText={`El monto máximo permitido es ${maxFinancingAmount}`}
                    inputProps={{ max: maxFinancingAmount }}
                    disabled={!propertyValue || formDisabled} // Deshabilitar si no hay valor de la propiedad o si formDisabled es true
                    error={amount <= 0 && !isFormValid()}
                />
                
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
                    disabled={formDisabled}
                />
                
                <TextField
                    label="Plazo (años)"
                    type="number"
                    value={term}
                    onChange={handleTermChange} // Llama a la función de validación
                    helperText={`El plazo máximo permitido es ${maxTerm} años`}
                    inputProps={{ max: maxTerm }}
                    error={term <= 0 && !isFormValid()}
                    disabled={formDisabled}
                />
                
                <TextField
                    label="Cuota Mensual"
                    type="number"
                    value={fee.toFixed(0)}
                    disabled={!amount || !anualInterestRate || !term || formDisabled}
                    InputProps={{
                        readOnly: true,
                    }}
                    error={fee <= 0 && !isFormValid()}
                />

                <FormControl fullWidth>
                    <Typography gutterBottom>Subir archivos</Typography>
                    
                    <label>Comprobante de ingresos</label>
                    <input type="file" accept="application/pdf" onChange={(e) => setIncomeFile(e.target.files[0])} required/>

                    {loan_type !== "Remodelacion" && (
                    <>
                        <label>Certificado Avaluo</label>
                        <input 
                            type="file" 
                            accept="application/pdf" 
                            onChange={(e) => setCertificadoAvaluo(e.target.files[0])} 
                        />
                    </>
                )}

                    {loan_type !== "Propiedades comerciales" && loan_type !== "Remodelacion" && (
                        <>
                            <label>Historial Crediticio</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setHistorialCrediticio(e.target.files[0])}
                                />
                        </>
                )}

                    {["Segunda vivienda"].includes(loan_type) && (
                        <>
                            <label>Escritura Primera Vivienda</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setEscrituraPrimeraVivienda(e.target.files[0])}
                            />
                        </>
                )}

                    {["Propiedades comerciales"].includes(loan_type) && (
                        <>
                            <label>Estado Financiero Negocio</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setEstadoFinancieroNegocio(e.target.files[0])}
                            />
                        </>
                )}

                    {["Propiedades comerciales"].includes(loan_type) && (
                        <>
                            <label>Plan de Negocios</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setPlanNegocios(e.target.files[0])}
                            />
                        </>
                )}

                    {["Remodelacion"].includes(loan_type) && (
                        <>
                            <label>Presupuesto Remodelación</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setPresupuestoRemodelacion(e.target.files[0])}
                            />
                        </>
                )}

                    {["Remodelacion"].includes(loan_type) && (
                        <>
                            <label>Certificado Avaluo Actualizado</label>
                            <input
                                type="file"
                                accept="application/pdf"
                                onChange={(e) => setCertificadoAvaluoActualizado(e.target.files[0])}
                            />
                        </>
                )}

                </FormControl>
 
                <Button
                    variant="contained"
                    color="info"
                    startIcon={<SaveIcon />}
                    onClick={(e) => saveLoanAplication(e)}
                    
                    style={{ marginLeft: "0.5rem" }}
                >
                    Solicitar
                </Button>        
                </FormControl>
            </form>
        </Box>
    );

};


export default AddSolicitud;