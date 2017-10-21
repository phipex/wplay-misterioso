package com.ies.misterioso.wplay.repository;

import com.ies.misterioso.wplay.WplayMisteriosoApp;
import com.ies.misterioso.wplay.config.Constants;
import com.ies.misterioso.wplay.config.audit.AuditEventConverter;
import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.domain.PersistentAuditEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the CustomAuditEventRepository customAuditEventRepository class.
 *
 * @see CustomAuditEventRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WplayMisteriosoApp.class)
@Transactional
public class MisteriosoRepositoryTest2 {

    @Autowired
    private MisteriosoRepository misteriosoRepository;

    

    @Before
    public void setup() {
        
    }

    @Test
    public void testFindByCantidadApuestasMax() {
    	String cantidadApuestas = "1";
    	
    	List<Misterioso> misteriososRetornados = misteriosoRepository.findByCantidadApuestasMax(cantidadApuestas);
    	
    	int cantidadRetornada = misteriososRetornados.size();
    	
    	int cantidadEsperada = 2;
    	
    	assertThat(cantidadRetornada).isEqualTo(cantidadEsperada);
    	
    }
    
    
}
