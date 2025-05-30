package com.zsp.catalogservice.web;

import com.zsp.catalogservice.domain.BookNotFoundException;
import com.zsp.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 使用WebMvcTest，测试只包含Web需要的上下文，更加轻量级
 */
@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "1234567890";
        given(bookService.viewBookDetail(isbn))
                .willThrow(BookNotFoundException.class);
        mockMvc.perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }
}
