package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static gitlet.Repository.*;

/**
 * Represents a gitlet commit object.
 * At a high level, commit class create a commit object including the metadata (Formatted datetime,
 * Unique ID number, and user message), object-oriented UID, parent's object-oriented UID, and a
 * hashmap (Filename to file-oriented Sha1) snapshot.
 * <p>
 * Important: The JAVA file is collaborated by Yichao DAI (s194) and Ong Aldrin (s240)
 *
 * @author Evan Day (YICHAO)
 * @Email yichaoday_evan@berkeley.edu
 */
public class Commit implements Serializable {
    private static final String PATTERN = "EEE MMM d HH:mm:ss yyyy Z";
    private static final SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat(PATTERN);
    // The message of this Commit.
    private final String message; // metadata
    private final String date; // metadata
    private final String sha1; // commit sha1
    private LinkedHashMap<String, String> M;
    private LinkedHashMap<String, String> rM;
    private String parentSha1; // commit parent sha1


    /**
     * An init constructor, it should only be used for the init automatically commit.
     *
     * @author Evan Day
     */
    public Commit() {
        this.date = SIMPLEDATEFORMAT.format(new Date(0));
        this.sha1 = Utils.sha1();
        message = "initial commit";
    }

    /**
     * A general commit object constructor.
     *
     * @author Evan Day
     */
    public Commit(String[] args) {
        // Metadata
        this.message = args[1];
        this.date = SIMPLEDATEFORMAT.format(new Date());

        // Current Commit-Object sha1 - Using the Byte[] addMap as input Variable
        this.sha1 = Utils.sha1("addMap", Utils.readContents(Utils.join(ADDAREA, "addMap")));

        // Get parent Commit-object sha1
        LinkedList<String> sha1List = Utils.readObject(Utils.join(COMMITSOBJ,
                "sha1List"), LinkedList.class);
        this.parentSha1 = sha1List.getFirst();
        sha1List.addFirst(this.sha1);
        // change the Header in the reference
        Header branches = Utils.readObject(Utils.join(REF, "branches"), Header.class);
        if (branches.isHeaderBranchTheSame()) {
            branches.update(sha1List);
        } else {
            branches.updateOnlyHeader(sha1List);
        }

        Utils.writeObject(Utils.join(COMMITSOBJ, "sha1List"), sha1List);
        Utils.writeObject(Utils.join(REF, "branches"), branches);
        // Blob
        LinkedHashMap<String, String> addMap = Utils.readObject(Utils.join(ADDAREA,
                "addMap"), LinkedHashMap.class);
        // Used Helper function to swap the key-value pair (filename, sha1)
        LinkedHashMap<String, String> rmMap = Utils.readObject(Utils.join(REMOVEAREA,
                "rmMap"), LinkedHashMap.class);
        this.M = swapHelper(addMap);
        this.rM = swapHelper(rmMap);
    }

    // this is for the merge issue
    public Commit(String date, String message, String sha1, String parentSha1,
                  LinkedHashMap<String, String> m, LinkedHashMap<String, String> rM) {
        this.date = date;
        this.message = message;
        this.sha1 = sha1;
        this.parentSha1 = parentSha1;
        this.M = m;
        this.rM = rM;
    }

    /**
     * Helper Function for the swap the key-value pair
     *
     * @author Evan Day
     * @source <a href="https://stackoverflow.com/questions/4436999/
     * how-to-swap-keys-and-values-in-a-map-elegantly">...</a>
     */

    private LinkedHashMap<String, String> swapHelper(LinkedHashMap<String, String> m) {
        LinkedHashMap<String, String> reverseHashMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            reverseHashMap.put(entry.getValue(), entry.getKey());
        }
        return reverseHashMap;
    }

    public String getSha1() {
        return this.sha1;
    }

    public String getParentSha1() {
        return this.parentSha1;
    }

    public LinkedHashMap<String, String> getM() {
        return this.M;
    }

    public LinkedHashMap<String, String> getRM() {
        return this.rM;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDate() {
        return this.date;
    }


}
