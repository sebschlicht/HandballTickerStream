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
    	"scores": {
    	    "first": {
    	        {
                    "home": "19",
                    "guest": "21"
                }
    	    },
    	    "second": {
    	        {
                    "home": "22",
                    "guest": "21"
                }
    	    }
    	},
        "items": [
        	{
        	    // TODO: INSERT ScoreItem
        	},
        	...
        ]
    }

| Field        | Necessary          | Type                                    | Description |
| ------------ |:------------------:|:---------------------------------------:| ----------- |
| time         | Yes                | [Object: MatchTime](TODO)               | Specifies the current match time. |
| home         | Yes                | [Object: Team](TODO)                    | Specifies the home team. |
| guest        | Yes                | [Object: Team](TODO)                    | Specifies the guest team. |
| scores       | Yes                | [Object: Scores](TODO)                  | Lists the current score and all previous intermediate scores. |
| items        | Yes (can be empty) | List of [object: StreamItem](TODO)      | Lists all stream items representing any update. |

## MatchTime

    {
        "minute": "36",
        "phase": "second"
    }

| Field  | Necessary | Type   |  Description |
| ------ |:---------:|:------:| ------------ |
| minute | Yes       | int    | Specifies the minute of the match. |
| phase  | Yes       | String | Specifies the phase the match is in.  Valid values below. |

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
| ----- |:---------:|:------------:| ----------- |
| id    | Yes       | int          | Specifies the unique team identifier. |
| name  | Yes       | String       | Specifies the team name displayed. |
| logo  | **No**    | String (URL) | Specifies the URL to the team logo displayed if available. |

## Scores

    {
        "first": {
            {
                "home": "19",
                "guest": "21"
            }
        },
        "second": {
            {
                "home": "22",
                "guest": "21"
            }
        }
    }

| Field  | Necessary | Type                  | Description |
| ------ |:---------:|:---------------------:| ----------- |
| first  | Yes       | [Object: Score](TODO) | Specifies the score of the teams in the first half. |
| second | **No**    | [Object: Score](TODO) | Specifies the score of the teams in the second half if reached yet. |


## Score

    {
        "home": "22",
        "guest": "21"
    }

| Field | Necessary | Type | Description |
| ----- |:---------:|:----:| ----------- |
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
| --------- |:---------:|:-------------------------:| ----------- |
| published | Yes       | String (DateTime)         | Specifies the exact time the item has been published. |
| time      | Yes       | [Object: MatchTime](TODO) | Specifies the match time the item has been published. This is considered to be the time the event happened. |
| type      | Yes       | String                    | Specifies the type of the item. Valid values below. |
| object    | Yes       | <Type>Item                | Holds stream item type specific data. |

### Types

There are several types for stream objects:
* [phase-end](TODO): the current phase has been finished
* [text](TODO): text message to streamers, no event happened necessarily
* [score](TODO): a player scored
* [foul](TODO): a player fouled
* [break](TODO): a team took a break

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
| ------- |:---------:|:------:| ----------- |
| message | Yes       | String | Text message that will be displayed. |

# TODO

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
            "player": {
                "id": "12",
                "number": "12",
                "name": "Mr. T",
                "team": "home"
            },
            "message": "The 21st goal of Mr. T!"
        }
    }

  
*score* is a object containing the current score of each team.  
*team* specifies which team scored due to the player's action.  
*player* holds information about the [player](TODO) scored.  
The *message* is optional holding a comment.

## Foul object

The type has to be `foul` so the object will be considered a foul object signalizing: A player fouled.

    {
        ...
        "type": "foul",
        "object": {
            "player": {
                "id": "12",
                "number": "12",
                "name": "Mr. T"
            },
            "disciplines": [
                "time",
                "yellow",
                "red"
            ],
            "victim": {
            	"id": "154",
            	"number": "7",
            	"name": "victim0815"
            }
        }
    }

*player* holds information about the [player](TODO) who fouled.  
*disciplines* holds the list of [disciplines](TODO) for that player.  
*victim* is optional and holds information about the player who has been fouled.

### Disciplines
* time: a simple time discipline
* yellow: yellow card shown by referee
* red: red card shown by referee

## Break object

The type has to be `break` so the object will be considered a break object signalizing: A team took a break.

    {
        ...
        "type": "break",
        "object": {
            "team": "home"
        }
    }

