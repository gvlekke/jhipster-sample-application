package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Clients;
import io.github.jhipster.application.repository.ClientsRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientsResource REST controller.
 *
 * @see ClientsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ClientsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientsMockMvc;

    private Clients clients;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientsResource clientsResource = new ClientsResource(clientsRepository);
        this.restClientsMockMvc = MockMvcBuilders.standaloneSetup(clientsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .name(DEFAULT_NAME);
        return clients;
    }

    @Before
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    public void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createClientsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients with an existing ID
        clients.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        // Disconnect from session so that the updates on updatedClients are not directly saved in db
        em.detach(updatedClients);
        updatedClients
            .name(UPDATED_NAME);

        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClients)))
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Create the Clients

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Get the clients
        restClientsMockMvc.perform(delete("/api/clients/{id}", clients.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clients.class);
        Clients clients1 = new Clients();
        clients1.setId(1L);
        Clients clients2 = new Clients();
        clients2.setId(clients1.getId());
        assertThat(clients1).isEqualTo(clients2);
        clients2.setId(2L);
        assertThat(clients1).isNotEqualTo(clients2);
        clients1.setId(null);
        assertThat(clients1).isNotEqualTo(clients2);
    }
}
