# CURL REST API

##Meals
#####GET getAll:
*curl http://localhost:8080/topjava/rest/meals*

#####GET get:
*curl http://localhost:8080/topjava/rest/meals/100002*

#####GET getBetween:
*curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=13:00&endDate=2020-01-31&endTime=20:00"*

#####DELETE delete:
*curl -X DELETE http://localhost:8080/topjava/rest/meals/100004*

#####PUT update:
*curl -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2020-01-30T10:02:00","description":"Updated meals","calories":300}' http://localhost:8080/topjava/rest/meals/100002*

#####POST create:
*curl -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2020-02-01T18:00:00","description":"Create meals","calories":300}' http://localhost:8080/topjava/rest/meals*
