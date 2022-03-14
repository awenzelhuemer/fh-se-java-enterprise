package swt6.domain.jpa;

import swt6.domain.Address;
import swt6.domain.Channel;
import swt6.domain.Employee;
import swt6.util.JpaUtil;

public class Client {
    public static void main(String[] args) {

        JpaUtil.getEntityManagerFactory();

        try {

            Channel channel = new Channel("Dis is a channel.");
            channel = JpaUtil.getTransactedEntityManager().merge(channel);

            Employee employee = new Employee();
            employee.setName("Andi");
            employee.setAddress1(new Address("Waschpoint 10 4070 Pupping"));

            employee = JpaUtil.getTransactedEntityManager().merge(employee);
            employee.addChannel(channel);

            JpaUtil.commit();

            System.out.println(employee);

        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }

        JpaUtil.closeEntityManagerFactory();
    }
}
