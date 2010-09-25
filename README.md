# clj-json

Fast JSON encoding and decoding for Clojure via the Jackson library.

## Synopsis

    (require '(clj-json [core :as json]))
    (import '(java.io StringReader BufferedReader))
    
    (json/generate-string {"foo" "bar"})
    "{\"foo\":\"bar\"}"
    
    (json/parse-string "{\"foo\":\"bar\"}")
    {"foo" "bar"}
    
    (json/parsed-seq
      (BufferedReader. (StringReader. "{\"foo\":\"bar\"}{\"biz\":\"bat\"}")))
    ({"foo" "bar"} {"biz" "bat"})
    

## Installation

`clj-json` is available as a Maven artifact from [Clojars](http://clojars.org/clj-json).

## Encoding/Decoding Details

`clj-json` can generate JSON for maps, vectors, lists, keywords, strings, integers, doubles, and booleans.

Note that keywords generate as strings without a leading :, and will
read via `parse-string` and `parsed-seq` as strings.

## Development

To build, test, and package the `clj-json` source:
    
    $ lein deps
    $ lein javac
    $ lein test
    $ lein jar
