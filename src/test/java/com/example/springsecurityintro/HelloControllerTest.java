package com.example.springsecurityintro;

import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {LoginUserDetailsService.class, LoginUserRepository.class, SecurityConfig.class}
))
@AutoConfigureJdbc  // 組み込みデータソース＋JdbcTemplateのAuto Configurationが有効化される
public class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Nested
    public class トップ画面へのアクセス {
        final MockHttpServletRequestBuilder request = get("/")
                .accept(MediaType.TEXT_HTML);

        @TestWithAnonymous
        public void 未ログインでアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }

        @TestWithGeneral
        public void 一般ユーザーでアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }

        @TestWithAdmin
        public void 管理者でアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }
    }

    @Nested
    public class ログイン画面へのアクセス {
        final MockHttpServletRequestBuilder request = get("/login")
                .accept(MediaType.TEXT_HTML);

        @TestWithAnonymous
        public void 未ログインでアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }

        @TestWithGeneral
        public void 一般ユーザーでアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }

        @TestWithAdmin
        public void 管理者でアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }
    }

    @Nested
    public class 一般ユーザー画面へのアクセス {
        final MockHttpServletRequestBuilder request = get("/general")
                .accept(MediaType.TEXT_HTML);

        @TestWithAnonymous
        public void 未ログインだとログイン画面にリダイレクトされる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }

        @TestWithGeneral
        public void 一般ユーザーでアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("general"));
        }

        @TestWithAdmin
        public void 管理者でアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("general"));
        }
    }

    @Nested
    public class 管理者画面へのアクセス {
        final MockHttpServletRequestBuilder request = get("/admin")
                .accept(MediaType.TEXT_HTML);

        @TestWithAnonymous
        public void 未ログインだとログイン画面にリダイレクトされる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }

        @TestWithGeneral
        public void 一般ユーザーだと403() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @TestWithAdmin
        public void 管理者でアクセスできる() throws Exception {
            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin"));
        }
    }
}
