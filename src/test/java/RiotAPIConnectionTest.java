import entity.Summoner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class RiotAPIConnectionTest {

    private RiotAPIConnection con = new RiotAPIConnection(Config.getRiotToken());
    private static final String summonerName1 = "철이네이삿짐센터";
    private static final String summonerName2 = "근 뇽";
    @Test
    @DisplayName("연결 확인")
    void test1(){
         assertThat(con).isNotNull();
    }


    @Test
    @DisplayName("소환사 이름으로 조회")
    void test2() throws Exception{
        Summoner findSummoner1 = con.getSummonerByName(summonerName1);
        Summoner findSummoner2 = con.getSummonerByName(summonerName2);

        assertThat(findSummoner1.getName()).isEqualTo(summonerName1);
        assertThat(findSummoner2.getName()).isEqualTo(summonerName2);
    }


}