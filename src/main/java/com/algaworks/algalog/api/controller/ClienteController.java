package com.algaworks.algalog.api.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteServecie;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/logistica/clientes")
public class ClienteController {

	private CatalogoClienteServecie catalogoClienteServecie;
	private ClienteRepository clienteRepository;
	
	@GetMapping("/listarClientes")
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id){
		return clienteRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
				
//		Primeira fora de se fazer o return:
//		Optional<Cliente> cliente = clienteRepository.findById(id);
//		
//		if (cliente.isPresent()) {
//			return ResponseEntity.ok(cliente.get());
//		}
//	
//		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente cadastrar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteServecie.salvar(cliente);
	}
	
	@PutMapping("/alterarCliente/{id}")
	public ResponseEntity<Cliente> alterar(@PathVariable Long id, @Valid @RequestBody Cliente cliente){
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		cliente = catalogoClienteServecie.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id){
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		catalogoClienteServecie.deletar(id);
		return ResponseEntity.noContent().build();
	}

}