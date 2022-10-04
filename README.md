## Riot API Usage

get summoner, game data from riot develop portal

https://developer.riotgames.com/

check riot policy

https://developer.riotgames.com/policies/general

### Create Connection Object
<hr/>

```java
private String API_KEY = "Riot development & production API KEY"; 
private RiotAPIConnection con =
  new RiotAPIConnection(API_KEY);
```

<br><br>

### Get Summoner Data By Summoner name
<hr/>

```java
Summoner summoner = con.getSummonerByName("{summonerName}");
```