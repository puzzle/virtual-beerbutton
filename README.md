# The virtual Puzzle button

The virtual Puzzle Button is a small web application, that allows you to let your colleagues know when ever you're waiting at your virtual or physical break space and want to have company.

You can find the whole idea and how we're using the virtual Coffee and Beer button at Puzzle in the [German Blogpost](https://www.puzzle.ch/de/blog/articles/2020/03/23/der-puzzle-fyrabebier-knopf)

In addition to the virtual coffee and beer buttons the application also provides a moodmeter (`http://<virtual-button-hostname>/vote/<survey>`), where the audience of a talk, or the participants of a meeting can vote how they liked the meeting. The results of those surveys are exposed as prometheus metrics (port: `9000`, path `/actuator/prometheus`), and can be visualised in a grafana dashboard.

The `<survey>` part is dynamic and gives you the possibility to run multiple surveys at the same time. As well as the actual value ( from 1 to 5; meaning bad to verygood) the survey (`group=<survey>`) will be exposed as label.

Votes on the URL `http://<virtual-button-hostname>/vote/my-meeting` therefore will be exposed as metrics like this:


```
...
# HELP puzzle_virtualbutton_vote_total  
# TYPE puzzle_virtualbutton_vote_total counter
puzzle_virtualbutton_vote_total{group="my-meeting",value="3",} 11.0
puzzle_virtualbutton_vote_total{group="my-meeting",value="4",} 1.0
...

```

## Configuration

The two buttons basically once clicked send post requests to backend urls.

### Beer Button

| Environmentvariable                  | Description           | Example  |
| ------------------------------------ |---------------------|----------|
| APPLICATION_BACKENDURLBEER           | Backend url           | https://homeassistantserver/api/services/script/turn_on or https://myrocketchat.ch/hooks/DKC4GeDPWYnjKEb5g/jDKAB5zbaG2tHx94cQny7epqAg2QBaE3QFdyBGmYoy3tekgi or what ever ULR you'd like to send a post request to, when the button was clicked |
| APPLICATION_AUTHTOKENBEER (optional) | The BearerToken to be sent to the backend |  |
| APPLICATION_PAYLOADBEER (optional)   | The Payload that will be sent to the backend in the request | {"entity_id": "script.notify_remote_fyrabebier"} |


### Coffee Button

| Environmentvariable                  | Description           | Example  |
| ------------------------------------ |---------------------|----------|
| APPLICATION_BACKENDURLCOFFEE           | Backend url           | https://homeassistantserver/api/services/script/turn_on or https://myrocketchat.ch/hooks/DKC4GeDPWYnjKEb5g/jDKAB5zbaG2tHx94cQny7epqAg2QBaE3QFdyBGmYoy3tekgi or what ever ULR you'd like to send a post request to, when the button was clicked  |
| APPLICATION_AUTHTOKENCOFFEE (optional) | The BearerToken to be sent to the backend |  |
| APPLICATION_PAYLOADCOFFEE (optional)   | The Payload that will be sent to the backend in the request | { "icon_emoji": ":ghost:", "text": "Let's have a coffee, join me please [Meetingroom](https://linktoyoumeetingroom)" } |


### Voter

| Environmentvariable                  | Description                   | Example  |
| ------------------------------------ |-------------------------------|----------|
| APPLICATION_DASHBOARDURLVOTERESULTS  | Dashboard URL displayed after voting to view Results `{{groupvar}}` is being replaced by the actual survey (`/vote/<survey>` --> `<survey>`) | https://grafanaserver/stimmungsbarometer?orgId=1&refresh=30s&var-group={{groupvar}} |


## Build application

To build the Springboot application just run:

```bash
./gradlew build
```

You can then start the Spring Boot server with (accessible via http://localhost:8080):

 ```bash
 java -jar java -jar build/libs/fyrabebier-0.0.1-SNAPSHOT.jar
 ```

Or build the docker image

```bash
docker build -it virtual-puzzle-button .
```

## pre built docker image from dockerhub

get the image from Docker Hub `docker pull puzzle/virtual-beerbutton`

https://hub.docker.com/r/puzzle/virtual-beerbutton
