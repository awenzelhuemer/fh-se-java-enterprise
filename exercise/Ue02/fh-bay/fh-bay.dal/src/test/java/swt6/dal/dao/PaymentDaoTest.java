package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import swt6.dal.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentDaoTest extends BaseTest {

    @Test
    public void insert_withValidData_insertsPayment() {

        var paymentDao = DaoFactory.getPaymentDao();

        BankAccountPayment bankAccount = new BankAccountPayment("Andreas Wenzelhuemer", "1234", "AT49 1949932");
        CreditCardPayment creditCard = new CreditCardPayment("Andreas Wenzelhuemer", "1234567", 9, 24);

        paymentDao.insert(bankAccount);
        paymentDao.insert(creditCard);

        var result = paymentDao.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void delete_withExistingPayment_deletesPayment() {
        var paymentDao = DaoFactory.getPaymentDao();

        Payment bankAccount = new BankAccountPayment("Andreas Wenzelhuemer", "1234", "AT49 1949932");
        Payment creditCard = new CreditCardPayment("Andreas Wenzelhuemer", "1234567", 9, 24);

        bankAccount = paymentDao.insert(bankAccount);
        creditCard = paymentDao.insert(creditCard);

        paymentDao.delete(bankAccount.getId());
        paymentDao.delete(creditCard.getId());

        assertNull(paymentDao.findById(bankAccount.getId()));
        assertNull(paymentDao.findById(creditCard.getId()));
    }

    @Test
    public void delete_withNoExistingPayment_throwsException() {
        PaymentDao dao = DaoFactory.getPaymentDao();
        assertThrows(Exception.class, () -> dao.delete(1));
    }

    @Test
    public void findById_withNoExistingPayment_returnsNull() {
        PaymentDao dao = DaoFactory.getPaymentDao();
        assertNull(dao.findById(1));
    }

    @Test
    public void findById_withExistingPayment_returnsPayment() {
        var paymentDao = DaoFactory.getPaymentDao();

        Payment bankAccount = new BankAccountPayment("Andreas Wenzelhuemer", "1234", "AT49 1949932");
        Payment creditCard = new CreditCardPayment("Andreas Wenzelhuemer", "1234567", 9, 24);

        bankAccount = paymentDao.insert(bankAccount);
        creditCard = paymentDao.insert(creditCard);

        assertNotNull(paymentDao.findById(bankAccount.getId()));
        assertNotNull(paymentDao.findById(creditCard.getId()));
    }
}
