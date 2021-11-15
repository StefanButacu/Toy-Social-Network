package repository.memory;

import domain.Friendship;
import repository.FriendshipRepository;
import repository.RepoException;
import repository.UserRepository;
import validator.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendshipRepoInMemory implements FriendshipRepository {
    private final UserRepository userRepo;
    private final Set<Friendship> friendships;
    private final Validator<Friendship> val;

    public FriendshipRepoInMemory(Validator<Friendship> val, UserRepository userRepo) {
        this.userRepo = userRepo;
        friendships = new HashSet<>();
        this.val = val;
    }

    /**
     * Adds a friendship to the repository
     * @param f - the friendship to be added
     * @throws RepoException - if the friendship is already saved
     */
    @Override
    public void addFriendship(Friendship f) throws RepoException {
        val.validate(f);
        if (!friendships.add(f))
            throw new RepoException("The two users are already friends");
    }

    /**
     * Removes a friendship from the repository
     * @param f - the friendship to be removed
     * @throws RepoException - if the friendship is not saved
     */
    @Override
    public void removeFriendship(Friendship f) throws RepoException {
        val.validate(f);
        if (!friendships.remove(f))
            throw new RepoException("The two users are not friends");
    }

    /**
     * @return no of friendships - int
     */
    @Override
    public int size() {
        return friendships.size();
    }

    /**
     * Removes all friendships from the repository
     */
    @Override
    public void clear() {
        friendships.clear();
    }

    /**
     * Verifies if there are no friendships saved
     * @return true if there are no friendships, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return friendships.size() == 0;
    }

    /**
     * Removes all the friendships of a user
     * @param email - the user's email
     */
    public void removeUserFships(String email) {
        List<Friendship> fships = new ArrayList<>();
        for (Friendship f : friendships) {
            if (f.getFirst().equals(email) || f.getSecond().equals(email))
                fships.add(f);
        }
        for (Friendship f : fships) {
            removeFriendship(f);
        }
    }

    /**
     * @param email1 - String the email of the first user
     * @param email2 - String the email of the second user
     * @return the friendship of the two users if it is saved in the repository,
     * null otherwise
     */
    @Override
    public Friendship getFriendship(String email1, String email2) {
        Friendship f = new Friendship(email1, email2);
        if (friendships.contains(f))
            return f;
        return null;
    }

    @Override
    public List<String> getUserFriendRequests(String email) {
        return null;
    }

    @Override
    public void acceptFriendship(Friendship f) {

    }

    /**
     * @return a list with all the friendships - List[Friendship]
     */
    @Override
    public List<Friendship> getAllApproved() {
        return friendships.stream().toList();
    }

    /**
     * @param email - String the email of the user
     * @return list with the user's friends
     */
    @Override
    public List<String> getUserFriends(String email) {
        List<String> friends = new ArrayList<>();
        for (Friendship f : friendships) {
            if (f.getFirst().equals(email))
                friends.add(f.getSecond());
            if (f.getSecond().equals(email))
                friends.add(f.getFirst());
        }
        return friends;
    }

    @Override
    public List<String> getUserFriendsAll(String email) {
        return null;
    }
}