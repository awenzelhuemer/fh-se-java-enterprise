using IntroEF.Dal;
using IntroEF.Db;
using IntroEF.Domain;
using IntroEF.Utils;

PrintUtil.PrintTitle("Creating database", character: '=');
using (var db = new OrderManagementContext())
{
    await DatabaseUtil.CreateDatabaseAsync(db, recreate: true);
}
PrintUtil.PrintTitle("Adding customers", character: '=');
await Commands.AddCustomerAsync();

PrintUtil.PrintTitle("Adding orders", character: '=');
await Commands.AddOrdersAsync();

PrintUtil.PrintTitle("Listing customers", character: '=');
await Commands.ListCustomersAsync();