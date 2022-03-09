package swt6.dal.dao.impl;

import swt6.dal.dao.PaymentDao;
import swt6.dal.domain.Payment;

public class PaymentDaoImpl extends BaseDaoImpl<Payment> implements PaymentDao {

    @Override
    protected Class<Payment> getEntityType() {
        return Payment.class;
    }
}
