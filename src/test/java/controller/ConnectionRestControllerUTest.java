package controller;

import config.UnitTestBase;
import cs309.controller.ConnectionRestController;
import cs309.data.ConnectionGroup;
import cs309.service.ConnectionService;
import cs309.service.NotificationService;
import cs309.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.MockData;

import java.security.Principal;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ConnectionRestControllerUTest extends UnitTestBase {

    private MockMvc mockMvc;

    @Mock
    private ConnectionService connectionService;

    @Mock
    private UserService userService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ConnectionRestController connectionRestController;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(connectionRestController).build();
    }

    @Test
    public void getConnections() throws Exception{
        Principal principal = mock(Principal.class);
        when(connectionService.getConnectionsByEmail(principal.getName())).thenReturn(MockData.getUsers(4));
        this.mockMvc.perform(get("/api/connections/get").accept(MediaType.APPLICATION_JSON).principal(principal))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    public void getConnectionStatus() throws Exception {
        Principal principal = mock(Principal.class);
        when(userService.getUserByEmail(principal.getName())).thenReturn(MockData.getUser(2));
        mockMvc.perform(post("/api/connection/status/1").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("nothing"));
    }

    @Test
    public void requestConnection() throws Exception {
        Principal principal = mock(Principal.class);
        when(userService.getUserByEmail(principal.getName())).thenReturn(MockData.getUser(2));
        mockMvc.perform(post("/api/connection/request/1").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("requesting"));
    }

    @Test
    public void removeConnection() throws Exception {
        Principal principal = mock(Principal.class);
        when(userService.getUserByEmail(principal.getName())).thenReturn(MockData.getUser(2));
        mockMvc.perform(post("/api/connection/remove/1").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("nothing"));
    }

    @Test
    public void addConnection() throws Exception {
        Principal principal = mock(Principal.class);
        when(userService.getUserByEmail(principal.getName())).thenReturn(MockData.getUser(2));
        mockMvc.perform(post("/api/connection/add/1").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("connected"));
    }

    @Test
    public void rejectConnectionRequest() throws Exception {
        Principal principal = mock(Principal.class);
        when(userService.getUserByEmail(principal.getName())).thenReturn(MockData.getUser(2));
        mockMvc.perform(post("/api/connection/reject/1").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("nothing"));
    }

    @Test
    public void connectionGroups() throws Exception {
        Principal principal = mock(Principal.class);
        when(connectionService.getConnectionGroupByEmail(principal.getName())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/connectionGroups").principal(principal))
                .andExpect(status().isOk());
    }

    @Test
    public void createConnectionGroup() throws Exception {
        Principal principal = mock(Principal.class);
        mockMvc.perform(post("/api/connectionGroup/create").principal(principal).content("blah"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteConnectionGroup() throws Exception {
        Principal principal = mock(Principal.class);
        mockMvc.perform(post("/api/connectionGroup/delete").principal(principal).content("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void editConnectionGroupName() throws Exception {
        Principal principal = mock(Principal.class);
        when(connectionService.getConnectionGroupById(4)).thenReturn(new ConnectionGroup());
        mockMvc.perform(post("/api/connectionGroup/4/edit").principal(principal).content("new name"))
                .andExpect(status().isOk());
    }

    @Test
    public void addConnectionToConnectionGroup() throws Exception {
        Principal principal = mock(Principal.class);
        mockMvc.perform(post("/api/connectionGroup/4/add").principal(principal).content("7"))
                .andExpect(status().isOk());
    }

    @Test
    public void removeConnectionFromConnectionGroup() throws Exception {
        Principal principal = mock(Principal.class);
        when(connectionService.getConnectionGroupById(4)).thenReturn(new ConnectionGroup());
        mockMvc.perform(post("/api/connectionGroup/4/edit").principal(principal).content("890"))
                .andExpect(status().isOk());
    }

    @Test
    public void getConnectionGroupUsers() throws Exception {
        Principal principal = mock(Principal.class);
        mockMvc.perform(post("/api/connectionGroup/4").principal(principal).content("new name"))
                .andExpect(status().isOk());    }
}
