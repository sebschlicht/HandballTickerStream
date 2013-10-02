# HandballTickerStream

A handball ticker stream holds information about the current progress of a match.  
This includes the current game time, the half time the game is in and information about the two match partner with their match scores for the first and second half.   
In addition the stream consists of items representing a events that happened during this match.

    {
    	"time": {
    	    "minute": "36",
    	    "half": "2"
    	},
    	"teams": [
    	    {
    	        "role": "home",
    		    "id": "1",
    		    "name": "Dixie Lions",
    		    "score": "21"
    		},
    		{
    		    "role": "guest",
    		    "id": "2",
    		    "name": "Ragers",
    		    "score": "22"
    		}
    	],
    	"scores": [
    	    {
    	        "name": "first",
    	        "teams": [
    	            {
    	                "id": "1",
    	                "score": "19"
    	            },
    	            {
    	                "id": "2",
    	                "score": "22"
    	            }
    	        ]
    	    }
    	],
        "items": [
        	{
        	    // stream item
        	    ...
        	},
        	{
        	    // stream item
        	    ...
        	},
        	...
        ]
    }

| Field        | Necessary | Type                                        | Description |
| ------------ |:---------:|:-------------------------------------------:|
| time         | Yes                | [Object: Time](TODO)               | Specifies the current match time. |
| teams        | Yes                | List of [object: Team](TODO)       | Lists all teams involved in the match being streamed. |
| scores       | Yes (can be empty) | List of [object: Score](TODO)      | Lists saved scores representing intermediary or final results. |
| items        | Yes (can be empty) | List of [object: StreamItem](TODO) | Lists all stream items representing any update. |

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

