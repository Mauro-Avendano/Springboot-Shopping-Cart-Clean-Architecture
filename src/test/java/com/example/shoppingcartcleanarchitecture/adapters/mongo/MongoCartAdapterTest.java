package com.example.shoppingcartcleanarchitecture.adapters.mongo;

import com.example.shoppingcartcleanarchitecture.domain.entities.Cart;
import com.example.shoppingcartcleanarchitecture.domain.entities.ItemCart;
import com.example.shoppingcartcleanarchitecture.domain.entities.Product;
import com.example.shoppingcartcleanarchitecture.domain.exceptions.CartNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//test containers support for junit 5, manages the lifecycle of the containers declared with @Container. It also can be used at method level.
//@Testcontainers
@DataMongoTest
@Import({MongoCartAdapter.class})
class MongoCartAdapterTest {
    // this annotation is available from springboot 3.1 and handles under the hood the dynamic properties
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest").withExposedPorts(27017);

    //use this to run the container just once for all tests, avoiding MongoSocketReadException: Prematurely reached end of stream at the end of the tests
    static {
        mongoDBContainer.start();
    }

    @Autowired
    private MongoCartRepository cartRepository;

    @Autowired
    private MongoProductRepository productRepository;

    @Autowired
    private MongoCartAdapter mongoCartAdapter;

    @Mock
    private MongoCartRepository mockedCartRepository;

    @InjectMocks
    private MongoCartAdapter mongoCartAdapterWithInjectedMocks;

    @Test
    void shouldRunTheMongoContainer() {
        assertTrue(mongoDBContainer.isRunning(), "MongoDB container should be running");
    }

    @Test
    void shouldGetTheCartBySessionId() {
        String sessionId = "fake-session-id";
        String product1IdStr = "60dd51f5b2c4d1153cc24456";
        ObjectId product1Id = new ObjectId(product1IdStr);
        String product1Name = "fake-product-1-name";
        String product1Description = "fake-product-1-description";
        String product1CreationDate = "2024-07-04T10:15:30";
        String product1ModifiedDate = "2024-07-04T10:15:30";
        int product1Price = 1000;
        ProductDB productDB = new ProductDB(product1Id, product1Name, product1Description,
                product1Price, LocalDateTime.parse(product1CreationDate), LocalDateTime.parse(product1ModifiedDate));
        Product product = new Product(product1IdStr, product1Name, product1Description, product1Price);
        List<ItemCart> items = new ArrayList<>(Arrays.asList(new ItemCart(product, 1)));
        Cart cart = new Cart(sessionId, items);
        productRepository.insert(productDB);
        mongoCartAdapter.insertCart(cart);

        Cart returnedCart = mongoCartAdapter.getCart(sessionId);

        assertEquals(sessionId, returnedCart.getSessionId());
        assertEquals(1, returnedCart.getItems().size());
        assertEquals(product1Name, returnedCart.getItems().get(0).getProduct().getName());
        assertEquals(product1Price, returnedCart.getItems().get(0).getProduct().getPrice());
        assertEquals(product1Description, returnedCart.getItems().get(0).getProduct().getDescription());
    }

    @Test
    void shouldThrowCartNotFoundExceptionWhenCartDoesNotExist() {
        String sessionId = "fake-session-id";
        String expectedErrorMessage = "Cart not found";

        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> mongoCartAdapter.getCart(sessionId));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenThereIsAnErrorGettingTheCart() {
        String sessionId = "fake-session-id";
        String expectedErrorMessage = "There was an error getting the cart";
        when(mockedCartRepository.findBySessionId(sessionId)).thenThrow(new RuntimeException("Fake exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mongoCartAdapterWithInjectedMocks.getCart(sessionId));

        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}


