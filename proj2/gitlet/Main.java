package gitlet;

import java.util.Objects;

import static gitlet.Repository.CWD;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 * Function Check: init, add, commit, log, checkout(specific file checkout)
 * merge, branch, checkout for whole branch, global-log, find, status, reset, rm (ALL CHECK)
 *
 * Important: The JAVA file is collaborated by Yichao DAI (s194) and Ong Aldrin (s240)
 *
 * @author Evan Day (YICHAO)
 * @Email yichaoday_evan@berkeley.edu
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        if (!Objects.equals(args[0], "init")) {
            if (!Utils.join(CWD, ".gitlet").exists()) {
                System.out.println("Not in an initialized Gitlet directory.");
                return;
            }
        }
        switch (firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(args);
                break;
            case "commit":
                if (args.length == 1 || Objects.equals(args[1], "")) {
                    System.out.println("Please enter a commit message.");
                    break;
                } else {
                    Repository.commit(args);
                }
                break;
            case "checkout":
                // For the previous checkout
                if (args.length == 3 && Objects.equals(args[1], "--")) {
                    Repository.preCheck(args);
                    break;
                }
                if (args.length == 2) {
                    Repository.branchCheckout(args);
                    break;
                }
                // For the specific UID file checkout
                if (args.length == 4) {
                    Repository.checkOut(args);
                    break;
                }
                break;
            case "log":
                Repository.log();
                break;
            case "global-log":
                Repository.globalLog();
                break;
            case "rm":
                Repository.rm(args);
                break;
            case "rm-branch": // not sure about this
                Repository.rmBranch(args);
                break;
            case "find": // not sure about this
                Repository.find(args);
                break;
            case "branch":
                Repository.branch(args);
                break;
            case "status":
                Repository.status();
                break;
            case "reset":
                Repository.reset(args);
                break;
            case "merge":
                Repository.merge(args);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }
}
