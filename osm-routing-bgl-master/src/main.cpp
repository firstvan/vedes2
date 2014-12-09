#include <iostream>
#include <osmium/io/any_input.hpp>
#include <osmium/index/map/vector.hpp>
#include <osmium/handler/node_locations_for_ways.hpp>
#include "NodeCounter.h"
#include "NearestNode.h"
#include "GraphBuilder.h"
#include "RoutePlanner.h"
#include <stdlib.h>

typedef osmium::index::map::VectorBasedSparseMap<osmium::unsigned_object_id_type,
        osmium::Location, std::vector> index_type;
typedef osmium::handler::NodeLocationsForWays<index_type> location_handler_type;

int main(int argc, char* argv[])
{
    // Print usage
    if (argc != 2)
    {
        std::cerr << "Usage: " << argv[0] << " OSMFILE\n";
        exit(1);
    }

    // Initialize osm file reader
    osmium::io::Reader
    reader(argv[1], osmium::osm_entity_bits::node | osmium::osm_entity_bits::way);
    auto buffer = reader.read();
    reader.close();

    index_type index;
    location_handler_type location_handler(index);

    // Count number of nodes in ways
    NodeCounter node_counter;
    osmium::apply(buffer, node_counter, location_handler);

    GraphBuilder graph_builder(buffer, index, node_counter.nodeSet);
    osmium::apply(buffer, graph_builder);

    double start1, start2, end1, end2;

//    start1 = atof(argv[2]);
//    start2 = atof(argv[3]);
//    end1 = atof(argv[4]);
//    end2 = atof(argv[5]);
//    std::cout << start1;
    std::cin >> start1;
    std::cin >> start2;
    std::cin >> end1;
    std::cin >> end2;

//    osmium::Location start(21.5772, 47.5972);
//    osmium::Location dest(21.663412, 47.54988);
    osmium::Location start(start2, start1);
    osmium::Location dest(end2, end1);

    RoutePlanner route_planner(graph_builder, start, dest);

    for (const auto& l : route_planner.get_route())
    {
        std::cout << l.lat() << " " << l.lon() << std::endl;
    }

}