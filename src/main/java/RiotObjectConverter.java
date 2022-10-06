import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.Set;


@Deprecated
public class RiotObjectConverter {
/*
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Set<String> dtoNameList = Set.of("metadata", "info",
            "participants", "teams", "bans", "objectives", "baron", "champion",
            "dragon", "inhibitor", "riftHerald", "tower");

    private static final Map<String, String> noSameNameDtoMap = Map.of("participants", "participant",
            "teams" , "team", "bans", "ban");

    public static <T> T convertEntity(String jsonString, String packageName, Class<T> clazz) throws Exception {
        final Map jsonMap = objectMapper.readValue(jsonString, Map.class);
        T t = clazz.getConstructor(null).newInstance();

        jsonMap.keySet().forEach((keyObj)->{
            try {
                String fieldName = (String) keyObj;

                if(!dtoNameList.contains(fieldName)){
                    Field field = clazz.getField(fieldName);
                    field.set(t, jsonMap.get(keyObj));
                }
                else{
                    if(noSameNameDtoMap.containsKey(fieldName)){
                        fieldName = noSameNameDtoMap.get(fieldName);
                    }
                    fieldName = capitalize(fieldName);

                    final Class<?> subClazz = Class.forName(packageName + fieldName);

                    Object subObject = convertEntity((String) jsonMap.get(keyObj), packageName, subClazz);

                    Field field = clazz.getField(fieldName);
                    field.set(t, subObject);
                }
            }catch (Exception e){}
        });

        return null;
    }


    private static String capitalize(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }


 */
}
