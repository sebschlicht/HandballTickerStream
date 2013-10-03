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
        	    // TODO: INSERT ScoreItem
        	},
        	...
        ]
    }

| Field        | Necessary | Type                                    | Description |
| ------------ | --------- | --------------------------------------- | ----------- |
| time         | Yes       | [Object: MatchTime](TODO)               | Specifies the current match time. |
| home         | Yes       | [Object: Team](TODO)                    | Specifies the home team. |
| guest        | Yes       | [Object: Team](TODO)                    | Specifies the guest team. |
| first        | Yes       | [Object: Score](TODO)                   | Specifies the score of the teams in the first half. |
| second       | **No**    | [Object: Score](TODO)                   | Specifies the score of the teams in the second half - if reached yet. |
| items        | **No**    | List of [object: StreamItem](TODO)      | Lists all stream items representing updates - if any existing. |

## MatchTime

    {
        "minute": "36",
        "phase": "second"
    }

| Field  | Necessary | Type               |  Description |
| ------ | --------- | ------------------ | ------------ |
| minute | Yes       | int                | Specifies the minute of the match. |
| phase  | Yes       | [MatchPhase](TODO) | Specifies the phase the match is in.  Valid values below. |

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
            "minute": "21",
            "half": "1"
        },
        "type": "score",
        "object": {
            ...
        }
    }

| Field     | Necessary | Type                      | Description |
| --------- | --------- | ------------------------- | ----------- |
| published | Yes       | String (DateTime)         | Specifies the exact time the item has been published. |
| time      | Yes       | [Object: MatchTime](TODO) | Specifies the match time the item has been published. This is considered to be the time the event happened. |
| type      | Yes       | [StreamItemType](TODO)    | Specifies the type of the item. Valid values below. |
| object    | Yes       | <Type>Item                | Holds stream item type specific data. |

### Stream item types

There are several types for stream items:
* [phase-end](TODO): a match phase has been finished
* [text](TODO): text message to viewers, no event happened necessarily
* [score](TODO): a player scored
* [foul](TODO): a player fouled



### PhaseEndItem

The type has to be `phase-end` so the stream item will be considered a match phase end.

    {
        "before": "paused",
        "after": "second",
        "message": "Mr. T let down the two players he lifted up before, the match goes on..."
    }

| Field   | Necessary                     | Type                  | Description |
| ------- | ----------------------------- | --------------------- | ----------- |
| before  | Yes                           | [MatchPhase](TODO)       | Specifies the phase that ended. |
| after   | Yes                           | [MatchPhase](TODO)       | Specifies the new phase that begun. |
| subType | **No**                        | [PhaseEndSubType](TODO)  | Specifies the sub type of the phase end - if any sub type applies. Valid values below. |
| object  | Yes if sub type **is set**    | <SubType>PhaseEndItem    | Holds phase end sub type specific data. |
| message | **No**                        | String                   | Text message that will be displayed instead of a generated value. |

#### Phase ends allowed:

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
* timeout: pause due to a timeout
* injury: pause due to an injury of a player
 


#### TimeoutPhaseEndItem

The sub type has to be `timeout` so the stream item will be considered a phase end due to a timeout.

    {
        ...
        "subType": "timeout",
        "object": {
            "team": "guest"
        }
    }

| Field | Necessary | Type             | Description |
| ----- | --------- | ---------------- | ----------- |
| team  | Yes       | [TeamRole](TODO) | Specifies the role of the team that used its timeout. |



#### InjuryPhaseEndItem

The sub type has to be `injury` so the stream item will be considered a phase end due to an injury of a player.

    {
        ...
        "subType": "injury",
        "object": {
            "player": {
                // TODO: INSERT Player
            }
        }
    }

| Field  | Necessary | Type                   | Description |
| ------ | --------- | ---------------------- | ----------- |
| player | Yes       | [Object: Player](TODO) | Specifies the player that was injured. |




### TextItem

The type has to be `text` so the stream item will be considered a simple update message.

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

The type has to be `score` so the stream item will be considered a signal for: A player scored.

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
                // TODO: INSERT Player
            },
            "message": "The 21st goal of Mr. T!"
        }
    }

| Field   | Necessary | Type                   | Description |
| ------- | --------- | ---------------------- | ----------- |
| score   | Yes       | [Object: Score](TODO)  | Specifies the score of teams after the scoring. |
| team    | Yes       | [TeamRole](TODO)       | Specifies the role of the team scored. |
| type    | Yes       | [ScoreType](TODO)      | Specifies the type of the scoring. |
| player  | **No**    | [Object: Player](TODO) | Specifies the player who scored - if known. |
| message | **No**    | String                 | Text message that will be displayed instead of a generated value. |

#### Team roles
* home: team marked and set as home team in the stream
* guest: team marked and set as guest team in the stream

#### Scoring types
* normal: no special kind of scoring
* rush: scoring due to a rush
* penalty: scoring due to a penalty throw



### FoulItem

The type has to be `foul` so the stream item will be considered a signal for: A player fouled.

    {
        ...
        "type": "foul",
        "object": {
            "player": {
                // TODO: INSERT Player
            },
            "disciplines": [
                "time",
                "yellow",
                "red"
            ],
            "victim": {
            	// TODO: INSERT Player
            },
            "message": "Mr. T crushed another bone..."
        }
    }

| Field       | Necessary | Type                       | Description |
| ----------- | --------- | -------------------------- | ----------- |
| player      | Yes       | [Object: Player](TODO)     | Specifies the player who fouled. |
| disciplines | **No**    | List of [Discipline](TODO) | Lists all disciplines for this player - if any. Valid values below. |
| victim      | **No**    | [Object: Player](TODO)     | Specifies the player who has been fouled - if known. |
| message     | **No**    | String                     | Text message that will be displayed instead of a generated value. |

#### Disciplines
* time: a simple time discipline
* penalty: penalty throw for the victim team
* yellow: yellow card shown by referee
* red: red card shown by referee
