package com.ies.misterioso.wplay.web.rest;

import com.ies.misterioso.wplay.WplayMisteriosoApp;

import com.ies.misterioso.wplay.domain.TicketGanador;
import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.repository.TicketGanadorRepository;
import com.ies.misterioso.wplay.service.TicketGanadorService;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import com.ies.misterioso.wplay.service.mapper.TicketGanadorMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ies.misterioso.wplay.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;
/**
 * Test class for the TicketGanadorResource REST controller.
 *
 * @see TicketGanadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WplayMisteriosoApp.class)
public class TicketGanadorResourceIntTest {

    private static final EstadoGanador DEFAULT_ESTADO = EstadoGanador.PENDIENTE;
    private static final EstadoGanador UPDATED_ESTADO = EstadoGanador.GANADO;

    private static final BigDecimal DEFAULT_VALOR_GANADO = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR_GANADO = new BigDecimal(1);

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDICE_TICKET_MISTERIOSO = 1;
    private static final Integer UPDATED_INDICE_TICKET_MISTERIOSO = 2;

    @Autowired
    private TicketGanadorRepository ticketGanadorRepository;

    @Autowired
    private TicketGanadorMapper ticketGanadorMapper;

    @Autowired
    private TicketGanadorService ticketGanadorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTicketGanadorMockMvc;

    private TicketGanador ticketGanador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TicketGanadorResource ticketGanadorResource = new TicketGanadorResource(ticketGanadorService);
        this.restTicketGanadorMockMvc = MockMvcBuilders.standaloneSetup(ticketGanadorResource)
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
    public static TicketGanador createEntity(EntityManager em) {
        TicketGanador ticketGanador = new TicketGanador()
            .estado(DEFAULT_ESTADO)
            .valor_ganado(DEFAULT_VALOR_GANADO)
            .fecha(DEFAULT_FECHA)
            .descripcion(DEFAULT_DESCRIPCION)
            .indice_ticket_misterioso(DEFAULT_INDICE_TICKET_MISTERIOSO);
        // Add required entity
        Misterioso misterioso = MisteriosoResourceIntTest.createEntity(em);
        em.persist(misterioso);
        em.flush();
        ticketGanador.setMisterioso(misterioso);
        return ticketGanador;
    }

    @Before
    public void initTest() {
        ticketGanador = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketGanador() throws Exception {
        int databaseSizeBeforeCreate = ticketGanadorRepository.findAll().size();

        // Create the TicketGanador
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorMapper.toDto(ticketGanador);
        restTicketGanadorMockMvc.perform(post("/api/ticket-ganadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketGanadorDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketGanador in the database
        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeCreate + 1);
        TicketGanador testTicketGanador = ticketGanadorList.get(ticketGanadorList.size() - 1);
        assertThat(testTicketGanador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTicketGanador.getValor_ganado()).isEqualTo(DEFAULT_VALOR_GANADO);
        assertThat(testTicketGanador.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTicketGanador.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTicketGanador.getIndice_ticket_misterioso()).isEqualTo(DEFAULT_INDICE_TICKET_MISTERIOSO);
    }

    @Test
    @Transactional
    public void createTicketGanadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketGanadorRepository.findAll().size();

        // Create the TicketGanador with an existing ID
        ticketGanador.setId(1L);
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorMapper.toDto(ticketGanador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketGanadorMockMvc.perform(post("/api/ticket-ganadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketGanadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketGanador in the database
        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketGanadorRepository.findAll().size();
        // set the field null
        ticketGanador.setEstado(null);

        // Create the TicketGanador, which fails.
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorMapper.toDto(ticketGanador);

        restTicketGanadorMockMvc.perform(post("/api/ticket-ganadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketGanadorDTO)))
            .andExpect(status().isBadRequest());

        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTicketGanadors() throws Exception {
        // Initialize the database
        ticketGanadorRepository.saveAndFlush(ticketGanador);

        // Get all the ticketGanadorList
        restTicketGanadorMockMvc.perform(get("/api/ticket-ganadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketGanador.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].valor_ganado").value(hasItem(DEFAULT_VALOR_GANADO.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].indice_ticket_misterioso").value(hasItem(DEFAULT_INDICE_TICKET_MISTERIOSO)));
    }

    @Test
    @Transactional
    public void getTicketGanador() throws Exception {
        // Initialize the database
        ticketGanadorRepository.saveAndFlush(ticketGanador);

        // Get the ticketGanador
        restTicketGanadorMockMvc.perform(get("/api/ticket-ganadors/{id}", ticketGanador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticketGanador.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.valor_ganado").value(DEFAULT_VALOR_GANADO.intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.indice_ticket_misterioso").value(DEFAULT_INDICE_TICKET_MISTERIOSO));
    }

    @Test
    @Transactional
    public void getNonExistingTicketGanador() throws Exception {
        // Get the ticketGanador
        restTicketGanadorMockMvc.perform(get("/api/ticket-ganadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketGanador() throws Exception {
        // Initialize the database
        ticketGanadorRepository.saveAndFlush(ticketGanador);
        int databaseSizeBeforeUpdate = ticketGanadorRepository.findAll().size();

        // Update the ticketGanador
        TicketGanador updatedTicketGanador = ticketGanadorRepository.findOne(ticketGanador.getId());
        updatedTicketGanador
            .estado(UPDATED_ESTADO)
            .valor_ganado(UPDATED_VALOR_GANADO)
            .fecha(UPDATED_FECHA)
            .descripcion(UPDATED_DESCRIPCION)
            .indice_ticket_misterioso(UPDATED_INDICE_TICKET_MISTERIOSO);
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorMapper.toDto(updatedTicketGanador);

        restTicketGanadorMockMvc.perform(put("/api/ticket-ganadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketGanadorDTO)))
            .andExpect(status().isOk());

        // Validate the TicketGanador in the database
        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeUpdate);
        TicketGanador testTicketGanador = ticketGanadorList.get(ticketGanadorList.size() - 1);
        assertThat(testTicketGanador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTicketGanador.getValor_ganado()).isEqualTo(UPDATED_VALOR_GANADO);
        assertThat(testTicketGanador.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTicketGanador.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTicketGanador.getIndice_ticket_misterioso()).isEqualTo(UPDATED_INDICE_TICKET_MISTERIOSO);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketGanador() throws Exception {
        int databaseSizeBeforeUpdate = ticketGanadorRepository.findAll().size();

        // Create the TicketGanador
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorMapper.toDto(ticketGanador);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketGanadorMockMvc.perform(put("/api/ticket-ganadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketGanadorDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketGanador in the database
        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicketGanador() throws Exception {
        // Initialize the database
        ticketGanadorRepository.saveAndFlush(ticketGanador);
        int databaseSizeBeforeDelete = ticketGanadorRepository.findAll().size();

        // Get the ticketGanador
        restTicketGanadorMockMvc.perform(delete("/api/ticket-ganadors/{id}", ticketGanador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TicketGanador> ticketGanadorList = ticketGanadorRepository.findAll();
        assertThat(ticketGanadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketGanador.class);
        TicketGanador ticketGanador1 = new TicketGanador();
        ticketGanador1.setId(1L);
        TicketGanador ticketGanador2 = new TicketGanador();
        ticketGanador2.setId(ticketGanador1.getId());
        assertThat(ticketGanador1).isEqualTo(ticketGanador2);
        ticketGanador2.setId(2L);
        assertThat(ticketGanador1).isNotEqualTo(ticketGanador2);
        ticketGanador1.setId(null);
        assertThat(ticketGanador1).isNotEqualTo(ticketGanador2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketGanadorDTO.class);
        TicketGanadorDTO ticketGanadorDTO1 = new TicketGanadorDTO();
        ticketGanadorDTO1.setId(1L);
        TicketGanadorDTO ticketGanadorDTO2 = new TicketGanadorDTO();
        assertThat(ticketGanadorDTO1).isNotEqualTo(ticketGanadorDTO2);
        ticketGanadorDTO2.setId(ticketGanadorDTO1.getId());
        assertThat(ticketGanadorDTO1).isEqualTo(ticketGanadorDTO2);
        ticketGanadorDTO2.setId(2L);
        assertThat(ticketGanadorDTO1).isNotEqualTo(ticketGanadorDTO2);
        ticketGanadorDTO1.setId(null);
        assertThat(ticketGanadorDTO1).isNotEqualTo(ticketGanadorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ticketGanadorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ticketGanadorMapper.fromId(null)).isNull();
    }
}
