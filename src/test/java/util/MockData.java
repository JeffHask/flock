package util;

import cs309.data.Notification;
import cs309.data.User;
import cs309.data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;


public class MockData {

    public static List<Event> getMockEvents(int x) {
        List<Event> events = new ArrayList<>();
        for(int i = 0; i < x; i++) {
            events.add(getEvent(i));
        }
        return events;
    }

    public static List<EventInvite> getEventInvites(int x) {
        List<EventInvite> eventInvites = new ArrayList<>();
        for(int i = 0; i < x; i++) {
            eventInvites.add(getEventInvite(i));
        }
        return eventInvites;
    }

    public static EventInvite getEventInvite(int x) {
        EventInvite eventInvite = new EventInvite();
        eventInvite.setEvent(getEvent(x));
        eventInvite.setId(x);
        return eventInvite;
    }

    public static Event getEvent(int x) {
        Event event = new Event();
        event.setId(x);
        event.setEventName("event" + x);
        EventInvite invite = new EventInvite();
        List<EventInvite> list = new ArrayList<>();
        list.add(invite);
        event.setEventInvites(list);
        return event;
    }

    public static List<Notification> getMockNotifications(int k) {
        List<Notification> notifications = new ArrayList<>();
        for(int i = 0; i < k; i++){
            notifications.add(getNotification(i));
        }
        return notifications;
    }

    public static Notification getNotification(int k) {
        Notification notification = new Notification();
        notification.setId(k);
        notification.setNotificationName("notification" + k);
        return notification;
    }

    public static User getUser(int k) {
        User user = new User();
        user.setId(k);
        user.setEmail("email" + k);
        user.setFirstName("first" + k);
        user.setLastName("last" + k);
        return user;
    }

    public static List<User> getUsers(int k) {
        List<User> mockUsers = new ArrayList<>();
        IntStream.range(0,k).forEach(i -> mockUsers.add(getUser(k)));
        return mockUsers;
    }

    public static Connection getConnection(int k) {
        Connection connection = new Connection();
        connection.setId(k);
        connection.setUser1(getUser(k));
        connection.setUser2(getUser(k + 1));
        return connection;
    }

    public static List<Connection> getConnections(int k) {
        List<Connection> mockConnections = new ArrayList<>();
        IntStream.range(0,k).forEach(i -> mockConnections.add(getConnection(i)));
        return mockConnections;
    }

    public static Comment getComment(int k) {
        Comment comment = new Comment();
        comment.setId(k);
        comment.setComment("Hey");
        return  comment;
    }

    public static EventInvite getInvite(int k) {
        EventInvite invite = new EventInvite();
        invite.setInviteStatus(1);
        return invite;
    }

    public static Role getEventAdmin(int k) {
        Role role = new Role("chase@test.com","ROLE_EVENT_ADMIN",1);
        return role;
    }

    public static ChatGroup getChatGroup(int k) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setId(k);
        chatGroup.setChatName(k + "");
        chatGroup.setDateCreated( new Date());
        return chatGroup;
    }

    public static List<ChatGroup> getChatGroups(int k) {
        List<ChatGroup> chatGroups = new ArrayList<>();
        IntStream.range(0,k).forEach(i -> chatGroups.add(getChatGroup(i)));
        return chatGroups;
    }

    public static ChatUser getChatUser(int k) {
        ChatUser chatUser = new ChatUser();
        chatUser.setId(k);
        chatUser.setStatus(ChatUser.STATUS_INVITED);
        return chatUser;
    }

}
