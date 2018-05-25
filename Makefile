comma:=,
empty:=
space:= $(empty) $(empty)

post:
	@ $(eval ids := $(foreach author, $(authors),{"id":${author}}))
	@ $(eval json := $(subst $(space),$(comma),$(strip $(ids))))
	curl -X POST -H "Content-Type: application/json" -d '{"title":"$(title)", "authors":[$(json)]}' http://localhost:8081/books -i

get:
ifdef id
	@ curl http://localhost:8081/books/$(id)
else
	@ curl http://localhost:8081/books
endif

package:
	@ echo "Packaging books project"
	@ mvn clean package && rm -f books.out

up: package
	@ echo "Start books project"
	@ nohup java -jar target/*.jar &> books.out&

down:
	@ echo "Stop books project"
	@ jps | grep "books-.*\.jar" | cut -f 1 -d ' ' | xargs kill -9
