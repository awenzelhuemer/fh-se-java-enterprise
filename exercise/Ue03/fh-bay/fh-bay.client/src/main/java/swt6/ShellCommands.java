package swt6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;
import swt6.dto.ArticleDto;
import swt6.dto.BidDto;
import swt6.dto.BidStatus;
import swt6.dto.CustomerDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ShellComponent
public class ShellCommands {

    @Autowired
    private RestTemplate restTemplate;

    private CustomerDto currentCustomer;

    @ShellMethod("Lists all available customers.")
    public void listCustomers() {
        var response = restTemplate.getForEntity("/customers", CustomerDto[].class);

        var customers = response.getBody();
        if (Arrays.stream(Objects.requireNonNull(customers)).findAny().isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Available customers: ");
            for (var customer :
                    customers) {
                System.out.println(customer);
            }
        }
    }

    @ShellMethod("Sign in customer.")
    public void signIn(
            @ShellOption({"-fn", "--first-name"}) String firstName,
            @ShellOption({"-ln", "--last-name"}) String lastName) {

        Map<String, String> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        var response = restTemplate.getForEntity("/customers/filter?firstName={firstName}&lastName={lastName}", CustomerDto.class, params);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            System.out.println("Customer not found.");
        } else {
            System.out.println("Successfully signed in");
            this.currentCustomer = response.getBody();
        }
    }

    @ShellMethod("Get current customer.")
    public void currentCustomer() {
        if (this.currentCustomer != null) {
            System.out.println(this.currentCustomer);
        } else {
            System.out.println("No customer signed in");
        }
    }

    @ShellMethod("Sign out customer.")
    public void signOut() {
        if (this.currentCustomer != null) {
            this.currentCustomer = null;
        } else {
            System.out.println("No customer signed in");
        }
    }

    @ShellMethod("Lists all available articles.")
    public void listArticles(@ShellOption(value = {"-t", "--term"}, defaultValue = "") String term) {
        Map<String, String> params = new HashMap<>();
        params.put("term", term);
        var response = restTemplate.getForEntity("/articles/filter?term={term}", ArticleDto[].class, params);

        for (var article : Objects.requireNonNull(response.getBody())) {
            System.out.println(article);
        }
    }


    @ShellMethod("Add new article.")
    public void addArticle(
            @ShellOption({"-n", "--name"}) String name,
            @ShellOption(value = {"-d", "--description"}, defaultValue = "No description.") String description,
            @ShellOption(value = {"-p", "--price"}) double price
    ) {
        var article = new ArticleDto(
                name,
                description,
                price,
                0.0,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(6),
                currentCustomer,
                null,
                BidStatus.Offered
        );

        var response = restTemplate.postForEntity("/articles", article, ArticleDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Article successfully created.");
        } else {
            System.out.println("Article could not be created.");
        }
    }

    @ShellMethod("Add bid.")
    public void addBid(
            @ShellOption({"-a", "--article-id"}) Long articleId,
            @ShellOption(value = {"-p", "--price"}) double price
    ) {
        var bid = new BidDto(
                currentCustomer,
                price,
                articleId
        );

        var response = restTemplate.postForEntity("/bids", bid, Object.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Bid successfully created.");
        } else {
            System.out.println("Bid could not be created");
        }
    }

    public Availability addBidAvailability() {
        return currentCustomer != null ? Availability.available() : Availability.unavailable("Must be signed in first.");
    }

    public Availability addArticleAvailability() {
        return currentCustomer != null ? Availability.available() : Availability.unavailable("Must be signed in first.");
    }
}
