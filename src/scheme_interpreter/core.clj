(ns scheme-interpreter.core
  (:gen-class)
  (:require [clojure.test :refer :all]
            [scheme-interpreter.scheme :refer :all]))

(defn -main
  [& args]
  (repl))
