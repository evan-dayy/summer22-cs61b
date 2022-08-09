package gitlet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a gitlet repository.
 * This is an action class, try to put every command in the right place of repository
 * <p>
 * * Important: The JAVA file is collaborated by Yichao DAI (s194) and Ong Aldrin (s240)
 * * Important: Except Merge and related Merge Helpers methods, and reset methods.
 *
 * @author Evan Day (YICHAO)
 * @Email yichaoday_evan@berkeley.edu
 */
public class Repository {

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File ADDAREA = Utils.join(".gitlet", "add");
    public static final File REMOVEAREA = Utils.join(".gitlet", "remove");
    public static final File COMMITSOBJ = Utils.join(".gitlet", "Commits");
    public static final File REF = Utils.join(".gitlet", "REF"); // store header
    private static final String PATTERN = "EEE MMM d HH:mm:ss yyyy Z";
    private static final SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat(PATTERN);

    public static void init() {
        if (!GITLET_DIR.exists()) { // java.io.files libraries
            LinkedHashMap<String, String> addMap = new LinkedHashMap<>();
            LinkedHashMap<String, String> rmMap = new LinkedHashMap<>();
            LinkedHashMap<String, Commit> commitMap = new LinkedHashMap<String, Commit>();
            LinkedList<String> sha1List = new LinkedList<>();
            GITLET_DIR.mkdir();
            ADDAREA.mkdir();
            REMOVEAREA.mkdir();
            COMMITSOBJ.mkdir();
            REF.mkdir();

            Commit initial = new Commit();
            // store the first init object
            commitMap.put(initial.getSha1(), initial);
            sha1List.addFirst(initial.getSha1());
            Header branches = new Header("main", sha1List);
            Utils.writeObject(Utils.join(REF, "branches"), branches);
            Utils.writeObject(Utils.join(ADDAREA, "addMap"), addMap);
            Utils.writeObject(Utils.join(REMOVEAREA, "rmMap"), rmMap);
            Utils.writeObject(Utils.join(COMMITSOBJ, "commitMap"), commitMap);
            Utils.writeObject(Utils.join(COMMITSOBJ, "sha1List"), sha1List);
        } else {
            System.out.println("A Gitlet version-control system "
                    + "already exists in the current directory.");
        }
    }

    public static void add(String[] args) {
        // load the Serializable Map
        LinkedHashMap<String, String> addMap = Utils.readObject(Utils.join(ADDAREA,
                "addMap"), LinkedHashMap.class);
        for (int i = 1; i < args.length; i++) {
            String name = args[i];
            File f = new File(CWD + "/" + name);
            if (!f.exists()) {
                System.out.println("File does not exist.");
                continue;
            }
            String contents = Utils.readContentsAsString(f);
            String sha1 = Utils.sha1(name + contents); // sha1 ID for name + contents
            // is SHA1 unique in .gitlet
            if (Utils.join(ADDAREA, sha1).exists()) {
                // situation when you rm, and then undo this process
                LinkedHashMap<String, String> rmMap = Utils.readObject(
                        Utils.join(REMOVEAREA, "rmMap"),
                        LinkedHashMap.class);
                rmMap.remove(sha1);
                Utils.writeObject(Utils.join(REMOVEAREA, "rmMap"), rmMap);
                return;
            }
            // in case there is no change or there is a redo process
            if (addMap.containsKey(sha1)) {
                File tempCheck = new File(ADDAREA + sha1); // name after sha1
                if (tempCheck.exists()) {
                    tempCheck.delete(); // remove it from the stage area
                }
                addMap.remove(sha1); // remove that key in the map
                continue;
            }
            // otherwise
            addMap.put(sha1, name);
            Utils.writeContents(Utils.join(ADDAREA, sha1), contents);
        }
        // save the Serializable Map
        Utils.writeObject(Utils.join(ADDAREA, "addMap"), addMap);
    }

    public static void commit(String[] args) {
        Commit c = new Commit(args);
        LinkedHashMap<String, String> rmMap = Utils.readObject(
                Utils.join(REMOVEAREA, "rmMap"),
                LinkedHashMap.class);
        if (c.getM().size() == 0 && rmMap.size() == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        commitMap.put(c.getSha1(), c);
        Utils.writeObject(Utils.join(COMMITSOBJ, "commitMap"), commitMap);
        // clean the map
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"),
                LinkedHashMap.class);
        addMap.clear();
        rmMap.clear();
        Utils.writeObject(Utils.join(ADDAREA, "addMap"), addMap);
        Utils.writeObject(Utils.join(REMOVEAREA, "rmMap"), rmMap);
    }

    public static void commitMerge(Commit mergeCommit, String givenBranch) {
        LinkedHashMap<String, String> rmMap = Utils.readObject(
                Utils.join(REMOVEAREA, "rmMap"),
                LinkedHashMap.class);
        if (mergeCommit.getM().size() == 0 && rmMap.size() == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        commitMap.put(mergeCommit.getSha1(), mergeCommit);
        Utils.writeObject(Utils.join(COMMITSOBJ, "commitMap"), commitMap);
        LinkedList<String> sha1List = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"),
                LinkedList.class);
        // sha1List.clear(); // for merge purpose
        sha1List.addFirst(mergeCommit.getSha1());
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        if (branches.isHeaderBranchTheSame()) {
            branches.update(sha1List);
        } else {
            branches.updateOnlyHeader(sha1List);
        }
        Utils.writeObject(Utils.join(COMMITSOBJ, "sha1List"), sha1List);
        Utils.writeObject(Utils.join(REF, "branches"), branches);
    }

    public static void preCheck(String[] args) {
        String fileName = args[2];
        File tempFile = new File(fileName);
        // read the commit map
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        // read LinkedList Commits sha1
        LinkedList<String> sha1List = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"),
                LinkedList.class);
        // get the latest SHA1-bounded commit
        String currSHA1 = sha1List.getFirst();
        // String preSHA1 = commitMap.get(currSHA1).getParentSha1();
        // parentSha1
        Commit currCommit = commitMap.get(currSHA1);
        if (!currCommit.getM().containsKey(fileName)) {
            System.out.println("File does not exist in that commit");
            return;
        }
        String contentsGit = Utils.readContentsAsString(
                Utils.join(ADDAREA, currCommit.getM().get(fileName)));
        Utils.writeContents(tempFile, contentsGit);
    }

    public static void checkOut(String[] args) {
        String uid = args[1];
        String fileName = args[3];
        File tempFile = new File(fileName);
        // read the commit map
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        // read LinkedList Commits sha1
        if (uid.length() <= 10) {
            LinkedList<String> directory = Utils.readObject(
                    Utils.join(COMMITSOBJ, "sha1List"),
                    LinkedList.class);
            for (String realUid : directory) {
                if (realUid.contains(uid)) {
                    uid = realUid;
                    break;
                }
            }
        }
        if (!commitMap.containsKey(uid)) {
            System.out.println("No commit with that id exists.");
            return;
        }

        if (!Objects.equals(args[2], "--")) {
            System.out.println("Incorrect operands.");
            return;
        }
        // get target sha1-commit object
        Commit currCommit = commitMap.get(uid);
        if (!currCommit.getM().containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        String contentsGit = Utils.readContentsAsString(
                Utils.join(ADDAREA, currCommit.getM().get(fileName)));
        Utils.writeContents(tempFile, contentsGit);
    }

    public static void reset(String[] args) {
        String uid = args[1];
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        if (!commitMap.containsKey(uid)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"),
                LinkedHashMap.class);
        LinkedList<String> sha1ListPrev = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"),
                LinkedList.class);
        String sha1Prev = sha1ListPrev.getFirst();
        LinkedHashMap<String, String> currCommitPrev = commitMap.get(sha1Prev).getM();
        Commit curr = commitMap.get(uid);
        LinkedHashMap<String, String> currCommit = curr.getM();

        // change the header
        Header branches = Utils.readObject(Utils.join(REF, "branches"), Header.class);
        branches.resetHeader(uid);
        LinkedList<String> sha1Header = branches.getHeader().get(branches.getBranch());
        Utils.writeObject(Utils.join(COMMITSOBJ, "sha1List"), sha1Header);

        // overwrite the CWD file and give a message when
        // there are untracked file which still need be overwrite

        List<String> cwdFileNames = Utils.plainFilenamesIn(CWD);
        for (String name : cwdFileNames) {
            if (currCommit == null || !currCommit.containsKey(name)) {
                File tempFile = Utils.join(CWD, name);
                tempFile.delete();
                if (addMap.containsValue(name)) {
                    LinkedHashMap<String, String> tempMap = swapHelper(addMap);
                    File addAreaFile = Utils.join(ADDAREA, tempMap.get(name));
                    tempMap.remove(name);
                    addAreaFile.delete();
                    addMap = tempMap;
                    Utils.writeObject(Utils.join(ADDAREA, "addMap"), addMap);
                }
                continue;
            }
            String cwdFileContent = Utils.readContentsAsString(Utils.join(CWD, name));
            String gitFileContent = Utils.readContentsAsString(
                    Utils.join(ADDAREA, currCommit.get(name)));

            // T: currCommitPrev == null
            //  ||(!currCommitPrev.containsKey(name) && currCommit.containsKey(name))
            if (currCommitPrev == null
                    || (!cwdFileContent.equals(gitFileContent)
                    && !currCommitPrev.containsKey(name))) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it, or add and commit it first.");
                return;
            }


            File tempFile = Utils.join(CWD, name);
            if (!currCommit.containsKey(name)) {
                continue;
            }
            String corrSha1 = currCommit.get(name);
            String contents = Utils.readContentsAsString(Utils.join(ADDAREA, corrSha1));
            Utils.writeContents(tempFile, contents);

        }
        // write file
        if (currCommit != null) {
            for (String filename : currCommit.keySet()) {
                if (!cwdFileNames.contains(filename)) {
                    String contents = Utils.readContentsAsString(
                            Utils.join(ADDAREA, currCommit.get(filename)));
                    Utils.writeContents(Utils.join(CWD, filename), contents);
                }
            }
        }
    }

    public static void branch(String[] args) {
        String branchName = args[1];
        LinkedList<String> sha1List = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"),
                LinkedList.class);
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        if (branches.getM().containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        branches.update(branchName, sha1List);
        branches.updateSplitPoint(branchName, sha1List);
        Utils.writeObject(Utils.join(REF, "branches"), branches);
    }

    public static void branchCheckout(String[] args) {
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"), LinkedHashMap.class);
        String branchName = args[1];
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"), Header.class);
        if (!branches.getM().containsKey(branchName)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (Objects.equals(branches.getBranch(), branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        branches.changeBranch(branchName);
        Utils.writeObject(Utils.join(REF, "branches"), branches);
        LinkedList<String> sha1ListPrev = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"), LinkedList.class);
        String sha1Prev = sha1ListPrev.getFirst();
        // used header
        LinkedList<String> sha1List = branches.getHeaderSha1List();
        String sha1Curr = sha1List.getFirst();
        // save the change to the header class
        // replace the main SHA commit list
        Utils.writeObject(Utils.join(COMMITSOBJ, "sha1List"), sha1List);
        // T: overwrite the CWD file and give a message
        //  when there are untracked file which still need be overwrite
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"), LinkedHashMap.class);
        LinkedHashMap<String, String> currCommitPrev = commitMap.get(sha1Prev).getM();
        LinkedHashMap<String, String> currRemoveCommitPrev = commitMap.get(sha1Prev).getRM();
        LinkedHashMap<String, String> currCommit = commitMap.get(sha1Curr).getM();
        // LinkedHashMap<String, String> curr_RemoveCommit = commitMap.get(sha1Curr).getRM();
        List<String> cwdFileNames = Utils.plainFilenamesIn(CWD);
        // If a working file is untracked in the
        // current branch and would be overwritten by the checkout
        for (String name : cwdFileNames) {
            if (currCommit == null || !currCommit.containsKey(name)) {
                File tempFile = Utils.join(CWD, name);
                tempFile.delete();
                continue;
            }
            String cwdFileContent = Utils.readContentsAsString(Utils.join(CWD, name));
            String gitFileContent = Utils.readContentsAsString(
                    Utils.join(ADDAREA, currCommit.get(name)));
            if (currCommitPrev == null
                    || (!cwdFileContent.equals(gitFileContent)
                    && !currCommitPrev.containsKey(name))
                    || addMap.containsValue(name)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it, or add and commit it first.");
                return;
            }
        }
        if (currCommit != null) {
            for (String name : cwdFileNames) {
                File tempFile = Utils.join(CWD, name);
                if (!currCommit.containsKey(name)) {
                    continue;
                }
                String corrSha1 = currCommit.get(name);
                String contents = Utils.readContentsAsString(Utils.join(ADDAREA, corrSha1));
                Utils.writeContents(tempFile, contents);
            }
            for (String filename : currCommit.keySet()) {
                if (!cwdFileNames.contains(filename)) {
                    String contents = Utils.readContentsAsString(
                            Utils.join(ADDAREA, currCommit.get(filename)));
                    Utils.writeContents(Utils.join(CWD, filename), contents);
                }
            }
            for (String filename : currRemoveCommitPrev.keySet()) {
                if (!cwdFileNames.contains(filename) && sha1ListPrev.contains(sha1Curr)) {
                    String contents = Utils.readContentsAsString(
                            Utils.join(ADDAREA, currRemoveCommitPrev.get(filename)));
                    Utils.writeContents(Utils.join(CWD, filename), contents);
                }
            }
        }
    }

    public static void rmBranch(String[] args) {
        String branchName = args[1];
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        if (!branches.getM().containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (Objects.equals(branches.getBranch(), branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        branches.getM().remove(branchName);
        Utils.writeObject(Utils.join(REF, "branches"), branches);

    }

    public static void log() {
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        LinkedList<String> sha1List;
        if (branches.isHeaderBranchTheSame()) {
            sha1List = Utils.readObject(
                    Utils.join(COMMITSOBJ, "sha1List"),
                    LinkedList.class);
        } else {
            sha1List = branches.getHeaderSha1List();
        }

        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        for (String sha1Id : sha1List) {
            if (!commitMap.get(sha1Id).getMessage().contains("Merged")) {
                System.out.println("===");
                System.out.print("commit ");
                System.out.println(sha1Id);
                System.out.print("Date: ");
                System.out.println(commitMap.get(sha1Id).getDate());
                System.out.println(commitMap.get(sha1Id).getMessage());
                System.out.println();
            } else {
                System.out.println("===");
                System.out.print("commit ");
                System.out.println(sha1Id);
                System.out.print("Date: ");
                System.out.println(commitMap.get(sha1Id).getDate());
                System.out.println(commitMap.get(sha1Id).getMessage());
                System.out.println();
            }
        }
    }

    public static void globalLog() {
        Header branches = Utils.readObject(Utils.join(REF, "branches"), Header.class);
        LinkedList<String> sha1List = branches.getBranchSha1List();

        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        for (String sha1Id : sha1List) {
            System.out.println("===");
            System.out.print("commit ");
            System.out.println(sha1Id);
            System.out.print("Date: ");
            System.out.println(commitMap.get(sha1Id).getDate());
            System.out.println(commitMap.get(sha1Id).getMessage());
            System.out.println();
        }
    }

    public static void status() {
        System.out.println("=== Branches ===");
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        for (String branchName : branches.getM().keySet()) {
            if (Objects.equals(branchName, branches.getBranch())) {
                System.out.print("*");
                System.out.println(branchName);
            } else {
                System.out.println(branchName);
            }
        }
        System.out.println();

        System.out.println("=== Staged Files ===");
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"),
                LinkedHashMap.class);
        reverseOrder(swapHelper(addMap));
        System.out.println();

        System.out.println("=== Removed Files ===");
        LinkedHashMap<String, String> rmMap = Utils.readObject(
                Utils.join(REMOVEAREA, "rmMap"),
                LinkedHashMap.class);
        reverseOrder(swapHelper(rmMap));
        System.out.println();

        // T (Optional) : Last two sections

        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void find(String[] args) {
        int count = 0;
        String message = args[1];
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        for (Commit c : commitMap.values()) {
            if (Objects.equals(c.getMessage(), message)) {
                System.out.println(c.getSha1());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void rm(String[] args) {
        String fileName = args[1];
        File tempFile = new File(fileName);
        LinkedList<String> sha1List = Utils.readObject(
                Utils.join(COMMITSOBJ, "sha1List"),
                LinkedList.class);
        int lstSize = sha1List.size();
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"),
                LinkedHashMap.class);
        LinkedHashMap<String, String> rmMap = Utils.readObject(
                Utils.join(REMOVEAREA, "rmMap"),
                LinkedHashMap.class);
        Commit currCommit = commitMap.get(sha1List.getFirst());
        if (addMap.containsValue(fileName)) {
            addMap = swapHelper(addMap);
            // remove it from the AddMap
            addMap.remove(fileName);
            addMap = swapHelper(addMap);
            // update the addMap
            Utils.writeObject(Utils.join(ADDAREA, "addMap"), addMap);
            return;
        }

        for (String sha1 : sha1List) {
            if (lstSize == 0 || commitMap.get(sha1).getM() == null) {
                System.out.println("No reason to remove the file.");
                return;
            }
            if (commitMap.get(sha1).getM().containsKey(fileName)) {
                currCommit = commitMap.get(sha1);
                break;
            }
            lstSize--;
        }
        if (lstSize > 0) {
            // (SHA1 - name), stage it, but not copy that in this repo;
            rmMap.put(currCommit.getM().get(fileName), fileName);
            Utils.writeObject(Utils.join(REMOVEAREA, "rmMap"), rmMap);
            if (tempFile.exists()) {
                tempFile.delete();
            }

        }
    }

    public static void merge(String[] args) {
        String givenBranch = args[1];
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        if (Objects.equals(givenBranch, branches.getBranch())) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        } else if (!branches.getBranchMap().containsKey(givenBranch)) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else if (Objects.equals(givenBranch, "main")
                && branches.getTargetBranchSha1List(branches.getBranch())
                == branches.getSplitPoint().get(branches.getBranch())) {
            branchCheckout(new String[]{"", "main"});
            System.out.println("Current branch fast-forwarded.");
            return;
        } else if (branches.getTargetBranchSha1List(
                givenBranch)
                == branches.getSplitPoint().get(givenBranch)
                && branches.getBranchSha1List().contains(
                branches.getSplitPoint().get(givenBranch).getFirst())) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }
        mergeHelper1(givenBranch);
    }

    public static void mergeHelper1(String args) {
        String givenBranch = args;
        Header branches = Utils.readObject(
                Utils.join(REF, "branches"),
                Header.class);
        LinkedHashMap<String, Commit> commitMap = Utils.readObject(
                Utils.join(COMMITSOBJ, "commitMap"),
                LinkedHashMap.class);
        // Split Point needed
        String splitSha1;
        if (branches.getSplitPoint().size() == 2) {
            splitSha1 = branches.getSplitPoint().get("main").getFirst();
        } else {
            int currSize = branches.getSplitPoint().get(branches.getBranch()).size();
            int mergeSize = branches.getSplitPoint().get(givenBranch).size();
            if (currSize <= mergeSize) {
                splitSha1 = branches.getSplitPoint().get(branches.getBranch()).getFirst();
            } else {
                splitSha1 = branches.getSplitPoint().get(givenBranch).getFirst();
            }
        }
        Commit splitCommit = commitMap.get(splitSha1);
        LinkedHashMap<String, String> splitMap = splitCommit.getM();
        // current Point needed
        String currSha1 = branches.getBranchSha1List().getFirst();
        LinkedList<String> currSha1List = branches.getBranchSha1List();
        int lstSize = currSha1List.size();
        Commit currCommit = commitMap.get(currSha1);
        LinkedHashMap<String, String> currMap = currCommit.getM();
        LinkedHashMap<String, String> currRMap = currCommit.getRM();
        // given point needed
        String givenSha1 = branches.getTargetBranchSha1List(givenBranch).getFirst();
        Commit givenCommit = commitMap.get(givenSha1);
        LinkedHashMap<String, String> givenMap = givenCommit.getM();
        LinkedHashMap<String, String> givenRMap = givenCommit.getRM();
        LinkedHashMap<String, String> addMap = Utils.readObject(
                Utils.join(ADDAREA, "addMap"),
                LinkedHashMap.class);
        LinkedHashMap<String, String> rmMap = Utils.readObject(
                Utils.join(REMOVEAREA, "rmMap"),
                LinkedHashMap.class);
        List<String> cwdFileNames = Utils.plainFilenamesIn(CWD);

        for (String name : cwdFileNames) {
            if (!currMap.containsKey(name) && addMap.containsValue(name)) {
                System.out.println("You have uncommitted changes.");
                return;
            }
            for (String sha1 : currSha1List) {

                if (lstSize == 0 || commitMap.get(sha1).getM() == null) {
                    System.out.println("There is an untracked file in the way; "
                            + "delete it, or add and commit it first.");
                    return;
                }
                if (commitMap.get(sha1).getM().containsKey(name)) {
                    break;
                }
                lstSize--;
            }
        }
        mergeHelper4(commitMap, splitSha1, branches, givenBranch);
    }

    public static void mergeHelper4(
            LinkedHashMap<String, Commit> commitMap, String splitSha1,
            Header branches, String givenBranch) {
        // situation one: Split point present;
        // T: present in split point
        Commit splitCommit = commitMap.get(splitSha1);
        LinkedHashMap<String, String> splitMap = splitCommit.getM();
        String currSha1 = branches.getBranchSha1List().getFirst();
        LinkedList<String> currSha1List = branches.getBranchSha1List();
        Commit currCommit = commitMap.get(currSha1);
        LinkedHashMap<String, String> currMap = currCommit.getM();
        LinkedHashMap<String, String> currRMap = currCommit.getRM();
        String givenSha1 = branches.getTargetBranchSha1List(givenBranch).getFirst();
        Commit givenCommit = commitMap.get(givenSha1);
        LinkedHashMap<String, String> givenMap = givenCommit.getM();
        LinkedHashMap<String, String> givenRMap = givenCommit.getRM();
        LinkedHashMap<String, String> tempMap = null;
        if (splitMap != null) {
            for (String splitPresent : splitMap.keySet()) {
                for (String sha1 : currSha1List) {
                    if (commitMap.get(sha1).getM().containsKey(splitPresent)) {
                        tempMap = commitMap.get(sha1).getM();
                        break;
                    }
                }
                // T: not modified in given branch
                if ((givenMap.containsKey(splitPresent)
                        && Objects.equals(splitMap.get(splitPresent),
                        givenMap.get(splitPresent)))) {
                    // T: non-exist in current branch
                    if (!currMap.containsKey(splitPresent)) {
                        continue;
                        // nothing happens,
                        // because it has already removed (potential bugs here)
                    }
                } else if (!givenMap.containsKey(splitPresent)
                        && !givenRMap.containsKey(splitPresent)) {
                    if (!currMap.containsKey(splitPresent)) {
                        continue;
                    }
                } else if (givenMap.containsKey(splitPresent)
                        && !Objects.equals(splitMap.get(splitPresent),
                        givenMap.get(splitPresent))) {
                    if (tempMap.containsKey(splitPresent)
                            && Objects.equals(
                            splitMap.get(splitPresent), tempMap.get(splitPresent))) {
                        File tempFile = Utils.join(ADDAREA, givenMap.get(splitPresent));
                        String contentGiven = Utils.readContentsAsString(tempFile);
                        Utils.writeContents(Utils.join(CWD, splitPresent), contentGiven);
                        // update the SHA1-ID
                        currMap.put(splitPresent, givenMap.get(splitPresent));
                    } else if (tempMap.containsKey(splitPresent)
                            && !Objects.equals(splitMap.get(splitPresent),
                            tempMap.get(splitPresent))
                            && !Objects.equals(givenMap.get(splitPresent),
                            currMap.get(splitPresent))) {
                        mergeHelper8(splitPresent, givenMap, currMap);
                    }
                } else if (!Objects.equals(splitMap.get(splitPresent), currMap.get(splitPresent))
                        && !givenMap.containsKey(splitPresent)) {
                    // should exist in current branch
                    if (currMap.containsKey(splitPresent)) {
                        mergeHelper7(currMap, splitPresent);
                    }
                }
                // T: non-present in split point but exit in given branch
                mergeHelper6(givenMap, splitMap, currMap);
            }
        }
        // T: non-present in split nor in current branch
        mergeHelper2(givenMap, splitMap, currMap, givenRMap, currSha1List, commitMap, currRMap);
        // T: make a new commit object in the current branch
        mergeCommitObjHelper(givenBranch, branches, currMap, currSha1, currRMap);
    }

    public static void mergeHelper8(String splitPresent,
                                    LinkedHashMap<String, String> givenMap,
                                    LinkedHashMap<String, String> currMap) {
        System.out.println("Encountered a merge conflict.");
        File tempFile = Utils.join(CWD, splitPresent);
        String contentGiven = Utils.readContentsAsString(Utils.join(
                ADDAREA,
                givenMap.get(splitPresent)));
        String contentCurr = Utils.readContentsAsString(Utils.join(
                ADDAREA,
                currMap.get(splitPresent)));
        String content = "<<<<<<< HEAD\n" + contentCurr
                + "=======" + "\n" + contentGiven + ">>>>>>>" + "\n";
        Utils.writeContents(tempFile, content);
        currMap.put(splitPresent, Utils.sha1(splitPresent, content));
    }

    public static void mergeHelper7(LinkedHashMap<String, String> currMap,
                                    String splitPresent) {
        System.out.println("Encountered a merge conflict.");
        File tempFile = Utils.join(CWD, splitPresent);
        String contentCurr = Utils.readContentsAsString(Utils.join(
                ADDAREA, currMap.get(splitPresent)));
        String content = "<<<<<<< HEAD\n" + contentCurr
                + "=======" + "\n" + ">>>>>>>" + "\n";
        Utils.writeContents(tempFile, content);
        currMap.put(splitPresent, Utils.sha1(splitPresent, content));
    }

    public static void mergeHelper6(LinkedHashMap<String, String> givenMap,
                                    LinkedHashMap<String, String> splitMap,
                                    LinkedHashMap<String, String> currMap) {
        for (String givenPresent : givenMap.keySet()) {
            if (splitMap.containsKey(givenPresent)) {
                continue;
            }
            if (!currMap.containsKey(givenPresent)) {
                File tempFile = Utils.join(CWD, givenPresent);
                String contents = Utils.readContentsAsString(
                        Utils.join(ADDAREA,
                                givenMap.get(givenPresent)));
                Utils.writeContents(tempFile, contents);
                currMap.put(givenPresent, givenMap.get(givenPresent));
            }
        }
    }

    public static void mergeHelper2(LinkedHashMap<String, String> givenMap,
                                    LinkedHashMap<String, String> splitMap,
                                    LinkedHashMap<String, String> currMap,
                                    LinkedHashMap<String, String> givenRMap,
                                    LinkedList<String> currSha1List,
                                    LinkedHashMap<String, Commit> commitMap,
                                    LinkedHashMap<String, String> currRMap) {
        for (String givenPresent : givenMap.keySet()) {
            if (splitMap == null) {
                if (!currMap.containsKey(givenPresent)) {
                    File tempFile = Utils.join(CWD, givenPresent);
                    String contents = Utils.readContentsAsString(
                            Utils.join(ADDAREA, givenMap.get(givenPresent)));
                    Utils.writeContents(tempFile, contents);
                    currMap.put(givenPresent, givenMap.get(givenPresent));
                    continue;
                }
            } else if (splitMap.containsKey(givenPresent)) {
                continue;
            }
            // T: Not exist in current branch
            if (!currMap.containsKey(givenPresent)) {
                String content = Utils.readContentsAsString(Utils.join(ADDAREA,
                        givenMap.get(givenPresent)));
                File tarFile = Utils.join(CWD, givenPresent);
                Utils.writeContents(tarFile, content);
                currMap.put(givenPresent, givenMap.get(givenPresent));
            }
        }
        LinkedHashMap<String, String> tempMap1 = null;
        for (String givenPresentRm : givenRMap.keySet()) {

            for (String sha1 : currSha1List) {
                if (commitMap.get(sha1).getM().containsKey(givenPresentRm)) {
                    tempMap1 = commitMap.get(sha1).getM();
                    break;
                }
            }
            if (tempMap1 == null) {
                continue;
            } else if (tempMap1.containsKey(givenPresentRm)
                    && !currMap.containsKey(givenPresentRm)) {
                File temp = Utils.join(CWD, givenPresentRm);
                if (temp.exists()) {
                    temp.delete();
                }
                currRMap.put(givenPresentRm, tempMap1.get(givenPresentRm));
            }
        }
        // T: non-present in split but in current branch
        for (String currPresent : currMap.keySet()) {
            if (splitMap == null) {
                if (givenRMap.containsKey(currPresent)) {
                    File tarFile = Utils.join(CWD, currPresent);
                    boolean x = tarFile.delete();
                    currRMap.put(currPresent, givenRMap.get(currPresent));
                } else if (!givenRMap.containsKey(currPresent)
                        && !givenMap.containsKey(currPresent)) {
                    continue;
                }
            } else if (splitMap.containsKey(currPresent)) {
                continue;
            }
        }
    }

    public static void mergeCommitObjHelper(
            String givenBranch,
            Header branches,
            LinkedHashMap<String, String> currMap,
            String currSha1,
            LinkedHashMap<String, String> currRMap
    ) {
        String message = "Merged " + givenBranch + " into " + branches.getBranch() + ".";
        String date = SIMPLEDATEFORMAT.format(new Date());
        Utils.writeObject(Utils.join(COMMITSOBJ, "currTempMap"), currMap);
        byte[] temp = Utils.readContents(Utils.join(COMMITSOBJ, "currTempMap"));
        String sha1 = Utils.sha1("currCommit", temp);
        String parentSha1 = currSha1;
        // create a commit object
        Commit c = new Commit(date, message, sha1, parentSha1, currMap, currRMap);
        commitMerge(c, givenBranch);
    }


    /**
     * Helper Function for the swap the key-value pair
     *
     * @author Evan Day
     * @source <a href="https://stackoverflow.com
     * /questions/4436999/how-to-swap-keys-and-values-in-a-map-elegantly">...</a>
     */
    private static LinkedHashMap<String, String> swapHelper(LinkedHashMap<String, String> M) {
        LinkedHashMap<String, String> reverseHashMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : M.entrySet()) {
            reverseHashMap.put(entry.getValue(), entry.getKey());
        }
        return reverseHashMap;
    }

    /**
     * Helper Function for the reverse the order of linked map
     *
     * @author Evan Day
     * @source <a href="https://www.benchresources.net/how-to-iterate-through
     * -linkedhashmap-in-reverse-order-in-java/#:~:
     * text=Iterate%20LinkedHashMap%20in%20reverse%2Dorder%3A&text
     * =Use%20Collections%20class%20utility%20method,values%20using%20get(key)%20method">...</a>
     */
    private static void reverseOrder(LinkedHashMap<String, String> M) {
        List<String> keys = new ArrayList<String>(M.keySet());
        Collections.reverse(keys);
        for (String reverseKey : keys) {
            System.out.println(reverseKey);
        }
    }
}
