package com.core.system.entity.System;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.*;

/* User Account Status emum
 * Author Sheena AP
 */
@AllArgsConstructor
@Getter
public enum UserAccountStatusEnum {
    ACTIVE("Active User",100),
    INACTIVE("Inactive User",101),
    DELETED("Deleted User",102);

    private final String key;
    private final Integer value;

    public static  String getKeyOf(Integer value){
        String key= "";
        if(Objects.equals(value, ACTIVE.value)){
            key = ACTIVE.key;
        } else if(Objects.equals(value, INACTIVE.value)){
            key = INACTIVE.key;
        }else if(Objects.equals(value, DELETED.value)){
            key = DELETED.key;
        }
        return key;
    }

    public static Map<String,Integer> getAll(){
        Map<String,Integer> map = new HashMap<>();
        map.put(ACTIVE.key, ACTIVE.value);
        map.put(INACTIVE.key, INACTIVE.value);
        map.put(DELETED.key, DELETED.value);
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
