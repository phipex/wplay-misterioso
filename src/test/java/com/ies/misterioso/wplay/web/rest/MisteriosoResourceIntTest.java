package com.ies.misterioso.wplay.web.rest;

import com.ies.misterioso.wplay.WplayMisteriosoApp;

import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.repository.MisteriosoRepository;
import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.mapper.MisteriosoMapper;
import com.ies.misterioso.wplay.web.rest.errors.ExceptionTranslator;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ies.misterioso.wplay.domain.enumeration.EstadoMisterioso;
/**
 * Test class for the MisteriosoResource REST controller.
 *
 * @see MisteriosoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WplayMisteriosoApp.class)
public class MisteriosoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ACUMULADO = new BigDecimal(0);
    private static final BigDecimal UPDATED_ACUMULADO = new BigDecimal(1);

    private static final Integer DEFAULT_CANTIDAD_APUESTAS = 0;
    private static final Integer UPDATED_CANTIDAD_APUESTAS = 1;

    private static final Float DEFAULT_PORCENTAJE_TICKET = 1F;
    private static final Float UPDATED_PORCENTAJE_TICKET = 2F;

    private static final BigDecimal DEFAULT_VALOR_BASE_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_BASE_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_BASE_MAX = new BigDecimal(100000000);
    private static final BigDecimal UPDATED_VALOR_BASE_MAX = new BigDecimal(99999999);

    private static final Integer DEFAULT_MINIMO_TICKET = 1;
    private static final Integer UPDATED_MINIMO_TICKET = 2;

    private static final Integer DEFAULT_MAXIMO_TICKET = 1;
    private static final Integer UPDATED_MAXIMO_TICKET = 2;

    private static final String DEFAULT_GANADOR = "AAAAAAAAAA";
    private static final String UPDATED_GANADOR = "BBBBBBBBBB";

    private static final EstadoMisterioso DEFAULT_ESTADO = EstadoMisterioso.ACTIVO;
    private static final EstadoMisterioso UPDATED_ESTADO = EstadoMisterioso.INACTIVO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_APUESTAS_MINIMA = 1;
    private static final Integer UPDATED_CANTIDAD_APUESTAS_MINIMA = 2;

    private static final Integer DEFAULT_CANTIDAD_APUESTAS_MAXIMA = 1;
    private static final Integer UPDATED_CANTIDAD_APUESTAS_MAXIMA = 2;

    @Autowired
    private MisteriosoRepository misteriosoRepository;

    @Autowired
    private MisteriosoMapper misteriosoMapper;

    @Autowired
    private MisteriosoService misteriosoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMisteriosoMockMvc;

    private Misterioso misterioso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MisteriosoResource misteriosoResource = new MisteriosoResource(misteriosoService);
        this.restMisteriosoMockMvc = MockMvcBuilders.standaloneSetup(misteriosoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Misterioso createEntity(EntityManager em) {
        Misterioso misterioso = new Misterioso()
            .nombre(DEFAULT_NOMBRE)
            .acumulado(DEFAULT_ACUMULADO)
            .cantidad_apuestas(DEFAULT_CANTIDAD_APUESTAS)
            .porcentaje_ticket(DEFAULT_PORCENTAJE_TICKET)
            .valor_base_min(DEFAULT_VALOR_BASE_MIN)
            .valor_base_max(DEFAULT_VALOR_BASE_MAX)
            .minimo_ticket(DEFAULT_MINIMO_TICKET)
            .maximo_ticket(DEFAULT_MAXIMO_TICKET)
            .ganador(DEFAULT_GANADOR)
            .estado(DEFAULT_ESTADO)
            .descripcion(DEFAULT_DESCRIPCION)
            .cantidad_apuestas_minima(DEFAULT_CANTIDAD_APUESTAS_MINIMA)
            .cantidad_apuestas_maxima(DEFAULT_CANTIDAD_APUESTAS_MAXIMA);
        return misterioso;
    }

    @Before
    public void initTest() {
        misterioso = createEntity(em);
    }

    @Test
    @Transactional
    public void createMisterioso() throws Exception {
        int databaseSizeBeforeCreate = misteriosoRepository.findAll().size();

        // Create the Misterioso
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);
        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isCreated());

        // Validate the Misterioso in the database
        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeCreate + 1);
        Misterioso testMisterioso = misteriosoList.get(misteriosoList.size() - 1);
        assertThat(testMisterioso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMisterioso.getAcumulado()).isEqualTo(DEFAULT_ACUMULADO);
        assertThat(testMisterioso.getCantidad_apuestas()).isEqualTo(DEFAULT_CANTIDAD_APUESTAS);
        assertThat(testMisterioso.getPorcentaje_ticket()).isEqualTo(DEFAULT_PORCENTAJE_TICKET);
        assertThat(testMisterioso.getValor_base_min()).isEqualTo(DEFAULT_VALOR_BASE_MIN);
        assertThat(testMisterioso.getValor_base_max()).isEqualTo(DEFAULT_VALOR_BASE_MAX);
        assertThat(testMisterioso.getMinimo_ticket()).isEqualTo(DEFAULT_MINIMO_TICKET);
        assertThat(testMisterioso.getMaximo_ticket()).isEqualTo(DEFAULT_MAXIMO_TICKET);
        assertThat(testMisterioso.getGanador()).isEqualTo(DEFAULT_GANADOR);
        assertThat(testMisterioso.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMisterioso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testMisterioso.getCantidad_apuestas_minima()).isEqualTo(DEFAULT_CANTIDAD_APUESTAS_MINIMA);
        assertThat(testMisterioso.getCantidad_apuestas_maxima()).isEqualTo(DEFAULT_CANTIDAD_APUESTAS_MAXIMA);
    }

    @Test
    @Transactional
    public void createMisteriosoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = misteriosoRepository.findAll().size();

        // Create the Misterioso with an existing ID
        misterioso.setId(1L);
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Misterioso in the database
        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setNombre(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidad_apuestasIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setCantidad_apuestas(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPorcentaje_ticketIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setPorcentaje_ticket(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValor_base_minIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setValor_base_min(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValor_base_maxIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setValor_base_max(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinimo_ticketIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setMinimo_ticket(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaximo_ticketIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setMaximo_ticket(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setEstado(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidad_apuestas_minimaIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setCantidad_apuestas_minima(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidad_apuestas_maximaIsRequired() throws Exception {
        int databaseSizeBeforeTest = misteriosoRepository.findAll().size();
        // set the field null
        misterioso.setCantidad_apuestas_maxima(null);

        // Create the Misterioso, which fails.
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        restMisteriosoMockMvc.perform(post("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isBadRequest());

        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMisteriosos() throws Exception {
        // Initialize the database
        misteriosoRepository.saveAndFlush(misterioso);

        // Get all the misteriosoList
        restMisteriosoMockMvc.perform(get("/api/misteriosos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(misterioso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].acumulado").value(hasItem(DEFAULT_ACUMULADO.intValue())))
            .andExpect(jsonPath("$.[*].cantidad_apuestas").value(hasItem(DEFAULT_CANTIDAD_APUESTAS)))
            .andExpect(jsonPath("$.[*].porcentaje_ticket").value(hasItem(DEFAULT_PORCENTAJE_TICKET.doubleValue())))
            .andExpect(jsonPath("$.[*].valor_base_min").value(hasItem(DEFAULT_VALOR_BASE_MIN.intValue())))
            .andExpect(jsonPath("$.[*].valor_base_max").value(hasItem(DEFAULT_VALOR_BASE_MAX.intValue())))
            .andExpect(jsonPath("$.[*].minimo_ticket").value(hasItem(DEFAULT_MINIMO_TICKET)))
            .andExpect(jsonPath("$.[*].maximo_ticket").value(hasItem(DEFAULT_MAXIMO_TICKET)))
            .andExpect(jsonPath("$.[*].ganador").value(hasItem(DEFAULT_GANADOR.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].cantidad_apuestas_minima").value(hasItem(DEFAULT_CANTIDAD_APUESTAS_MINIMA)))
            .andExpect(jsonPath("$.[*].cantidad_apuestas_maxima").value(hasItem(DEFAULT_CANTIDAD_APUESTAS_MAXIMA)));
    }

    @Test
    @Transactional
    public void getMisterioso() throws Exception {
        // Initialize the database
        misteriosoRepository.saveAndFlush(misterioso);

        // Get the misterioso
        restMisteriosoMockMvc.perform(get("/api/misteriosos/{id}", misterioso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(misterioso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.acumulado").value(DEFAULT_ACUMULADO.intValue()))
            .andExpect(jsonPath("$.cantidad_apuestas").value(DEFAULT_CANTIDAD_APUESTAS))
            .andExpect(jsonPath("$.porcentaje_ticket").value(DEFAULT_PORCENTAJE_TICKET.doubleValue()))
            .andExpect(jsonPath("$.valor_base_min").value(DEFAULT_VALOR_BASE_MIN.intValue()))
            .andExpect(jsonPath("$.valor_base_max").value(DEFAULT_VALOR_BASE_MAX.intValue()))
            .andExpect(jsonPath("$.minimo_ticket").value(DEFAULT_MINIMO_TICKET))
            .andExpect(jsonPath("$.maximo_ticket").value(DEFAULT_MAXIMO_TICKET))
            .andExpect(jsonPath("$.ganador").value(DEFAULT_GANADOR.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.cantidad_apuestas_minima").value(DEFAULT_CANTIDAD_APUESTAS_MINIMA))
            .andExpect(jsonPath("$.cantidad_apuestas_maxima").value(DEFAULT_CANTIDAD_APUESTAS_MAXIMA));
    }

    @Test
    @Transactional
    public void getNonExistingMisterioso() throws Exception {
        // Get the misterioso
        restMisteriosoMockMvc.perform(get("/api/misteriosos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMisterioso() throws Exception {
        // Initialize the database
        misteriosoRepository.saveAndFlush(misterioso);
        int databaseSizeBeforeUpdate = misteriosoRepository.findAll().size();

        // Update the misterioso
        Misterioso updatedMisterioso = misteriosoRepository.findOne(misterioso.getId());
        updatedMisterioso
            .nombre(UPDATED_NOMBRE)
            .acumulado(UPDATED_ACUMULADO)
            .cantidad_apuestas(UPDATED_CANTIDAD_APUESTAS)
            .porcentaje_ticket(UPDATED_PORCENTAJE_TICKET)
            .valor_base_min(UPDATED_VALOR_BASE_MIN)
            .valor_base_max(UPDATED_VALOR_BASE_MAX)
            .minimo_ticket(UPDATED_MINIMO_TICKET)
            .maximo_ticket(UPDATED_MAXIMO_TICKET)
            .ganador(UPDATED_GANADOR)
            .estado(UPDATED_ESTADO)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad_apuestas_minima(UPDATED_CANTIDAD_APUESTAS_MINIMA)
            .cantidad_apuestas_maxima(UPDATED_CANTIDAD_APUESTAS_MAXIMA);
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(updatedMisterioso);

        restMisteriosoMockMvc.perform(put("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isOk());

        // Validate the Misterioso in the database
        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeUpdate);
        Misterioso testMisterioso = misteriosoList.get(misteriosoList.size() - 1);
        assertThat(testMisterioso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMisterioso.getAcumulado()).isEqualTo(UPDATED_ACUMULADO);
        assertThat(testMisterioso.getCantidad_apuestas()).isEqualTo(UPDATED_CANTIDAD_APUESTAS);
        assertThat(testMisterioso.getPorcentaje_ticket()).isEqualTo(UPDATED_PORCENTAJE_TICKET);
        assertThat(testMisterioso.getValor_base_min()).isEqualTo(UPDATED_VALOR_BASE_MIN);
        assertThat(testMisterioso.getValor_base_max()).isEqualTo(UPDATED_VALOR_BASE_MAX);
        assertThat(testMisterioso.getMinimo_ticket()).isEqualTo(UPDATED_MINIMO_TICKET);
        assertThat(testMisterioso.getMaximo_ticket()).isEqualTo(UPDATED_MAXIMO_TICKET);
        assertThat(testMisterioso.getGanador()).isEqualTo(UPDATED_GANADOR);
        assertThat(testMisterioso.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMisterioso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMisterioso.getCantidad_apuestas_minima()).isEqualTo(UPDATED_CANTIDAD_APUESTAS_MINIMA);
        assertThat(testMisterioso.getCantidad_apuestas_maxima()).isEqualTo(UPDATED_CANTIDAD_APUESTAS_MAXIMA);
    }

    @Test
    @Transactional
    public void updateNonExistingMisterioso() throws Exception {
        int databaseSizeBeforeUpdate = misteriosoRepository.findAll().size();

        // Create the Misterioso
        MisteriosoDTO misteriosoDTO = misteriosoMapper.toDto(misterioso);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMisteriosoMockMvc.perform(put("/api/misteriosos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(misteriosoDTO)))
            .andExpect(status().isCreated());

        // Validate the Misterioso in the database
        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMisterioso() throws Exception {
        // Initialize the database
        misteriosoRepository.saveAndFlush(misterioso);
        int databaseSizeBeforeDelete = misteriosoRepository.findAll().size();

        // Get the misterioso
        restMisteriosoMockMvc.perform(delete("/api/misteriosos/{id}", misterioso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Misterioso> misteriosoList = misteriosoRepository.findAll();
        assertThat(misteriosoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Misterioso.class);
        Misterioso misterioso1 = new Misterioso();
        misterioso1.setId(1L);
        Misterioso misterioso2 = new Misterioso();
        misterioso2.setId(misterioso1.getId());
        assertThat(misterioso1).isEqualTo(misterioso2);
        misterioso2.setId(2L);
        assertThat(misterioso1).isNotEqualTo(misterioso2);
        misterioso1.setId(null);
        assertThat(misterioso1).isNotEqualTo(misterioso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MisteriosoDTO.class);
        MisteriosoDTO misteriosoDTO1 = new MisteriosoDTO();
        misteriosoDTO1.setId(1L);
        MisteriosoDTO misteriosoDTO2 = new MisteriosoDTO();
        assertThat(misteriosoDTO1).isNotEqualTo(misteriosoDTO2);
        misteriosoDTO2.setId(misteriosoDTO1.getId());
        assertThat(misteriosoDTO1).isEqualTo(misteriosoDTO2);
        misteriosoDTO2.setId(2L);
        assertThat(misteriosoDTO1).isNotEqualTo(misteriosoDTO2);
        misteriosoDTO1.setId(null);
        assertThat(misteriosoDTO1).isNotEqualTo(misteriosoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(misteriosoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(misteriosoMapper.fromId(null)).isNull();
    }
}
