# Paper Reproduction for "Aroma: Code Recommendation via Structural Code Search"

This archive contains source code to help reproduce the evaluation results on my own datasets.

## Reference Implementation and Examples

### Java to Simplified Parse Tree Converter

File: `reference/src/main/java/ConvertJava.java`

This file contains a reference implementation of a parser that parses Java source files, and extracts Simplified Parse Trees, as defined in Section 3.1 of the paper.

An example input file is `reference/data/example_query.java`. The example output by the parser program is `reference/data/example_query.json`. The output file contains information about the input, as well as the Simplified Parse Tree in JSON format.

### Main Algorithm

File: `reference/src/main/python/similar.py`

This file contains a reference implementation of the main Aroma algorithm. It contains the indexing stage, as well as the search stages (light-weight search, prune and rerank, cluster and intersect).

Specifically, the features produced for the same example input file is listed in `reference/data/example_features.txt`, where each line represents a feature.

### Build and Run

File: `reference/run.sh`

To build and run the reference implementation, follow the instructions in the `test.sh` file. Java, Maven and Python must be available in the environment.

The other files in the `reference/` directory are support files (Maven build configurations and ANTLR4 grammar files).

## References

- Sergio Mover, Rhys Olsen, Bor-Yuh Evan Chang, Sriram Sankaranarayanan. _Mining Framework Usage Graphs from App Corpora_. SANER 2018.