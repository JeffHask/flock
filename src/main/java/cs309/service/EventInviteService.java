package cs309.service;

import cs309.data.EventInvite;
import cs309.repo.EventInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class EventInviteService {

    @Autowired
    private EventInviteRepository eventInviteRepository;


    public void saveEventInvite(EventInvite eventInvite) {
        eventInviteRepository.save(eventInvite);
    }

    public EventInvite getEventInvite(List<EventInvite> list, Principal principal) {
        for (EventInvite i : list) {
            if(i.getUserInvited().getEmail() == principal.getName()) {
                return i;
            }
        }
        return null;
    }

}
