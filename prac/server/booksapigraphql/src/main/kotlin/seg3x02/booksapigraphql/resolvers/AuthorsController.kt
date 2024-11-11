package seg3x02.booksapigraphql.resolvers

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.booksapigraphql.entity.Author
import seg3x02.booksapigraphql.repository.AuthorRepository
import seg3x02.booksapigraphql.resolvers.types.CreateAuthorInput

@Controller
class AuthorsController(
    val mongoOperations: MongoOperations,
    private val authorRepository: AuthorRepository
) {
    @QueryMapping
    fun authors(@Argument bookNumber: Int): List<Author> {
        val query = Query()
        query.addCriteria(Criteria.where("bookNumber").`is`(bookNumber))
        return mongoOperations.find(query, Author::class.java)
    }

    @MutationMapping
    fun newAuthor(@Argument("createAuthorInput") input: CreateAuthorInput): Author {
        val author = Author(input.bookNumber, input.firstName, input.lastName)
        authorRepository.save(author)
        return author
    }
}