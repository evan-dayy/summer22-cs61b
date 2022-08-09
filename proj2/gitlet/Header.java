package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

import static gitlet.Repository.COMMITSOBJ;

/**
 * This is reference (Header) class, particularly for the reset and merge issues
 * At a high level, Header class store the Sha1List for each branch, and it also
 * stores the Sha1List of Header. For merge issue, Header class also store the
 * split point and the Sha1List before the split point
 * <p>
 * * Important: The JAVA file is collaborated by Yichao DAI (s194) and Ong Aldrin (s240)
 *
 * @author Evan Day (YICHAO)
 * @Email yichaoday_evan@berkeley.edu
 */

public class Header implements Serializable {
    public static final File REF = Utils.join(".gitlet", "REF"); // store header
    private final LinkedHashMap<String,
            LinkedList<String>> branchSha1List = new LinkedHashMap<String,
            LinkedList<String>>();
    private final LinkedHashMap<String,
            LinkedList<String>> currHeader = new LinkedHashMap<String,
            LinkedList<String>>();
    private final LinkedHashMap<String,
            LinkedList<String>> splitPoint = new LinkedHashMap<String,
            LinkedList<String>>();
    private String branch = "main";

    public Header(String branchName, LinkedList<String> lst) {
        // only update
        branchSha1List.put(branchName, lst);
        currHeader.put(branchName, lst);
        splitPoint.put(branchName, lst);
    }

    public void update(LinkedList<String> lst) {
        // potential bug here (reset)
        branchSha1List.put(branch, lst);
        currHeader.put(branch, lst);
    }

    public void update(String branchName, LinkedList<String> lst) {
        // for change of branch
        branchSha1List.put(branchName, lst);
        currHeader.put(branchName, lst);
    }

    public void updateSplitPoint(String branchName, LinkedList<String> lst) {
        // to store the merge split point
        splitPoint.put("main", lst);
        splitPoint.put(branchName, lst);
    }

    public LinkedHashMap<String, LinkedList<String>> getM() {
        return this.branchSha1List;
    }

    public LinkedHashMap<String, LinkedList<String>> getHeader() {
        return this.currHeader;
    }


    public String getBranch() {
        return branch;
    }


    public void changeBranch(String branchName) {
        branch = branchName;
    }


    public void resetHeader(String commitSha1) { // potential bugs here
        LinkedList<String> lst = Utils.readObject(Utils.join(COMMITSOBJ,
                "sha1List"), LinkedList.class);
        LinkedList<String> lst2 = Utils.readObject(Utils.join(COMMITSOBJ,
                "sha1List"), LinkedList.class);
        if (lst2.contains(commitSha1)) {
            for (String sha1 : lst) {
                if (!Objects.equals(sha1, commitSha1)) {
                    lst2.removeFirst();
                } else {
                    break;
                }
            }
        } else {
            lst = getBranchSha1List();
            lst2 = getBranchSha1List();
            for (String sha1 : lst) {
                if (!Objects.equals(sha1, commitSha1)) {
                    lst2.removeFirst();
                } else {
                    break;
                }
            }
        }

        currHeader.put(branch, lst2);
        Utils.writeObject(Utils.join(REF, "branches"), this);
    }

    public LinkedHashMap<String, LinkedList<String>> getBranchMap() {
        return branchSha1List;
    }

    public LinkedList<String> getBranchSha1List() {
        return branchSha1List.get(branch);
    }

    public LinkedList<String> getTargetBranchSha1List(String name) {
        return branchSha1List.get(name);
    }

    public LinkedHashMap<String, LinkedList<String>> getSplitPoint() {
        return this.splitPoint;
    }


    public LinkedList<String> getHeaderSha1List() {
        return currHeader.get(branch);
    }

    public boolean isHeaderBranchTheSame() {
        return branchSha1List.get(branch).equals(currHeader.get(branch));
    }

    public void updateOnlyHeader(LinkedList<String> lst) { // potential bug here (reset)
        currHeader.put(branch, lst);
    }

}
