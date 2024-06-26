.PHONY: clean install test

clean:
	@mvn clean

install:
	@mvn clean install

test:
	@mvn clean test
