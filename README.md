In osm-routing-bgl directory:
### Build

    $ mkdir build
    $ cd build
    $ cmake ..
    $ make

In the main directory:

You'll need an osm file, e. g:

    $ wget http://reccos.inf.unideb.hu/~norbi/res/debrecen.osm

Then in Route directory:
 
### Build

mvn clean compile package assembly:single

### Run

java -jar target/firstvan-0.0.1-jar-with-dependencies.jar
