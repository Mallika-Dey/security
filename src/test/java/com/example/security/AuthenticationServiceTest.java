package com.example.security;

import com.example.security.sec1.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class AuthenticationServiceTest {
    @Autowired
    TestService testService;

    @WithMockUser(username = "dummyuser")
    @Test
    public void testCallUserFailed() {
        assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(
                () -> this.testService.callUser(12L));
    }
}
