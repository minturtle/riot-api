import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Summoner;
import entity.match.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

public class RiotAPIConnection {

    private static final String KR_DEFAULT_HOST = "https://kr.api.riotgames.com";
    private static final String ASIA_DEFAULT_HOST = "https://asia.api.riotgames.com";
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_TOKEN_KEY = "X-Riot-Token";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String RiotToken;

    public RiotAPIConnection(String riotToken) {
        RiotToken = riotToken;
    }


    public Summoner getSummonerByName(String summonerName) throws RuntimeException, IOException{
        String urlString = KR_DEFAULT_HOST +  "/lol/summoner/v4/summoners/by-name/" + URLEncoder.encode(summonerName, "UTF-8");

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setHttpHeader(conn);

        Summoner result = getResponseBody(conn, Summoner.class);
        return result;
    }


    public List<Match> getMatchesBySummonerName(String summonerName, int startIndex, int count) throws RuntimeException, IOException{
        Summoner summoner = getSummonerByName(summonerName);
        String urlString = ASIA_DEFAULT_HOST +  "/lol/match/v5/matches/by-puuid/" +
                URLEncoder.encode(summoner.getPuuid(), "UTF-8") + "/ids" +
                "?start=" + startIndex + "&count=" + count;

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setHttpHeader(conn);

        List<String> matchIdList = (List<String>) getResponseBody(conn, List.class).stream().map(Object::toString).collect(Collectors.toList());


        return null;
    }

    public Match getMatchById(String matchId) throws RuntimeException, IOException{
        URL url = new URL("https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setHttpHeader(conn);

        return getResponseBody(conn, Match.class);

    }

    private void setHttpHeader(HttpURLConnection conn) throws ProtocolException {
        conn.setRequestMethod(REQUEST_METHOD_GET);
        conn.setRequestProperty(REQUEST_TOKEN_KEY, RiotToken);
        conn.setRequestProperty("Accept-Language", "ko");
        conn.setRequestProperty("Accept-Charset", "*");
        conn.setRequestProperty("Origin", "https://developer.riotgames.com");
    }

    private void checkIsDataFound(HttpURLConnection conn) throws IOException, RuntimeException{
        int responseCode = conn.getResponseCode();
        if(responseCode == 403){
            throw new InvalidToken("Token이 유효한지 확인해 주세요");
        }
        else if(responseCode == 404){
            throw new CannotFindData("데이터를 찾을 수 없습니다.");
        }
        else if(responseCode == 429){ throw new ExceedRateLimit("잠시 후에 시도해 주세요.");}
    }
    private <T> T getResponseBody(HttpURLConnection conn, Class<T> clazz) throws IOException, CannotFindData, InvalidToken, ExceedRateLimit{
        checkIsDataFound(conn);
        StringBuffer sb = new StringBuffer();
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        ){
            String inputLine;
            while ((inputLine = br.readLine()) != null)  {
                sb.append(inputLine);
            }
        }
        return objectMapper.readValue(sb.toString() , clazz);
    }






    public class CannotFindData extends RuntimeException{
        public CannotFindData() {
        }

        public CannotFindData(String message) {
            super(message);
        }
    }
    public class InvalidToken extends RuntimeException{
        public InvalidToken() {
        }

        public InvalidToken(String message) {
            super(message);
        }
    }
    public class ExceedRateLimit extends RuntimeException{
        public ExceedRateLimit() {
        }

        public ExceedRateLimit(String message) {
            super(message);
        }
    }
}
