using IntroEF.Domain;
using IntroEF.Utils;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace IntroEF.Db
{
    public class OrderManagementContext : DbContext
    {
        #region Protected Methods

        public DbSet<Customer> Customers => Set<Customer>();
        public DbSet<Order> Orders => Set<Order>();

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

            //modelBuilder.Entity<Customer>()
            //    .OwnsOne(e => e.Address);

            modelBuilder.Entity<Customer>()
                .HasMany(c => c.Orders)
                .WithOne(c => c.Customer)
                .IsRequired(true);
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            base.OnConfiguring(optionsBuilder);
            optionsBuilder.UseSqlServer(ConfigurationUtil.GetConnectionString("OrderDbConnection"))
                .EnableSensitiveDataLogging()
                .LogTo(Console.WriteLine, LogLevel.Information);
        }

        #endregion
    }
}