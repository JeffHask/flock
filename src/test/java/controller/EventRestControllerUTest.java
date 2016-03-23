package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.UnitTestBase;
import cs309.controller.EventRestController;
import cs309.data.Comment;
import cs309.data.Event;
import cs309.data.EventInvite;
import cs309.data.User;
import cs309.dto.CreateEventDTO;
import cs309.service.CommentService;
import cs309.service.EventInviteService;
import cs309.service.EventService;
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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class EventRestControllerUTest extends UnitTestBase {
    private MockMvc mockMvc;

    @Mock
    private EventInviteService eventInviteService;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventRestController eventController;

    @Mock
    private CommentService commentService;


    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(eventController).build();
    }

    @Test
    public void getEvent() throws Exception {
        when(eventService.getEvent(1)).thenReturn(MockData.getEvent(1));
        this.mockMvc.perform(get("/api/event/1"))
                .andExpect(status().isOk());
                Event event = eventService.getEvent(1);
                assertEquals(event.getEventName(), "event1");
    }

    @Test
    public void getEvents() throws Exception {
        when(eventService.getEvents()).thenReturn(MockData.getMockEvents(4));
        this.mockMvc.perform(get("/api/events").accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    public void createEvent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(eventService.saveEvent(any(Event.class))).thenReturn(MockData.getEvent(1));
        CreateEventDTO eventDTO = new CreateEventDTO();
        String string = mapper.writeValueAsString(eventDTO);
        when(userService.getUser(1)).thenReturn(mock(User.class));
        this.mockMvc.perform(post("/api/create")
                .content(string)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService, times(1)).saveEvent(any(Event.class));
    }

    @Test
    public void editEvent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        when(eventService.saveEvent(any(Event.class))).thenReturn(MockData.getEvent(1));
        Event event = new Event();
        String string = mapper.writeValueAsString(event);
        this.mockMvc.perform(post("/api/event/1")
                .content(string)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService, times(1)).saveEvent(any(Event.class));
    }

    @Test
    public void addInvites() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String ids = mapper.writeValueAsString(new int[]{1,2,3});

        mockMvc.perform(post("/api/event/1/invites").principal(mock(Principal.class))
        .content(ids)
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(eventInviteService, times(3)).saveEventInvite(any(EventInvite.class));
    }

    @Test
    public void getAttending() throws Exception {
        Principal principal = mock(Principal.class);
        when(eventInviteService.getEventInvite(any(User.class), any(Event.class))).thenReturn(MockData.getInvite(1));
        mockMvc.perform(get("/api/event/getAttending/1")
                .principal(principal))
                .andExpect(status().isOk());
                EventInvite invite = eventInviteService.getEventInvite(userService.getUserByEmail(principal.getName()), eventService.getEvent(1));
                assertEquals(invite.getInviteStatus().toString(), "1");

    }

    @Test
    public void setAttending() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Principal principal = mock(Principal.class);
        when(eventInviteService.getEventInvite(any(User.class), any(Event.class))).thenReturn(MockData.getInvite(1));
        when(eventInviteService.saveEventInvite(any(EventInvite.class))).thenReturn(MockData.getInvite(1));
        EventInvite invite = new EventInvite();
        String string = mapper.writeValueAsString(invite);
        when(userService.getUserByEmail(principal.getName())).thenReturn(mock(User.class));
        this.mockMvc.perform(post("/api/event/setAttending/1")
                .content(string)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventInviteService, times(1)).saveEventInvite(any(EventInvite.class));
    }

    @Test
    public void createComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Principal principal = mock(Principal.class);
        when(commentService.saveComment(any(Comment.class))).thenReturn(MockData.getComment(1));
        Comment comment = new Comment();
        String string = mapper.writeValueAsString(comment);
        when(userService.getUserByEmail(principal.getName())).thenReturn(mock(User.class));
        this.mockMvc.perform(post("/api/event/createComment/1")
                .content(string)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(commentService, times(1)).saveComment(any(Comment.class));
    }
}
