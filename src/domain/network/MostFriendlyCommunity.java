package domain.network;

import domain.Friendship;
import domain.User;
import repository.FriendshipRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostFriendlyCommunity {
    private final UserRepository uRepo;
    private final FriendshipRepository fRepo;
    int nrUsersLongestPath;
    private final List<User> usersMostFrCom;
    private final List<Friendship> friendshipsMostFrCom;
    private final Map<String, Boolean> used;
    private final Map<String, UserNode> nodes;

    public MostFriendlyCommunity(UserRepository uRepo, FriendshipRepository fRepo, Map<String, Integer> com, int nrCommunities) {
        this.uRepo = uRepo;
        this.fRepo = fRepo;
        usersMostFrCom = new ArrayList<>();
        friendshipsMostFrCom = new ArrayList<>();
        used = new HashMap<>();
        nodes = new HashMap<>();
        for (User u : uRepo.getAll()) {
            nodes.put(u.getEmail(), new UserNode(u));
            used.put(u.getEmail(), false);
        }
        for (User u : uRepo.getAll()) {
            nodes.put(u.getEmail(), new UserNode(u));
            DFS(u.getEmail());
        }
    }

    /**
     * @return utilizatorii path-ului cel mai lung din reteaua de prietenii - List[User]
     */
    public List<User> getUsersMostFrCom() {
        return usersMostFrCom;
    }

    public int getNrUsers() {
        return nrUsersLongestPath;
    }

    /**
     * Determina drumul de lungime maxima in comunitate
     * @param e - email-ul utilizatorului de la nodul caruia se porneste in graful comunitatii
     */
    private void DFS(String e) {
        used.put(e, true);
        for (String em : fRepo.getUserFriends(uRepo.getUser(e))) {
            if (used.get(em) == false) {
                nodes.put(em, new UserNode(uRepo.getUser(em), uRepo.getUser(e), nodes.get(e).steps + 1));
                DFS(em);
            }
        }
        if (nodes.get(e).steps + 1 > nrUsersLongestPath)
            updateAns(e);
        used.put(e, false);
    }

    private void updateAns(String e) {
        UserNode un = nodes.get(e);
        int nr = 0;
        usersMostFrCom.clear();
        friendshipsMostFrCom.clear();
        while (un.prevU != null) {
            nr++;
            usersMostFrCom.add(un.u);
            friendshipsMostFrCom.add(new Friendship(un.u, un.prevU));
            un = nodes.get(un.prevU.getEmail());
        }
        nr++;
        usersMostFrCom.add(un.u);
        nrUsersLongestPath = nr;
    }
}