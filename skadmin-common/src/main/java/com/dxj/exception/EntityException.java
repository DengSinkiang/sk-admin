package com.dxj.exception;

<<<<<<< HEAD
import org.springframework.util.StringUtils;
=======
import org.apache.commons.lang3.StringUtils;
>>>>>>> dev

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

<<<<<<< HEAD
=======
/**
 * @Author: dxj
 * @Date: 2019-05-14 09:58
 */
>>>>>>> dev
class EntityException {


    static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

    static String generateMessage(String entity, String msg, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + msg + searchParams;
    }

}
