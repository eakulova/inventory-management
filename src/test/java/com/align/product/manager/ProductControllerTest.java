package com.align.product.manager;

import com.align.product.manager.controller.ProductController;
import com.align.product.manager.model.ProductDto;
import com.align.product.manager.service.ProductServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser("USER")
    @Test
    void shouldReturnListOfLeftovers() throws Exception {
        ProductDto product = createProduct();

        when(productService.getAllLeftovers())
                .thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/products/leftovers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @WithMockUser("USER")
    @Test
    void shouldReturnProductsByNameAndBrand() throws Exception {
        ProductDto product = createProduct();
        when(productService.findProductsByNameAndBrand(any(), any()))
                .thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/products?name=watch&brand=casio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @WithMockUser(value = "ADMIN", roles = "ADMIN")
    @Test
    void shouldCreateNewProject() throws Exception {
        String request = readJson("/product-create-request.json");

        when(productService.createNewProduct(any()))
                .thenReturn(new ProductDto().setId(1L));

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @WithMockUser(value = "ADMIN", roles = "ADMIN")
    @Test
    void shouldUpdateProject() throws Exception {
        String request = readJson("/product-update-request.json");

        when(productService.updateProduct(any()))
                .thenReturn(new ProductDto().setId(1L));

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @WithMockUser(value = "ADMIN", roles = "ADMIN")
    @Test
    void shouldDeleteProject() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorisedExceptionWhileGettingListOfProducts() throws Exception {
        mockMvc.perform(get("/products/leftovers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorisedExceptionWhileGettingListOfProductsByParam() throws Exception {
        mockMvc.perform(get("/products?name=watch&brand=casio"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorisedExceptionWhileCreatingNewProduct() throws Exception {
        String request = readJson("/product-create-request.json");

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorisedExceptionWhileUpdatingProduct() throws Exception {
        String request = readJson("/product-update-request.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorisedExceptionWhileDeletingProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser("USER")
    @Test
    void shouldReturnForbiddenExceptionWhileCreatingNewProduct() throws Exception {
        String request = readJson("/product-create-request.json");

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isForbidden());
    }

    @WithMockUser("USER")
    @Test
    void shouldReturnForbiddenExceptionWhileUpdatingProduct() throws Exception {
        String request = readJson("/product-update-request.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isForbidden());
    }

    @WithMockUser("USER")
    @Test
    void shouldReturnForbiddenExceptionWhileDeletingProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isForbidden());
    }

    private ProductDto createProduct() {
        return new ProductDto()
                .setId(1L)
                .setName("name")
                .setBrand("brand")
                .setPrice(BigDecimal.ONE)
                .setQuantity(1);
    }

    private String readJson(String fileName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(fileName), StandardCharsets.UTF_8);
    }

}