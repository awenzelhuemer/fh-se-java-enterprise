package swt6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import swt6.dal.domain.Article;
import swt6.dto.ArticleDto;
import swt6.exceptions.InvalidOperationException;
import swt6.exceptions.NotFoundException;
import swt6.logic.ArticleLogic;

import java.util.List;

@RestController
@RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired
    private ArticleLogic articleLogic;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inserts article", description = "Inserts new article.")
    @ApiResponse(responseCode = "200", description = "Success")
    public ArticleDto insert(@RequestBody ArticleDto article) {
        var entity = mapper.map(article, Article.class);
        entity = articleLogic.insert(entity);
        article = mapper.map(entity, ArticleDto.class);
        return article;
    }

    @GetMapping("/filter")
    @Operation(summary = "Get Articles by given filters", description = "Returns list of articles.")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<ArticleDto> filter(String term) {
        var articles = articleLogic.findByNameAndDescription(term);
        return articles.stream().map(a -> mapper.map(a, ArticleDto.class)).toList();
    }

    @PutMapping("/close-bid")
    @Operation(summary = "Ends bidding", description = "Ends time for bidding immediately.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void finalizeBidding(@RequestBody long articleId) {
        if (!articleLogic.exists(articleId)) {
            throw new NotFoundException(articleId);
        }

        try {
            articleLogic.finalizeBidding(articleId);
        } catch (IllegalArgumentException ex) {
            throw new InvalidOperationException(ex.getMessage());
        }
    }
}
