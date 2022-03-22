package swt6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.dal.domain.Address;
import swt6.dal.domain.Article;
import swt6.dal.domain.BidStatus;
import swt6.dal.domain.Customer;
import swt6.logic.ArticleLogic;
import swt6.logic.CustomerLogic;

import java.time.LocalDateTime;
import java.util.Optional;

@ShellComponent
public class ShellCommands {

    @Autowired
    private CustomerLogic customerLogic;

    @Autowired
    private ArticleLogic articleLogic;

    private Optional<Customer> currentCustomer = Optional.empty();

    @ShellMethod("Inserts new customer.")
    public void addCustomer(
            @ShellOption({"-fn", "--first-name"}) String firstName,
            @ShellOption({"-ln", "--last-name"}) String lastName,
            @ShellOption(defaultValue = "4070") String billingZipCode,
            @ShellOption(defaultValue = "Pupping") String billingCity,
            @ShellOption(defaultValue = "Waschpoint 10") String billingStreet,
            @ShellOption(defaultValue = "4070") String deliveryZipCode,
            @ShellOption(defaultValue = "Pupping") String deliveryCity,
            @ShellOption(defaultValue = "Waschpoint 10") String deliveryStreet
    ) {
        var customer = new Customer(firstName, lastName,
                new Address(billingZipCode, billingCity, billingStreet),
                new Address(deliveryZipCode, deliveryCity, deliveryStreet));
        customerLogic.insert(customer);
    }

    @ShellMethod("Lists all available customers.")
    public void listCustomers() {
        StringBuilder builder = new StringBuilder();

        var customers = customerLogic.findAll();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Available customers: ");
            customers.forEach(System.out::println);
        }
    }

    @ShellMethod("Sign in customer.")
    public void signIn(
            @ShellOption({"-fn", "--first-name"}) String firstName,
            @ShellOption({"-ln", "--last-name"}) String lastName) {
        this.currentCustomer = customerLogic.findByFirstnameAndLastname(firstName, lastName);
    }

    @ShellMethod("Get current customer.")
    public void currentCustomer() {
        if(this.currentCustomer.isPresent()) {
            System.out.println(this.currentCustomer.get());
        } else {
            System.out.println("No customer signed in");
        }
    }

    @ShellMethod("Sign out customer.")
    public void signOut() {
        this.currentCustomer = Optional.empty();
    }

    private boolean isSignedIn() {
        return currentCustomer.isPresent();
    }


    @ShellMethod("Add new article.")
    public void addArticle(
            @ShellOption({"-n", "--name"}) String name,
            @ShellOption(value = {"-d", "--description"}, defaultValue = "No description.") String description,
            @ShellOption(value = {"-p", "--price"}) double price
    ) {
        var article = new Article(
                name,
                description,
                price,
                0.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(6),
                currentCustomer.orElse(null),
                null,
                null,
                BidStatus.Offered
        );

        articleLogic.insert(article);
    }

    @ShellMethod("Show all articles.")
    public void listArticles(@ShellOption({"-t", "--term"}) String searchTerm) {
        var articles = articleLogic.findByNameAndDescription(searchTerm);
        articles.forEach(System.out::println);
    }

    public Availability addArticleAvailability() {
        return isSignedIn() ? Availability.available() : Availability.unavailable("Must be signed in first");
    }
}
