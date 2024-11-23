package com.backbyte.controllers;

import com.backbyte.models.*;
import com.backbyte.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/admin/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.getClientes();  // Obtener todos los clientes mapeados a ClienteModel
        System.out.println("Número de clientes pasados a la vista: " + clientes.size());  // Verificar cuántos clientes se están pasando a la vista
        System.out.println("Clientes recuperados: ");
        clientes.forEach(cliente -> System.out.println(cliente));  // Imprimimos cada cliente en consola


        model.addAttribute("clientes", clientes);  // Agrega los clientes al modelo para pasarlos a la vista
        return "clientes";  // Retorna la vista 'clientes/list'
    }

    @PostMapping("/clientes/agregar")
    public String addCliente(@RequestParam String nombre,
                             @RequestParam String apellido,
                             @RequestParam String dni,
                             @RequestParam String direccion,
                             @RequestParam String telefono) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setApellido(apellido);
        nuevoCliente.setDni(dni);
        nuevoCliente.setDireccion(direccion);
        nuevoCliente.setTelefono(telefono);

        clienteService.addCliente(nuevoCliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/editar/{id}")
    public String editCliente(@PathVariable Integer id,
                              @RequestParam String nombre,
                              @RequestParam String apellido,
                              @RequestParam String dni,
                              @RequestParam String direccion,
                              @RequestParam String telefono) {
        Cliente clienteExistente = clienteService.getClienteById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        // Asignar nuevos valores al cliente existente
        clienteExistente.setNombre(nombre);
        clienteExistente.setApellido(apellido);
        clienteExistente.setDni(dni);
        clienteExistente.setDireccion(direccion);
        clienteExistente.setTelefono(telefono);

        clienteService.updateCliente(id, clienteExistente);
        return "redirect:/clientes";
    }


    @GetMapping("/clientes/eliminar/{id}")
    public String deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return "redirect:/clientes";
    }

}