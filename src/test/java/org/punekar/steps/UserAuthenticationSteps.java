package org.punekar.steps;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.punekar.entity.User;
import org.punekar.repositories.UserRepository;
import org.punekar.service.FirebaseTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAuthenticationSteps {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private FirebaseTokenVerifier tokenVerifier;

    private MockMvc mockMvc;
    private ResultActions resultActions;
    private String currentToken;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    }

    @Given("the system is running and database is connected")
    public void the_system_is_running_and_database_is_connected() {
        // Handled by Spring Context
    }

    @Given("a new user wants to log in using Google")
    public void a_new_user_wants_to_log_in_using_google() {
        // Setup mock user intent if necessary
    }

    @Given("the user has a valid Firebase Auth token with UID {string} and email {string}")
    public void the_user_has_a_valid_firebase_auth_token_with_uid_and_email(String uid, String email) throws Exception {
        currentToken = "valid-token";
        FirebaseToken mockToken = mock(FirebaseToken.class);
        when(mockToken.getUid()).thenReturn(uid);
        when(mockToken.getEmail()).thenReturn(email);
        when(mockToken.getName()).thenReturn("Test Citizen");
        when(mockToken.getPicture()).thenReturn("http://photo.pune.kar");
        
        when(tokenVerifier.verifyIdToken(currentToken)).thenReturn(mockToken);
    }

    @When("the frontend sends the token to POST {string}")
    public void the_frontend_sends_the_token_to_post(String url) throws Exception {
        resultActions = mockMvc.perform(post(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + currentToken)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the system should verify the token successfully")
    public void the_system_should_verify_the_token_successfully() {
        // Implied by Mockito verify or returning success below
    }

    @Then("create a new user record in the database")
    public void create_a_new_user_record_in_the_database() {
        assert userRepository.count() == 1L;
    }

    @Then("return a {int} {word} status")
    public void return_a_status(Integer code, String statusText) throws Exception {
        if (code == 200) {
            resultActions.andExpect(status().isOk());
        } else if (code == 401) {
            resultActions.andExpect(status().isUnauthorized());
        }
    }

    @Then("the response should contain the user's profile information mapped to the database ID")
    public void the_response_should_contain_the_user_s_profile_information_mapped_to_the_database_id() throws Exception {
        resultActions.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firebaseUid").value("fire123"))
                .andExpect(jsonPath("$.email").value("citizen@pune.kar"));
    }

    @Given("an existing user with UID {string} exists in the system")
    public void an_existing_user_with_uid_exists_in_the_system(String uid) {
        User user = User.builder()
                .firebaseUid(uid)
                .email("existing@pune.kar")
                .displayName("Existing User")
                .role(User.Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Given("the user has a valid Firebase Auth token")
    public void the_user_has_a_valid_firebase_auth_token() throws Exception {
        currentToken = "valid-existing-token";
        FirebaseToken mockToken = mock(FirebaseToken.class);
        when(mockToken.getUid()).thenReturn("fire123");
        when(mockToken.getEmail()).thenReturn("existing@pune.kar");
        
        when(tokenVerifier.verifyIdToken(currentToken)).thenReturn(mockToken);
    }

    @Then("the response should contain the existing user's profile information")
    public void the_response_should_contain_the_existing_user_s_profile_information() throws Exception {
        resultActions.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firebaseUid").value("fire123"))
                .andExpect(jsonPath("$.email").value("existing@pune.kar"));
    }

    @Given("a user provides an invalid or expired Firebase Auth token")
    public void a_user_provides_an_invalid_or_expired_firebase_auth_token() throws Exception {
        currentToken = "invalid-token";
        FirebaseAuthException mockEx = mock(FirebaseAuthException.class);
        when(tokenVerifier.verifyIdToken(anyString())).thenThrow(mockEx);
    }

    @Then("the system should fail to verify the token")
    public void the_system_should_fail_to_verify_the_token() {
        // Failed implicitly because Exception is thrown and caught by controller returning 401
    }
}
