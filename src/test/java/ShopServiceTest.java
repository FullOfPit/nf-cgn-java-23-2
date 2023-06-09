import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private Product[] TEST_PRODUCTS = new Product[] {
            new Vegetable("testVegetableId", "testVegetable"),
            new Crisps("testCrispsId", "testCrisps"),
            new Toiletry("testToiletryId", "testToiletry")
    };

    private Product TEST_PRODUCT_ONE = TEST_PRODUCTS[(int) (Math.random() * TEST_PRODUCTS.length)];
    private Order TEST_ORDER_ONE = new Order("testOrderId", List.of(TEST_PRODUCT_ONE));
    private Order TEST_ORDER_TWO = new Order("testOrderIdTwo", List.of(TEST_PRODUCT_ONE));
    private ShopService TEST_SHOP_SERVICE = new ShopService(
            new ProductRepository(new ArrayList<>(List.of(TEST_PRODUCT_ONE))),
            new OrderRepository(new ArrayList<>(List.of(TEST_ORDER_ONE)))
    );

    @Test
    void getProduct_returnsProduct_whenIdRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        Product actual = shop.getProduct(TEST_PRODUCT_ONE.getId());
        //Then
        Assertions.assertEquals(actual, TEST_PRODUCT_ONE);
    }

    @Test
    void getProduct_returnsNull_whenIdNotRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        Product actual = shop.getProduct("NOT_REGISTERED");
        //Then
        Assertions.assertNull(actual);
    }

    @Test
    void listProducts_returnsProductList_WhenProductsRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        List<Product> actual = shop.listProducts();
        //Then
        Assertions.assertEquals(actual, List.of(TEST_PRODUCT_ONE));
    }

    @Test
    void listProducts_returnsEmptyList_WhenNoProductsRegistered() {
        //Given
        ShopService shop = new ShopService(new ProductRepository(), new OrderRepository());
        //When
        List<Product> actual = shop.listProducts();
        //Then
        Assertions.assertEquals(actual, List.of());
    }

    @Test
    void getOrder_ReturnOrder_WhenOrderRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        Order actual = shop.getOrder(TEST_ORDER_ONE.getId());
        //Then
        Assertions.assertEquals(actual, TEST_ORDER_ONE);
    }

    @Test
    void getOrder_returnNull_WhenOrderNotRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        Order actual = shop.getOrder("NOT_REGISTERED");
        //Then
        Assertions.assertNull(actual);
    }

    @Test
    void listOrder_returnListOfOrders_WhenOrdersRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        List<Order> actual = shop.listOrders();
        //Then
        Assertions.assertEquals(actual, List.of(TEST_ORDER_ONE));
    }

    @Test
    void listOrder_returnEmptyList_WhenNoOrdersRegistered() {
        //Given
        ShopService shop = new ShopService(new ProductRepository(), new OrderRepository());
        //When
        List<Order> actual = shop.listOrders();
        //Then
        Assertions.assertEquals(actual, List.of());
    }

    @Test
    void addOrder_returnsListedOrder_WhenAddedToEmptyList() {
        //Given
        ShopService shop = new ShopService(new ProductRepository(), new OrderRepository());
        //When
        List<Order> actual = shop.addOrder(TEST_ORDER_ONE);
        //Then
        Assertions.assertEquals(actual, List.of(TEST_ORDER_ONE));
    }

    @Test
    void addOrder_returnsUpdatedOrderList_WhenNewOrderNotRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When
        List<Order> actual = shop.addOrder(TEST_ORDER_TWO);
        //Then
        Assertions.assertEquals(actual, List.of(TEST_ORDER_ONE, TEST_ORDER_TWO));
    }

    @Test
    void addOrder_throwsException_WhenOrderAlreadyRegistered() {
        //Given
        ShopService shop = TEST_SHOP_SERVICE;
        //When - Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> shop.addOrder(TEST_ORDER_ONE));
    }
}