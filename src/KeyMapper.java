import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.internal.IterableIterate;

import java.util.Map;

public class KeyMapper {
    public final Map<Integer, Integer> keyMap;

    public KeyMapper() {
        UnifiedMap<Integer, Integer> map = new UnifiedMap<>();

        map.put(37, 0);
        map.put(38, 1);
        map.put(39, 2);
        map.put(40, 3);
        map.put(32, 4);
        map.put(65, 5);
        map.put(87, 6);
        map.put(68, 7);
        map.put(83, 8);

        keyMap = map.toImmutable().toMap();
    }
}
