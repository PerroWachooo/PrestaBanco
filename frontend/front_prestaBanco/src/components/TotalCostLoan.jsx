import React, { useState } from 'react';
import LoanAplicationService from '../services/LoanAplication.service';
import { Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";


const TotalCostLoan = () => {
    const [id, setId] = useState('');
    const [totalCost, setTotalCost] = useState(null);
    const [error, setError] = useState(null);
    const [creditInsurance, setCreditInsurance] = useState(null);
    const [fireInsurance, setFireInsurance] = useState(null);
    const [administrationFee, setAdministrationFee] = useState(null);
    const [amount, setAmount] = useState(null);

    const handleInputChange = (e) => {
        setId(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await LoanAplicationService.calculateTotalCost(id);
            const loanResponse = await LoanAplicationService.getLoanAplicationById(id);
            setTotalCost(response.data);
            setCreditInsurance(loanResponse.data.creditInsuarance);
            setFireInsurance(loanResponse.data.monthlyFireInsurance);
            setAdministrationFee(loanResponse.data.administrationCommission);
            setAmount(loanResponse.data.amount);
            console.log(loanResponse.data);
            console.log(response.data.creditInsurance);
            setError(null);
        } catch (err) {
            setError('Error fetching total cost');
            setTotalCost(null);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <div style={{ marginRight: '200px' }}>
                <h2>Calculo de costo total</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="loanId">ID de la solicitud:</label>
                        <input
                            type="text"
                            id="loanId"
                            value={id}
                            onChange={handleInputChange}
                        />
                    </div>
                    <Button type="submit" variant="contained" color="info" style={{ marginLeft: "0.5rem" }} startIcon={<SaveIcon />}
                    >Calcular</Button>
                </form>
                {totalCost !== null && (
                    <div>
                        <h3>Total Costo Total: {totalCost.toFixed(2)}</h3>
                    </div>
                )}
                {error && (
                    <div>
                        <h3>{error}</h3>
                    </div>
                )}
            </div>
            <div>
                {amount !== null && (
                    <div>
                        <h3>Detalles del Préstamo</h3>
                        <p>Monto: {amount}</p>
                        <p>Comisión por manejo: {administrationFee.toFixed(2)}</p>
                        <p>Seguro de incendio: {fireInsurance.toFixed(0)}</p>
                        <p>Seguro de crédito: {creditInsurance.toFixed(2)}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default TotalCostLoan;
