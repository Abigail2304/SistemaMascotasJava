package com.petcare.SistemaMascotasJava.controller;

import com.petcare.SistemaMascotasJava.model.Mascota;
import com.petcare.SistemaMascotasJava.service.MascotaService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class MascotaController {

    private final MascotaService service;

    public MascotaController(MascotaService service) {
        this.service = service;
    }

    private void cargarEstadisticas(Model model) {
        model.addAttribute("totalMascotas", service.totalMascotas());
        model.addAttribute("totalPerros", service.totalPerros());
        model.addAttribute("totalGatos", service.totalGatos());
        model.addAttribute("totalVacunadas", service.totalVacunadas());
    }

    @GetMapping("/")
    public String inicio(Model model) {

        cargarEstadisticas(model);

        return "inicio";
    }

    @GetMapping("/mascotas")
    public String listar(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(defaultValue = "") String especie,
            Model model) {

        var mascotas = service.listarTodas()
                .stream()
                .filter(m -> nombre.isBlank() ||
                        m.getNombre().toLowerCase(Locale.ROOT)
                                .contains(nombre.toLowerCase(Locale.ROOT)))
                .filter(m -> especie.isBlank() ||
                        m.getEspecie().equalsIgnoreCase(especie))
                .toList();

        model.addAttribute("mascotas", mascotas);
        model.addAttribute("nombre", nombre);
        model.addAttribute("especie", especie);

        cargarEstadisticas(model);

        return "mascotas/lista";
    }

    @GetMapping("/mascotas/crear")
    public String crear(Model model) {

        model.addAttribute("mascota", new Mascota());

        return "mascotas/crear";
    }

    @PostMapping("/mascotas/guardar")
    public String guardar(
            @ModelAttribute("mascota") Mascota mascota,
            BindingResult result,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            return "mascotas/crear";
        }

        mascota.setId(null);
        mascota.setFechaRegistro(null);

        service.guardar(mascota);

        redirect.addFlashAttribute(
                "mensaje", "Mascota registrada correctamente.");

        return "redirect:/mascotas";
    }

    @GetMapping("/mascotas/detalles/{id}")
    public String detalles(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("mascota", service.buscarPorId(id));

        return "mascotas/detalles";
    }

    @GetMapping("/mascotas/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("mascota", service.buscarPorId(id));

        return "mascotas/editar";
    }

    @PostMapping("/mascotas/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("mascota") Mascota mascota,
            BindingResult result,
            RedirectAttributes redirect) {

        mascota.setId(id);

        if (result.hasErrors()) {
            return "mascotas/editar";
        }

        service.actualizar(id, mascota);

        redirect.addFlashAttribute(
                "mensaje", "Mascota actualizada correctamente.");

        return "redirect:/mascotas";
    }

    @PostMapping("/mascotas/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirect) {

        service.eliminar(id);

        redirect.addFlashAttribute(
                "mensaje", "Mascota eliminada correctamente.");

        return "redirect:/mascotas";
    }
}