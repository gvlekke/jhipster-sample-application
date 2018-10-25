package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.SusteenUsers;
import io.github.jhipster.application.repository.SusteenUsersRepository;
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
 * Test class for the SusteenUsersResource REST controller.
 *
 * @see SusteenUsersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class SusteenUsersResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private SusteenUsersRepository susteenUsersRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSusteenUsersMockMvc;

    private SusteenUsers susteenUsers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SusteenUsersResource susteenUsersResource = new SusteenUsersResource(susteenUsersRepository);
        this.restSusteenUsersMockMvc = MockMvcBuilders.standaloneSetup(susteenUsersResource)
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
    public static SusteenUsers createEntity(EntityManager em) {
        SusteenUsers susteenUsers = new SusteenUsers()
            .username(DEFAULT_USERNAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return susteenUsers;
    }

    @Before
    public void initTest() {
        susteenUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createSusteenUsers() throws Exception {
        int databaseSizeBeforeCreate = susteenUsersRepository.findAll().size();

        // Create the SusteenUsers
        restSusteenUsersMockMvc.perform(post("/api/susteen-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(susteenUsers)))
            .andExpect(status().isCreated());

        // Validate the SusteenUsers in the database
        List<SusteenUsers> susteenUsersList = susteenUsersRepository.findAll();
        assertThat(susteenUsersList).hasSize(databaseSizeBeforeCreate + 1);
        SusteenUsers testSusteenUsers = susteenUsersList.get(susteenUsersList.size() - 1);
        assertThat(testSusteenUsers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSusteenUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSusteenUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createSusteenUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = susteenUsersRepository.findAll().size();

        // Create the SusteenUsers with an existing ID
        susteenUsers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSusteenUsersMockMvc.perform(post("/api/susteen-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(susteenUsers)))
            .andExpect(status().isBadRequest());

        // Validate the SusteenUsers in the database
        List<SusteenUsers> susteenUsersList = susteenUsersRepository.findAll();
        assertThat(susteenUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSusteenUsers() throws Exception {
        // Initialize the database
        susteenUsersRepository.saveAndFlush(susteenUsers);

        // Get all the susteenUsersList
        restSusteenUsersMockMvc.perform(get("/api/susteen-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(susteenUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getSusteenUsers() throws Exception {
        // Initialize the database
        susteenUsersRepository.saveAndFlush(susteenUsers);

        // Get the susteenUsers
        restSusteenUsersMockMvc.perform(get("/api/susteen-users/{id}", susteenUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(susteenUsers.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSusteenUsers() throws Exception {
        // Get the susteenUsers
        restSusteenUsersMockMvc.perform(get("/api/susteen-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSusteenUsers() throws Exception {
        // Initialize the database
        susteenUsersRepository.saveAndFlush(susteenUsers);

        int databaseSizeBeforeUpdate = susteenUsersRepository.findAll().size();

        // Update the susteenUsers
        SusteenUsers updatedSusteenUsers = susteenUsersRepository.findById(susteenUsers.getId()).get();
        // Disconnect from session so that the updates on updatedSusteenUsers are not directly saved in db
        em.detach(updatedSusteenUsers);
        updatedSusteenUsers
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);

        restSusteenUsersMockMvc.perform(put("/api/susteen-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSusteenUsers)))
            .andExpect(status().isOk());

        // Validate the SusteenUsers in the database
        List<SusteenUsers> susteenUsersList = susteenUsersRepository.findAll();
        assertThat(susteenUsersList).hasSize(databaseSizeBeforeUpdate);
        SusteenUsers testSusteenUsers = susteenUsersList.get(susteenUsersList.size() - 1);
        assertThat(testSusteenUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSusteenUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSusteenUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingSusteenUsers() throws Exception {
        int databaseSizeBeforeUpdate = susteenUsersRepository.findAll().size();

        // Create the SusteenUsers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSusteenUsersMockMvc.perform(put("/api/susteen-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(susteenUsers)))
            .andExpect(status().isBadRequest());

        // Validate the SusteenUsers in the database
        List<SusteenUsers> susteenUsersList = susteenUsersRepository.findAll();
        assertThat(susteenUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSusteenUsers() throws Exception {
        // Initialize the database
        susteenUsersRepository.saveAndFlush(susteenUsers);

        int databaseSizeBeforeDelete = susteenUsersRepository.findAll().size();

        // Get the susteenUsers
        restSusteenUsersMockMvc.perform(delete("/api/susteen-users/{id}", susteenUsers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SusteenUsers> susteenUsersList = susteenUsersRepository.findAll();
        assertThat(susteenUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SusteenUsers.class);
        SusteenUsers susteenUsers1 = new SusteenUsers();
        susteenUsers1.setId(1L);
        SusteenUsers susteenUsers2 = new SusteenUsers();
        susteenUsers2.setId(susteenUsers1.getId());
        assertThat(susteenUsers1).isEqualTo(susteenUsers2);
        susteenUsers2.setId(2L);
        assertThat(susteenUsers1).isNotEqualTo(susteenUsers2);
        susteenUsers1.setId(null);
        assertThat(susteenUsers1).isNotEqualTo(susteenUsers2);
    }
}
