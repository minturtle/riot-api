package entity.match;

import dto.match.InfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class Info {

    public Info(InfoDto infoDto) {
        this.gameCreation = infoDto.getGameCreation();
        this.gameDuration = infoDto.getGameDuration();
        this.gameEndTimestamp = infoDto.getGameEndTimestamp();
        this.gameId = infoDto.getGameId();
        this.gameMode = infoDto.getGameMode();
        this.gameName = infoDto.getGameName();
        this.gameStartTimestamp = infoDto.getGameStartTimestamp();
        this.gameType = infoDto.getGameType();
        this.gameVersion = infoDto.getGameVersion();
        this.mapId = infoDto.getMapId();
        this.platfromId = infoDto.getPlatfromId();;
        this.queueId = infoDto.getQueueId();
        this.tournamentCode = infoDto.getTournamentCode();
    }

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
    private List<Participant> participants;
    private String platfromId;
    private Integer queueId;
    private List<Team> teams;
    private String tournamentCode;

}
