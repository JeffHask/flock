package cs309.service;

import cs309.data.Event;
import cs309.data.EventInvite;
import cs309.data.User;
import cs309.repo.EventInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class EventInviteService {

    @Autowired
    private EventInviteRepository eventInviteRepository;


    @Transactional
    public EventInvite saveEventInvite(EventInvite eventInvite) {
        return eventInviteRepository.save(eventInvite);
    }

    public EventInvite getEventInvite(User user, Event event) {
        return eventInviteRepository.findEventInviteByUserAndEvent(user, event);
    }

}
