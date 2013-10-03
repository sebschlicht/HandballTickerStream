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
    	        // TODO: INSERT Score
    	    },
    	    "second": {
    	        // TODO: INSERT Score
    	    }
    	},
        "items": [
        	{
        	    // TODO: INSERT ScoreItem
        	},
        	...
        ]
    }

| Field        | Necessary          | Type                               | Description |
| ------------ |:------------------:|:----------------------------------:| ----------- |
| time         | Yes                | [Object: Time](TODO)               | Specifies the current match time. |
| home         | Yes                | [Object: Team](TODO)               | Specifies the home team. |
| guest        | Yes                | [Object: Team](TODO)               | Specifies the guest team. |
| scores       | Yes                | [Object: Scores](TODO)             | Lists the current score and all previous intermediate scores. |
| items        | Yes (can be empty) | List of [object: StreamItem](TODO) | Lists all stream items representing any update. |

## Time

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

### Fields
* id: unique team identifier
* name: team name
* logo: URL to the team logo

## Scores

    {
        "first": {
            // TODO: INSERT Score
        },
        "second": {
            // TODO: INSERT Score
        }
    }




## Score

    {
        "home": "22",
        "guest": "21"
    }

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

*published* holds the exact time the stream object has been published.  
*game-time* is the game minute when the object has been published.  
*type* determines the type of the stream object

### Types

There are several types for stream objects:
* [text](TODO): text message to streamers, no event happened necessarily
* [score](TODO): a player scored
* [foul](TODO): a player fouled
* [break](TODO): a team took a break
* [half-time](TODO): the current half-time has been left (half time/match end)

## Text object

The type has to be `text` so the object will be considered a text object being a simple update message.

    {
        ...
        "type": "text",
        "object": {
            "message": "Hello folks, this match is amazing!"
        }
    }

*message* holds the update message to be displayed.

## Score object

The type has to be `score` so the object will be considered a score object signalizing: A player scored.

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

