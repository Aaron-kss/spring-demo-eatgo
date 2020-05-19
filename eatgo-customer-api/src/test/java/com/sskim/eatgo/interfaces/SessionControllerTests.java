package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.EmailNotExistedException;
import com.sskim.eatgo.application.PasswordWrongException;
import com.sskim.eatgo.application.UserService;
import com.sskim.eatgo.domain.User;
import com.sskim.eatgo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception {
        Long id = 1004L;
        String name = "Tester";
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .id(id)
                .name(name)
                .level(1L)
                .build();

        given(userService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string(
                        containsString("{\"accessToken\":\"header.payload.signature\"}")
                ));

        verify(userService).authenticate(email, password);
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {

        String email = "x@example.com";
        String password = "test";

        given(userService.authenticate(email, password)).willThrow(
                EmailNotExistedException.class
        );

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(email, password);
    }

    @Test
    public void createWithWrongPassword() throws Exception {

        String email = "tester@example.com";
        String password = "x";

        given(userService.authenticate(email, password)).willThrow(
                PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(email, password);
    }
}