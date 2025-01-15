package br.com.diegomarques.textrix.services.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.diegomarques.textrix.domains.Endereco;
import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.repositories.EnderecoRepository;
import br.com.diegomarques.textrix.repositories.ParceiroRepository;
import br.com.diegomarques.textrix.services.ParceiroService;
import br.com.diegomarques.textrix.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ParceiroServiceTest {

    @Mock
    private ParceiroRepository parceiroRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private ParceiroService parceiroService;

    private ParceiroDTO parceiroDTO;
    private Parceiro parceiro;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("123");

        parceiro = new Parceiro();
        parceiro.setChave(1L);
        parceiro.setNome("Parceiro Novo");
        parceiro.setExcluido(false);
        parceiro.setEndereco(endereco);

        parceiroDTO = new ParceiroDTO(parceiro);
    }

    @Test
    void testCreateParceiro() {
        ParceiroNovoDTO dto = new ParceiroNovoDTO();
        dto.setNome("Parceiro Novo");

        when(parceiroRepository.save(any(Parceiro.class))).thenReturn(parceiro);
        when(enderecoRepository.save(any())).thenReturn(parceiro.getEndereco());

        ParceiroDTO parceiroResult = parceiroService.create(dto);

        assertNotNull(parceiroResult);
        assertEquals("Parceiro Novo", parceiroResult.getNome());
        verify(parceiroRepository).save(any(Parceiro.class));
        verify(enderecoRepository).save(any());
    }

    @Test
    void testFindById_ParceiroExists() {
        when(parceiroRepository.findByChaveAndExcluidoFalse(1L)).thenReturn(Optional.of(parceiro));

        ParceiroDTO parceiroResult = parceiroService.findById(1L);

        assertNotNull(parceiroResult);
        assertEquals("Parceiro Novo", parceiroResult.getNome());
        verify(parceiroRepository).findByChaveAndExcluidoFalse(1L);
    }

    @Test
    void testFindById_ParceiroNotFound() {
        when(parceiroRepository.findByChaveAndExcluidoFalse(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> parceiroService.findById(1L));
        verify(parceiroRepository).findByChaveAndExcluidoFalse(1L);
    }

    @Test
    void testFindAtivos() {
        when(parceiroRepository.findByExcluidoFalse()).thenReturn(List.of(parceiro));

        List<ParceiroDTO> parceiroResults = parceiroService.findAtivos();

        assertEquals(1, parceiroResults.size());
        assertEquals("Parceiro Novo", parceiroResults.get(0).getNome());
        verify(parceiroRepository).findByExcluidoFalse();
    }

    @Test
    void testUpdateParceiro() {
        // Configurar o parceiro existente
        when(parceiroRepository.findByChaveAndExcluidoFalse(1L)).thenReturn(Optional.of(parceiro));

        Parceiro updatedParceiro = new Parceiro();
        updatedParceiro.setChave(1L);
        updatedParceiro.setNome("Parceiro Atualizado");
        updatedParceiro.setEndereco(parceiro.getEndereco());

        // Criar o ParceiroDTO a partir do Parceiro atualizado
        ParceiroDTO updateDTO = new ParceiroDTO(updatedParceiro);

        // Mockar o save para devolver o parceiro atualizado
       when(parceiroRepository.save(any(Parceiro.class))).thenAnswer(invocation -> {
            Parceiro p = invocation.getArgument(0);
            p.setNome("Parceiro Atualizado"); // Simulando a atualização
            return p;
        });

        // Executar o método de atualização
        ParceiroDTO updatedParceiroFromService = parceiroService.update(1L, updateDTO);

        // Verificações
        assertNotNull(updatedParceiroFromService);
        assertEquals("Parceiro Atualizado", updatedParceiroFromService.getNome());
        verify(parceiroRepository).findByChaveAndExcluidoFalse(1L);
        verify(parceiroRepository).save(any(Parceiro.class));
    }

    @Test
    void deletarParceiroSucesso() {
        when(parceiroRepository.findByChaveAndExcluidoFalse(1L)).thenReturn(Optional.of(parceiro));
        when(parceiroRepository.save(any(Parceiro.class))).thenReturn(parceiro);

        parceiroService.delete(1L);

        assertTrue(parceiro.isExcluido());
        verify(parceiroRepository, times(1)).save(any(Parceiro.class));
        verify(parceiroRepository, times(1)).findByChaveAndExcluidoFalse(1L);
    }
}