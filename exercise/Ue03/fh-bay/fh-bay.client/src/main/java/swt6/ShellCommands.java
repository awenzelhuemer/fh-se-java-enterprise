package swt6;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestClientException;
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

    private final RestTemplate restTemplate;

    private CustomerDto currentCustomer;

    public ShellCommands(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ShellMethod("Lists all available customers.")
    public void listCustomers() {
        var customers = restTemplate.getForObject("/customers", CustomerDto[].class);

        if (Arrays.stream(Objects.requireNonNull(customers)).findAny().isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Available customers: ");
            for (var customer : customers) {
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

        try {
            this.currentCustomer = restTemplate.getForObject("/customers/filter?firstName={firstName}&lastName={lastName}", CustomerDto.class, params);
            System.out.println("Successfully signed in.");
        } catch (RestClientException ex) {
            System.out.println("Customer not found.");
        }
    }

    @ShellMethod("Get current customer.")
    public void currentCustomer() {
        if (this.currentCustomer != null) {
            System.out.println(this.currentCustomer);
        } else {
            System.out.println("No customer signed in.");
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
        try {
            var articles = restTemplate.getForObject("/articles/filter?term={term}", ArticleDto[].class, params);
            for (var article : Objects.requireNonNull(articles)) {
                System.out.println(article);
            }
        } catch (RestClientException ex) {
            System.out.println("Loading of articles failed.");
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

        try {
            var response = restTemplate.postForObject("/articles", article, ArticleDto.class);
            System.out.printf("Article successfully created with id %d.%n", Objects.requireNonNull(response).getId());
        } catch (RestClientException ex) {
            System.out.println("Article could not be created.");
        }
    }

    @ShellMethod("Close bid.")
    public void closeBid(@ShellOption({"-a", "--article-id"}) long articleId) {
        try {
            restTemplate.put("/articles/close-bid", articleId);
            System.out.println("Bidding for article successfully closed.");
        } catch (RestClientException ex) {
            System.out.println("Bidding could not be closed.");
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

        try {
            var response = restTemplate.postForObject("/bids", bid, BidDto.class);
            System.out.println("Bid successfully created.");
        } catch (RestClientException ex) {
            System.out.println("Bid could not be created.");
        }
    }

    public Availability addBidAvailability() {
        return currentCustomer != null ? Availability.available() : Availability.unavailable("Must be signed in first.");
    }

    public Availability addArticleAvailability() {
        return currentCustomer != null ? Availability.available() : Availability.unavailable("Must be signed in first.");
    }

    public Availability closeBidAvailability() {
        return currentCustomer != null ? Availability.available() : Availability.unavailable("Must be signed in first.");
    }
}
