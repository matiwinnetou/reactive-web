## DEMO application with play framework and facebook's BigPipe with slides

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
docker run -ti --rm -p 8000:8000 matiwinnetou/reactive-web-slides
docker run -ti --rm -p 9000:8000 matiwinnetou/reactive-web-server
```

find out your docker ip using:
```
IP=boot2docker ip 2> /dev/null
```
For slides navigate to:
```
http://$IP:8000
```
For web app demo navigate to:
```
http://$IP:9000
```

### Docker for linux
```
docker run -ti --rm -p 8000:8000 matiwinnetou/reactive-web-slides
docker run -ti --rm -p 9000:9000 matiwinnetou/reactive-web-server
```

For slides navigate to:
```
http://localhost:8000
```
For web app demo navigate to:
```
http://localhost:9000
```

## it is also possible to run demo without docker:

# Running DEMO  (Mac Osx) using brew and brew cask
```
brew cask install java
brew install sbt
git clone https://github.com/matiwinnetou/reactive-web
cd reactive-web
sbt run
```

# Running presentation (Mac Osx)
```
brew install node
sudo npm install -g grunt-cli
git clone https://github.com/matiwinnetou/reactive-web
cd reactive-web/slides
npm install
grunt serve
```
navigate to http://localhost:8000
