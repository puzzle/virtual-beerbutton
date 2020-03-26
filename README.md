# The virtual Puzzle button

The virtual Puzzle Button is a small web application, that allows you to let your colleagues know when ever you're waiting at your virtual or physical break space and want to have company.

You can find the whole idea and how we're using the virtual Coffee and Beer button at Puzzle in the [German Blogpost](https://www.puzzle.ch/de/blog/articles/2020/03/23/der-puzzle-fyrabebier-knopf)

## Configuration

The two buttons basically once clicked send post requests to backend urls.

### Beer Button

| Environmentvariable                  | Description           | Example  |
| ------------------------------------ |---------------------|----------|
| APPLICATION_BACKENDURLBEER           | Backend url           | https://homeassistantserver/api/services/script/turn_on  |
| APPLICATION_AUTHTOKENBEER (optional) | The BearerToken to be sent to the backend |  |
| APPLICATION_PAYLOADBEER (optional)   | The Payload that will be sent to the backend in the request | {"entity_id": "script.notify_remote_fyrabebier"} |


### Coffee Button

| Environmentvariable                  | Description           | Example  |
| ------------------------------------ |---------------------|----------|
| APPLICATION_BACKENDURLCOFFEE           | Backend url           | https://myrocketchat.ch/hooks/DKC4GeDPWYnjKEb5g/jDKAB5zbaG2tHx94cQny7epqAg2QBaE3QFdyBGmYoy3tekgi  |
| APPLICATION_AUTHTOKENCOFFEE (optional) | The BearerToken to be sent to the backend |  |
| APPLICATION_PAYLOADCOFFEE (optional)   | The Payload that will be sent to the backend in the request | { "icon_emoji": ":ghost:", "text": "Let's have a coffee, join me please [Meetingroom](https://linktoyoumeetingroom)", |




## Build application 

To build the Springboot application just run

```bash
./gradlew build
```

Or directly build the docker image

```bash
docker build -it virtual-puzzle-button .
```

## pre built docker image from dockerhub

https://hub.docker.com/r/puzzle/virtual-beerbutton