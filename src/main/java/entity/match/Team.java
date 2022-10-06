package entity.match;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Team {
    private List<Ban> bans;
    private Objectives objectives;
    private Boolean win;
}
