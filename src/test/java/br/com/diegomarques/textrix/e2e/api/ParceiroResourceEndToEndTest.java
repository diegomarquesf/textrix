package br.com.diegomarques.textrix.e2e.api;

import br.com.diegomarques.textrix.domains.dtos.EnderecoDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroDTO;
import br.com.diegomarques.textrix.domains.dtos.ParceiroNovoDTO;
import br.com.diegomarques.textrix.domains.enums.TipoNaturezaJuridica;
import br.com.diegomarques.textrix.domains.enums.TipoParceiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ParceiroResourceEndToEndTest {

    private static  final String BASE_URL = "/api/parceiros";

    @Autowired
    private WebTestClient webClient;

    private EnderecoDTO endereco;
    private ParceiroNovoDTO parceiro;

    @BeforeEach
    void setUp() {
        parceiro = new ParceiroNovoDTO();
        parceiro.setTipoNaturezaJuridica(TipoNaturezaJuridica.PESSOA_FISICA);
        parceiro.setTipoParceiro(TipoParceiro.CLIENTE);
        parceiro.setNome("Nome Teste");
        parceiro.setNomeFantasia("Nome Fantasia Teste");
        parceiro.setCpf("83249694037");

        endereco = new EnderecoDTO();
        endereco.setLogradouro("Rua teste");
        endereco.setNumero("1");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade teste");
        endereco.setEstado("Estado teste");
        endereco.setCep("11111-111");
        parceiro.setEndereco(endereco);
    }

    @Test
    public void testCreateParceiro() {
        webClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(parceiro)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(ParceiroDTO.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody().getChave()).isNotNull();
                    assertThat(response.getResponseBody().getDataCriacao()).isNotNull();
                    assertThat(response.getResponseBody().getTipoNaturezaJuridica().name()).isEqualTo(parceiro.getTipoNaturezaJuridica().name());
                    assertThat(response.getResponseBody().getTipoParceiro().name()).isEqualTo(parceiro.getTipoParceiro().name());
                    assertThat(response.getResponseBody().getNome()).isEqualTo(parceiro.getNome());
                    assertThat(response.getResponseBody().getNomeFantasia()).isEqualTo(parceiro.getNomeFantasia());
                    assertThat(response.getResponseBody().getCpf()).isEqualTo(parceiro.getCpf());
                    assertThat(response.getResponseBody().getEndereco().getLogradouro()).isEqualTo(parceiro.getEndereco().getLogradouro());
                    assertThat(response.getResponseBody().getEndereco().getNumero()).isEqualTo(parceiro.getEndereco().getNumero());
                    assertThat(response.getResponseBody().getEndereco().getBairro()).isEqualTo(parceiro.getEndereco().getBairro());
                    assertThat(response.getResponseBody().getEndereco().getCidade()).isEqualTo(parceiro.getEndereco().getCidade());
                    assertThat(response.getResponseBody().getEndereco().getEstado()).isEqualTo(parceiro.getEndereco().getEstado());
                    assertThat(response.getResponseBody().getEndereco().getCep()).isEqualTo(parceiro.getEndereco().getCep());
                    assertThat(response.getResponseBody().getEndereco().getComplemento()).isNull(); // Assumindo que complemento não foi enviado
                    assertThat(response.getResponseBody().isExcluido()).isFalse();

                    System.out.println("JSON Response Body: " + response);
                });
    }

    @Test
    public void testFindAllParceiros() {
        // Primeiro, criamos um parceiro para garantir que a lista não está vazia
        Long chave = createdParceiroChave();

        webClient.get().uri(BASE_URL + "/todos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ParceiroDTO.class)
                .consumeWith(response -> {
                    List<ParceiroDTO> parceiros = response.getResponseBody();
                    assertThat(response.getResponseBody()).isNotEmpty();
                    assertThat(response.getResponseBody().stream().anyMatch(p -> p.getChave().equals(chave))).isTrue();

                    System.out.println("Lista de Parceiros: " + parceiros.size());
                });
    }

    @Test
    public void testFindByIdParceiro() {
        Long chave = createdParceiroChave();

        webClient.get().uri(BASE_URL + "/ativos/{id}", chave)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParceiroDTO.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody().getChave()).isEqualTo(chave);

                    System.out.println("Chave do Parceiro: " + response.getResponseBody().getChave());
                });
    }

    @Test
    public void testFindByIdParceiroNotFound() {
        webClient.get().uri(BASE_URL + "/ativos/{id}", 999L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testDeleteParceiro() {
        Long chave = createdParceiroChave();

        webClient.delete().uri(BASE_URL + "/{id}", chave)
                .exchange()
                .expectStatus().isNoContent();

        webClient.get().uri(BASE_URL + "/ativos/{id}", chave)
                .exchange()
                .expectStatus().isNotFound();
    }

    private Long createdParceiroChave() {
        return webClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(parceiro)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ParceiroDTO.class)
                .returnResult()
                .getResponseBody()
                .getChave();
    }
}