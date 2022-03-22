package swt6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt6.dal.domain.Customer;
import swt6.dto.BidDto;
import swt6.exceptions.InvalidOperationException;
import swt6.logic.ArticleLogic;

@RestController
@RequestMapping(value = "/bids", produces = MediaType.APPLICATION_JSON_VALUE)
public class BidController {

    @Autowired
    private ArticleLogic articleLogic;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Operation(summary = "Add bid", description = "Inserts new bid.")
    public void add(@RequestBody BidDto bid) {
        try {
            var customer = mapper.map(bid.getCustomer(), Customer.class);
            articleLogic.addBid(bid.getArticleId(), bid.getAmount(), customer);
        } catch (IllegalArgumentException ex) {

            throw new InvalidOperationException(ex.getMessage());
        }
    }
}
