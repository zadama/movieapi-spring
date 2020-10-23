# Movies REST API(Hibernate, Postgres,Spring)
API which can create, read, update and delete from a Postgres database using Hibernate.

The API can be found at: https://protected-plains-75730.herokuapp.com/

## Endpoints
Endpoints     | Type          | Description
------------- | ------------- | -------------
"/api/v1/movies"                           | (GET) | All movies
"/api/v1/movies"                           | (POST) | Adds a movies
"/api/v1/movies/{id}"                    | (PUT) | Updates/Replaces a movie
"/api/v1/movies/{id}"      | (DELETE)| Deletes a movie.
"/api/v1/movies/{movie_id}/actors/{actor_id}"      | (DELETE)| Deletes an actor from a movie.
"/api/v1/movies/actors"                    | (POST) | Adds a new movie WITH actors.
"/api/v1/movies/{id}"                | (GET)| Gets a specific movie
"/api/v1/movies/{movie_id}/actors/{actor_id}"      | (POST)| Adds an actor to a movie
"/api/v1/actors"       | (GET)| All Actors
"/api/v1/actors/{id}" | (GET)| Gets a specific actor.
"/api/v1/actors/{id}" | (DELETE)| Deletes an actor.
"/api/v1/actors/filter?from={date}&to={date}" | (GET)|  Gets an actor whose date of birth is between the two dates.
"/api/v1/movies/search?title={string}" | (GET)|  Gets a movie based on title.

The endpoints are pretty self explanatory, hence the Genre and Director endpoints not shown. They are identical to Actor, replacing "actors" with e.g. "genre" & "directors".
