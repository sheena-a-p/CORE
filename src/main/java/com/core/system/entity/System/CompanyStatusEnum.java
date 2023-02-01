package com.core.system.entity.System;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.*;

/* Company Status enum
 * Author Sheena AP
 */
@AllArgsConstructor
@Getter
public enum CompanyStatusEnum {

    ACTIVE("Active Company",110),
    INACTIVE("Inactive Company",111);

    private final String key;
    private final Integer value;

    public static  String getKeyOf(Integer value){
        String key= "";
        if(Objects.equals(value, ACTIVE.value)){
            key = ACTIVE.key;
        } else if(Objects.equals(value, INACTIVE.value)) {
            key = INACTIVE.key;
        }
        return key;
    }

    public static Map<String,Integer> getAll(){
        Map<String,Integer> map = new HashMap<>();
        map.put(ACTIVE.key, ACTIVE.value);
        map.put(INACTIVE.key, INACTIVE.value);
        return SortByValue(map);
    }

    public static Map<String, Integer> SortByValue(Map<String, Integer> hm) {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.comparingByValue());
        HashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            result.put(aa.getKey(), aa.getValue());
        }
        return result;
    }
}
