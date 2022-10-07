import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Summoner;
import entity.match.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public Summoner getSummonerByName(String summonerName) throws RuntimeException, IOException{
        final String responseString = ApiConnector.getResponseString(KR_DEFAULT_HOST, "/lol/summoner/v4/summoners/by-name/" + URLEncoder.encode(summonerName, "UTF-8"), null, RiotToken);

        return objectMapper.readValue(responseString, Summoner.class);
    }


    public List<Match> getMatchesBySummonerName(String summonerName, int startIndex, int count) throws RuntimeException, IOException{
        final List<String> matchIdList = getMatchIdList(summonerName, startIndex, count);

        return matchIdList.stream().map(matchId->{
            try{
                return getMatchById(matchId);
            }catch (IOException e){return null;}
        }).collect(Collectors.toList());

    }


    public Match getMatchById(String matchId) throws RuntimeException, IOException{
        final String responseString = ApiConnector.getResponseString(ASIA_DEFAULT_HOST, "/lol/match/v5/matches/" + matchId, null, RiotToken);

        return objectMapper.readValue(responseString, Match.class);
    }


    private List<String> getMatchIdList(String summonerName, Integer startIndex, Integer count) throws IOException {
        Summoner summoner = getSummonerByName(summonerName);
        String urlString = ASIA_DEFAULT_HOST +  "/lol/match/v5/matches/by-puuid/" +
                URLEncoder.encode(summoner.getPuuid(), "UTF-8") + "/ids" +
                "?start=" + startIndex + "&count=" + count;

        String responseString = ApiConnector.getResponseString(ASIA_DEFAULT_HOST, "/lol/match/v5/matches/by-puuid/" +
                        URLEncoder.encode(summoner.getPuuid(), "UTF-8") + "/ids",
                Map.of("start", startIndex.toString(), "count", count.toString()), RiotToken);

        return objectMapper.readValue(responseString, List.class);
    }

    protected static class ApiConnector{

        public static String getResponseString(String host, String uri, Map<String, String> params, String token) throws IOException {
            String paramString = "";
            if(params != null){
                paramString = setParamString(params);
            }
            String urlString = host + uri + paramString;

            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setHttpHeader(conn, token);
            return getResponseBody(conn);
        }

        private static String setParamString(Map<String, String> params) {
            StringBuilder sb = new StringBuilder("?");

            for (String key : params.keySet()) {
                sb.append("&");
                sb.append(key);
                sb.append("=");
                sb.append(params.get(key));
            }

            sb.deleteCharAt(1);
            return sb.toString();
        }

        private static void setHttpHeader(HttpURLConnection conn, String token) throws ProtocolException {
            conn.setRequestMethod(REQUEST_METHOD_GET);
            conn.setRequestProperty(REQUEST_TOKEN_KEY, token);
            conn.setRequestProperty("Accept-Language", "ko");
            conn.setRequestProperty("Accept-Charset", "*");
            conn.setRequestProperty("Origin", "https://developer.riotgames.com");
        }

        private static String getResponseBody(HttpURLConnection conn) throws IOException, CannotFindData, InvalidToken, ExceedRateLimit{
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
            return sb.toString();
        }

        private static void checkIsDataFound(HttpURLConnection conn) throws IOException, RuntimeException{
            int responseCode = conn.getResponseCode();
            if(responseCode == 403){
                throw new InvalidToken("Token이 유효한지 확인해 주세요");
            }
            else if(responseCode == 404){
                throw new CannotFindData("데이터를 찾을 수 없습니다.");
            }
            else if(responseCode == 429){ throw new ExceedRateLimit("잠시 후에 시도해 주세요.");}
        }
    }

    public static class CannotFindData extends RuntimeException{
        public CannotFindData() {
        }

        public CannotFindData(String message) {
            super(message);
        }
    }
    public static class InvalidToken extends RuntimeException{
        public InvalidToken() {
        }

        public InvalidToken(String message) {
            super(message);
        }
    }
    public static class ExceedRateLimit extends RuntimeException{
        public ExceedRateLimit() {
        }

        public ExceedRateLimit(String message) {
            super(message);
        }
    }


}
