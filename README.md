reactive-web
============
# DEMO application with play framework and facebook's BigPipe with slides

## The easiest way to run both slides and demo application is to use docker:

### Docker for macosx
```
brew install boot2docker
boot2docker init
boot2docker up
$(boot2docker shellinit)
```
then using docker:
```
docker run -d matiwinnetou/reactive-web-slides
docker run -d matiwinnetou/reactive-web-server
```

Now navigate to:
```
`boot2docker ip 2> /dev/null`:8000 (usually http://192.168.59.103:8000)
```
Now navigate to:
```
`boot2docker ip 2> /dev/null`:9000 ((usually http://192.168.59.103:8000)
```

this would be similar on linux system but without a need to install boot2docker just docker and making sure kernel supports docker

it is also possible to run demo without docker:

# Running DEMO  (Mac Osx)
* install Java 8 (brew cask install java)
* install sbt (brew install sbt)
* git clone https://github.com/matiwinnetou/reactive-web
* cd reactive-web
* sbt run
* navigate to http://localhost:9000

# Running presentation (Mac Osx)
* install nodejs (brew install node)
* install grunt (sudo npm install -g grunt-cli)
* git clone https://github.com/matiwinnetou/reactive-web
* cd reactive-web/slides
* npm install
* grunt serve
* navigate to http://localhost:8000
