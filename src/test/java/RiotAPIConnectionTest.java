import entity.Summoner;
import entity.match.Match;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class RiotAPIConnectionTest {

    private RiotAPIConnection con = new RiotAPIConnection(Config.getRiotToken());
    private static final String summonerName1 = "철이네이삿짐센터";
    private static final String summonerName2 = "근 뇽";
    private static final String matchId = "KR_6159112812";
    @Test
    @DisplayName("연결 확인")
    void t1(){
         assertThat(con).isNotNull();
    }


    @Test
    @DisplayName("소환사 이름으로 조회")
    void t2() throws Exception{
        Summoner findSummoner1 = con.getSummonerByName(summonerName1);
        Summoner findSummoner2 = con.getSummonerByName(summonerName2);

        assertThat(findSummoner1.getName()).isEqualTo(summonerName1);
        assertThat(findSummoner2.getName()).isEqualTo(summonerName2);
    }

    @Test
    @DisplayName("게임 1개 조회")
    void t3() throws Exception {
        //given
        //when
        Match findMatch = con.getMatchById(matchId);
        //then

        assertThat(findMatch.getMetadata()).isNotNull();
        assertThat(findMatch.getInfo()).isNotNull();
    }

    @Test
    @DisplayName("게임 5개 조회")
    void t4() throws Exception {
        //given

        //when
        List<Match> matches = con.getMatchesBySummonerName(summonerName1, 0, 5);
        //then
        assertThat(matches).doesNotContainNull();
        assertThat(matches.size()).isEqualTo(5);
    }
}