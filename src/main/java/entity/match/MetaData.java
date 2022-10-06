package entity.match;

import lombok.Getter;

import java.util.List;

@Getter
public class MetaData {
    private String dataVersion;
    private String matchId;
    private List<String> participants;
}
