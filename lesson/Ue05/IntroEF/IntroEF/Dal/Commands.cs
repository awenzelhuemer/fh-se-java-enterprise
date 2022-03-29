using IntroEF.Db;
using IntroEF.Domain;
using Microsoft.EntityFrameworkCore;

namespace IntroEF.Dal
{
    public class Commands
    {
        #region Public Methods

        public static async Task AddCustomerAsync()
        {
            using var db = new OrderManagementContext();

            var customer1 = new Customer("Mayr Immobilien", Rating.B)
            {
                Address = new Address(4020, "Linz", "Wiener Straße 9")
            };

            var customer2 = new Customer("Software Solutions", Rating.A)
            {
                Address = new Address(4020, "Linz", "Wiener Straße 9")
            };

            await db.Customers.AddRangeAsync(customer1, customer2);
            await db.SaveChangesAsync();
        }

        public static async Task AddOrdersAsync()
        {
            using var db = new OrderManagementContext();

            var customer = await db.Customers.OrderBy(c => c.Id).FirstOrDefaultAsync();
            if(customer is null)
            {
                return;
            }
            var order1 = new Order("Surface Book 3", new DateTime(2022, 1, 1), 2850m)
            {
                Customer = customer
            };
            var order2 = new Order("Dell Monitor", new DateTime(2022, 2, 2), 250m)
            {
                Customer = customer
            };
            await db.Orders.AddRangeAsync(order1, order2);
            await db.SaveChangesAsync();
        }

        public static async Task ListCustomersAsync()
        {
            using var db = new OrderManagementContext();
            var customers = await db.Customers
                                        .AsNoTracking()
                                        .Include(c => c.Address)
                                        .Include(c => c.Orders)
                                        .ToListAsync();

            foreach (var customer in customers)
            {
                Console.WriteLine(customer);

                if(customer.Address is not null)
                {
                    Console.WriteLine($"  Address: {customer.Address}");
                }

                if(customer.Orders.Count > 0)
                {
                    Console.WriteLine("  Orders:");
                    foreach (var order in customer.Orders)
                    {
                        Console.WriteLine($"    {order}");
                    }
                }
            }
        }

        #endregion
    }
}