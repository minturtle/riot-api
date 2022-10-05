package dto.match;


import entity.match.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoDto {
    private Long gameCreation;
    private Long gameDuration;
    private Long gameEndTimestamp;
    private Long gameId;
    private String gameMode;
    private String gameName;
    private Long gameStartTimestamp;
    private String gameType;
    private String gameVersion;
    private Integer mapId;
    private List<String> participants;
    private String platfromId;
    private Integer queueId;
    private List<Team> temas;
    private String tournamentCode;
}
