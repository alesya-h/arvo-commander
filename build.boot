(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.10.339"]
                 [adzerk/boot-cljs          "2.1.4"      :scope "test"]
                 [adzerk/boot-cljs-repl     "0.3.3"      :scope "test"]
                 [powerlaces/boot-figreload "0.5.14"     :scope "test"]
                 [pandeiro/boot-http        "0.8.3"      :scope "test"]
                 [com.cemerick/piggieback   "0.2.1"      :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]
                 [weasel                    "0.7.0"      :scope "test"]

                 [reagent "0.8.1" :exclusions [cljsjs/react cljsjs/react-dom cljsjs/react-create-class]]
                 [historian "1.2.0"]
                 [funcool/lentes "1.2.0"] ; for lense-derived atoms
                 ;; [freactive.core "0.2.0-alpha1"]
                 ])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[powerlaces.boot-figreload :refer [reload]]
; '[pandeiro.boot-http    :refer [serve]]
)

(task-options!
 cljs {:compiler-options {:target :nodejs
                          :install-deps true
                          :npm-deps {"react" "16.2.0"
                                     "react-dom" "16.2.1"
                                     "create-react-class" "15.6.3"
                                     "fs-plus" "3.1.1"

                                     "jsdom" "11.12.0"

                                     "neo-blessed" "0.2.0"
                                     "react-blessed" "0.5.0"}}})

(deftask build
  "This task contains all the necessary steps to produce a build
   You can use 'profile-tasks' like `production` and `development`
   to change parameters (like optimizations level of the cljs compiler)"
  []
  (comp (speak)

        (cljs)
        (target)
        ))

(deftask run
  "The `run` task wraps the building of your application in some
   useful tools for local development: an http server, a file watcher
   a ClojureScript REPL and a hot reloading mechanism"
  []
  (comp ;(serve)
        (watch)
        (cljs-repl)

        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
