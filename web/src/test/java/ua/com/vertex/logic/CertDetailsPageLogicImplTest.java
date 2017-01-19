package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertDetailsPageLogicImplTest {

    // todo : inspect and add/remove tests according to implemented code refactoring

    @Autowired
    private CertificateDaoInf certificateDao;

    private CertDetailsPageLogicImpl logic;

    private static final int EXISTING_USER_ID = 22;
    private static final int EXISTING_CERT_ID = 222;
    private static final int NOT_EXISTING_ID = Integer.MIN_VALUE;

    @Before
    public void setUp() {
        logic = new CertDetailsPageLogicImpl(certificateDao);
    }

    @Test
    public void certificateOptionalForCertificateStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = logic.getCertificateDetails(EXISTING_CERT_ID);
        assertNotNull(optional);
        assertEquals(EXISTING_CERT_ID, optional.get().getCertificationId());
    }

    @Test
    public void certificateOptionalForCertificateNotStoredInDBShouldBeReturned() {
        Optional<Certificate> optional = logic.getCertificateDetails(NOT_EXISTING_ID);
        assertNotNull(optional);
        assertEquals(new Certificate(), optional.orElse(new Certificate()));
    }

//    @Test
//    public void userOptionalForUserStoredInDBShouldBeReturned() {
//        Optional<User> optional = logic.getUserById(EXISTING_USER_ID);
//        assertNotNull(optional);
//        assertEquals(EXISTING_USER_ID, optional.get().getUserId());
//    }
//
//    @Test
//    public void userOptionalForUserNotStoredInDBShouldBeReturned() {
//        Optional<User> optional = logic.getUserById(NOT_EXISTING_ID);
//        assertNotNull(optional);
//        assertEquals(new User(), optional.orElse(new User()));
//    }
}
