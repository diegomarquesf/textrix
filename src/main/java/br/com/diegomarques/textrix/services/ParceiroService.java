package br.com.diegomarques.textrix.services;

import br.com.diegomarques.textrix.domains.Endereco;
import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.dtos.EnderecoDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.repositories.EnderecoRepository;
import br.com.diegomarques.textrix.repositories.ParceiroRepository;
import br.com.diegomarques.textrix.services.exceptions.ObjectNotFoundException;
import br.com.diegomarques.textrix.validators.CnpjValidator;
import br.com.diegomarques.textrix.validators.CpfValidator;
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

    @Autowired
    private ValidationService validationService;

    @Transactional
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
                .orElseThrow(() -> new ObjectNotFoundException("Parceiro não encontrado! Chave: " + chave));
    }

    @Transactional(readOnly = true)
    public List<ParceiroDTO> findAtivos() {
        return parceiroRepository.findByExcluidoFalse()
                .stream()
                .map(ParceiroDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParceiroDTO update(Long chave, ParceiroDTO parceiroDTO) {
        if (chave == null || !chave.equals(parceiroDTO.getChave()))
            throw new IllegalArgumentException("A chave fornecida não corresponde à chave do DTO.");

        if (parceiroDTO.getCpf() != null)
            validationService.validate(CpfValidator.class, parceiroDTO.getCpf());

        if (parceiroDTO.getCnpj() != null)
            validationService.validate(CnpjValidator.class, parceiroDTO.getCnpj());

        return parceiroRepository.findByChaveAndExcluidoFalse(parceiroDTO.getChave())
                .map(parceiro -> {
                    updateParceiroFromDTO(parceiro, parceiroDTO);
                    return new ParceiroDTO(saveParceiroAndEndereco(parceiro));
                })
                .orElseThrow(() -> new ObjectNotFoundException("Parceiro não encontrado! Chave: " + chave));
    }

    @Transactional
    public void delete(Long chave) {
        Parceiro parceiro = parceiroRepository.findByChaveAndExcluidoFalse(chave)
                .orElseThrow(() -> new ObjectNotFoundException("Parceiro não encontrado! Chave: " + chave));

        parceiro.setExcluido(true);
        if (parceiro.getEndereco() != null)
            parceiro.getEndereco().setExcluido(true);

        parceiroRepository.save(parceiro);

        if (parceiro.getEndereco() != null)
            enderecoRepository.save(parceiro.getEndereco());
    }

    private void updateParceiroFromDTO(Parceiro parceiro, ParceiroDTO dto) {
        if (dto.getNome() != null) parceiro.setNome(dto.getNome());
        if (dto.getNomeFantasia() != null) parceiro.setNomeFantasia(dto.getNomeFantasia());
        if (dto.getCnpj() != null) parceiro.setCnpj(dto.getCnpj());
        if (dto.getCpf() != null) parceiro.setCpf(dto.getCpf());
        if (dto.getTipoNaturezaJuridica() != null) parceiro.setTipoNaturezaJuridica(dto.getTipoNaturezaJuridica());
        if (dto.getTipoParceiro() != null) parceiro.setTipoParceiro(dto.getTipoParceiro());

        if (dto.getEndereco() != null) {
            Endereco endereco = parceiro.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
                parceiro.setEndereco(endereco);
            }
            updateEnderecoFromDTO(endereco, dto.getEndereco());
        }
    }

    private void updateEnderecoFromDTO(Endereco endereco, EnderecoDTO dto) {
        if (dto.getLogradouro() != null) endereco.setLogradouro(dto.getLogradouro());
        if (dto.getNumero() != null) endereco.setNumero(dto.getNumero());
        if (dto.getBairro() != null) endereco.setBairro(dto.getBairro());
        if (dto.getComplemento() != null) endereco.setComplemento(dto.getComplemento());
        if (dto.getCidade() != null) endereco.setCidade(dto.getCidade());
        if (dto.getEstado() != null) endereco.setEstado(dto.getEstado());
        if (dto.getCep() != null) endereco.setCep(dto.getCep());
    }

    private Parceiro saveParceiroAndEndereco(Parceiro parceiro) {
        parceiro = parceiroRepository.save(parceiro);
        if (parceiro.getEndereco() != null)
            enderecoRepository.save(parceiro.getEndereco());

        return parceiro;
    }

    private Parceiro toEntity(ParceiroNovoDTO parceiroNovoDTO) {
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
