import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RiotObjectConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String PACKAGE_PATH = "entity.match.";

    public static <T> T convertMatchEntity(Map jsonString, Class<T> clazz){
        jsonString.keySet().forEach((keyObj)->{
            String key = (String)keyObj;

            Pattern p = Pattern.compile("[a-zA-Z]dto");
            Matcher m = p.matcher(key);

            if(m.find()){
                m.group();
            }



        });
    }
}
