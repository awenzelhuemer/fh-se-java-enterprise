using Microsoft.EntityFrameworkCore;
using OrderManagement.Domain;

namespace OrderManagement.Dal
{
    public class OrderManagementContext : DbContext
    {
        #region Public Constructors

        public OrderManagementContext(DbContextOptions options) : base(options)
        {
        }

        #endregion

        #region Public Properties

        public DbSet<Customer> Customers => Set<Customer>();
        public DbSet<Order> Orders => Set<Order>();

        #endregion

        #region Protected Methods

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Customer>()
                .ToTable("TBL_Customer")
                .HasKey(c => c.Id);

            modelBuilder.Entity<Customer>()
                .Property(c => c.Name)
                .HasColumnName("COL_Name")
                .IsRequired();

            modelBuilder.Entity<Customer>()
                .Property(c => c.TotalRevenue)
                .HasColumnType("decimal(18, 2");

            modelBuilder.Entity<Customer>()
                .Ignore(c => c.TotalRevenue);

            modelBuilder.Entity<Customer>()
                .OwnsOne(e => e.Address);

            modelBuilder.Entity<Customer>()
                .HasMany(c => c.Orders)
                .WithOne(c => c.Customer)
                .IsRequired(true);
        }

        #endregion
    }
}