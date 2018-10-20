package cloud.fmunozse.demojpaentitylistener.repository;

import cloud.fmunozse.demojpaentitylistener.model.Payment;
import cloud.fmunozse.demojpaentitylistener.model.iso2022.ISOCreditTransferTransaction;
import cloud.fmunozse.demojpaentitylistener.model.iso2022.ISOSuplementaryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTest {


    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void whenInsertPayloadComplete_thenCanSearchAndRetrieve() {

        //Given
        ISOSuplementaryData isoSuplementaryData = new ISOSuplementaryData();
        isoSuplementaryData.setName("supl-name");

        ISOCreditTransferTransaction trn = new ISOCreditTransferTransaction();
        trn.setCreditPartyAgentId("credit");
        trn.setIsoSuplementaryData(isoSuplementaryData);

        Payment payment = new Payment();
        payment.setName("name");
        payment.setPayload(trn);

        //When
        payment = paymentRepository.saveAndFlush(payment);
        payment = paymentRepository.findOne(payment.getId());

        //Then
        assertThat(payment.getName(),is("name"));

        //TODO The client of payment should be know the type of payload
        ISOCreditTransferTransaction trn2 = payment.getPayload();

        assertThat(trn2.getCreditPartyAgentId(), is("credit"));
        assertThat(trn2.getIsoSuplementaryData().getName(), is("supl-name"));
    }
}