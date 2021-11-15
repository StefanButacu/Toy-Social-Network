package repository;

import domain.Friendship;
import domain.User;

import java.util.List;

public interface FriendshipRepository {

    public void addFriendship(Friendship f);
    public void removeFriendship(Friendship f);
    public int size();
    public void clear();
    public boolean isEmpty();
    public List<Friendship> getAllApproved();
    public List<String> getUserFriends(String email);

    List<String> getUserFriendsAll(String email);

    public void removeUserFships(String email);
    public Friendship getFriendship(String email1, String email2);

    List<String> getUserFriendRequests(String email);

    void acceptFriendship(Friendship f);
}
