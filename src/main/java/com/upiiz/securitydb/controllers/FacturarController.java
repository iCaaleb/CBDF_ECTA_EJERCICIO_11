package com.upiiz.securitydb.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
@PreAuthorize("denyAll()")
public class FacturarController {
    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('READ')")
    public String listar(){
        return "Facturas Listadas - Con seguridad";
    }

    @GetMapping("/actualizar")
    public String actualizar(){
        return "Factura Actualizada - Con seguridad";
    }

    @GetMapping("/eliminar")
    public String eliminar(){
        return "Factura Eliminada - Con seguridad";
    }

    @PreAuthorize("hasAuthority('READ') or hasAuthority('CREATE')")
    @GetMapping("/crear")
    public String crear(){
        return "Factura Creada - Con seguridad";
    }

    @PreAuthorize("hasAuthority('DEPLOY')")
    @GetMapping("/patch")
    public String patch(){
        return "Factura Patch - Con seguridad";
    }

}
