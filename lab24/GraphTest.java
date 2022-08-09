import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void prims() {
        Graph x = Graph.loadFromText("inputs/normal");
        Graph tar = x.prims(0);
        System.out.println(x.spans(tar));

        Graph x1 = Graph.loadFromText("inputs/graphTestMultiEdge.in");
        Graph tar1 = x1.prims(0);
        System.out.println(x1.spans(tar1));

        Graph x2 = Graph.loadFromText("inputs/graphTestSomeDisjoint.in");
        Graph tar2 = x2.prims(0);
        System.out.println(x2.spans(tar2));

    }

}