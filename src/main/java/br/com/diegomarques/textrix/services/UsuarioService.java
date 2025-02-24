package br.com.diegomarques.textrix.services;

import br.com.diegomarques.textrix.domains.dtos.UsuarioDTO;
import br.com.diegomarques.textrix.domains.dtos.UsuarioNovoDTO;
import br.com.diegomarques.textrix.domains.enums.TipoRole;
import br.com.diegomarques.textrix.domains.mappers.UsuarioMapper;
import br.com.diegomarques.textrix.repositories.EnderecoRepository;
import br.com.diegomarques.textrix.repositories.UsuarioRepository;
import br.com.diegomarques.textrix.services.exceptions.ObjectNotFoundException;
import br.com.diegomarques.textrix.validators.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public UsuarioDTO create(UsuarioNovoDTO usuarioNovoDTO) {
        var usuario = usuarioMapper.toEntityDtoNovo(usuarioNovoDTO);
        usuario.setSenha(encoder.encode(usuario.getSenha()));

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty())
            usuario.setRoles(Set.of(TipoRole.ROLE_USER2));

        usuario = usuarioRepository.save(usuario);

        if (usuario.getEndereco() != null)
            enderecoRepository.save(usuario.getEndereco());

        return usuarioMapper.toDto(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll(){
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> findAtivos() {
        return usuarioRepository.findByAtivoTrue()
                .stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findByChave(Long chave) {
        return usuarioRepository.findById(chave)
                .map(usuarioMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não foi encontrado! Chave: " + chave));
    }

    @Transactional
    public UsuarioDTO update(Long chave, UsuarioDTO usuarioDTO) {
        if (chave == null || !chave.equals(usuarioDTO.chave()))
            throw new IllegalArgumentException("A chave fornecida não corresponde à chave do DTO.");

        if (usuarioDTO.cpf() == null || usuarioDTO.cpf().isEmpty())
            throw new ObjectNotFoundException("O campo 'CPF' é necessário!");
        else
            validationService.validate(CpfValidator.class, usuarioDTO.cpf());

        var usuario = usuarioRepository.findById(chave)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não foi encontrado! Chave: " + chave));

        usuarioMapper.updateEntityFromDto(usuarioDTO, usuario);

        return  usuarioMapper.toDto(usuario);
    }

    @Transactional
    public void disable(Long chave) {
        var usuario = usuarioRepository.findById(chave)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não foi encontrado! Chave: " + chave));

        if (!usuario.isAtivo())
            throw new IllegalArgumentException("Usuário já está desativado!");

        usuario.setAtivo(false);

        usuarioRepository.save(usuario);
    }
}
