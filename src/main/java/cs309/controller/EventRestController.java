package cs309.controller;

import cs309.data.*;
import cs309.dto.CreateEventDTO;
import cs309.dto.ErrorsDTO;
import cs309.dto.EventDTO;
import cs309.service.*;
import cs309.validator.CreateEventValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class EventRestController {

    private Logger LOG = Logger.getLogger(EventRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CreateEventValidator eventValidator;

    @Autowired
    private EventInviteService eventInviteService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/api/event/{id}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable Integer id) {
        return eventService.getEvent(id);
    }

    @RequestMapping(value = "/api/event/{id}", method = RequestMethod.POST)
    public String updateEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
        return "/";
    }

    @RequestMapping("/api/events")
    public List<EventDTO> getEvents(Principal principal) {
        List<EventDTO> eventDTOs = new ArrayList<>();
        User user = userService.getUserByEmail(principal.getName());
        user.getEvents().stream().forEach(event -> eventDTOs.add(new EventDTO(event.getEvent())));
        return eventDTOs;
    }

    @RequestMapping(value = "/api/event/createComment/{id}", method = RequestMethod.POST)
    public String createComment(@RequestBody String string, @PathVariable Integer id, Principal principal) {
        Comment comment = new Comment();
        Event event = eventService.getEvent(id);
        comment.setEvent(event);
        comment.setComment(string);
        comment.setDateCreated(new Date());
        comment.setOwner(userService.getUserByEmail(principal.getName()));
        commentService.saveComment(comment);
        return "/";
    }

    @RequestMapping(value = "/api/event/setAttending/{id}", method = RequestMethod.POST)
    public int setAttending(@RequestBody String status, @PathVariable Integer id, Principal principal) {
        EventInvite invite = eventInviteService.getEventInvite(userService.getUserByEmail(principal.getName()), eventService.getEvent(id));
        if (status.equals("Going")) {
            invite.setInviteStatus(invite.GOING);
        }
        else if(status.equals("Maybe")) {
            invite.setInviteStatus(invite.UNDECIDED);
        }
        else if(status.equals("Not Going")) {
            invite.setInviteStatus(invite.NOT_GOING);
        }
        else if(status.equals("Change")) {
            invite.setInviteStatus(invite.INVITED);
        }
        eventInviteService.saveEventInvite(invite);
        return invite.getInviteStatus();
    }

    @RequestMapping(value = "/api/event/getAttending/{id}", method = RequestMethod.GET)
    public int getInvite(@PathVariable Integer id, Principal principal) {
        EventInvite invite = eventInviteService.getEventInvite(userService.getUserByEmail(principal.getName()), eventService.getEvent(id));
        return invite != null ? invite.getInviteStatus() : EventInvite.JOINABLE;
    }

    @RequestMapping(value = "/api/event/isEventAdmin/{id}", method = RequestMethod.GET)
    public Boolean isEventAdmin(@PathVariable Integer id, Principal principal) {
        Role role = roleService.getRole(principal.getName(), Role.EVENT_ADMIN, id);
        return role != null;
    }

    @RequestMapping(value = "/api/create", method = RequestMethod.POST)
    public List<ErrorsDTO> createEvent(@Valid @RequestBody final CreateEventDTO createEventDTO,
                                       BindingResult result, Principal principal) throws IOException, ParseException {
        LOG.error(result.getFieldErrors());
        if(result.hasErrors()) {
            List<ErrorsDTO> errors = new ArrayList<>();
            result.getFieldErrors().stream().forEach(fieldError ->  errors.add(new ErrorsDTO(fieldError.getField(),fieldError.getCode())));
            return errors;
        }
        User creator = userService.getUserByEmail(principal.getName());
        Event event = eventService.saveEvent(new Event(createEventDTO, creator));
        roleService.createRole(principal.getName(), Role.EVENT_ADMIN, event.getId());
        EventInvite invite = new EventInvite(creator, creator, event);
        invite.setInviteStatus(EventInvite.GOING);
        eventInviteService.saveEventInvite(invite);
        List<ErrorsDTO> noErrors = new ArrayList<>();
        noErrors.add(new ErrorsDTO("success", event.getId() + ""));
        return noErrors;
    }

    @RequestMapping(value = "/api/event/{eventId}/invites", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addInvites(@PathVariable Integer eventId, @RequestBody final Integer[] userIds, Principal principal) {
        Event event = eventService.getEvent(eventId);
        User inviter = userService.getUserByEmail(principal.getName());
        for(Integer userId : userIds) {
            if(!eventInviteService.eventInviteExists(eventId,userId)) {
                User invitedUser = userService.getUser(userId);
                EventInvite eventInvite = new EventInvite(inviter, invitedUser, event);
                eventInviteService.saveEventInvite(eventInvite);
            }
        }
    }

    @RequestMapping(value = "/api/event/{eventId}/admins", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addEventAdmins(@RequestBody final Integer[] userIds, @PathVariable Integer eventId) {
        for(Integer userId : userIds) {
            User invitedAdmin = userService.getUser(userId);
            roleService.createRole(invitedAdmin.getEmail(), Role.EVENT_ADMIN, eventId);
        }
    }

    @InitBinder(value = "createEventDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(eventValidator);
    }
}
