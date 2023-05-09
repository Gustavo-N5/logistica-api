package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteServecie {
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Long id) {
		return  clienteRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso= clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteEcistente -> !clienteEcistente.equals(cliente));
		if (emailEmUso) {
			throw new NegocioException("Este e-mail já está cadastrado");
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}
}
