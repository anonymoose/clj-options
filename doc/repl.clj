;;
;; Main part of repl workflow.
;;
;; M-x cider-jack-in
;;
;; user> (load-file "doc/repl.clj")
;; "ready."
;; user> (util/dt-now)
;; #<DateTime 2013-12-19T19:41:05.074Z>
;; --- do some stuff in the files --
;; user> (re)
;; "ready."
;;



(require '[clojure.string :as str] :reload)
(use '[clojure.pprint])

(require '[clojure.reflect :as reflect])

;(use '[midje.sweet])
;(use '[midje.repl])

(defonce ignored-namespaces (atom #{}))

(defn re []
  (load-file "doc/repl.clj")(load-file "doc/repl.clj")
  ;(doseq [n (remove (comp @ignored-namespaces ns-name) (all-ns))] (require (ns-name n) :reload ))
  )

(defn obj-methods [o]
  (print-table (:members (reflect/reflect o)))
  )

"ready."
