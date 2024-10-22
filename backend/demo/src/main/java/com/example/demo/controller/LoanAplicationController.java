package com.example.demo.controller;

import com.example.demo.entities.LoanAplicactionEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.LoanAplicationService;
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

    @GetMapping("/")
    public ResponseEntity<List<LoanAplicactionEntity>> listAllAplications() {
        List<LoanAplicactionEntity> loansAplications = loanAplicationService.getLoans();
        return ResponseEntity.ok(loansAplications);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<LoanAplicactionEntity>> listAplicationsByRut(@RequestBody UserEntity user) {
        List<LoanAplicactionEntity> loansAplications = loanAplicationService.getLoansByRut(user.getRut());
        return ResponseEntity.ok(loansAplications);
    }

    @PostMapping("/")
    public ResponseEntity<LoanAplicactionEntity> registerLoanAplication(@RequestBody LoanAplicactionEntity loanAplicaction) {
        LoanAplicactionEntity newLoanAplication = loanAplicationService.saveLoan(loanAplicaction);
        return ResponseEntity.ok(newLoanAplication);
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
            }else{
                System.out.println("NO PASOOOO NULLL");;
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

    @GetMapping("/download/incomeFile/{id}")
    public ResponseEntity<byte[]> downloadIncomeFile(@PathVariable Long id) {
        // Buscar la entidad por su ID
        LoanAplicactionEntity loanApplication = loanAplicationService.getLoanAplicationById(id)
                .orElseThrow(() -> new ResourceAccessException("Loan application not found"));

        // Recuperar el OID del archivo desde la entidad (si es un OID y no un byte array)
        Long fileOid = loanApplication.getIncome_file_oid();  // Supongamos que lo almacenaste como un Long o similar

        if (fileOid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Recuperar el archivo desde el OID usando una consulta personalizada
        byte[] fileData = loanAplicationService.getFileDataFromOid(fileOid);

        // Configurar las cabeceras para el archivo PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "income_file.pdf");

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }



}



