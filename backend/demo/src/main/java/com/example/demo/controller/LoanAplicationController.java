package com.example.demo.controller;

import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.entities.SimulationLoanEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.LoanAplicationService;
import com.example.demo.services.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/loanaplication")
@CrossOrigin("*")

public class LoanAplicationController {
    @Autowired
    LoanAplicationService loanAplicationService;
    @Autowired
    SimulationService simulationService;

    @GetMapping("/")
    public ResponseEntity<List<LoanAplicactionEntity>> listAllAplications() {
        List<LoanAplicactionEntity> loansAplications = loanAplicationService.getLoans();
        return ResponseEntity.ok(loansAplications);
    }

    @GetMapping("/by-user/{rut}")
    public ResponseEntity<List<LoanAplicactionEntity>> listAplicationsByRut(@PathVariable String rut) {
        List<LoanAplicactionEntity> loansAplications = loanAplicationService.getLoansByRut(rut);
        return ResponseEntity.ok(loansAplications);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<LoanAplicactionEntity> getLoanAplicationById(@PathVariable Long id) {
        LoanAplicactionEntity loan = loanAplicationService.getLoanAplicationById(id)
                .orElseThrow(() -> new ResourceAccessException("Loan application not found"));
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/")
    public ResponseEntity<LoanAplicactionEntity> registerLoanAplication(
            @RequestParam(name = "rutUser") String rutUser,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "amount") int amount,
            @RequestParam(name = "anualInterestRate") float anualInterestRate,
            @RequestParam(name = "term") Integer term,
            @RequestParam(name = "loan_type") String loan_type,
            @RequestParam(name = "fee") Double fee,
            @RequestParam(name = "creditInsurance", required = false) Double creditInsurance,
            @RequestParam(name = "monthlyFireInsurance", required = false) Double monthlyFireInsurance,
            @RequestParam(name = "administrationCommission", required = false) Double administrationCommission,
            @RequestParam(name = "propertyValue", required = false) Double propertyValue,
            @RequestParam(name = "consistentSaveCheck", required = false) Boolean consistentSaveCheck,
            @RequestParam(name = "periodicDepositsCheck", required = false) Boolean periodicDepositsCheck,
            @RequestParam(name = "recentWithdrawCheck", required = false) Boolean recentWithdrawCheck,
            @RequestParam(name = "saveCapacity", required = false) String saveCapacity,
            @RequestParam(name = "income_file", required = false) MultipartFile income_file,
            @RequestParam(name = "certificadoAvaluo", required = false) MultipartFile certificadoAvaluo,
            @RequestParam(name = "historialCrediticio", required = false) MultipartFile historialCrediticio,
            @RequestParam(name = "escrituraPrimeraVivienda", required = false) MultipartFile escrituraPrimeraVivienda,
            @RequestParam(name = "estadoFinancieroNegocio", required = false) MultipartFile estadoFinancieroNegocio,
            @RequestParam(name = "planNegocios", required = false) MultipartFile planNegocios,
            @RequestParam(name = "presupuestoRemodelacion", required = false) MultipartFile presupuestoRemodelacion,
            @RequestParam(name = "certificadoAvaluoActualizado", required = false) MultipartFile certificadoAvaluoActualizado) {

        try {
            // Crear una nueva instancia de LoanAplicactionEntity y asignar los campos
            LoanAplicactionEntity loanAplicaction = new LoanAplicactionEntity();
            loanAplicaction.setRutUser(rutUser);
            loanAplicaction.setState(state);
            loanAplicaction.setAmount(amount);
            loanAplicaction.setAnualInterestRate(anualInterestRate);
            loanAplicaction.setTerm(term);
            loanAplicaction.setLoan_type(loan_type);
            loanAplicaction.setFee(fee);
            loanAplicaction.setCreditInsuarance(creditInsurance);
            loanAplicaction.setMonthlyFireInsurance(monthlyFireInsurance);
            loanAplicaction.setAdministrationCommission(administrationCommission);
            loanAplicaction.setPropertyValue(propertyValue);
            loanAplicaction.setConsistentSaveCheck(consistentSaveCheck);
            loanAplicaction.setPeriodicDepositsCheck(periodicDepositsCheck);
            loanAplicaction.setRecentWithdrawCheck(recentWithdrawCheck);
            loanAplicaction.setSave_capacity(saveCapacity);

            // Convertir archivos a byte[] si no son nulos
            if (income_file != null && !income_file.isEmpty()) {
                loanAplicaction.setIncome_file(income_file.getBytes());
            }
            if (certificadoAvaluo != null && !certificadoAvaluo.isEmpty()) {
                loanAplicaction.setCertificadoAvaluo(certificadoAvaluo.getBytes());
            }
            if (historialCrediticio != null && !historialCrediticio.isEmpty()) {
                loanAplicaction.setHistorialCrediticio(historialCrediticio.getBytes());
            }
            if (escrituraPrimeraVivienda != null && !escrituraPrimeraVivienda.isEmpty()) {
                loanAplicaction.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda.getBytes());
            }
            if (estadoFinancieroNegocio != null && !estadoFinancieroNegocio.isEmpty()) {
                loanAplicaction.setEstadoFinancieroNegocio(estadoFinancieroNegocio.getBytes());
            }
            if (planNegocios != null && !planNegocios.isEmpty()) {
                loanAplicaction.setPlanNegocios(planNegocios.getBytes());
            }
            if (presupuestoRemodelacion != null && !presupuestoRemodelacion.isEmpty()) {
                loanAplicaction.setPresupuestoRemodelacion(presupuestoRemodelacion.getBytes());
            }
            if (certificadoAvaluoActualizado != null && !certificadoAvaluoActualizado.isEmpty()) {
                loanAplicaction.setCertificadoAvaluoActualizado(certificadoAvaluoActualizado.getBytes());
            }

            // Guardar la entidad en la base de datos
            LoanAplicactionEntity newLoanAplication = loanAplicationService.saveLoan(loanAplicaction);
            return ResponseEntity.ok(newLoanAplication);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar archivos", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanAplicactionEntity> updateLoanAplication(
            @PathVariable Long id,
            @RequestParam(name = "rutUser") String rutUser,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "amount") int amount,
            @RequestParam(name = "anualInterestRate") float anualInterestRate,
            @RequestParam(name = "term") Integer term,
            @RequestParam(name = "loan_type") String loan_type,
            @RequestParam(name = "fee") Double fee,
            @RequestParam(name = "creditInsurance", required = false) Double creditInsurance,
            @RequestParam(name = "monthlyFireInsurance", required = false) Double monthlyFireInsurance,
            @RequestParam(name = "administrationCommission", required = false) Double administrationCommission,
            @RequestParam(name = "propertyValue", required = false) Double propertyValue,
            @RequestParam(name = "consistentSaveCheck", required = false) Boolean consistentSaveCheck,
            @RequestParam(name = "periodicDepositsCheck", required = false) Boolean periodicDepositsCheck,
            @RequestParam(name = "recentWithdrawCheck", required = false) Boolean recentWithdrawCheck,
            @RequestParam(name = "saveCapacity", required = false) String saveCapacity,
            @RequestParam(name = "income_file", required = false) MultipartFile income_file,
            @RequestParam(name = "certificadoAvaluo", required = false) MultipartFile certificadoAvaluo,
            @RequestParam(name = "historialCrediticio", required = false) MultipartFile historialCrediticio,
            @RequestParam(name = "escrituraPrimeraVivienda", required = false) MultipartFile escrituraPrimeraVivienda,
            @RequestParam(name = "estadoFinancieroNegocio", required = false) MultipartFile estadoFinancieroNegocio,
            @RequestParam(name = "planNegocios", required = false) MultipartFile planNegocios,
            @RequestParam(name = "presupuestoRemodelacion", required = false) MultipartFile presupuestoRemodelacion,
            @RequestParam(name = "certificadoAvaluoActualizado", required = false) MultipartFile certificadoAvaluoActualizado) {

        try {
            // Buscar la entidad por su ID y lanzar una excepción si no existe
            LoanAplicactionEntity loanAplicaction = loanAplicationService.getLoanAplicationById(id)
                    .orElseThrow(() -> new ResourceAccessException("Loan application not found"));

            // Actualizar los campos de la entidad
            loanAplicaction.setRutUser(rutUser);
            loanAplicaction.setState(state);
            loanAplicaction.setAmount(amount);
            loanAplicaction.setAnualInterestRate(anualInterestRate);
            loanAplicaction.setTerm(term);
            loanAplicaction.setLoan_type(loan_type);
            loanAplicaction.setFee(fee);
            loanAplicaction.setCreditInsuarance(creditInsurance);
            loanAplicaction.setMonthlyFireInsurance(monthlyFireInsurance);
            loanAplicaction.setAdministrationCommission(administrationCommission);
            loanAplicaction.setPropertyValue(propertyValue);
            loanAplicaction.setConsistentSaveCheck(consistentSaveCheck);
            loanAplicaction.setPeriodicDepositsCheck(periodicDepositsCheck);
            loanAplicaction.setRecentWithdrawCheck(recentWithdrawCheck);
            loanAplicaction.setSave_capacity(saveCapacity);

            // Convertir archivos a byte[] si no son nulos
            if (income_file != null && !income_file.isEmpty()) {
                loanAplicaction.setIncome_file(income_file.getBytes());
            }
            if (certificadoAvaluo != null && !certificadoAvaluo.isEmpty()) {
                loanAplicaction.setCertificadoAvaluo(certificadoAvaluo.getBytes());
            }
            if (historialCrediticio != null && !historialCrediticio.isEmpty()) {
                loanAplicaction.setHistorialCrediticio(historialCrediticio.getBytes());
            }
            if (escrituraPrimeraVivienda != null && !escrituraPrimeraVivienda.isEmpty()) {
                loanAplicaction.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda.getBytes());
            }
            if (estadoFinancieroNegocio != null && !estadoFinancieroNegocio.isEmpty()) {
                loanAplicaction.setEstadoFinancieroNegocio(estadoFinancieroNegocio.getBytes());
            }
            if (planNegocios != null && !planNegocios.isEmpty()) {
                loanAplicaction.setPlanNegocios(planNegocios.getBytes());
            }
            if (presupuestoRemodelacion != null && !presupuestoRemodelacion.isEmpty()) {
                loanAplicaction.setPresupuestoRemodelacion(presupuestoRemodelacion.getBytes());
            }
            if (certificadoAvaluoActualizado != null && !certificadoAvaluoActualizado.isEmpty()) {
                loanAplicaction.setCertificadoAvaluoActualizado(certificadoAvaluoActualizado.getBytes());
            }

            // Guardar la entidad actualizada en la base de datos
            LoanAplicactionEntity updatedLoanAplication = loanAplicationService.saveLoan(loanAplicaction);
            return ResponseEntity.ok(updatedLoanAplication);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar archivos", e);
        }
    }

    @PostMapping("/calculateFee")
    public ResponseEntity<Double> calcualteFee(@RequestBody SimulationLoanEntity loanAplicaction) {
        double result = simulationService.monthlyPaymentCal(loanAplicaction.getAmount(), loanAplicaction.getTerm(),
                loanAplicaction.getAnualInterestRate());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) throws Exception {
        var isDeleted = loanAplicationService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFiles(
            @PathVariable Long id,
            @RequestParam(name = "income_file", required = false) MultipartFile income_file,
            @RequestParam(name = "certificadoAvaluo", required = false) MultipartFile certificadoAvaluo,
            @RequestParam(name = "historialCrediticio", required = false) MultipartFile historialCrediticio,
            @RequestParam(name = "escrituraPrimeraVivienda", required = false) MultipartFile escrituraPrimeraVivienda,
            @RequestParam(name = "estadoFinancieroNegocio", required = false) MultipartFile estadoFinancieroNegocio,
            @RequestParam(name = "planNegocios", required = false) MultipartFile planNegocios,
            @RequestParam(name = "presupuestoRemodelacion", required = false) MultipartFile presupuestoRemodelacion,
            @RequestParam(name = "certificadoAvaluoActualizado", required = false) MultipartFile certificadoAvaluoActualizado) {

        try {

            // Buscar la entidad por su ID y lanzar una excepción si no existe
            LoanAplicactionEntity loanApplication = loanAplicationService.getLoanAplicationById(id)
                    .orElseThrow(() -> new ResourceAccessException("Loan application not found"));

            // Convertir cada archivo a byte[] y asignarlo a los atributos si no son nulos
            if (income_file != null && !income_file.isEmpty()) {
                loanApplication.setIncome_file(income_file.getBytes());
                System.out.println(income_file);
            } else {
                System.out.println("NO PASOOOO NULLL");
                ;
            }
            if (certificadoAvaluo != null && !certificadoAvaluo.isEmpty()) {
                loanApplication.setCertificadoAvaluo(certificadoAvaluo.getBytes());
            }
            if (historialCrediticio != null && !historialCrediticio.isEmpty()) {
                loanApplication.setHistorialCrediticio(historialCrediticio.getBytes());
            }
            if (escrituraPrimeraVivienda != null && !escrituraPrimeraVivienda.isEmpty()) {
                loanApplication.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda.getBytes());
            }
            if (estadoFinancieroNegocio != null && !estadoFinancieroNegocio.isEmpty()) {
                loanApplication.setEstadoFinancieroNegocio(estadoFinancieroNegocio.getBytes());
            }
            if (planNegocios != null && !planNegocios.isEmpty()) {
                loanApplication.setPlanNegocios(planNegocios.getBytes());
            }
            if (presupuestoRemodelacion != null && !presupuestoRemodelacion.isEmpty()) {
                loanApplication.setPresupuestoRemodelacion(presupuestoRemodelacion.getBytes());
            }
            if (certificadoAvaluoActualizado != null && !certificadoAvaluoActualizado.isEmpty()) {
                loanApplication.setCertificadoAvaluoActualizado(certificadoAvaluoActualizado.getBytes());
            }

            // Guardar los cambios en la base de datos
            loanAplicationService.saveLoan(loanApplication);

            return ResponseEntity.ok("Files uploaded successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
