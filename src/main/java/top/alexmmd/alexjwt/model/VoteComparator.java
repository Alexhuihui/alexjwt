package top.alexmmd.alexjwt.model;

import java.util.Comparator;

/**
 * @author 汪永晖
 */
public class VoteComparator implements Comparator<ApplicationUser> {
    @Override
    public int compare(ApplicationUser o1, ApplicationUser o2) {
        return o2.getVoteNumber() - o1.getVoteNumber();
    }
}
