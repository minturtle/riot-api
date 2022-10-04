package entity;



public class Summoner {
    private String accountId;
    private Integer profileIconId;
    private Long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private Long summonerLevel;

    public Summoner() {
    }

    public Summoner(String accountId, Integer profileIconId, Long revisionDate, String name, String id, String puuid, Long summonerLevel) {
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.name = name;
        this.id = id;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
    }


    public String getAccountId() {
        return accountId;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public Long getRevisionDate() {
        return revisionDate;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPuuid() {
        return puuid;
    }

    public Long getSummonerLevel() {
        return summonerLevel;
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "accountId='" + accountId + '\'' +
                ",\n profileIconId=" + profileIconId +
                ",\n revisionDate=" + revisionDate +
                ",\n name='" + name + '\'' +
                ",\n id='" + id + '\'' +
                ",\n puuid='" + puuid + '\'' +
                ",\n summonerLevel=" + summonerLevel +
                '}';
    }
}
