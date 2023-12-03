package Test;

import Application.Model.Order;
import Application.Model.Product;
import Presentation.Controller.Supporting.AssistantController;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;

public class AssistantControllerTest {

    @Test
    public void testaddToCart() throws SQLException {
        AssistantController controller = new AssistantController();
        assertTrue(controller.addToCart("TE35Med2571410", 5));
        assertFalse(controller.addToCart("InvalidCode", 10));
    }

    @org.junit.jupiter.api.Test
    void testupdateCart() throws SQLException{
        AssistantController controller = new AssistantController();
        controller.addToCart("TE35Med2571410", 5);
        assertTrue(controller.updateCart("TE35Med2571410", 10));
        assertFalse(controller.updateCart("InvalidCode", 10));
    }

    @org.junit.jupiter.api.Test
    void testremoveCart() throws SQLException {
        AssistantController controller = new AssistantController();
        controller.addToCart("TE35Med2571410", 5);
        assertTrue(controller.removeCart("TE35Med2571410"));
        assertFalse(controller.removeCart("InvalidCode"));
    }

    @org.junit.jupiter.api.Test
    void testclearCart() throws SQLException {
        AssistantController controller = new AssistantController();
        controller.addToCart("TE35Med2571410", 5);
        assertTrue(controller.clearCart());
    }

    @org.junit.jupiter.api.Test
    void testmakeOrder() throws SQLException {
        AssistantController controller = new AssistantController();
        assertNotNull(controller.makeOrder());
    }

    @org.junit.jupiter.api.Test
    void testclearExpireStock() throws SQLException {
        AssistantController controller = new AssistantController();
        assertFalse(controller.clearExpireStock());
    }

    @org.junit.jupiter.api.Test
    void sellStock() throws SQLException {
        AssistantController controller = new AssistantController();
        controller.addToCart("TE35Med2571410", 5);
        assertTrue(controller.sellStock("TE35Med2571410", 5));
        assertFalse(controller.sellStock("InvalidCode", 10));
    }

    @org.junit.jupiter.api.Test
    void testgiveSearchResult() throws SQLException {
        AssistantController controller = new AssistantController();
        List<String> searchResult = controller.giveSearchResult("Pana");
        assertNotNull(searchResult);
        assertTrue(!searchResult.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testgetSearchedProd() throws SQLException {
        AssistantController controller = new AssistantController();
        assertNotNull(controller.getSearchedProd("Test"));
    }

    @org.junit.jupiter.api.Test
    void testcodeSearch() throws SQLException {
        AssistantController controller = new AssistantController();
        assertNotNull(controller.codeSearch("TE35Med2571410"));
        assertNull(controller.codeSearch("InvalidCode"));
    }

    @org.junit.jupiter.api.Test
    void testcheckCodeProd() throws SQLException {
        AssistantController controller = new AssistantController();
        Product product = controller.getSearchedProd("Test");
        assertNotNull(product);
        assertEquals(product.getCode(), controller.checkCodeProd(product));
    }

    @org.junit.jupiter.api.Test
    void testcancelOrder() throws SQLException {
        AssistantController controller = new AssistantController();
        Order order = controller.makeOrder();
        assertTrue(controller.cancelOrder(order));
    }

    @org.junit.jupiter.api.Test
    void testgenerateBill() throws SQLException {
        AssistantController controller = new AssistantController();
        Order order = controller.makeOrder();
        List<Product> lowStock = controller.generateBill(order);
        assertNotNull(lowStock);
    }

}