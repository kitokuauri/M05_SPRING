package com.mvc.services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.repositories.ClientesRepository;

import jakarta.persistence.EntityNotFoundException;

import com.mvc.models.ClientesModel;
import com.mvc.models.GestoresModel;

@Service
public class ClientesService {
	
//	Instancia que realiza la Inyección de la dependencia (pomp)
	@Autowired
	ClientesRepository clientesRepository;
	
//	Sirve tanto para insertar como actualizar (si ponemos un id existente)
	public ClientesModel guardarCliente(ClientesModel cliente) {
		return clientesRepository.save(cliente);
	}
	
//	cuando tratamos con id => clase Optional
	public Optional<ClientesModel> obtenerPorId(long id) {
		return clientesRepository.findById(id);
	}
	
	public ArrayList<ClientesModel> obtenerClientes(){
		return (ArrayList<ClientesModel>)clientesRepository.findAll();
	}

	public boolean eliminarCliente(long id) {
		try {
			if(clientesRepository.existsById(id)) {
				clientesRepository.deleteById(id);
				return true;
			} else {
				System.out.println("El id seleccionado no existe.");
				return false;
			}
		} catch(Exception err) {
			return false;
		}
	}
	
	public ClientesModel actualizarCliente(long id, Map<String, Object> cambios) {
        Optional<ClientesModel> clienteExiste = clientesRepository.findById(id);

        if (clienteExiste.isPresent()) {
        	ClientesModel cliente = clienteExiste.get();

            cambios.forEach((campo, valor) -> {
                switch (campo) {
                	case "id":
                		cliente.setId((long) valor);
                		break;
                	case "id_gestor":
                		cliente.setId_gestor((GestoresModel) valor);
                		break;
                    case "nombre":
                    	cliente.setNombre((String) valor);
                        break;
                    case "apellido":
                    	cliente.setApellido((String) valor);
                        break;
                    case "email":
                    	cliente.setEmail((String) valor);
                        break;
                    case "edad":
                    	cliente.setEdad((int)valor);
                    	break;
                    case "presupuesto":
                    	cliente.setPresupuesto((double)valor);
                    	break;
                }
            });
            
            return clientesRepository.save(cliente);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado con ID: " + id);
        }
	}
 
	
	
	
}
