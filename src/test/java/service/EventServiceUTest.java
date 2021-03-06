package service;

import config.UnitTestBase;
import cs309.data.Event;
import cs309.repo.EventRepository;
import cs309.service.EventService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import util.MockData;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class EventServiceUTest extends UnitTestBase {

    @Mock
    private EventRepository eventRepo;

    @InjectMocks
    private EventService eventService;

    @Test
    public void getEvents() {
        when(eventRepo.findAll()).thenReturn(new ArrayList<>(MockData.getMockEvents(5)));
        List<Event> events = eventService.getEvents();
        assertEquals(events.size(),5);
    }

    @Test
    public void getEvent() {
        when(eventRepo.findOne(0)).thenReturn(MockData.getEvent(0));
        Event event = eventService.getEvent(0);
        assertNotNull(event);
    }

    @Test
    public void saveEvent() {
        Event event = MockData.getEvent(1);
        eventService.saveEvent(event);
        verify(eventRepo, times(1)).save(event);
    }

    @Test
    public void getEventSearch() {
        String query ="jeffIsAwesome";
        List<Event> events = eventService.getEventSearch(query);
        assertTrue(events.isEmpty());
    }
}
