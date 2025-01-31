package br.com.diegomarques.textrix.domains.mappers;

import br.com.diegomarques.textrix.domains.Usuario;
import br.com.diegomarques.textrix.domains.dtos.UsuarioDTO;
import br.com.diegomarques.textrix.domains.dtos.UsuarioNovoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericMapper<Usuario, UsuarioDTO, UsuarioNovoDTO>  {

}
