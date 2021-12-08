(defproject scheme-interpreter "0.1.0-SNAPSHOT"
  :description "Scheme interpreter"
  :url "https://github.com/fsicardir/scheme-interpreter"
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :main ^:skip-aot scheme-interpreter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
