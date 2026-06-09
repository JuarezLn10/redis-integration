package com.juarezln.testing.redisintegration.inventories.interfaces.rest.controllers;

import com.juarezln.testing.redisintegration.inventories.application.services.ProductCommandService;
import com.juarezln.testing.redisintegration.inventories.application.services.ProductQueryService;
import com.juarezln.testing.redisintegration.inventories.application.services.StockCommandService;
import com.juarezln.testing.redisintegration.inventories.application.services.StockQueryService;
import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetAllProductsQuery;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetProductByIdQuery;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers.*;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.*;
import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.application.result.Result;
import com.juarezln.testing.redisintegration.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class responsible for handling HTTP requests related to product management in the inventory system. It provides endpoints for creating new products, retrieving product information, updating product details, and managing stock levels. The controller interacts with the application layer through command and query services to perform the necessary operations and returns appropriate HTTP responses based on the outcome of each request.
 *
 * <p>
 * This controller implements the following endpoints:
 *      <li> POST /api/v1/products: Create a new product in the inventory system. </li>
 *      <li> GET /api/v1/products/{productId}: Retrieve a product by its unique identifier, including its stock information. </li>
 *      <li> GET /api/v1/products: Retrieve a list of all products along with their stock information. </li>
 *      <li> PATCH /api/v1/products/{productId}/price: Update the price of an existing product. </li>
 *      <li> POST /api/v1/products/{productId}/additions: Add stock to an existing product in the inventory. </li>
 *      <li> POST /api/v1/products/{productId}/subtractions: Subtract stock from an existing product in the inventory. </li>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Endpoints for managing products")
public class ProductsController {

    // The ProductCommandService is responsible for handling commands related to product management in the inventory system. It allows the controller to perform operations such as creating new products, updating existing product information, and managing product-related actions, enabling it to maintain the integrity and consistency of product data within the application.
    private final ProductCommandService productCommandService;

    // The ProductQueryService is responsible for handling queries related to product information in the inventory management system. It allows the controller to retrieve product details, such as name, description, price, and other relevant information, enabling it to provide comprehensive product data to clients when requested.
    private final ProductQueryService productQueryService;

    // The StockCommandService is responsible for handling commands related to stock management in the inventory system. It allows the controller to perform operations such as adding or subtracting stock for products, enabling it to manage inventory levels effectively.
    private final StockCommandService stockCommandService;

    // The StockQueryService is responsible for handling queries related to stock information in the inventory management system. It allows the controller to retrieve stock levels and other related data for products, enabling it to provide comprehensive product information that includes both product details and current stock status.
    private final StockQueryService stockQueryService;

    // Constructor for dependency injection of the command and query services related to products and stock management in the inventory system. This constructor allows the controller to interact with the application layer to perform operations such as creating products, retrieving product information, and managing stock levels.
    public ProductsController(
            ProductCommandService productCommandService,
            ProductQueryService productQueryService,
            StockCommandService stockCommandService,
            StockQueryService stockQueryService
    ) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
        this.stockCommandService = stockCommandService;
        this.stockQueryService = stockQueryService;
    }

    /**
     * Endpoint to create a new product. This endpoint allows clients to register a new product in the inventory management system by providing the necessary product information in the request body.
     *
     * @param resource the request body containing the product information required to create a new product, which must include valid values for all required fields such as name, description, price, and initial stock quantity
     * @return a ResponseEntity containing the created product information if the operation is successful, or an appropriate error response if the input data is invalid or if there are authentication/authorization issues
     */
    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Registers a new product in the inventory system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<?> createProduct(@RequestBody RegisterProductResource resource) {
        var registerProductCommand = RegisterProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = productCommandService.handle(registerProductCommand)
                .flatMap(productId -> productQueryService.handle(new GetProductByIdQuery(productId))
                        .<Result<Product, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Product", productId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Endpoint to retrieve a product by its unique identifier. This endpoint allows clients to fetch detailed information about a specific product in the inventory management system, including its current stock levels.
     *
     * @param productId the unique identifier of the product to be retrieved, which must be a valid long value
     * @return a ResponseEntity containing the product information with stock details if the operation is successful, or an appropriate error response if the product is not found or if there are authentication/authorization issues
     */
    @GetMapping("/{productId}")
    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a product along with its stock information by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductWithStockResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<ProductWithStockResource> getProductById(
            @PathVariable
            @Parameter(description = "ID of the product to retrieve", example = "123e4567-e89b-12d3-a456-426614174000")
            Long productId
    ) {
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var resultPair = stockQueryService.handle(getProductByIdQuery);
        if (resultPair.getLeft().isEmpty()) return ResponseEntity.notFound().build();
        if (resultPair.getRight().isEmpty()) return ResponseEntity.notFound().build();
        var productEntity = resultPair.getLeft().get();
        var stockEntity = resultPair.getRight().get();
        var pair = Pair.of(productEntity, stockEntity);
        var productResource = ProductWithStockResourceFromEntitiesAssembler.toResourceFromEntity(pair);
        return ResponseEntity.ok(productResource);
    }

    /**
     * Endpoint to retrieve all products along with their stock information. This endpoint allows clients to fetch a list of all products available in the inventory management system, including details about their current stock levels.
     *
     * @return a ResponseEntity containing a list of all products with their stock information if the operation is successful, or an appropriate error response if there are authentication/authorization issues
     */
    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products along with their stock information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductWrapperResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<ProductWrapperResource> getAllProducts() {
        var result = stockQueryService.handle(new GetAllProductsQuery());
        var productResources = ProductWrapperFromEntitiesAssembler.toWrapperFromEntities(result.getLeft(), result.getRight());
        return ResponseEntity.ok(productResources);
    }

    /**
     * Endpoint to update the price of an existing product. This endpoint allows clients to modify the price of a product in the inventory management system by providing the product's unique identifier and the new price information in the request body.
     *
     * @param productId the unique identifier of the product whose price is to be updated
     * @param resource the request body containing the new price information, which must be a valid decimal number greater than zero
     * @return a ResponseEntity containing the updated product information if the operation is successful, or an appropriate error response if the product is not found, the input data is invalid, or if there are authentication/authorization issues
     */
    @PatchMapping("/{productId}/price")
    @Operation(
            summary = "Update product price",
            description = "Updates the price of an existing product."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product price updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<?> updateProductPrice(
            @PathVariable
            @Parameter(description = "ID of the product to update", example = "123e4567-e89b-12d3-a456-426614174000")
            Long productId,

            @RequestBody
            UpdateProductPriceResource resource
    ) {
        var updateProductPriceCommand = UpdateProductPriceCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var result = productCommandService.handle(updateProductPriceCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Endpoint to add stock to a product. This endpoint allows clients to increase the stock quantity of an existing product in the inventory management system.
     *
     * @param productId the unique identifier of the product to which stock will be added
     * @param resource the request body containing the quantity of stock to be added, which must be a positive integer
     * @return a ResponseEntity containing the updated product information with stock details if the operation is successful, or an appropriate error response if the product is not found, the input data is invalid, or if there are authentication/authorization issues
     */
    @PostMapping("/{productId}/additions")
    @Operation(
            summary = "Add stock to product",
            description = "Adds stock to an existing product in the inventory."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Stock added successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductWithStockResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<?> addStock(
            @PathVariable
            @Parameter(description = "ID of the product to add stock to", example = "123e4567-e89b-12d3-a456-426614174000")
            Long productId,

            @RequestBody
            AddStockResource resource
    ) {
        var addStockCommand = AddStockCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var result = stockCommandService.handle(addStockCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductWithStockResourceFromEntitiesAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Endpoint to subtract stock from a product. This endpoint allows clients to decrease the stock quantity of an existing product in the inventory management system.
     *
     * @param productId the unique identifier of the product from which stock will be subtracted
     * @param resource the request body containing the quantity of stock to be subtracted, which must be a positive integer
     * @return a ResponseEntity containing the updated product information with stock details if the operation is successful, or an appropriate error response if the product is not found, the input data is invalid, or if there are authentication/authorization issues
     */
    @PostMapping("/{productId}/subtractions")
    @Operation(
            summary = "Subtract stock from product",
            description = "Subtracts stock from an existing product in the inventory."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Stock subtracted successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductWithStockResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - insufficient permissions"
            )
    })
    public ResponseEntity<?> subtrackStock(
            @PathVariable
            @Parameter(description = "ID of the product to subtract stock from", example = "123e4567-e89b-12d3-a456-426614174000")
            Long productId,

            @RequestBody
            SubtrackStockResource resource
    ) {
        var subtractStockCommand = SubtrackStockCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var result = stockCommandService.handle(subtractStockCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProductWithStockResourceFromEntitiesAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
