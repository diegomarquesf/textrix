package br.com.diegomarques.textrix.services;

import br.com.diegomarques.textrix.domains.Endereco;
import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.repositories.EnderecoRepository;
import br.com.diegomarques.textrix.repositories.ParceiroRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParceiroService {

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public ParceiroDTO create(ParceiroNovoDTO parceiroNovoDTO) {
        Parceiro parceiro = toEntity(parceiroNovoDTO);

        parceiro = parceiroRepository.save(parceiro);
        if (parceiro.getEndereco() != null)
            enderecoRepository.save(parceiro.getEndereco());

        return new ParceiroDTO(parceiro);
    }

    @Transactional(readOnly = true)
    public List<ParceiroDTO> findAll() {
        return parceiroRepository.findAll()
                .stream()
                .map(ParceiroDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ParceiroDTO findById(Long chave) {
        return parceiroRepository.findByChaveAndExcluidoFalse(chave)
                .map(ParceiroDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Parceiro não encontrado! Chave: " + chave));
    }

    @Transactional(readOnly = true)
    public List<ParceiroDTO> findAtivos() {
        return parceiroRepository.findByExcluidoFalse()
                .stream()
                .map(ParceiroDTO::new)
                .collect(Collectors.toList());
    }

    public Parceiro toEntity(ParceiroNovoDTO parceiroNovoDTO) {
        var parceiro = new Parceiro(
                parceiroNovoDTO.getTipoNaturezaJuridica(),
                parceiroNovoDTO.getTipoParceiro(),
                parceiroNovoDTO.getNome(),
                parceiroNovoDTO.getNomeFantasia(),
                parceiroNovoDTO.getCpf(),
                parceiroNovoDTO.getCnpj());

        if(parceiroNovoDTO.getEndereco() != null) {
            var endereco = new Endereco(
                    parceiroNovoDTO.getEndereco().getLogradouro(),
                    parceiroNovoDTO.getEndereco().getNumero(),
                    parceiroNovoDTO.getEndereco().getBairro(),
                    parceiroNovoDTO.getEndereco().getComplemento(),
                    parceiroNovoDTO.getEndereco().getCidade(),
                    parceiroNovoDTO.getEndereco().getEstado(),
                    parceiroNovoDTO.getEndereco().getCep(),
                    parceiro);
            parceiro.setEndereco(endereco);
        }

        return parceiro;
    }
}
