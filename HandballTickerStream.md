# HandballTickerStream

A handball ticker stream holds information about the current progress of a match and consists of items representing events that happened during this match and other updates the hoster wants to publish.

    {
    	"time": {
    	    "minute": "36",
    	    "phase": "second"
    	},
    	"home": {
    	    "id": "1",
            "name": "HSV Schienbeinknacker",
            "logo": "http://hsvschienbeinknacker.de/logo.png"
    	},
    	"guest": {
    	    "id": "34",
    	    "name": "TV Gummibären",
    	    "logo": "http://tvgummibaeren.de/logo.jpg"
    	}
    	"first": {
            "home": "19",
            "guest": "21"
    	},
    	"second": {
            "home": "22",
            "guest": "21"
    	},
        "items": [
        	{
        	    "published": "2013-10-02 11:52:34",
                "time": {
                    "minute": "35",
                    "phase": "second"
                },
                "type": "score",
                "object": {
                    "score": {
                        "home": "21",
                        "guest": "22"
                    },
                    "team": "home",
                    "type": "normal",
                    "player": {
                        "id": "12",
                        "number": "1",
                        "name": "Mr. T",
                        "team": "home"
                    }
                },
                "message": "The 21st goal of Mr. T!"
        	},
        	...
        ]
    }

| Field        | Necessary | Type                                            | Description |
| ------------ | --------- | ----------------------------------------------- | ----------- |
| time         | Yes       | [Object: MatchTime](#matchtime)                 | Specifies the current match time. |
| home         | Yes       | [Object: Team](#team)                           | Specifies the team having the home team role. |
| guest        | Yes       | [Object: Team](#team)                           | Specifies the team having the guest team role. |
| first        | Yes       | [Object: Score](#score)                         | Specifies the score of the teams in the first half. |
| second       | **No**    | [Object: Score](#score)                         | Specifies the score of the teams in the second half - if reached yet. |
| items        | **No**    | List of [object: StreamItem](#stream-item)      | Lists all stream items representing updates - if any existing. |

## MatchTime

    {
        "minute": "36",
        "phase": "second"
    }

| Field  | Necessary | Type                        |  Description |
| ------ | --------- | --------------------------- | ------------ |
| minute | Yes       | int                         | Specifies the minute of the match. |
| phase  | Yes       | [MatchPhase](#match-phases) | Specifies the phase the match is in. |

### Match Phases
* warmup: match has not been started yet
* first: match is in first half
* paused: match is paused
* half-time: match is paused due to half time
* second: match is in second half
* finished: match has been finished

## Team

    {
        "id": "1",
        "name": "HSV Schienbeinknacker",
        "logo": "http://hsvschienbeinknacker.de/logo.png"
    }

| Field | Necessary | Type         | Description |
| ----- | --------- | ------------ | ----------- |
| id    | Yes       | int          | Specifies the unique team identifier. |
| name  | Yes       | String       | Specifies the team name displayed. |
| logo  | **No**    | String (URL) | Specifies the URL to the team logo displayed - if available. |



## Score

    {
        "home": "22",
        "guest": "21"
    }

| Field | Necessary | Type | Description |
| ----- | --------- | ---- | ----------- |
| home  | Yes       | int  | Specifies the score of the home team. |
| guest | Yes       | int  | Specifies the score of the guest team. |

## Stream item

Stream items have some general data but contain data specific for their type also.  
This specific data is specified in `object`.

    {
        "published": "2013-10-02 11:52:34",
        "time": {
            "minute": "35",
            "phase": "second"
        },
        "type": "score",
        "object": {
            ...
        },
        "message": "The 21st goal of Mr. T!"
    }

| Field     | Necessary | Type                                 | Description |
| --------- | --------- | ------------------------------------ | ----------- |
| published | Yes       | String (DateTime)                    | Specifies the exact time the item has been published. |
| time      | Yes       | [Object: MatchTime](#matchtime)      | Specifies the match time the item has been published. This is considered to be the time the event happened. |
| type      | Yes       | [StreamItemType](#stream-item-types) | Specifies the type of the item. |
| object    | Yes       | <Type>Item                           | Holds stream item type specific data. |
| message   | **No**    | String                               | Text message that will be displayed instead of a generated value. |

### Stream item types
* [phase-end](#phaseenditem): a match phase has been finished
* [text](#textitem): text message to viewers, no event happened necessarily
* [score](#scoreitem): a player scored
* [foul](#foulitem): a player fouled



### PhaseEndItem

The type of the stream item has to be `phase-end` so it will be considered a match phase end.

    {
        ...
        "type": "phase-end",
        "object": {
            "before": "second",
            "after": "pause",
            "subType": "injury",
            "object": {
                "player": {
                    "id": "12",
                    "number": "1",
                    "name": "Mr. T",
                    "team": "home"
                }
            }
            "message": "Mr. T threw a player through the goal..."
        }
    }

| Field   | Necessary                     | Type                                    | Description |
| ------- | ----------------------------- | --------------------------------------- | ----------- |
| before  | Yes                           | [MatchPhase](#match-phases)             | Specifies the phase that ended. |
| after   | Yes                           | [MatchPhase](#match-phases)             | Specifies the new phase that begun. |
| subType | **No**                        | [PhaseEndSubType](#phase-end-sub-types) | Specifies the sub type of the phase end - if any sub type applies. |
| object  | Yes if sub type **is set**    | <SubType>PhaseEndItem                   | Holds phase end sub type specific data. |

#### Match phase combinations allowed:

| Before    | After     | Meaning |
| --------- | --------- | ------- |
| warmup    | first     | Match started |
| first     | paused    | Match paused during first half |
| paused    | first     | Match unpaused during first half |
| first     | half-time | Half time |
| half-time | second    | Second half of the match started |
| second    | paused    | Match paused during second half |
| paused    | second    | Match unpaused during second half |
| second    | finished  | Match finished |

#### Phase end sub types
* [timeout](#timeoutphaseenditem): pause due to a timeout
* [injury](#injuryphaseenditem): pause due to an injury of a player

 
#### TimeoutPhaseEndItem

The sub type of the phase end item has to be `timeout` so it will be considered a phase end due to a timeout.

    {
        ...
        "subType": "timeout",
        "object": {
            "team": "guest"
        }
    }

| Field | Necessary | Type                    | Description |
| ----- | --------- | ----------------------- | ----------- |
| team  | Yes       | [TeamRole](#team-roles) | Specifies the role of the team that used its timeout. |

#### InjuryPhaseEndItem

The sub type of the phase end item has to be `injury` so it will be considered a phase end due to an injury of a player.

    {
        ...
        "subType": "injury",
        "object": {
            "player": {
                "id": "12",
                "number": "1",
                "name": "Mr. T",
                "team": "home"
            }
        }
    }

| Field  | Necessary | Type                      | Description |
| ------ | --------- | ------------------------- | ----------- |
| player | Yes       | [Object: Player](#player) | Specifies the player that was injured. |



### TextItem

The type of the stream item has to be `text` so it will be considered a simple update message.

    {
        ...
        "type": "text",
        "object": {
            "message": "Hello folks, this match is amazing!"
        }
    }

| Field   | Necessary | Type   | Description |
| ------- | --------- | ------ | ----------- |
| message | Yes       | String | Text message that will be displayed. |



### ScoreItem

The type of the stream item has to be `score` so it will be considered a signal for: A player scored.

    {
        ...
        "type": "score",
        "object": {
            "score": {
                "home": "21",
                "guest": "22"
            },
            "team": "home",
            "type": "normal",
            "player": {
                "id": "12",
                "number": "1",
                "name": "Mr. T",
                "team": "home"
            }
        },
        ...
    }

| Field   | Necessary | Type                          | Description |
| ------- | --------- | ----------------------------- | ----------- |
| score   | Yes       | [Object: Score](#score)       | Specifies the score of teams after the scoring. |
| team    | Yes       | [TeamRole](#team-roles)       | Specifies the role of the team scored. |
| type    | Yes       | [ScoringType](#scoring-types) | Specifies the type of the scoring. |
| player  | **No**    | [Object: Player](#player)     | Specifies the player who scored - if known. |

#### Scoring types
* normal: no special kind of scoring
* rush: scoring due to a rush
* penalty: scoring due to a penalty throw

#### Team roles
* home: team marked and set as home team in the stream
* guest: team marked and set as guest team in the stream

#### Player

    {
         "id": "12",
         "number": "1",
         "name": "Mr. T",
         "team": "home"
    }

| Field  | Necessary | Type                    | Description |
| ------ | --------- | ----------------------- | ----------- |
| id     | **No**    | int                     | Specifies the unique player identifier - if player is in the system. |
| number | Yes       | int                     | Specifies the player number used in the match. |
| name   | **No**    | String                  | Specifies the real name - if known. |
| team   | Yes       | [TeamRole](#team-roles) | Specifies the role of the team the player is playing for. |



### FoulItem

The type of the stream item has to be `foul` so it will be considered a signal for: A player fouled.

    {
        ...
        "type": "foul",
        "object": {
            "player": {
                "id": "12",
                "number": "1",
                "name": "Mr. T",
                "team": "home"
            },
            "disciplines": [
                "time",
                "yellow",
                "red"
            ],
            "victim": {
            	"id": "12",
                "number": "1",
                "name": "Mr. T",
                "team": "home"
            }
        },
        ...
    }

| Field       | Necessary | Type                               | Description |
| ----------- | --------- | ---------------------------------- | ----------- |
| player      | Yes       | [Object: Player](#player)          | Specifies the player who fouled. |
| disciplines | **No**    | List of [Discipline](#disciplines) | Lists all disciplines for this player - if any. |
| victim      | **No**    | [Object: Player](#player)          | Specifies the player who has been fouled - if known. |

#### Disciplines
* time: a simple time discipline
* penalty: penalty throw for the victim team
* yellow: yellow card shown by referee
* red: red card shown by referee
