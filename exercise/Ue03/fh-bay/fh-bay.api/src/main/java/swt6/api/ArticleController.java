package swt6.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import swt6.dal.domain.Article;
import swt6.dto.ArticleDto;
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
}
