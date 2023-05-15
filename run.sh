# installation
mvn clean package

# compile entire corpus to ast json file and create jsrc.json from all Java files under jsrc
mvn exec:java -Dexec.mainClass=ConvertJava -Dexec.args="/Users/huangxin/code/java/aroma-convert-java/outputs/codebase_convert/jsrc.json /Users/huangxin/code/java/aroma-convert-java/datasets/codebase_all/jsrc"
# convert query_file.java into ast in json format
mvn exec:java -Dexec.mainClass=ConvertJava -Dexec.args="compilationUnit /Users/huangxin/code/java/aroma-convert-java/outputs/query_convert/query_test.json /Users/huangxin/code/java/aroma-convert-java/datasets/query_all/test_all"

# featurize asts
time python3 src/main/python/similar.py -c /Users/huangxin/code/java/aroma-convert-java/outputs/codebase_convert/jsrc.json  -d /Users/huangxin/code/java/aroma-convert-java/outputs/codebase_all_featurize_asts
# run experiments assuming that featurization has already been done
time python3 src/main/python/similar.py -d /Users/huangxin/code/java/aroma-convert-java/outputs/codebase_all_featurize_asts -t
# search code at index 83403 of the corpus
time python3 src/main/python/similar.py -d /Users/huangxin/code/java/aroma-convert-java/outputs/codebase_all_featurize_asts -i 7
# search using the query ast in query_file.json
time python3 src/main/python/similar.py -d /Users/huangxin/code/java/aroma-convert-java/outputs/codebase_all_featurize_asts -f query_file.json
