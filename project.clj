(defproject shorelands-api "0.1.0-SNAPSHOT"

  :description "Shorelands Real Estate Api"
  :url "https://www.shorelandsrealestate.com"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [selmer "1.0.2"]
                 [markdown-clj "0.9.86"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "1.0.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.2"]
                 [org.webjars/font-awesome "4.5.0"]
                 [org.webjars.bower/tether "1.1.1"]
                 [org.webjars/jquery "2.2.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.0"]
                 [ring-ttl-session "0.3.0"]
                 [mount "0.1.10"]
                 [cprop "0.1.6"]
                 [buddy "0.10.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [luminus-nrepl "0.1.4"]
                 [luminus-http-kit "0.1.3"]
                 [metosin/compojure-api "1.0.1"]
                 [luminus-log4j "0.1.3"]
                 [com.datomic/datomic-free "0.9.5350"
                  :exclusions [org.slf4j/slf4j-nop org.slf4j/log4j-over-slf4j]]
                 [datomic-schema "1.3.0"]
                 [crypto-password "0.2.0"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :resource-paths ["resources"]

  :main shorelands-api.core

  :plugins [[lein-cprop "1.0.1"]]
  :target-path "target/%s/"
  :profiles
  {:uberjar {:omit-source true
             
             :aot :all
             :uberjar-name "shorelands-api.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "1.0.2"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.1"]]
                  
                  
                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns use}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev {}
   :profiles/test {}})
