package cl.kibernumacademy.service;

import cl.kibernumacademy.model.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductManagerTest {

    private ProductManager manager;

    @BeforeEach
    void init() {
        manager = new ProductManager();
    }

    @AfterEach
    void cleanUp() {
        manager.clearAll();
    }

    @Test
    void agregarProducto() {
        Product p = manager.addProduct("Laptop", "Dell XPS", 1500);
        assertNotNull(p);
        assertEquals("Laptop", p.getName());
        assertThat(manager.getAll(), hasSize(1));
    }

    @Test
    void actualizarProductoExistente() {
        Product p = manager.addProduct("Monitor", "24 inch", 300);
        boolean updated = manager.updateProduct(p.getId(), "Monitor HD", "27 inch", 350);
        assertTrue(updated);

        Optional<Product> updatedProduct = manager.getProduct(p.getId());
        assertTrue(updatedProduct.isPresent()); // JUnit
        assertThat(updatedProduct.get().getName(), equalTo("Monitor HD")); // Hamcrest
    }

    @Test
    void noActualizarProductoInexistente() {
        boolean updated = manager.updateProduct(999, "Nuevo", "Desc", 100);
        assertFalse(updated);
    }

    @Test
    void eliminarProductoExistente() {
        Product p = manager.addProduct("Mouse", "Wireless", 50);
        boolean deleted = manager.deleteProduct(p.getId());
        assertTrue(deleted);
        assertThat(manager.getAll(), empty());
    }

    @Test
    void eliminarProductoInexistente() {
        boolean deleted = manager.deleteProduct(999);
        assertFalse(deleted);
    }

    @Test
    void usoDeAssumeTrue() {
        Product p = manager.addProduct("Tablet", "iPad", 800);
        assumeTrue(p.getPrice() > 0);
        assertEquals("iPad", p.getDescription());
    }

    @Test
    void usoDeAssumingThat() {
        Product p = manager.addProduct("Impresora", "HP", 200);
        assumingThat(p.getPrice() < 500, () -> {
            assertThat(p.getName(), containsString("Impresora"));
        });
        assertThat(p.getPrice(), lessThan(1000.0));
    }

    @ParameterizedTest
    @CsvSource({
            "Camara,Sony,1200",
            "Teclado,Logitech,100",
            "Smartphone,Samsung,900"
    })
    void testAgregarProductosConParametros(String name, String desc, double price) {
        Product p = manager.addProduct(name, desc, price);
        assertNotNull(p);
        assertThat(p.getPrice(), greaterThan(0.0));
    }
}
