# clj-json

Fast JSON encoding and decoding for Clojure via the Jackson library.

## Synopsis

```clojure
(require '(clj-json [core :as json]))
(import '(java.io StringReader BufferedReader))

(json/generate-string {"foo" "bar"})
"{\"foo\":\"bar\"}"

(json/parse-string "{\"foo\":\"bar\"}")
{"foo" "bar"}

(json/parsed-seq
  (BufferedReader. (StringReader. "{\"foo\":\"bar\"}{\"biz\":\"bat\"}")))
({"foo" "bar"} {"biz" "bat"})
```

## Installation

`clj-json` is available as a Maven artifact from [Clojars](http://clojars.org/clj-json).

## Encoding/Decoding Details

`clj-json` can generate JSON for maps, vectors, lists, keywords, strings, integers, doubles, floats, and booleans.

Note that keywords generate as strings without a leading :, and will read via `parse-string` and `parsed-seq` as strings,
however both have an optional boolean argument to keywordize the keys of maps.

## Redefining Coercions

Coercions can be redefined by binding `*coercions*`. For example, to coerce sets into existence map:

```clojure
(binding [clj-json.core/*coercions* {clojure.lang.PersistentHashSet (fn [x] (reduce (fn [acc x] (assoc acc x true)) {} x))}]
  (is (= {"foo" {"bang" true, "bar" true}}
       (json/parse-string
        (json/generate-string {"foo" #{"bar" "bang"}})))))
```

## Development

To build, test, and package the `clj-json` source using Leiningen:

    $ lein deps
    $ lein javac
    $ lein test
    $ lein jar

## License

Release under an MIT license.
