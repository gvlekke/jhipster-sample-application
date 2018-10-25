package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Templates;
import io.github.jhipster.application.repository.TemplatesRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TemplatesResource REST controller.
 *
 * @see TemplatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TemplatesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplatesMockMvc;

    private Templates templates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplatesResource templatesResource = new TemplatesResource(templatesRepository);
        this.restTemplatesMockMvc = MockMvcBuilders.standaloneSetup(templatesResource)
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
    public static Templates createEntity(EntityManager em) {
        Templates templates = new Templates()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return templates;
    }

    @Before
    public void initTest() {
        templates = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplates() throws Exception {
        int databaseSizeBeforeCreate = templatesRepository.findAll().size();

        // Create the Templates
        restTemplatesMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templates)))
            .andExpect(status().isCreated());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate + 1);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplates.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testTemplates.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTemplatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templatesRepository.findAll().size();

        // Create the Templates with an existing ID
        templates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplatesMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templates)))
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList
        restTemplatesMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get the templates
        restTemplatesMockMvc.perform(get("/api/templates/{id}", templates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(templates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingTemplates() throws Exception {
        // Get the templates
        restTemplatesMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Update the templates
        Templates updatedTemplates = templatesRepository.findById(templates.getId()).get();
        // Disconnect from session so that the updates on updatedTemplates are not directly saved in db
        em.detach(updatedTemplates);
        updatedTemplates
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restTemplatesMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplates)))
            .andExpect(status().isOk());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplates.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testTemplates.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Create the Templates

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplatesMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templates)))
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeDelete = templatesRepository.findAll().size();

        // Get the templates
        restTemplatesMockMvc.perform(delete("/api/templates/{id}", templates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Templates.class);
        Templates templates1 = new Templates();
        templates1.setId(1L);
        Templates templates2 = new Templates();
        templates2.setId(templates1.getId());
        assertThat(templates1).isEqualTo(templates2);
        templates2.setId(2L);
        assertThat(templates1).isNotEqualTo(templates2);
        templates1.setId(null);
        assertThat(templates1).isNotEqualTo(templates2);
    }
}
