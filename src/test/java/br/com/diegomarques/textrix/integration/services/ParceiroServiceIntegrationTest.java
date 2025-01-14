package br.com.diegomarques.textrix.integration.services;

import br.com.diegomarques.textrix.domains.Endereco;
import br.com.diegomarques.textrix.domains.Parceiro;
import br.com.diegomarques.textrix.domains.dtos.EnderecoDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import br.com.diegomarques.textrix.repositories.ParceiroRepository;
import br.com.diegomarques.textrix.services.ParceiroService;
import br.com.diegomarques.textrix.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ParceiroServiceIntegrationTest {

    @Autowired
    private ParceiroService parceiroService;

    @Autowired
    private ParceiroRepository parceiroRepository;

    private EnderecoDTO endereco;
    private ParceiroNovoDTO parceiro;

    @BeforeEach
    void setUp() {
        // Configuração do EnderecoDTO
        endereco = new EnderecoDTO();
        endereco.setLogradouro("Rua teste");
        endereco.setNumero("1");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade teste");
        endereco.setEstado("Estado teste");
        endereco.setCep("11111-111");

        // Configuração do ParceiroNovoDTO
        parceiro = new ParceiroNovoDTO();
        parceiro.setTipoNaturezaJuridica(TipoNaturezaJuridica.PESSOA_FISICA);
        parceiro.setTipoParceiro(TipoParceiro.CLIENTE);
        parceiro.setNome("Nome Teste");
        parceiro.setNomeFantasia("Nome Fantasia Teste");
        parceiro.setCpf("83249694037");
        parceiro.setEndereco(endereco);
    }

    @Test
    public void testCreateParceiro() {
        // Quando: Chamamos o método create do serviço
        ParceiroDTO createdDTO = parceiroService.create(parceiro);

        // Então: Verificamos se o parceiro foi criado corretamente
        assertNotNull(createdDTO.getChave());
        assertEquals(parceiro.getNome(), createdDTO.getNome());
        assertNotNull(createdDTO.getEndereco());

        // Verificamos se foi salvo no banco de dados
        Optional<Parceiro> savedParceiro = parceiroRepository.findByChaveAndExcluidoFalse(createdDTO.getChave());
        assertTrue(savedParceiro.isPresent());
        assertEquals(createdDTO.getNome(), savedParceiro.get().getNome());
        assertEquals(endereco.getLogradouro(), createdDTO.getEndereco().getLogradouro());
    }

    @Test
    public void testFindAllParceiros() {
        // Dado: Inserimos alguns parceiros no banco de dados
        parceiroService.create(parceiro);

        // Quando: Chamamos o método para buscar todos os parceiros
        List<ParceiroDTO> parceiros = parceiroService.findAll();

        // Então: Verificamos se a lista retornada tem o tamanho esperado
        assertEquals(1, parceiros.size());
    }

    @Test
    public void testFindByIdParceiro() {
        // Dado: Criamos e salvamos um parceiro no banco de dados
        ParceiroDTO savedParceiro = parceiroService.create(parceiro);

        // Quando: Chamamos o método para buscar pelo ID
        ParceiroDTO foundParceiro = parceiroService.findById(savedParceiro.getChave());

        // Então: Verificamos se o parceiro encontrado corresponde ao salvo
        assertEquals(savedParceiro.getNome(), foundParceiro.getNome());
    }

    @Test
    public void testFindByIdParceiroNotFound() {
        // Quando e Então: Esperamos que uma exceção seja lançada para um ID inexistente
        assertThrows(ObjectNotFoundException.class, () -> parceiroService.findById(999L), "Exceção lançada para a chave inexistente!");
    }

    @Test
    @Rollback(true)
    public void testUpdateParceiro() {
        // Dado: Criamos e salvamos um parceiro no banco de dados
        ParceiroDTO savedParceiro = parceiroService.create(parceiro);

        Optional<Parceiro> parceiroEntity = parceiroRepository.findByChaveAndExcluidoFalse(savedParceiro.getChave());
        assertTrue(parceiroEntity.isPresent());

        Parceiro parceiro = parceiroEntity.get();
        parceiro.setNome("Nome Atualizado");

        // Quando: Atualizamos o parceiro
        ParceiroDTO updatedDTO = parceiroService.update(savedParceiro.getChave(), new ParceiroDTO(parceiro));

        // Então: Verificamos se o nome foi atualizado no DTO e no banco de dados
        assertNotNull(updatedDTO);
        assertEquals("Nome Atualizado", updatedDTO.getNome());
        Optional<Parceiro> updatedParceiro = parceiroRepository.findByChaveAndExcluidoFalse(savedParceiro.getChave());
        assertTrue(updatedParceiro.isPresent());
        assertEquals("Nome Atualizado", updatedParceiro.get().getNome());
    }

    @Test
    @Rollback(true)
    public void testDeleteParceiro() {
        // Dado: Criamos e salvamos um parceiro no banco de dados
        ParceiroDTO savedParceiro = parceiroService.create(parceiro);

        Optional<Parceiro> parceiroEntity = parceiroRepository.findByChaveAndExcluidoFalse(savedParceiro.getChave());
        assertTrue(parceiroEntity.isPresent());

        // Quando: Chamamos o método de deletar o parceiro
        parceiroService.delete(savedParceiro.getChave());

        // Então: Verificamos se o parceiro foi "excluído" logicamente
        Optional<Parceiro> deletedParceiro = parceiroRepository.findByChaveAndExcluidoFalse(savedParceiro.getChave());
        assertFalse(deletedParceiro.isPresent());

        // Verifica se o parceiro ainda existe, mas foi excluído logicamente
        List<ParceiroDTO> parceirosExcluidos = parceiroService.findAll();
        ParceiroDTO parceiroExcluido = parceirosExcluidos.stream()
                .filter(p -> p.getChave().equals(savedParceiro.getChave()))
                .findFirst()
                .orElse(null);

        assertNotNull(parceiroExcluido);
        assertTrue(parceiroExcluido.isExcluido());
    }
}