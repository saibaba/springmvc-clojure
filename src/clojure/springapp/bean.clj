(ns springapp.bean)

(defn set-field
  [instance key value]
  (swap! (.state instance) into {key value}))

(defn get-field
  [instance key]
  (@(.state instance) key))
