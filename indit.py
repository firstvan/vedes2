#!/bin/python2
# encoding: utf-8

import os
import sys

def main():
    
    if len(sys.argv) == 5:
        s1 = sys.argv[1]
        s2 = sys.argv[2]
        e1 = sys.argv[3]
        e2 = sys.argv[4]
        f = open("cord.txt", "w");
        f.write("{a} {b} {c} {d}".format(a=s1, b=s2, c=e1, d=e2))
        f.close()
        cmd="./osm-routing-bgl-master/dist/osm-routing-bgl debrecen.osm < cord.txt > Route/way.txt"
        os.system(cmd)
        os.system("rm cord.txt")
        f = open("Route/way.txt")
        s = f.readlines();
        f.close();
        if len(s) == 1:
            print "Út nem található"
        else:
#            cmd="cd Route; mvn clean compile package assembly:single"
#            os.system(cmd)
            cmd="cd Route; java -jar target/firstvan-0.0.1-jar-with-dependencies.jar"
            os.system(cmd)

    else:
        print "Használat ./indit.py indulási lat indulási long érkezési lat érkezési long"


if __name__ == "__main__":
    main()
